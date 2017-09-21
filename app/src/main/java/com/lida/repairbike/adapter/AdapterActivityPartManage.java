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
 * 配件管理
 * Created by Administrator on 2017/3/11 0011.
 */

public class AdapterActivityPartManage extends BaseAdapter {

    private Context context;
    private List<String> names;
    private List<String> numbers;

    public AdapterActivityPartManage(Context context, List<String> names, List<String> numbers) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview_partmanege, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(names.get(position));
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.tvNumber.setOnNumberChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                numbers.set(position, finalViewHolder.tvNumber.getNumber());
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.number)
        NumberWidget tvNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
