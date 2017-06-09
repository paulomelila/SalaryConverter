package com.example.paulo.salaryconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {
    private ImageView mConvertImage;
    private TextView mErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Instantiating the string arrays for each Spinner
        final String[] hc_array = getResources().getStringArray(R.array.home_countries_array);
        final String[] j_array = getResources().getStringArray(R.array.job_array);
        final String[] rc_array = getResources().getStringArray(R.array.remote_countries_array);

        // Error text to be displayed if any of the options are not chosen or if the home country
        // and remote country are the same.
        mErrorText = (TextView) findViewById(R.id.error_text);

        //----------------------------------
        //  HOME COUNTRY SPINNER
        //----------------------------------
        final Spinner home_country = (Spinner) findViewById(R.id.home_countries_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> hc = ArrayAdapter.createFromResource(this,
                R.array.home_countries_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        hc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        home_country.setAdapter(hc);

        //----------------------------------
        // JOB SPINNER
        //----------------------------------
        final Spinner job = (Spinner) findViewById(R.id.job_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> j = ArrayAdapter.createFromResource(this,
                R.array.job_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        j.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        job.setAdapter(j);

        //----------------------------------
        //  REMOTE JOB LOCATION SPINNER
        //----------------------------------
        final Spinner remote_country = (Spinner) findViewById(R.id.remote_countries_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> rc = ArrayAdapter.createFromResource(this,
                R.array.remote_countries_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        rc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        remote_country.setAdapter(rc);

        // Adding the rotate animation to the image after it's clicked by the user.
        mConvertImage = (ImageView) findViewById(R.id.convert_image);
        mConvertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String hc = home_country.getSelectedItem().toString();
                final String j = job.getSelectedItem().toString();
                final String rc = remote_country.getSelectedItem().toString();

                if (hc.equals(hc_array[0]) || j.equals(j_array[0]) || rc.equals(rc_array[0])) {
                    mErrorText.setVisibility(View.VISIBLE);
                    mErrorText.setText(R.string.error_message);
                }

                else {
                    if (hc.equals(rc)) {
                        mErrorText.setVisibility(View.VISIBLE);
                        mErrorText.setText(R.string.error_same_hc_rc);
                    } else {
                        mErrorText.setVisibility(View.INVISIBLE);
                        RotateAnimation rotate = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF,
                                0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                        rotate.setDuration(1500);
                        rotate.setRepeatCount(1);
                        mConvertImage.startAnimation(rotate);

                        // after rotating start SalaryInfoActivity
                        rotate.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                // not needed
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                // Creating an intent to start SalaryInfoActivity and send the user's
                                // choices to the next page.
                                Intent salary_info = new Intent(MainPageActivity.this, SalaryInfoActivity.class);
                                salary_info.putExtra("Home country", hc);
                                salary_info.putExtra("Job", j);
                                salary_info.putExtra("Remote country", rc);
                                startActivity(salary_info);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                                // not needed
                            }
                        });
                    }
                }
            }
        });
    }
}