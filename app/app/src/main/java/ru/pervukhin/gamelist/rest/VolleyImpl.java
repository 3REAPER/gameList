package ru.pervukhin.gamelist.rest;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import ru.pervukhin.gamelist.App;
import ru.pervukhin.gamelist.db.DataManager;
import ru.pervukhin.gamelist.db.rating.RatingTable;
import ru.pervukhin.gamelist.db.rating.SendId;
import ru.pervukhin.gamelist.ui.fragments.ListFragment;
import ru.pervukhin.gamelist.db.AppDataBase;
import ru.pervukhin.gamelist.domain.Author;
import ru.pervukhin.gamelist.domain.Game;
import ru.pervukhin.gamelist.domain.Genre;
import ru.pervukhin.gamelist.domain.mapper.AuthorMapper;
import ru.pervukhin.gamelist.domain.mapper.GameMapper;
import ru.pervukhin.gamelist.domain.mapper.GenreMapper;

public class VolleyImpl implements GameAPI {

    public static final String URL = "http://192.168.0.177:8082"; //write here url
    private Context context;
    private Response.ErrorListener errorListener;
    private WeakReference<ListFragment> fragment;

    public VolleyImpl(Context context,WeakReference<ListFragment> fragment) {
        this.context = context;
        this.fragment = fragment;
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }

    @Override
    public void fillGame() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String uri = URL +"/game";

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (fragment != null) {
                            fragment.get().clearAdapter();
                        }
                        new DataManager().deleteAllGame();
                        fillAuthor();
                        fillGenre();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Genre genre = new GenreMapper().jsonToGenre(jsonObject.getJSONObject("genreDto"));
                                Author author = new AuthorMapper().jsonToAuthor(jsonObject.getJSONObject("authorDto"));
                                Game game = new GameMapper().jsonToGame(jsonObject, author, genre);
                                new InsertAsync(fragment).execute(game);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(arrayRequest);
    }

    @Override
    public void setRating(int id, int rating) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String uri = URL + "/game/" +id +"/" + rating;

        StringRequest postRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context,"Оценка выставлена",Toast.LENGTH_SHORT).show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DataManager dataManager = new DataManager();
                                RatingTable ratingTable = dataManager.findRatingByGameId(id);
                                ratingTable.setSendId(SendId.SEND);
                                dataManager.updateRating(ratingTable);
                            }
                        }).start();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DataManager dataManager = new DataManager();
                                RatingTable ratingTable = dataManager.findRatingByGameId(id);
                                ratingTable.setSendId(SendId.DONT_SEND);
                                dataManager.updateRating(ratingTable);
                            }
                        }).start();
                    }
                });
        queue.add(postRequest);
    }

    @Override
    public void createGame(final Game game) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String uri = URL + "/game";

        StringRequest postRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // выгрузка заново плохо?
                        fillGame();
                    }
                },

                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nameGame", game.getName());
                params.put("description", game.getDescription());
                params.put("rating", String.valueOf(game.getRating()));
                params.put("nameAuthor", game.getAuthor().getName());
                params.put("nameGenre", game.getGenre().getName());

                return params;
            }
        };

        queue.add(postRequest);

    }

    @Override
    public void updateGame(final Game game) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URL + "/game/" + game.getId() + "/";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //стоит обновлять локально
                        //но пока так
                        fillGame();
                    }
                },

                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("nameGame", game.getName());
                params.put("description", game.getDescription());
                params.put("nameAuthor", game.getAuthor().getName());
                params.put("nameGenre", game.getGenre().getName());
                params.put("id", String.valueOf(game.getId()));

                return params;
            }
        };

        queue.add(postRequest);
    }

    @Override
    public void deleteGame(final int id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URL + "/game/" + id;

        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        fillGame();
                    }
                },

                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };

        queue.add(dr);

    }

    @Override
    public void fillGenre() {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        String uri = URL +"/genre";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Genre genre = new GenreMapper().jsonToGenre(jsonObject);
                                new DataManager().insertGenre(genre);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                errorListener
        );
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void fillAuthor() {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        String uri = URL +"/author";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Author author = new AuthorMapper().jsonToAuthor(jsonObject);
                                new DataManager().insertAuthor(author);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                errorListener
        );
        requestQueue.add(jsonArrayRequest);

    }

    public class InsertAsync extends android.os.AsyncTask<Game,Void,Void> {
        private AppDataBase db;

        public InsertAsync(WeakReference<ListFragment> fragment) {
            db = App.getInstance().getDatabase();

        }



        @Override
        protected Void doInBackground(Game... games) {
            try {

                db.gameDao().insert(new GameMapper().gameToTable(games[0]));
                if (db.authorDao().getById(games[0].getAuthor().getId()) == null) {
                    db.authorDao().insert(new AuthorMapper().AuthorToTable(games[0].getAuthor()));
                }
                if (db.genreDao().getById(games[0].getGenre().getId()) == null) {
                    db.genreDao().insert(new GenreMapper().genreToTable(games[0].getGenre()));
                }
                if (db.ratingDao().findByIdGame(games[0].getId()) == null ){
                    db.ratingDao().insert(new RatingTable(games[0].getId(), games[0].getName(), SendId.DONT_SEND,0));
                    Log.d("dataBase","data rating create");
                }
                Log.d("dataBase","data was create");
                fragment.get().onGetJson(games[0]);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("dataBase","data creating error");
            }

            return null;
        }
    }

}
