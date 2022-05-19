package ru.pervukhin.gamelist.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.pervukhin.gamelist.R;
import ru.pervukhin.gamelist.domain.Game;
import ru.pervukhin.gamelist.ui.BaseActivity;

public class RecycleListAdapter extends RecyclerView.Adapter<RecycleListAdapter.ViewHolder> {
    private List<Game> localDataSet;
    private BaseActivity activity;

    public RecycleListAdapter(List<Game> localDataSet) {
        if (localDataSet == null) {
            this.localDataSet = new ArrayList<>();
        }else {
            this.localDataSet = localDataSet;
        }
    }

    public void addItem(Game game){
        localDataSet.add(game);
        notifyDataSetChanged();

    }

    public void clear(){
        localDataSet = new ArrayList<>();
        notifyDataSetChanged();

    }

    public Game getGameByPosition(int position){
        return localDataSet.get(position);
    }

    public void setContext(Context context){
        this.activity = (BaseActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = localDataSet.get(position);
        String id =String.valueOf(game.getId());
        String nameValue = game.getName();
        String descriptionValue = game.getDescription();
        String authorValue = game.getAuthor().getName();
        String genreValue = game.getGenre().getName();
        holder.getName().setText(nameValue);
        holder.getDescription().setText(descriptionValue);
        float rating = game.getRating();
        holder.getRatingBar().setRating(rating / 2);
        holder.getAuthor().setText(authorValue);
        holder.getGenre().setText(genreValue);
        holder.getEdit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    bundle.putString("name", nameValue);
                    bundle.putString("description", descriptionValue);
                    bundle.putString("author", authorValue);
                    bundle.putString("genre", genreValue);
                    activity.navigateToEditFragment(bundle);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (localDataSet == null){
            return 0;
        }
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView description;
        private final TextView author;
        private final TextView genre;
        private final RatingBar ratingBar;
        private final Button edit;

        public TextView getName() {
            return name;
        }

        public TextView getDescription() {
            return description;
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }

        public TextView getAuthor() {
            return author;
        }

        public TextView getGenre() {
            return genre;
        }

        public Button getEdit() {
            return edit;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            author = itemView.findViewById(R.id.author);
            genre = itemView.findViewById(R.id.genre);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            edit = itemView.findViewById(R.id.edit);

        }
    }
}
