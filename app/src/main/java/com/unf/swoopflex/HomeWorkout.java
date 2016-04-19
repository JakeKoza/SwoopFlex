package com.unf.swoopflex;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Class used to display and take in user information
 */
public class HomeWorkout extends Fragment{

    private Context ctx = null;
    private Button btn;
    private Spinner Height_Feet, Height_Inch, Age;
    private EditText Weight;
    private RadioGroup Gender;
    private int user_height, user_feet, user_inch, user_weight, user_age, user_gender;
    private double temp_height, temp_weight;
    private double bmi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.client_information_form, container, false);

        Height_Feet = (Spinner) view.findViewById(R.id.spnFeet);
        Height_Inch = (Spinner) view.findViewById(R.id.spnInch);
        Weight = (EditText) view.findViewById(R.id.nmbrWeight);
        Age = (Spinner) view.findViewById(R.id.spnAge);
        Gender = (RadioGroup) view.findViewById(R.id.radGender);
        btn = (Button) view.findViewById(R.id.btnSaveInfo);

        ctx = getActivity().getApplicationContext();
        SQLiteDB DB = new SQLiteDB(ctx);

        //Checks to see if DB exist. If true loads user data from local DB
        if(DB.SQLiteCheckDB(DB)) {
            Cursor CR = DB.SQLiteUserData(DB);
            CR.moveToFirst();


            user_height = CR.getInt(0);
            user_inch = user_height%12;
            user_feet = (user_height-user_inch)/12;
            user_weight = CR.getInt(1);
            user_age = CR.getInt(2);
            user_gender = CR.getInt(3);

            Height_Feet.setSelection(user_feet-3);
            Height_Inch.setSelection(user_inch);
            Weight.setText(String.valueOf(user_weight));
            Age.setSelection(user_age - 16);
            if(user_gender==1) {
                ((RadioButton) Gender.findViewById(R.id.radioButtonMale)).setChecked(true);
            }else {
                ((RadioButton) Gender.findViewById(R.id.radioButtonFemale)).setChecked(true);
            }
        }

        /**
         * When clicked Saves entered information to local DB
         */
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_feet = Integer.parseInt(Height_Feet.getSelectedItem().toString());
                user_inch = Integer.parseInt(Height_Inch.getSelectedItem().toString());
                user_height = user_inch +(user_feet * 12);
                user_weight = Integer.parseInt(Weight.getText().toString());
                user_age = Integer.parseInt(Age.getSelectedItem().toString());

                int genderID = Gender.getCheckedRadioButtonId();
                if (genderID == -1){
                    user_gender = 0;
                }
                if (genderID == R.id.radioButtonFemale){
                    user_gender = 0;
                }
                if (genderID == R.id.radioButtonMale){
                    user_gender = 1;
                }
                //calculate bmi
                temp_weight = user_weight/2.2;

                temp_height = user_height * .0254;

                temp_height = temp_height * temp_height;

                bmi = temp_weight/temp_height;

                ctx = getActivity().getApplicationContext();
                SQLiteDB DB = new SQLiteDB(ctx);
                DB.SQLiteUserInsert(DB, user_height, user_weight, user_age, user_gender, bmi);
                Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }


}




