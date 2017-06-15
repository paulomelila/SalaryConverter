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

    // Currencies codes
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

        // Getting the content of the string arrays for conversion rates stored in rates.xml
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

        // All widgets used in this activity
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

        // Applying Roboto typefaces to the TextViews
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

        // Getting the extras from the intent
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

        // Setting the conversion rate text
        if (home.equals("United States") && remote.equals("Canada") || home.equals("Canada") && remote.equals("United States")) {
            mRateText.setText(" 1 " + USD + " = " + usa_rates[0] + " " + CAD);
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

// Getting the average salaries from the database, so we can set the percent difference,
// home country salary and remote country salary texts.

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
                            mHc_salary.setText(avg_salary.averageSalary(can_UId, CAD));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_UId, usa_UId, rate.getRate(can_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UId, USD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UId, mex_UId, rate.getRate(can_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UId, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UId, bra_UId, rate.getRate(can_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UId, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_UId, ger_UId, rate.getRate(can_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UId, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UId, jap_UId, rate.getRate(can_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UId, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UId, nig_UId, rate.getRate(can_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UId, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UId, pol_UId, rate.getRate(can_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UId, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_UId, eng_UId, rate.getRate(can_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UId, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UId, kor_UId, rate.getRate(can_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UId, KRW));
                            }
                        }

                        // UNITED STATES AS THE HOME COUNTRY
                        if (home.equals("United States")) {
                            mHc_salary.setText(avg_salary.averageSalary(usa_UId, USD));
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UId, can_UId, rate.getRate(usa_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UId, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UId, mex_UId, rate.getRate(usa_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UId, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UId, bra_UId, rate.getRate(usa_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UId, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_UId, ger_UId, rate.getRate(usa_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UId, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UId, jap_UId, rate.getRate(usa_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UId, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UId, nig_UId, rate.getRate(usa_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UId, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UId, pol_UId, rate.getRate(usa_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UId, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_UId, eng_UId, rate.getRate(usa_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UId, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UId, kor_UId, rate.getRate(usa_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UId, KRW));
                            }
                        }

                        // MEXICO AS THE HOME COUNTRY
                        if (home.equals("Mexico")) {
                            mHc_salary.setText(avg_salary.averageSalary(mex_UId, MXN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UId, usa_UId, rate.getRate(mex_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UId, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UId, can_UId, rate.getRate(mex_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UId, CAD));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UId, bra_UId, rate.getRate(mex_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UId, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UId, ger_UId, rate.getRate(mex_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UId, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_UId, jap_UId, rate.getRate(mex_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UId, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_UId, nig_UId, rate.getRate(mex_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UId, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UId, pol_UId, rate.getRate(mex_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UId, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UId, eng_UId, rate.getRate(mex_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UId, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_UId, kor_UId, rate.getRate(mex_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UId, KRW));
                            }
                        }

                        // BRAZIL AS THE HOME COUNTRY
                        if (home.equals("Brazil")) {
                            mHc_salary.setText(avg_salary.averageSalary(bra_UId, BRL));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_UId, usa_UId, rate.getRate(bra_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UId, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_UId, can_UId, rate.getRate(bra_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UId, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_UId, mex_UId, rate.getRate(bra_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UId, MXN));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_UId, ger_UId, rate.getRate(bra_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UId, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_UId, jap_UId, rate.getRate(bra_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UId, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_UId, nig_UId, rate.getRate(bra_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UId, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_UId, pol_UId, rate.getRate(bra_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UId, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_UId, eng_UId, rate.getRate(bra_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UId, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_UId, kor_UId, rate.getRate(bra_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UId, KRW));
                            }
                        }

                        // GERMANY AS THE HOME COUNTRY
                        if (home.equals("Germany")) {
                            mHc_salary.setText(avg_salary.averageSalary(ger_UId, EUR));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UId, usa_UId, rate.getRate(ger_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UId, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UId, can_UId, rate.getRate(ger_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UId, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UId, mex_UId, rate.getRate(ger_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UId, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UId, bra_UId, rate.getRate(ger_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UId, BRL));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UId, jap_UId, rate.getRate(ger_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UId, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UId, nig_UId, rate.getRate(ger_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UId, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UId, pol_UId, rate.getRate(ger_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UId, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(ger_UId, eng_UId, rate.getRate(ger_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UId, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UId, kor_UId, rate.getRate(ger_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UId, KRW));
                            }
                        }

                        // JAPAN AS THE HOME COUNTRY
                        if (home.equals("Japan")) {
                            mHc_salary.setText(avg_salary.averageSalary(jap_UId, JPY));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UId, usa_UId, rate.getRate(jap_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UId, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UId, can_UId, rate.getRate(jap_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UId, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UId, mex_UId, rate.getRate(jap_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UId, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UId, bra_UId, rate.getRate(jap_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UId, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UId, ger_UId, rate.getRate(jap_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UId, EUR));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_UId, nig_UId, rate.getRate(jap_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UId, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UId, pol_UId, rate.getRate(jap_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UId, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UId, eng_UId, rate.getRate(jap_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UId, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_UId, kor_UId, rate.getRate(jap_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UId, KRW));
                            }
                        }

                        // NIGERIA AS THE HOME COUNTRY
                        if (home.equals("Nigeria")) {
                            mHc_salary.setText(avg_salary.averageSalary(nig_UId, NGN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UId, usa_UId, rate.getRate(nig_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UId, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UId, can_UId, rate.getRate(nig_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UId, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UId, mex_UId, rate.getRate(nig_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UId, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UId, bra_UId, rate.getRate(nig_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UId, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UId, ger_UId, rate.getRate(nig_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UId, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UId, jap_UId, rate.getRate(nig_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UId, JPY));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UId, pol_UId, rate.getRate(nig_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UId, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UId, eng_UId, rate.getRate(nig_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UId, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(nig_UId, kor_UId, rate.getRate(nig_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UId, KRW));
                            }
                        }

                        // POLAND AS THE HOME COUNTRY
                        if (home.equals("Poland")) {
                            mHc_salary.setText(avg_salary.averageSalary(pol_UId, PLN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_UId, usa_UId, rate.getRate(pol_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UId, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_UId, can_UId, rate.getRate(pol_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UId, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_UId, mex_UId, rate.getRate(pol_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UId, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_UId, bra_UId, rate.getRate(pol_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UId, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_UId, ger_UId, rate.getRate(pol_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UId, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_UId, jap_UId, rate.getRate(pol_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UId, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_UId, nig_UId, rate.getRate(pol_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UId, NGN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_UId, eng_UId, rate.getRate(pol_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UId, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_UId, kor_UId, rate.getRate(pol_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UId, KRW));
                            }
                        }

                        // ENGLAND AS THE HOME COUNTRY
                        if (home.equals("England")) {
                            mHc_salary.setText(avg_salary.averageSalary(eng_UId, GBP));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UId, usa_UId, rate.getRate(eng_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UId, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UId, can_UId, rate.getRate(eng_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UId, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UId, mex_UId, rate.getRate(eng_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UId, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UId, bra_UId, rate.getRate(eng_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UId, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UId, ger_UId, rate.getRate(eng_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UId, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UId, jap_UId, rate.getRate(eng_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UId, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UId, nig_UId, rate.getRate(eng_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UId, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UId, pol_UId, rate.getRate(eng_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UId, PLN));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UId, kor_UId, rate.getRate(eng_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UId, KRW));
                            }
                        }

                        // SOUTH KOREA AS THE HOME COUNTRY
                        if (home.equals("South Korea")) {
                            mHc_salary.setText(avg_salary.averageSalary(kor_UId, KRW));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UId, usa_UId, rate.getRate(kor_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UId, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UId, can_UId, rate.getRate(kor_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UId, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UId, mex_UId, rate.getRate(kor_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UId, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UId, bra_UId, rate.getRate(kor_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UId, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UId, ger_UId, rate.getRate(kor_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UId, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UId, jap_UId, rate.getRate(kor_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UId, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UId, nig_UId, rate.getRate(kor_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UId, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UId, pol_UId, rate.getRate(kor_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UId, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UId, eng_UId, rate.getRate(kor_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UId, GBP));
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
                mRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double can_wDev = dataSnapshot.child("Canada").child("Web developer").getValue(Double.class);
                        double usa_wDev = dataSnapshot.child("United States").child("Web developer").getValue(Double.class);
                        double mex_wDev = dataSnapshot.child("Mexico").child("Web developer").getValue(Double.class);
                        double bra_wDev = dataSnapshot.child("Brazil").child("Web developer").getValue(Double.class);
                        double ger_wDev = dataSnapshot.child("Germany").child("Web developer").getValue(Double.class);
                        double jap_wDev = dataSnapshot.child("Japan").child("Web developer").getValue(Double.class);
                        double nig_wDev = dataSnapshot.child("Nigeria").child("Web developer").getValue(Double.class);
                        double pol_wDev = dataSnapshot.child("Poland").child("Web developer").getValue(Double.class);
                        double eng_wDev = dataSnapshot.child("England").child("Web developer").getValue(Double.class);
                        double kor_wDev = dataSnapshot.child("South Korea").child("Web developer").getValue(Double.class);
                        // CANADA AS THE HOME COUNTRY
                        if (home.equals("Canada")) {
                            mHc_salary.setText(avg_salary.averageSalary(can_wDev, CAD));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_wDev, usa_wDev, rate.getRate(can_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDev, USD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDev, mex_wDev, rate.getRate(can_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDev, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDev, bra_wDev, rate.getRate(can_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDev, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_wDev, ger_wDev, rate.getRate(can_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDev, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDev, jap_wDev, rate.getRate(can_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDev, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDev, nig_wDev, rate.getRate(can_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDev, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDev, pol_wDev, rate.getRate(can_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDev, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_wDev, eng_wDev, rate.getRate(can_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDev, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDev, kor_wDev, rate.getRate(can_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDev, KRW));
                            }
                        }

                        // UNITED STATES AS THE HOME COUNTRY
                        if (home.equals("United States")) {
                            mHc_salary.setText(avg_salary.averageSalary(usa_wDev, USD));
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDev, can_wDev, rate.getRate(usa_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDev, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDev, mex_wDev, rate.getRate(usa_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDev, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDev, bra_wDev, rate.getRate(usa_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDev, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_wDev, ger_wDev, rate.getRate(usa_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDev, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDev, jap_wDev, rate.getRate(usa_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDev, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDev, nig_wDev, rate.getRate(usa_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDev, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDev, pol_wDev, rate.getRate(usa_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDev, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_wDev, eng_wDev, rate.getRate(usa_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDev, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDev, kor_wDev, rate.getRate(usa_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDev, KRW));
                            }
                        }

                        // MEXICO AS THE HOME COUNTRY
                        if (home.equals("Mexico")) {
                            mHc_salary.setText(avg_salary.averageSalary(mex_wDev, MXN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDev, usa_wDev, rate.getRate(mex_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDev, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDev, can_wDev, rate.getRate(mex_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDev, CAD));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDev, bra_wDev, rate.getRate(mex_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDev, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDev, ger_wDev, rate.getRate(mex_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDev, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_wDev, jap_wDev, rate.getRate(mex_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDev, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_wDev, nig_wDev, rate.getRate(mex_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDev, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDev, pol_wDev, rate.getRate(mex_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDev, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDev, eng_wDev, rate.getRate(mex_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDev, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_wDev, kor_wDev, rate.getRate(mex_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDev, KRW));
                            }
                        }

                        // BRAZIL AS THE HOME COUNTRY
                        if (home.equals("Brazil")) {
                            mHc_salary.setText(avg_salary.averageSalary(bra_wDev, BRL));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_wDev, usa_wDev, rate.getRate(bra_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDev, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_wDev, can_wDev, rate.getRate(bra_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDev, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_wDev, mex_wDev, rate.getRate(bra_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDev, MXN));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_wDev, ger_wDev, rate.getRate(bra_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDev, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_wDev, jap_wDev, rate.getRate(bra_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDev, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_wDev, nig_wDev, rate.getRate(bra_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDev, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_wDev, pol_wDev, rate.getRate(bra_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDev, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_wDev, eng_wDev, rate.getRate(bra_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDev, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_wDev, kor_wDev, rate.getRate(bra_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDev, KRW));
                            }
                        }

                        // GERMANY AS THE HOME COUNTRY
                        if (home.equals("Germany")) {
                            mHc_salary.setText(avg_salary.averageSalary(ger_wDev, EUR));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDev, usa_wDev, rate.getRate(ger_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDev, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDev, can_wDev, rate.getRate(ger_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDev, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDev, mex_wDev, rate.getRate(ger_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDev, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDev, bra_wDev, rate.getRate(ger_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDev, BRL));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDev, jap_wDev, rate.getRate(ger_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDev, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDev, nig_wDev, rate.getRate(ger_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDev, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDev, pol_wDev, rate.getRate(ger_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDev, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(ger_wDev, eng_wDev, rate.getRate(ger_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDev, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDev, kor_wDev, rate.getRate(ger_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDev, KRW));
                            }
                        }

                        // JAPAN AS THE HOME COUNTRY
                        if (home.equals("Japan")) {
                            mHc_salary.setText(avg_salary.averageSalary(jap_wDev, JPY));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDev, usa_wDev, rate.getRate(jap_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDev, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDev, can_wDev, rate.getRate(jap_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDev, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDev, mex_wDev, rate.getRate(jap_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDev, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDev, bra_wDev, rate.getRate(jap_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDev, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDev, ger_wDev, rate.getRate(jap_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDev, EUR));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_wDev, nig_wDev, rate.getRate(jap_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDev, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDev, pol_wDev, rate.getRate(jap_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDev, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDev, eng_wDev, rate.getRate(jap_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDev, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_wDev, kor_wDev, rate.getRate(jap_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDev, KRW));
                            }
                        }

                        // NIGERIA AS THE HOME COUNTRY
                        if (home.equals("Nigeria")) {
                            mHc_salary.setText(avg_salary.averageSalary(nig_wDev, NGN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDev, usa_wDev, rate.getRate(nig_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDev, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDev, can_wDev, rate.getRate(nig_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDev, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDev, mex_wDev, rate.getRate(nig_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDev, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDev, bra_wDev, rate.getRate(nig_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDev, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDev, ger_wDev, rate.getRate(nig_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDev, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDev, jap_wDev, rate.getRate(nig_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDev, JPY));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDev, pol_wDev, rate.getRate(nig_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDev, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDev, eng_wDev, rate.getRate(nig_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDev, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(nig_wDev, kor_wDev, rate.getRate(nig_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDev, KRW));
                            }
                        }

                        // POLAND AS THE HOME COUNTRY
                        if (home.equals("Poland")) {
                            mHc_salary.setText(avg_salary.averageSalary(pol_wDev, PLN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_wDev, usa_wDev, rate.getRate(pol_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDev, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_wDev, can_wDev, rate.getRate(pol_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDev, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_wDev, mex_wDev, rate.getRate(pol_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDev, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_wDev, bra_wDev, rate.getRate(pol_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDev, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_wDev, ger_wDev, rate.getRate(pol_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDev, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_wDev, jap_wDev, rate.getRate(pol_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDev, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_wDev, nig_wDev, rate.getRate(pol_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDev, NGN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_wDev, eng_wDev, rate.getRate(pol_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDev, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_wDev, kor_wDev, rate.getRate(pol_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDev, KRW));
                            }
                        }

                        // ENGLAND AS THE HOME COUNTRY
                        if (home.equals("England")) {
                            mHc_salary.setText(avg_salary.averageSalary(eng_wDev, GBP));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDev, usa_wDev, rate.getRate(eng_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDev, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDev, can_wDev, rate.getRate(eng_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDev, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDev, mex_wDev, rate.getRate(eng_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDev, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDev, bra_wDev, rate.getRate(eng_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDev, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDev, ger_wDev, rate.getRate(eng_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDev, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDev, jap_wDev, rate.getRate(eng_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDev, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDev, nig_wDev, rate.getRate(eng_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDev, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDev, pol_wDev, rate.getRate(eng_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDev, PLN));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDev, kor_wDev, rate.getRate(eng_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDev, KRW));
                            }
                        }

                        // SOUTH KOREA AS THE HOME COUNTRY
                        if (home.equals("South Korea")) {
                            mHc_salary.setText(avg_salary.averageSalary(kor_wDev, KRW));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDev, usa_wDev, rate.getRate(kor_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDev, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDev, can_wDev, rate.getRate(kor_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDev, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDev, mex_wDev, rate.getRate(kor_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDev, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDev, bra_wDev, rate.getRate(kor_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDev, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDev, ger_wDev, rate.getRate(kor_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDev, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDev, jap_wDev, rate.getRate(kor_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDev, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDev, nig_wDev, rate.getRate(kor_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDev, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDev, pol_wDev, rate.getRate(kor_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDev, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDev, eng_wDev, rate.getRate(kor_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDev, GBP));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }

//======================================================================================================================


            // DIGITAL MARKETING SPECIALIST
            if (job.equals("Digital marketing specialist")) {
                mRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double can_dms = dataSnapshot.child("Canada").child("Digital media specialist").getValue(Double.class);
                        double usa_dms = dataSnapshot.child("United States").child("Digital media specialist").getValue(Double.class);
                        double mex_dms = dataSnapshot.child("Mexico").child("Digital media specialist").getValue(Double.class);
                        double bra_dms = dataSnapshot.child("Brazil").child("Digital media specialist").getValue(Double.class);
                        double ger_dms = dataSnapshot.child("Germany").child("Digital media specialist").getValue(Double.class);
                        double jap_dms = dataSnapshot.child("Japan").child("Digital media specialist").getValue(Double.class);
                        double nig_dms = dataSnapshot.child("Nigeria").child("Digital media specialist").getValue(Double.class);
                        double pol_dms = dataSnapshot.child("Poland").child("Digital media specialist").getValue(Double.class);
                        double eng_dms = dataSnapshot.child("England").child("Digital media specialist").getValue(Double.class);
                        double kor_dms = dataSnapshot.child("South Korea").child("Digital media specialist").getValue(Double.class);
                        // CANADA AS THE HOME COUNTRY
                        if (home.equals("Canada")) {
                            mHc_salary.setText(avg_salary.averageSalary(can_dms, CAD));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_dms, usa_dms, rate.getRate(can_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_dms, USD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_dms, mex_dms, rate.getRate(can_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_dms, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_dms, bra_dms, rate.getRate(can_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_dms, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_dms, ger_dms, rate.getRate(can_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_dms, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_dms, jap_dms, rate.getRate(can_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_dms, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_dms, nig_dms, rate.getRate(can_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_dms, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_dms, pol_dms, rate.getRate(can_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_dms, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_dms, eng_dms, rate.getRate(can_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_dms, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_dms, kor_dms, rate.getRate(can_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_dms, KRW));
                            }
                        }

                        // UNITED STATES AS THE HOME COUNTRY
                        if (home.equals("United States")) {
                            mHc_salary.setText(avg_salary.averageSalary(usa_dms, USD));
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_dms, can_dms, rate.getRate(usa_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_dms, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_dms, mex_dms, rate.getRate(usa_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_dms, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_dms, bra_dms, rate.getRate(usa_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_dms, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_dms, ger_dms, rate.getRate(usa_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_dms, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_dms, jap_dms, rate.getRate(usa_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_dms, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_dms, nig_dms, rate.getRate(usa_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_dms, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_dms, pol_dms, rate.getRate(usa_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_dms, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_dms, eng_dms, rate.getRate(usa_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_dms, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_dms, kor_dms, rate.getRate(usa_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_dms, KRW));
                            }
                        }

                        // MEXICO AS THE HOME COUNTRY
                        if (home.equals("Mexico")) {
                            mHc_salary.setText(avg_salary.averageSalary(mex_dms, MXN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_dms, usa_dms, rate.getRate(mex_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_dms, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_dms, can_dms, rate.getRate(mex_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_dms, CAD));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_dms, bra_dms, rate.getRate(mex_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_dms, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_dms, ger_dms, rate.getRate(mex_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_dms, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_dms, jap_dms, rate.getRate(mex_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_dms, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_dms, nig_dms, rate.getRate(mex_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_dms, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_dms, pol_dms, rate.getRate(mex_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_dms, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_dms, eng_dms, rate.getRate(mex_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_dms, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_dms, kor_dms, rate.getRate(mex_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_dms, KRW));
                            }
                        }

                        // BRAZIL AS THE HOME COUNTRY
                        if (home.equals("Brazil")) {
                            mHc_salary.setText(avg_salary.averageSalary(bra_dms, BRL));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_dms, usa_dms, rate.getRate(bra_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_dms, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_dms, can_dms, rate.getRate(bra_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_dms, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_dms, mex_dms, rate.getRate(bra_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_dms, MXN));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_dms, ger_dms, rate.getRate(bra_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_dms, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_dms, jap_dms, rate.getRate(bra_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_dms, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_dms, nig_dms, rate.getRate(bra_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_dms, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_dms, pol_dms, rate.getRate(bra_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_dms, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_dms, eng_dms, rate.getRate(bra_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_dms, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_dms, kor_dms, rate.getRate(bra_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_dms, KRW));
                            }
                        }

                        // GERMANY AS THE HOME COUNTRY
                        if (home.equals("Germany")) {
                            mHc_salary.setText(avg_salary.averageSalary(ger_dms, EUR));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_dms, usa_dms, rate.getRate(ger_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_dms, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_dms, can_dms, rate.getRate(ger_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_dms, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_dms, mex_dms, rate.getRate(ger_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_dms, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_dms, bra_dms, rate.getRate(ger_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_dms, BRL));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_dms, jap_dms, rate.getRate(ger_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_dms, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_dms, nig_dms, rate.getRate(ger_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_dms, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_dms, pol_dms, rate.getRate(ger_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_dms, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(ger_dms, eng_dms, rate.getRate(ger_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_dms, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_dms, kor_dms, rate.getRate(ger_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_dms, KRW));
                            }
                        }

                        // JAPAN AS THE HOME COUNTRY
                        if (home.equals("Japan")) {
                            mHc_salary.setText(avg_salary.averageSalary(jap_dms, JPY));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_dms, usa_dms, rate.getRate(jap_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_dms, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_dms, can_dms, rate.getRate(jap_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_dms, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_dms, mex_dms, rate.getRate(jap_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_dms, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_dms, bra_dms, rate.getRate(jap_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_dms, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_dms, ger_dms, rate.getRate(jap_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_dms, EUR));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_dms, nig_dms, rate.getRate(jap_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_dms, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_dms, pol_dms, rate.getRate(jap_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_dms, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_dms, eng_dms, rate.getRate(jap_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_dms, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_dms, kor_dms, rate.getRate(jap_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_dms, KRW));
                            }
                        }

                        // NIGERIA AS THE HOME COUNTRY
                        if (home.equals("Nigeria")) {
                            mHc_salary.setText(avg_salary.averageSalary(nig_dms, NGN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_dms, usa_dms, rate.getRate(nig_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_dms, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_dms, can_dms, rate.getRate(nig_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_dms, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_dms, mex_dms, rate.getRate(nig_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_dms, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_dms, bra_dms, rate.getRate(nig_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_dms, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_dms, ger_dms, rate.getRate(nig_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_dms, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_dms, jap_dms, rate.getRate(nig_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_dms, JPY));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_dms, pol_dms, rate.getRate(nig_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_dms, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_dms, eng_dms, rate.getRate(nig_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_dms, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(nig_dms, kor_dms, rate.getRate(nig_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_dms, KRW));
                            }
                        }

                        // POLAND AS THE HOME COUNTRY
                        if (home.equals("Poland")) {
                            mHc_salary.setText(avg_salary.averageSalary(pol_dms, PLN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_dms, usa_dms, rate.getRate(pol_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_dms, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_dms, can_dms, rate.getRate(pol_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_dms, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_dms, mex_dms, rate.getRate(pol_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_dms, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_dms, bra_dms, rate.getRate(pol_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_dms, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_dms, ger_dms, rate.getRate(pol_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_dms, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_dms, jap_dms, rate.getRate(pol_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_dms, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_dms, nig_dms, rate.getRate(pol_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_dms, NGN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_dms, eng_dms, rate.getRate(pol_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_dms, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_dms, kor_dms, rate.getRate(pol_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_dms, KRW));
                            }
                        }

                        // ENGLAND AS THE HOME COUNTRY
                        if (home.equals("England")) {
                            mHc_salary.setText(avg_salary.averageSalary(eng_dms, GBP));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_dms, usa_dms, rate.getRate(eng_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_dms, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_dms, can_dms, rate.getRate(eng_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_dms, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_dms, mex_dms, rate.getRate(eng_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_dms, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_dms, bra_dms, rate.getRate(eng_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_dms, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_dms, ger_dms, rate.getRate(eng_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_dms, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_dms, jap_dms, rate.getRate(eng_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_dms, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_dms, nig_dms, rate.getRate(eng_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_dms, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_dms, pol_dms, rate.getRate(eng_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_dms, PLN));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_dms, kor_dms, rate.getRate(eng_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_dms, KRW));
                            }
                        }

                        // SOUTH KOREA AS THE HOME COUNTRY
                        if (home.equals("South Korea")) {
                            mHc_salary.setText(avg_salary.averageSalary(kor_dms, KRW));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_dms, usa_dms, rate.getRate(kor_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_dms, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_dms, can_dms, rate.getRate(kor_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_dms, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_dms, mex_dms, rate.getRate(kor_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_dms, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_dms, bra_dms, rate.getRate(kor_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_dms, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_dms, ger_dms, rate.getRate(kor_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_dms, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_dms, jap_dms, rate.getRate(kor_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_dms, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_dms, nig_dms, rate.getRate(kor_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_dms, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_dms, pol_dms, rate.getRate(kor_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_dms, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_dms, eng_dms, rate.getRate(kor_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_dms, GBP));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }


//======================================================================================================================


            // WEB DESIGNER
            if (job.equals("Web designer")) {
                mRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double can_wDes = dataSnapshot.child("Canada").child("Web designer").getValue(Double.class);
                        double usa_wDes = dataSnapshot.child("United States").child("Web designer").getValue(Double.class);
                        double mex_wDes = dataSnapshot.child("Mexico").child("Web designer").getValue(Double.class);
                        double bra_wDes = dataSnapshot.child("Brazil").child("Web designer").getValue(Double.class);
                        double ger_wDes = dataSnapshot.child("Germany").child("Web designer").getValue(Double.class);
                        double jap_wDes = dataSnapshot.child("Japan").child("Web designer").getValue(Double.class);
                        double nig_wDes = dataSnapshot.child("Nigeria").child("Web designer").getValue(Double.class);
                        double pol_wDes = dataSnapshot.child("Poland").child("Web designer").getValue(Double.class);
                        double eng_wDes = dataSnapshot.child("England").child("Web designer").getValue(Double.class);
                        double kor_wDes = dataSnapshot.child("South Korea").child("Web designer").getValue(Double.class);
                        // CANADA AS THE HOME COUNTRY
                        if (home.equals("Canada")) {
                            mHc_salary.setText(avg_salary.averageSalary(can_wDes, CAD));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_wDes, usa_wDes, rate.getRate(can_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDes, USD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDes, mex_wDes, rate.getRate(can_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDes, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDes, bra_wDes, rate.getRate(can_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDes, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_wDes, ger_wDes, rate.getRate(can_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDes, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDes, jap_wDes, rate.getRate(can_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDes, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDes, nig_wDes, rate.getRate(can_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDes, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDes, pol_wDes, rate.getRate(can_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDes, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_wDes, eng_wDes, rate.getRate(can_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDes, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_wDes, kor_wDes, rate.getRate(can_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDes, KRW));
                            }
                        }

                        // UNITED STATES AS THE HOME COUNTRY
                        if (home.equals("United States")) {
                            mHc_salary.setText(avg_salary.averageSalary(usa_wDes, USD));
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDes, can_wDes, rate.getRate(usa_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDes, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDes, mex_wDes, rate.getRate(usa_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDes, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDes, bra_wDes, rate.getRate(usa_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDes, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_wDes, ger_wDes, rate.getRate(usa_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDes, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDes, jap_wDes, rate.getRate(usa_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDes, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDes, nig_wDes, rate.getRate(usa_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDes, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDes, pol_wDes, rate.getRate(usa_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDes, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_wDes, eng_wDes, rate.getRate(usa_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDes, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_wDes, kor_wDes, rate.getRate(usa_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDes, KRW));
                            }
                        }

                        // MEXICO AS THE HOME COUNTRY
                        if (home.equals("Mexico")) {
                            mHc_salary.setText(avg_salary.averageSalary(mex_wDes, MXN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDes, usa_wDes, rate.getRate(mex_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDes, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDes, can_wDes, rate.getRate(mex_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDes, CAD));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDes, bra_wDes, rate.getRate(mex_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDes, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDes, ger_wDes, rate.getRate(mex_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDes, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_wDes, jap_wDes, rate.getRate(mex_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDes, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_wDes, nig_wDes, rate.getRate(mex_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDes, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDes, pol_wDes, rate.getRate(mex_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDes, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_wDes, eng_wDes, rate.getRate(mex_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDes, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_wDes, kor_wDes, rate.getRate(mex_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDes, KRW));
                            }
                        }

                        // BRAZIL AS THE HOME COUNTRY
                        if (home.equals("Brazil")) {
                            mHc_salary.setText(avg_salary.averageSalary(bra_wDes, BRL));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_wDes, usa_wDes, rate.getRate(bra_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDes, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_wDes, can_wDes, rate.getRate(bra_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDes, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_wDes, mex_wDes, rate.getRate(bra_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDes, MXN));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_wDes, ger_wDes, rate.getRate(bra_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDes, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_wDes, jap_wDes, rate.getRate(bra_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDes, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_wDes, nig_wDes, rate.getRate(bra_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDes, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_wDes, pol_wDes, rate.getRate(bra_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDes, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_wDes, eng_wDes, rate.getRate(bra_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDes, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_wDes, kor_wDes, rate.getRate(bra_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDes, KRW));
                            }
                        }

                        // GERMANY AS THE HOME COUNTRY
                        if (home.equals("Germany")) {
                            mHc_salary.setText(avg_salary.averageSalary(ger_wDes, EUR));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDes, usa_wDes, rate.getRate(ger_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDes, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDes, can_wDes, rate.getRate(ger_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDes, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDes, mex_wDes, rate.getRate(ger_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDes, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDes, bra_wDes, rate.getRate(ger_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDes, BRL));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDes, jap_wDes, rate.getRate(ger_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDes, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDes, nig_wDes, rate.getRate(ger_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDes, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDes, pol_wDes, rate.getRate(ger_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDes, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(ger_wDes, eng_wDes, rate.getRate(ger_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDes, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_wDes, kor_wDes, rate.getRate(ger_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDes, KRW));
                            }
                        }

                        // JAPAN AS THE HOME COUNTRY
                        if (home.equals("Japan")) {
                            mHc_salary.setText(avg_salary.averageSalary(jap_wDes, JPY));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDes, usa_wDes, rate.getRate(jap_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDes, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDes, can_wDes, rate.getRate(jap_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDes, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDes, mex_wDes, rate.getRate(jap_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDes, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDes, bra_wDes, rate.getRate(jap_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDes, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDes, ger_wDes, rate.getRate(jap_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDes, EUR));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_wDes, nig_wDes, rate.getRate(jap_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDes, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDes, pol_wDes, rate.getRate(jap_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDes, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_wDes, eng_wDes, rate.getRate(jap_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDes, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_wDes, kor_wDes, rate.getRate(jap_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDes, KRW));
                            }
                        }

                        // NIGERIA AS THE HOME COUNTRY
                        if (home.equals("Nigeria")) {
                            mHc_salary.setText(avg_salary.averageSalary(nig_wDes, NGN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDes, usa_wDes, rate.getRate(nig_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDes, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDes, can_wDes, rate.getRate(nig_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDes, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDes, mex_wDes, rate.getRate(nig_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDes, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDes, bra_wDes, rate.getRate(nig_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDes, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDes, ger_wDes, rate.getRate(nig_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDes, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDes, jap_wDes, rate.getRate(nig_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDes, JPY));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDes, pol_wDes, rate.getRate(nig_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDes, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_wDes, eng_wDes, rate.getRate(nig_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDes, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(nig_wDes, kor_wDes, rate.getRate(nig_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDes, KRW));
                            }
                        }

                        // POLAND AS THE HOME COUNTRY
                        if (home.equals("Poland")) {
                            mHc_salary.setText(avg_salary.averageSalary(pol_wDes, PLN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_wDes, usa_wDes, rate.getRate(pol_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDes, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_wDes, can_wDes, rate.getRate(pol_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDes, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_wDes, mex_wDes, rate.getRate(pol_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDes, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_wDes, bra_wDes, rate.getRate(pol_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDes, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_wDes, ger_wDes, rate.getRate(pol_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDes, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_wDes, jap_wDes, rate.getRate(pol_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDes, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_wDes, nig_wDes, rate.getRate(pol_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDes, NGN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_wDes, eng_wDes, rate.getRate(pol_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDes, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_wDes, kor_wDes, rate.getRate(pol_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDes, KRW));
                            }
                        }

                        // ENGLAND AS THE HOME COUNTRY
                        if (home.equals("England")) {
                            mHc_salary.setText(avg_salary.averageSalary(eng_wDes, GBP));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDes, usa_wDes, rate.getRate(eng_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDes, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDes, can_wDes, rate.getRate(eng_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDes, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDes, mex_wDes, rate.getRate(eng_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDes, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDes, bra_wDes, rate.getRate(eng_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDes, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDes, ger_wDes, rate.getRate(eng_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDes, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDes, jap_wDes, rate.getRate(eng_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDes, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDes, nig_wDes, rate.getRate(eng_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDes, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDes, pol_wDes, rate.getRate(eng_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDes, PLN));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_wDes, kor_wDes, rate.getRate(eng_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_wDes, KRW));
                            }
                        }

                        // SOUTH KOREA AS THE HOME COUNTRY
                        if (home.equals("South Korea")) {
                            mHc_salary.setText(avg_salary.averageSalary(kor_wDes, KRW));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDes, usa_wDes, rate.getRate(kor_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_wDes, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDes, can_wDes, rate.getRate(kor_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_wDes, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDes, mex_wDes, rate.getRate(kor_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_wDes, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDes, bra_wDes, rate.getRate(kor_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_wDes, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDes, ger_wDes, rate.getRate(kor_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_wDes, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDes, jap_wDes, rate.getRate(kor_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_wDes, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDes, nig_wDes, rate.getRate(kor_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_wDes, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDes, pol_wDes, rate.getRate(kor_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_wDes, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_wDes, eng_wDes, rate.getRate(kor_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_wDes, GBP));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }


//======================================================================================================================


            // MOBILE DEVELOPER
            if (job.equals("Mobile developer")) {
                mRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double can_md = dataSnapshot.child("Canada").child("Mobile developer").getValue(Double.class);
                        double usa_md = dataSnapshot.child("United States").child("Mobile developer").getValue(Double.class);
                        double mex_md = dataSnapshot.child("Mexico").child("Mobile developer").getValue(Double.class);
                        double bra_md = dataSnapshot.child("Brazil").child("Mobile developer").getValue(Double.class);
                        double ger_md = dataSnapshot.child("Germany").child("Mobile developer").getValue(Double.class);
                        double jap_md = dataSnapshot.child("Japan").child("Mobile developer").getValue(Double.class);
                        double nig_md = dataSnapshot.child("Nigeria").child("Mobile developer").getValue(Double.class);
                        double pol_md = dataSnapshot.child("Poland").child("Mobile developer").getValue(Double.class);
                        double eng_md = dataSnapshot.child("England").child("Mobile developer").getValue(Double.class);
                        double kor_md = dataSnapshot.child("South Korea").child("Mobile developer").getValue(Double.class);
                        // CANADA AS THE HOME COUNTRY
                        if (home.equals("Canada")) {
                            mHc_salary.setText(avg_salary.averageSalary(can_md, CAD));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_md, usa_md, rate.getRate(can_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_md, USD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_md, mex_md, rate.getRate(can_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_md, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_md, bra_md, rate.getRate(can_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_md, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_md, ger_md, rate.getRate(can_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_md, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_md, jap_md, rate.getRate(can_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_md, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_md, nig_md, rate.getRate(can_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_md, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_md, pol_md, rate.getRate(can_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_md, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_md, eng_md, rate.getRate(can_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_md, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_md, kor_md, rate.getRate(can_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_md, KRW));
                            }
                        }

                        // UNITED STATES AS THE HOME COUNTRY
                        if (home.equals("United States")) {
                            mHc_salary.setText(avg_salary.averageSalary(usa_md, USD));
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_md, can_md, rate.getRate(usa_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_md, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_md, mex_md, rate.getRate(usa_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_md, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_md, bra_md, rate.getRate(usa_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_md, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_md, ger_md, rate.getRate(usa_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_md, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_md, jap_md, rate.getRate(usa_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_md, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_md, nig_md, rate.getRate(usa_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_md, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_md, pol_md, rate.getRate(usa_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_md, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_md, eng_md, rate.getRate(usa_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_md, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_md, kor_md, rate.getRate(usa_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_md, KRW));
                            }
                        }

                        // MEXICO AS THE HOME COUNTRY
                        if (home.equals("Mexico")) {
                            mHc_salary.setText(avg_salary.averageSalary(mex_md, MXN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_md, usa_md, rate.getRate(mex_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_md, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_md, can_md, rate.getRate(mex_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_md, CAD));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_md, bra_md, rate.getRate(mex_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_md, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_md, ger_md, rate.getRate(mex_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_md, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_md, jap_md, rate.getRate(mex_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_md, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_md, nig_md, rate.getRate(mex_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_md, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_md, pol_md, rate.getRate(mex_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_md, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_md, eng_md, rate.getRate(mex_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_md, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_md, kor_md, rate.getRate(mex_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_md, KRW));
                            }
                        }

                        // BRAZIL AS THE HOME COUNTRY
                        if (home.equals("Brazil")) {
                            mHc_salary.setText(avg_salary.averageSalary(bra_md, BRL));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_md, usa_md, rate.getRate(bra_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_md, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_md, can_md, rate.getRate(bra_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_md, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_md, mex_md, rate.getRate(bra_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_md, MXN));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_md, ger_md, rate.getRate(bra_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_md, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_md, jap_md, rate.getRate(bra_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_md, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_md, nig_md, rate.getRate(bra_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_md, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_md, pol_md, rate.getRate(bra_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_md, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_md, eng_md, rate.getRate(bra_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_md, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_md, kor_md, rate.getRate(bra_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_md, KRW));
                            }
                        }

                        // GERMANY AS THE HOME COUNTRY
                        if (home.equals("Germany")) {
                            mHc_salary.setText(avg_salary.averageSalary(ger_md, EUR));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_md, usa_md, rate.getRate(ger_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_md, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_md, can_md, rate.getRate(ger_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_md, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_md, mex_md, rate.getRate(ger_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_md, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_md, bra_md, rate.getRate(ger_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_md, BRL));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_md, jap_md, rate.getRate(ger_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_md, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_md, nig_md, rate.getRate(ger_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_md, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_md, pol_md, rate.getRate(ger_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_md, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(ger_md, eng_md, rate.getRate(ger_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_md, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_md, kor_md, rate.getRate(ger_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_md, KRW));
                            }
                        }

                        // JAPAN AS THE HOME COUNTRY
                        if (home.equals("Japan")) {
                            mHc_salary.setText(avg_salary.averageSalary(jap_md, JPY));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_md, usa_md, rate.getRate(jap_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_md, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_md, can_md, rate.getRate(jap_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_md, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_md, mex_md, rate.getRate(jap_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_md, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_md, bra_md, rate.getRate(jap_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_md, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_md, ger_md, rate.getRate(jap_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_md, EUR));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_md, nig_md, rate.getRate(jap_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_md, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_md, pol_md, rate.getRate(jap_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_md, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_md, eng_md, rate.getRate(jap_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_md, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_md, kor_md, rate.getRate(jap_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_md, KRW));
                            }
                        }

                        // NIGERIA AS THE HOME COUNTRY
                        if (home.equals("Nigeria")) {
                            mHc_salary.setText(avg_salary.averageSalary(nig_md, NGN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_md, usa_md, rate.getRate(nig_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_md, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_md, can_md, rate.getRate(nig_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_md, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_md, mex_md, rate.getRate(nig_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_md, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_md, bra_md, rate.getRate(nig_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_md, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_md, ger_md, rate.getRate(nig_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_md, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_md, jap_md, rate.getRate(nig_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_md, JPY));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_md, pol_md, rate.getRate(nig_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_md, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_md, eng_md, rate.getRate(nig_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_md, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(nig_md, kor_md, rate.getRate(nig_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_md, KRW));
                            }
                        }

                        // POLAND AS THE HOME COUNTRY
                        if (home.equals("Poland")) {
                            mHc_salary.setText(avg_salary.averageSalary(pol_md, PLN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_md, usa_md, rate.getRate(pol_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_md, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_md, can_md, rate.getRate(pol_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_md, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_md, mex_md, rate.getRate(pol_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_md, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_md, bra_md, rate.getRate(pol_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_md, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_md, ger_md, rate.getRate(pol_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_md, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_md, jap_md, rate.getRate(pol_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_md, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_md, nig_md, rate.getRate(pol_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_md, NGN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_md, eng_md, rate.getRate(pol_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_md, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_md, kor_md, rate.getRate(pol_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_md, KRW));
                            }
                        }

                        // ENGLAND AS THE HOME COUNTRY
                        if (home.equals("England")) {
                            mHc_salary.setText(avg_salary.averageSalary(eng_md, GBP));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_md, usa_md, rate.getRate(eng_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_md, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_md, can_md, rate.getRate(eng_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_md, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_md, mex_md, rate.getRate(eng_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_md, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_md, bra_md, rate.getRate(eng_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_md, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_md, ger_md, rate.getRate(eng_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_md, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_md, jap_md, rate.getRate(eng_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_md, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_md, nig_md, rate.getRate(eng_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_md, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_md, pol_md, rate.getRate(eng_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_md, PLN));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_md, kor_md, rate.getRate(eng_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_md, KRW));
                            }
                        }

                        // SOUTH KOREA AS THE HOME COUNTRY
                        if (home.equals("South Korea")) {
                            mHc_salary.setText(avg_salary.averageSalary(kor_md, KRW));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_md, usa_md, rate.getRate(kor_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_md, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_md, can_md, rate.getRate(kor_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_md, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_md, mex_md, rate.getRate(kor_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_md, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_md, bra_md, rate.getRate(kor_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_md, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_md, ger_md, rate.getRate(kor_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_md, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_md, jap_md, rate.getRate(kor_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_md, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_md, nig_md, rate.getRate(kor_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_md, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_md, pol_md, rate.getRate(kor_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_md, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_md, eng_md, rate.getRate(kor_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_md, GBP));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }


//======================================================================================================================


            // GAME DEVELOPER
            if (job.equals("Game developer")) {
                mRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double can_gd = dataSnapshot.child("Canada").child("Game developer").getValue(Double.class);
                        double usa_gd = dataSnapshot.child("United States").child("Game developer").getValue(Double.class);
                        double mex_gd = dataSnapshot.child("Mexico").child("Game developer").getValue(Double.class);
                        double bra_gd = dataSnapshot.child("Brazil").child("Game developer").getValue(Double.class);
                        double ger_gd = dataSnapshot.child("Germany").child("Game developer").getValue(Double.class);
                        double jap_gd = dataSnapshot.child("Japan").child("Game developer").getValue(Double.class);
                        double nig_gd = dataSnapshot.child("Nigeria").child("Game developer").getValue(Double.class);
                        double pol_gd = dataSnapshot.child("Poland").child("Game developer").getValue(Double.class);
                        double eng_gd = dataSnapshot.child("England").child("Game developer").getValue(Double.class);
                        double kor_gd = dataSnapshot.child("South Korea").child("Game developer").getValue(Double.class);
                        // CANADA AS THE HOME COUNTRY
                        if (home.equals("Canada")) {
                            mHc_salary.setText(avg_salary.averageSalary(can_gd, CAD));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_gd, usa_gd, rate.getRate(can_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_gd, USD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_gd, mex_gd, rate.getRate(can_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_gd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_gd, bra_gd, rate.getRate(can_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_gd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_gd, ger_gd, rate.getRate(can_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_gd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_gd, jap_gd, rate.getRate(can_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_gd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_gd, nig_gd, rate.getRate(can_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_gd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_gd, pol_gd, rate.getRate(can_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_gd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_gd, eng_gd, rate.getRate(can_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_gd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_gd, kor_gd, rate.getRate(can_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_gd, KRW));
                            }
                        }

                        // UNITED STATES AS THE HOME COUNTRY
                        if (home.equals("United States")) {
                            mHc_salary.setText(avg_salary.averageSalary(usa_gd, USD));
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_gd, can_gd, rate.getRate(usa_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_gd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_gd, mex_gd, rate.getRate(usa_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_gd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_gd, bra_gd, rate.getRate(usa_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_gd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_gd, ger_gd, rate.getRate(usa_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_gd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_gd, jap_gd, rate.getRate(usa_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_gd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_gd, nig_gd, rate.getRate(usa_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_gd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_gd, pol_gd, rate.getRate(usa_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_gd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_gd, eng_gd, rate.getRate(usa_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_gd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_gd, kor_gd, rate.getRate(usa_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_gd, KRW));
                            }
                        }

                        // MEXICO AS THE HOME COUNTRY
                        if (home.equals("Mexico")) {
                            mHc_salary.setText(avg_salary.averageSalary(mex_gd, MXN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_gd, usa_gd, rate.getRate(mex_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_gd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_gd, can_gd, rate.getRate(mex_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_gd, CAD));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_gd, bra_gd, rate.getRate(mex_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_gd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_gd, ger_gd, rate.getRate(mex_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_gd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_gd, jap_gd, rate.getRate(mex_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_gd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_gd, nig_gd, rate.getRate(mex_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_gd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_gd, pol_gd, rate.getRate(mex_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_gd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_gd, eng_gd, rate.getRate(mex_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_gd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_gd, kor_gd, rate.getRate(mex_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_gd, KRW));
                            }
                        }

                        // BRAZIL AS THE HOME COUNTRY
                        if (home.equals("Brazil")) {
                            mHc_salary.setText(avg_salary.averageSalary(bra_gd, BRL));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_gd, usa_gd, rate.getRate(bra_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_gd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_gd, can_gd, rate.getRate(bra_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_gd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_gd, mex_gd, rate.getRate(bra_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_gd, MXN));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_gd, ger_gd, rate.getRate(bra_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_gd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_gd, jap_gd, rate.getRate(bra_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_gd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_gd, nig_gd, rate.getRate(bra_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_gd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_gd, pol_gd, rate.getRate(bra_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_gd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_gd, eng_gd, rate.getRate(bra_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_gd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_gd, kor_gd, rate.getRate(bra_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_gd, KRW));
                            }
                        }

                        // GERMANY AS THE HOME COUNTRY
                        if (home.equals("Germany")) {
                            mHc_salary.setText(avg_salary.averageSalary(ger_gd, EUR));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_gd, usa_gd, rate.getRate(ger_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_gd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_gd, can_gd, rate.getRate(ger_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_gd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_gd, mex_gd, rate.getRate(ger_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_gd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_gd, bra_gd, rate.getRate(ger_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_gd, BRL));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_gd, jap_gd, rate.getRate(ger_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_gd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_gd, nig_gd, rate.getRate(ger_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_gd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_gd, pol_gd, rate.getRate(ger_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_gd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(ger_gd, eng_gd, rate.getRate(ger_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_gd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_gd, kor_gd, rate.getRate(ger_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_gd, KRW));
                            }
                        }

                        // JAPAN AS THE HOME COUNTRY
                        if (home.equals("Japan")) {
                            mHc_salary.setText(avg_salary.averageSalary(jap_gd, JPY));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_gd, usa_gd, rate.getRate(jap_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_gd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_gd, can_gd, rate.getRate(jap_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_gd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_gd, mex_gd, rate.getRate(jap_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_gd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_gd, bra_gd, rate.getRate(jap_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_gd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_gd, ger_gd, rate.getRate(jap_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_gd, EUR));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_gd, nig_gd, rate.getRate(jap_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_gd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_gd, pol_gd, rate.getRate(jap_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_gd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_gd, eng_gd, rate.getRate(jap_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_gd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_gd, kor_gd, rate.getRate(jap_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_gd, KRW));
                            }
                        }

                        // NIGERIA AS THE HOME COUNTRY
                        if (home.equals("Nigeria")) {
                            mHc_salary.setText(avg_salary.averageSalary(nig_gd, NGN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_gd, usa_gd, rate.getRate(nig_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_gd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_gd, can_gd, rate.getRate(nig_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_gd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_gd, mex_gd, rate.getRate(nig_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_gd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_gd, bra_gd, rate.getRate(nig_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_gd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_gd, ger_gd, rate.getRate(nig_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_gd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_gd, jap_gd, rate.getRate(nig_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_gd, JPY));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_gd, pol_gd, rate.getRate(nig_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_gd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_gd, eng_gd, rate.getRate(nig_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_gd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(nig_gd, kor_gd, rate.getRate(nig_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_gd, KRW));
                            }
                        }

                        // POLAND AS THE HOME COUNTRY
                        if (home.equals("Poland")) {
                            mHc_salary.setText(avg_salary.averageSalary(pol_gd, PLN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_gd, usa_gd, rate.getRate(pol_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_gd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_gd, can_gd, rate.getRate(pol_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_gd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_gd, mex_gd, rate.getRate(pol_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_gd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_gd, bra_gd, rate.getRate(pol_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_gd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_gd, ger_gd, rate.getRate(pol_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_gd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_gd, jap_gd, rate.getRate(pol_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_gd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_gd, nig_gd, rate.getRate(pol_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_gd, NGN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_gd, eng_gd, rate.getRate(pol_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_gd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_gd, kor_gd, rate.getRate(pol_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_gd, KRW));
                            }
                        }

                        // ENGLAND AS THE HOME COUNTRY
                        if (home.equals("England")) {
                            mHc_salary.setText(avg_salary.averageSalary(eng_gd, GBP));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_gd, usa_gd, rate.getRate(eng_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_gd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_gd, can_gd, rate.getRate(eng_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_gd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_gd, mex_gd, rate.getRate(eng_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_gd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_gd, bra_gd, rate.getRate(eng_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_gd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_gd, ger_gd, rate.getRate(eng_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_gd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_gd, jap_gd, rate.getRate(eng_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_gd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_gd, nig_gd, rate.getRate(eng_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_gd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_gd, pol_gd, rate.getRate(eng_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_gd, PLN));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_gd, kor_gd, rate.getRate(eng_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_gd, KRW));
                            }
                        }

                        // SOUTH KOREA AS THE HOME COUNTRY
                        if (home.equals("South Korea")) {
                            mHc_salary.setText(avg_salary.averageSalary(kor_gd, KRW));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_gd, usa_gd, rate.getRate(kor_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_gd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_gd, can_gd, rate.getRate(kor_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_gd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_gd, mex_gd, rate.getRate(kor_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_gd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_gd, bra_gd, rate.getRate(kor_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_gd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_gd, ger_gd, rate.getRate(kor_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_gd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_gd, jap_gd, rate.getRate(kor_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_gd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_gd, nig_gd, rate.getRate(kor_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_gd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_gd, pol_gd, rate.getRate(kor_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_gd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_gd, eng_gd, rate.getRate(kor_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_gd, GBP));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }


//======================================================================================================================


            // UX DESIGNER
            if (job.equals("UX designer")) {
                mRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double can_UXd = dataSnapshot.child("Canada").child("UX designer").getValue(Double.class);
                        double usa_UXd = dataSnapshot.child("United States").child("UX designer").getValue(Double.class);
                        double mex_UXd = dataSnapshot.child("Mexico").child("UX designer").getValue(Double.class);
                        double bra_UXd = dataSnapshot.child("Brazil").child("UX designer").getValue(Double.class);
                        double ger_UXd = dataSnapshot.child("Germany").child("UX designer").getValue(Double.class);
                        double jap_UXd = dataSnapshot.child("Japan").child("UX designer").getValue(Double.class);
                        double nig_UXd = dataSnapshot.child("Nigeria").child("UX designer").getValue(Double.class);
                        double pol_UXd = dataSnapshot.child("Poland").child("UX designer").getValue(Double.class);
                        double eng_UXd = dataSnapshot.child("England").child("UX designer").getValue(Double.class);
                        double kor_UXd = dataSnapshot.child("South Korea").child("UX designer").getValue(Double.class);
                        // CANADA AS THE HOME COUNTRY
                        if (home.equals("Canada")) {
                            mHc_salary.setText(avg_salary.averageSalary(can_UXd, CAD));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_UXd, usa_UXd, rate.getRate(can_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UXd, USD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UXd, mex_UXd, rate.getRate(can_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UXd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UXd, bra_UXd, rate.getRate(can_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UXd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_UXd, ger_UXd, rate.getRate(can_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UXd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UXd, jap_UXd, rate.getRate(can_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UXd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UXd, nig_UXd, rate.getRate(can_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UXd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UXd, pol_UXd, rate.getRate(can_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UXd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_UXd, eng_UXd, rate.getRate(can_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UXd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_UXd, kor_UXd, rate.getRate(can_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UXd, KRW));
                            }
                        }

                        // UNITED STATES AS THE HOME COUNTRY
                        if (home.equals("United States")) {
                            mHc_salary.setText(avg_salary.averageSalary(usa_UXd, USD));
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UXd, can_UXd, rate.getRate(usa_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UXd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UXd, mex_UXd, rate.getRate(usa_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UXd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UXd, bra_UXd, rate.getRate(usa_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UXd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_UXd, ger_UXd, rate.getRate(usa_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UXd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UXd, jap_UXd, rate.getRate(usa_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UXd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UXd, nig_UXd, rate.getRate(usa_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UXd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UXd, pol_UXd, rate.getRate(usa_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UXd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_UXd, eng_UXd, rate.getRate(usa_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UXd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_UXd, kor_UXd, rate.getRate(usa_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UXd, KRW));
                            }
                        }

                        // MEXICO AS THE HOME COUNTRY
                        if (home.equals("Mexico")) {
                            mHc_salary.setText(avg_salary.averageSalary(mex_UXd, MXN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UXd, usa_UXd, rate.getRate(mex_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UXd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UXd, can_UXd, rate.getRate(mex_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UXd, CAD));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UXd, bra_UXd, rate.getRate(mex_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UXd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UXd, ger_UXd, rate.getRate(mex_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UXd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_UXd, jap_UXd, rate.getRate(mex_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UXd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_UXd, nig_UXd, rate.getRate(mex_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UXd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UXd, pol_UXd, rate.getRate(mex_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UXd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_UXd, eng_UXd, rate.getRate(mex_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UXd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_UXd, kor_UXd, rate.getRate(mex_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UXd, KRW));
                            }
                        }

                        // BRAZIL AS THE HOME COUNTRY
                        if (home.equals("Brazil")) {
                            mHc_salary.setText(avg_salary.averageSalary(bra_UXd, BRL));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_UXd, usa_UXd, rate.getRate(bra_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UXd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_UXd, can_UXd, rate.getRate(bra_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UXd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_UXd, mex_UXd, rate.getRate(bra_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UXd, MXN));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_UXd, ger_UXd, rate.getRate(bra_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UXd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_UXd, jap_UXd, rate.getRate(bra_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UXd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_UXd, nig_UXd, rate.getRate(bra_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UXd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_UXd, pol_UXd, rate.getRate(bra_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UXd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_UXd, eng_UXd, rate.getRate(bra_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UXd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_UXd, kor_UXd, rate.getRate(bra_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UXd, KRW));
                            }
                        }

                        // GERMANY AS THE HOME COUNTRY
                        if (home.equals("Germany")) {
                            mHc_salary.setText(avg_salary.averageSalary(ger_UXd, EUR));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UXd, usa_UXd, rate.getRate(ger_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UXd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UXd, can_UXd, rate.getRate(ger_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UXd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UXd, mex_UXd, rate.getRate(ger_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UXd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UXd, bra_UXd, rate.getRate(ger_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UXd, BRL));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UXd, jap_UXd, rate.getRate(ger_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UXd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UXd, nig_UXd, rate.getRate(ger_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UXd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UXd, pol_UXd, rate.getRate(ger_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UXd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(ger_UXd, eng_UXd, rate.getRate(ger_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UXd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_UXd, kor_UXd, rate.getRate(ger_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UXd, KRW));
                            }
                        }

                        // JAPAN AS THE HOME COUNTRY
                        if (home.equals("Japan")) {
                            mHc_salary.setText(avg_salary.averageSalary(jap_UXd, JPY));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UXd, usa_UXd, rate.getRate(jap_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UXd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UXd, can_UXd, rate.getRate(jap_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UXd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UXd, mex_UXd, rate.getRate(jap_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UXd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UXd, bra_UXd, rate.getRate(jap_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UXd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UXd, ger_UXd, rate.getRate(jap_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UXd, EUR));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_UXd, nig_UXd, rate.getRate(jap_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UXd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UXd, pol_UXd, rate.getRate(jap_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UXd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_UXd, eng_UXd, rate.getRate(jap_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UXd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_UXd, kor_UXd, rate.getRate(jap_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UXd, KRW));
                            }
                        }

                        // NIGERIA AS THE HOME COUNTRY
                        if (home.equals("Nigeria")) {
                            mHc_salary.setText(avg_salary.averageSalary(nig_UXd, NGN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UXd, usa_UXd, rate.getRate(nig_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UXd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UXd, can_UXd, rate.getRate(nig_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UXd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UXd, mex_UXd, rate.getRate(nig_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UXd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UXd, bra_UXd, rate.getRate(nig_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UXd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UXd, ger_UXd, rate.getRate(nig_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UXd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UXd, jap_UXd, rate.getRate(nig_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UXd, JPY));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UXd, pol_UXd, rate.getRate(nig_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UXd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_UXd, eng_UXd, rate.getRate(nig_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UXd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(nig_UXd, kor_UXd, rate.getRate(nig_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UXd, KRW));
                            }
                        }

                        // POLAND AS THE HOME COUNTRY
                        if (home.equals("Poland")) {
                            mHc_salary.setText(avg_salary.averageSalary(pol_UXd, PLN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_UXd, usa_UXd, rate.getRate(pol_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UXd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_UXd, can_UXd, rate.getRate(pol_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UXd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_UXd, mex_UXd, rate.getRate(pol_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UXd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_UXd, bra_UXd, rate.getRate(pol_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UXd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_UXd, ger_UXd, rate.getRate(pol_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UXd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_UXd, jap_UXd, rate.getRate(pol_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UXd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_UXd, nig_UXd, rate.getRate(pol_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UXd, NGN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_UXd, eng_UXd, rate.getRate(pol_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UXd, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_UXd, kor_UXd, rate.getRate(pol_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UXd, KRW));
                            }
                        }

                        // ENGLAND AS THE HOME COUNTRY
                        if (home.equals("England")) {
                            mHc_salary.setText(avg_salary.averageSalary(eng_UXd, GBP));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UXd, usa_UXd, rate.getRate(eng_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UXd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UXd, can_UXd, rate.getRate(eng_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UXd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UXd, mex_UXd, rate.getRate(eng_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UXd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UXd, bra_UXd, rate.getRate(eng_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UXd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UXd, ger_UXd, rate.getRate(eng_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UXd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UXd, jap_UXd, rate.getRate(eng_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UXd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UXd, nig_UXd, rate.getRate(eng_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UXd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UXd, pol_UXd, rate.getRate(eng_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UXd, PLN));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_UXd, kor_UXd, rate.getRate(eng_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_UXd, KRW));
                            }
                        }

                        // SOUTH KOREA AS THE HOME COUNTRY
                        if (home.equals("South Korea")) {
                            mHc_salary.setText(avg_salary.averageSalary(kor_UXd, KRW));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UXd, usa_UXd, rate.getRate(kor_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_UXd, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UXd, can_UXd, rate.getRate(kor_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_UXd, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UXd, mex_UXd, rate.getRate(kor_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_UXd, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UXd, bra_UXd, rate.getRate(kor_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_UXd, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UXd, ger_UXd, rate.getRate(kor_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_UXd, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UXd, jap_UXd, rate.getRate(kor_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_UXd, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UXd, nig_UXd, rate.getRate(kor_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_UXd, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UXd, pol_UXd, rate.getRate(kor_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_UXd, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_UXd, eng_UXd, rate.getRate(kor_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_UXd, GBP));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }


//======================================================================================================================


            // NETWORK ADMINISTRATOR
            if (job.equals("Network administrator")) {
                mRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double can_na = dataSnapshot.child("Canada").child("Network administrator").getValue(Double.class);
                        double usa_na = dataSnapshot.child("United States").child("Network administrator").getValue(Double.class);
                        double mex_na = dataSnapshot.child("Mexico").child("Network administrator").getValue(Double.class);
                        double bra_na = dataSnapshot.child("Brazil").child("Network administrator").getValue(Double.class);
                        double ger_na = dataSnapshot.child("Germany").child("Network administrator").getValue(Double.class);
                        double jap_na = dataSnapshot.child("Japan").child("Network administrator").getValue(Double.class);
                        double nig_na = dataSnapshot.child("Nigeria").child("Network administrator").getValue(Double.class);
                        double pol_na = dataSnapshot.child("Poland").child("Network administrator").getValue(Double.class);
                        double eng_na = dataSnapshot.child("England").child("Network administrator").getValue(Double.class);
                        double kor_na = dataSnapshot.child("South Korea").child("Network administrator").getValue(Double.class);
                        // CANADA AS THE HOME COUNTRY
                        if (home.equals("Canada")) {
                            mHc_salary.setText(avg_salary.averageSalary(can_na, CAD));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_na, usa_na, rate.getRate(can_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_na, USD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_na, mex_na, rate.getRate(can_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_na, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_na, bra_na, rate.getRate(can_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_na, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_na, ger_na, rate.getRate(can_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_na, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_na, jap_na, rate.getRate(can_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_na, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_na, nig_na, rate.getRate(can_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_na, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_na, pol_na, rate.getRate(can_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_na, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(can_na, eng_na, rate.getRate(can_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_na, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(can_na, kor_na, rate.getRate(can_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_na, KRW));
                            }
                        }

                        // UNITED STATES AS THE HOME COUNTRY
                        if (home.equals("United States")) {
                            mHc_salary.setText(avg_salary.averageSalary(usa_na, USD));
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_na, can_na, rate.getRate(usa_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_na, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_na, mex_na, rate.getRate(usa_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_na, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_na, bra_na, rate.getRate(usa_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_na, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_na, ger_na, rate.getRate(usa_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_na, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_na, jap_na, rate.getRate(usa_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_na, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_na, nig_na, rate.getRate(usa_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_na, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_na, pol_na, rate.getRate(usa_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_na, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(usa_na, eng_na, rate.getRate(usa_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_na, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(usa_na, kor_na, rate.getRate(usa_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_na, KRW));
                            }
                        }

                        // MEXICO AS THE HOME COUNTRY
                        if (home.equals("Mexico")) {
                            mHc_salary.setText(avg_salary.averageSalary(mex_na, MXN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_na, usa_na, rate.getRate(mex_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_na, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_na, can_na, rate.getRate(mex_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_na, CAD));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_na, bra_na, rate.getRate(mex_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_na, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_na, ger_na, rate.getRate(mex_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_na, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_na, jap_na, rate.getRate(mex_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_na, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_na, nig_na, rate.getRate(mex_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_na, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_na, pol_na, rate.getRate(mex_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_na, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(mex_na, eng_na, rate.getRate(mex_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_na, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(mex_na, kor_na, rate.getRate(mex_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_na, KRW));
                            }
                        }

                        // BRAZIL AS THE HOME COUNTRY
                        if (home.equals("Brazil")) {
                            mHc_salary.setText(avg_salary.averageSalary(bra_na, BRL));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_na, usa_na, rate.getRate(bra_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_na, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_na, can_na, rate.getRate(bra_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_na, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_na, mex_na, rate.getRate(bra_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_na, MXN));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_na, ger_na, rate.getRate(bra_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_na, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_na, jap_na, rate.getRate(bra_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_na, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_na, nig_na, rate.getRate(bra_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_na, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_na, pol_na, rate.getRate(bra_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_na, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(bra_na, eng_na, rate.getRate(bra_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_na, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(bra_na, kor_na, rate.getRate(bra_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_na, KRW));
                            }
                        }

                        // GERMANY AS THE HOME COUNTRY
                        if (home.equals("Germany")) {
                            mHc_salary.setText(avg_salary.averageSalary(ger_na, EUR));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_na, usa_na, rate.getRate(ger_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_na, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_na, can_na, rate.getRate(ger_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_na, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_na, mex_na, rate.getRate(ger_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_na, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_na, bra_na, rate.getRate(ger_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_na, BRL));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_na, jap_na, rate.getRate(ger_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_na, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_na, nig_na, rate.getRate(ger_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_na, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_na, pol_na, rate.getRate(ger_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_na, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(ger_na, eng_na, rate.getRate(ger_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_na, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(ger_na, kor_na, rate.getRate(ger_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_na, KRW));
                            }
                        }

                        // JAPAN AS THE HOME COUNTRY
                        if (home.equals("Japan")) {
                            mHc_salary.setText(avg_salary.averageSalary(jap_na, JPY));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_na, usa_na, rate.getRate(jap_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_na, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_na, can_na, rate.getRate(jap_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_na, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_na, mex_na, rate.getRate(jap_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_na, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_na, bra_na, rate.getRate(jap_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_na, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_na, ger_na, rate.getRate(jap_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_na, EUR));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_na, nig_na, rate.getRate(jap_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_na, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_na, pol_na, rate.getRate(jap_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_na, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(jap_na, eng_na, rate.getRate(jap_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_na, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(jap_na, kor_na, rate.getRate(jap_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_na, KRW));
                            }
                        }

                        // NIGERIA AS THE HOME COUNTRY
                        if (home.equals("Nigeria")) {
                            mHc_salary.setText(avg_salary.averageSalary(nig_na, NGN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_na, usa_na, rate.getRate(nig_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_na, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_na, can_na, rate.getRate(nig_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_na, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_na, mex_na, rate.getRate(nig_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_na, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_na, bra_na, rate.getRate(nig_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_na, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_na, ger_na, rate.getRate(nig_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_na, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_na, jap_na, rate.getRate(nig_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_na, JPY));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_na, pol_na, rate.getRate(nig_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_na, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(nig_na, eng_na, rate.getRate(nig_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_na, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(nig_na, kor_na, rate.getRate(nig_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_na, KRW));
                            }
                        }

                        // POLAND AS THE HOME COUNTRY
                        if (home.equals("Poland")) {
                            mHc_salary.setText(avg_salary.averageSalary(pol_na, PLN));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_na, usa_na, rate.getRate(pol_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_na, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_na, can_na, rate.getRate(pol_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_na, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_na, mex_na, rate.getRate(pol_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_na, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_na, bra_na, rate.getRate(pol_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_na, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_na, ger_na, rate.getRate(pol_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_na, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_na, jap_na, rate.getRate(pol_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_na, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_na, nig_na, rate.getRate(pol_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_na, NGN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(pol_na, eng_na, rate.getRate(pol_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_na, GBP));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(pol_na, kor_na, rate.getRate(pol_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_na, KRW));
                            }
                        }

                        // ENGLAND AS THE HOME COUNTRY
                        if (home.equals("England")) {
                            mHc_salary.setText(avg_salary.averageSalary(eng_na, GBP));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_na, usa_na, rate.getRate(eng_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_na, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_na, can_na, rate.getRate(eng_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_na, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_na, mex_na, rate.getRate(eng_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_na, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_na, bra_na, rate.getRate(eng_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_na, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_na, ger_na, rate.getRate(eng_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_na, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_na, jap_na, rate.getRate(eng_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_na, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_na, nig_na, rate.getRate(eng_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_na, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_na, pol_na, rate.getRate(eng_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_na, PLN));
                            }
                            if (remote.equals("South Korea")) {
                                mPercent.setText(percent.difference_home_more_valuable(eng_na, kor_na, rate.getRate(eng_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(kor_na, KRW));
                            }
                        }

                        // SOUTH KOREA AS THE HOME COUNTRY
                        if (home.equals("South Korea")) {
                            mHc_salary.setText(avg_salary.averageSalary(kor_na, KRW));
                            if (remote.equals("United States")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_na, usa_na, rate.getRate(kor_rates[0]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(usa_na, USD));
                            }
                            if (remote.equals("Canada")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_na, can_na, rate.getRate(kor_rates[1]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(can_na, CAD));
                            }
                            if (remote.equals("Mexico")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_na, mex_na, rate.getRate(kor_rates[2]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(mex_na, MXN));
                            }
                            if (remote.equals("Brazil")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_na, bra_na, rate.getRate(kor_rates[3]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(bra_na, BRL));
                            }
                            if (remote.equals("Germany")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_na, ger_na, rate.getRate(kor_rates[4]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(ger_na, EUR));
                            }
                            if (remote.equals("Japan")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_na, jap_na, rate.getRate(kor_rates[5]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(jap_na, JPY));
                            }
                            if (remote.equals("Nigeria")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_na, nig_na, rate.getRate(kor_rates[6]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(nig_na, NGN));
                            }
                            if (remote.equals("Poland")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_na, pol_na, rate.getRate(kor_rates[7]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(pol_na, PLN));
                            }
                            if (remote.equals("England")) {
                                mPercent.setText(percent.difference_remote_more_valuable(kor_na, eng_na, rate.getRate(kor_rates[8]), home, remote));
                                mRc_salary.setText(avg_salary.averageSalary(eng_na, GBP));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        }
}

