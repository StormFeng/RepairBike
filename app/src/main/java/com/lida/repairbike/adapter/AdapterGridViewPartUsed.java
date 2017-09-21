package com.lida.repairbike.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.R;
import com.lida.repairbike.widget.NumberWidget;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 配件
 * Created by Administrator on 2017/3/11 0011.
 */

public class AdapterGridViewPartUsed extends BaseAdapter {

    private Context context;
    private List<String> names;
    private List<String> numbers;

    public AdapterGridViewPartUsed(Context context, List<String> names, List<String> numbers) {
        this.context = context;
        this.names = names;
        this.numbers = numbers;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview_partused, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvPartName.setText(names.get(position));
        viewHolder.tvPartNumber.setText(numbers.get(position));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_partName)
        TextView tvPartName;
        @BindView(R.id.tv_partNumber)
        TextView tvPartNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
