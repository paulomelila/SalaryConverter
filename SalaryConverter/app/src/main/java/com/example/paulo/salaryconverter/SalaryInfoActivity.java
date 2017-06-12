package com.example.paulo.salaryconverter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class SalaryInfoActivity extends AppCompatActivity {
    private ArrayList<Country> mCountryBank = new ArrayList<>();
    private ArrayList<Job> mJobBank = new ArrayList<>();
    private Salary percent = new Salary();
    private Salary avg_salary = new Salary();
    private Salary rate = new Salary();
    private final String CAD = "CAD";
    private final String USD = "USD";
    private final String MXN = "MXN";
    private final String BRL = "BRL";
    private final String EUR = "EUR";
    private final String JPY = "JPY";
    private final String NGN = "NGN";
    private final String PLN = "PLN";
    private final String GBP = "GBP";
    private final String KRW = "KRW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_info);

        // Getting a reference to the root element of the database
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        // Getting the content of the string arrays for conversion rates
        final String[] can_rates = getResources().getStringArray(R.array.canada_rates);
        final String[] usa_rates = getResources().getStringArray(R.array.usa_rates);
        final String[] mex_rates = getResources().getStringArray(R.array.mexico_rates);
        final String[] bra_rates = getResources().getStringArray(R.array.brazil_rates);
        final String[] ger_rates = getResources().getStringArray(R.array.germany_rates);
        final String[] jap_rates = getResources().getStringArray(R.array.japan_rates);
        final String[] nig_rates = getResources().getStringArray(R.array.nigeria_rates);
        final String[] pol_rates = getResources().getStringArray(R.array.poland_rates);
        final String[] eng_rates = getResources().getStringArray(R.array.england_rates);
        final String[] kor_rates = getResources().getStringArray(R.array.south_korea_rates);

        // Widgets
        ImageView home_country_flag = (ImageView) findViewById(R.id.home_country_flag);
        ImageView remote_country_flag = (ImageView) findViewById(R.id.remote_country_flag);
        ImageView hc_flag_salary = (ImageView) findViewById(R.id.hc_flag_salary);
        ImageView rc_flag_salary = (ImageView) findViewById(R.id.rc_flag_salary);
        final TextView mJob = (TextView) findViewById(R.id.job);
        final TextView mPercent = (TextView) findViewById(R.id.percentDifferenceTex);
        TextView mRateLabel = (TextView) findViewById(R.id.rate_label);
        TextView mRateText = (TextView) findViewById(R.id.rate);
        TextView mAvg_salary = (TextView) findViewById(R.id.avg_salary);
        final TextView mHc_salary = (TextView) findViewById(R.id.homeCountryAverageSalary);
        final TextView mRc_salary = (TextView) findViewById(R.id.remoteCountryAverageSalary);

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

        final String home = b.getString("Home country");
        final String job = b.getString("Job");
        final String remote = b.getString("Remote country");

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

        // CONVERSION RATE TEXT
        if (home.equals("United States") && remote.equals("Canada") || home.equals("Canada") && remote.equals("United States")) {
            mRateText.setText(" 1 " + USD + " = " + can_rates[0] + " " + CAD);
        }
        if (home.equals("United States") && remote.equals("Mexico") || home.equals("Mexico") && remote.equals("United States")) {
            mRateText.setText(" 1 " + USD + " = " + usa_rates[1] + " " + MXN);
        }
        if (home.equals("United States") && remote.equals("Brazil") || home.equals("Brazil") && remote.equals("United States")) {
            mRateText.setText(" 1 " + USD + " = " + usa_rates[2] + " " + BRL);
        }
        if (home.equals("United States") && remote.equals("Germany") || home.equals("Germany") && remote.equals("United States")) {
            mRateText.setText(" 1 " + EUR + " = " + usa_rates[3] + " " + USD);
        }
        if (home.equals("United States") && remote.equals("Japan") || home.equals("Japan") && remote.equals("United States")) {
            mRateText.setText(" 1 " + USD + " = " + usa_rates[4] + " " + JPY);
        }
        if (home.equals("United States") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("United States")) {
            mRateText.setText(" 1 " + USD + " = " + usa_rates[5] + " " + NGN);
        }
        if (home.equals("United States") && remote.equals("Poland") || home.equals("Poland") && remote.equals("United States")) {
            mRateText.setText(" 1 " + USD + " = " + usa_rates[6] + " " + PLN);
        }
        if (home.equals("United States") && remote.equals("England") || home.equals("England") && remote.equals("United States")) {
            mRateText.setText(" 1 " + GBP + " = " + usa_rates[7] + " " + USD);
        }
        if (home.equals("United States") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("United States")) {
            mRateText.setText(" 1 " + USD + " = " + usa_rates[8] + " " + KRW);
        }



        if (home.equals("Canada") && remote.equals("Mexico") || home.equals("Mexico") && remote.equals("Canada")) {
            mRateText.setText(" 1 " + CAD + " = " + can_rates[1] + " " + MXN);
        }
        if (home.equals("Canada") && remote.equals("Brazil") || home.equals("Brazil") && remote.equals("Canada")) {
            mRateText.setText(" 1 " + CAD + " = " + can_rates[2] + " " + BRL);
        }
        if (home.equals("Canada") && remote.equals("Germany") || home.equals("Germany") && remote.equals("Canada")) {
            mRateText.setText(" 1 " + EUR + " = " + can_rates[3] + " " + CAD);
        }
        if (home.equals("Canada") && remote.equals("Japan") || home.equals("Japan") && remote.equals("Canada")) {
            mRateText.setText(" 1 " + CAD + " = " + can_rates[4] + " " + JPY);
        }
        if (home.equals("Canada") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("Canada")) {
            mRateText.setText(" 1 " + CAD + " = " + can_rates[5] + " " + NGN);
        }
        if (home.equals("Canada") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Canada")) {
            mRateText.setText(" 1 " + CAD + " = " + can_rates[6] + " " + PLN);
        }
        if (home.equals("Canada") && remote.equals("England") || home.equals("England") && remote.equals("Canada")) {
            mRateText.setText(" 1 " + GBP + " = " + can_rates[7] + " " + CAD);
        }
        if (home.equals("Canada") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Canada")) {
            mRateText.setText(" 1 " + CAD + " = " + can_rates[8] + " " + KRW);
        }



        if (home.equals("Mexico") && remote.equals("Brazil") || home.equals("Brazil") && remote.equals("Mexico")) {
            mRateText.setText(" 1 " + BRL + " = " + mex_rates[2] + " " + MXN);
        }
        if (home.equals("Mexico") && remote.equals("Germany") || home.equals("Germany") && remote.equals("Mexico")) {
            mRateText.setText(" 1 " + EUR + " = " + mex_rates[3] + " " + MXN);
        }
        if (home.equals("Mexico") && remote.equals("Japan") || home.equals("Japan") && remote.equals("Mexico")) {
            mRateText.setText(" 1 " + MXN + " = " + mex_rates[4] + " " + JPY);
        }
        if (home.equals("Mexico") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("Mexico")) {
            mRateText.setText(" 1 " + MXN + " = " + mex_rates[5] + " " + NGN);
        }
        if (home.equals("Mexico") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Mexico")) {
            mRateText.setText(" 1 " + PLN + " = " + mex_rates[6] + " " + MXN);
        }
        if (home.equals("Mexico") && remote.equals("England") || home.equals("England") && remote.equals("Mexico")) {
            mRateText.setText(" 1 " + GBP + " = " + mex_rates[7] + " " + MXN);
        }
        if (home.equals("Mexico") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Mexico")) {
            mRateText.setText(" 1 " + MXN + " = " + mex_rates[8] + " " + KRW);
        }



        if (home.equals("Brazil") && remote.equals("Germany") || home.equals("Germany") && remote.equals("Brazil")) {
            mRateText.setText(" 1 " + EUR + " = " + bra_rates[3] + " " + BRL);
        }
        if (home.equals("Brazil") && remote.equals("Japan") || home.equals("Japan") && remote.equals("Brazil")) {
            mRateText.setText(" 1 " + BRL + " = " + bra_rates[4] + " " + JPY);
        }
        if (home.equals("Brazil") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("Brazil")) {
            mRateText.setText(" 1 " + BRL + " = " + bra_rates[5] + " " + NGN);
        }
        if (home.equals("Brazil") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Brazil")) {
            mRateText.setText(" 1 " + BRL + " = " + bra_rates[6] + " " + PLN);
        }
        if (home.equals("Brazil") && remote.equals("England") || home.equals("England") && remote.equals("Brazil")) {
            mRateText.setText(" 1 " + GBP + " = " + bra_rates[7] + " " + BRL);
        }
        if (home.equals("Brazil") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Brazil")) {
            mRateText.setText(" 1 " + BRL + " = " + bra_rates[8] + " " + KRW);
        }



        if (home.equals("Germany") && remote.equals("Japan") || home.equals("Japan") && remote.equals("Germany")) {
            mRateText.setText(" 1 " + EUR + " = " + ger_rates[4] + " " + JPY);
        }
        if (home.equals("Germany") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("Germany")) {
            mRateText.setText(" 1 " + EUR + " = " + ger_rates[5] + " " + NGN);
        }
        if (home.equals("Germany") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Germany")) {
            mRateText.setText(" 1 " + EUR + " = " + ger_rates[6] + " " + PLN);
        }
        if (home.equals("Germany") && remote.equals("England") || home.equals("England") && remote.equals("Germany")) {
            mRateText.setText(" 1 " + GBP + " = " + ger_rates[7] + " " + EUR);
        }
        if (home.equals("Germany") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Germany")) {
            mRateText.setText(" 1 " + EUR + " = " + ger_rates[8] + " " + KRW);
        }



        if (home.equals("Japan") && remote.equals("Nigeria") || home.equals("Nigeria") && remote.equals("Japan")) {
            mRateText.setText(" 1 " + JPY + " = " + jap_rates[5] + " " + NGN);
        }
        if (home.equals("Japan") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Japan")) {
            mRateText.setText(" 1 " + PLN + " = " + jap_rates[6] + " " + JPY);
        }
        if (home.equals("Japan") && remote.equals("England") || home.equals("England") && remote.equals("Japan")) {
            mRateText.setText(" 1 " + GBP + " = " + jap_rates[7] + " " + JPY);
        }
        if (home.equals("Japan") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Japan")) {
            mRateText.setText(" 1 " + JPY + " = " + jap_rates[8] + " " + KRW);
        }



        if (home.equals("Nigeria") && remote.equals("Poland") || home.equals("Poland") && remote.equals("Nigeria")) {
            mRateText.setText(" 1 " + PLN + " = " + nig_rates[6] + " " + NGN);
        }
        if (home.equals("Nigeria") && remote.equals("England") || home.equals("England") && remote.equals("Nigeria")) {
            mRateText.setText(" 1 " + GBP + " = " + nig_rates[7] + " " + NGN);
        }
        if (home.equals("Nigeria") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Nigeria")) {
            mRateText.setText(" 1 " + NGN + " = " + nig_rates[8] + " " + KRW);
        }



        if (home.equals("Poland") && remote.equals("England") || home.equals("England") && remote.equals("Poland")) {
            mRateText.setText(" 1 " + GBP + " = " + pol_rates[7] + " " + PLN);
        }
        if (home.equals("Poland") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("Poland")) {
            mRateText.setText(" 1 " + PLN + " = " + pol_rates[8] + " " + KRW);
        }



        if (home.equals("England") && remote.equals("South Korea") || home.equals("South Korea") && remote.equals("England")) {
            mRateText.setText(" 1 " + GBP + " = " + eng_rates[8] + " " + KRW);
        }


// SETTING THE PERCENT DIFFERENCE TEXT

        // SOFTWARE ENGINEER
        if (job.equals("Software engineer")) {
            mRootRef.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   double can_se = dataSnapshot.child("Canada").child("Software engineer").getValue(Double.class);
                   double usa_se = dataSnapshot.child("United States").child("Software engineer").getValue(Double.class);
                   double mex_se = dataSnapshot.child("Mexico").child("Software engineer").getValue(Double.class);
                   double bra_se = dataSnapshot.child("Brazil").child("Software engineer").getValue(Double.class);
                   double ger_se = dataSnapshot.child("Germany").child("Software engineer").getValue(Double.class);
                   double jap_se = dataSnapshot.child("Japan").child("Software engineer").getValue(Double.class);
                   double nig_se = dataSnapshot.child("Nigeria").child("Software engineer").getValue(Double.class);
                   double pol_se = dataSnapshot.child("Poland").child("Software engineer").getValue(Double.class);
                   double eng_se = dataSnapshot.child("England").child("Software engineer").getValue(Double.class);
                   double kor_se = dataSnapshot.child("South Korea").child("Software engineer").getValue(Double.class);

                   // CANADA AS THE HOME COUNTRY
                   if (home.equals("Canada")) {
                       mHc_salary.setText(avg_salary.averageSalary(can_se, CAD));
                       if (remote.equals("United States")) {
                           mPercent.setText(percent.difference_remote_more_valuable(can_se, usa_se, rate.getRate(can_rates[0]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(usa_se, USD));
                       }
                       if (remote.equals("Mexico")) {
                           mPercent.setText(percent.difference_home_more_valuable(can_se, mex_se, rate.getRate(can_rates[1]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(mex_se, MXN));
                       }
                       if (remote.equals("Brazil")) {
                           mPercent.setText(percent.difference_home_more_valuable(can_se, bra_se, rate.getRate(can_rates[2]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(bra_se, BRL));
                       }
                       if (remote.equals("Germany")) {
                           mPercent.setText(percent.difference_remote_more_valuable(can_se, ger_se, rate.getRate(can_rates[3]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(ger_se, EUR));
                       }
                       if (remote.equals("Japan")) {
                           mPercent.setText(percent.difference_home_more_valuable(can_se, jap_se, rate.getRate(can_rates[4]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(jap_se, JPY));
                       }
                       if (remote.equals("Nigeria")) {
                           mPercent.setText(percent.difference_home_more_valuable(can_se, nig_se, rate.getRate(can_rates[5]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(nig_se, NGN));
                       }
                       if (remote.equals("Poland")) {
                           mPercent.setText(percent.difference_home_more_valuable(can_se, pol_se, rate.getRate(can_rates[6]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(pol_se, PLN));
                       }
                       if (remote.equals("England")) {
                           mPercent.setText(percent.difference_remote_more_valuable(can_se, eng_se, rate.getRate(can_rates[7]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(eng_se, GBP));
                       }
                       if (remote.equals("South Korea")) {
                           mPercent.setText(percent.difference_home_more_valuable(can_se, kor_se, rate.getRate(can_rates[8]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(kor_se, KRW));
                       }
                   }

                   // UNITED STATES AS THE HOME COUNTRY
                   if (home.equals("United States")) {
                       mHc_salary.setText(avg_salary.averageSalary(usa_se, USD));
                       if (remote.equals("Canada")) {
                           mPercent.setText(percent.difference_home_more_valuable(usa_se, can_se, rate.getRate(usa_rates[0]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(can_se, CAD));
                       }
                       if (remote.equals("Mexico")) {
                           mPercent.setText(percent.difference_home_more_valuable(usa_se, mex_se, rate.getRate(usa_rates[1]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(mex_se, MXN));
                       }
                       if (remote.equals("Brazil")) {
                           mPercent.setText(percent.difference_home_more_valuable(usa_se, bra_se, rate.getRate(usa_rates[2]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(bra_se, BRL));
                       }
                       if (remote.equals("Germany")) {
                           mPercent.setText(percent.difference_remote_more_valuable(usa_se, ger_se, rate.getRate(usa_rates[3]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(ger_se, EUR));
                       }
                       if (remote.equals("Japan")) {
                           mPercent.setText(percent.difference_home_more_valuable(usa_se, jap_se, rate.getRate(usa_rates[4]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(jap_se, JPY));
                       }
                       if (remote.equals("Nigeria")) {
                           mPercent.setText(percent.difference_home_more_valuable(usa_se, nig_se, rate.getRate(usa_rates[5]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(nig_se, NGN));
                       }
                       if (remote.equals("Poland")) {
                           mPercent.setText(percent.difference_home_more_valuable(usa_se, pol_se, rate.getRate(usa_rates[6]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(pol_se, PLN));
                       }
                       if (remote.equals("England")) {
                           mPercent.setText(percent.difference_remote_more_valuable(usa_se, eng_se, rate.getRate(usa_rates[7]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(eng_se, GBP));
                       }
                       if (remote.equals("South Korea")) {
                           mPercent.setText(percent.difference_home_more_valuable(usa_se, kor_se, rate.getRate(usa_rates[8]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(kor_se, KRW));
                       }
                   }

                   // MEXICO AS THE HOME COUNTRY
                   if (home.equals("Mexico")) {
                       mHc_salary.setText(avg_salary.averageSalary(mex_se, MXN));
                       if (remote.equals("United States")) {
                           mPercent.setText(percent.difference_remote_more_valuable(mex_se, usa_se, rate.getRate(mex_rates[0]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(usa_se, USD));
                       }
                       if (remote.equals("Canada")) {
                           mPercent.setText(percent.difference_remote_more_valuable(mex_se, can_se, rate.getRate(mex_rates[1]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(can_se, CAD));
                       }
                       if (remote.equals("Brazil")) {
                           mPercent.setText(percent.difference_remote_more_valuable(mex_se, bra_se, rate.getRate(mex_rates[2]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(bra_se, BRL));
                       }
                       if (remote.equals("Germany")) {
                           mPercent.setText(percent.difference_remote_more_valuable(mex_se, ger_se, rate.getRate(mex_rates[3]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(ger_se, EUR));
                       }
                       if (remote.equals("Japan")) {
                           mPercent.setText(percent.difference_home_more_valuable(mex_se, jap_se, rate.getRate(mex_rates[4]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(jap_se, JPY));
                       }
                       if (remote.equals("Nigeria")) {
                           mPercent.setText(percent.difference_home_more_valuable(mex_se, nig_se, rate.getRate(mex_rates[5]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(nig_se, NGN));
                       }
                       if (remote.equals("Poland")) {
                           mPercent.setText(percent.difference_remote_more_valuable(mex_se, pol_se, rate.getRate(mex_rates[6]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(pol_se, PLN));
                       }
                       if (remote.equals("England")) {
                           mPercent.setText(percent.difference_remote_more_valuable(mex_se, eng_se, rate.getRate(mex_rates[7]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(eng_se, GBP));
                       }
                       if (remote.equals("South Korea")) {
                           mPercent.setText(percent.difference_home_more_valuable(mex_se, kor_se, rate.getRate(mex_rates[8]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(kor_se, KRW));
                       }
                   }

                   // BRAZIL AS THE HOME COUNTRY
                   if (home.equals("Brazil")) {
                       mHc_salary.setText(avg_salary.averageSalary(bra_se, BRL));
                       if (remote.equals("United States")) {
                           mPercent.setText(percent.difference_remote_more_valuable(bra_se, usa_se, rate.getRate(bra_rates[0]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(usa_se, USD));
                       }
                       if (remote.equals("Canada")) {
                           mPercent.setText(percent.difference_remote_more_valuable(bra_se, can_se, rate.getRate(bra_rates[1]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(can_se, CAD));
                       }
                       if (remote.equals("Mexico")) {
                           mPercent.setText(percent.difference_home_more_valuable(bra_se, mex_se, rate.getRate(bra_rates[2]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(mex_se, MXN));
                       }
                       if (remote.equals("Germany")) {
                           mPercent.setText(percent.difference_remote_more_valuable(bra_se, ger_se, rate.getRate(bra_rates[3]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(ger_se, EUR));
                       }
                       if (remote.equals("Japan")) {
                           mPercent.setText(percent.difference_home_more_valuable(bra_se, jap_se, rate.getRate(bra_rates[4]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(jap_se, JPY));
                       }
                       if (remote.equals("Nigeria")) {
                           mPercent.setText(percent.difference_home_more_valuable(bra_se, nig_se, rate.getRate(bra_rates[5]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(nig_se, NGN));
                       }
                       if (remote.equals("Poland")) {
                           mPercent.setText(percent.difference_home_more_valuable(bra_se, pol_se, rate.getRate(bra_rates[6]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(pol_se, PLN));
                       }
                       if (remote.equals("England")) {
                           mPercent.setText(percent.difference_remote_more_valuable(bra_se, eng_se, rate.getRate(bra_rates[7]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(eng_se, GBP));
                       }
                       if (remote.equals("South Korea")) {
                           mPercent.setText(percent.difference_home_more_valuable(bra_se, kor_se, rate.getRate(bra_rates[8]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(kor_se, KRW));
                       }
                   }

                   // GERMANY AS THE HOME COUNTRY
                   if (home.equals("Germany")) {
                       mHc_salary.setText(avg_salary.averageSalary(ger_se, EUR));
                       if (remote.equals("United States")) {
                           mPercent.setText(percent.difference_home_more_valuable(ger_se, usa_se, rate.getRate(ger_rates[0]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(usa_se, USD));
                       }
                       if (remote.equals("Canada")) {
                           mPercent.setText(percent.difference_home_more_valuable(ger_se, can_se, rate.getRate(ger_rates[1]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(can_se, CAD));
                       }
                       if (remote.equals("Mexico")) {
                           mPercent.setText(percent.difference_home_more_valuable(ger_se, mex_se, rate.getRate(ger_rates[2]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(mex_se, MXN));
                       }
                       if (remote.equals("Brazil")) {
                           mPercent.setText(percent.difference_home_more_valuable(ger_se, bra_se, rate.getRate(ger_rates[3]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(bra_se, BRL));
                       }
                       if (remote.equals("Japan")) {
                           mPercent.setText(percent.difference_home_more_valuable(ger_se, jap_se, rate.getRate(ger_rates[4]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(jap_se, JPY));
                       }
                       if (remote.equals("Nigeria")) {
                           mPercent.setText(percent.difference_home_more_valuable(ger_se, nig_se, rate.getRate(ger_rates[5]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(nig_se, NGN));
                       }
                       if (remote.equals("Poland")) {
                           mPercent.setText(percent.difference_home_more_valuable(ger_se, pol_se, rate.getRate(ger_rates[6]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(pol_se, PLN));
                       }
                       if (remote.equals("England")) {
                           mPercent.setText(percent.difference_remote_more_valuable(ger_se, eng_se, rate.getRate(ger_rates[7]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(eng_se, GBP));
                       }
                       if (remote.equals("South Korea")) {
                           mPercent.setText(percent.difference_home_more_valuable(ger_se, kor_se, rate.getRate(ger_rates[8]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(kor_se, KRW));
                       }
                   }

                   // JAPAN AS THE HOME COUNTRY
                   if (home.equals("Japan")) {
                       mHc_salary.setText(percent.averageSalary(jap_se, JPY));
                       if (remote.equals("United States")) {
                           mPercent.setText(percent.difference_remote_more_valuable(jap_se, usa_se, rate.getRate(jap_rates[0]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(usa_se, USD));
                       }
                       if (remote.equals("Canada")) {
                           mPercent.setText(percent.difference_remote_more_valuable(jap_se, can_se, rate.getRate(jap_rates[1]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(can_se, CAD));
                       }
                       if (remote.equals("Mexico")) {
                           mPercent.setText(percent.difference_remote_more_valuable(jap_se, mex_se, rate.getRate(jap_rates[2]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(mex_se, MXN));
                       }
                       if (remote.equals("Brazil")) {
                           mPercent.setText(percent.difference_remote_more_valuable(jap_se, bra_se, rate.getRate(jap_rates[3]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(bra_se, BRL));
                       }
                       if (remote.equals("Germany")) {
                           mPercent.setText(percent.difference_remote_more_valuable(jap_se, ger_se, rate.getRate(jap_rates[4]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(ger_se, EUR));
                       }
                       if (remote.equals("Nigeria")) {
                           mPercent.setText(percent.difference_home_more_valuable(jap_se, nig_se, rate.getRate(jap_rates[5]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(nig_se, NGN));
                       }
                       if (remote.equals("Poland")) {
                           mPercent.setText(percent.difference_remote_more_valuable(jap_se, pol_se, rate.getRate(jap_rates[6]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(pol_se, PLN));
                       }
                       if (remote.equals("England")) {
                           mPercent.setText(percent.difference_remote_more_valuable(jap_se, eng_se, rate.getRate(jap_rates[7]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(eng_se, GBP));
                       }
                       if (remote.equals("South Korea")) {
                           mPercent.setText(percent.difference_home_more_valuable(jap_se, kor_se, rate.getRate(jap_rates[8]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(kor_se, KRW));
                       }
                   }

                   // NIGERIA AS THE HOME COUNTRY
                   if (home.equals("Nigeria")) {
                       mHc_salary.setText(avg_salary.averageSalary(nig_se, NGN));
                       if (remote.equals("United States")) {
                           mPercent.setText(percent.difference_remote_more_valuable(nig_se, usa_se, rate.getRate(nig_rates[0]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(usa_se, USD));
                       }
                       if (remote.equals("Canada")) {
                           mPercent.setText(percent.difference_remote_more_valuable(nig_se, can_se, rate.getRate(nig_rates[1]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(can_se, CAD));
                       }
                       if (remote.equals("Mexico")) {
                           mPercent.setText(percent.difference_remote_more_valuable(nig_se, mex_se, rate.getRate(nig_rates[2]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(mex_se, MXN));
                       }
                       if (remote.equals("Brazil")) {
                           mPercent.setText(percent.difference_remote_more_valuable(nig_se, bra_se, rate.getRate(nig_rates[3]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(bra_se, BRL));
                       }
                       if (remote.equals("Germany")) {
                           mPercent.setText(percent.difference_remote_more_valuable(nig_se, ger_se, rate.getRate(nig_rates[4]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(ger_se, EUR));
                       }
                       if (remote.equals("Japan")) {
                           mPercent.setText(percent.difference_remote_more_valuable(nig_se, jap_se, rate.getRate(nig_rates[5]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(jap_se, JPY));
                       }
                       if (remote.equals("Poland")) {
                           mPercent.setText(percent.difference_remote_more_valuable(nig_se, pol_se, rate.getRate(nig_rates[6]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(pol_se, PLN));
                       }
                       if (remote.equals("England")) {
                           mPercent.setText(percent.difference_remote_more_valuable(nig_se, eng_se, rate.getRate(nig_rates[7]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(eng_se, GBP));
                       }
                       if (remote.equals("South Korea")) {
                           mPercent.setText(percent.difference_home_more_valuable(nig_se, kor_se, rate.getRate(nig_rates[8]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(kor_se, KRW));
                       }
                   }

                   // POLAND AS THE HOME COUNTRY
                   if (home.equals("Poland")) {
                       mHc_salary.setText(avg_salary.averageSalary(pol_se, PLN));
                       if (remote.equals("United States")) {
                           mPercent.setText(percent.difference_remote_more_valuable(pol_se, usa_se, rate.getRate(pol_rates[0]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(usa_se, USD));
                       }
                       if (remote.equals("Canada")) {
                           mPercent.setText(percent.difference_remote_more_valuable(pol_se, can_se, rate.getRate(pol_rates[1]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(can_se, CAD));
                       }
                       if (remote.equals("Mexico")) {
                           mPercent.setText(percent.difference_home_more_valuable(pol_se, mex_se, rate.getRate(pol_rates[2]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(mex_se, MXN));
                       }
                       if (remote.equals("Brazil")) {
                           mPercent.setText(percent.difference_remote_more_valuable(pol_se, bra_se, rate.getRate(pol_rates[3]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(bra_se, BRL));
                       }
                       if (remote.equals("Germany")) {
                           mPercent.setText(percent.difference_remote_more_valuable(pol_se, ger_se, rate.getRate(pol_rates[4]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(ger_se, EUR));
                       }
                       if (remote.equals("Japan")) {
                           mPercent.setText(percent.difference_home_more_valuable(pol_se, jap_se, rate.getRate(pol_rates[5]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(jap_se, JPY));
                       }
                       if (remote.equals("Nigeria")) {
                           mPercent.setText(percent.difference_home_more_valuable(pol_se, nig_se, rate.getRate(pol_rates[6]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(nig_se, NGN));
                       }
                       if (remote.equals("England")) {
                           mPercent.setText(percent.difference_remote_more_valuable(pol_se, eng_se, rate.getRate(pol_rates[7]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(eng_se, GBP));
                       }
                       if (remote.equals("South Korea")) {
                           mPercent.setText(percent.difference_home_more_valuable(pol_se, kor_se, rate.getRate(pol_rates[8]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(kor_se, KRW));
                       }
                   }

                   // ENGLAND AS THE HOME COUNTRY
                   if (home.equals("England")) {
                       mHc_salary.setText(avg_salary.averageSalary(eng_se, GBP));
                       if (remote.equals("United States")) {
                           mPercent.setText(percent.difference_home_more_valuable(eng_se, usa_se, rate.getRate(eng_rates[0]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(usa_se, USD));
                       }
                       if (remote.equals("Canada")) {
                           mPercent.setText(percent.difference_home_more_valuable(eng_se, can_se, rate.getRate(eng_rates[1]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(can_se, CAD));
                       }
                       if (remote.equals("Mexico")) {
                           mPercent.setText(percent.difference_home_more_valuable(eng_se, mex_se, rate.getRate(eng_rates[2]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(mex_se, MXN));
                       }
                       if (remote.equals("Brazil")) {
                           mPercent.setText(percent.difference_home_more_valuable(eng_se, bra_se, rate.getRate(eng_rates[3]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(bra_se, BRL));
                       }
                       if (remote.equals("Germany")) {
                           mPercent.setText(percent.difference_home_more_valuable(eng_se, ger_se, rate.getRate(eng_rates[4]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(ger_se, EUR));
                       }
                       if (remote.equals("Japan")) {
                           mPercent.setText(percent.difference_home_more_valuable(eng_se, jap_se, rate.getRate(eng_rates[5]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(jap_se, JPY));
                       }
                       if (remote.equals("Nigeria")) {
                           mPercent.setText(percent.difference_home_more_valuable(eng_se, nig_se, rate.getRate(eng_rates[6]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(nig_se, NGN));
                       }
                       if (remote.equals("Poland")) {
                           mPercent.setText(percent.difference_home_more_valuable(eng_se, pol_se, rate.getRate(eng_rates[7]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(pol_se, PLN));
                       }
                       if (remote.equals("South Korea")) {
                           mPercent.setText(percent.difference_home_more_valuable(eng_se, kor_se, rate.getRate(eng_rates[8]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(kor_se, KRW));
                       }
                   }

                   // SOUTH KOREA AS THE HOME COUNTRY
                   if (home.equals("South Korea")) {
                       mHc_salary.setText(avg_salary.averageSalary(kor_se, KRW));
                       if (remote.equals("United States")) {
                           mPercent.setText(percent.difference_remote_more_valuable(kor_se, usa_se, rate.getRate(kor_rates[0]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(usa_se, USD));
                       }
                       if (remote.equals("Canada")) {
                           mPercent.setText(percent.difference_remote_more_valuable(kor_se, can_se, rate.getRate(kor_rates[1]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(can_se, CAD));
                       }
                       if (remote.equals("Mexico")) {
                           mPercent.setText(percent.difference_remote_more_valuable(kor_se, mex_se, rate.getRate(kor_rates[2]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(mex_se, MXN));
                       }
                       if (remote.equals("Brazil")) {
                           mPercent.setText(percent.difference_remote_more_valuable(kor_se, bra_se, rate.getRate(kor_rates[3]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(bra_se, BRL));
                       }
                       if (remote.equals("Germany")) {
                           mPercent.setText(percent.difference_remote_more_valuable(kor_se, ger_se, rate.getRate(kor_rates[4]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(ger_se, EUR));
                       }
                       if (remote.equals("Japan")) {
                           mPercent.setText(percent.difference_remote_more_valuable(kor_se, jap_se, rate.getRate(kor_rates[5]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(jap_se, JPY));
                       }
                       if (remote.equals("Nigeria")) {
                           mPercent.setText(percent.difference_remote_more_valuable(kor_se, nig_se, rate.getRate(kor_rates[6]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(nig_se, NGN));
                       }
                       if (remote.equals("Poland")) {
                           mPercent.setText(percent.difference_remote_more_valuable(kor_se, pol_se, rate.getRate(kor_rates[7]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(pol_se, PLN));
                       }
                       if (remote.equals("England")) {
                           mPercent.setText(percent.difference_remote_more_valuable(kor_se, eng_se, rate.getRate(kor_rates[8]), home, remote));
                           mRc_salary.setText(avg_salary.averageSalary(eng_se, GBP));
                       }
                   }
               }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }});
           }

//======================================================================================================================


            // SOFTWARE TESTER
            if (job.equals("Software tester")) {
                mRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    double can_st = dataSnapshot.child("Canada").child("Software tester").getValue(Double.class);
                    double usa_st = dataSnapshot.child("United States").child("Software tester").getValue(Double.class);
                    double mex_st = dataSnapshot.child("Mexico").child("Software tester").getValue(Double.class);
                    double bra_st = dataSnapshot.child("Brazil").child("Software tester").getValue(Double.class);
                    double ger_st = dataSnapshot.child("Germany").child("Software tester").getValue(Double.class);
                    double jap_st = dataSnapshot.child("Japan").child("Software tester").getValue(Double.class);
                    double nig_st = dataSnapshot.child("Nigeria").child("Software tester").getValue(Double.class);
                    double pol_st = dataSnapshot.child("Poland").child("Software tester").getValue(Double.class);
                    double eng_st = dataSnapshot.child("England").child("Software tester").getValue(Double.class);
                    double kor_st = dataSnapshot.child("South Korea").child("Software tester").getValue(Double.class);
                    // CANADA AS THE HOME COUNTRY
                    if (home.equals("Canada")) {
                        mHc_salary.setText(avg_salary.averageSalary(can_st, CAD));
                        if (remote.equals("United States")) {
                            mPercent.setText(percent.difference_remote_more_valuable(can_st, usa_st, rate.getRate(can_rates[0]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(usa_st, USD));
                        }
                        if (remote.equals("Mexico")) {
                            mPercent.setText(percent.difference_home_more_valuable(can_st, mex_st, rate.getRate(can_rates[1]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(mex_st, MXN));
                        }
                        if (remote.equals("Brazil")) {
                            mPercent.setText(percent.difference_home_more_valuable(can_st, bra_st, rate.getRate(can_rates[2]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(bra_st, BRL));
                        }
                        if (remote.equals("Germany")) {
                            mPercent.setText(percent.difference_remote_more_valuable(can_st, ger_st, rate.getRate(can_rates[3]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(ger_st, EUR));
                        }
                        if (remote.equals("Japan")) {
                            mPercent.setText(percent.difference_home_more_valuable(can_st, jap_st, rate.getRate(can_rates[4]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(jap_st, JPY));
                        }
                        if (remote.equals("Nigeria")) {
                            mPercent.setText(percent.difference_home_more_valuable(can_st, nig_st, rate.getRate(can_rates[5]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(nig_st, NGN));
                        }
                        if (remote.equals("Poland")) {
                            mPercent.setText(percent.difference_home_more_valuable(can_st, pol_st, rate.getRate(can_rates[6]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(pol_st, PLN));
                        }
                        if (remote.equals("England")) {
                            mPercent.setText(percent.difference_remote_more_valuable(can_st, eng_st, rate.getRate(can_rates[7]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(eng_st, GBP));
                        }
                        if (remote.equals("South Korea")) {
                            mPercent.setText(percent.difference_home_more_valuable(can_st, kor_st, rate.getRate(can_rates[8]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(kor_st, KRW));
                        }
                    }

                    // UNITED STATES AS THE HOME COUNTRY
                    if (home.equals("United States")) {
                        mHc_salary.setText(avg_salary.averageSalary(usa_st, USD));
                        if (remote.equals("Canada")) {
                            mPercent.setText(percent.difference_home_more_valuable(usa_st, can_st, rate.getRate(usa_rates[0]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(can_st, CAD));
                        }
                        if (remote.equals("Mexico")) {
                            mPercent.setText(percent.difference_home_more_valuable(usa_st, mex_st, rate.getRate(usa_rates[1]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(mex_st, MXN));
                        }
                        if (remote.equals("Brazil")) {
                            mPercent.setText(percent.difference_home_more_valuable(usa_st, bra_st, rate.getRate(usa_rates[2]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(bra_st, BRL));
                        }
                        if (remote.equals("Germany")) {
                            mPercent.setText(percent.difference_remote_more_valuable(usa_st, ger_st, rate.getRate(usa_rates[3]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(ger_st, EUR));
                        }
                        if (remote.equals("Japan")) {
                            mPercent.setText(percent.difference_home_more_valuable(usa_st, jap_st, rate.getRate(usa_rates[4]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(jap_st, JPY));
                        }
                        if (remote.equals("Nigeria")) {
                            mPercent.setText(percent.difference_home_more_valuable(usa_st, nig_st, rate.getRate(usa_rates[5]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(nig_st, NGN));
                        }
                        if (remote.equals("Poland")) {
                            mPercent.setText(percent.difference_home_more_valuable(usa_st, pol_st, rate.getRate(usa_rates[6]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(pol_st, PLN));
                        }
                        if (remote.equals("England")) {
                            mPercent.setText(percent.difference_remote_more_valuable(usa_st, eng_st, rate.getRate(usa_rates[7]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(eng_st, GBP));
                        }
                        if (remote.equals("South Korea")) {
                            mPercent.setText(percent.difference_home_more_valuable(usa_st, kor_st, rate.getRate(usa_rates[8]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(kor_st, KRW));
                        }
                    }

                    // MEXICO AS THE HOME COUNTRY
                    if (home.equals("Mexico")) {
                        mHc_salary.setText(avg_salary.averageSalary(mex_st, MXN));
                        if (remote.equals("United States")) {
                            mPercent.setText(percent.difference_remote_more_valuable(mex_st, usa_st, rate.getRate(mex_rates[0]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(usa_st, USD));
                        }
                        if (remote.equals("Canada")) {
                            mPercent.setText(percent.difference_remote_more_valuable(mex_st, can_st, rate.getRate(mex_rates[1]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(can_st, CAD));
                        }
                        if (remote.equals("Brazil")) {
                            mPercent.setText(percent.difference_remote_more_valuable(mex_st, bra_st, rate.getRate(mex_rates[2]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(bra_st, BRL));
                        }
                        if (remote.equals("Germany")) {
                            mPercent.setText(percent.difference_remote_more_valuable(mex_st, ger_st, rate.getRate(mex_rates[3]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(ger_st, EUR));
                        }
                        if (remote.equals("Japan")) {
                            mPercent.setText(percent.difference_home_more_valuable(mex_st, jap_st, rate.getRate(mex_rates[4]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(jap_st, JPY));
                        }
                        if (remote.equals("Nigeria")) {
                            mPercent.setText(percent.difference_home_more_valuable(mex_st, nig_st, rate.getRate(mex_rates[5]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(nig_st, NGN));
                        }
                        if (remote.equals("Poland")) {
                            mPercent.setText(percent.difference_remote_more_valuable(mex_st, pol_st, rate.getRate(mex_rates[6]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(pol_st, PLN));
                        }
                        if (remote.equals("England")) {
                            mPercent.setText(percent.difference_remote_more_valuable(mex_st, eng_st, rate.getRate(mex_rates[7]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(eng_st, GBP));
                        }
                        if (remote.equals("South Korea")) {
                            mPercent.setText(percent.difference_home_more_valuable(mex_st, kor_st, rate.getRate(mex_rates[8]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(kor_st, KRW));
                        }
                    }

                    // BRAZIL AS THE HOME COUNTRY
                    if (home.equals("Brazil")) {
                        mHc_salary.setText(avg_salary.averageSalary(bra_st, BRL));
                        if (remote.equals("United States")) {
                            mPercent.setText(percent.difference_remote_more_valuable(bra_st, usa_st, rate.getRate(bra_rates[0]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(usa_st, USD));
                        }
                        if (remote.equals("Canada")) {
                            mPercent.setText(percent.difference_remote_more_valuable(bra_st, can_st, rate.getRate(bra_rates[1]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(can_st, CAD));
                        }
                        if (remote.equals("Mexico")) {
                            mPercent.setText(percent.difference_home_more_valuable(bra_st, mex_st, rate.getRate(bra_rates[2]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(mex_st, MXN));
                        }
                        if (remote.equals("Germany")) {
                            mPercent.setText(percent.difference_remote_more_valuable(bra_st, ger_st, rate.getRate(bra_rates[3]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(ger_st, EUR));
                        }
                        if (remote.equals("Japan")) {
                            mPercent.setText(percent.difference_home_more_valuable(bra_st, jap_st, rate.getRate(bra_rates[4]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(jap_st, JPY));
                        }
                        if (remote.equals("Nigeria")) {
                            mPercent.setText(percent.difference_home_more_valuable(bra_st, nig_st, rate.getRate(bra_rates[5]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(nig_st, NGN));
                        }
                        if (remote.equals("Poland")) {
                            mPercent.setText(percent.difference_home_more_valuable(bra_st, pol_st, rate.getRate(bra_rates[6]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(pol_st, PLN));
                        }
                        if (remote.equals("England")) {
                            mPercent.setText(percent.difference_remote_more_valuable(bra_st, eng_st, rate.getRate(bra_rates[7]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(eng_st, GBP));
                        }
                        if (remote.equals("South Korea")) {
                            mPercent.setText(percent.difference_home_more_valuable(bra_st, kor_st, rate.getRate(bra_rates[8]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(kor_st, KRW));
                        }
                    }

                    // GERMANY AS THE HOME COUNTRY
                    if (home.equals("Germany")) {
                        mHc_salary.setText(avg_salary.averageSalary(ger_st, EUR));
                        if (remote.equals("United States")) {
                            mPercent.setText(percent.difference_home_more_valuable(ger_st, usa_st, rate.getRate(ger_rates[0]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(usa_st, USD));
                        }
                        if (remote.equals("Canada")) {
                            mPercent.setText(percent.difference_home_more_valuable(ger_st, can_st, rate.getRate(ger_rates[1]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(can_st, CAD));
                        }
                        if (remote.equals("Mexico")) {
                            mPercent.setText(percent.difference_home_more_valuable(ger_st, mex_st, rate.getRate(ger_rates[2]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(mex_st, MXN));
                        }
                        if (remote.equals("Brazil")) {
                            mPercent.setText(percent.difference_home_more_valuable(ger_st, bra_st, rate.getRate(ger_rates[3]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(bra_st, BRL));
                        }
                        if (remote.equals("Japan")) {
                            mPercent.setText(percent.difference_home_more_valuable(ger_st, jap_st, rate.getRate(ger_rates[4]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(jap_st, JPY));
                        }
                        if (remote.equals("Nigeria")) {
                            mPercent.setText(percent.difference_home_more_valuable(ger_st, nig_st, rate.getRate(ger_rates[5]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(nig_st, NGN));
                        }
                        if (remote.equals("Poland")) {
                            mPercent.setText(percent.difference_home_more_valuable(ger_st, pol_st, rate.getRate(ger_rates[6]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(pol_st, PLN));
                        }
                        if (remote.equals("England")) {
                            mPercent.setText(percent.difference_remote_more_valuable(ger_st, eng_st, rate.getRate(ger_rates[7]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(eng_st, GBP));
                        }
                        if (remote.equals("South Korea")) {
                            mPercent.setText(percent.difference_home_more_valuable(ger_st, kor_st, rate.getRate(ger_rates[8]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(kor_st, KRW));
                        }
                    }

                    // JAPAN AS THE HOME COUNTRY
                    if (home.equals("Japan")) {
                        mHc_salary.setText(avg_salary.averageSalary(jap_st, JPY));
                        if (remote.equals("United States")) {
                            mPercent.setText(percent.difference_remote_more_valuable(jap_st, usa_st, rate.getRate(jap_rates[0]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(usa_st, USD));
                        }
                        if (remote.equals("Canada")) {
                            mPercent.setText(percent.difference_remote_more_valuable(jap_st, can_st, rate.getRate(jap_rates[1]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(can_st, CAD));
                        }
                        if (remote.equals("Mexico")) {
                            mPercent.setText(percent.difference_remote_more_valuable(jap_st, mex_st, rate.getRate(jap_rates[2]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(mex_st, MXN));
                        }
                        if (remote.equals("Brazil")) {
                            mPercent.setText(percent.difference_remote_more_valuable(jap_st, bra_st, rate.getRate(jap_rates[3]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(bra_st, BRL));
                        }
                        if (remote.equals("Germany")) {
                            mPercent.setText(percent.difference_remote_more_valuable(jap_st, ger_st, rate.getRate(jap_rates[4]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(ger_st, EUR));
                        }
                        if (remote.equals("Nigeria")) {
                            mPercent.setText(percent.difference_home_more_valuable(jap_st, nig_st, rate.getRate(jap_rates[5]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(nig_st, NGN));
                        }
                        if (remote.equals("Poland")) {
                            mPercent.setText(percent.difference_remote_more_valuable(jap_st, pol_st, rate.getRate(jap_rates[6]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(pol_st, PLN));
                        }
                        if (remote.equals("England")) {
                            mPercent.setText(percent.difference_remote_more_valuable(jap_st, eng_st, rate.getRate(jap_rates[7]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(eng_st, GBP));
                        }
                        if (remote.equals("South Korea")) {
                            mPercent.setText(percent.difference_home_more_valuable(jap_st, kor_st, rate.getRate(jap_rates[8]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(kor_st, KRW));
                        }
                    }

                    // NIGERIA AS THE HOME COUNTRY
                    if (home.equals("Nigeria")) {
                        mHc_salary.setText(avg_salary.averageSalary(nig_st, NGN));
                        if (remote.equals("United States")) {
                            mPercent.setText(percent.difference_remote_more_valuable(nig_st, usa_st, rate.getRate(nig_rates[0]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(usa_st, USD));
                        }
                        if (remote.equals("Canada")) {
                            mPercent.setText(percent.difference_remote_more_valuable(nig_st, can_st, rate.getRate(nig_rates[1]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(can_st, CAD));
                        }
                        if (remote.equals("Mexico")) {
                            mPercent.setText(percent.difference_remote_more_valuable(nig_st, mex_st, rate.getRate(nig_rates[2]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(mex_st, MXN));
                        }
                        if (remote.equals("Brazil")) {
                            mPercent.setText(percent.difference_remote_more_valuable(nig_st, bra_st, rate.getRate(nig_rates[3]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(bra_st, BRL));
                        }
                        if (remote.equals("Germany")) {
                            mPercent.setText(percent.difference_remote_more_valuable(nig_st, ger_st, rate.getRate(nig_rates[4]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(ger_st, EUR));
                        }
                        if (remote.equals("Japan")) {
                            mPercent.setText(percent.difference_remote_more_valuable(nig_st, jap_st, rate.getRate(nig_rates[5]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(jap_st, JPY));
                        }
                        if (remote.equals("Poland")) {
                            mPercent.setText(percent.difference_remote_more_valuable(nig_st, pol_st, rate.getRate(nig_rates[6]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(pol_st, PLN));
                        }
                        if (remote.equals("England")) {
                            mPercent.setText(percent.difference_remote_more_valuable(nig_st, eng_st, rate.getRate(nig_rates[7]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(eng_st, GBP));
                        }
                        if (remote.equals("South Korea")) {
                            mPercent.setText(percent.difference_home_more_valuable(nig_st, kor_st, rate.getRate(nig_rates[8]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(kor_st, KRW));
                        }
                    }

                    // POLAND AS THE HOME COUNTRY
                    if (home.equals("Poland")) {
                        mHc_salary.setText(avg_salary.averageSalary(pol_st, PLN));
                        if (remote.equals("United States")) {
                            mPercent.setText(percent.difference_remote_more_valuable(pol_st, usa_st, rate.getRate(pol_rates[0]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(usa_st, USD));
                        }
                        if (remote.equals("Canada")) {
                            mPercent.setText(percent.difference_remote_more_valuable(pol_st, can_st, rate.getRate(pol_rates[1]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(can_st, CAD));
                        }
                        if (remote.equals("Mexico")) {
                            mPercent.setText(percent.difference_home_more_valuable(pol_st, mex_st, rate.getRate(pol_rates[2]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(mex_st, MXN));
                        }
                        if (remote.equals("Brazil")) {
                            mPercent.setText(percent.difference_remote_more_valuable(pol_st, bra_st, rate.getRate(pol_rates[3]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(bra_st, BRL));
                        }
                        if (remote.equals("Germany")) {
                            mPercent.setText(percent.difference_remote_more_valuable(pol_st, ger_st, rate.getRate(pol_rates[4]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(ger_st, EUR));
                        }
                        if (remote.equals("Japan")) {
                            mPercent.setText(percent.difference_home_more_valuable(pol_st, jap_st, rate.getRate(pol_rates[5]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(jap_st, JPY));
                        }
                        if (remote.equals("Nigeria")) {
                            mPercent.setText(percent.difference_home_more_valuable(pol_st, nig_st, rate.getRate(pol_rates[6]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(nig_st, NGN));
                        }
                        if (remote.equals("England")) {
                            mPercent.setText(percent.difference_remote_more_valuable(pol_st, eng_st, rate.getRate(pol_rates[7]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(eng_st, GBP));
                        }
                        if (remote.equals("South Korea")) {
                            mPercent.setText(percent.difference_home_more_valuable(pol_st, kor_st, rate.getRate(pol_rates[8]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(kor_st, KRW));
                        }
                    }

                    // ENGLAND AS THE HOME COUNTRY
                    if (home.equals("England")) {
                        mHc_salary.setText(avg_salary.averageSalary(eng_st, GBP));
                        if (remote.equals("United States")) {
                            mPercent.setText(percent.difference_home_more_valuable(eng_st, usa_st, rate.getRate(eng_rates[0]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(usa_st, USD));
                        }
                        if (remote.equals("Canada")) {
                            mPercent.setText(percent.difference_home_more_valuable(eng_st, can_st, rate.getRate(eng_rates[1]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(can_st, CAD));
                        }
                        if (remote.equals("Mexico")) {
                            mPercent.setText(percent.difference_home_more_valuable(eng_st, mex_st, rate.getRate(eng_rates[2]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(mex_st, MXN));
                        }
                        if (remote.equals("Brazil")) {
                            mPercent.setText(percent.difference_home_more_valuable(eng_st, bra_st, rate.getRate(eng_rates[3]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(bra_st, BRL));
                        }
                        if (remote.equals("Germany")) {
                            mPercent.setText(percent.difference_home_more_valuable(eng_st, ger_st, rate.getRate(eng_rates[4]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(ger_st, EUR));
                        }
                        if (remote.equals("Japan")) {
                            mPercent.setText(percent.difference_home_more_valuable(eng_st, jap_st, rate.getRate(eng_rates[5]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(jap_st, JPY));
                        }
                        if (remote.equals("Nigeria")) {
                            mPercent.setText(percent.difference_home_more_valuable(eng_st, nig_st, rate.getRate(eng_rates[6]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(nig_st, NGN));
                        }
                        if (remote.equals("Poland")) {
                            mPercent.setText(percent.difference_home_more_valuable(eng_st, pol_st, rate.getRate(eng_rates[7]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(pol_st, PLN));
                        }
                        if (remote.equals("South Korea")) {
                            mPercent.setText(percent.difference_home_more_valuable(eng_st, kor_st, rate.getRate(eng_rates[8]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(kor_st, KRW));
                        }
                    }

                    // SOUTH KOREA AS THE HOME COUNTRY
                    if (home.equals("South Korea")) {
                        mHc_salary.setText(avg_salary.averageSalary(kor_st, KRW));
                        if (remote.equals("United States")) {
                            mPercent.setText(percent.difference_remote_more_valuable(kor_st, usa_st, rate.getRate(kor_rates[0]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(usa_st, USD));
                        }
                        if (remote.equals("Canada")) {
                            mPercent.setText(percent.difference_remote_more_valuable(kor_st, can_st, rate.getRate(kor_rates[1]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(can_st, CAD));
                        }
                        if (remote.equals("Mexico")) {
                            mPercent.setText(percent.difference_remote_more_valuable(kor_st, mex_st, rate.getRate(kor_rates[2]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(mex_st, MXN));
                        }
                        if (remote.equals("Brazil")) {
                            mPercent.setText(percent.difference_remote_more_valuable(kor_st, bra_st, rate.getRate(kor_rates[3]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(bra_st, BRL));
                        }
                        if (remote.equals("Germany")) {
                            mPercent.setText(percent.difference_remote_more_valuable(kor_st, ger_st, rate.getRate(kor_rates[4]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(ger_st, EUR));
                        }
                        if (remote.equals("Japan")) {
                            mPercent.setText(percent.difference_remote_more_valuable(kor_st, jap_st, rate.getRate(kor_rates[5]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(jap_st, JPY));
                        }
                        if (remote.equals("Nigeria")) {
                            mPercent.setText(percent.difference_remote_more_valuable(kor_st, nig_st, rate.getRate(kor_rates[6]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(nig_st, NGN));
                        }
                        if (remote.equals("Poland")) {
                            mPercent.setText(percent.difference_remote_more_valuable(kor_st, pol_st, rate.getRate(kor_rates[7]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(pol_st, PLN));
                        }
                        if (remote.equals("England")) {
                            mPercent.setText(percent.difference_remote_more_valuable(kor_st, eng_st, rate.getRate(kor_rates[8]), home, remote));
                            mRc_salary.setText(avg_salary.averageSalary(eng_st, GBP));
                        }
                    }
                }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
            });
        }


//======================================================================================================================


            // UI DESIGNER
            if (job.equals("UI designer")) {
                mRootRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                double can_UId = dataSnapshot.child("Canada").child("UI designer").getValue(Double.class);
                double usa_UId = dataSnapshot.child("United States").child("UI designer").getValue(Double.class);
                double mex_UId = dataSnapshot.child("Mexico").child("UI designer").getValue(Double.class);
                double bra_UId = dataSnapshot.child("Brazil").child("UI designer").getValue(Double.class);
                double ger_UId = dataSnapshot.child("Germany").child("UI designer").getValue(Double.class);
                double jap_UId = dataSnapshot.child("Japan").child("UI designer").getValue(Double.class);
                double nig_UId = dataSnapshot.child("Nigeria").child("UI designer").getValue(Double.class);
                double pol_UId = dataSnapshot.child("Poland").child("UI designer").getValue(Double.class);
                double eng_UId = dataSnapshot.child("England").child("UI designer").getValue(Double.class);
                double kor_UId = dataSnapshot.child("South Korea").child("UI designer").getValue(Double.class);

                // CANADA AS THE HOME COUNTRY
                if (home.equals("Canada")) {
                    if (remote.equals("United States")) {
                        mPercent.setText(percent.difference_remote_more_valuable(can_UId, usa_UId, rate.getRate(can_rates[0]), home, remote));
                    }
                    if (remote.equals("Mexico")) {
                        mPercent.setText(percent.difference_home_more_valuable(can_UId, mex_UId, 13.85, home, remote));
                    }
                    if (remote.equals("Brazil")) {
                        mPercent.setText(percent.difference_home_more_valuable(can_UId, bra_UId, 2.41, home, remote));
                    }
                    if (remote.equals("Germany")) {
                        mPercent.setText(percent.difference_remote_more_valuable(can_UId, ger_UId, 1.52, home, remote));
                    }
                    if (remote.equals("Japan")) {
                        mPercent.setText(percent.difference_home_more_valuable(can_UId, jap_UId, 81.98, home, remote));
                    }
                    if (remote.equals("Nigeria")) {
                        mPercent.setText(percent.difference_home_more_valuable(can_UId, nig_UId, 233.40, home, remote));
                    }
                    if (remote.equals("Poland")) {
                        mPercent.setText(percent.difference_home_more_valuable(can_UId, pol_UId, 2.75, home, remote));
                    }
                    if (remote.equals("England")) {
                        mPercent.setText(percent.difference_remote_more_valuable(can_UId, eng_UId, 1.74, home, remote));
                    }
                    if (remote.equals("South Korea")) {
                        mPercent.setText(percent.difference_home_more_valuable(can_UId, kor_UId, 829.34, home, remote));
                    }
                }

                // UNITED STATES AS THE HOME COUNTRY
                if (home.equals("United States")) {
                    if (remote.equals("Canada")) {
                        mPercent.setText(percent.difference_home_more_valuable(usa_UId, can_UId, 1.35, home, remote));
                    }
                    if (remote.equals("Mexico")) {
                        mPercent.setText(percent.difference_home_more_valuable(usa_UId, mex_UId, 18.68, home, remote));
                    }
                    if (remote.equals("Brazil")) {
                        mPercent.setText(percent.difference_home_more_valuable(usa_UId, bra_UId, 3.25, home, remote));
                    }
                    if (remote.equals("Germany")) {
                        mPercent.setText(percent.difference_remote_more_valuable(usa_UId, ger_UId, 1.13, home, remote));
                    }
                    if (remote.equals("Japan")) {
                        mPercent.setText(percent.difference_home_more_valuable(usa_UId, jap_UId, 110.52, home, remote));
                    }
                    if (remote.equals("Nigeria")) {
                        mPercent.setText(percent.difference_home_more_valuable(usa_UId, nig_UId, 314.75, home, remote));
                    }
                    if (remote.equals("Poland")) {
                        mPercent.setText(percent.difference_home_more_valuable(usa_UId, pol_UId, 3.71, home, remote));
                    }
                    if (remote.equals("England")) {
                        mPercent.setText(percent.difference_remote_more_valuable(usa_UId, eng_UId, 1.29, home, remote));
                    }
                    if (remote.equals("South Korea")) {
                        mPercent.setText(percent.difference_home_more_valuable(usa_UId, kor_UId, 1118.40, home, remote));
                    }
                }

                // MEXICO AS THE HOME COUNTRY
                if (home.equals("Mexico")) {
                    if (remote.equals("United States")) {
                        mPercent.setText(percent.difference_remote_more_valuable(mex_UId, usa_UId, 18.68, home, remote));
                    }
                    if (remote.equals("Canada")) {
                        mPercent.setText(percent.difference_remote_more_valuable(mex_UId, can_UId, 13.85, home, remote));
                    }
                    if (remote.equals("Brazil")) {
                        mPercent.setText(percent.difference_remote_more_valuable(mex_UId, bra_UId, 5.75, home, remote));
                    }
                    if (remote.equals("Germany")) {
                        mPercent.setText(percent.difference_remote_more_valuable(mex_UId, ger_UId, 21.13, home, remote));
                    }
                    if (remote.equals("Japan")) {
                        mPercent.setText(percent.difference_home_more_valuable(mex_UId, jap_UId, 5.89, home, remote));
                    }
                    if (remote.equals("Nigeria")) {
                        mPercent.setText(percent.difference_home_more_valuable(mex_UId, nig_UId, 16.80, home, remote));
                    }
                    if (remote.equals("Poland")) {
                        mPercent.setText(percent.difference_remote_more_valuable(mex_UId, pol_UId, 5.05, home, remote));
                    }
                    if (remote.equals("England")) {
                        mPercent.setText(percent.difference_remote_more_valuable(mex_UId, eng_UId, 24.11, home, remote));
                    }
                    if (remote.equals("South Korea")) {
                        mPercent.setText(percent.difference_home_more_valuable(mex_UId, kor_UId, 59.67, home, remote));
                    }
                }

                // BRAZIL AS THE HOME COUNTRY
                if (home.equals("Brazil")) {
                    if (remote.equals("United States")) {
                        mPercent.setText(percent.difference_remote_more_valuable(bra_UId, usa_UId, 3.25, home, remote));
                    }
                    if (remote.equals("Canada")) {
                        mPercent.setText(percent.difference_remote_more_valuable(bra_UId, can_UId, 2.41, home, remote));
                    }
                    if (remote.equals("Mexico")) {
                        mPercent.setText(percent.difference_home_more_valuable(bra_UId, mex_UId, 5.75, home, remote));
                    }
                    if (remote.equals("Germany")) {
                        mPercent.setText(percent.difference_remote_more_valuable(bra_UId, ger_UId, 3.66, home, remote));
                    }
                    if (remote.equals("Japan")) {
                        mPercent.setText(percent.difference_home_more_valuable(bra_UId, jap_UId, 34.00, home, remote));
                    }
                    if (remote.equals("Nigeria")) {
                        mPercent.setText(percent.difference_home_more_valuable(bra_UId, nig_UId, 96.96, home, remote));
                    }
                    if (remote.equals("Poland")) {
                        mPercent.setText(percent.difference_home_more_valuable(bra_UId, pol_UId, 1.14, home, remote));
                    }
                    if (remote.equals("England")) {
                        mPercent.setText(percent.difference_remote_more_valuable(bra_UId, eng_UId, 4.18, home, remote));
                    }
                    if (remote.equals("South Korea")) {
                        mPercent.setText(percent.difference_home_more_valuable(bra_UId, kor_UId, 344.29, home, remote));
                    }
                }

                // GERMANY AS THE HOME COUNTRY
                if (home.equals("Germany")) {
                    if (remote.equals("United States")) {
                        mPercent.setText(percent.difference_home_more_valuable(ger_UId, usa_UId, 1.13, home, remote));
                    }
                    if (remote.equals("Canada")) {
                        mPercent.setText(percent.difference_home_more_valuable(ger_UId, can_UId, 1.52, home, remote));
                    }
                    if (remote.equals("Mexico")) {
                        mPercent.setText(percent.difference_home_more_valuable(ger_UId, mex_UId, 21.13, home, remote));
                    }
                    if (remote.equals("Brazil")) {
                        mPercent.setText(percent.difference_home_more_valuable(ger_UId, bra_UId, 3.66, home, remote));
                    }
                    if (remote.equals("Japan")) {
                        mPercent.setText(percent.difference_home_more_valuable(ger_UId, jap_UId, 124.49, home, remote));
                    }
                    if (remote.equals("Nigeria")) {
                        mPercent.setText(percent.difference_home_more_valuable(ger_UId, nig_UId, 354.90, home, remote));
                    }
                    if (remote.equals("Poland")) {
                        mPercent.setText(percent.difference_home_more_valuable(ger_UId, pol_UId, 4.18, home, remote));
                    }
                    if (remote.equals("England")) {
                        mPercent.setText(percent.difference_remote_more_valuable(ger_UId, eng_UId, 1.14, home, remote));
                    }
                    if (remote.equals("South Korea")) {
                        mPercent.setText(percent.difference_home_more_valuable(ger_UId, kor_UId, 1260.39, home, remote));
                    }
                }

                // JAPAN AS THE HOME COUNTRY
                if (home.equals("Japan")) {
                    if (remote.equals("United States")) {
                        mPercent.setText(percent.difference_remote_more_valuable(jap_UId, usa_UId, 110.52, home, remote));
                    }
                    if (remote.equals("Canada")) {
                        mPercent.setText(percent.difference_remote_more_valuable(jap_UId, can_UId, 81.98, home, remote));
                    }
                    if (remote.equals("Mexico")) {
                        mPercent.setText(percent.difference_remote_more_valuable(jap_UId, mex_UId, 5.89, home, remote));
                    }
                    if (remote.equals("Brazil")) {
                        mPercent.setText(percent.difference_remote_more_valuable(jap_UId, bra_UId, 34.00, home, remote));
                    }
                    if (remote.equals("Germany")) {
                        mPercent.setText(percent.difference_remote_more_valuable(jap_UId, ger_UId, 124.49, home, remote));
                    }
                    if (remote.equals("Nigeria")) {
                        mPercent.setText(percent.difference_home_more_valuable(jap_UId, nig_UId, 2.85, home, remote));
                    }
                    if (remote.equals("Poland")) {
                        mPercent.setText(percent.difference_remote_more_valuable(jap_UId, pol_UId, 29.74, home, remote));
                    }
                    if (remote.equals("England")) {
                        mPercent.setText(percent.difference_remote_more_valuable(jap_UId, eng_UId, 142.12, home, remote));
                    }
                    if (remote.equals("South Korea")) {
                        mPercent.setText(percent.difference_home_more_valuable(jap_UId, kor_UId, 10.12, home, remote));
                    }
                }

                // NIGERIA AS THE HOME COUNTRY
                if (home.equals("Nigeria")) {
                    if (remote.equals("United States")) {
                        mPercent.setText(percent.difference_remote_more_valuable(nig_UId, usa_UId, 314.75, home, remote));
                    }
                    if (remote.equals("Canada")) {
                        mPercent.setText(percent.difference_remote_more_valuable(nig_UId, can_UId, 233.40, home, remote));
                    }
                    if (remote.equals("Mexico")) {
                        mPercent.setText(percent.difference_remote_more_valuable(nig_UId, mex_UId, 16.80, home, remote));
                    }
                    if (remote.equals("Brazil")) {
                        mPercent.setText(percent.difference_remote_more_valuable(nig_UId, bra_UId, 96.96, home, remote));
                    }
                    if (remote.equals("Germany")) {
                        mPercent.setText(percent.difference_remote_more_valuable(nig_UId, ger_UId, 354.90, home, remote));
                    }
                    if (remote.equals("Japan")) {
                        mPercent.setText(percent.difference_remote_more_valuable(nig_UId, jap_UId, 2.85, home, remote));
                    }
                    if (remote.equals("Poland")) {
                        mPercent.setText(percent.difference_remote_more_valuable(nig_UId, pol_UId, 84.80, home, remote));
                    }
                    if (remote.equals("England")) {
                        mPercent.setText(percent.difference_remote_more_valuable(nig_UId, eng_UId, 404.99, home, remote));
                    }
                    if (remote.equals("South Korea")) {
                        mPercent.setText(percent.difference_home_more_valuable(nig_UId, kor_UId, 3.55, home, remote));
                    }
                }

                // POLAND AS THE HOME COUNTRY
                if (home.equals("Poland")) {
                    if (remote.equals("United States")) {
                        mPercent.setText(percent.difference_remote_more_valuable(pol_UId, usa_UId, 3.71, home, remote));
                    }
                    if (remote.equals("Canada")) {
                        mPercent.setText(percent.difference_remote_more_valuable(pol_UId, can_UId, 2.75, home, remote));
                    }
                    if (remote.equals("Mexico")) {
                        mPercent.setText(percent.difference_home_more_valuable(pol_UId, mex_UId, 5.05, home, remote));
                    }
                    if (remote.equals("Brazil")) {
                        mPercent.setText(percent.difference_remote_more_valuable(pol_UId, bra_UId, 1.14, home, remote));
                    }
                    if (remote.equals("Germany")) {
                        mPercent.setText(percent.difference_remote_more_valuable(pol_UId, ger_UId, 4.18, home, remote));
                    }
                    if (remote.equals("Japan")) {
                        mPercent.setText(percent.difference_home_more_valuable(pol_UId, jap_UId, 29.74, home, remote));
                    }
                    if (remote.equals("Nigeria")) {
                        mPercent.setText(percent.difference_home_more_valuable(pol_UId, nig_UId, 84.80, home, remote));
                    }
                    if (remote.equals("England")) {
                        mPercent.setText(percent.difference_remote_more_valuable(pol_UId, eng_UId, 4.78, home, remote));
                    }
                    if (remote.equals("South Korea")) {
                        mPercent.setText(percent.difference_home_more_valuable(pol_UId, kor_UId, 301.16, home, remote));
                    }
                }

                // ENGLAND AS THE HOME COUNTRY
                if (home.equals("England")) {
                    if (remote.equals("United States")) {
                        mPercent.setText(percent.difference_home_more_valuable(eng_UId, usa_UId, 1.29, home, remote));
                    }
                    if (remote.equals("Canada")) {
                        mPercent.setText(percent.difference_home_more_valuable(eng_UId, can_UId, 1.74, home, remote));
                    }
                    if (remote.equals("Mexico")) {
                        mPercent.setText(percent.difference_home_more_valuable(eng_UId, mex_UId, 24.11, home, remote));
                    }
                    if (remote.equals("Brazil")) {
                        mPercent.setText(percent.difference_home_more_valuable(eng_UId, bra_UId, 4.18, home, remote));
                    }
                    if (remote.equals("Germany")) {
                        mPercent.setText(percent.difference_home_more_valuable(eng_UId, ger_UId, 1.14, home, remote));
                    }
                    if (remote.equals("Japan")) {
                        mPercent.setText(percent.difference_home_more_valuable(eng_UId, jap_UId, 142.12, home, remote));
                    }
                    if (remote.equals("Nigeria")) {
                        mPercent.setText(percent.difference_home_more_valuable(eng_UId, nig_UId, 404.99, home, remote));
                    }
                    if (remote.equals("Poland")) {
                        mPercent.setText(percent.difference_home_more_valuable(eng_UId, pol_UId, 4.78, home, remote));
                    }
                    if (remote.equals("South Korea")) {
                        mPercent.setText(percent.difference_home_more_valuable(eng_UId, kor_UId, 1438.08, home, remote));
                    }
                }

                // SOUTH KOREA AS THE HOME COUNTRY
                if (home.equals("South Korea")) {
                    if (remote.equals("United States")) {
                        mPercent.setText(percent.difference_remote_more_valuable(kor_UId, usa_UId, 1118.40, home, remote));
                    }
                    if (remote.equals("Canada")) {
                        mPercent.setText(percent.difference_remote_more_valuable(kor_UId, can_UId, 829.34, home, remote));
                    }
                    if (remote.equals("Mexico")) {
                        mPercent.setText(percent.difference_remote_more_valuable(kor_UId, mex_UId, 59.67, home, remote));
                    }
                    if (remote.equals("Brazil")) {
                        mPercent.setText(percent.difference_remote_more_valuable(kor_UId, bra_UId, 344.29, home, remote));
                    }
                    if (remote.equals("Germany")) {
                        mPercent.setText(percent.difference_remote_more_valuable(kor_UId, ger_UId, 1260.39, home, remote));
                    }
                    if (remote.equals("Japan")) {
                        mPercent.setText(percent.difference_remote_more_valuable(kor_UId, jap_UId, 10.12, home, remote));
                    }
                    if (remote.equals("Nigeria")) {
                        mPercent.setText(percent.difference_remote_more_valuable(kor_UId, nig_UId, 3.55, home, remote));
                    }
                    if (remote.equals("Poland")) {
                        mPercent.setText(percent.difference_remote_more_valuable(kor_UId, pol_UId, 301.16, home, remote));
                    }
                    if (remote.equals("England")) {
                        mPercent.setText(percent.difference_remote_more_valuable(kor_UId, eng_UId, 1438.08, home, remote));
                    }
                }
            }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
        });
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

