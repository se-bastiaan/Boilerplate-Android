package com.digitalisma.boilerplate.ui.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class DeveloperSettingsSpinnerAdapter<T extends DeveloperSettingsSpinnerAdapter.SelectionOption> extends BaseAdapter {

    @NonNull
    private List<? extends T> selectionOptions = emptyList();

    @NonNull
    private final LayoutInflater layoutInflater;

    public DeveloperSettingsSpinnerAdapter(@NonNull LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public DeveloperSettingsSpinnerAdapter<T> setSelectionOptions(@NonNull List<? extends T> selectionOptions) {
        this.selectionOptions = unmodifiableList(selectionOptions);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public int getCount() {
        return selectionOptions.size();
    }

    @Override
    @NonNull
    public T getItem(int position) {
        return selectionOptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setBackgroundColor(Color.TRANSPARENT);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindItem(selectionOptions.get(position));
        return convertView;
    }

    @Override
    @NonNull
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindItem(selectionOptions.get(position));
        return convertView;
    }

    static class ViewHolder {
        @NonNull
        private final TextView titleTextView;

        ViewHolder(@NonNull View itemView) {
            titleTextView = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setBackgroundColor(Color.BLACK);
            titleTextView.setTextColor(Color.WHITE);
        }

        public void bindItem(@NonNull SelectionOption selectionOption) {
            titleTextView.setText(selectionOption.title());
        }
    }

    public interface SelectionOption {
        @NonNull
        String title();
    }

}