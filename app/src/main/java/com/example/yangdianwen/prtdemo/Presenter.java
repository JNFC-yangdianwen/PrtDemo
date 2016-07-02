package com.example.yangdianwen.prtdemo;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yangdianwen on 16-7-2.
 *这是mvp设计模式中的业务逻辑层，
 */
public class Presenter {
    private  Iview view;

    public Presenter(Iview view) {
        this.view = view;
    }
    //下拉刷新加载数据的方法，使用异步处理机制
      public  void loadData(){
          new LoadAsyncTask().execute();
      }
      public void loadMoreData(){
          new LoadMoreData().execute();
      }
   //处理下拉刷新数据的异步任务
    private class LoadAsyncTask extends AsyncTask<Void,Void,List<String>>{
        //获取数据，模拟网络请求数据
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int size=new Random().nextInt(20);
            List<String>data=new ArrayList<>();
            for (int i = 0; i <20 ; i++) {
                data.add("第"+i+"条数据");
            }
            return data;
        }
       //和view建立联系，处理数据的逻辑
        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            int size=strings.size();
            //模拟空视图情况
            if (size==0){
                view.showEmptyView();
            }//显示错误视图情况
            else if (size==1){
                view.showErroView("没有内容");
            }//显示内容视图情况
            else {
                view.showContentView();
                //通知视图刷新数据
                view.refreshData(strings);
            }
            //停止刷新
            view.stopRefresh();
        }
    }
    //使用异步任务处理上拉加载更多的数据
    private class LoadMoreData extends AsyncTask<Void,Void,List<String>> {
        //首先当listview到达底部时,显示上拉加载的布局

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.showLoadMoreLoading();
        }

        //模拟网络获取数据
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<String> data=new ArrayList<>();
            for (int i = 0; i <20 ; i++) {
                data.add("这是上拉加载的第"+i+"条数据");
            }
            return data;
        }
        //与上拉加载的视图建立联系
        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            view.addMoreData(strings);
            view.hideLoadMore();
//            view.stopRefresh();
        }
    }
}