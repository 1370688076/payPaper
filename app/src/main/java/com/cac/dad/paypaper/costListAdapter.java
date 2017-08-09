package com.cac.dad.paypaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.security.PublicKey;
import java.util.List;
import java.util.zip.Inflater;

import static android.R.attr.id;
import static android.R.attr.listChoiceBackgroundIndicator;

/**
 * Created by Dad on 2017/8/9.
 */

public class costListAdapter  extends BaseAdapter{

    private List<CostBean> mList;
    private Context context;
    private LayoutInflater minflater;



    public costListAdapter(Context context,List<CostBean>list){
        this.mList=list;
        this.context=context;
        minflater= LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder viewholder;
        if(view==null) {
            viewholder = new viewHolder();
            view = minflater.inflate(R.layout.item, null);
            viewholder.mtvCostTitle = view.findViewById(R.id.tv_title);
            viewholder.mtvCostDate = view.findViewById(R.id.tv_date);
            viewholder.mtvCostMoney = view.findViewById(R.id.tv_money);
            view.setTag(viewholder);
        }
        else
        {
            viewholder= (viewHolder) view.getTag();
        }
        CostBean bean=mList.get(mList.size()-i-1);
        viewholder.mtvCostMoney.setText(bean.costMoney);
        viewholder.mtvCostTitle.setText(bean.costTitle);
        viewholder.mtvCostDate.setText(bean.costData);
        return view;
    }


    public static class viewHolder{
       public TextView mtvCostTitle;
        public TextView mtvCostDate;
        public TextView mtvCostMoney;
    }
}
