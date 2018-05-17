package uz.tech.dst.doctorapp.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Connection {
    public String doctor;
    public String patient;
    public String pFullname;
    public String st;

    public Connection() {
    }

    public Connection(String doctor, String patient, String pFullname, String st) {
        this.doctor = doctor;
        this.patient = patient;
        this.pFullname = pFullname;
        this.st = st;
    }
}
