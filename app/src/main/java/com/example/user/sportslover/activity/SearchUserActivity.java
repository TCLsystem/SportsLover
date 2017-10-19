package com.example.user.sportslover.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.sportslover.R;
import com.example.user.sportslover.adapter.OnRecyclerViewListener;
import com.example.user.sportslover.adapter.SearchUserAdapter;
import com.example.user.sportslover.base.ParentWithNaviActivity;
import com.example.user.sportslover.bean.GroupInfo;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.model.BaseModel;
import com.example.user.sportslover.model.UserModel;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.listener.FindListener;

public class SearchUserActivity extends ParentWithNaviActivity {

    @Bind(R.id.et_find_name)
    EditText et_find_name;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    @Bind(R.id.btn_search)
    Button btn_search;
    @Bind(R.id.rc_view)
    RecyclerView rc_view;
    LinearLayoutManager layoutManager;
    SearchUserAdapter adapter;

    @Override
    protected String title() {
           if (getIntent().getIntExtra("type", 0) == 0) {
            return "Search Friend";
        } else {
            return "Search Crew";
        }

    }

    @Override
    public Object right() {
        /*return R.drawable.contact_add;*/
        return null;
    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {
                finish();
            }

            @Override
            public void clickRight() {
               /* View crewView = LayoutInflater.from(SearchUserActivity.this).inflate(R.layout
                        .item_new_crew, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchUserActivity.this);
                builder.setView(crewView);
                final AlertDialog alertDialog = new AlertDialog.Builder(SearchUserActivity.this).
                        setTitle("Create Crew").setView(crewView).create();
                final EditText crew_name = (EditText) crewView.findViewById(R.id.crew_name);
                final EditText crew_des = (EditText) crewView.findViewById(R.id.crew_des);
                Button btn_sure = (Button) crewView.findViewById(R.id.btn_sure);
                Button btn_cancel = (Button) crewView.findViewById(R.id.btn_cancel);
                btn_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!crew_name.getText().toString().equals("")) {
                            GroupManager.getInstance().sendCreateGroupMessage(crew_name.getText()
                                    .toString(), crew_des.getText().toString(), new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    toast("success");
                                    alertDialog.dismiss();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    alertDialog.dismiss();
                                    toast("error:" + s + i);
                                }
                            });
                        } else {
                            toast("error: please input crew name");
                        }
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();*/
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        initNaviView();
        adapter =new SearchUserAdapter();
        layoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(layoutManager);
        rc_view.setAdapter(adapter);
        sw_refresh.setEnabled(true);
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                User user = adapter.getItem(position);
                bundle.putSerializable("u", user);
                startActivity(UserInfoActivity.class, bundle, false);
            }

            @Override
            public boolean onItemLongClick(int position) {
                return true;
            }
        });
    }

      @OnClick(R.id.btn_search)
    public void onSearchClick(View view) {
        Integer intExtra = getIntent().getIntExtra("type", 0);
        sw_refresh.setRefreshing(true);
        if (intExtra == 0) {
            query();
        } else {
            queryCrew();
        }
    }

    public void query(){
        String name =et_find_name.getText().toString();
        if(TextUtils.isEmpty(name)){
            toast("please input name");
            sw_refresh.setRefreshing(false);
            return;
        }
        UserModel.getInstance().queryUsers(name, BaseModel.DEFAULT_LIMIT, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                sw_refresh.setRefreshing(false);
                adapter.setDatas(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {
                sw_refresh.setRefreshing(false);
                adapter.setDatas(null);
                adapter.notifyDataSetChanged();
                toast(s + "(" + i + ")");
            }
        });
    }

    public void queryCrew() {
        String name = et_find_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            toast("please input crewName");
            sw_refresh.setRefreshing(false);
            return;
        }
        UserModel.getInstance().queryCrew(name, BaseModel.DEFAULT_LIMIT, new
                FindListener<GroupInfo>() {
            @Override
            public void onSuccess(List<GroupInfo> list) {
                sw_refresh.setRefreshing(false);
                adapter.setGroupDatas(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {
                sw_refresh.setRefreshing(false);
                adapter.setGroupDatas(null);
                adapter.notifyDataSetChanged();
                toast(s + "(" + i + ")");
            }
        });
    }

}
