package com.example.yangdianwen.prtdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by yangdianwen on 16-7-1.
 */
public class MyFragment extends MvpFragment<Iview,Presenter> implements Iview{
    @Bind(R.id.lvRepos)ListView listView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @Bind(R.id.emptyView)TextView emptyView;
    @Bind(R.id.errorView)TextView errorView;
    private ArrayAdapter adapter;
    private  FooteView footerview;
    private static final String TAG = "MyFragment";

    @Override
    public Presenter createPresenter() {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        adapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //开始执行加载数据的方法
              getPresenter() .loadData();
            }
        });
        footerview=new FooteView(getContext());
        Log.d(TAG, "onViewCreated: "+footerview);
        Mugen.with(listView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore:加载数据。。");
                getPresenter().loadMoreData();
            }
            @Override
            public boolean isLoading() {
                return listView.getFooterViewsCount() > 0 && footerview.isLoading();
            }
            @Override
            public boolean hasLoadedAllItems() {
                return listView.getFooterViewsCount() > 0 && footerview.isComplete();
            }
        }).start();
    }

    @OnClick({R.id.emptyView, R.id.errorView})
    public void autoRefresh() {
        ptrClassicFrameLayout.autoRefresh();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void showContentView() {
        ptrClassicFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }
    @Override
    public void showErroView(String msg) {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

   //刷新数据的方法
    public void refreshData(List<String> datas) {
        adapter.clear();
        adapter.addAll(datas);
    }
   //停止刷新
    @Override
    public void stopRefresh() {
          ptrClassicFrameLayout.refreshComplete();
    }
    @Override
    public void showMessage(String msg) {

    }
    //上拉加载更多
    @Override
    public void addMoreData(List<String> datas) {
      adapter.addAll(datas);
    }
    //隐藏footerview
    @Override
    public void hideLoadMore() {
     listView.removeFooterView(footerview);
    }
    //显示正在加载更多
    @Override
    public void showLoadMoreLoading() {
        if (listView.getFooterViewsCount()==0){
            listView.addFooterView(footerview);
        }
           footerview.showLoading();
    }
    //显示加载错误
    @Override
    public void showLoadMoreErro(String msg) {
        if (listView.getFooterViewsCount()==0){
            listView.addFooterView(footerview);
        }
        footerview.showError(msg);
    }
    //显示加载结束
    @Override
    public void showLoadMoreEnd() {
        if (listView.getFooterViewsCount()==0){
            listView.addFooterView(footerview);
        }
        footerview.showComplete();
    }
}