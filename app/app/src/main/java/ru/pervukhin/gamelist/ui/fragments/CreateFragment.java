package ru.pervukhin.gamelist.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import ru.pervukhin.gamelist.R;
import ru.pervukhin.gamelist.adapter.CustomSpinnerAdapter;
import ru.pervukhin.gamelist.db.DataManager;
import ru.pervukhin.gamelist.domain.Author;
import ru.pervukhin.gamelist.domain.Game;
import ru.pervukhin.gamelist.domain.Genre;
import ru.pervukhin.gamelist.rest.VolleyImpl;
import ru.pervukhin.gamelist.ui.BaseActivity;


public class CreateFragment extends Fragment {
    private TextInputEditText name;
    private TextInputEditText description;
    private AutoCompleteTextView author;
    private TextInputLayout authorLayout;
    private AutoCompleteTextView genre;
    private TextInputLayout genreLayout;
    private RatingBar ratingBar;
    private Button create;
    private VolleyImpl api;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_create, container, false);

        ((BaseActivity)getActivity()).getLabel().setText(R.string.create);
        name = view.findViewById(R.id.name_edit_text);
        description = view.findViewById(R.id.description_edit_text);
        author = view.findViewById(R.id.author_spinner);
        authorLayout = view.findViewById(R.id.author);
        genre = view.findViewById(R.id.genre_spinner);
        genreLayout = view.findViewById(R.id.genre);
        ratingBar = view.findViewById(R.id.ratingBar);
        create = view.findViewById(R.id.create);

        api = new VolleyImpl(getContext(), null);

        Thread threadAuthor = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Author> listAuthor = new DataManager().getAllAuthor();
                List<String> listString = new ArrayList<>();
                for (Author author: listAuthor) {
                    listString.add(author.getName());
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_item, listString);
                        author.setAdapter(adapter);
                    }
                });
            }
        });
        threadAuthor.start();

        Thread threadGenre = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Genre> listAuthor = new DataManager().getAllGenre();
                List<String> listString = new ArrayList<>();
                for (Genre genre: listAuthor) {
                    listString.add(genre.getName());
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_item, listString);
                        genre.setAdapter(adapter);
                    }
                });
            }
        });
        threadGenre.start();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCorrectData()){
                    Game game = new Game(
                            name.getText().toString(),
                            description.getText().toString(),
                            Math.round(ratingBar.getRating() * 2),
                            new Author(1,author.getText().toString()),
                            new Genre(1,genre.getText().toString())

                    );
                    api.createGame(game);
                }
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isCorrectName()){
                    name.setError(null);
                }
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isCorrectName()){
                    name.setError(null);
                }
            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isCorrectDescription()){
                    description.setError(null);
                }
            }
        });
        author.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isCorrectAuthor()){
                    authorLayout.setHelperText("");
                }
            }
        });
        genre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isCorrectGenre()){
                    genreLayout.setHelperText("");
                }
            }
        });
        return view;
    }


    private boolean isCorrectData(){
        boolean result = true;
        if (!isCorrectName()){
            result = false;
        }
        if (!isCorrectDescription()){
            result = false;
        }
        if (!isCorrectAuthor()){
            result = false;
        }
        if (!isCorrectGenre()){
            result = false;
        }
        return result;
    }

    private boolean isCorrectName(){
        String nameValue = name.getText().toString();
        if (nameValue.isEmpty()){
            name.setError("Заполните имя");
            return false;
        }
        return true;
    }

    private boolean isCorrectDescription(){
        String descriptionValue = description.getText().toString();
        if (descriptionValue.isEmpty()){
            description.setError("Заполните описание");
            return false;
        }
        return true;
    }

    private boolean isCorrectAuthor(){
        String authorValue = author.getText().toString();
        if (authorValue.isEmpty()){
            authorLayout.setHelperText("Выбирите студию");
            return false;
        }
        return true;
    }

    private boolean isCorrectGenre(){
        String genreValue = genre.getText().toString();
        if (genreValue.isEmpty()){
            genreLayout.setHelperText("Выбирите жанр");
            return false;
        }
        return true;
    }
}