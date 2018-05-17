package uz.tech.dst.doctorapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import uz.tech.dst.doctorapp.models.LocalDb;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context = this;
    GridView gridView;
    ImageView img;
    int pos;

    String[] txt = new String[]{
            "Personal Hospital Chart",
            "Search For Clinics For Information",
            "Communication module",
            "Personal Physical Activity Monitoring",
            "Medical Knowledge Base",
    };

    int[] icon = new int[]{
            R.drawable.personal_hospital_chart,
            R.drawable.search_for_clinics_for_information,
            R.drawable.communication_module,
            R.drawable.personal_physical_activity_monitoring,
            R.drawable.medical_knowledge_base,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 5; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("txt", txt[i]);
            hm.put("icon", Integer.toString(icon[i]));
            aList.add(hm);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        String[] from = {"icon", "txt"};
        int[] to = {R.id.icon, R.id.txt};
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.item_dashboard_grid, from, to);
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                pos = position;

                img = (ImageView) gridView.getChildAt(position).findViewById(R.id.icon);
                img.animate().translationY(20).setDuration(300).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        img.animate().translationY(-20).setDuration(300);
                        if (pos == 0) {
                            startActivity(new Intent(context, HospitalChartActivity.class));
                        } else if (pos == 1) {
                            Constants.ACTIVITY_STATE = Constants.SEARCH_FOR_CLINICS;
                            startActivity(new Intent(context, SearchClinicsActivity.class));
                        } else if (pos == 2) {
                            Constants.ACTIVITY_STATE = Constants.COMMUNICATION_MODULE;
                            startActivity(new Intent(context, CommunicationListActivity.class));
                        } else if (pos == 3) {
                            startActivity(new Intent(context, PhysicalMonitoringActivity.class));
                        } else if (pos == 4) {
                            startActivity(new Intent(context, KnowledgeBaseActivity.class));
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //

        int id = item.getItemId();
        Intent intent;
        if (id == R.id.profile) {
            startActivity(new Intent(context, MyProfileActivity.class));
        } else if (id == R.id.settings) {
            startActivity(new Intent(context, SettingsActivity.class));
        } else if (id == R.id.favorite) {
            startActivity(new Intent(context,FavoriteListActivity.class));
        } else if (id == R.id.invite) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Download DoctorApp, guys!");
            intent.setType("text/plain");
            startActivity(intent);
        } else if (id == R.id.help) {
            Toast.makeText(this, "Coming soon...", Toast.LENGTH_LONG).show();
        } else if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Logout");
            builder.setMessage("Are you sure?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    FirebaseAuth.getInstance().signOut();

                    finish();
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalDb.instance.getUser();
        if(!LocalDb.DOCTOR_LISTENER){
            LocalDb.instance.retriveDoctors();
        }
        if(!LocalDb.INVITATION_LISTENER){
            LocalDb.instance.retriveInvitations();
        }
        if(!LocalDb.CONNECTION_LISTENER){
            LocalDb.instance.retriveConnections();
        }
    }
}
