package uz.tech.dst.doctorapp.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by AB on 22-May-17.
 */

@IgnoreExtraProperties
public class Card {
    public String patient;

    public String author;
    public String diagnosis;
    public String doctor;
    public String doctorName;
    public long time;
    public String type;

    public Card(String author, String diagnosis, String doctor, String doctorName, long time, String type) {
        this.author = author;
        this.diagnosis = diagnosis;
        this.doctor = doctor;
        this.doctorName = doctorName;
        this.time = time;
        this.type = type;
    }

    public Card(String patient){
        this.patient = patient;
    }

    public Card() {
    }
}
