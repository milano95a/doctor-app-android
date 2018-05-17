package uz.tech.dst.doctorapp.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Invitation {
    public String sender;
    public String from;
    public String to;
    public String toAndStatus;


    public Invitation() {
    }

    public Invitation(String sender, String from, String to, String toAndStatus) {
        this.sender = sender;
        this.from = from;
        this.to = to;
        this.toAndStatus = toAndStatus;
    }
}
