package com.example.quarantinemanagement;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {


    String[] stateNames = {"Jharkhand", "Andhra Pradesh", "Arunachal Pradesh", "Assam",
            "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh"
            , "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan",
            "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh",
            "Uttarakhand", "West Bengal"};


    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String fullName="";
    String age="";
    String adLine1="";
    String adLine2="";
    String district="";
    String state;
    String bloodGroup="";
    String emergencyNumber="";
    String gender="";
    Button REGISTER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("CMA/user_details/");
        final EditText et_fullName = findViewById(R.id.full_name);
        final EditText et_age = findViewById(R.id.txt_age);
        final EditText et_adLine1 = findViewById(R.id.txt_add_line_1);
        final EditText et_adLine2 = findViewById(R.id.txt_add_line_2);
        final EditText et_district = findViewById(R.id.get_district);
        final EditText et_bloodGroup = findViewById(R.id.blood_group);
        final EditText et_EmergencyNumber = findViewById(R.id.emergency_contact_number);
        REGISTER=findViewById(R.id.register_button);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner_choose_state);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stateNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


// comment added


        REGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName=et_fullName.getText().toString();
                age=et_age.getText().toString();
                adLine1=et_adLine1.getText().toString();
                adLine2=et_adLine2.getText().toString();
                district=et_district.getText().toString();
                bloodGroup=et_bloodGroup.getText().toString();
                emergencyNumber=et_EmergencyNumber.getText().toString();
                Map<String, String> mUser = new HashMap<>();
                mUser.put("Name",fullName);
                mUser.put("Gender",gender);
                mUser.put("Age",age);
                mUser.put("Address",adLine1+", "+adLine2+", "+district+", "+state);
                mUser.put("Blood Group",bloodGroup);
                mUser.put("Emergency Contact No",emergencyNumber);
                myRef.child("user1").setValue(mUser);

                //for location update
                Intent intent=new Intent(RegistrationActivity.this,MapsActivity.class);
                intent.putExtra("username",fullName);
                startActivity(intent);


            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), stateNames[position], Toast.LENGTH_SHORT).show();
        state= stateNames[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    gender="male";
                    break;
            case R.id.radio_female:
                if (checked)
                    gender="female";
                    break;
        }
    }


}
