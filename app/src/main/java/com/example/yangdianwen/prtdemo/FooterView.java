package com.example.yangdianwen.prtdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yangdianwen on 16-7-1.
 * 这是上拉加载更多的布局
 */
public class FooterView extends FrameLayout {
    // 代表视图三种状态的静态常量值
    private static final int STATE_LOADING = 0;
    private static final int STATE_COMPLETE = 1;
    private static final int STATE_ERROR = 2;
    @Bind(R.id.progressBar) ProgressBar progressBar;
    @Bind(R.id.tv_complete) TextView tvComplete;
    @Bind(R.id.tv_error) TextView tvError;
    // 默认为加载中状态
    private int state = STATE_LOADING;
    public FooterView(Context context) {
        super(context,null);

    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs,0);

    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.content_load_footer,this,true);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
    }
    //显示加载中
    public void showLoading(){
        state=STATE_LOADING;
        progressBar.setVisibility(VISIBLE);
        tvError.setVisibility(GONE);
        tvComplete.setVisibility(GONE);
    }
    //显示加载错误
    public void showLoadErrow(String msg){
        state=STATE_ERROR;
        progressBar.setVisibility(GONE);
        tvError.setVisibility(VISIBLE);
        tvComplete.setVisibility(GONE);
    }
    //显示所有数据加载完毕
    public void showLoadComplete(){
        state=STATE_COMPLETE;
        progressBar.setVisibility(GONE);
        tvError.setVisibility(GONE);
        tvComplete.setVisibility(VISIBLE);
    }
    public boolean isLoading(){
        return state==STATE_LOADING;
    }
    public  boolean isComplete(){
        return state==STATE_COMPLETE;
    }
    public void setErrorClickListener(OnClickListener onClickListener){
        tvError.setOnClickListener(onClickListener);
    }
}
