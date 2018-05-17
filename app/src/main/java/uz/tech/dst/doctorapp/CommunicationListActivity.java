package uz.tech.dst.doctorapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import uz.tech.dst.doctorapp.adapters.CommunicationListAdapter;
import uz.tech.dst.doctorapp.models.Clinic;
import uz.tech.dst.doctorapp.models.Doctor;
import uz.tech.dst.doctorapp.models.LocalDb;

/**
 * Created by Fz_Designs on 3/9/2017.
 */

public class CommunicationListActivity extends AppCompatActivity {

    ArrayList<Doctor> docsData;
    Context context = this;
    RecyclerView commRecView;
    CommunicationListAdapter clAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Doctors List");
        commRecView = (RecyclerView)findViewById(R.id.commRecView);

        if(getIntent().getSerializableExtra("obj")!=null){
            docsData = ((Clinic)getIntent().getSerializableExtra("obj")).doctors;
        } else if(getIntent().getStringExtra("result") != null) {

        }else {
            docsData = new ArrayList<>();

            docsData.add(0,new Doctor("Anvar", "Usmanov","ophthalmologist", "\"Red health\" clinic", true, 47));
            docsData.add(new Doctor("Sabina", "Shukurova","otolaryngologist", "\"Red health\" clinic", false, 32));
            docsData.add(new Doctor("Said", "Sattorov","dentist", "\"Red health\" clinic", false, 32));
            docsData.add(0,new Doctor("Madina", "Alieva","cardiologist", "\"Red health\" clinic", true,35));
            docsData.add(new Doctor("Andrey", "Pak","cardiologist", "\"Oxygen\" medical clinic", false,38));
            docsData.add(new Doctor("Zebo", "Maksudova","gastrologist", "\"Oxygen\" medical clinic", false,36));
            docsData.add(0,new Doctor("Ekaterina", "Volkova","dermotologist", "\"Oxygen\" medical clinic", true,34));
            docsData.add(new Doctor("Nuriddin", "Azimov","general medicine", "\"Oxygen\" medical clinic", false,32));
            docsData.add(0,new Doctor("Sunnat", "Isaev","urologist", "\"NurMed\" clinic", true,53));
            docsData.add(0,new Doctor("Shakhnoza", "Talipova","gynaecologist", "\"NurMed\" clinic", true,36));
            docsData.add(new Doctor("Sevara", "Usmanova","neonatologist", "\"NurMed\" clinic", false,34));
            docsData.add(new Doctor("Umar", "Soatov","neurologist", "\"NurMed\" clinic", false,41));
            docsData.add(0,new Doctor("Sattor", "Isakov","general", "\"Salom Med\" clinic", true,30));
            docsData.add(new Doctor("Ismat", "Mahmudov","psychologist", "\"Salom Med\" clinic", false,43));
            docsData.add(0,new Doctor("Natalya", "Sabyanina","ophthalmologist", "\"Salom Med\" clinic", true,29));
            docsData.add(new Doctor("Ozoda", "Abdullaeva","cardiologist", "\"Salom Med\" clinic", false,36));
        }
        clAdapter = new CommunicationListAdapter(context, docsData);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        commRecView.setAdapter(clAdapter);
        commRecView.setLayoutManager(mLayoutManager);


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
                CommunicationListAdapter searchAdapter;
                if(!query.isEmpty()){
                    query = query.toLowerCase();
                    ArrayList<Doctor> searchedList = new ArrayList<>();
                    for(int i = 0;i<docsData.size();i++){
                        Doctor doc = docsData.get(i);
                        if(doc.lastName.toLowerCase().contains(query)||
                                doc.firstName.toLowerCase().contains(query)||
                                doc.specialization.toLowerCase().contains(query)){
                            searchedList.add(doc);
                        }
                    }
                    searchAdapter = new CommunicationListAdapter(context,searchedList);
                    commRecView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
                }else{
                    searchAdapter = new CommunicationListAdapter(context,docsData);
                    commRecView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();

                }
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onResume() {
        Log.v("---------------","-------------------");
        if(LocalDb.instance.invitedDoctor != null){
            Log.v("-------IF--------","-------------------" + LocalDb.instance.invitedDoctor);
            docsData.remove((int)LocalDb.instance.invitedDoctor);
            clAdapter.notifyDataSetChanged();
            LocalDb.instance.invitedDoctor = null;
        }
        super.onResume();
    }
}
