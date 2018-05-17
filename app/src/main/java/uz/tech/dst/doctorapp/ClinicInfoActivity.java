package uz.tech.dst.doctorapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import uz.tech.dst.doctorapp.models.Clinic;


/**
 * Created by Fz_Designs on 3/26/2017.
 */

public class ClinicInfoActivity extends AppCompatActivity {

    TextView clinicName, clinicLocation, clinicInfo;
    Button doctorsListBtn, reviewsBtn;
    Context context = this;
    Intent gettedIntent;
    ImageView clinicLogo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Clinic Info");

        clinicLogo = (ImageView)findViewById(R.id.clinicLogo);

        doctorsListBtn = (Button) findViewById(R.id.doctorsListBtn);
        reviewsBtn = (Button) findViewById(R.id.reviewsBtn);

        clinicName = (TextView) findViewById(R.id.clinicName);
        clinicLocation = (TextView) findViewById(R.id.clinicLocation);
        clinicInfo = (TextView) findViewById(R.id.clinicInfo);

        gettedIntent = getIntent();

        clinicName.setText(((Clinic) gettedIntent.getSerializableExtra("obj")).name);
        clinicLocation.setText(((Clinic)gettedIntent.getSerializableExtra("obj")).location);
        clinicInfo.setText(((Clinic)gettedIntent.getSerializableExtra("obj")).info);

//        clinicLogo.setImageDrawable(getResources().getDrawable(((Clinic)gettedIntent.getSerializableExtra("obj")).imgId,null));

        doctorsListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommunicationListActivity.class);
                intent.putExtra("obj",gettedIntent.getSerializableExtra("obj"));
                startActivity(intent);
            }
        });

    reviewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Coming soon...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
