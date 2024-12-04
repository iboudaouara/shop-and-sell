package com.example.applicationshopandsell.adapteurs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private List<String> items;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, android.R.layout.simple_spinner_item);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());

        if (items.get(position).startsWith("--")) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            TextView textView = convertView.findViewById(android.R.id.text1);
            textView.setText(getItem(position));
            textView.setBackgroundColor(getContext().getResources().getColor(android.R.color.darker_gray));
            textView.setPadding(8, 8, 8, 8);
        } else {
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            TextView textView = convertView.findViewById(android.R.id.text1);
            textView.setText(getItem(position));
        }

        return convertView;
    }

    private View createViewFromResource(int position, @Nullable View convertView, @NonNull ViewGroup parent, int resource) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(resource, parent, false);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(getItem(position));
        return convertView;
    }
}
