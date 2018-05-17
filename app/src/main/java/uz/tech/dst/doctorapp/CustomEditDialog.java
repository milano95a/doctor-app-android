package uz.tech.dst.doctorapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import uz.tech.dst.doctorapp.R;

/**
 * Created by Muxriddin on 5/16/2017.
 */

public class CustomEditDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity ac;
    public Dialog d;
    public Button Save, Cancel;

    public CustomEditDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.ac = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_my_profile);
        Save = (Button) findViewById(R.id.btn_Save);
        Cancel = (Button) findViewById(R.id.btn_Cancel);
        Save.setOnClickListener(this);
        Cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Save:
                ac.finish();
                break;
            case R.id.btn_Cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
