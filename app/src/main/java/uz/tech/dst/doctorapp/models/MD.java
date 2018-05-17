package uz.tech.dst.doctorapp.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by AB on 23-May-17.
 */


@IgnoreExtraProperties
public class MD {
    public String firstname;
    public String lastname;
    public String email;
    public String speciality;
    public String clinic;

    public MD() {
    }

    public MD(String firstname, String lastname, String email, String speciality, String clinic) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.speciality = speciality;
        this.clinic = clinic;
    }
}
