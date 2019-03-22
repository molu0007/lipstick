package com.qyy.app.lipstick.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibupush.molu.common.net.CallDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.qyy.app.lipstick.BaseApplication;
import com.qyy.app.lipstick.R;

/**
 * @author dengwg
 * @date 2018/3/26
 */
public class NoNetToUpDialog extends DialogFragment implements CallDelegate {
    @BindView(R.id.tv_sim_no)
    TextView mTvSimNo;
    @BindView(R.id.tv_iccid)
    TextView mTvIccid;
    Unbinder unbinder;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    private LayoutInflater inflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.full_dialog_style);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(),R.style.full_dialog_style);
        inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.dialog_no_net,null);
        unbinder = ButterKnife.bind(this, view);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        initView();
        return dialog;
    }

    private void initView() {

    }

    @Override
    public boolean isHostDestroyed() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        getDialog().dismiss();
    }
}
