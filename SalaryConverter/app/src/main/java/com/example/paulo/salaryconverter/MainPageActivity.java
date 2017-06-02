package com.example.paulo.salaryconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainPageActivity extends AppCompatActivity {
    private ImageView mConvertImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //----------------------------------
        //  HOME COUNTRY SPINNER
        //----------------------------------
        Spinner home_country = (Spinner) findViewById(R.id.home_countries_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> hc = ArrayAdapter.createFromResource(this,
                R.array.home_countries_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        hc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        home_country.setAdapter(hc);


        //----------------------------------
        // JOB SPINNER
        //----------------------------------
        Spinner job = (Spinner) findViewById(R.id.job_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> j = ArrayAdapter.createFromResource(this,
                R.array.job_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        j.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        job.setAdapter(j);


        //----------------------------------
        //  REMOTE JOB LOCATION SPINNER
        //----------------------------------
        Spinner remote_country = (Spinner) findViewById(R.id.remote_countries_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> rc = ArrayAdapter.createFromResource(this,
                R.array.remote_countries_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        rc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        remote_country.setAdapter(rc);

        // Adding the rotate animation to the image after it's clicked by the user.
        mConvertImage = (ImageView) findViewById(R.id.convert_image);
        mConvertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation rotate = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF,
                        0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(1500);
                rotate.setRepeatCount(2);
                mConvertImage.startAnimation(rotate);
                // after rotating start SalaryInfoActivity
            }
        });
    }
}
