package com.qyy.app.lipstick.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.adapter.LBaseAdapter;
import com.bumptech.glide.Glide;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.adapter.OrderRechareAdapter;
import com.qyy.app.lipstick.model.response.OrderDetail;
import com.qyy.app.lipstick.model.response.home.GoodsList;
import com.qyy.app.lipstick.ui.activity.home.WebViewActivity;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-11 14:54
 * @name: UrlImgAdapter
 */
public class UrlImgAdapter implements LBaseAdapter<GoodsList.BannerBean> {
    private Context mContext;

    public UrlImgAdapter(Context context) {
        mContext=context;
    }


    ItemOnClikeListener mItemOnClikeListener;
    public void setOnItemClikeListener(ItemOnClikeListener onClikeListener){
        mItemOnClikeListener=onClikeListener;
    }
    public interface ItemOnClikeListener{
        void onClick( GoodsList.BannerBean data);
    }
    @Override
    public View getView(final LMBanners lBanners, final Context context, int position, final GoodsList.BannerBean data) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_banner, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.id_image);
        Glide.with(mContext).load(data.getImage_url()).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if (mItemOnClikeListener!=null){
                     mItemOnClikeListener.onClick(data);
                 }
            }
        });
        return view;
    }

}
