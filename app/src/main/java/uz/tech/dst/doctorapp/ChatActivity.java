package uz.tech.dst.doctorapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import uz.tech.dst.doctorapp.adapters.MessageAdapter;
import uz.tech.dst.doctorapp.models.Doctor;
import uz.tech.dst.doctorapp.models.Message;

import java.util.ArrayList;

/**
 * Created by Evgeniy on 12.03.2017.
 */

public class ChatActivity extends AppCompatActivity {

    CircleImageView doctorProfileImage;
    ArrayList<Message> msg;
    Button send;
    ListView listView;
    Context context = this;
    EditText editText;
    Intent intent;
    TextView nameStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        doctorProfileImage = (CircleImageView)findViewById(R.id.doctorProfileImage);
        listView = (ListView) findViewById(R.id.chatListView);
        send = (Button) findViewById(R.id.send);
        editText = (EditText) findViewById(R.id.editText);
        msg = new ArrayList<Message>();
        nameStatus = (TextView)findViewById(R.id.nameStatus);

        intent = getIntent();

        nameStatus.setText((intent.getStringExtra("name")));


        Message message = new Message("Hello, if you have any quesions, you may ask me", false);
        msg.add(message);
        listView.setAdapter(new MessageAdapter(context, R.layout.item_doctor_msg, msg));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg.add(new Message(editText.getText().toString(),true));
                listView.setAdapter(new MessageAdapter(context, R.layout.item_doctor_msg, msg));
                editText.setText("");

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        msg.add(new Message("I will try to help you", false));
                        listView.setAdapter(new MessageAdapter(context, R.layout.item_doctor_msg, msg));
                    }
                }, 5000);
            }
        });


        doctorProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DoctorProfileActivity.class);
                intent.putExtra("docObject",(Doctor)getIntent().getSerializableExtra("docObj"));
                startActivity(intent);

            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
