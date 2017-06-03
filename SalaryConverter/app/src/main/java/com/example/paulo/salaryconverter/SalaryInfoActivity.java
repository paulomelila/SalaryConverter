package com.example.paulo.salaryconverter;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SalaryInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_info);

        Typeface roboto_regular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        Typeface roboto_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Italic.ttf");
        Typeface roboto_bold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");

        TextView mJob = (TextView) findViewById(R.id.job);
        mJob.setTypeface(roboto_regular);
        TextView mPercent = (TextView) findViewById(R.id.percentDifferenceTex);
        mPercent.setTypeface(roboto_italic);

        TextView mRateLabel = (TextView) findViewById(R.id.rate_label);
        mRateLabel.setTypeface(roboto_bold);
        TextView mRateText = (TextView) findViewById(R.id.rate);
        mRateText.setTypeface(roboto_bold);

        TextView mAvg_salary = (TextView) findViewById(R.id.avg_salary);
        mAvg_salary.setTypeface(roboto_regular);
        TextView mHc_salary = (TextView) findViewById(R.id.homeCountryAverageSalary);
        mHc_salary.setTypeface(roboto_italic);
        TextView mRc_salary = (TextView) findViewById(R.id.emotCountryAverageSalary);
        mRc_salary.setTypeface(roboto_italic);
    }
}
