package ru.pervukhin.gamelist.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ru.pervukhin.gamelist.R;
import ru.pervukhin.gamelist.domain.Game;
import ru.pervukhin.gamelist.ui.BaseActivity;
import ru.pervukhin.gamelist.ui.fragments.RatingFragment;

public class RecycleRatingAdapter extends RecyclerView.Adapter<RecycleRatingAdapter.ViewHolder> {
    private List<Game> data;
    private WeakReference<RatingFragment> fragment;
    private Context context;

    public RecycleRatingAdapter(List<Game> data,WeakReference<RatingFragment> fragment) {
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
        this.fragment = fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = data.get(position);
        RatingBar ratingBar = holder.getRatingBar();
        float rating = game.getRating();
        ratingBar.setRating(rating / 2);
        holder.getName().setText(game.getName());
        holder.getSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (context != null){
                   float result = ratingBar.getRating() * 2;
                   fragment.get().onRatingSet(context,game.getId(),Math.round(result));
                   data.remove(position);
                   notifyDataSetChanged();
               }
            }
        });

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
        private final Button send;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            send = itemView.findViewById(R.id.send);
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }

        public TextView getName() {
            return name;
        }

        public Button getSend() {
            return send;
        }
    }
}