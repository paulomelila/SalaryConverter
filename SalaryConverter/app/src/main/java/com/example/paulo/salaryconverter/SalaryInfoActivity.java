package com.example.paulo.salaryconverter;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SalaryInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_info);

        // Getting the content of the string array for conversion rates
        String[] rates_array = getResources().getStringArray(R.array.conversion_rates_array);

        // Getting the content of the string arrays for average salary
        String[] avg_salary_can = getResources().getStringArray(R.array.average_salary_canada);
        String[] avg_salary_usa = getResources().getStringArray(R.array.average_salary_unitedStates);
        String[] avg_salary_mex = getResources().getStringArray(R.array.average_salary_mexico);
        String[] avg_salary_bra = getResources().getStringArray(R.array.average_salary_brazil);
        String[] avg_salary_ger = getResources().getStringArray(R.array.average_salary_germany);
        String[] avg_salary_jap = getResources().getStringArray(R.array.average_salary_japan);
        String[] avg_salary_nig = getResources().getStringArray(R.array.average_salary_nigeria);
        String[] avg_salary_pol = getResources().getStringArray(R.array.average_salary_poland);
        String[] avg_salary_eng = getResources().getStringArray(R.array.average_salary_england);
        String[] avg_salary_kor = getResources().getStringArray(R.array.average_salary_southKorea);

        // Widgets
        ImageView home_country_flag = (ImageView) findViewById(R.id.home_country_flag);
        ImageView remote_country_flag = (ImageView) findViewById(R.id.remote_country_flag);
        ImageView hc_flag_salary = (ImageView) findViewById(R.id.hc_flag_salary);
        ImageView rc_flag_salary = (ImageView) findViewById(R.id.rc_flag_salary);
        TextView mJob = (TextView) findViewById(R.id.job);
        TextView mPercent = (TextView) findViewById(R.id.percentDifferenceTex);
        TextView mRateLabel = (TextView) findViewById(R.id.rate_label);
        TextView mRateText = (TextView) findViewById(R.id.rate);
        TextView mAvg_salary = (TextView) findViewById(R.id.avg_salary);
        TextView mHc_salary = (TextView) findViewById(R.id.homeCountryAverageSalary);
        TextView mRc_salary = (TextView) findViewById(R.id.remoteCountryAverageSalary);

        // Roboto typefaces
        Typeface roboto_regular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        Typeface roboto_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Italic.ttf");
        Typeface roboto_bold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        mJob.setTypeface(roboto_regular);
        mPercent.setTypeface(roboto_italic);
        mRateLabel.setTypeface(roboto_bold);
        mRateText.setTypeface(roboto_bold);
        mAvg_salary.setTypeface(roboto_regular);
        mHc_salary.setTypeface(roboto_italic);
        mRc_salary.setTypeface(roboto_italic);

        // Intent to receive the user's choices from the main page.
        Intent receiver = getIntent();
        Bundle b = receiver.getExtras();

        String home = b.getString("Home country");
        String job = b.getString("Job");
        String remote = b.getString("Remote country");

        // HOME COUNTRY FLAGS
        if (home.equals("Canada")) {
            home_country_flag.setImageResource(R.mipmap.canada);
            hc_flag_salary.setImageResource(R.mipmap.canada);
        }
        if (home.equals("United States")) {
            home_country_flag.setImageResource(R.mipmap.usa);
            hc_flag_salary.setImageResource(R.mipmap.usa);
        }
        if (home.equals("Mexico")) {
            home_country_flag.setImageResource(R.mipmap.mexico);
            hc_flag_salary.setImageResource(R.mipmap.mexico);
        }
        if (home.equals("Brazil")) {
            home_country_flag.setImageResource(R.mipmap.brazil);
            hc_flag_salary.setImageResource(R.mipmap.brazil);
        }
        if (home.equals("Germany")) {
            home_country_flag.setImageResource(R.mipmap.germany);
            hc_flag_salary.setImageResource(R.mipmap.germany);
        }
        if (home.equals("Japan")) {
            home_country_flag.setImageResource(R.mipmap.japan);
            hc_flag_salary.setImageResource(R.mipmap.japan);
        }
        if (home.equals("Nigeria")) {
            home_country_flag.setImageResource(R.mipmap.nigeria);
            hc_flag_salary.setImageResource(R.mipmap.nigeria);
        }
        if (home.equals("Poland")) {
            home_country_flag.setImageResource(R.mipmap.poland);
            hc_flag_salary.setImageResource(R.mipmap.poland);
        }
        if (home.equals("England")) {
            home_country_flag.setImageResource(R.mipmap.england);
            hc_flag_salary.setImageResource(R.mipmap.england);
        }
        if (home.equals("South Korea")) {
            home_country_flag.setImageResource(R.mipmap.south_korea);
            hc_flag_salary.setImageResource(R.mipmap.south_korea);
        }

        // JOB TEXT
        if (job.equals("Software engineer")) {
            mJob.setText(job);
        }
        if (job.equals("Software tester")) {
            mJob.setText(job);
        }
        if (job.equals("UI designer")) {
            mJob.setText(job);
        }
        if (job.equals("Web developer")) {
            mJob.setText(job);
        }
        if (job.equals("Digital marketing specialist")) {
            mJob.setText(job);
        }
        if (job.equals("Web designer")) {
            mJob.setText(job);
        }
        if (job.equals("Mobile developer")) {
            mJob.setText(job);
        }
        if (job.equals("Game developer")) {
            mJob.setText(job);
        }
        if (job.equals("UX designer")) {
            mJob.setText(job);
        }
        if (job.equals("Network administrator")) {
            mJob.setText(job);
        }
        
        // REMOTE COUNTRY FLAGS
        if (remote.equals("Canada")) {
            remote_country_flag.setImageResource(R.mipmap.canada);
            rc_flag_salary.setImageResource(R.mipmap.canada);
        }
        if (remote.equals("United States")) {
            remote_country_flag.setImageResource(R.mipmap.usa);
            rc_flag_salary.setImageResource(R.mipmap.usa);
        }
        if (remote.equals("Mexico")) {
            remote_country_flag.setImageResource(R.mipmap.mexico);
            rc_flag_salary.setImageResource(R.mipmap.mexico);
        }
        if (remote.equals("Brazil")) {
            remote_country_flag.setImageResource(R.mipmap.brazil);
            rc_flag_salary.setImageResource(R.mipmap.brazil);
        }
        if (remote.equals("Germany")) {
            remote_country_flag.setImageResource(R.mipmap.germany);
            rc_flag_salary.setImageResource(R.mipmap.germany);
        }
        if (remote.equals("Japan")) {
            remote_country_flag.setImageResource(R.mipmap.japan);
            rc_flag_salary.setImageResource(R.mipmap.japan);
        }
        if (remote.equals("Nigeria")) {
            remote_country_flag.setImageResource(R.mipmap.nigeria);
            rc_flag_salary.setImageResource(R.mipmap.nigeria);
        }
        if (remote.equals("Poland")) {
            remote_country_flag.setImageResource(R.mipmap.poland);
            rc_flag_salary.setImageResource(R.mipmap.poland);
        }
        if (remote.equals("England")) {
            remote_country_flag.setImageResource(R.mipmap.england);
            rc_flag_salary.setImageResource(R.mipmap.england);
        }
        if (remote.equals("South Korea")) {
            remote_country_flag.setImageResource(R.mipmap.south_korea);
            rc_flag_salary.setImageResource(R.mipmap.south_korea);
        }

        // CONVERSION RATE TEXT
        if (home.equals("Canada") && remote.equals("United States") || home.equals("United States") && remote.equals("Canada")) {
            mRateText.setText(rates_array[0]);
        }
        if (home.equals("Canada") && remote.equals("Mexico") || home.equals("Mexico") && remote.equals("Canada")) {
            mRateText.setText(rates_array[1]);
        }
        if (home.equals("Canada") && remote.equals("Brazil") || home.equals("Brazil") && remote.equals("Canada")) {
            mRateText.setText(rates_array[2]);
        }
        if (home.equals("Canada") && remote.equals("Germany") || home.equals("Germany") && remote.equals("Canada")) {
            mRateText.setText(rates_array[3]);
        }
        if (home.equals("Canada") && remote.equals("Japan") || home.equals("Japan") && remote.equals("Canada")) {
            mRateText.setText(rates_array[4]);
        }
        if (home.equals("Canada") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("Canada")) {
            mRateText.setText(rates_array[5]);
        }
        if (home.equals("Canada") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Canada")) {
            mRateText.setText(rates_array[6]);
        }
        if (home.equals("Canada") && remote.equals("England") || home.equals("England") && remote.equals("Canada")) {
            mRateText.setText(rates_array[7]);
        }
        if (home.equals("Canada") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Canada")) {
            mRateText.setText(rates_array[8]);
        }
        if (home.equals("United States") && remote.equals("Mexico") || home.equals("Mexico") && remote.equals("United States")) {
            mRateText.setText(rates_array[9]);
        }
        if (home.equals("United States") && remote.equals("Brazil") || home.equals("Brazil") && remote.equals("United States")) {
            mRateText.setText(rates_array[10]);
        }
        if (home.equals("United States") && remote.equals("Germany") || home.equals("Germany") && remote.equals("United States")) {
            mRateText.setText(rates_array[11]);
        }
        if (home.equals("United States") && remote.equals("Japan") || home.equals("Japan") && remote.equals("United States")) {
            mRateText.setText(rates_array[12]);
        }
        if (home.equals("United States") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("United States")) {
            mRateText.setText(rates_array[13]);
        }
        if (home.equals("United States") && remote.equals("Poland") || home.equals("Poland") && remote.equals("United States")) {
            mRateText.setText(rates_array[14]);
        }
        if (home.equals("United States") && remote.equals("England") || home.equals("England") && remote.equals("United States")) {
            mRateText.setText(rates_array[15]);
        }
        if (home.equals("United States") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("United States")) {
            mRateText.setText(rates_array[16]);
        }
        if (home.equals("Mexico") && remote.equals("Brazil") || home.equals("Brazil") && remote.equals("Mexico")) {
            mRateText.setText(rates_array[17]);
        }
        if (home.equals("Mexico") && remote.equals("Germany") || home.equals("Germany") && remote.equals("Mexico")) {
            mRateText.setText(rates_array[18]);
        }
        if (home.equals("Mexico") && remote.equals("Japan") || home.equals("Japan") && remote.equals("Mexico")) {
            mRateText.setText(rates_array[19]);
        }
        if (home.equals("Mexico") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("Mexico")) {
            mRateText.setText(rates_array[20]);
        }
        if (home.equals("Mexico") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Mexico")) {
            mRateText.setText(rates_array[21]);
        }
        if (home.equals("Mexico") && remote.equals("England") || home.equals("England") && remote.equals("Mexico")) {
            mRateText.setText(rates_array[22]);
        }
        if (home.equals("Mexico") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Mexico")) {
            mRateText.setText(rates_array[23]);
        }
        if (home.equals("Brazil") && remote.equals("Germany") || home.equals("Germany") && remote.equals("Brazil")) {
            mRateText.setText(rates_array[24]);
        }
        if (home.equals("Brazil") && remote.equals("Japan") || home.equals("Japan") && remote.equals("Brazil")) {
            mRateText.setText(rates_array[25]);
        }
        if (home.equals("Brazil") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("Brazil")) {
            mRateText.setText(rates_array[26]);
        }
        if (home.equals("Brazil") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Brazil")) {
            mRateText.setText(rates_array[27]);
        }
        if (home.equals("Brazil") && remote.equals("England") || home.equals("England") && remote.equals("Brazil")) {
            mRateText.setText(rates_array[28]);
        }
        if (home.equals("Brazil") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Brazil")) {
            mRateText.setText(rates_array[29]);
        }
        if (home.equals("Germany") && remote.equals("Japan") || home.equals("Japan") && remote.equals("Germany")) {
            mRateText.setText(rates_array[30]);
        }
        if (home.equals("Germany") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("Germany")) {
            mRateText.setText(rates_array[31]);
        }
        if (home.equals("Germany") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Germany")) {
            mRateText.setText(rates_array[32]);
        }
        if (home.equals("Germany") && remote.equals("England") || home.equals("England") && remote.equals("Germany")) {
            mRateText.setText(rates_array[33]);
        }
        if (home.equals("Germany") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Germany")) {
            mRateText.setText(rates_array[34]);
        }
        if (home.equals("Japan") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("Japan")) {
            mRateText.setText(rates_array[35]);
        }
        if (home.equals("Japan") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Japan")) {
            mRateText.setText(rates_array[36]);
        }
        if (home.equals("Japan") && remote.equals("England") || home.equals("England") && remote.equals("Japan")) {
            mRateText.setText(rates_array[37]);
        }
        if (home.equals("Japan") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Japan")) {
            mRateText.setText(rates_array[38]);
        }
        if (home.equals("Nigeria") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Nigeria")) {
            mRateText.setText(rates_array[39]);
        }
        if (home.equals("Nigeria") && remote.equals("England") || home.equals("England") && remote.equals("Nigeria")) {
            mRateText.setText(rates_array[40]);
        }
        if (home.equals("Nigeria") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Nigeria")) {
            mRateText.setText(rates_array[41]);
        }
        if (home.equals("Poland") && remote.equals("England") || home.equals("England") && remote.equals("Poland")) {
            mRateText.setText(rates_array[42]);
        }
        if (home.equals("Poland") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Poland")) {
            mRateText.setText(rates_array[43]);
        }
        if (home.equals("England") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("England")) {
            mRateText.setText(rates_array[44]);
        }

        //  AVERAGE SALARY TEXT

        // CANADA
        if (home.equals("Canada") && job.equals("Software engineer")) {
            mHc_salary.setText(avg_salary_can[0]);
        }
        if (remote.equals("Canada") && job.equals("Software engineer")) {
            mRc_salary.setText(avg_salary_can[0]);
        }
        if (home.equals("Canada") && job.equals("Software tester")) {
            mHc_salary.setText(avg_salary_can[1]);
        }
        if (remote.equals("Canada") && job.equals("Software tester")) {
            mRc_salary.setText(avg_salary_can[1]);
        }
        if (home.equals("Canada") && job.equals("UI designer")) {
            mHc_salary.setText(avg_salary_can[2]);
        }
        if (remote.equals("Canada") && job.equals("UI designer")) {
            mRc_salary.setText(avg_salary_can[2]);
        }
        if (home.equals("Canada") && job.equals("Web developer")) {
            mHc_salary.setText(avg_salary_can[3]);
        }
        if (remote.equals("Canada") && job.equals("Web developer")) {
            mRc_salary.setText(avg_salary_can[3]);
        }
        if (home.equals("Canada") && job.equals("Digital marketing specialist")) {
            mHc_salary.setText(avg_salary_can[4]);
        }
        if (remote.equals("Canada") && job.equals("Digital marketing specialist")) {
            mRc_salary.setText(avg_salary_can[4]);
        }
        if (home.equals("Canada") && job.equals("Web designer")) {
            mHc_salary.setText(avg_salary_can[5]);
        }
        if (remote.equals("Canada") && job.equals("Web designer")) {
            mRc_salary.setText(avg_salary_can[5]);
        }
        if (home.equals("Canada") && job.equals("Mobile developer")) {
            mHc_salary.setText(avg_salary_can[6]);
        }
        if (remote.equals("Canada") && job.equals("Mobile developer")) {
            mRc_salary.setText(avg_salary_can[6]);
        }
        if (home.equals("Canada") && job.equals("Game developer")) {
            mHc_salary.setText(avg_salary_can[7]);
        }
        if (remote.equals("Canada") && job.equals("Game developer")) {
            mRc_salary.setText(avg_salary_can[7]);
        }
        if (home.equals("Canada") && job.equals("UX designer")) {
            mHc_salary.setText(avg_salary_can[8]);
        }
        if (remote.equals("Canada") && job.equals("UX designer")) {
            mRc_salary.setText(avg_salary_can[8]);
        }
        if (home.equals("Canada") && job.equals("Network administrator")) {
            mHc_salary.setText(avg_salary_can[9]);
        }
        if (remote.equals("Canada") && job.equals("Network administrator")) {
            mRc_salary.setText(avg_salary_can[9]);
        }

        // UNITED STATES
        if (home.equals("United States") && job.equals("Software engineer")) {
            mHc_salary.setText(avg_salary_usa[0]);
        }
        if (remote.equals("United States") && job.equals("Software engineer")) {
            mRc_salary.setText(avg_salary_usa[0]);
        }
        if (home.equals("United States") && job.equals("Software tester")) {
            mHc_salary.setText(avg_salary_usa[1]);
        }
        if (remote.equals("United States") && job.equals("Software tester")) {
            mRc_salary.setText(avg_salary_usa[1]);
        }
        if (home.equals("United States") && job.equals("UI designer")) {
            mHc_salary.setText(avg_salary_usa[2]);
        }
        if (remote.equals("United States") && job.equals("UI designer")) {
            mRc_salary.setText(avg_salary_usa[2]);
        }
        if (home.equals("United States") && job.equals("Web developer")) {
            mHc_salary.setText(avg_salary_usa[3]);
        }
        if (remote.equals("United States") && job.equals("Web developer")) {
            mRc_salary.setText(avg_salary_usa[3]);
        }
        if (home.equals("United States") && job.equals("Digital marketing specialist")) {
            mHc_salary.setText(avg_salary_usa[4]);
        }
        if (remote.equals("United States") && job.equals("Digital marketing specialist")) {
            mRc_salary.setText(avg_salary_usa[4]);
        }
        if (home.equals("United States") && job.equals("Web designer")) {
            mHc_salary.setText(avg_salary_usa[5]);
        }
        if (remote.equals("United States") && job.equals("Web designer")) {
            mRc_salary.setText(avg_salary_usa[5]);
        }
        if (home.equals("United States") && job.equals("Mobile developer")) {
            mHc_salary.setText(avg_salary_usa[6]);
        }
        if (remote.equals("United States") && job.equals("Mobile developer")) {
            mRc_salary.setText(avg_salary_usa[6]);
        }
        if (home.equals("United States") && job.equals("Game developer")) {
            mHc_salary.setText(avg_salary_usa[7]);
        }
        if (remote.equals("United States") && job.equals("Game developer")) {
            mRc_salary.setText(avg_salary_usa[7]);
        }
        if (home.equals("United States") && job.equals("UX designer")) {
            mHc_salary.setText(avg_salary_usa[8]);
        }
        if (remote.equals("United States") && job.equals("UX designer")) {
            mRc_salary.setText(avg_salary_usa[8]);
        }
        if (home.equals("United States") && job.equals("Network administrator")) {
            mHc_salary.setText(avg_salary_usa[9]);
        }
        if (remote.equals("United States") && job.equals("Network administrator")) {
            mRc_salary.setText(avg_salary_usa[9]);
        }

        // MEXICO
        if (home.equals("Mexico") && job.equals("Software engineer")) {
            mHc_salary.setText(avg_salary_mex[0]);
        }
        if (remote.equals("Mexico") && job.equals("Software engineer")) {
            mRc_salary.setText(avg_salary_mex[0]);
        }
        if (home.equals("Mexico") && job.equals("Software tester")) {
            mHc_salary.setText(avg_salary_mex[1]);
        }
        if (remote.equals("Mexico") && job.equals("Software tester")) {
            mRc_salary.setText(avg_salary_mex[1]);
        }
        if (home.equals("Mexico") && job.equals("UI designer")) {
            mHc_salary.setText(avg_salary_mex[2]);
        }
        if (remote.equals("Mexico") && job.equals("UI designer")) {
            mRc_salary.setText(avg_salary_mex[2]);
        }
        if (home.equals("Mexico") && job.equals("Web developer")) {
            mHc_salary.setText(avg_salary_mex[3]);
        }
        if (remote.equals("Mexico") && job.equals("Web developer")) {
            mRc_salary.setText(avg_salary_mex[3]);
        }
        if (home.equals("Mexico") && job.equals("Digital marketing specialist")) {
            mHc_salary.setText(avg_salary_mex[4]);
        }
        if (remote.equals("Mexico") && job.equals("Digital marketing specialist")) {
            mRc_salary.setText(avg_salary_mex[4]);
        }
        if (home.equals("Mexico") && job.equals("Web designer")) {
            mHc_salary.setText(avg_salary_mex[5]);
        }
        if (remote.equals("Mexico") && job.equals("Web designer")) {
            mRc_salary.setText(avg_salary_mex[5]);
        }
        if (home.equals("Mexico") && job.equals("Mobile developer")) {
            mHc_salary.setText(avg_salary_mex[6]);
        }
        if (remote.equals("Mexico") && job.equals("Mobile developer")) {
            mRc_salary.setText(avg_salary_mex[6]);
        }
        if (home.equals("Mexico") && job.equals("Game developer")) {
            mHc_salary.setText(avg_salary_mex[7]);
        }
        if (remote.equals("Mexico") && job.equals("Game developer")) {
            mRc_salary.setText(avg_salary_mex[7]);
        }
        if (home.equals("Mexico") && job.equals("UX designer")) {
            mHc_salary.setText(avg_salary_mex[8]);
        }
        if (remote.equals("Mexico") && job.equals("UX designer")) {
            mRc_salary.setText(avg_salary_mex[8]);
        }
        if (home.equals("Mexico") && job.equals("Network administrator")) {
            mHc_salary.setText(avg_salary_mex[9]);
        }
        if (remote.equals("Mexico") && job.equals("Network administrator")) {
            mRc_salary.setText(avg_salary_mex[9]);
        }

        // BRAZIL
        if (home.equals("Brazil") && job.equals("Software engineer")) {
            mHc_salary.setText(avg_salary_bra[0]);
        }
        if (remote.equals("Brazil") && job.equals("Software engineer")) {
            mRc_salary.setText(avg_salary_bra[0]);
        }
        if (home.equals("Brazil") && job.equals("Software tester")) {
            mHc_salary.setText(avg_salary_bra[1]);
        }
        if (remote.equals("Brazil") && job.equals("Software tester")) {
            mRc_salary.setText(avg_salary_bra[1]);
        }
        if (home.equals("Brazil") && job.equals("UI designer")) {
            mHc_salary.setText(avg_salary_bra[2]);
        }
        if (remote.equals("Brazil") && job.equals("UI designer")) {
            mRc_salary.setText(avg_salary_bra[2]);
        }
        if (home.equals("Brazil") && job.equals("Web developer")) {
            mHc_salary.setText(avg_salary_bra[3]);
        }
        if (remote.equals("Brazil") && job.equals("Web developer")) {
            mRc_salary.setText(avg_salary_bra[3]);
        }
        if (home.equals("Brazil") && job.equals("Digital marketing specialist")) {
            mHc_salary.setText(avg_salary_bra[4]);
        }
        if (remote.equals("Brazil") && job.equals("Digital marketing specialist")) {
            mRc_salary.setText(avg_salary_bra[4]);
        }
        if (home.equals("Brazil") && job.equals("Web designer")) {
            mHc_salary.setText(avg_salary_bra[5]);
        }
        if (remote.equals("Brazil") && job.equals("Web designer")) {
            mRc_salary.setText(avg_salary_bra[5]);
        }
        if (home.equals("Brazil") && job.equals("Mobile developer")) {
            mHc_salary.setText(avg_salary_bra[6]);
        }
        if (remote.equals("Brazil") && job.equals("Mobile developer")) {
            mRc_salary.setText(avg_salary_bra[6]);
        }
        if (home.equals("Brazil") && job.equals("Game developer")) {
            mHc_salary.setText(avg_salary_bra[7]);
        }
        if (remote.equals("Brazil") && job.equals("Game developer")) {
            mRc_salary.setText(avg_salary_bra[7]);
        }
        if (home.equals("Brazil") && job.equals("UX designer")) {
            mHc_salary.setText(avg_salary_bra[8]);
        }
        if (remote.equals("Brazil") && job.equals("UX designer")) {
            mRc_salary.setText(avg_salary_bra[8]);
        }
        if (home.equals("Brazil") && job.equals("Network administrator")) {
            mHc_salary.setText(avg_salary_bra[9]);
        }
        if (remote.equals("Brazil") && job.equals("Network administrator")) {
            mRc_salary.setText(avg_salary_bra[9]);
        }

        // GERMANY
        if (home.equals("Germany") && job.equals("Software engineer")) {
            mHc_salary.setText(avg_salary_ger[0]);
        }
        if (remote.equals("Germany") && job.equals("Software engineer")) {
            mRc_salary.setText(avg_salary_ger[0]);
        }
        if (home.equals("Germany") && job.equals("Software tester")) {
            mHc_salary.setText(avg_salary_ger[1]);
        }
        if (remote.equals("Germany") && job.equals("Software tester")) {
            mRc_salary.setText(avg_salary_ger[1]);
        }
        if (home.equals("Germany") && job.equals("UI designer")) {
            mHc_salary.setText(avg_salary_ger[2]);
        }
        if (remote.equals("Germany") && job.equals("UI designer")) {
            mRc_salary.setText(avg_salary_ger[2]);
        }
        if (home.equals("Germany") && job.equals("Web developer")) {
            mHc_salary.setText(avg_salary_ger[3]);
        }
        if (remote.equals("Germany") && job.equals("Web developer")) {
            mRc_salary.setText(avg_salary_ger[3]);
        }
        if (home.equals("Germany") && job.equals("Digital marketing specialist")) {
            mHc_salary.setText(avg_salary_ger[4]);
        }
        if (remote.equals("Germany") && job.equals("Digital marketing specialist")) {
            mRc_salary.setText(avg_salary_ger[4]);
        }
        if (home.equals("Germany") && job.equals("Web designer")) {
            mHc_salary.setText(avg_salary_ger[5]);
        }
        if (remote.equals("Germany") && job.equals("Web designer")) {
            mRc_salary.setText(avg_salary_ger[5]);
        }
        if (home.equals("Germany") && job.equals("Mobile developer")) {
            mHc_salary.setText(avg_salary_ger[6]);
        }
        if (remote.equals("Germany") && job.equals("Mobile developer")) {
            mRc_salary.setText(avg_salary_ger[6]);
        }
        if (home.equals("Germany") && job.equals("Game developer")) {
            mHc_salary.setText(avg_salary_ger[7]);
        }
        if (remote.equals("Germany") && job.equals("Game developer")) {
            mRc_salary.setText(avg_salary_ger[7]);
        }
        if (home.equals("Germany") && job.equals("UX designer")) {
            mHc_salary.setText(avg_salary_ger[8]);
        }
        if (remote.equals("Germany") && job.equals("UX designer")) {
            mRc_salary.setText(avg_salary_ger[8]);
        }
        if (home.equals("Germany") && job.equals("Network administrator")) {
            mHc_salary.setText(avg_salary_ger[9]);
        }
        if (remote.equals("Germany") && job.equals("Network administrator")) {
            mRc_salary.setText(avg_salary_ger[9]);
        }

        // JAPAN
        if (home.equals("Japan") && job.equals("Software engineer")) {
            mHc_salary.setText(avg_salary_jap[0]);
        }
        if (remote.equals("Japan") && job.equals("Software engineer")) {
            mRc_salary.setText(avg_salary_jap[0]);
        }
        if (home.equals("Japan") && job.equals("Software tester")) {
            mHc_salary.setText(avg_salary_jap[1]);
        }
        if (remote.equals("Japan") && job.equals("Software tester")) {
            mRc_salary.setText(avg_salary_jap[1]);
        }
        if (home.equals("Japan") && job.equals("UI designer")) {
            mHc_salary.setText(avg_salary_jap[2]);
        }
        if (remote.equals("Japan") && job.equals("UI designer")) {
            mRc_salary.setText(avg_salary_jap[2]);
        }
        if (home.equals("Japan") && job.equals("Web developer")) {
            mHc_salary.setText(avg_salary_jap[3]);
        }
        if (remote.equals("Japan") && job.equals("Web developer")) {
            mRc_salary.setText(avg_salary_jap[3]);
        }
        if (home.equals("Japan") && job.equals("Digital marketing specialist")) {
            mHc_salary.setText(avg_salary_jap[4]);
        }
        if (remote.equals("Japan") && job.equals("Digital marketing specialist")) {
            mRc_salary.setText(avg_salary_jap[4]);
        }
        if (home.equals("Japan") && job.equals("Web designer")) {
            mHc_salary.setText(avg_salary_jap[5]);
        }
        if (remote.equals("Japan") && job.equals("Web designer")) {
            mRc_salary.setText(avg_salary_jap[5]);
        }
        if (home.equals("Japan") && job.equals("Mobile developer")) {
            mHc_salary.setText(avg_salary_jap[6]);
        }
        if (remote.equals("Japan") && job.equals("Mobile developer")) {
            mRc_salary.setText(avg_salary_jap[6]);
        }
        if (home.equals("Japan") && job.equals("Game developer")) {
            mHc_salary.setText(avg_salary_jap[7]);
        }
        if (remote.equals("Japan") && job.equals("Game developer")) {
            mRc_salary.setText(avg_salary_jap[7]);
        }
        if (home.equals("Japan") && job.equals("UX designer")) {
            mHc_salary.setText(avg_salary_jap[8]);
        }
        if (remote.equals("Japan") && job.equals("UX designer")) {
            mRc_salary.setText(avg_salary_jap[8]);
        }
        if (home.equals("Japan") && job.equals("Network administrator")) {
            mHc_salary.setText(avg_salary_jap[9]);
        }
        if (remote.equals("Japan") && job.equals("Network administrator")) {
            mRc_salary.setText(avg_salary_jap[9]);
        }

        // NIGERIA
        if (home.equals("Nigeria") && job.equals("Software engineer")) {
            mHc_salary.setText(avg_salary_nig[0]);
        }
        if (remote.equals("Nigeria") && job.equals("Software engineer")) {
            mRc_salary.setText(avg_salary_nig[0]);
        }
        if (home.equals("Nigeria") && job.equals("Software tester")) {
            mHc_salary.setText(avg_salary_nig[1]);
        }
        if (remote.equals("Nigeria") && job.equals("Software tester")) {
            mRc_salary.setText(avg_salary_nig[1]);
        }
        if (home.equals("Nigeria") && job.equals("UI designer")) {
            mHc_salary.setText(avg_salary_nig[2]);
        }
        if (remote.equals("Nigeria") && job.equals("UI designer")) {
            mRc_salary.setText(avg_salary_nig[2]);
        }
        if (home.equals("Nigeria") && job.equals("Web developer")) {
            mHc_salary.setText(avg_salary_nig[3]);
        }
        if (remote.equals("Nigeria") && job.equals("Web developer")) {
            mRc_salary.setText(avg_salary_nig[3]);
        }
        if (home.equals("Nigeria") && job.equals("Digital marketing specialist")) {
            mHc_salary.setText(avg_salary_nig[4]);
        }
        if (remote.equals("Nigeria") && job.equals("Digital marketing specialist")) {
            mRc_salary.setText(avg_salary_nig[4]);
        }
        if (home.equals("Nigeria") && job.equals("Web designer")) {
            mHc_salary.setText(avg_salary_nig[5]);
        }
        if (remote.equals("Nigeria") && job.equals("Web designer")) {
            mRc_salary.setText(avg_salary_nig[5]);
        }
        if (home.equals("Nigeria") && job.equals("Mobile developer")) {
            mHc_salary.setText(avg_salary_nig[6]);
        }
        if (remote.equals("Nigeria") && job.equals("Mobile developer")) {
            mRc_salary.setText(avg_salary_nig[6]);
        }
        if (home.equals("Nigeria") && job.equals("Game developer")) {
            mHc_salary.setText(avg_salary_nig[7]);
        }
        if (remote.equals("Nigeria") && job.equals("Game developer")) {
            mRc_salary.setText(avg_salary_nig[7]);
        }
        if (home.equals("Nigeria") && job.equals("UX designer")) {
            mHc_salary.setText(avg_salary_nig[8]);
        }
        if (remote.equals("Nigeria") && job.equals("UX designer")) {
            mRc_salary.setText(avg_salary_nig[8]);
        }
        if (home.equals("Nigeria") && job.equals("Network administrator")) {
            mHc_salary.setText(avg_salary_nig[9]);
        }
        if (remote.equals("Nigeria") && job.equals("Network administrator")) {
            mRc_salary.setText(avg_salary_nig[9]);
        }

        // POLAND
        if (home.equals("Poland") && job.equals("Software engineer")) {
            mHc_salary.setText(avg_salary_pol[0]);
        }
        if (remote.equals("Poland") && job.equals("Software engineer")) {
            mRc_salary.setText(avg_salary_pol[0]);
        }
        if (home.equals("Poland") && job.equals("Software tester")) {
            mHc_salary.setText(avg_salary_pol[1]);
        }
        if (remote.equals("Poland") && job.equals("Software tester")) {
            mRc_salary.setText(avg_salary_pol[1]);
        }
        if (home.equals("Poland") && job.equals("UI designer")) {
            mHc_salary.setText(avg_salary_pol[2]);
        }
        if (remote.equals("Poland") && job.equals("UI designer")) {
            mRc_salary.setText(avg_salary_pol[2]);
        }
        if (home.equals("Poland") && job.equals("Web developer")) {
            mHc_salary.setText(avg_salary_pol[3]);
        }
        if (remote.equals("Poland") && job.equals("Web developer")) {
            mRc_salary.setText(avg_salary_pol[3]);
        }
        if (home.equals("Poland") && job.equals("Digital marketing specialist")) {
            mHc_salary.setText(avg_salary_pol[4]);
        }
        if (remote.equals("Poland") && job.equals("Digital marketing specialist")) {
            mRc_salary.setText(avg_salary_pol[4]);
        }
        if (home.equals("Poland") && job.equals("Web designer")) {
            mHc_salary.setText(avg_salary_pol[5]);
        }
        if (remote.equals("Poland") && job.equals("Web designer")) {
            mRc_salary.setText(avg_salary_pol[5]);
        }
        if (home.equals("Poland") && job.equals("Mobile developer")) {
            mHc_salary.setText(avg_salary_pol[6]);
        }
        if (remote.equals("Poland") && job.equals("Mobile developer")) {
            mRc_salary.setText(avg_salary_pol[6]);
        }
        if (home.equals("Poland") && job.equals("Game developer")) {
            mHc_salary.setText(avg_salary_pol[7]);
        }
        if (remote.equals("Poland") && job.equals("Game developer")) {
            mRc_salary.setText(avg_salary_pol[7]);
        }
        if (home.equals("Poland") && job.equals("UX designer")) {
            mHc_salary.setText(avg_salary_pol[8]);
        }
        if (remote.equals("Poland") && job.equals("UX designer")) {
            mRc_salary.setText(avg_salary_pol[8]);
        }
        if (home.equals("Poland") && job.equals("Network administrator")) {
            mHc_salary.setText(avg_salary_pol[9]);
        }
        if (remote.equals("Poland") && job.equals("Network administrator")) {
            mRc_salary.setText(avg_salary_pol[9]);
        }

        // ENGLAND
        if (home.equals("England") && job.equals("Software engineer")) {
            mHc_salary.setText(avg_salary_eng[0]);
        }
        if (remote.equals("England") && job.equals("Software engineer")) {
            mRc_salary.setText(avg_salary_eng[0]);
        }
        if (home.equals("England") && job.equals("Software tester")) {
            mHc_salary.setText(avg_salary_eng[1]);
        }
        if (remote.equals("England") && job.equals("Software tester")) {
            mRc_salary.setText(avg_salary_eng[1]);
        }
        if (home.equals("England") && job.equals("UI designer")) {
            mHc_salary.setText(avg_salary_eng[2]);
        }
        if (remote.equals("England") && job.equals("UI designer")) {
            mRc_salary.setText(avg_salary_eng[2]);
        }
        if (home.equals("England") && job.equals("Web developer")) {
            mHc_salary.setText(avg_salary_eng[3]);
        }
        if (remote.equals("England") && job.equals("Web developer")) {
            mRc_salary.setText(avg_salary_eng[3]);
        }
        if (home.equals("England") && job.equals("Digital marketing specialist")) {
            mHc_salary.setText(avg_salary_eng[4]);
        }
        if (remote.equals("England") && job.equals("Digital marketing specialist")) {
            mRc_salary.setText(avg_salary_eng[4]);
        }
        if (home.equals("England") && job.equals("Web designer")) {
            mHc_salary.setText(avg_salary_eng[5]);
        }
        if (remote.equals("England") && job.equals("Web designer")) {
            mRc_salary.setText(avg_salary_eng[5]);
        }
        if (home.equals("England") && job.equals("Mobile developer")) {
            mHc_salary.setText(avg_salary_eng[6]);
        }
        if (remote.equals("England") && job.equals("Mobile developer")) {
            mRc_salary.setText(avg_salary_eng[6]);
        }
        if (home.equals("England") && job.equals("Game developer")) {
            mHc_salary.setText(avg_salary_eng[7]);
        }
        if (remote.equals("England") && job.equals("Game developer")) {
            mRc_salary.setText(avg_salary_eng[7]);
        }
        if (home.equals("England") && job.equals("UX designer")) {
            mHc_salary.setText(avg_salary_eng[8]);
        }
        if (remote.equals("England") && job.equals("UX designer")) {
            mRc_salary.setText(avg_salary_eng[8]);
        }
        if (home.equals("England") && job.equals("Network administrator")) {
            mHc_salary.setText(avg_salary_eng[9]);
        }
        if (remote.equals("England") && job.equals("Network administrator")) {
            mRc_salary.setText(avg_salary_eng[9]);
        }

        // SOUTH KOREA
        if (home.equals("South Korea") && job.equals("Software engineer")) {
            mHc_salary.setText(avg_salary_kor[0]);
        }
        if (remote.equals("South Korea") && job.equals("Software engineer")) {
            mRc_salary.setText(avg_salary_kor[0]);
        }
        if (home.equals("South Korea") && job.equals("Software tester")) {
            mHc_salary.setText(avg_salary_kor[1]);
        }
        if (remote.equals("South Korea") && job.equals("Software tester")) {
            mRc_salary.setText(avg_salary_kor[1]);
        }
        if (home.equals("South Korea") && job.equals("UI designer")) {
            mHc_salary.setText(avg_salary_kor[2]);
        }
        if (remote.equals("South Korea") && job.equals("UI designer")) {
            mRc_salary.setText(avg_salary_kor[2]);
        }
        if (home.equals("South Korea") && job.equals("Web developer")) {
            mHc_salary.setText(avg_salary_kor[3]);
        }
        if (remote.equals("South Korea") && job.equals("Web developer")) {
            mRc_salary.setText(avg_salary_kor[3]);
        }
        if (home.equals("South Korea") && job.equals("Digital marketing specialist")) {
            mHc_salary.setText(avg_salary_kor[4]);
        }
        if (remote.equals("South Korea") && job.equals("Digital marketing specialist")) {
            mRc_salary.setText(avg_salary_kor[4]);
        }
        if (home.equals("South Korea") && job.equals("Web designer")) {
            mHc_salary.setText(avg_salary_kor[5]);
        }
        if (remote.equals("South Korea") && job.equals("Web designer")) {
            mRc_salary.setText(avg_salary_kor[5]);
        }
        if (home.equals("South Korea") && job.equals("Mobile developer")) {
            mHc_salary.setText(avg_salary_kor[6]);
        }
        if (remote.equals("South Korea") && job.equals("Mobile developer")) {
            mRc_salary.setText(avg_salary_kor[6]);
        }
        if (home.equals("South Korea") && job.equals("Game developer")) {
            mHc_salary.setText(avg_salary_kor[7]);
        }
        if (remote.equals("South Korea") && job.equals("Game developer")) {
            mRc_salary.setText(avg_salary_kor[7]);
        }
        if (home.equals("South Korea") && job.equals("UX designer")) {
            mHc_salary.setText(avg_salary_kor[8]);
        }
        if (remote.equals("South Korea") && job.equals("UX designer")) {
            mRc_salary.setText(avg_salary_kor[8]);
        }
        if (home.equals("South Korea") && job.equals("Network administrator")) {
            mHc_salary.setText(avg_salary_kor[9]);
        }
        if (remote.equals("South Korea") && job.equals("Network administrator")) {
            mRc_salary.setText(avg_salary_kor[9]);
        }
    }
}
