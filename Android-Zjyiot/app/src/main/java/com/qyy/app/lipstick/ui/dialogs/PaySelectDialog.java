package com.qyy.app.lipstick.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.ibupush.molu.common.net.CallDelegate;
import com.qyy.app.lipstick.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-14 15:42
 * @name: PaySelectDialog
 */
public class PaySelectDialog extends DialogFragment implements CallDelegate {
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rb_wx)
    RadioButton rbWx;
    @BindView(R.id.tv_alipy)
    RadioButton tvAlipy;
    @BindView(R.id.bt_cancle)
    Button btCancle;
    @BindView(R.id.bt_ok)
    Button btOk;
    Unbinder unbinder1;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.full_dialog_style);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.full_dialog_style);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_select_pay, null);
        unbinder = ButterKnife.bind(this, view);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean isHostDestroyed() {
        return false;
    }



    @OnClick({R.id.iv_close, R.id.bt_cancle, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                this.dismiss();
                break;
            case R.id.bt_cancle:
                dismiss();
                break;
            case R.id.bt_ok:
                if (mOnSelectListener!=null){
                    if (rbWx.isChecked()){
                        mOnSelectListener.onSelect(0);
                    }else {
                        mOnSelectListener.onSelect(1);
                    }
                }
               dismiss();
                break;
        }
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    OnSelectListener mOnSelectListener;

    public interface OnSelectListener{
       void onSelect(int pos);
    }
}
