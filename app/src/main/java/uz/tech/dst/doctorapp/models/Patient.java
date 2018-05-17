package uz.tech.dst.doctorapp.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by AB on 22-May-17.
 */

@IgnoreExtraProperties
public class Patient {
    public String firsname;
    public String lastname;
    public String email;

    public Patient(String firsname, String lastname, String email) {
        this.firsname = firsname;
        this.lastname = lastname;
        this.email = email;
    }

    public Patient() {
    }

    @Override
    public String toString() {
        return firsname + "  " + lastname + "  " + email;
    }
}
