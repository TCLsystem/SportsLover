package com.example.user.sportslover.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.sportslover.R;
import com.example.user.sportslover.adapter.DynamicAdapter;
import com.example.user.sportslover.bean.DynamicItem;
import com.example.user.sportslover.bean.User;
import com.example.user.sportslover.model.DynamicModelImpr;
import com.example.user.sportslover.model.SportModelInter;
import com.example.user.sportslover.model.UserModelImpl;
import com.example.user.sportslover.presenter.MyDynamicPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
//implements MyDynamic, XListView.IXListViewListener
public class MyDynamicActivity extends AppCompatActivity {
//    @Bind(R.id.my_dynamic_xListView)
//    XListView xListView;
//    @Bind(R.id.my_dynamic_loading)
//    RelativeLayout loading;
//    @Bind(R.id.my_dynamic_tip)
//    LinearLayout tip;
    @Bind(R.id.listview)
     ListView listview;
    @Bind(R.id.tv_my_posted)
    TextView myPosted;
    @Bind(R.id.tv_my_apply)
    TextView myApply;
    @Bind(R.id.tv_dynamic_edit)
    TextView edit;
    @Bind(R.id.iv_myDynamic_back)
    ImageView back;

    private MyDynamicPresenter mPresenter;
    private DynamicAdapter mAdapter;
    private List<DynamicItem> mList = new ArrayList<>();

    private UserModelImpl mUserModel = new UserModelImpl();

    private List<DynamicItem> mDynamicList;

    private DynamicModelImpr mDynamicModel = new DynamicModelImpr();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dynamic);
        ButterKnife.bind(this);

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_my_posted, R.id.tv_my_apply,R.id.tv_dynamic_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_myDynamic_back:
                finish();
                break;
            case R.id.tv_my_posted:
                myApply.setOnClickListener(null);
                mUserModel.getUser(mUserModel.getUserLocal().getObjectId(), new SportModelInter.BaseListener() {
                    @Override
                    public void getSuccess(Object o) {
                        User user = (User) o;
                        mDynamicModel.getDynamicItemByPhone(user, new SportModelInter.BaseListener() {
                            @Override
                            public void getSuccess(Object o) {
                                List<DynamicItem> list = (List<DynamicItem>) o;
                                listview.setAdapter(new DynamicAdapter(MyDynamicActivity.this, R.layout.item_dynamic_list, list));
                            }
                            @Override
                            public void getFailure() {

                            }
                        });
                    }

                    @Override
                    public void getFailure() {

                    }
                });

                break;
            case R.id.tv_my_apply:



                break;
            case R.id.tv_dynamic_edit:


                break;
            default:
                break;
        }

    }

}



