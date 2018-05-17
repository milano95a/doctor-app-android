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

import uz.tech.dst.doctorapp.adapters.SearchClinicsAdapter;
import uz.tech.dst.doctorapp.models.Clinic;
import uz.tech.dst.doctorapp.models.Doctor;
import uz.tech.dst.doctorapp.models.LocalDb;

/**
 * Created by Fz_Designs on 3/14/2017.
 */

public class SearchClinicsActivity extends AppCompatActivity {

    RecyclerView clinicsRecycler;
    SearchClinicsAdapter scAdapter;
    ArrayList<Clinic> clinicData;
    Context context = this;
    ArrayList<Doctor> doctors_0, doctors_1, doctors_2, doctors_3;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_clinics);
//        doctors_0 = new ArrayList<>();
//        doctors_0.add(0, new Doctor("Anvar", "Usmanov", "ophthalmologist", "\"Red health\" clinic", true, 47));
//        doctors_0.add(new Doctor("Sabina", "Shukurova", "otolaryngologist", "\"Red health\" clinic", false, 32));
//        doctors_0.add(new Doctor("Said", "Sattorov", "dentist", "\"Red health\" clinic", false, 32));
//        doctors_0.add(0, new Doctor("Madina", "Alieva", "cardiologist", "\"Red health\" clinic", true, 35));
//
//        doctors_1 = new ArrayList<>();
//        doctors_1.add(new Doctor("Andrey", "Pak", "cardiologist", "\"Oxygen\" medical clinic", false, 38));
//        doctors_1.add(new Doctor("Zebo", "Maksudova", "gastrologist", "\"Oxygen\" medical clinic", false, 36));
//        doctors_1.add(0, new Doctor("Ekaterina", "Volkova", "dermotologist", "\"Oxygen\" medical clinic", true, 34));
//        doctors_1.add(new Doctor("Nuriddin", "Azimov", "general medicine", "\"Oxygen\" medical clinic", false, 32));
//
//        doctors_2 = new ArrayList<>();
//        doctors_2.add(0, new Doctor("Sunnat", "Isaev", "urologist", "\"NurMed\" clinic", true, 53));
//        doctors_2.add(0, new Doctor("Shakhnoza", "Talipova", "gynaecologist", "\"NurMed\" clinic", true, 36));
//        doctors_2.add(new Doctor("Sevara", "Usmanova", "neonatologist", "\"NurMed\" clinic", false, 34));
//        doctors_2.add(new Doctor("Umar", "Soatov", "neurologist", "\"NurMed\" clinic", false, 41));
//
//        doctors_3 = new ArrayList<>();
//        doctors_3.add(0, new Doctor("Sattor", "Isakov", "general", "\"Salom Med\" clinic", true, 30));
//        doctors_3.add(new Doctor("Ismat", "Mahmudov", "psychologist", "\"Salom Med\" clinic", false, 43));
//        doctors_3.add(0, new Doctor("Natalya", "Sabyanina", "ophthalmologist", "\"Salom Med\" clinic", true, 29));
//        doctors_3.add(new Doctor("Ozoda", "Abdullaeva", "cardiologist", "\"Salom Med\" clinic", false, 36));

        doctors_0 = LocalDb.instance.getDoctorsByClinic("Red Health");
        doctors_1 = LocalDb.instance.getDoctorsByClinic("Oxygen medical");
        doctors_2 = LocalDb.instance.getDoctorsByClinic("Nurmed");
        doctors_3 = LocalDb.instance.getDoctorsByClinic("Salom Med");

        clinicData = new ArrayList<>();
        clinicData.add(new Clinic("\"Red Health\" clinic", "Chief_0",
                "(Ophthalmology, Otolaryngology, Dentistry, General medicine)",
                "Узбекистан, 100015, ТАШКЕНТ, МИРЗО-УЛУГБЕКСКИЙ р-н, ул. БУЮК ИПАК ЙУЛИ, 105 офис 2.",
                doctors_0, "Info_0", R.drawable.clinic_img_2));

        clinicData.add(new Clinic("\"Oxygen medical\" clinic", "Chief_1",
                "(Cardiology, Gastronomy, dermotology, general medicine)",
                "Адрес: Узбекистан, ТАШКЕНТ, ЧИЛАНЗАРСКИЙ р-н, ул. КИЧИК ХАЛКА ЙУЛИ, 16/44.",
                doctors_1, "Info_1", R.drawable.clinic_img_1));

        clinicData.add(new Clinic("\"Nurmed\" clinic", "Chief_2",
                "(Urology, Gynaecology, Neonatology, Neurology)",
                "Адрес: Узбекистан, ТАШКЕНТ, ЧИЛАНЗАРСКИЙ р-н, м-в ЧИЛАНЗАР-7, ул. КАТАРТАЛ, 10/1.",
                doctors_2, "Info_2", R.drawable.clinic_img_3));

        clinicData.add(new Clinic("\"Salom Med\" clinic", "Chief_3",
                "(psycology, ophtalmology, Cardiology, Family medicine)",
                "Адрес: Узбекистан, ТАШКЕНТ, ЯККАСАРАЙСКИЙ р-н, ул. ШОТА РУСТАВЕЛИ, 13.",
                doctors_3, "Info_3", R.drawable.clinic_img_4));


        scAdapter = new SearchClinicsAdapter(context, clinicData);

        clinicsRecycler = (RecyclerView) findViewById(R.id.clinicsRecycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        clinicsRecycler.setAdapter(scAdapter);
        clinicsRecycler.setLayoutManager(mLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Clinics");
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
                SearchClinicsAdapter searchAdapter;
                if(!query.isEmpty()){
                    query = query.toLowerCase();
                    ArrayList<Clinic> searchedList = new ArrayList<>();
                    for(int i = 0;i<clinicData.size();i++){
                        Clinic clinic = clinicData.get(i);
                        if(clinic.name.toLowerCase().contains(query)||
                                clinic.location.toLowerCase().contains(query)||
                                clinic.workSphere.toLowerCase().contains(query)){
                            searchedList.add(clinic);
                        }
                    }
                    searchAdapter = new SearchClinicsAdapter(context,searchedList);
                    clinicsRecycler.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
                }else{
                    searchAdapter = new SearchClinicsAdapter(context,clinicData);
                    clinicsRecycler.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();

                }
                return false;
            }
        });

        return true;
    }
}
