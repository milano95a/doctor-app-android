package uz.tech.dst.doctorapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import uz.tech.dst.doctorapp.LoginActivity;
import uz.tech.dst.doctorapp.PersonalNoteActivity;
import uz.tech.dst.doctorapp.models.Note;
import uz.tech.dst.doctorapp.R;

/**
 * Created by Fz_Designs on 3/15/2017.
 */

public class HospitalChartAdapter extends RecyclerView.Adapter<HospitalChartAdapter.ViewHolder> {

    public ArrayList<Note> data;
    public boolean boxVisible = false;
    private Context context;
    private boolean[] shareChart;
    private boolean clickable = true;
    public boolean dontClickItemIfCheckBoxesIsVisible = true;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView noteType, noteName, docName, speciality, modifyDate;
        private CheckBox box;
        private RelativeLayout relLayout;
        private TextView shareTime;
        private TextView sharedTo;
        private LinearLayout sharedLayout;
        private Button endShareBtn;


        public ViewHolder(View v) {
            super(v);
            noteType = (TextView) v.findViewById(R.id.noteType);
            noteName = (TextView) v.findViewById(R.id.noteName);
            docName = (TextView) v.findViewById(R.id.docName);
            speciality = (TextView) v.findViewById(R.id.speciality);
            modifyDate = (TextView) v.findViewById(R.id.modifyDate);
            box = (CheckBox) v.findViewById(R.id.chartBox);
            relLayout = (RelativeLayout) v.findViewById(R.id.relLayout);
            shareTime = (TextView) v.findViewById(R.id.shareTime);
            sharedTo = (TextView) v.findViewById(R.id.sharedTo);
            sharedLayout = (LinearLayout)v.findViewById(R.id.sharedLayout);
            endShareBtn = (Button)v.findViewById(R.id.endShareBtn);

        }
    }

    public HospitalChartAdapter(Context cont, ArrayList<Note> dataSet) {
        data = dataSet;
        context = cont;
        shareChart = new boolean[getItemCount()];
        for (int i = 0; i < getItemCount(); i++) {
            shareChart[i] = false;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_note, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (data.get(position).check) {
            viewHolder.box.setChecked(true);
        } else {
            viewHolder.box.setChecked(false);
        }

        viewHolder.noteType.setText(data.get(position).noteType);
        viewHolder.noteName.setText(data.get(position).noteName);
        viewHolder.docName.setText(data.get(position).docName);
        viewHolder.speciality.setText(data.get(position).speciality);
        viewHolder.modifyDate.setText(data.get(position).modifyDate);

        if (!data.get(position).shareTime.equals("")) {
            viewHolder.sharedLayout.setVisibility(View.VISIBLE);
            viewHolder.endShareBtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.sharedLayout.setVisibility(View.GONE);
            viewHolder.endShareBtn.setVisibility(View.GONE);
        }

        viewHolder.shareTime.setText(data.get(position).shareTime);
        viewHolder.sharedTo.setText(data.get(position).sharedTo);

        if (boxVisible) {
            viewHolder.box.setVisibility(View.VISIBLE);
        } else {
            viewHolder.box.setVisibility(View.INVISIBLE);
        }

        if (clickable) {
            viewHolder.relLayout.setClickable(true);
        } else {
            viewHolder.relLayout.setClickable(false);
        }

        viewHolder.endShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("End sharing");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if (dontClickItemIfCheckBoxesIsVisible) {
                            data.get(position).shareTime = "";
                            notifyDataSetChanged();
                        }
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
        });

        viewHolder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                data.get(position).check = b;
            }
        });
        viewHolder.relLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dontClickItemIfCheckBoxesIsVisible) {
                    viewHolder.relLayout.animate().translationZ(10).setDuration(300).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            viewHolder.relLayout.animate().translationZ(2).setDuration(300);
                            Intent intent = new Intent(context, PersonalNoteActivity.class);
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



