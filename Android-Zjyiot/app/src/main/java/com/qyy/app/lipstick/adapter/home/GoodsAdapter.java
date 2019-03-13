package com.qyy.app.lipstick.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.model.response.home.GoodsBean;
import com.qyy.app.lipstick.model.response.home.GoodsList;

import java.util.ArrayList;
import java.util.List;

import static com.qyy.app.lipstick.utils.ResourceUtil.getContext;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-11 10:29
 * @name: GoodsAdapter
 */
public class GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<GoodsBean> mArrayList=new ArrayList();

    public GoodsAdapter(Context context, List<GoodsBean> arrayList) {
        mContext = context;
        mArrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GoodsViewHolder holder = new GoodsViewHolder(LayoutInflater.from(
                getContext()).inflate(R.layout.item_home_goods, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mArrayList==null?0:mArrayList.size();
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        public GoodsViewHolder(View itemView) {
            super(itemView);
        }

    }

}
