package com.qyy.app.lipstick.views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ibupush.molu.common.util.LogUtil;

import com.qyy.app.lipstick.R;

/**
 * @author dengwg
 * @date 2018/3/13
 */
public class UploadingView implements View.OnClickListener {

    public static final String TAG = "UploadingView";

    private Context context;
    private ViewGroup decorView;//activity的根View
    private ViewGroup rootView;//附加View 的 根View

    private boolean dismissing;

    private boolean isShowing;

    CharSequence contenttext;

    public UploadingView(Context context, CharSequence text) {
        this.context = context;
        contenttext = text;
        initViews();
        initEvents();
    }


    public UploadingView(Context context) {
        this.context = context;
        initViews();
        initEvents();
    }

    protected void initViews() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.view_uploading, decorView, false);
        TextView textView = (TextView) rootView.findViewById(R.id.tv_message);
        if (contenttext != null) {
            textView.setText(contenttext);
        }
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }

    protected void initEvents() {
        rootView.setOnClickListener(this);
    }

    /**
     * show的时候调用
     *
     * @param view 这个View
     */
    private void onAttached(View view) {
        decorView.addView(view);
    }

    /**
     * 添加这个View到Activity的根视图
     */
    public void show() {
        if (isShowing()) {
            return;
        }
        isShowing = true;
        onAttached(rootView);
    }

    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        return rootView.getParent() != null || isShowing;
    }

    public void dismiss() {
        if (dismissing) {
            return;
        }

        dismissing = true;
        //从activity根视图移除
        decorView.removeView(rootView);
        isShowing = false;
        dismissing = false;
    }

    public UploadingView setCancelable(boolean isCancelable) {
        View view = rootView.findViewById(R.id.rl_upload);

        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener);
        } else {
            view.setOnTouchListener(null);
        }
        return this;
    }

    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        LogUtil.d(TAG,"对话框显示中,屏蔽应用点击事件");
    }
}
