package com.lida.repairbike.widget.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.lida.repairbike.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 */

public class BasePopupWindow extends PopupWindow {

    private Context context;
    private ListView listView;
    private List<String> data=new ArrayList<>();
    private Adapter adapter;
    private OnItemClickListener listener;


    public BasePopupWindow(Context context,View contentView, int width, int height) {
        super(contentView, width, height);
        this.context = context;
        listView = (ListView) contentView.findViewById(R.id.listView);
        adapter = new Adapter();
        listView.setAdapter(new Adapter());
    }

    public void setData(List<String> str){
        data = str;
        adapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }



    class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_spinner,null);
            tv.setText(data.get(position));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.doNext(position,tv.getText().toString().trim());
                }
            });
            return tv;
        }
    }
}
