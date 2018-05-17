package uz.tech.dst.doctorapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import uz.tech.dst.doctorapp.models.LocalDb;

public class LoginActivity extends AppCompatActivity{

    Button SignIn, SignUp, mBtnTry;
    EditText Email, Password;
    Context context = this;
    LinearLayout mProgressBar, mParentLayout, mNoInternetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtnTry = (Button)findViewById(R.id.btn_try);
        mNoInternetLayout = (LinearLayout)findViewById(R.id.no_connection);
        mParentLayout = (LinearLayout)findViewById(R.id.root);
        mProgressBar = (LinearLayout)findViewById(R.id.progress_bar);
        SignIn = (Button)findViewById(R.id.btn_SignedUser);
        SignUp = (Button)findViewById(R.id.btn_registerNew);

        Email = (EditText)findViewById(R.id.et_email);
        Password = (EditText) findViewById(R.id.et_password);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Email.getText().toString();
                String password = Password.getText().toString();

                if(email.equals("") || password.equals("")){
                    msg("Fields cannot be empty");
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

                FirebaseAuth.getInstance().signInWithEmailAndPassword(Email.getText().toString(),Password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                finish();
                                startActivity(new Intent(context, DashboardActivity.class));
                        }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        msg("Athentication failed, please check and try again");
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, LoginFormActivity.class));
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

    private void msg(String message){
        Snackbar snackbar = Snackbar.make(mParentLayout,message,Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
        snackbar.show();
    }
    @Override
    protected void onStart() {
        mProgressBar.setVisibility(View.GONE);
        LocalDb.CONNECTION_LISTENER = false;
        LocalDb.INVITATION_LISTENER = false;
        LocalDb.DOCTOR_LISTENER = false;
        super.onStart();
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    @Override
    protected void onPostResume() {
        Log.v("Network---------",isInternetAvailable()+"");
        if(isInternetAvailable()){
            mNoInternetLayout.setVisibility(View.GONE);
        }else{
            mNoInternetLayout.setVisibility(View.VISIBLE);
        }
        super.onPostResume();
    }


}
