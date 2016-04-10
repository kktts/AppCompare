package com.konstantinost.appcompare;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Application> {

    private ArrayList<Application> appsList = null;
    private Context context;

    public CustomAdapter(Context context, int textViewResourceId,
                         ArrayList<Application> appsList) {
        super(context, textViewResourceId, appsList);
        this.context = context;
        this.appsList = appsList;
    }

    @Override
    public int getCount() {
        return ((null != appsList) ? appsList.size() : 0);
    }

    @Override
    public Application getItem(int position) {
        return ((null != appsList) ? appsList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_adapter, null);
        }

        if (null != appsList.get(position)) {

            TextView appName = (TextView) view.findViewById(R.id.app_name);
            TextView packageName = (TextView) view.findViewById(R.id.app_paackage);
            TextView appVersion = (TextView) view.findViewById(R.id.app_version);
            ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);

            appName.setText(appsList.get(position).getName());
            packageName.setText(appsList.get(position).getPack());
            appVersion.setText(appsList.get(position).getVersion());
            iconview.setImageDrawable(appsList.get(position).getIcon());
        }
        return view;
    }
}