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
 * Created by Administrator on 2017/3/11 0011.
 */

public class AdapterGridViewPartNumber extends BaseAdapter {

    private Context context;
    private List<String> ids;
    private List<String> names;
    private Map<String, String> numbers;

    public AdapterGridViewPartNumber(Context context, List<String> ids, List<String> names, Map<String, String> numbers) {
        this.context = context;
        this.ids = ids;
        this.names = names;
        this.numbers = numbers;
    }

    @Override
    public int getCount() {
        return ids.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview_partnumber, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(names.get(position));
        viewHolder.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e(ids.get(position));
                numbers.remove(ids.get(position));
                ids.remove(position);
                names.remove(position);
                notifyDataSetChanged();
            }
        });
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
                numbers.put(ids.get(position), finalViewHolder.tvNumber.getNumber());
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.number)
        NumberWidget tvNumber;
        @BindView(R.id.iv_close)
        ImageView ivClose;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
