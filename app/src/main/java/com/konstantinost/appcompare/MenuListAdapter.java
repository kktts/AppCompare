package com.konstantinost.appcompare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Konstantinos on 17/2/2016.
 */
public class MenuListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;

    public MenuListAdapter(Context context, String[] values) {
        super(context, R.layout.menu_row_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.menu_row_layout, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.label);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        textView.setText(values[position]);
        String option = values[position];

        switch (option) {
            case "Applications":
                imageView.setImageResource(R.drawable.button_lines);
                break;
            case "Results":
                imageView.setImageResource(R.drawable.button_exclamation);
                break;
            case "About":
                imageView.setImageResource(R.drawable.button_question);
                break;
        }

        return view;
    }
}

