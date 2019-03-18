package com.qyy.app.lipstick.ui.activity.mall;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.ToastUtils;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.api.MallApiService;
import com.qyy.app.lipstick.ui.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-18 10:43
 * @name: LinkmanActivity
 */
public class LinkmanActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_linkman)
    EditText etLinkman;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_ok)
    TextView tvOk;

    private long orderId;
    private MallApiService mMallApiService;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_linkmain;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setCenterTitleText("联系方式");
        orderId=getIntent().getLongExtra("orderId",0);
        mMallApiService= HttpManager.create(MallApiService.class);

    }

    @OnClick({R.id.tv_cancle, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_ok:
                if (!"".equals(etAddress.getText().toString())&&!"".equals(etAddress.getText().toString())&&!"".equals(etAddress.getText().toString()))
                    saveAddress();
                else {
                    ToastUtils.showShortToast(this,"请完善信息！");
                }
                break;
        }
    }
    private void saveAddress(){
        Call<RespInfo<Object>> call= mMallApiService.saveAddress((int) orderId,etName.getText().toString(),etAddress.getText().toString(),etLinkman.getText().toString());
        call.enqueue(new NetResponseCall<Object>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<Object>> call, Object data) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            protected void onFail(Call<RespInfo<Object>> call, int type, String code, String tip) {

            }
        });
    }
}
