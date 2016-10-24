package com.alex.sunshineapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

/**
 * Created by Alex on 23/10/2016.
 */

public class ForecastListAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private List<String> objects;

    public ForecastListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }
        TextView tvInfo = (TextView) convertView.findViewById(R.id.list_item_forecast_textview);
        tvInfo.setText(objects.get(position));

        return convertView;
    }

    @Override
    public int getPosition(String item) {
        return objects.indexOf(item);
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return objects.get(position);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public void addAll(Collection<? extends String> collection) {
        objects.addAll(collection);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        objects.clear();
        notifyDataSetChanged();
    }
}
