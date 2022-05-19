package ru.pervukhin.gamelist.ui.fragments;

import android.app.GameManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ru.pervukhin.gamelist.R;
import ru.pervukhin.gamelist.adapter.RecycleListAdapter;
import ru.pervukhin.gamelist.adapter.RecycleOldRatingAdapter;
import ru.pervukhin.gamelist.adapter.RecycleRatingAdapter;
import ru.pervukhin.gamelist.db.DataManager;
import ru.pervukhin.gamelist.db.rating.RatingTable;
import ru.pervukhin.gamelist.db.rating.SendId;
import ru.pervukhin.gamelist.domain.Game;
import ru.pervukhin.gamelist.rest.GameAPI;
import ru.pervukhin.gamelist.rest.VolleyImpl;
import ru.pervukhin.gamelist.ui.BaseActivity;

public class RatingFragment extends Fragment {
    private RecycleRatingAdapter ratingAdapter;
    private RecycleOldRatingAdapter oldRatingAdapter;
    private RecyclerView recyclerRating;
    private RecyclerView recyclerOldRating;
    private TextView textOldRating;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((BaseActivity)getActivity()).getLabel().setText(R.string.rating);
        View view =  inflater.inflate(R.layout.fragment_rating, container, false);
        recyclerRating = view.findViewById(R.id.rv_rating);
        recyclerOldRating = view.findViewById(R.id.rv_your_rating);
        textOldRating = view.findViewById(R.id.text_old_rating);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RatingTable> gameList = new DataManager().getRatingWithSend();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (gameList.size() != 0){
                            textOldRating.setVisibility(View.VISIBLE);
                            oldRatingAdapter = new RecycleOldRatingAdapter(gameList);
                            recyclerOldRating.setVisibility(View.VISIBLE);
                            recyclerOldRating.setAdapter(oldRatingAdapter);
                        }else {
                            oldRatingAdapter = new RecycleOldRatingAdapter(null);
                            recyclerOldRating.setAdapter(oldRatingAdapter);
                        }
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
              List<Game> gameList = new DataManager().getGameWithRatingNoSend();
              new Handler(Looper.getMainLooper()).post(new Runnable() {
                  @Override
                  public void run() {
                      ratingAdapter = new RecycleRatingAdapter(gameList,new WeakReference<>(RatingFragment.this));
                      ratingAdapter.setContext(getContext());
                      recyclerRating.setAdapter(ratingAdapter);
                  }
              });
            }
        }).start();

        return view;
    }

    public void onRatingSet(Context context,int id,int rating){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataManager dataManager = new DataManager();
                if (dataManager.findRatingByGameId(id).getSendId() == 0) {
                    VolleyImpl volley = new VolleyImpl(context, null);
                    volley.setRating(id, rating);
                    RatingTable ratingTable = dataManager.findRatingByGameId(id);
                    ratingTable.setRating(rating);
                    ratingTable.setSendId(SendId.SERVER_WAITING);
                    dataManager.updateRating(ratingTable);
                    recyclerOldRating.setVisibility(View.VISIBLE);
                    textOldRating.setVisibility(View.VISIBLE);
                    oldRatingAdapter.addItem(ratingTable);
                }
            }
        }).start();
    }
}
