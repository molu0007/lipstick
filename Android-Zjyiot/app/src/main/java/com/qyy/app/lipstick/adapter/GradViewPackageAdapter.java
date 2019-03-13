package com.qyy.app.lipstick.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.model.response.PackageList;

/**
 * @author dengwg
 * @date 2018/3/16
 */
public class GradViewPackageAdapter extends BaseAdapter{
    List<PackageList> mPackageLists;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public GradViewPackageAdapter(List<PackageList> packageLists, Context context) {
        mPackageLists = packageLists;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mPackageLists==null?0:mPackageLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mPackageLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_gr_package, null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        if (i==clickTemp){
            viewHolder.mTextViewName.setTextColor(mContext.getResources().getColor(R.color.color_white));
            viewHolder.mTextViewInfo.setTextColor(mContext.getResources().getColor(R.color.color_white));
            viewHolder.mLinearLayout.setBackgroundResource(R.mipmap.bg_package_select);
        }else {
            viewHolder.mLinearLayout.setBackgroundResource(R.mipmap.bg_package);
            viewHolder.mTextViewName.setTextColor(mContext.getResources().getColor(R.color.color_black));
            viewHolder.mTextViewInfo.setTextColor(mContext.getResources().getColor(R.color.text_7d8eb7));
        }
        viewHolder.mTextViewName.setText(mPackageLists.get(i).getPrdName());
        viewHolder.mTextViewInfo.setText(mPackageLists.get(i).getPrdPrice().toString());
        return view;
    }
    private int clickTemp = -1;
    public void setSelection(int position){
          clickTemp = position;
      }
    public final class ViewHolder{
         public LinearLayout mLinearLayout;
          public TextView mTextViewName;
          public TextView mTextViewInfo;

        public ViewHolder(View view) {
            mLinearLayout=view.findViewById(R.id.ll_bg);
            mTextViewInfo=view.findViewById(R.id.tv_package_price);
            mTextViewName=view.findViewById(R.id.tv_package_name);
        }
    }
}
