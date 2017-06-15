package com.example.paulo.salaryconverter;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Paulo on 07/06/2017.
 */

public class Salary {

//      Methods to convert currencies and get percentage difference

    /**     HOME COUNTRY AS THE MORE VALUABLE CURRENCY
     *  This method is called when the home country has a stronger
     *  currency than the remote country. In this case, the remote country
     *  will be divided by the rate, to be converted to the home country,
     *  this way it can be compared to the salary in the home country.
     *
     *  After this if the remote country salary is higher than the home country,
     *  the home country will be divided by the remote country to find the percentage.
     *  If the home country is higher, we do the opposite.
     *  Then, we do the subtraction (100% - percentage) to find the percent difference.
     *
     *  The result is formatted to the percentInstance to be displayed on the app.
     *  Finally, we return the result, specifying which country has the higher salary.
     *
     * @param  home the home country salary in the correspondent currency
     * @param  remote the remote country salary in the correspondent country's currency
*      @param  rate the conversion rate between the countries
     * @param  hc the home country name to be displayed if it's higher
     * @param  rc the remote country name to be displayed if it's higher
     * @return result / the result of the calculation ater it's formatted to percentInstance
     */
    public String  difference_home_more_valuable (double home, double remote, double rate, String hc, String rc) {
      NumberFormat fmt = NumberFormat.getPercentInstance();
      double remote_converted = remote / rate;
      if (remote_converted > home) {
         String result = fmt.format(1 - (home/remote_converted));
         return result + " higher in " + rc;
       }
       else {
          String result = fmt.format(1 - (remote_converted / home));
          return result + " higher in " + hc;
      }
    }

    /**     REMOTE COUNTRY AS THE MORE VALUABLE CURRENCY
     *  This method is called when the remote country has a stronger
     *  currency than the home country. In this case, the home country
     *  will be divided by the rate, to be converted to the remote country,
     *  this way it can be compared to the salary in the remote country.
     *
     *  After this if the home country salary is higher than the remote country,
     *  the remote country will be divided by the home country to find the percentage.
     *  If the remote country is higher, we do the opposite.
     *  Then, we do the subtraction (100% - percentage) to find the percent difference.
     *
     *  The result is formatted to the percentInstance to be displayed on the app.
     *  Finally, we return the result, specifying which country has the higher salary.
     *
     * @param  home the home country salary in the correspondent currency
     * @param  remote the remote country salary in the correspondent country's currency
     * @param  rate the conversion rate between the countries
     * @param  hc the home country name to be displayed if it's higher
     * @param  rc the remote country name to be displayed if it's higher
     * @return result / the result of the calculation after it's formatted to percentInstance
     */
    public String difference_remote_more_valuable (double home, double remote, double rate, String hc, String rc) {
        NumberFormat fmt = NumberFormat.getPercentInstance();
        double home_converted = home / rate; // convert the home country to the remote country currency
        if (home_converted > remote) {
            String result = fmt.format(1 - (remote/home_converted) );
            return result + " higher in " + hc;
        }
        else {
            String result = fmt.format(1 - (home_converted/remote));
            return result + " higher in " + rc;
        }
    }

    /** Method to format the salary with grouped thousands using commas
     *  This method is called to get the average salary from the database
     *  and apply a decimal format using commas to group thousands.
     *  Then we return the salary with this format + the currency of the
     *  specific country.
     *
     * @param  salary the salary that is stored in the database
     * @param  currency the currency of the designated country
     * @return salary / the salary after it's formatted
     */
    public String averageSalary(double salary, String currency) {
        DecimalFormat fmt = new DecimalFormat("###,###");
        return fmt.format(salary) + " " + currency;
    }

    /** Method to get the rates from the rates.xml and parse it to double,
     *  so it can be used in the calculations of difference_home_more_valuable
     *  and difference_remote_more_valuable methods.
     *
     * @param  rate the conversion rate between two countries
     * @return mRate / the rate after it's parsed to double
     */
    public double getRate (String rate) {
        double mRate = Double.parseDouble(rate);
        return mRate;
    }
}
