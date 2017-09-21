package com.lida.repairbike.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/17 0017.
 */

public class AdapterGridViewReason extends BaseAdapter {

    private Context context;
    private List<String> str;
    private Map<Integer,Boolean> state=new HashMap<>();

    public AdapterGridViewReason(Context context, List<String> str, Map<Integer, Boolean> state) {
        this.context = context;
        this.str = str;
        this.state = state;
    }

    public AdapterGridViewReason(Context context, List<String> str) {
        this.context = context;
        this.str = str;
        for (int i = 0; i < str.size(); i++) {
            state.put(i,false);
        }
    }

    public AdapterGridViewReason(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return str.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(str.get(position));
        if(state.size()>0){
            if(state.get(position)){
                viewHolder.tv.setBackgroundResource(R.drawable.bg_gridview);
            }else{
                viewHolder.tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!state.get(position)){
                    state.put(position,true);
                }else{
                    state.put(position,false);
                }
//                LogUtils.e(state.get(position));
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
