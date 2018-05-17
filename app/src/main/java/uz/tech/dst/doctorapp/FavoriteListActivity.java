package uz.tech.dst.doctorapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import uz.tech.dst.doctorapp.adapters.FavoriteListAdapter;
import uz.tech.dst.doctorapp.models.Favorite;

/**
 * Created by Fz_Designs on 3/31/2017.
 */

public class FavoriteListActivity extends AppCompatActivity {

    RecyclerView favoriteRecycler;
    ArrayList<Favorite> favoriteData;
    Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Favorites");

        favoriteRecycler = (RecyclerView) findViewById(R.id.favoriteRecycler);
        favoriteData = new ArrayList<>();
        favoriteData.add(new Favorite("\"Oxygen\" medical clinic", "clinic"));
        favoriteData.add(new Favorite("\"Red health\" clinic", "clinic"));
        favoriteData.add(new Favorite("Andrey pak (38 years) - Cardiologist", "doctor"));
        favoriteData.add(new Favorite("Said Sattorov (32 year) - Dentist", "doctor"));
        favoriteData.add(new Favorite("Natalya Sabyanina (29 years) - Cardiologist", "doctor"));
        favoriteData.add(new Favorite("Ozoda Abdullaeva (36 years) - Family doctor", "doctor"));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);

        favoriteRecycler.setLayoutManager(mLayoutManager);
        favoriteRecycler.setAdapter(new FavoriteListAdapter(context, favoriteData));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
