/*
Copyright 2015 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.midian.base.widget.pulltorefresh.listviewhelper.imp;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bishilai.thirdpackage.R;
import com.midian.base.widget.pulltorefresh.listviewhelper.ILoadViewFactory;
import com.midian.base.widget.pulltorefresh.vary.VaryViewHelper;

public class DeFaultLoadViewFactory implements ILoadViewFactory {
    @Override
    public void config(boolean isShowBtn, String tip) {
        System.out.println("config:::::::::");
        this.isShowBtn = isShowBtn;
        this.tip=tip;
        if (btn!=null){
            if(isShowBtn){
                btn.setVisibility(View.VISIBLE);
            }else{
                btn.setVisibility(View.GONE);
            }
        }
        if (tip_tv!=null){
            if(TextUtils.isEmpty(tip)){

            }else{
                tip_tv.setText(tip);
            }
        }
    }

    View btn;
    TextView tip_tv;
    boolean isShowBtn = false;
    String tip = "";
    String text = "";
    Button mbutton;
    public OnClickListener onClickRefreshListener;
    @Override
    public void BtOnClickListener(OnClickListener onClickRefreshListener, String text) {
        this.onClickRefreshListener = onClickRefreshListener;
        this.text = text;
        if (btn!=null){
            if(TextUtils.isEmpty(text)){

            }else{
                mbutton.setText(text);
            }
        }

    }



    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new LoadMoreHelper();
    }

    @Override
    public ILoadView madeLoadView() {
        return new LoadViewHelper();
    }

    private class LoadMoreHelper implements ILoadMoreView {

        protected TextView footView;

        private OnClickListener onClickRefreshListener;
        private Context context;

        @Override
        public void init(ListView listView, OnClickListener onmClickRefreshListener) {
            footView = (TextView) LayoutInflater.from(listView.getContext()).inflate(R.layout.layout_listview_foot,
                    listView, false);
            listView.addFooterView(footView);
            this.context = listView.getContext().getApplicationContext();
            this.onClickRefreshListener = onmClickRefreshListener;
            showNormal();
        }

        @Override
        public void showNormal() {
            footView.setText(context.getString(R.string.onclick_load_more));
            footView.setOnClickListener(this.onClickRefreshListener);
        }

        @Override
        public void showLoading() {
            footView.setText(context.getString(R.string.loading));
            footView.setOnClickListener(null);
        }

        @Override
        public void showFail() {
            footView.setText(context.getString(R.string.onclick_reload_more));
            footView.setOnClickListener(this.onClickRefreshListener);
        }

        @Override
        public void showNomore() {
            footView.setVisibility(View.GONE);
            footView.setText(context.getString(R.string.load_finish));
            footView.setOnClickListener(null);
        }

    }

    private class LoadViewHelper implements ILoadView {
        private VaryViewHelper helper;
//        private OnClickListener onClickRefreshListener;
        private Context context;

        @Override
        public void init(ListView mListView, OnClickListener onmClickRefreshListener) {
            helper = new VaryViewHelper(mListView);
            this.context = mListView.getContext().getApplicationContext();
           onClickRefreshListener = onmClickRefreshListener;
        }

        @Override
        public void restore() {
            helper.restoreView();
        }

        @Override
        public void showLoading() {
            View layout = helper.inflate(R.layout.load_ing);
            TextView textView = (TextView) layout.findViewById(R.id.textView1);
            textView.setText(context.getString(R.string.loading));
            helper.showLayout(layout);
        }

        @Override
        public void tipFail() {
            Toast.makeText(context, context.getString(R.string.loading_fail), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showFail() {
            View layout = helper.inflate(R.layout.load_error);
            TextView textView = (TextView) layout.findViewById(R.id.textView1);
            textView.setText(context.getString(R.string.loading_fail));
            Button button = (Button) layout.findViewById(R.id.button1);
            if (isShowBtn) {
                button.setText(context.getString(R.string.try_again));
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }
            btn=button;
            button.setOnClickListener(onClickRefreshListener);
            helper.showLayout(layout);
        }


        @Override
        public void showEmpty() {
            View layout = helper.inflate(R.layout.load_empty);
            TextView textView = (TextView) layout.findViewById(R.id.textView1);
            textView.setText(context.getString(R.string.not_data));
            tip_tv=textView;
            Button button = (Button) layout.findViewById(R.id.button1);
            if (isShowBtn) {
                if (TextUtils.isEmpty(text)) {
                    button.setText(context.getString(R.string.try_again));
                } else {
                    button.setText(text);
                }

                button.setVisibility(View.VISIBLE);
                mbutton = button;
            } else {
                button.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(tip))
                tip_tv.setText(context.getString(R.string.not_data));
            else {
                tip_tv.setText(tip);
            }
            btn=button;
            button.setOnClickListener(onClickRefreshListener);
            helper.showLayout(layout);
        }


        @Override
        public void init(View mListView, OnClickListener onmClickRefreshListener) {
            helper = new VaryViewHelper(mListView);
            this.context = mListView.getContext().getApplicationContext();
            onClickRefreshListener = onmClickRefreshListener;
        }

    }

}
