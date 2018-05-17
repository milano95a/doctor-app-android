package uz.tech.dst.doctorapp.models;

/**
 * Created by Fz_Designs on 3/13/2017.
 */

public class Message {
    public String msg;
    public boolean myMsg;

    public Message() {
        super();
    }

    public Message(String msg, boolean myMsg){
        this.msg = msg;
        this.myMsg = myMsg;
    }
}
