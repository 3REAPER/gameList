package ru.pervukhin.gamelist.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.NavigatorProvider;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import ru.pervukhin.gamelist.R;

public class BaseActivity extends AppCompatActivity {

    private NavController nav;
    private TextView label;
    private int idItemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_base);
        label = findViewById(R.id.label);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bar);
        nav = Navigation.findNavController(this,R.id.nav_host_fragment);
        idItemSelected = bottomNavigationView.getSelectedItemId();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int idItemWillSelect = item.getItemId();

                if (idItemSelected == idItemWillSelect ){
                    return false;
                }else {
                    switch (idItemWillSelect) {
                        case R.id.item_list:
                            nav.navigate(R.id.nav_list);
                            idItemSelected = R.id.item_list;
                            break;
                        case R.id.item_add:
                            nav.navigate(R.id.nav_create);
                            idItemSelected = R.id.item_add;
                            break;
                        case R.id.item_rating:
                            nav.navigate(R.id.nav_rating);
                            idItemSelected = R.id.item_rating;
                            break;
                    }
                    return true;
                }
            }
        });

    }

    public TextView getLabel() {
        return label;
    }

    public void navigateToEditFragment(Bundle bundle){
        nav.navigate(R.id.nav_edit,bundle);
        idItemSelected = 0;
    }


}
