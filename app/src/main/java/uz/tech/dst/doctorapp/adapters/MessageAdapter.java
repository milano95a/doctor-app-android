package uz.tech.dst.doctorapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import uz.tech.dst.doctorapp.models.Message;
import uz.tech.dst.doctorapp.R;

/**
 * Created by Fz_Designs on 3/13/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    private ArrayList<Message> data;

    private Context context;
    private int layoutResourceId;

    public MessageAdapter(Context context, int layoutResourceId, ArrayList<Message> data) {
        super(context,layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    public View getView(int position, View row, ViewGroup parent) {

        Message msg = data.get(position);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        if (msg.myMsg){
            row = inflater.inflate(R.layout.item_my_msg, parent, false);
        } else {
            row = inflater.inflate(R.layout.item_doctor_msg, parent, false);
        }

        TextView msgChat;

        msgChat = (TextView)row.findViewById(R.id.msgChat);

        msgChat.setText(msg.msg);


        return row;
    }

}


