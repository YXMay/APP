package com.example.administrator.jzb;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ConstListAdapter extends BaseAdapter {

    private List<ConstBean> mList;
    private Context mcontext;
    private LayoutInflater mLyoutInflater;
    private DataBaseHelper mDataBaseHelper;
    private ListView mListView;
    private ImageButton mButton;

    public ConstListAdapter(Context context,List<ConstBean> mList) {
        mcontext=context;
        this.mList = mList;  //拿到数组
        mLyoutInflater=LayoutInflater.from(context);

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

   // public void delete(View.OnClickListener button){  //删除接口
    //    this.mButton = mButton;
    //}

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {


        ViewHolder holder = null;
        if (view == null) {
            view = mLyoutInflater.inflate(R.layout.list_item, null); //加载布局
            holder = new ViewHolder();

            holder.mTvConstTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.mTvConstDate = (TextView) view.findViewById(R.id.tv_date);
            holder.mTvConstMoney = (TextView) view.findViewById(R.id.tv_cost);

            holder.mTvButton = (ImageButton) view.findViewById(R.id.delete);  //添加删除
            holder.mTvButton.setTag(i);

            view.setTag(holder);

        } else { //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) view.getTag();
        }


        holder.mTvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(v.getTag().toString()); ;
                mList.remove(position);
                notifyDataSetChanged();
            }
        });


        view.setTag(holder);

        ConstBean bean = mList.get(i);
        holder.mTvConstTitle.setText(bean.constTitle);
        holder.mTvConstDate.setText(bean.constDate);
        holder.mTvConstMoney.setText(bean.constMoney);

        return view;
    }


    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView mTvConstTitle;
        TextView mTvConstDate;
        TextView mTvConstMoney;
        ImageButton mTvButton;
    }


}
