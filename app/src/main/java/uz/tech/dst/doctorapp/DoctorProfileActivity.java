package uz.tech.dst.doctorapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import uz.tech.dst.doctorapp.models.Doctor;
import uz.tech.dst.doctorapp.models.Invitation;
import uz.tech.dst.doctorapp.models.LocalDb;

public class DoctorProfileActivity extends AppCompatActivity {

    TextView docFirstName, docLastName, docAge, docSpeciality, docClinic;
    Button btn_invite;
    Context context = this;

    DatabaseReference mDb;

    Doctor currentDoctor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        mDb = FirebaseDatabase.getInstance().getReference();

        docFirstName = (TextView)findViewById(R.id.docFirstName);
        docLastName = (TextView)findViewById(R.id.docLastName);
        docAge = (TextView)findViewById(R.id.docAge);
        docSpeciality= (TextView)findViewById(R.id.docSpeciality);
        docClinic= (TextView)findViewById(R.id.docClinic);
        btn_invite = (Button)findViewById(R.id.btn_invite);
        final Intent intent = getIntent();
        currentDoctor = (Doctor)intent.getSerializableExtra("docObject");

        docFirstName.setText(currentDoctor.firstName);
        docLastName.setText(currentDoctor.lastName);
        docAge.setText(currentDoctor.email);
        docSpeciality.setText(currentDoctor.specialization);
        docClinic.setText(currentDoctor.clinicName);

        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("-----firsname----",LocalDb.instance.currentUser.firsname);
                Log.v("-----doctor----",currentDoctor.email);
                Invitation invitation = new Invitation(
                        LocalDb.instance.currentUser.firsname +"  "+ LocalDb.instance.currentUser.lastname,
                        LocalDb.instance.currentUser.email,
                        currentDoctor.email,
                        currentDoctor.email + "_new");
                mDb.child("invitations/" + new Date().getTime()).setValue(invitation).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context,"Invitation has been sent", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Invitation failed", Toast.LENGTH_SHORT).show();
                    }
                });

                finish();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Doctor Profile");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        LocalDb.instance.invitedDoctor = null;
        super.onBackPressed();
    }
}
