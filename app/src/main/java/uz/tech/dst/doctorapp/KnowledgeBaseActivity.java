package uz.tech.dst.doctorapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import uz.tech.dst.doctorapp.adapters.HospitalChartAdapter;
import uz.tech.dst.doctorapp.adapters.KnowledgeBaseAdapter;
import uz.tech.dst.doctorapp.models.Article;
import uz.tech.dst.doctorapp.models.Note;

/**
 * Created by Fz_Designs on 3/14/2017.
 */

public class KnowledgeBaseActivity extends AppCompatActivity {

    RecyclerView knowledgeRecycler;
    ArrayList<Article> data;
    Context context = this;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_base);

        knowledgeRecycler = (RecyclerView)findViewById(R.id.knowledgeRecycler);

        data = new ArrayList<>();
        data.add(new Article("Anvar Usmanov   20.12.2012","Diarrhea",
                "disease", getResources().getString(R.string.diarrhea_disease)));
        data.add(new Article("Ekaterina Volkova   12.06.2013","Deep vein thrombosis",
                "disease", getResources().getString(R.string.thrombosis)));
        data.add(new Article("Umar Soatov   03.11.2005","Thrombophlebitis", "disease",
                getResources().getString(R.string.thrombophlebitis)));
        data.add(new Article("Ozoda Abdullaeva   01.08.2010","Tachycardia", "disease",
                getResources().getString(R.string.tachycardia)));
        data.add(new Article("Ismat Mahmudov   03.02.2011","Pancreatic cysts", "disease",
                getResources().getString(R.string.pancreatic)));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);

        knowledgeRecycler.setLayoutManager(mLayoutManager);
        knowledgeRecycler.setAdapter(new KnowledgeBaseAdapter(context,data));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Knowledge Base");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        final MenuItem myActionsMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionsMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionsMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                KnowledgeBaseAdapter searchAdapter;
                if(!query.isEmpty()){
                    query = query.toLowerCase();
                    ArrayList<Article> searchedList = new ArrayList<>();
                        for(int i = 0;i<data.size();i++){
                            Article art = data.get(i);
                            if(art.name.toLowerCase().contains(query)||art.type.toLowerCase().contains(query)){
                                searchedList.add(art);
                            }
                        }
                    searchAdapter = new KnowledgeBaseAdapter(context,searchedList);
                    knowledgeRecycler.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
                }else{
                    searchAdapter = new KnowledgeBaseAdapter(context,data);
                    knowledgeRecycler.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();

                }
                return false;
            }
        });

        return true;
    }
}

