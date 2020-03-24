package com.example.classregistration;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter {
    private Context context;
    private List<Notice> listNotice;

    public NoticeListAdapter(Context context, List<Notice> listNotice) {
        this.context = context;
        this.listNotice = listNotice;
    }

    @Override
    public int getCount() {
        return listNotice.size();
    }

    @Override
    public Object getItem(int position) {
        return listNotice.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_notice, null);
        TextView textViewTitle = v.findViewById(R.id.textViewTitle);
        TextView textViewName = v.findViewById(R.id.textViewName);
        TextView textViewDate = v.findViewById(R.id.textViewDate);
        textViewTitle.setText(listNotice.get(position).title);
        textViewName.setText(listNotice.get(position).name);
        textViewDate.setText(listNotice.get(position).date);
        return v;
    }
}
