package com.example.user.sportslover.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.activity.ChatActivity;
import com.example.user.sportslover.activity.ConversationActivity;
import com.example.user.sportslover.activity.NewFriendActivity;
import com.example.user.sportslover.activity.SearchUserActivity;
import com.example.user.sportslover.adapter.ContactAdapter;
import com.example.user.sportslover.adapter.OnRecyclerViewListener;
import com.example.user.sportslover.adapter.base.IMutlipleItem;
import com.example.user.sportslover.base.ParentWithNaviActivity;
import com.example.user.sportslover.base.ParentWithNaviFragment;
import com.example.user.sportslover.bean.Friend;
import com.example.user.sportslover.bean.GroupInfo;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.customview.SideBar;
import com.example.user.sportslover.event.RefreshEvent;
import com.example.user.sportslover.model.BaseModel;
import com.example.user.sportslover.model.UserModel;
import com.example.user.sportslover.widget.CharacterParser;
import com.example.user.sportslover.widget.PinyinComparator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

public class ContactFragment extends ParentWithNaviFragment implements SideBar
        .OnTouchingLetterChangedListener, View.OnClickListener/*,IDynamicFragment*/ {

    @Bind(R.id.rl_new_friend)
    RelativeLayout rl_new_friend;
    @Bind(R.id.sports_friend_dialog)
    TextView mDialog;
    @Bind(R.id.sports_friend_sidebar)
    SideBar mSideBar;
    @Bind(R.id.tab_myFriend)
    TextView mFiend;
    @Bind(R.id.tab_myCrew)
    TextView mCrew;
    @Bind(R.id.scrollbarType)
    ImageView scrollbarType;
    @Bind(R.id.sports_friend_member)
    RecyclerView rc_view;
    /* @Bind(R.id.sports_crew_member)
     XListView sports_crew_member;*/
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    ContactAdapter adapter;
    LinearLayoutManager layoutManager;
    private static int currIndex = 0;
    private static int preIndex = 0;
    private int offset = 0;
    private int one;
    // String alphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static CharacterParser characterParser;// 汉字转拼音
    private static PinyinComparator pinyinComparator;// 根据拼音来排列ListView里面的数据类
    private List<Friend> sortList = new ArrayList<Friend>();

/*

    private DynamicFragmentPresenter mPresenter;
    private DynamicAdapter mAdapter;
    private List<DynamicItem> mList = new ArrayList<>();
*/

    @Override
    protected String title() {
        return "Contacts";
    }

    @Override
    public Object right() {
        return R.mipmap.icon_message;
    }

    @Override
    public Object left() {
        return R.drawable.base_action_bar_add_bg_selector;
    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {
                Intent intent = new Intent(getActivity(), SearchUserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", currIndex);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }

            @Override
            public void clickRight() {
                startActivity(ConversationActivity.class, null);
                /*UserModel.getInstance().logout();
                //可断开连接
                BmobIM.getInstance().disConnect();
                getActivity().finish();
                startActivity(LoginActivity.class, null);*/
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        initNaviView();
        //实例化汉字转拼音
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        ButterKnife.bind(this, rootView);
        IMutlipleItem<Friend> mutlipleItem = new IMutlipleItem<Friend>() {

            @Override
            public int getItemViewType(int postion, Friend friend) {
                if (postion == 0) {
                    return ContactAdapter.TYPE_NEW_FRIEND;
                } else {
                    return ContactAdapter.TYPE_ITEM;
                }
            }

            @Override
            public int getItemLayoutId(int viewtype) {
                if (viewtype == ContactAdapter.TYPE_NEW_FRIEND) {
                    return R.layout.header_new_friend;
                } else {
                    return R.layout.item_contact;
                }
            }

            @Override
            public int getItemCount(List<Friend> list) {
                return list.size() + 1;
            }
        };
        adapter = new ContactAdapter(getActivity(), mutlipleItem, null);
        rc_view.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        rc_view.setLayoutManager(layoutManager);
        sw_refresh.setEnabled(true);
        setListener();
        //好友/运动团切换动画
        int bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.bar).getWidth();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenW = displayMetrics.widthPixels;
        offset = (screenW / 2 - bmpW) / 2;
        one = offset * 2 + bmpW;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        scrollbarType.setImageMatrix(matrix);
        mFiend.setOnClickListener(this);
        mCrew.setOnClickListener(this);
        mSideBar.setTextView(mDialog);
        mSideBar.setOnTouchingLetterChangedListener(this);

       /* mPresenter = new DynamicFragmentPresenter(this);
        mAdapter = new DynamicAdapter(getActivity(), R.layout.item_dynamic_list, mList);*/
        // sports_crew_member.setAdapter(new SportsEventFragment().xListView.getAdapter());

        return rootView;
    }

    /*@Override
    public void onLoadMore(List<DynamicItem> list) {

    }
    @Override
    public void onRefresh(List<DynamicItem> list) {
      *//*  mDynamicList = list;
        loading.setVisibility(View.GONE);
        tip.setVisibility(View.GONE);
        xListView.setVisibility(View.VISIBLE);
        xListView.stopRefresh();
        mAdapter.setDatas(list);
        mAdapter.notifyDataSetChanged();*//*
    }

*/
    @OnClick(R.id.rl_new_friend)
    public void showNewFriend() {
        startActivity(NewFriendActivity.class, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_myFriend:
                preIndex = 0;
                rc_view.setVisibility(View.VISIBLE);
                break;
            case R.id.tab_myCrew:
                preIndex = 1;
                rc_view.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        setScrollbarType(preIndex);

    }

    public void setScrollbarType(int index) {
        Animation animation = null;
        if (index != currIndex) {
            if (index == 0) {
                animation = new TranslateAnimation(one, 0, 0, 0);
            } else {
                animation = new TranslateAnimation(offset, one, 0, 0);
            }
            currIndex = index;
            animation.setFillAfter(true);
            animation.setDuration(100);
            scrollbarType.startAnimation(animation);
        }
        if (currIndex == 1) {
            UserModel.getInstance().queryCrew("a", BaseModel.DEFAULT_LIMIT, new
                    FindListener<GroupInfo>() {
                @Override
                public void onSuccess(List<GroupInfo> list) {
                    list.size();
                }

                @Override
                public void onError(int i, String s) {
                }
            });

        }

    }

    private void setListener() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                sw_refresh.setRefreshing(true);
                query();
            }
        });
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {//跳转到新朋友页面
                    startActivity(NewFriendActivity.class, null);
                } else {
                    Friend friend = adapter.getItem(position);
                    User user = friend.getFriendUser();
                    BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername
                            (), user.getAvatar());
                    //启动一个会话，实际上就是在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
                    BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info,
                            null);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("c", c);
                    startActivity(ChatActivity.class, bundle);
                }
            }

            @Override
            public boolean onItemLongClick(final int position) {
                log("长按" + position);
                if (position == 0) {
                    return true;
                }
                UserModel.getInstance().deleteFriend(adapter.getItem(position), new
                        DeleteListener() {
                    @Override
                    public void onSuccess() {
                        toast("好友删除成功");
                        adapter.remove(position);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toast("好友删除失败：" + i + ",s =" + s);
                    }
                });
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sw_refresh.setRefreshing(true);
        query();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * 注册自定义消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        //重新刷新列表
        log("---联系人界面接收到自定义消息---");
        adapter.notifyDataSetChanged();
    }

    /**
     * 查询本地会话
     */
    public void query() {
        UserModel.getInstance().queryFriends(new FindListener<Friend>() {
            @Override
            public void onSuccess(List<Friend> list) {
                sortList = getSortedList(list);
                Collections.sort(getSortedList(list), pinyinComparator);
                adapter.bindDatas(list);
                adapter.notifyDataSetChanged();
                sw_refresh.setRefreshing(false);
            }

            @Override
            public void onError(int i, String s) {
                adapter.bindDatas(null);
                adapter.notifyDataSetChanged();
                sw_refresh.setRefreshing(false);
            }
        });
    }

    @OnTextChanged(R.id.sports_friend_member_search_input)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        List<Friend> filterDateList = new ArrayList<Friend>();

        if (TextUtils.isEmpty(s.toString())) {
            filterDateList = sortList;
        } else {
            filterDateList.clear();
            for (Friend friend : sortList) {
                String name = friend.getFriendUser().getUsername();
                String firstSpell = friend.getfirstSpell();
                if (name.indexOf(s.toString()) != -1 || firstSpell.indexOf(s.toString()) != -1 ||
                        characterParser.getSelling(name).startsWith(s.toString())) {
                    filterDateList.add(friend);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.bindDatas(filterDateList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int position = 0;
        // 该字母首次出现的位置
        if (adapter != null) {
            for (int i = 0; i < sortList.size(); i++) {
                String sortStr = sortList.get(i).getSortLetters();
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == s.charAt(0)) {
                    position = i;
                    break;
                }
            }
        }
        if (position != -1) {
            rc_view.scrollToPosition(position + 1);
        }
    }

    /**
     * 好友拼音排序
     *
     * @param list
     */
    private List<Friend> getSortedList(List<Friend> list) {
        for (Friend friend : list) {
            if (friend != null && friend.getFriendUser().getUsername() != null) {
                String firstSpell = CharacterParser.getFirstSpell(friend.getFriendUser()
                        .getUsername());
                friend.setfirstSpell(firstSpell);
                String pinyin = characterParser.getSelling(friend.getFriendUser().getUsername());
                String sortString = pinyin.substring(0, 1).toUpperCase();

                if ("1".equals(friend.getUsertype())) {// 判断是否是管理员
                    friend.setSortLetters("☆");
                } else if (sortString.matches("[A-Z]")) {// 正则表达式，判断首字母是否是英文字母
                    friend.setSortLetters(sortString);
                } else {
                    friend.setSortLetters("#");
                }
            }
        }
        return list;
    }

}
