package uz.tech.dst.doctorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uz.tech.dst.doctorapp.ChatActivity;
import uz.tech.dst.doctorapp.Constants;
import uz.tech.dst.doctorapp.DoctorProfileActivity;
import uz.tech.dst.doctorapp.R;
import uz.tech.dst.doctorapp.models.Clinic;
import uz.tech.dst.doctorapp.models.Doctor;
import uz.tech.dst.doctorapp.models.LocalDb;

/**
 * Created by Evgeniy on 27.03.2017.
 */

public class CommunicationListAdapter extends RecyclerView.Adapter<CommunicationListAdapter.ViewHolder> {

    private ArrayList<Doctor> data;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameOfDoc, spec;
        RelativeLayout rel;
        ImageView onlineIndicator;

        public ViewHolder(View v) {
            super(v);
            nameOfDoc = (TextView) v.findViewById(R.id.nameOfDoc);
            spec = (TextView) v.findViewById(R.id.spec);
            onlineIndicator = (ImageView) v.findViewById(R.id.onlineIndicator);
            rel = (RelativeLayout) v.findViewById(R.id.rel);
        }
    }

    public CommunicationListAdapter(Context cont, ArrayList<Doctor> dataSet) {
        data = dataSet;
        context = cont;
    }

    @Override
    public CommunicationListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_communication_list, viewGroup, false);

        return new CommunicationListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CommunicationListAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.nameOfDoc.setText(data.get(position).firstName + " " + data.get(position).lastName);
        viewHolder.spec.setText(data.get(position).clinicName +
                ", " + data.get(position).specialization + ", " + data.get(position).email);
        if (data.get(position).onlineIndicator) {
            viewHolder.onlineIndicator.setImageDrawable(context.getResources()
                    .getDrawable(R.drawable.online_indicator));
        } else {
            viewHolder.onlineIndicator.setImageDrawable(context.getResources()
                    .getDrawable(R.drawable.offline_indicator));
        }

        viewHolder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s;
                if (data.get(position).onlineIndicator) {
                    s = " (online)";
                } else {
                    s = " (offline)";
                }

                if(Constants.ACTIVITY_STATE == Constants.SEARCH_FOR_CLINICS){
                    Intent intent = new Intent(context, DoctorProfileActivity.class);
//                    intent.putExtra("docObject",(Doctor)getIntent().getSerializableExtra("docObj"));
                    intent.putExtra("docObject", data.get(position));
                    LocalDb.instance.invitedDoctor = position;
                    context.startActivity(intent);

//                    viewHolder.rel.animate().translationZ(10).setDuration(300).withEndAction(new Runnable() {

//                        @Override
//                        public void run() {
//                            viewHolder.rel.animate().translationZ(2).setDuration(300);
//                        }

//                    });
                }else{


                    final Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("name", data.get(position).firstName + " " +
                            data.get(position).lastName + s);

                    intent.putExtra("docObj", data.get(position));

                    viewHolder.rel.animate().translationZ(10).setDuration(300).withEndAction(new Runnable() {

                        @Override
                        public void run() {
                            viewHolder.rel.animate().translationZ(2).setDuration(300);
                            context.startActivity(intent);
                        }

                    });
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
