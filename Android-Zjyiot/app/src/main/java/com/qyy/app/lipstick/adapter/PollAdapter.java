package com.qyy.app.lipstick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import com.qyy.app.lipstick.R;

/**
 * @author dengwg
 * @date 2018/3/20
 */
public class PollAdapter extends RecyclerView.Adapter<PollAdapter.RecyclerHolder>  {

    private final Context mContext;
    private final List<String> mData;
    public PollAdapter(Context context,List<String> mdata) {
        this.mContext = context;
        this.mData=mdata;
    }
    private int clickTemp = 11;
    public void setSelection(int position){
        clickTemp = position;
    }
    @Override
    public PollAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_date_poll, parent, false);
        PollAdapter.RecyclerHolder holder = new PollAdapter.RecyclerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position) {
        if (clickTemp==position){
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.text_white));
            holder.mImageView.setVisibility(View.VISIBLE);
        }else {
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.text_7d8eb4));
            holder.mImageView.setVisibility(View.GONE);
        }
        String data = mData.get(position%mData.size());
        holder.textView.setText(data);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.onClick(position);
                    clickTemp=position;
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 12;
    }
    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RelativeLayout mRelativeLayout;
        ImageView mImageView;
        private RecyclerHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_month);
            mRelativeLayout=itemView.findViewById(R.id.rl_month);
            mImageView=itemView.findViewById(R.id.iv_month);
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }
    OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
       void  onClick(int pos);
    }
}
