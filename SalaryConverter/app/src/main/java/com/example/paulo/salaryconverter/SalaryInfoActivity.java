package com.example.paulo.salaryconverter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SalaryInfoActivity extends AppCompatActivity {
    private ArrayList<Country> mCountryBank = new ArrayList<>();
    private ArrayList<Job> mJobBank = new ArrayList<>();
    private ArrayList<Salary> mSalaryBank = new ArrayList<>();
    private PercentDifference percent = new PercentDifference();

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

        // Setting countries flags
        mCountryBank.add(new Country("Canada", R.mipmap.canada));
        mCountryBank.add(new Country("United States", R.mipmap.usa));
        mCountryBank.add(new Country("Mexico", R.mipmap.mexico));
        mCountryBank.add(new Country("Germany", R.mipmap.germany));
        mCountryBank.add(new Country("Japan", R.mipmap.japan));
        mCountryBank.add(new Country("Nigeria", R.mipmap.nigeria));
        mCountryBank.add(new Country("Poland", R.mipmap.poland));
        mCountryBank.add(new Country("England", R.mipmap.england));
        mCountryBank.add(new Country("South Korea", R.mipmap.south_korea));
        mCountryBank.add(new Country("Brazil", R.mipmap.brazil));

        for (int i = 0; i < mCountryBank.size(); i++) {
            Country country = mCountryBank.get(i);
            // SET HOME COUNTRY IMG
            if (country.getCountryName().equals(home)) {
                home_country_flag.setImageResource(country.getCountryImg());
                hc_flag_salary.setImageResource(country.getCountryImg());
            }
            // SET REMOTE COUNTRY IMG
            if (country.getCountryName().equals(remote)) {
                remote_country_flag.setImageResource(country.getCountryImg());
                rc_flag_salary.setImageResource(country.getCountryImg());
            }
        }

        // Setting job text
        mJobBank.add(new Job("Software engineer"));
        mJobBank.add(new Job("Software tester"));
        mJobBank.add(new Job("UI designer"));
        mJobBank.add(new Job("Web developer"));
        mJobBank.add(new Job("Digital marketing specialist"));
        mJobBank.add(new Job("Web designer"));
        mJobBank.add(new Job("Mobile developer"));
        mJobBank.add(new Job("Game developer"));
        mJobBank.add(new Job("UX designer"));
        mJobBank.add(new Job("Network administrator"));

        for (int j = 0; j < mJobBank.size(); j++) {
            Job searchJob = mJobBank.get(j);
            // SET JOB
            if (searchJob.getJobName().equals(job)) {
                mJob.setText(job);
            }
        }

        // Setting the average salary for each job depending on the country

        // CANADA
        mSalaryBank.add(new Salary(new Country("Canada", R.mipmap.canada), avg_salary_can[0], new Job("Software engineer")));
        mSalaryBank.add(new Salary(new Country("Canada", R.mipmap.canada), avg_salary_can[1], new Job("Software tester")));
        mSalaryBank.add(new Salary(new Country("Canada", R.mipmap.canada), avg_salary_can[2], new Job("UI designer")));
        mSalaryBank.add(new Salary(new Country("Canada", R.mipmap.canada), avg_salary_can[3], new Job("Web developer")));
        mSalaryBank.add(new Salary(new Country("Canada", R.mipmap.canada), avg_salary_can[4], new Job("Digital marketing specialist")));
        mSalaryBank.add(new Salary(new Country("Canada", R.mipmap.canada), avg_salary_can[5], new Job("Web designer")));
        mSalaryBank.add(new Salary(new Country("Canada", R.mipmap.canada), avg_salary_can[6], new Job("Mobile developer")));
        mSalaryBank.add(new Salary(new Country("Canada", R.mipmap.canada), avg_salary_can[7], new Job("Game developer")));
        mSalaryBank.add(new Salary(new Country("Canada", R.mipmap.canada), avg_salary_can[8], new Job("UX designer")));
        mSalaryBank.add(new Salary(new Country("Canada", R.mipmap.canada), avg_salary_can[9], new Job("Network administrator")));
        // UNITED STATES
        mSalaryBank.add(new Salary(new Country("United States", R.mipmap.usa), avg_salary_usa[0], new Job("Software engineer")));
        mSalaryBank.add(new Salary(new Country("United States", R.mipmap.usa), avg_salary_usa[1], new Job("Software tester")));
        mSalaryBank.add(new Salary(new Country("United States", R.mipmap.usa), avg_salary_usa[2], new Job("UI designer")));
        mSalaryBank.add(new Salary(new Country("United States", R.mipmap.usa), avg_salary_usa[3], new Job("Web developer")));
        mSalaryBank.add(new Salary(new Country("United States", R.mipmap.usa), avg_salary_usa[4], new Job("Digital marketing specialist")));
        mSalaryBank.add(new Salary(new Country("United States", R.mipmap.usa), avg_salary_usa[5], new Job("Web designer")));
        mSalaryBank.add(new Salary(new Country("United States", R.mipmap.usa), avg_salary_usa[6], new Job("Mobile developer")));
        mSalaryBank.add(new Salary(new Country("United States", R.mipmap.usa), avg_salary_usa[7], new Job("Game developer")));
        mSalaryBank.add(new Salary(new Country("United States", R.mipmap.usa), avg_salary_usa[8], new Job("UX designer")));
        mSalaryBank.add(new Salary(new Country("United States", R.mipmap.usa), avg_salary_usa[9], new Job("Network administrator")));
        // MEXICO
        mSalaryBank.add(new Salary(new Country("Mexico", R.mipmap.mexico), avg_salary_mex[0], new Job("Software engineer")));
        mSalaryBank.add(new Salary(new Country("Mexico", R.mipmap.mexico), avg_salary_mex[1], new Job("Software tester")));
        mSalaryBank.add(new Salary(new Country("Mexico", R.mipmap.mexico), avg_salary_mex[2], new Job("UI designer")));
        mSalaryBank.add(new Salary(new Country("Mexico", R.mipmap.mexico), avg_salary_mex[3], new Job("Web developer")));
        mSalaryBank.add(new Salary(new Country("Mexico", R.mipmap.mexico), avg_salary_mex[4], new Job("Digital marketing specialist")));
        mSalaryBank.add(new Salary(new Country("Mexico", R.mipmap.mexico), avg_salary_mex[5], new Job("Web designer")));
        mSalaryBank.add(new Salary(new Country("Mexico", R.mipmap.mexico), avg_salary_mex[6], new Job("Mobile developer")));
        mSalaryBank.add(new Salary(new Country("Mexico", R.mipmap.mexico), avg_salary_mex[7], new Job("Game developer")));
        mSalaryBank.add(new Salary(new Country("Mexico", R.mipmap.mexico), avg_salary_mex[8], new Job("UX designer")));
        mSalaryBank.add(new Salary(new Country("Mexico", R.mipmap.mexico), avg_salary_mex[9], new Job("Network administrator")));
        // BRAZIL
        mSalaryBank.add(new Salary(new Country("Brazil", R.mipmap.brazil), avg_salary_bra[0], new Job("Software engineer")));
        mSalaryBank.add(new Salary(new Country("Brazil", R.mipmap.brazil), avg_salary_bra[1], new Job("Software tester")));
        mSalaryBank.add(new Salary(new Country("Brazil", R.mipmap.brazil), avg_salary_bra[2], new Job("UI designer")));
        mSalaryBank.add(new Salary(new Country("Brazil", R.mipmap.brazil), avg_salary_bra[3], new Job("Web developer")));
        mSalaryBank.add(new Salary(new Country("Brazil", R.mipmap.brazil), avg_salary_bra[4], new Job("Digital marketing specialist")));
        mSalaryBank.add(new Salary(new Country("Brazil", R.mipmap.brazil), avg_salary_bra[5], new Job("Web designer")));
        mSalaryBank.add(new Salary(new Country("Brazil", R.mipmap.brazil), avg_salary_bra[6], new Job("Mobile developer")));
        mSalaryBank.add(new Salary(new Country("Brazil", R.mipmap.brazil), avg_salary_bra[7], new Job("Game developer")));
        mSalaryBank.add(new Salary(new Country("Brazil", R.mipmap.brazil), avg_salary_bra[8], new Job("UX designer")));
        mSalaryBank.add(new Salary(new Country("Brazil", R.mipmap.brazil), avg_salary_bra[9], new Job("Network administrator")));
        // GERMANY
        mSalaryBank.add(new Salary(new Country("Germany", R.mipmap.germany), avg_salary_ger[0], new Job("Software engineer")));
        mSalaryBank.add(new Salary(new Country("Germany", R.mipmap.germany), avg_salary_ger[1], new Job("Software tester")));
        mSalaryBank.add(new Salary(new Country("Germany", R.mipmap.germany), avg_salary_ger[2], new Job("UI designer")));
        mSalaryBank.add(new Salary(new Country("Germany", R.mipmap.germany), avg_salary_ger[3], new Job("Web developer")));
        mSalaryBank.add(new Salary(new Country("Germany", R.mipmap.germany), avg_salary_ger[4], new Job("Digital marketing specialist")));
        mSalaryBank.add(new Salary(new Country("Germany", R.mipmap.germany), avg_salary_ger[5], new Job("Web designer")));
        mSalaryBank.add(new Salary(new Country("Germany", R.mipmap.germany), avg_salary_ger[6], new Job("Mobile developer")));
        mSalaryBank.add(new Salary(new Country("Germany", R.mipmap.germany), avg_salary_ger[7], new Job("Game developer")));
        mSalaryBank.add(new Salary(new Country("Germany", R.mipmap.germany), avg_salary_ger[8], new Job("UX designer")));
        mSalaryBank.add(new Salary(new Country("Germany", R.mipmap.germany), avg_salary_ger[9], new Job("Network administrator")));
        // JAPAN
        mSalaryBank.add(new Salary(new Country("Japan", R.mipmap.japan), avg_salary_jap[0], new Job("Software engineer")));
        mSalaryBank.add(new Salary(new Country("Japan", R.mipmap.japan), avg_salary_jap[1], new Job("Software tester")));
        mSalaryBank.add(new Salary(new Country("Japan", R.mipmap.japan), avg_salary_jap[2], new Job("UI designer")));
        mSalaryBank.add(new Salary(new Country("Japan", R.mipmap.japan), avg_salary_jap[3], new Job("Web developer")));
        mSalaryBank.add(new Salary(new Country("Japan", R.mipmap.japan), avg_salary_jap[4], new Job("Digital marketing specialist")));
        mSalaryBank.add(new Salary(new Country("Japan", R.mipmap.japan), avg_salary_jap[5], new Job("Web designer")));
        mSalaryBank.add(new Salary(new Country("Japan", R.mipmap.japan), avg_salary_jap[6], new Job("Mobile developer")));
        mSalaryBank.add(new Salary(new Country("Japan", R.mipmap.japan), avg_salary_jap[7], new Job("Game developer")));
        mSalaryBank.add(new Salary(new Country("Japan", R.mipmap.japan), avg_salary_jap[8], new Job("UX designer")));
        mSalaryBank.add(new Salary(new Country("Japan", R.mipmap.japan), avg_salary_jap[9], new Job("Network administrator")));
        // NIGERIA
        mSalaryBank.add(new Salary(new Country("Nigeria", R.mipmap.nigeria), avg_salary_nig[0], new Job("Software engineer")));
        mSalaryBank.add(new Salary(new Country("Nigeria", R.mipmap.nigeria), avg_salary_nig[1], new Job("Software tester")));
        mSalaryBank.add(new Salary(new Country("Nigeria", R.mipmap.nigeria), avg_salary_nig[2], new Job("UI designer")));
        mSalaryBank.add(new Salary(new Country("Nigeria", R.mipmap.nigeria), avg_salary_nig[3], new Job("Web developer")));
        mSalaryBank.add(new Salary(new Country("Nigeria", R.mipmap.nigeria), avg_salary_nig[4], new Job("Digital marketing specialist")));
        mSalaryBank.add(new Salary(new Country("Nigeria", R.mipmap.nigeria), avg_salary_nig[5], new Job("Web designer")));
        mSalaryBank.add(new Salary(new Country("Nigeria", R.mipmap.nigeria), avg_salary_nig[6], new Job("Mobile developer")));
        mSalaryBank.add(new Salary(new Country("Nigeria", R.mipmap.nigeria), avg_salary_nig[7], new Job("Game developer")));
        mSalaryBank.add(new Salary(new Country("Nigeria", R.mipmap.nigeria), avg_salary_nig[8], new Job("UX designer")));
        mSalaryBank.add(new Salary(new Country("Nigeria", R.mipmap.nigeria), avg_salary_nig[9], new Job("Network administrator")));
        // POLAND
        mSalaryBank.add(new Salary(new Country("Poland", R.mipmap.poland), avg_salary_pol[0], new Job("Software engineer")));
        mSalaryBank.add(new Salary(new Country("Poland", R.mipmap.poland), avg_salary_pol[1], new Job("Software tester")));
        mSalaryBank.add(new Salary(new Country("Poland", R.mipmap.poland), avg_salary_pol[2], new Job("UI designer")));
        mSalaryBank.add(new Salary(new Country("Poland", R.mipmap.poland), avg_salary_pol[3], new Job("Web developer")));
        mSalaryBank.add(new Salary(new Country("Poland", R.mipmap.poland), avg_salary_pol[4], new Job("Digital marketing specialist")));
        mSalaryBank.add(new Salary(new Country("Poland", R.mipmap.poland), avg_salary_pol[5], new Job("Web designer")));
        mSalaryBank.add(new Salary(new Country("Poland", R.mipmap.poland), avg_salary_pol[6], new Job("Mobile developer")));
        mSalaryBank.add(new Salary(new Country("Poland", R.mipmap.poland), avg_salary_pol[7], new Job("Game developer")));
        mSalaryBank.add(new Salary(new Country("Poland", R.mipmap.poland), avg_salary_pol[8], new Job("UX designer")));
        mSalaryBank.add(new Salary(new Country("Poland", R.mipmap.poland), avg_salary_pol[9], new Job("Network administrator")));
        // ENGLAND
        mSalaryBank.add(new Salary(new Country("England", R.mipmap.england), avg_salary_eng[0], new Job("Software engineer")));
        mSalaryBank.add(new Salary(new Country("England", R.mipmap.england), avg_salary_eng[1], new Job("Software tester")));
        mSalaryBank.add(new Salary(new Country("England", R.mipmap.england), avg_salary_eng[2], new Job("UI designer")));
        mSalaryBank.add(new Salary(new Country("England", R.mipmap.england), avg_salary_eng[3], new Job("Web developer")));
        mSalaryBank.add(new Salary(new Country("England", R.mipmap.england), avg_salary_eng[4], new Job("Digital marketing specialist")));
        mSalaryBank.add(new Salary(new Country("England", R.mipmap.england), avg_salary_eng[5], new Job("Web designer")));
        mSalaryBank.add(new Salary(new Country("England", R.mipmap.england), avg_salary_eng[6], new Job("Mobile developer")));
        mSalaryBank.add(new Salary(new Country("England", R.mipmap.england), avg_salary_eng[7], new Job("Game developer")));
        mSalaryBank.add(new Salary(new Country("England", R.mipmap.england), avg_salary_eng[8], new Job("UX designer")));
        mSalaryBank.add(new Salary(new Country("England", R.mipmap.england), avg_salary_eng[9], new Job("Network administrator")));
        // SOUTH KOREA
        mSalaryBank.add(new Salary(new Country("South Korea", R.mipmap.south_korea), avg_salary_kor[0], new Job("Software engineer")));
        mSalaryBank.add(new Salary(new Country("South Korea", R.mipmap.south_korea), avg_salary_kor[1], new Job("Software tester")));
        mSalaryBank.add(new Salary(new Country("South Korea", R.mipmap.south_korea), avg_salary_kor[2], new Job("UI designer")));
        mSalaryBank.add(new Salary(new Country("South Korea", R.mipmap.south_korea), avg_salary_kor[3], new Job("Web developer")));
        mSalaryBank.add(new Salary(new Country("South Korea", R.mipmap.south_korea), avg_salary_kor[4], new Job("Digital marketing specialist")));
        mSalaryBank.add(new Salary(new Country("South Korea", R.mipmap.south_korea), avg_salary_kor[5], new Job("Web designer")));
        mSalaryBank.add(new Salary(new Country("South Korea", R.mipmap.south_korea), avg_salary_kor[6], new Job("Mobile developer")));
        mSalaryBank.add(new Salary(new Country("South Korea", R.mipmap.south_korea), avg_salary_kor[7], new Job("Game developer")));
        mSalaryBank.add(new Salary(new Country("South Korea", R.mipmap.south_korea), avg_salary_kor[8], new Job("UX designer")));
        mSalaryBank.add(new Salary(new Country("South Korea", R.mipmap.south_korea), avg_salary_kor[9], new Job("Network administrator")));

        for (int a = 0; a < mSalaryBank.size(); a++) {
            Salary averageSalary = mSalaryBank.get(a);
            if (averageSalary.salaryJobName.getJobName().equals(job)) {
                if (averageSalary.salaryCountry.getCountryName().equals(home)) {
                    // SET AVERAGE SALARY TEXT
                    mHc_salary.setText(mSalaryBank.get(a).getAverageSalary());
                }
                else if(averageSalary.salaryCountry.getCountryName().equals(remote)) {
                    // SET AVERAGE SALARY TEXT
                    mRc_salary.setText(mSalaryBank.get(a).getAverageSalary());
                }
            }
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



// PERCENT DIFFERENCE TEXT

        // SOFTWARE ENGINEER
        if (job.equals("Software engineer")) {

            // CANADA AS THE HOME COUNTRY
            if (home.equals("Canada")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(70009, 80825, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(70009, 255000, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(70009, 93893, 2.41, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(70009, 54000, 1.52, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(70009, 5250000, 81.98, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(70009, 3000000, 233.40, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(70009, 90500, 2.75, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(70009, 34142, 1.74, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(70009, 45000000, 829.34, home, remote));
                }
            }

            // UNITED STATES AS THE HOME COUNTRY
            if (home.equals("United States")) {
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(80825, 70009, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(80825, 255000, 18.68, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(80825, 93893, 3.25, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(80825, 54000, 1.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(80825, 5250000, 110.52, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(80825, 3000000, 314.75, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(80825, 90500, 3.71, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(80825, 34142, 1.29, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(80825, 45000000, 1118.40, home, remote));
                }
            }

            // MEXICO AS THE HOME COUNTRY
            if (home.equals("Mexico")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(255000, 80825, 18.68, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(255000, 70009, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(255000, 93893, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(255000, 54000, 21.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(255000, 5250000, 5.89, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(255000, 3000000, 16.80, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(255000, 90500, 5.05, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(255000, 34142, 24.11, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(255000, 45000000, 59.67, home, remote));
                }
            }

            // BRAZIL AS THE HOME COUNTRY
            if (home.equals("Brazil")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(93893, 80825, 3.25, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(93893, 70009, 2.41, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(93893, 255000, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(93893, 54000, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(93893, 5250000, 34.00, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(93893, 3000000, 96.96, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(93893, 90500, 1.14, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(93893, 34142, 4.18, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(93893, 45000000, 344.29, home, remote));
                }
            }

            // GERMANY AS THE HOME COUNTRY
            if (home.equals("Germany")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 80825, 1.13, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 70009, 1.52, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 255000, 21.13, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 93893, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 5250000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 3000000, 354.90, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 90500, 4.18, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(54000, 34142, 1.14, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 45000000, 1260.39, home, remote));
                }
            }

            // JAPAN AS THE HOME COUNTRY
            if (home.equals("Japan")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5250000, 80825, 110.52, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5250000, 70009, 81.98, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5250000, 255000, 5.89, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5250000, 93893, 34.00, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5250000, 54000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(5250000, 3000000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5250000, 90500, 29.74, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5250000, 34142, 142.12, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(5250000, 45000000, 10.12, home, remote));
                }
            }

            // NIGERIA AS THE HOME COUNTRY
            if (home.equals("Nigeria")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3000000, 80825, 314.75, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3000000, 70009, 233.40, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3000000, 255000, 16.80, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3000000, 93893, 96.96, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3000000, 54000, 354.90, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3000000, 5250000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3000000, 90500, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3000000, 34142, 404.99, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(3000000, 45000000, 3.55, home, remote));
                }
            }

            // POLAND AS THE HOME COUNTRY
            if (home.equals("Poland")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(90500, 80825, 3.71, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(90500, 70009, 2.75, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(90500, 255000, 5.05, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(90500, 93893, 1.14, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(90500, 54000, 4.18, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(90500, 5250000, 29.74, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(90500, 3000000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(90500, 34142, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(90500, 45000000, 301.16, home, remote));
                }
            }

            // ENGLAND AS THE HOME COUNTRY
            if (home.equals("England")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(34142, 80825, 1.29, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(34142, 70009, 1.74, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(34142, 255000, 24.11, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(34142, 93893, 4.18, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_home_more_valuable(34142, 54000, 1.14, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(34142, 5250000, 142.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(34142, 3000000, 404.99, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(34142, 90500, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(34142, 45000000, 1438.08, home, remote));
                }
            }

            // SOUTH KOREA AS THE HOME COUNTRY
            if (home.equals("South Korea")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(45000000, 80825, 1118.40, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(45000000, 70009, 829.34, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(45000000, 255000, 59.67, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(45000000, 93893, 344.29, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(45000000, 54000, 1260.39, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(45000000, 5250000, 10.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_remote_more_valuable(45000000, 3000000, 3.55, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(45000000, 90500, 301.16, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(45000000, 34142, 1438.08, home, remote));
                }
            }
        }


//======================================================================================================================


        // SOFTWARE TESTER
        if (job.equals("Software tester")) {

            // CANADA AS THE HOME COUNTRY
            if (home.equals("Canada")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(52052, 55678, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(52052, 276500, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(52052, 78657, 2.41, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(52052, 49000, 1.52, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(52052, 3700000, 81.98, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(52052, 2000000, 233.40, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(52052, 50000, 2.75, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(52052, 24950, 1.74, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(52052, 40000000, 829.34, home, remote));
                }
            }

            // UNITED STATES AS THE HOME COUNTRY
            if (home.equals("United States")) {
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(55678, 52052, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(55678, 276500, 18.68, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(55678, 78657, 3.25, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(55678, 49000, 1.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(55678, 3700000, 110.52, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(55678, 2000000, 314.75, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(55678, 50000, 3.71, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(55678, 24950, 1.29, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(55678, 40000000, 1118.40, home, remote));
                }
            }

            // MEXICO AS THE HOME COUNTRY
            if (home.equals("Mexico")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(276500, 55678, 18.68, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(276500, 52052, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(276500, 78657, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(276500, 49000, 21.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(276500, 3700000, 5.89, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(276500, 2000000, 16.80, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(276500, 50000, 5.05, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(276500, 24950, 24.11, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(276500, 40000000, 59.67, home, remote));
                }
            }

            // BRAZIL AS THE HOME COUNTRY
            if (home.equals("Brazil")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(78657, 55678, 3.25, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(78657, 52052, 2.41, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(78657, 276500, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(78657, 49000, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(78657, 3700000, 34.00, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(78657, 2000000, 96.96, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(78657, 50000, 1.14, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(78657, 24950, 4.18, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(78657, 40000000, 344.29, home, remote));
                }
            }

            // GERMANY AS THE HOME COUNTRY
            if (home.equals("Germany")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 55678, 1.13, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 52052, 1.52, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 276500, 21.13, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 78657, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 3700000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 2000000, 354.90, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 50000, 4.18, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49000, 24950, 1.14, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 40000000, 1260.39, home, remote));
                }
            }

            // JAPAN AS THE HOME COUNTRY
            if (home.equals("Japan")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3700000, 55678, 110.52, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3700000, 52052, 81.98, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3700000, 276500, 5.89, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3700000, 78657, 34.00, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3700000, 49000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(3700000, 2000000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3700000, 50000, 29.74, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3700000, 24950, 142.12, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(3700000, 40000000, 10.12, home, remote));
                }
            }

            // NIGERIA AS THE HOME COUNTRY
            if (home.equals("Nigeria")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 55678, 314.75, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 52052, 233.40, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 276500, 16.80, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 78657, 96.96, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 49000, 354.90, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 3700000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 50000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 24950, 404.99, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(2000000, 40000000, 3.55, home, remote));
                }
            }

            // POLAND AS THE HOME COUNTRY
            if (home.equals("Poland")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50000, 55678, 3.71, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50000, 52052, 2.75, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 276500, 5.05, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50000, 78657, 1.14, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50000, 49000, 4.18, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 3700000, 29.74, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 2000000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50000, 24950, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 40000000, 301.16, home, remote));
                }
            }

            // ENGLAND AS THE HOME COUNTRY
            if (home.equals("England")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(24950, 55678, 1.29, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(24950, 52052, 1.74, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(24950, 276500, 24.11, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(24950, 78657, 4.18, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_home_more_valuable(24950, 49000, 1.14, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(24950, 3700000, 142.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(24950, 2000000, 404.99, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(24950, 50000, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(24950, 40000000, 1438.08, home, remote));
                }
            }

            // SOUTH KOREA AS THE HOME COUNTRY
            if (home.equals("South Korea")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40000000, 55678, 1118.40, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40000000, 52052, 829.34, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40000000, 276500, 59.67, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40000000, 78657, 344.29, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40000000, 49000, 1260.39, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40000000, 3700000, 10.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40000000, 2000000, 3.55, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40000000, 50000, 301.16, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40000000, 24950, 1438.08, home, remote));
                }
            }
        }


//======================================================================================================================


        // UI DESIGNER
        if (job.equals("UI designer")) {

            // CANADA AS THE HOME COUNTRY
            if (home.equals("Canada")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(54488, 64760, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(54488, 287340, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(54488, 48342, 2.41, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(54488, 40500, 1.52, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(54488, 3730000, 81.98, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(54488, 4000000, 233.40, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(54488, 54000, 2.75, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(54488, 29117, 1.74, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(54488, 43000000, 829.34, home, remote));
                }
            }

            // UNITED STATES AS THE HOME COUNTRY
            if (home.equals("United States")) {
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(64760, 54488, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(64760, 287340, 18.68, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(64760, 48342, 3.25, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(64760, 40500, 1.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(64760, 3730000, 110.52, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(64760, 4000000, 314.75, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(64760, 54000, 3.71, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(64760, 29117, 1.29, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(64760, 43000000, 1118.40, home, remote));
                }
            }

            // MEXICO AS THE HOME COUNTRY
            if (home.equals("Mexico")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(287340, 64760, 18.68, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(287340, 54488, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(287340, 48342, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(287340, 40500, 21.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(287340, 3730000, 5.89, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(287340, 4000000, 16.80, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(287340, 54000, 5.05, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(287340, 29117, 24.11, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(287340, 43000000, 59.67, home, remote));
                }
            }

            // BRAZIL AS THE HOME COUNTRY
            if (home.equals("Brazil")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48342, 64760, 3.25, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48342, 54488, 2.41, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(48342, 287340, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48342, 40500, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(48342, 3730000, 34.00, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(48342, 4000000, 96.96, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(48342, 54000, 1.14, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48342, 29117, 4.18, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(48342, 43000000, 344.29, home, remote));
                }
            }

            // GERMANY AS THE HOME COUNTRY
            if (home.equals("Germany")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(40500, 64760, 1.13, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(40500, 54488, 1.52, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(40500, 287340, 21.13, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(40500, 48342, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(40500, 3730000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(40500, 4000000, 354.90, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(40500, 54000, 4.18, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40500, 29117, 1.14, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(40500, 43000000, 1260.39, home, remote));
                }
            }

            // JAPAN AS THE HOME COUNTRY
            if (home.equals("Japan")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3730000, 64760, 110.52, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3730000, 54488, 81.98, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3730000, 287340, 5.89, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3730000, 48342, 34.00, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3730000, 40500, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(3730000, 4000000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3730000, 54000, 29.74, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3730000, 29117, 142.12, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(3730000, 43000000, 10.12, home, remote));
                }
            }

            // NIGERIA AS THE HOME COUNTRY
            if (home.equals("Nigeria")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4000000, 64760, 314.75, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4000000, 54488, 233.40, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4000000, 287340, 16.80, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4000000, 48342, 96.96, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4000000, 40500, 354.90, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4000000, 3730000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4000000, 54000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4000000, 29117, 404.99, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(4000000, 43000000, 3.55, home, remote));
                }
            }

            // POLAND AS THE HOME COUNTRY
            if (home.equals("Poland")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(54000, 64760, 3.71, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(54000, 54488, 2.75, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 287340, 5.05, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(54000, 48342, 1.14, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(54000, 40500, 4.18, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 3730000, 29.74, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 4000000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(54000, 29117, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(54000, 43000000, 301.16, home, remote));
                }
            }

            // ENGLAND AS THE HOME COUNTRY
            if (home.equals("England")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(29117, 64760, 1.29, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(29117, 54488, 1.74, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(29117, 287340, 24.11, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(29117, 48342, 4.18, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_home_more_valuable(29117, 40500, 1.14, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(29117, 3730000, 142.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(29117, 4000000, 404.99, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(29117, 54000, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(29117, 43000000, 1438.08, home, remote));
                }
            }

            // SOUTH KOREA AS THE HOME COUNTRY
            if (home.equals("South Korea")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43000000, 64760, 1118.40, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43000000, 54488, 829.34, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43000000, 287340, 59.67, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43000000, 48342, 344.29, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43000000, 40500, 1260.39, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43000000, 3730000, 10.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43000000, 4000000, 3.55, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43000000, 54000, 301.16, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43000000, 29117, 1438.08, home, remote));
                }
            }
        }


//======================================================================================================================


        // WEB DEVELOPER
        if (job.equals("Web developer")) {

            // CANADA AS THE HOME COUNTRY
            if (home.equals("Canada")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49758, 57702, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(49758, 354000, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(49758, 60560, 2.41, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49758, 50000, 1.52, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(49758, 3763765, 81.98, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(49758, 2350000, 233.40, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(49758, 57000, 2.75, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49758, 24833, 1.74, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(49758, 39045640, 829.34, home, remote));
                }
            }

            // UNITED STATES AS THE HOME COUNTRY
            if (home.equals("United States")) {
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(57702, 49758, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(57702, 354000, 18.68, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(57702, 60560, 3.25, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57702, 50000, 1.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(57702, 3763765, 110.52, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(57702, 2350000, 314.75, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(57702, 57000, 3.71, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57702, 24833, 1.29, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(57702, 39045640, 1118.40, home, remote));
                }
            }

            // MEXICO AS THE HOME COUNTRY
            if (home.equals("Mexico")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(354000, 57702, 18.68, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(354000, 49758, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(354000, 60560, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(354000, 50000, 21.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(354000, 3763765, 5.89, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(354000, 2350000, 16.80, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(354000, 57000, 5.05, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(354000, 24833, 24.11, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(354000, 39045640, 59.67, home, remote));
                }
            }

            // BRAZIL AS THE HOME COUNTRY
            if (home.equals("Brazil")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60560, 57702, 3.25, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60560, 49758, 2.41, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(60560, 354000, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60560, 50000, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(60560, 3763765, 34.00, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(60560, 2350000, 96.96, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(60560, 57000, 1.14, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60560, 24833, 4.18, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(60560, 39045640, 344.29, home, remote));
                }
            }

            // GERMANY AS THE HOME COUNTRY
            if (home.equals("Germany")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 57702, 1.13, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 49758, 1.52, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 354000, 21.13, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 60560, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 3763765, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 2350000, 354.90, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 57000, 4.18, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50000, 24833, 1.14, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(50000, 39045640, 1260.39, home, remote));
                }
            }

            // JAPAN AS THE HOME COUNTRY
            if (home.equals("Japan")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3763765, 57702, 110.52, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3763765, 49758, 81.98, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3763765, 354000, 5.89, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3763765, 60560, 34.00, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3763765, 50000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(3763765, 2350000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3763765, 57000, 29.74, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3763765, 24833, 142.12, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(3763765, 39045640, 10.12, home, remote));
                }
            }

            // NIGERIA AS THE HOME COUNTRY
            if (home.equals("Nigeria")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 57702, 314.75, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 49758, 233.40, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 354000, 16.80, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 60560, 96.96, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 50000, 354.90, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 3763765, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 57000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 24833, 404.99, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(2350000, 39045640, 3.55, home, remote));
                }
            }

            // POLAND AS THE HOME COUNTRY
            if (home.equals("Poland")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57000, 57702, 3.71, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57000, 49758, 2.75, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(57000, 354000, 5.05, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57000, 60560, 1.14, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57000, 50000, 4.18, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(57000, 3763765, 29.74, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(57000, 2350000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57000, 24833, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(57000, 39045640, 301.16, home, remote));
                }
            }

            // ENGLAND AS THE HOME COUNTRY
            if (home.equals("England")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(24833, 57702, 1.29, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(24833, 49758, 1.74, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(24833, 354000, 24.11, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(24833, 60560, 4.18, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_home_more_valuable(24833, 50000, 1.14, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(24833, 3763765, 142.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(24833, 2350000, 404.99, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(24833, 57000, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(24833, 39045640, 1438.08, home, remote));
                }
            }

            // SOUTH KOREA AS THE HOME COUNTRY
            if (home.equals("South Korea")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(39045640, 57702, 1118.40, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(39045640, 49758, 829.34, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(39045640, 354000, 59.67, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(39045640, 60560, 344.29, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(39045640, 50000, 1260.39, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(39045640, 3763765, 10.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_remote_more_valuable(39045640, 2350000, 3.55, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(39045640, 57000, 301.16, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(39045640, 24833, 1438.08, home, remote));
                }
            }
        }

//======================================================================================================================


        // DIGITAL MARKETING SPECIALIST
        if (job.equals("Digital marketing specialist")) {

            // CANADA AS THE HOME COUNTRY
            if (home.equals("Canada")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49533, 44663, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(49533, 480000, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(49533, 50194, 2.41, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49533, 53000, 1.52, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(49533, 3400000, 81.98, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(49533, 1500000, 233.40, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(49533, 52000, 2.75, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49533, 26450, 1.74, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(49533, 38000000, 829.34, home, remote));
                }
            }

            // UNITED STATES AS THE HOME COUNTRY
            if (home.equals("United States")) {
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(44663, 49533, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(44663, 480000, 18.68, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(44663, 50194, 3.25, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44663, 53000, 1.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(44663, 3400000, 110.52, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(44663, 1500000, 314.75, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(44663, 52000, 3.71, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44663, 26450, 1.29, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(44663, 38000000, 1118.40, home, remote));
                }
            }

            // MEXICO AS THE HOME COUNTRY
            if (home.equals("Mexico")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(480000, 44663, 18.68, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(480000, 49533, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(480000, 50194, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(480000, 53000, 21.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(480000, 3400000, 5.89, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(480000, 1500000, 16.80, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(480000, 52000, 5.05, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(480000, 26450, 24.11, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(480000, 38000000, 59.67, home, remote));
                }
            }

            // BRAZIL AS THE HOME COUNTRY
            if (home.equals("Brazil")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50194, 44663, 3.25, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50194, 49533, 2.41, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(50194, 480000, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50194, 53000, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(50194, 3400000, 34.00, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(50194, 1500000, 96.96, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(50194, 52000, 1.14, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50194, 26450, 4.18, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(50194, 38000000, 344.29, home, remote));
                }
            }

            // GERMANY AS THE HOME COUNTRY
            if (home.equals("Germany")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(53000, 44663, 1.13, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(53000, 49533, 1.52, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(53000, 480000, 21.13, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(53000, 50194, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(53000, 3400000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(53000, 1500000, 354.90, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(53000, 52000, 4.18, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(53000, 26450, 1.14, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(53000, 38000000, 1260.39, home, remote));
                }
            }

            // JAPAN AS THE HOME COUNTRY
            if (home.equals("Japan")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3400000, 44663, 110.52, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3400000, 49533, 81.98, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3400000, 480000, 5.89, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3400000, 50194, 34.00, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3400000, 53000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(3400000, 1500000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3400000, 52000, 29.74, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3400000, 26450, 142.12, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(3400000, 38000000, 10.12, home, remote));
                }
            }

            // NIGERIA AS THE HOME COUNTRY
            if (home.equals("Nigeria")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1500000, 44663, 314.75, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1500000, 49533, 233.40, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1500000, 480000, 16.80, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1500000, 50194, 96.96, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1500000, 53000, 354.90, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1500000, 3400000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1500000, 52000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1500000, 26450, 404.99, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(1500000, 38000000, 3.55, home, remote));
                }
            }

            // POLAND AS THE HOME COUNTRY
            if (home.equals("Poland")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(52000, 44663, 3.71, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(52000, 49533, 2.75, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(52000, 480000, 5.05, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(52000, 50194, 1.14, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(52000, 53000, 4.18, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(52000, 3400000, 29.74, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(52000, 1500000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(52000, 26450, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(52000, 38000000, 301.16, home, remote));
                }
            }

            // ENGLAND AS THE HOME COUNTRY
            if (home.equals("England")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(26450, 44663, 1.29, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(26450, 49533, 1.74, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(26450, 480000, 24.11, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(26450, 50194, 4.18, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_home_more_valuable(26450, 53000, 1.14, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(26450, 3400000, 142.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(26450, 1500000, 404.99, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(26450, 52000, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(26450, 38000000, 1438.08, home, remote));
                }
            }

            // SOUTH KOREA AS THE HOME COUNTRY
            if (home.equals("South Korea")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(38000000, 44663, 1118.40, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(38000000, 49533, 829.34, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(38000000, 480000, 59.67, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(38000000, 50194, 344.29, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(38000000, 53000, 1260.39, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(38000000, 3400000, 10.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_remote_more_valuable(38000000, 1500000, 3.55, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(38000000, 52000, 301.16, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(38000000, 26450, 1438.08, home, remote));
                }
            }
        }


//======================================================================================================================


        // WEB DESIGNER
        if (job.equals("Web designer")) {

            // CANADA AS THE HOME COUNTRY
            if (home.equals("Canada")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48083, 48632, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(48083, 150000, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(48083, 46928, 2.41, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48083, 44300, 1.52, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(48083, 3500000, 81.98, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(48083, 1200000, 233.40, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(48083, 57000, 2.75, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48083, 22860, 1.74, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(48083, 41612840, 829.34, home, remote));
                }
            }

            // UNITED STATES AS THE HOME COUNTRY
            if (home.equals("United States")) {
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(48632, 48083, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(48632, 150000, 18.68, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(48632, 46928, 3.25, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48632, 44300, 1.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(48632, 3500000, 110.52, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(48632, 1200000, 314.75, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(48632, 57000, 3.71, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48632, 22860, 1.29, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(48632, 41612840, 1118.40, home, remote));
                }
            }

            // MEXICO AS THE HOME COUNTRY
            if (home.equals("Mexico")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(150000, 48632, 18.68, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(150000, 48083, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(150000, 46928, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(150000, 44300, 21.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(150000, 3500000, 5.89, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(150000, 1200000, 16.80, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(150000, 57000, 5.05, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(150000, 22860, 24.11, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(150000, 41612840, 59.67, home, remote));
                }
            }

            // BRAZIL AS THE HOME COUNTRY
            if (home.equals("Brazil")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(46928, 48632, 3.25, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(46928, 48083, 2.41, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(46928, 150000, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(46928, 44300, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(46928, 3500000, 34.00, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(46928, 1200000, 96.96, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(46928, 57000, 1.14, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(46928, 22860, 4.18, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(46928, 41612840, 344.29, home, remote));
                }
            }

            // GERMANY AS THE HOME COUNTRY
            if (home.equals("Germany")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(44300, 48632, 1.13, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(44300, 48083, 1.52, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(44300, 150000, 21.13, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(44300, 46928, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(44300, 3500000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(44300, 1200000, 354.90, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(44300, 57000, 4.18, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44300, 22860, 1.14, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(44300, 41612840, 1260.39, home, remote));
                }
            }

            // JAPAN AS THE HOME COUNTRY
            if (home.equals("Japan")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3500000, 48632, 110.52, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3500000, 48083, 81.98, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3500000, 150000, 5.89, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3500000, 46928, 34.00, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3500000, 44300, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(3500000, 1200000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3500000, 57000, 29.74, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(3500000, 22860, 142.12, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(3500000, 41612840, 10.12, home, remote));
                }
            }

            // NIGERIA AS THE HOME COUNTRY
            if (home.equals("Nigeria")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1200000, 48632, 314.75, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1200000, 48083, 233.40, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1200000, 150000, 16.80, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1200000, 46928, 96.96, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1200000, 44300, 354.90, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1200000, 3500000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1200000, 57000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(1200000, 22860, 404.99, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(1200000, 41612840, 3.55, home, remote));
                }
            }

            // POLAND AS THE HOME COUNTRY
            if (home.equals("Poland")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57000, 48632, 3.71, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57000, 48083, 2.75, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(57000, 150000, 5.05, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57000, 46928, 1.14, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57000, 44300, 4.18, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(57000, 3500000, 29.74, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(57000, 1200000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57000, 22860, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(57000, 41612840, 301.16, home, remote));
                }
            }

            // ENGLAND AS THE HOME COUNTRY
            if (home.equals("England")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(22860, 48632, 1.29, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(22860, 48083, 1.74, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(22860, 150000, 24.11, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(22860, 46928, 4.18, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_home_more_valuable(22860, 44300, 1.14, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(22860, 3500000, 142.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(22860, 1200000, 404.99, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(22860, 57000, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(22860, 41612840, 1438.08, home, remote));
                }
            }

            // SOUTH KOREA AS THE HOME COUNTRY
            if (home.equals("South Korea")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(41612840, 48632, 1118.40, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(41612840, 48083, 829.34, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(41612840, 150000, 59.67, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(41612840, 46928, 344.29, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(41612840, 44300, 1260.39, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(41612840, 3500000, 10.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_remote_more_valuable(41612840, 1200000, 3.55, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(41612840, 57000, 301.16, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(41612840, 22860, 1438.08, home, remote));
                }
            }
        }


//======================================================================================================================


        // MOBILE DEVELOPER
        if (job.equals("Mobile developer")) {

            // CANADA AS THE HOME COUNTRY
            if (home.equals("Canada")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57380, 71495, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(57380, 300000, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(57380, 60789, 2.41, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57380, 48802, 1.52, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(57380, 4560000, 81.98, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(57380, 2250000, 233.40, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(57380, 59000, 2.75, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(57380, 30200, 1.74, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(57380, 64964060, 829.34, home, remote));
                }
            }

            // UNITED STATES AS THE HOME COUNTRY
            if (home.equals("United States")) {
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(71495, 57380, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(71495, 300000, 18.68, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(71495, 60789, 3.25, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(71495, 48802, 1.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(71495, 4560000, 110.52, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(71495, 2250000, 314.75, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(71495, 59000, 3.71, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(71495, 30200, 1.29, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(71495, 64964060, 1118.40, home, remote));
                }
            }

            // MEXICO AS THE HOME COUNTRY
            if (home.equals("Mexico")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(300000, 71495, 18.68, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(300000, 57380, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(300000, 60789, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(300000, 48802, 21.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(300000, 4560000, 5.89, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(300000, 2250000, 16.80, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(300000, 59000, 5.05, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(300000, 30200, 24.11, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(300000, 64964060, 59.67, home, remote));
                }
            }

            // BRAZIL AS THE HOME COUNTRY
            if (home.equals("Brazil")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60789, 71495, 3.25, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60789, 57380, 2.41, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(60789, 300000, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60789, 48802, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(60789, 4560000, 34.00, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(60789, 2250000, 96.96, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(60789, 59000, 1.14, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60789, 30200, 4.18, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(60789, 64964060, 344.29, home, remote));
                }
            }

            // GERMANY AS THE HOME COUNTRY
            if (home.equals("Germany")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(48802, 71495, 1.13, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(48802, 57380, 1.52, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(48802, 300000, 21.13, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(48802, 60789, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(48802, 4560000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(48802, 2250000, 354.90, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(48802, 59000, 4.18, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48802, 30200, 1.14, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(48802, 64964060, 1260.39, home, remote));
                }
            }

            // JAPAN AS THE HOME COUNTRY
            if (home.equals("Japan")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4560000, 71495, 110.52, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4560000, 57380, 81.98, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4560000, 300000, 5.89, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4560000, 60789, 34.00, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4560000, 48802, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(4560000, 2250000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4560000, 59000, 29.74, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(4560000, 30200, 142.12, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(4560000, 64964060, 10.12, home, remote));
                }
            }

            // NIGERIA AS THE HOME COUNTRY
            if (home.equals("Nigeria")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2250000, 71495, 314.75, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2250000, 57380, 233.40, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2250000, 300000, 16.80, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2250000, 60789, 96.96, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2250000, 48802, 354.90, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2250000, 4560000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2250000, 59000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2250000, 30200, 404.99, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(2250000, 64964060, 3.55, home, remote));
                }
            }

            // POLAND AS THE HOME COUNTRY
            if (home.equals("Poland")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(59000, 71495, 3.71, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(59000, 57380, 2.75, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(59000, 300000, 5.05, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(59000, 60789, 1.14, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(59000, 48802, 4.18, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(59000, 4560000, 29.74, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(59000, 2250000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(59000, 30200, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(59000, 64964060, 301.16, home, remote));
                }
            }

            // ENGLAND AS THE HOME COUNTRY
            if (home.equals("England")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(30200, 71495, 1.29, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(30200, 57380, 1.74, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(30200, 300000, 24.11, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(30200, 60789, 4.18, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_home_more_valuable(30200, 48802, 1.14, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(30200, 4560000, 142.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(30200, 2250000, 404.99, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(30200, 59000, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(30200, 64964060, 1438.08, home, remote));
                }
            }

            // SOUTH KOREA AS THE HOME COUNTRY
            if (home.equals("South Korea")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(64964060, 71495, 1118.40, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(64964060, 57380, 829.34, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(64964060, 300000, 59.67, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(64964060, 60789, 344.29, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(64964060, 48802, 1260.39, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(64964060, 4560000, 10.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_remote_more_valuable(64964060, 2250000, 3.55, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(64964060, 59000, 301.16, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(64964060, 30200, 1438.08, home, remote));
                }
            }
        }


//======================================================================================================================


        // GAME DEVELOPER
        if (job.equals("Game developer")) {

            // CANADA AS THE HOME COUNTRY
            if (home.equals("Canada")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(51352, 65244, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(51352, 180000, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(51352, 40934, 2.41, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(51352, 38500, 1.52, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(51352, 5180000, 81.98, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(51352, 2500000, 233.40, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(51352, 48000, 2.75, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(51352, 30671, 1.74, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(51352, 43500000, 829.34, home, remote));
                }
            }

            // UNITED STATES AS THE HOME COUNTRY
            if (home.equals("United States")) {
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(65244, 51352, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(65244, 180000, 18.68, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(65244, 40934, 3.25, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(65244, 38500, 1.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(65244, 5180000, 110.52, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(65244, 2500000, 314.75, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(65244, 48000, 3.71, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(65244, 30671, 1.29, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(65244, 43500000, 1118.40, home, remote));
                }
            }

            // MEXICO AS THE HOME COUNTRY
            if (home.equals("Mexico")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(180000, 65244, 18.68, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(180000, 51352, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(180000, 40934, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(180000, 38500, 21.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(180000, 5180000, 5.89, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(180000, 2500000, 16.80, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(180000, 48000, 5.05, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(180000, 30671, 24.11, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(180000, 43500000, 59.67, home, remote));
                }
            }

            // BRAZIL AS THE HOME COUNTRY
            if (home.equals("Brazil")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40934, 65244, 3.25, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40934, 51352, 2.41, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(40934, 180000, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40934, 38500, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(40934, 5180000, 34.00, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(40934, 2500000, 96.96, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(40934, 48000, 1.14, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(40934, 30671, 4.18, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(40934, 43500000, 344.29, home, remote));
                }
            }

            // GERMANY AS THE HOME COUNTRY
            if (home.equals("Germany")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(38500, 65244, 1.13, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(38500, 51352, 1.52, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(38500, 180000, 21.13, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(38500, 40934, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(38500, 5180000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(38500, 2500000, 354.90, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(38500, 48000, 4.18, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(38500, 30671, 1.14, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(38500, 43500000, 1260.39, home, remote));
                }
            }

            // JAPAN AS THE HOME COUNTRY
            if (home.equals("Japan")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5180000, 65244, 110.52, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5180000, 51352, 81.98, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5180000, 180000, 5.89, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5180000, 40934, 34.00, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5180000, 38500, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(5180000, 2500000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5180000, 48000, 29.74, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5180000, 30671, 142.12, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(5180000, 43500000, 10.12, home, remote));
                }
            }

            // NIGERIA AS THE HOME COUNTRY
            if (home.equals("Nigeria")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2500000, 65244, 314.75, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2500000, 51352, 233.40, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2500000, 180000, 16.80, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2500000, 40934, 96.96, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2500000, 38500, 354.90, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2500000, 5180000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2500000, 48000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2500000, 30671, 404.99, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(2500000, 43500000, 3.55, home, remote));
                }
            }

            // POLAND AS THE HOME COUNTRY
            if (home.equals("Poland")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48000, 65244, 3.71, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48000, 51352, 2.75, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(48000, 180000, 5.05, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48000, 40934, 1.14, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48000, 38500, 4.18, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(48000, 5180000, 29.74, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(48000, 2500000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(48000, 30671, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(48000, 43500000, 301.16, home, remote));
                }
            }

            // ENGLAND AS THE HOME COUNTRY
            if (home.equals("England")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(30671, 65244, 1.29, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(30671, 51352, 1.74, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(30671, 180000, 24.11, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(30671, 40934, 4.18, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_home_more_valuable(30671, 38500, 1.14, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(30671, 5180000, 142.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(30671, 2500000, 404.99, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(30671, 48000, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(30671, 43500000, 1438.08, home, remote));
                }
            }

            // SOUTH KOREA AS THE HOME COUNTRY
            if (home.equals("South Korea")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43500000, 65244, 1118.40, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43500000, 51352, 829.34, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43500000, 180000, 59.67, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43500000, 40934, 344.29, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43500000, 38500, 1260.39, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43500000, 5180000, 10.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43500000, 2500000, 3.55, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43500000, 48000, 301.16, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(43500000, 30671, 1438.08, home, remote));
                }
            }
        }


//======================================================================================================================


        // UX DESIGNER
        if (job.equals("UX designer")) {

            // CANADA AS THE HOME COUNTRY
            if (home.equals("Canada")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(58670, 73800, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(58670, 365000, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(58670, 50229, 2.41, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(58670, 51000, 1.52, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(58670, 5000000, 81.98, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(58670, 2350000, 233.40, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(58670, 49000, 2.75, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(58670, 30758, 1.74, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(58670, 44802800, 829.34, home, remote));
                }
            }

            // UNITED STATES AS THE HOME COUNTRY
            if (home.equals("United States")) {
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(73800, 58670, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(73800, 365000, 18.68, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(73800, 50229, 3.25, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(73800, 51000, 1.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(73800, 5000000, 110.52, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(73800, 2350000, 314.75, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(73800, 49000, 3.71, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(73800, 30758, 1.29, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(73800, 44802800, 1118.40, home, remote));
                }
            }

            // MEXICO AS THE HOME COUNTRY
            if (home.equals("Mexico")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(365000, 73800, 18.68, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(365000, 58670, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(365000, 50229, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(365000, 51000, 21.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(365000, 5000000, 5.89, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(365000, 2350000, 16.80, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(365000, 49000, 5.05, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(365000, 30758, 24.11, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(365000, 44802800, 59.67, home, remote));
                }
            }

            // BRAZIL AS THE HOME COUNTRY
            if (home.equals("Brazil")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50229, 73800, 3.25, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50229, 58670, 2.41, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(50229, 365000, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50229, 51000, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(50229, 5000000, 34.00, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(50229, 2350000, 96.96, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(50229, 49000, 1.14, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(50229, 30758, 4.18, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(50229, 44802800, 344.29, home, remote));
                }
            }

            // GERMANY AS THE HOME COUNTRY
            if (home.equals("Germany")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(51000, 73800, 1.13, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(51000, 58670, 1.52, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(51000, 365000, 21.13, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(51000, 50229, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(51000, 5000000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(51000, 2350000, 354.90, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(51000, 49000, 4.18, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(51000, 30758, 1.14, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(51000, 44802800, 1260.39, home, remote));
                }
            }

            // JAPAN AS THE HOME COUNTRY
            if (home.equals("Japan")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5000000, 73800, 110.52, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5000000, 58670, 81.98, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5000000, 365000, 5.89, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5000000, 50229, 34.00, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5000000, 51000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(5000000, 2350000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5000000, 49000, 29.74, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(5000000, 30758, 142.12, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(5000000, 44802800, 10.12, home, remote));
                }
            }

            // NIGERIA AS THE HOME COUNTRY
            if (home.equals("Nigeria")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 73800, 314.75, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 58670, 233.40, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 365000, 16.80, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 50229, 96.96, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 51000, 354.90, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 5000000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 49000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2350000, 30758, 404.99, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(2350000, 44802800, 3.55, home, remote));
                }
            }

            // POLAND AS THE HOME COUNTRY
            if (home.equals("Poland")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49000, 73800, 3.71, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49000, 58670, 2.75, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 365000, 5.05, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49000, 50229, 1.14, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49000, 51000, 4.18, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 5000000, 29.74, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 2350000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(49000, 30758, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(49000, 44802800, 301.16, home, remote));
                }
            }

            // ENGLAND AS THE HOME COUNTRY
            if (home.equals("England")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(30758, 73800, 1.29, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(30758, 58670, 1.74, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(30758, 365000, 24.11, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(30758, 50229, 4.18, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_home_more_valuable(30758, 51000, 1.14, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(30758, 5000000, 142.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(30758, 2350000, 404.99, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(30758, 49000, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(30758, 44802800, 1438.08, home, remote));
                }
            }

            // SOUTH KOREA AS THE HOME COUNTRY
            if (home.equals("South Korea")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44802800, 73800, 1118.40, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44802800, 58670, 829.34, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44802800, 365000, 59.67, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44802800, 50229, 344.29, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44802800, 51000, 1260.39, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44802800, 5000000, 10.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44802800, 2350000, 3.55, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44802800, 49000, 301.16, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(44802800, 30758, 1438.08, home, remote));
                }
            }
        }


//======================================================================================================================


        // NETWORK ADMINISTRATOR
        if (job.equals("Network administrator")) {

            // CANADA AS THE HOME COUNTRY
            if (home.equals("Canada")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(56174, 56576, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(56174, 360000, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(56174, 80994, 2.41, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(56174, 55000, 1.52, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(56174, 6450000, 81.98, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(56174, 2000000, 233.40, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(56174, 60000, 2.75, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(56174, 20338, 1.74, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(56174, 27785576, 829.34, home, remote));
                }
            }

            // UNITED STATES AS THE HOME COUNTRY
            if (home.equals("United States")) {
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(56576, 56174, 1.35, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(56576, 360000, 18.68, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(56576, 80994, 3.25, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(56576, 55000, 1.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(56576, 6450000, 110.52, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(56576, 2000000, 314.75, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(56576, 60000, 3.71, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(56576, 20338, 1.29, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(56576, 27785576, 1118.40, home, remote));
                }
            }

            // MEXICO AS THE HOME COUNTRY
            if (home.equals("Mexico")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(360000, 56576, 18.68, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(360000, 56174, 13.85, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(360000, 80994, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(360000, 55000, 21.13, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(360000, 6450000, 5.89, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(360000, 2000000, 16.80, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(360000, 60000, 5.05, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(360000, 20338, 24.11, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(360000, 27785576, 59.67, home, remote));
                }
            }

            // BRAZIL AS THE HOME COUNTRY
            if (home.equals("Brazil")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(80994, 56576, 3.25, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(80994, 56174, 2.41, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(80994, 360000, 5.75, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(80994, 55000, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(80994, 6450000, 34.00, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(80994, 2000000, 96.96, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(80994, 60000, 1.14, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(80994, 20338, 4.18, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(80994, 27785576, 344.29, home, remote));
                }
            }

            // GERMANY AS THE HOME COUNTRY
            if (home.equals("Germany")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(55000, 56576, 1.13, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(55000, 56174, 1.52, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(55000, 360000, 21.13, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(55000, 80994, 3.66, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(55000, 6450000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(55000, 2000000, 354.90, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(55000, 60000, 4.18, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(55000, 20338, 1.14, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(55000, 27785576, 1260.39, home, remote));
                }
            }

            // JAPAN AS THE HOME COUNTRY
            if (home.equals("Japan")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(6450000, 56576, 110.52, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(6450000, 56174, 81.98, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(6450000, 360000, 5.89, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(6450000, 80994, 34.00, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(6450000, 55000, 124.49, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(6450000, 2000000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(6450000, 60000, 29.74, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(6450000, 20338, 142.12, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(6450000, 27785576, 10.12, home, remote));
                }
            }

            // NIGERIA AS THE HOME COUNTRY
            if (home.equals("Nigeria")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 56576, 314.75, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 56174, 233.40, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 360000, 16.80, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 80994, 96.96, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 55000, 354.90, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 6450000, 2.85, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 60000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(2000000, 20338, 404.99, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(2000000, 27785576, 3.55, home, remote));
                }
            }

            // POLAND AS THE HOME COUNTRY
            if (home.equals("Poland")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60000, 56576, 3.71, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60000, 56174, 2.75, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(60000, 360000, 5.05, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60000, 80994, 1.14, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60000, 55000, 4.18, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(60000, 6450000, 29.74, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(60000, 2000000, 84.80, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(60000, 20338, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(60000, 27785576, 301.16, home, remote));
                }
            }

            // ENGLAND AS THE HOME COUNTRY
            if (home.equals("England")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_home_more_valuable(20338, 56576, 1.29, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_home_more_valuable(20338, 56174, 1.74, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_home_more_valuable(20338, 360000, 24.11, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_home_more_valuable(20338, 80994, 4.18, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_home_more_valuable(20338, 55000, 1.14, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_home_more_valuable(20338, 6450000, 142.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_home_more_valuable(20338, 2000000, 404.99, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_home_more_valuable(20338, 60000, 4.78, home, remote));
                }
                if (remote.equals("South Korea")) {
                    mPercent.setText(percent.difference_home_more_valuable(20338, 27785576, 1438.08, home, remote));
                }
            }

            // SOUTH KOREA AS THE HOME COUNTRY
            if (home.equals("South Korea")) {
                if (remote.equals("United States")) {
                    mPercent.setText(percent.difference_remote_more_valuable(27785576, 56576, 1118.40, home, remote));
                }
                if (remote.equals("Canada")) {
                    mPercent.setText(percent.difference_remote_more_valuable(27785576, 56174, 829.34, home, remote));
                }
                if (remote.equals("Mexico")) {
                    mPercent.setText(percent.difference_remote_more_valuable(27785576, 360000, 59.67, home, remote));
                }
                if (remote.equals("Brazil")) {
                    mPercent.setText(percent.difference_remote_more_valuable(27785576, 80994, 344.29, home, remote));
                }
                if (remote.equals("Germany")) {
                    mPercent.setText(percent.difference_remote_more_valuable(27785576, 55000, 1260.39, home, remote));
                }
                if (remote.equals("Japan")) {
                    mPercent.setText(percent.difference_remote_more_valuable(27785576, 6450000, 10.12, home, remote));
                }
                if (remote.equals("Nigeria")) {
                    mPercent.setText(percent.difference_remote_more_valuable(27785576, 2000000, 3.55, home, remote));
                }
                if (remote.equals("Poland")) {
                    mPercent.setText(percent.difference_remote_more_valuable(27785576, 60000, 301.16, home, remote));
                }
                if (remote.equals("England")) {
                    mPercent.setText(percent.difference_remote_more_valuable(27785576, 20338, 1438.08, home, remote));
                }
            }
        }
    }
}
