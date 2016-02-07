package com.unf.swoopflex;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Ricky on 1/31/16.
 */
public class HomeWorkout extends Fragment{

    Context ctx = null;
    Button btn;
    Spinner Height_Feet;
    Spinner Height_Inch;
    EditText Weight;
    Spinner Age;
    RadioGroup Gender;
    int user_height, user_feet, user_inch, user_weight, user_age, user_gender;
    double bmi;


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
                bmi = 1.5;

                ctx = getActivity().getApplicationContext();
                SQLiteDB DB = new SQLiteDB(ctx);
                DB.SQLiteUserInsert(DB, user_height, user_weight, user_age, user_gender, bmi);
                Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }


}




