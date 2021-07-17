package com.example.datascomemorativas;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DateAdapter extends BaseAdapter {
    private Activity activity;
    private List<String> list;

    public DateAdapter(Activity activity, List<String> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = activity.getLayoutInflater().inflate(R.layout.item_listview_dates,viewGroup,false);
        TextView textViewNome = v.findViewById(R.id.textViewName);
        textViewNome.setText(list.get(i));
        return v;
    }
}
