package uz.tech.dst.doctorapp.models;

import java.io.Serializable;

/**
 * Created by Fz_Designs on 3/26/2017.
 */

public class Doctor implements Serializable {
    public String email;

    public String firstName;
    public String lastName;
    public String specialization;
    public String clinicName;
    public boolean onlineIndicator;
    public int age;

    public Doctor() {
        super();
    }

    public Doctor(String firstName, String lastName, String specialization, String clinicName, boolean onlineIndicator, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.clinicName = clinicName;
        this.onlineIndicator = onlineIndicator;
        this.age = age;
    }

    public Doctor(String email, String firstName, String lastName, String specialization, String clinicName, boolean onlineIndicator, int age) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.clinicName = clinicName;
        this.onlineIndicator = onlineIndicator;
        this.age = age;
    }
}
