package ru.pervukhin.gamelist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.pervukhin.gamelist.R;
import ru.pervukhin.gamelist.db.rating.RatingTable;
import ru.pervukhin.gamelist.domain.Game;
import ru.pervukhin.gamelist.ui.fragments.RatingFragment;

public class RecycleOldRatingAdapter extends RecyclerView.Adapter<RecycleOldRatingAdapter.ViewHolder>{
    private List<RatingTable> data;
    private Context context;

    public RecycleOldRatingAdapter(List<RatingTable> data) {
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
    }

    public void addItem(RatingTable ratingTable) {
        data.add(ratingTable);
        notifyDataSetChanged();

    }

    public void setData(List<RatingTable> data) {
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
        notifyDataSetChanged();

    }

    public RatingTable getGameByPosition(int position) {
        return data.get(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_old_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RatingTable ratingTable = data.get(position);
        RatingBar ratingBar = holder.getRatingBar();
        float rating = ratingTable.getRating();
        ratingBar.setRating(rating / 2);
        holder.getName().setText(ratingTable.getGameName());

    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RatingBar ratingBar;
        private final TextView name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }

        public TextView getName() {
            return name;
        }
    }
}