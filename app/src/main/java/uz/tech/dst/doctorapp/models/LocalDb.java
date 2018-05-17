package uz.tech.dst.doctorapp.models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LocalDb {

    DatabaseReference mDb = FirebaseDatabase.getInstance().getReference();

    private LocalDb() {
    }
    public static boolean DOCTOR_LISTENER;
    public static boolean CONNECTION_LISTENER;
    public static boolean INVITATION_LISTENER;

    public Integer invitedDoctor = null;
    public Patient currentUser;
    public static LocalDb instance = new LocalDb();

    public ArrayList<MD> mds = new ArrayList<>();
    public ArrayList<Connection> connections = new ArrayList<>();

    public ArrayList<Invitation> invitations = new ArrayList<>();

    public void retriveDoctors(){
        mds.clear();
        DOCTOR_LISTENER = true;
        mDb.child("doctors").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try{
                    MD doctor = dataSnapshot.getValue(MD.class);

//                    Log.v("Doctor retreved_____",
//                            "Firstname: " + doctor.firstname +
//                                    " Lastname: " + doctor.lastname +
//                                    " Email: " + doctor.email +
//                                    " Speciality: "+ doctor.speciality +
//                                    " Clinic: " + doctor.clinic);
                    mds.add(doctor);
                }catch (Exception e){
                    Log.v("----retrieveDoctors----", "Exception");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void retriveConnections(){
        connections.clear();
        CONNECTION_LISTENER = true;
        mDb.child("connections").orderByChild("patient").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.v("Connection___---__--",dataSnapshot.getValue().toString());
                Connection connection = dataSnapshot.getValue(Connection.class);
                connections.add(connection);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void retriveInvitations(){
        invitations.clear();
        INVITATION_LISTENER = true;
        mDb.child("invitations").orderByChild("from").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.v("Invitation-------------",dataSnapshot.getValue().toString());
                Invitation invitation = dataSnapshot.getValue(Invitation.class);

                if(invitation.toAndStatus.contains("_new")){
                    invitations.add(invitation);
                    Log.v("Invitation Added-------",dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<MD> getMyDoctors(){
        ArrayList<MD> myDoctors = new ArrayList<>();

        for (MD md:mds) {
            for (Connection c:connections) {
                if(c.doctor.equals(md.email)){
                    myDoctors.add(md);
                }
            }
        }

        Log.v("-----My doctors: ", myDoctors.size() + "");
        Log.v("-----Connected doctors:", connections.size() + "");
        Log.v("-----All doctors", mds.size() + "");
        return myDoctors;
    }

    public ArrayList<Doctor> getDoctorsByClinic(String clinic){
        ArrayList<MD> otherDoctors = new ArrayList<>();
        ArrayList<Doctor> doctors = new ArrayList<>();

        for (MD md:mds) {
            if(md.clinic.equals(clinic)){
                checkConnection(md,otherDoctors);
            }
        }

        for(MD md:otherDoctors){
            Doctor doctor = new Doctor(md.email,md.firstname,md.lastname,md.speciality,md.clinic,false,0);
            doctors.add(doctor);
        }
        return doctors;
    }

    public void checkConnection(MD doctor, ArrayList<MD> otherDoctors){
        for (Connection c:connections) {
            if (c.doctor.equals(doctor.email)){
                return;
            }
        }
        checkInvitation(doctor,otherDoctors);
    }

    public void checkInvitation(MD doctor, ArrayList<MD> otherDoctors){
        Log(invitations.size() + "");
        for (Invitation i:invitations) {
            Log(i.toAndStatus);
            if (i.to.equals(doctor.email)){
                Log(i.to + "  email true");
                if(i.toAndStatus.equals(doctor.email + "_new") || i.toAndStatus.equals(doctor.email+"_accepted")){
                    Log(i.toAndStatus + "toAndStatus true");
                    return;
                }else if(i.toAndStatus.equals(doctor.email + "_rejected")){
                    otherDoctors.add(doctor);
                }
            }
        }
        otherDoctors.add(doctor);
    }

    public void getUser(){
        mDb.child("patients").orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                currentUser = dataSnapshot.getValue(Patient.class);
                Log.v("-------User---------",currentUser.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void Log(String s){
        Log.v("-----------------------",s);
    }
}
