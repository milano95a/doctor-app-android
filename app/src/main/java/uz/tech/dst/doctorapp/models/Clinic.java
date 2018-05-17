package uz.tech.dst.doctorapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fz_Designs on 3/26/2017.
 */

public class Clinic implements Serializable{

    public String name;
    public String headPhisician;
    public String workSphere;
    public String location;
    public ArrayList<Doctor> doctors;
    public String info;
    public int imgId;


    public Clinic(){
        super();
    }

    public Clinic(String name, String headPhisician, String workSphere, String location,ArrayList<Doctor> doctors, String info, int imgId){
        this.name = name;
        this.headPhisician = headPhisician;
        this.workSphere = workSphere;
        this.location = location;
        this.doctors = doctors;
        this.info = info;
        this.imgId = imgId;
    }
}
