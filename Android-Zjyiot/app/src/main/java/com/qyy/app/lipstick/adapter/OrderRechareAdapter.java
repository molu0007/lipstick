package com.qyy.app.lipstick.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ibupush.molu.common.util.NumberUtil;
import com.ibupush.molu.common.util.TimeUtil;

import java.text.DecimalFormat;
import java.util.List;

import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.model.response.OrderDetail;

/**
 * @author dengwg
 * @date 2018/3/20
 */
public class OrderRechareAdapter extends BaseAdapter {
    Context mContext;
    List<OrderDetail> mOrderRecordList;

    LayoutInflater mLayoutInflater;
    public OrderRechareAdapter(Context context, List<OrderDetail> orderRecordList) {
        mContext = context;
        mOrderRecordList = orderRecordList;
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mOrderRecordList==null?0:mOrderRecordList.size();
    }

    @Override
    public Object getItem(int i) {
        return mOrderRecordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
         ViewHolder viewHolder;
        if (view==null){
            view =mLayoutInflater.inflate(R.layout.item_order_rechare,null);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.tv_rechare_date.setText(TimeUtil.formatTime(mOrderRecordList.get(i).getDateCreated(),"yyyy-MM-dd HH:mm:ss"));
        viewHolder.tv_order_no.setText(mOrderRecordList.get(i).getClientOrderNo());
        DecimalFormat fnum  =   new  DecimalFormat("##0.00");
        String   price=fnum.format(Math.abs(NumberUtil.toFloat(mOrderRecordList.get(i).getGoodsAmountDis().toString())));
        viewHolder.tv_order_price.setText("￥"+price);
        viewHolder.tv_package_name.setText(mOrderRecordList.get(i).getFlowCardName());
        if (mOrderRecordList.get(i).getStatus().equals("00")){//待支付
            viewHolder.tv_pay.setVisibility(View.VISIBLE);
        }else if (mOrderRecordList.get(i).getStatus().equals("10")){
            viewHolder.tv_pay.setVisibility(View.GONE);
            viewHolder.tv_transaction_status.setText("支付成功");
            viewHolder.tv_transaction_status.setTextColor(mContext.getResources().getColor(R.color.text_0dd51a));
        }else if (mOrderRecordList.get(i).getStatus().equals("20")){
            viewHolder.tv_pay.setVisibility(View.GONE);
            viewHolder.tv_transaction_status.setTextColor(mContext.getResources().getColor(R.color.text_7d8eb7));
            viewHolder.tv_transaction_status.setText("交易中");
        }else if (mOrderRecordList.get(i).getStatus().equals("30")){
            viewHolder.tv_pay.setVisibility(View.GONE);
            viewHolder.tv_transaction_status.setTextColor(mContext.getResources().getColor(R.color.text_0dd51a));
            viewHolder.tv_transaction_status.setText("交易成功");
        }else if (mOrderRecordList.get(i).getStatus().equals("31")){
            viewHolder.tv_pay.setVisibility(View.GONE);
            viewHolder.tv_transaction_status.setTextColor(mContext.getResources().getColor(R.color.text_7d8eb7));
            viewHolder.tv_transaction_status.setText("交易失败");
        }else if (mOrderRecordList.get(i).getStatus().equals("88")){
            viewHolder.tv_transaction_status.setTextColor(mContext.getResources().getColor(R.color.text_7d8eb7));
            viewHolder.tv_pay.setVisibility(View.GONE);
            viewHolder.tv_transaction_status.setText("交易成功");
        }else if (mOrderRecordList.get(i).getStatus().equals("98")){
            viewHolder.tv_pay.setVisibility(View.GONE);
            viewHolder.tv_transaction_status.setTextColor(mContext.getResources().getColor(R.color.text_7d8eb7));
            viewHolder.tv_transaction_status.setText("失效订单");
        }else if(mOrderRecordList.get(i).getStatus().equals("99")) {
            viewHolder.tv_transaction_status.setTextColor(mContext.getResources().getColor(R.color.text_7d8eb7));
            viewHolder.tv_pay.setVisibility(View.GONE);
            viewHolder.tv_transaction_status.setText("退款订单");
        }

        viewHolder.tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemOnClikeListener!=null){
                    mItemOnClikeListener.onClick(mOrderRecordList.get(i));
                }
            }
        });
        return view;
    }
    ItemOnClikeListener mItemOnClikeListener;
    public void setOnItemClikeListener(ItemOnClikeListener onClikeListener){
        mItemOnClikeListener=onClikeListener;
    }
    public interface ItemOnClikeListener{
        void onClick(OrderDetail resultBean);
    }
    public final class ViewHolder{
        public TextView tv_rechare_date;
        public TextView tv_order_no;
        public TextView tv_order_price,tv_transaction_status,tv_pay,tv_package_name;
        public ViewHolder(View view) {
            tv_package_name =view.findViewById(R.id.tv_package_name);
            tv_rechare_date=view.findViewById(R.id.tv_rechare_date);
            tv_order_no=view.findViewById(R.id.tv_order_no);
            tv_order_price=view.findViewById(R.id.tv_order_price);
            tv_transaction_status=view.findViewById(R.id.tv_transaction_status);
            tv_pay=view.findViewById(R.id.tv_pay);
        }
    }
}
