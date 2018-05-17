package uz.tech.dst.doctorapp.models;

/**
 * Created by Fz_Designs on 3/15/2017.
 */

public class Note {
    public String id;
    public String doctors;
    public String noteType;
    public String noteName;
    public String docName;
    public String speciality;
    public String modifyDate;
    public String shareTime;
    public String sharedTo;
    public boolean check;

    public Note() {
        super();
    }

    public Note(String noteType, String noteName, String docName, String speciality,
                String modifyDate, String shareTime, String sharedTo, boolean check) {
        super();
        this.sharedTo = sharedTo;
        this.noteName = noteName;
        this.noteType = noteType;
        this.docName = docName;
        this.speciality = speciality;
        this.modifyDate = modifyDate;
        this.shareTime = shareTime;
        this.check = check;

    }

    public Note(String doctors,String id, String noteType, String noteName, String docName, String speciality, String modifyDate, String shareTime, String sharedTo, boolean check) {
        this.doctors = doctors;
        this.id = id;
        this.noteType = noteType;
        this.noteName = noteName;
        this.docName = docName;
        this.speciality = speciality;
        this.modifyDate = modifyDate;
        this.shareTime = shareTime;
        this.sharedTo = sharedTo;
        this.check = check;
    }
}
