package ru.pervukhin.gamelist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.pervukhin.gamelist.R;
import ru.pervukhin.gamelist.domain.Author;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private List<String> list;

    public CustomSpinnerAdapter(@NonNull Context context, int resource, List<String> list) {
        super(context,resource,list);
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.spinner_item, null);
        }

        ((TextView) convertView.findViewById(R.id.spinner_item))
                .setText(list.get(position));

        return convertView;
    }

}

