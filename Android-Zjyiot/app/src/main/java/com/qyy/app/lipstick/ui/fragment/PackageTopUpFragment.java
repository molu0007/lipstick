package com.qyy.app.lipstick.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.qyy.app.lipstick.BaseApplication;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.adapter.GradViewPackageAdapter;
import com.qyy.app.lipstick.api.HomeApiService;
import com.qyy.app.lipstick.model.response.PackageInfo;
import com.qyy.app.lipstick.model.response.PackageList;

import retrofit2.Call;

/**
 * @author dengwg
 * @date 2018/3/15
 */
public class PackageTopUpFragment extends BasePackageTopUpFragment {
    public static final String mPackageList = "PackageList";
    @BindView(R.id.gr_package)
    GridView mGrPackage;
    HomeApiService mHomeApiService;
    List<PackageList> mPackageLists;
    static TextView mTextView;

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_package;
    }

    public static PackageTopUpFragment newInstance( List<PackageList> mPackageLists,TextView textView) {
        PackageTopUpFragment packageTopUpFragment = new PackageTopUpFragment();
        mTextView=textView;
        Bundle bundle = new Bundle();
        bundle.putSerializable(mPackageList, (Serializable) mPackageLists);
        packageTopUpFragment.setArguments(bundle);
        return packageTopUpFragment;
    }

    @Override
    protected void initData() {
        super.initData();
            List<PackageList> mPackageLists1= (List<PackageList>) getArguments().getSerializable(mPackageList);
        if(mPackageLists1!=null){
            mPackageLists.addAll(mPackageLists1);
        }
        mGradViewPackageAdapter.notifyDataSetChanged();
    }
    GradViewPackageAdapter mGradViewPackageAdapter;
    FlowCardProductInterface mFlowCardProductInterface;
    @Override
    protected void initView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initView(container, savedInstanceState);
        mHomeApiService= HttpManager.create(HomeApiService.class);
        mPackageLists=new ArrayList<>();
        mGradViewPackageAdapter=new GradViewPackageAdapter(mPackageLists,mActivity);
        mGrPackage.setAdapter(mGradViewPackageAdapter);
    }
    public void setFlowCardProduct(FlowCardProductInterface mFlowCardProduct){
        this.mFlowCardProductInterface=mFlowCardProduct;
    }
    @Override
    protected void initListener() {
        super.initListener();
        mGrPackage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getPackageInfo(mPackageLists.get(i).getIdIfcFlowCardProduct()+"");
                mGradViewPackageAdapter.setSelection(i);
            }
        });
    }
    void getPackageInfo(String  id){
        if (id==null||id.equals("")){
            Toast.makeText(getActivity(),"商品id为null",Toast.LENGTH_SHORT);
            return;
        }
        showLoading();
       final Call<RespInfo<PackageInfo>> call=mHomeApiService.getPackageInfo(BaseApplication.iccid, BaseApplication.cardNo, BaseApplication.accoundId,id);
        call.enqueue(new NetResponseCall<PackageInfo>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<PackageInfo>> call, PackageInfo data) {
                mTextView.setText(data.getGoodsAmountDis().toString());
                if (mFlowCardProductInterface!=null){
                    mFlowCardProductInterface.getFlowCardProductId(data.getFlowCardProductId());
                }
                mGradViewPackageAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFail(Call<RespInfo<PackageInfo>> call, int type, String code, String tip) {
                ToastUtils.showLongToast(getActivity(),tip);
            }

            @Override
            protected void onFinish() {
                super.onFinish();
                hideLoading();
            }
        });
    }
   public interface FlowCardProductInterface{
       public void getFlowCardProductId(int flowCardProductId);
   }

}
