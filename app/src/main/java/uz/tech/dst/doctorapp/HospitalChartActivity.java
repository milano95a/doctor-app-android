package uz.tech.dst.doctorapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uz.tech.dst.doctorapp.adapters.HospitalChartAdapter;
import uz.tech.dst.doctorapp.models.Card;
import uz.tech.dst.doctorapp.models.LocalDb;
import uz.tech.dst.doctorapp.models.MD;
import uz.tech.dst.doctorapp.models.Note;

public class HospitalChartActivity extends AppCompatActivity implements View.OnClickListener {

    HospitalChartAdapter mAdapter;
    ArrayList<Note> data;
    Context context = this;
    RecyclerView rec;
    FloatingActionButton fab;

    List<MD> doctors;
    public String CARD_ID;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_chart);

        mDb = FirebaseDatabase.getInstance().getReference();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        sharePanel = (LinearLayout) findViewById(R.id.sharePanel);
        share = (Button) findViewById(R.id.share);
        shareCancel = (Button) findViewById(R.id.shareCancel);
        spinnerShareTo = (Spinner) findViewById(R.id.shareTo);

        btnAll = (Button) findViewById(R.id.btnAll);
        btnGeneral = (Button) findViewById(R.id.btnGeneral);
        btnPsychology = (Button) findViewById(R.id.btnPsychology);
        btnOphthalmology = (Button) findViewById(R.id.btnOphthalmology);
        btnCardiology = (Button) findViewById(R.id.btnCardiology);

        btnAll.setOnClickListener(this);
        btnGeneral.setOnClickListener(this);
        btnPsychology.setOnClickListener(this);
        btnOphthalmology.setOnClickListener(this);
        btnCardiology.setOnClickListener(this);

        rec = (RecyclerView) findViewById(R.id.rec);

        data = new ArrayList<>();
        mAdapter = new HospitalChartAdapter(context, data);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        rec.setAdapter(mAdapter);
        rec.setLayoutManager(mLayoutManager);

        setCardItems("all");

        fab.setOnClickListener(this);
        shareCancel.setOnClickListener(this);
        share.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hospital Chart");

    }
    LinearLayout sharePanel;
    Button share, shareCancel, btnAll, btnGeneral, btnPsychology, btnOphthalmology, btnCardiology;
    Spinner spinnerShareTo;

    DatabaseReference mDb;

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
                if (sharePanel.getVisibility() == View.VISIBLE) {
                    mAdapter.dontClickItemIfCheckBoxesIsVisible = true;
                    sharePanel.setVisibility(View.GONE);
                    fab.animate().translationY(-16).setDuration(200);
                    mAdapter.boxVisible = false;
                    mAdapter.notifyDataSetChanged();
                }
                HospitalChartAdapter searchAdapter;
                if (!query.isEmpty()) {
                    query = query.toLowerCase();
                    ArrayList<Note> searchedList = new ArrayList<Note>();
                    for (int i = 0; i < data.size(); i++) {
                        Note note = data.get(i);
                        if (note.speciality.toLowerCase().contains(query) ||
                                note.noteName.toLowerCase().contains(query) ||
                                note.noteType.toLowerCase().contains(query) ||
                                note.docName.toLowerCase().contains(query)) {
                            searchedList.add(note);
                        }
                    }
                    searchAdapter = new HospitalChartAdapter(context, searchedList);
                    rec.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();

                } else {
                    searchAdapter = new HospitalChartAdapter(context, data);
                    rec.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        return true;
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAll:
                setCardItems("all");
                cancelShare();
                break;
            case R.id.btnGeneral:
                setCardItems("General");
                cancelShare();
                break;
            case R.id.btnPsychology:
                setCardItems("Psychology");
                cancelShare();
                break;
            case R.id.btnCardiology:
                setCardItems("Cardiology");
                cancelShare();
                break;
            case R.id.btnOphthalmology:
                setCardItems("Ophthalmology");
                cancelShare();
                break;
            case R.id.fab:
                fab.animate().translationY(200).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        sharePanel.setVisibility(View.VISIBLE);

                        doctors = LocalDb.instance.getMyDoctors();
                        ArrayList<String> doctorsList = new ArrayList<>();

                        for(MD md:doctors){
                            doctorsList.add(md.firstname + " " + md.lastname + " " + md.speciality);
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(HospitalChartActivity.this,R.layout.item_spinner_share,doctorsList);
                        spinnerShareTo.setAdapter(arrayAdapter);

                        mAdapter.boxVisible = true;
                        mAdapter.dontClickItemIfCheckBoxesIsVisible = false;
                        mAdapter.notifyDataSetChanged();
                    }
                });
                break;
            case R.id.share:
                mAdapter.dontClickItemIfCheckBoxesIsVisible = true;
                for (int i = 0; i < mAdapter.getItemCount(); i++) {
                    if (mAdapter.data.get(i).check) {
//                        mAdapter.data.get(i).shareTime = "30 min";
//                        mAdapter.data.get(i).sharedTo = spinnerShareTo.getSelectedItem().toString();

                        Log.v("Share card ---___---__",data.get(i).doctors);
                        Log.v("Share to",doctors.get(spinnerShareTo.getSelectedItemPosition()).email);
                        share(data.get(i),doctors.get(spinnerShareTo.getSelectedItemPosition()).email);
                    }
                }
                for (int i = 0; i < mAdapter.getItemCount(); i++) {
                    mAdapter.data.get(i).check = false;
                }

                sharePanel.setVisibility(View.GONE);
                fab.animate().translationY(-16).setDuration(200);
                mAdapter.boxVisible = false;
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.shareCancel:
                cancelShare();
                break;
        }
    }

    public void setCardItems(String typeArg){

        final String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        if(typeArg.equals("all")){
            data.clear();
            mAdapter.notifyDataSetChanged();

            mDb.child("cards").orderByChild("patient").equalTo(userEmail).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    CARD_ID = dataSnapshot.getKey();
                    mDb.child("cards").child(dataSnapshot.getKey()).orderByChild("time").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            try {

                                Log.v("-------Card item-------",dataSnapshot.getValue().toString());

                                Card card = null;
                                try{
                                    card = dataSnapshot.getValue(Card.class);
                                }catch (Exception e){
                                    Log.v("_-_-_-_-_-_-_-","Not convertable" + e.getMessage());
//                                    e.printStackTrace();
                                }

                                if(card ==  null){return;}

                                long dateInLong = card.time;
                                Date date = new Date(dateInLong);

                                SimpleDateFormat parser = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
                                Date parsedDate = parser.parse(date.toString());

                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy ");
                                String formattedDate = formatter.format(parsedDate);

                                Note note = new Note(card.doctor,dataSnapshot.getKey(), card.type, card.diagnosis, card.doctorName, card.type, formattedDate, "", "", false);
                                addValue(note);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            Log.v("---____----____---___","Else");

            data.clear();
//            mAdapter = new HospitalChartAdapter(context, data);
//            rec.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            final String type = typeArg;

            mDb.child("cards").orderByChild("patient").equalTo(userEmail).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    mDb.child("cards").child(dataSnapshot.getKey()).orderByChild("type").equalTo(type).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            try {
                                Log.v("___----___---____--", dataSnapshot.getValue().toString());

                                Card card = dataSnapshot.getValue(Card.class);

                                long dateInLong = card.time;
                                Date date = new Date(dateInLong);

                                SimpleDateFormat parser = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
                                Date parsedDate = parser.parse(date.toString());

                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                                String formattedDate = formatter.format(parsedDate);

                                Note note = new Note(card.type, card.diagnosis, card.doctorName, card.type, formattedDate, "", "", false);
                                addValue(note);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    public void addValue(Note note){
        data.add(note);
        Log.v("_-_-_-_-_-_-_- Size","" +data.size());
        mAdapter = new HospitalChartAdapter(context, data);
        rec.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalDb.instance.getMyDoctors();
    }

    public void cancelShare(){
        mAdapter.dontClickItemIfCheckBoxesIsVisible = true;
        sharePanel.setVisibility(View.GONE);
        fab.animate().translationY(-16).setDuration(200);
        mAdapter.boxVisible = false;
        mAdapter.notifyDataSetChanged();
    }

    public void share(Note note, String shareToEmail){
        if(!note.doctors.contains(shareToEmail)){
            note.doctors = note.doctors + "_"+shareToEmail;
            Log.v("Log:)  :) :(  :D ",note.doctors);

            mDb.child("cards").child(CARD_ID).child(note.id).child("doctor").setValue(note.doctors).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.v("Log :) :) :)", "Success");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.v("Log :( :( :(", "Failed");
                }
            });
        }
    }
}
