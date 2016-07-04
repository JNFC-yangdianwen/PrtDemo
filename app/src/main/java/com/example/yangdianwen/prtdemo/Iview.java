package com.example.yangdianwen.prtdemo;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by yangdianwen on 16-7-2.
 */
public interface Iview extends MvpView,PtrView<List<String>>,LoadMoreView<List<String>>{
}
