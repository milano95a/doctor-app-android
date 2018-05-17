package uz.tech.dst.doctorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uz.tech.dst.doctorapp.ClinicInfoActivity;
import uz.tech.dst.doctorapp.R;
import uz.tech.dst.doctorapp.models.Clinic;

/**
 * Created by Fz_Designs on 3/26/2017.
 */

public class SearchClinicsAdapter extends RecyclerView.Adapter<SearchClinicsAdapter.ViewHolder> {

    private ArrayList<Clinic> data;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView clinicName, clinicLocation;
        RelativeLayout relative;
        ImageView clinicImage;

        public ViewHolder(View v) {
            super(v);
            clinicName = (TextView) v.findViewById(R.id.clinicName);
            clinicLocation = (TextView) v.findViewById(R.id.clinicLocation);
            relative = (RelativeLayout) v.findViewById(R.id.relative);
            clinicImage = (ImageView) v.findViewById(R.id.clinicImage);
        }
    }

    public SearchClinicsAdapter(Context cont, ArrayList<Clinic> dataSet) {
        data = dataSet;
        context = cont;
    }

    @Override
    public SearchClinicsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_search_clinics, viewGroup, false);

        return new SearchClinicsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SearchClinicsAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.clinicName.setText(data.get(position).name);
        viewHolder.clinicLocation.setText(data.get(position).location);
//        viewHolder.clinicImage.setImageDrawable(context.getResources().getDrawable(data.get(position).imgId,null));

        viewHolder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    viewHolder.relative.animate().setDuration(300).withEndAction(new Runnable() {
//                    viewHolder.relative.animate().translationZ(10).setDuration(300).withEndAction(new Runnable() {
//                        @Override
//                        public void run() {
//                            viewHolder.relative.animate().translationZ(2).setDuration(300);
//                            viewHolder.relative.animate().setDuration(300);
                            Log.v("Log Viewholder click", "_---_--_-_-");
                            Intent intent = new Intent(context, ClinicInfoActivity.class);
                            intent.putExtra("obj", data.get(position));
                            context.startActivity(intent);
//                        }
//                    });
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

