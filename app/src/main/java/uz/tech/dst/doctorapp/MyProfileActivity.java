package uz.tech.dst.doctorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by Fz_Designs on 3/27/2017.
 */

public class MyProfileActivity extends AppCompatActivity {
    TextView firstName, lastName, gender;
    SharedPreferences sPref;
    Button btn_Edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        gender = (TextView) findViewById(R.id.gender);
        btn_Edit= (Button)findViewById(R.id.btn_Edit);
        sPref = getSharedPreferences("myPrefs",MODE_PRIVATE);

        firstName.setText(sPref.getString("fName",null));
        lastName.setText(sPref.getString("lName",null));
        gender.setText(sPref.getString("gender",null));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");
        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent()
                CustomEditDialog cdd = new CustomEditDialog(MyProfileActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
