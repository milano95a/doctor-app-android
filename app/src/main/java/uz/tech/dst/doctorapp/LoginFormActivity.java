package uz.tech.dst.doctorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uz.tech.dst.doctorapp.models.Patient;

public class LoginFormActivity extends AppCompatActivity {

    Context context = this;
    Button Register, SignIn, mBtnTry;
    EditText etFName, etLName, Email, etPassword, etSecondPassword;
    SharedPreferences sPref;
    Spinner genderSpinner;
    DatabaseReference mDb;
    LinearLayout mProgressBar, mNoInternetLayout;
    CoordinatorLayout mParentLayout;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        mBtnTry = (Button)findViewById(R.id.btn_try);
        mNoInternetLayout = (LinearLayout)findViewById(R.id.no_connection);
        mParentLayout = (CoordinatorLayout)findViewById(R.id.parent_layout);
        mDb = FirebaseDatabase.getInstance().getReference();
        mProgressBar = (LinearLayout)findViewById(R.id.progress_bar);
        sPref = getSharedPreferences("myPrefs",MODE_PRIVATE);
        Register = (Button)findViewById(R.id.btn_register);
        SignIn = (Button)findViewById(R.id.btn_signIn);
        genderSpinner = (Spinner)findViewById(R.id.genderSpinner);
        Email = (EditText) findViewById(R.id.et_Email);
        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etSecondPassword = (EditText) findViewById(R.id.etSecondPassword);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etFName.getText().toString().equals("") || etLName.getText().toString().equals("") || Email.getText().toString().equals("") || etPassword.getText().toString().equals("") || etSecondPassword.getText().toString().equals("")) {
                    msg("Please, fill the form!");
                    return;
                } else if (!etPassword.getText().toString().equals(etSecondPassword.getText().toString())) {
                    msg("Passwords aren\'t matching!");
                    return;
                }else if(etPassword.getText().length() < 6){
                    msg("Passwords must be at least 6 character length");
                    return;
                } else if(checkEmail(Email.getText().toString())){
                    msg("Badly formatted email");
                    return;
                } else {
                    Patient patient = new Patient(etFName.getText().toString(),
                                                    etLName.getText().toString(),
                                                    Email.getText().toString());
                    String email = Email.getText().toString();
                    String password = etPassword.getText().toString();

                    mProgressBar.setVisibility(View.VISIBLE);

                    writeNewPatient(patient,etPassword.getText().toString());
                }
            }
        });


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LoginActivity.class));
            }
        });

        mBtnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                if(isInternetAvailable()){
                    mProgressBar.setVisibility(View.GONE);
                    mNoInternetLayout.setVisibility(View.GONE);
                }else{
                    msg("No internet connection");
                    mProgressBar.setVisibility(View.GONE);
                    mNoInternetLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void writeNewPatient(Patient patient,String password){
        try{
            final Patient p = patient;

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(p.email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        long timeStamp = new Date().getTime();
                        mDb.child("patients").child("" + timeStamp).setValue(p);

                        NewCard card = new NewCard(p.email);
                        mDb.child("cards").child("" + timeStamp).setValue(card);

                        finish();
                        startActivity(new Intent(context, DashboardActivity.class));
                    }else{
                        mProgressBar.setVisibility(View.GONE);
                        msg("Error: " + task.getException().toString());
                    }
                }
            });
        }catch (Exception e){
            mProgressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    private boolean checkEmail(String e) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(e);

        if(matcher.find()){
            return false;
        }else if(!matcher.find()){
            return true;
        } else{
            Log.v("Else else","Got here");
            return false;
        }

    }

    private void msg(String message){
        Snackbar snackbar = Snackbar.make(mParentLayout,message,Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    class NewCard{
        public String patient;

        public NewCard(String patient) {
            this.patient = patient;
        }
    }

    @Override
    protected void onPostResume() {
        Log.v("Network---------",isInternetAvailable()+"");
        if(isInternetAvailable()){
            mNoInternetLayout.setVisibility(View.GONE);
        }else{
            mNoInternetLayout.setVisibility(View.VISIBLE);
        }
        mProgressBar.setVisibility(View.GONE);
        super.onPostResume();
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }
}
