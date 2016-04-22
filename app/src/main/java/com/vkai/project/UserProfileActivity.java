package com.vkai.project;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ria on 3/25/2016.
 */
public class UserProfileActivity extends Activity {

    private EditText ename,eage,ehgt,ewgt;
    int user_id,uage;
    float uhgt,uwgt;
    double bmr;
    String username,ugender;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Context ctx =this;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        btnSave = (Button) findViewById(R.id.button1);
        ename = (EditText) findViewById(R.id.editText3);
        eage = (EditText) findViewById(R.id.editText4);
        ehgt = (EditText) findViewById(R.id.editText5);
        ewgt = (EditText) findViewById(R.id.editText6);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        Bundle bundle = getIntent().getExtras();                       //Setting text field of user profile activity
        user_id = bundle.getInt("id1");
        TextView mytextview = (TextView) findViewById(R.id.textView2);
        mytextview.setText(user_id + "");

        TableUserProfile DB= new TableUserProfile(ctx);                           //Setting values of fields in user profile activity
        DB.open();
        Cursor cur=DB.selectOneItem(user_id);

        if (cur == null || cur.getCount() == 0) {                   //If record not found, set default values
            ename.setText("NULL");
            eage.setText("NULL");
            ehgt.setText("NULL");
            ewgt.setText("NULL");
        }
        else {                                                      //Else set values from table
            cur.moveToNext();
            String name1 = cur.getString(1);
            String age1 = cur.getString(2);
            String height1 = cur.getString(3);
            String weight1 = cur.getString(4);
            String gender1 = cur.getString(5);

            ename.setText(name1);
            eage.setText(age1);
            ehgt.setText(height1);
            ewgt.setText(weight1);

            for(int i=0; i<radioGroup.getChildCount(); i++) {
                radioButton = (RadioButton) radioGroup.getChildAt(i);
                if (radioButton.getText().equals(gender1))
                    radioButton.setChecked(true);
            }
        }
        btnSave.setOnClickListener(new View.OnClickListener() {                      // When save button is clicked, if user_id is present updated values are stored in database else new record is inserted.
            public void onClick(View v) {
                //Bundle bundle = getIntent().getExtras();
                //user_id = bundle.getInt("id1");
                username = ename.getText().toString();
                uage =Integer.parseInt(eage.getText().toString());
                uhgt =Float.parseFloat(ehgt.getText().toString());
                uwgt =Float.parseFloat(ewgt.getText().toString());
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                ugender=radioButton.getText().toString();
                ename.setText("");
                eage.setText("");
                ehgt.setText("");
                ewgt.setText("");
                radioButton.setChecked(false);
                // Toast.makeText(getApplicationContext(),"id is"+user_id,Toast.LENGTH_LONG).show();
                if(ugender.equals("Female")) {
                    bmr=(9.56*uwgt)+(1.85*uhgt)-(4.68*uage)+655;
                }
                else if(ugender.equals("Male")){
                    bmr=(13.75*uwgt)+(5*uhgt)-(6.76*uage)+66;
                }
                TableUserProfile DB= new TableUserProfile(ctx);
                DB.open();
                DB.insertData(user_id,username,uage,uhgt,uwgt,ugender,bmr);
                Toast.makeText(getBaseContext(), "USER ENTRY INSERTED", Toast.LENGTH_LONG).show();
            }
        });
    }
}