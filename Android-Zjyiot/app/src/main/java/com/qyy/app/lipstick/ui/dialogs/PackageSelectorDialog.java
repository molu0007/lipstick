package com.qyy.app.lipstick.ui.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.CallDelegate;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.LogUtil;
import com.ibupush.molu.common.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.qyy.app.lipstick.BaseApplication;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.adapter.PackageAdapter;
import com.qyy.app.lipstick.api.HomeApiService;
import com.qyy.app.lipstick.model.request.OrderParameter;
import com.qyy.app.lipstick.model.response.OrderDetail;
import com.qyy.app.lipstick.model.response.PackageList;
import com.qyy.app.lipstick.ui.fragment.PackageTopUpFragment;

import retrofit2.Call;

/**
 * @author dengwg
 * @date 2018/3/15
 */
public class PackageSelectorDialog extends DialogFragment implements CallDelegate, PackageTopUpFragment.FlowCardProductInterface {


    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.vp_package)
    ViewPager mVpPackage;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.tv_package_newprice)
    TextView mTextView;
    @BindView(R.id.tv_original_price)
    TextView mTvOriginalPrice;

    @BindView(R.id.bt_wx_pay)
    Button mBtWxPay;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    Unbinder unbinder;
    PackageAdapter mPackageAdapter;
    HomeApiService mHomeApiService;
    private PackageTopUpFragment mFragment;
    private List<Fragment> mFragments;

    private int mPrcId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.full_dialog_style);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = getDialog().getWindow();
        View view = inflater.inflate(R.layout.dialog_package_select, container);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        unbinder = ButterKnife.bind(this, view);
        mHomeApiService= HttpManager.create(HomeApiService.class);
        Dialog dialog=getDialog();
        if (dialog!=null){
            DisplayMetrics dm = new DisplayMetrics();
            //设置弹框的占屏宽        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout(dm.widthPixels, dm.heightPixels);
        }
        mFragments=new ArrayList<>();
        mPackageAdapter=new PackageAdapter(getChildFragmentManager(),mFragments);
        mVpPackage.setAdapter(mPackageAdapter);
        initData();
        return view;
    }

    protected void initData() {
        final Call<RespInfo<List<PackageList>>> call = mHomeApiService.getPackageList(BaseApplication.iccid, BaseApplication.cardNo, BaseApplication.accoundId);
        call.enqueue(new NetResponseCall<List<PackageList>>(this) {

            @Override
            protected void onSuccess(Call<RespInfo<List<PackageList>>> call, List<PackageList> data) {
                LogUtil.d(data.toString());
                int m=data.size()/6;//m页
                List<PackageList> mlist=new ArrayList<>();
                if (m==0){
                    mFragment= PackageTopUpFragment.newInstance(data,mTvOriginalPrice);
                    mFragment.setFlowCardProduct(PackageSelectorDialog.this);
                    mFragments.add(mFragment);
                }else {
                    for (int i=0;i<data.size();i++){
                        mlist.add(data.get(i));
                        if ((i+1)%6==0){
                            List<PackageList> mlist1=new ArrayList<>();
                            mlist1.addAll(mlist);
                            mFragment= PackageTopUpFragment.newInstance(mlist1,mTvOriginalPrice);
                            mFragment.setFlowCardProduct(PackageSelectorDialog.this);
                            mFragments.add(mFragment);
                            mlist.clear();
                        }else if (i-m*6>=0){
                            List<PackageList> mlist1=new ArrayList<>();
                            mlist1.addAll(mlist);
                            if (data.size()-1==i){
                                mFragment= PackageTopUpFragment.newInstance(mlist1,mTvOriginalPrice);
                                mFragment.setFlowCardProduct(PackageSelectorDialog.this);
                                mFragments.add(mFragment);
                                mlist.clear();
                            }
                        }
                    }
                }

                mPackageAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFail(Call<RespInfo<List<PackageList>>> call, int type, String code, String tip) {
                ToastUtils.showLongToast(getActivity(),tip);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_wx_pay, R.id.iv_close,R.id.iv_left,R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_wx_pay:
                toOrder();
                break;
            case R.id.iv_close:
                getDialog().dismiss();
                break;
            case R.id.iv_left:
                if (mVpPackage.getCurrentItem()==0){
                    mVpPackage.setCurrentItem(mFragments.size()-1);
                    break;
                }
                mVpPackage.setCurrentItem(mVpPackage.getCurrentItem()-1);
                break;
            case R.id.iv_right:
                if (mVpPackage.getCurrentItem()==(mFragments.size()-1)){
                    mVpPackage.setCurrentItem(0);
                    break;
                }
                mVpPackage.setCurrentItem(mVpPackage.getCurrentItem()+1);
                break;
        }
    }
    void toOrder(){
        if (mPrcId==0){
            Toast.makeText(getContext(),"商品获取失败！",Toast.LENGTH_SHORT);
            return;
        }
        OrderParameter orderParameter=new OrderParameter();
        orderParameter.setProductId(mPrcId+"");
        Call<RespInfo<OrderDetail>> call = mHomeApiService.getOrderInfo(BaseApplication.iccid,BaseApplication.cardNo,BaseApplication.accoundId,mPrcId+"");
        call.enqueue(new NetResponseCall<OrderDetail>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<OrderDetail>> call, OrderDetail data) {
                OrderDetailDialog orderDetailDialog = OrderDetailDialog.newInstance(data);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                orderDetailDialog.show(ft,"OrderDetailDialog");
//
            }

            @Override
            protected void onFail(Call<RespInfo<OrderDetail>> call, int type, String code, String tip) {
                ToastUtils.showLongToast(getActivity(),tip);
            }

        });
    }
    @Override
    public boolean isHostDestroyed() {
        return false;
    }

    @Override
    public void getFlowCardProductId(int flowCardProductId) {
        mPrcId=flowCardProductId;
        mTextView.setVisibility(View.VISIBLE);
        mTvOriginalPrice.setVisibility(View.VISIBLE);
    }
}
