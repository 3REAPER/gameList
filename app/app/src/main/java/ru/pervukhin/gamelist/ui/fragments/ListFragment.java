package ru.pervukhin.gamelist.ui.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.pervukhin.gamelist.R;
import ru.pervukhin.gamelist.adapter.RecycleListAdapter;
import ru.pervukhin.gamelist.db.DataManager;
import ru.pervukhin.gamelist.domain.Game;
import ru.pervukhin.gamelist.rest.VolleyImpl;
import ru.pervukhin.gamelist.ui.BaseActivity;

public class ListFragment extends Fragment {

    private RecyclerView recycler;
    private RecycleListAdapter adapter;
    private VolleyImpl api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TextView label = ((BaseActivity)getActivity()).getLabel();
        if (label != null) {
            ((BaseActivity) getActivity()).getLabel().setText(R.string.game_list);
        }

        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        recycler = view.findViewById(R.id.games);
        adapter = new RecycleListAdapter(null);
        adapter.setContext(getContext());
        recycler.setAdapter(adapter);

        if (adapter.getItemCount() == 0) {
            showData();
        }

        if (hasConnection()) {
            api = new VolleyImpl(getContext(), new WeakReference<>(ListFragment.this));
            api.fillGame();
        }else {
            Toast.makeText(getContext(),"Включите интернет для обновления списка игр",Toast.LENGTH_LONG).show();

        }

        ItemTouchHelper.SimpleCallback itemCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    int position = viewHolder.getAdapterPosition();
                    Game game = adapter.getGameByPosition(position);
                    Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
                    api.deleteGame(game.getId());

                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemCallBack);
        itemTouchHelper.attachToRecyclerView(recycler);

        return view;
    }
    public void clearAdapter(){
        adapter.clear();
    }

    public boolean hasConnection()
    {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void showData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Game> gameList = new DataManager().getAllGame();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < gameList.size(); i++) {
                            adapter.addItem(gameList.get(i));
                        }
                    }
                });
            }
        });
        thread.start();
    }

    public void onGetJson(Game game){
        Log.d("dataBase","game show: " +game.getName() );
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                adapter.addItem(game);
            }
        });
    }
}
