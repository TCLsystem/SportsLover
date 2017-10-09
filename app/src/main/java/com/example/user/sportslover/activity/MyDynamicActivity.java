package com.example.user.sportslover.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.sportslover.R;

import butterknife.ButterKnife;

public class MyDynamicActivity extends AppCompatActivity  {
//    @Bind(R.id.title)
//    TextView title;
//    @Bind(R.id.xListView)
//    XListView xListView;
//    @Bind(R.id.loading)
//    RelativeLayout loading;
//    @Bind(R.id.tip)
//    LinearLayout tip;
//
//    private DynamicFragmentPresenter mPresenter;
//    private DynamicAdapter mAdapter;
//    private List<DynamicItem> mList = new ArrayList<>();
//
//    private UserModelImpl mUserModelImpl = new UserModelImpl();
//
//    private List<DynamicItem> mDynamicList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dynamic);
        ButterKnife.bind(this);
//        mPresenter = new DynamicFragmentPresenter(this);
//        mAdapter = new DynamicAdapter(this, R.layout.item_dynamic_listviewother, mList);
//        xListView.setAdapter(mAdapter);
//        xListView.setPullRefreshEnable(true);
//        xListView.setPullLoadEnable(false);
//        xListView.setXListViewListener(this);
//        mPresenter.onRefresh();
//        if (NetUtil.checkNet(this)) {
//            mPresenter.onRefresh();
//        } else {
//            loading.setVisibility(View.GONE);
//            tip.setVisibility(View.VISIBLE);
//            xListView.setVisibility(View.GONE);
//        }
//        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                DynamicItem item = mDynamicList.get(position-1);
//                Intent intent = new Intent(MyDynamicActivity.this, DynamicDetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("DYNAMIC", item);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }




//    @Override
//    public void onLoadMore(List<DynamicItem> list) {
//
//    }
//
//    @Override
//    public void onRefresh(List<DynamicItem> list) {
//        mDynamicList = list;
//        loading.setVisibility(View.GONE);
//        tip.setVisibility(View.GONE);
//        xListView.setVisibility(View.VISIBLE);
//        xListView.stopRefresh();
//        mAdapter.setDatas(list);
//        mAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onRefresh() {
//        mPresenter.onRefresh();
//    }
//
//    @Override
//    public void onLoadMore() {
//
//    }
}
