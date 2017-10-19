package com.example.user.sportslover.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.user.sportslover.R;
import com.example.user.sportslover.activity.DynamicDetailActivity;
import com.example.user.sportslover.activity.SendDynamicActivity;
import com.example.user.sportslover.adapter.DynamicAdapter;
import com.example.user.sportslover.adapter.SpinnerAdapter;
import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.model.SportModelInter;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.presenter.DynamicFragmentPresenter;
import com.example.user.sportslover.util.NetUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.maxwin.view.XListView;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link SportsEventFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link SportsEventFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class SportsEventFragment extends Fragment implements IDynamicFragment, XListView
        .IXListViewListener {
    @Bind(R.id.publish)
    ImageView publish;
    @Bind(R.id.myspinner)
    Spinner mySpinner;
    @Bind(R.id.xListView1)
    XListView xListView;
    @Bind(R.id.loading)
    RelativeLayout loading;
    @Bind(R.id.tip)
    LinearLayout tip;


    private DynamicFragmentPresenter mPresenter;
    private DynamicAdapter mAdapter;
    private List<DynamicItem> mList = new ArrayList<>();

    private UserModelImpl mUserModel = new UserModelImpl();

    private List<DynamicItem> mDynamicList;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sports_event, container, false);
        ButterKnife.bind(this, view);
        mySpinner.setAdapter(new SpinnerAdapter(getContext(), android.R.layout
                .simple_spinner_item, getResources().getStringArray(R.array.spingarr)));
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0) {
                   // ToastUtil.showShort(SportsEventFragment.this.getContext(), "id = " + id);
                    xListView.setAdapter(null);
                    mPresenter = new DynamicFragmentPresenter(SportsEventFragment.this);
                    mAdapter = new DynamicAdapter(getActivity(), R.layout.item_dynamic_list, mList);
                    xListView.setAdapter(mAdapter);
                    xListView.setPullRefreshEnable(true);
                    xListView.setPullLoadEnable(false);
                    xListView.setXListViewListener(SportsEventFragment.this);
                    mPresenter.onRefresh();

                   // View ts = xListView.getChildAt(1);
                    //TextView    textView = (TextView)ts.findViewById(R.id.show_activity_Name);

                    if (NetUtil.checkNet(getActivity())) {
                        mPresenter.onRefresh();
                    } else {
                        loading.setVisibility(View.GONE);
                        tip.setVisibility(View.VISIBLE);
                        xListView.setVisibility(View.GONE);
                    }
                    xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {
                            DynamicItem item = mDynamicList.get(position - 1);
                            Intent intent = new Intent(getActivity(), DynamicDetailActivity.class);
                            Bundle bundle = new Bundle();
                            item.getCreatedAt();
                            bundle.putSerializable("DYNAMIC", item);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });


                } else if (id == 1) {
                  //  ToastUtil.showShort(SportsEventFragment.this.getContext(), "id = " + id);
                    mList.clear();
                    mAdapter.notifyDataSetChanged();
                    mPresenter = new DynamicFragmentPresenter(SportsEventFragment.this);
                    mAdapter = new DynamicAdapter(getActivity(), R.layout.item_dynamic_list, mList);
                    xListView.setAdapter(mAdapter);
                    xListView.setPullRefreshEnable(true);
                    xListView.setPullLoadEnable(false);
                    xListView.setXListViewListener(SportsEventFragment.this);
                    mPresenter.onRefreshbyType("Running");
                    if (NetUtil.checkNet(getActivity())) {
                        mPresenter.onRefreshbyType("Running");
                    } else {
                        loading.setVisibility(View.GONE);
                        tip.setVisibility(View.VISIBLE);
                        xListView.setVisibility(View.GONE);
                    }
                    xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {
                            DynamicItem item = mDynamicList.get(position - 1);
                            Intent intent = new Intent(getActivity(), DynamicDetailActivity.class);
                            Bundle bundle = new Bundle();
                            item.getCreatedAt();
                            bundle.putSerializable("DYNAMIC", item);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });

                } else if (id == 2) {
                   // ToastUtil.showShort(SportsEventFragment.this.getContext(), "id = " + id);
                    mList.clear();
                    mAdapter.notifyDataSetChanged();
                    xListView.setAdapter(null);
                    mPresenter = new DynamicFragmentPresenter(SportsEventFragment.this);
                    mAdapter = new DynamicAdapter(getActivity(), R.layout.item_dynamic_list, mList);
                    xListView.setAdapter(mAdapter);
                    xListView.setPullRefreshEnable(true);
                    xListView.setPullLoadEnable(false);
                    xListView.setXListViewListener(SportsEventFragment.this);
                    mPresenter.onRefreshbyType("Walking");
                    if (NetUtil.checkNet(getActivity())) {
                        mPresenter.onRefreshbyType("Walking");
                    } else {
                        loading.setVisibility(View.GONE);
                        tip.setVisibility(View.VISIBLE);
                        xListView.setVisibility(View.GONE);
                    }
                    xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {
                            DynamicItem item = mDynamicList.get(position - 1);
                            Intent intent = new Intent(getActivity(), DynamicDetailActivity.class);
                            Bundle bundle = new Bundle();
                            item.getCreatedAt();
                            bundle.putSerializable("DYNAMIC", item);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                } else if (id == 3) {
                   // ToastUtil.showShort(SportsEventFragment.this.getContext(), "id = " + id);
                    mList.clear();
                    mAdapter.notifyDataSetChanged();
                    xListView.setAdapter(null);
                    mPresenter = new DynamicFragmentPresenter(SportsEventFragment.this);
                    mAdapter = new DynamicAdapter(getActivity(), R.layout.item_dynamic_list, mList);
                    xListView.setAdapter(mAdapter);
                    xListView.setPullRefreshEnable(true);
                    xListView.setPullLoadEnable(false);
                    xListView.setXListViewListener(SportsEventFragment.this);
                    mPresenter.onRefreshbyType("Riding");
                    if (NetUtil.checkNet(getActivity())) {
                        mPresenter.onRefreshbyType("Riding");
                    } else {
                        loading.setVisibility(View.GONE);
                        tip.setVisibility(View.VISIBLE);
                        xListView.setVisibility(View.GONE);
                    }
                    xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {
                            DynamicItem item = mDynamicList.get(position - 1);
                            Intent intent = new Intent(getActivity(), DynamicDetailActivity.class);
                            Bundle bundle = new Bundle();
                            item.getCreatedAt();
                            bundle.putSerializable("DYNAMIC", item);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.publish)
    public void onClick() {
        mUserModel.getUser(mUserModel.getUserLocal().getObjectId(), new SportModelInter
                .BaseListener() {
            @Override
            public void getSuccess(Object o) {
                User user = (User) o;
                Intent intent = new Intent(getActivity(), SendDynamicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("User", user);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }

            @Override
            public void getFailure() {

            }
        });
    }

    @Override
    public void onLoadMore(List<DynamicItem> list) {

    }


    @Override
    public void onRefreshByType(List<DynamicItem> list) {

    }

    @Override
    public void onRefresh(List<DynamicItem> list) {
        mDynamicList = list;
        loading.setVisibility(View.GONE);
        tip.setVisibility(View.GONE);
        xListView.setVisibility(View.VISIBLE);
        xListView.stopRefresh();
        mAdapter.setDatas(list);
        mAdapter.notifyDataSetChanged();
    }
//
//    @Override
//    public void onRefreshByType(List<DynamicItem> list) {
//        mDynamicList = list;
//        loading.setVisibility(View.GONE);
//        tip.setVisibility(View.GONE);
//        xListView.setVisibility(View.VISIBLE);
//        xListView.stopRefresh();
//        mAdapter.setDatas(list);
//        mAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    public void onRefreshbyType(String type) {
        mPresenter.onRefreshbyType(type);
    }

    @Override
    public void onLoadMore() {

    }
}



