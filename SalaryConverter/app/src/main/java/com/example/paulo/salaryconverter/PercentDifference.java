package com.example.paulo.salaryconverter;

import java.text.NumberFormat;

/**
 * Created by Paulo on 07/06/2017.
 */

public class PercentDifference {

//      methods to convert currencies and get percentage difference


    // IF HOME COUNTRY IS MORE VALUABLE THAN REMOTE COUNTRY
    public String  difference_home_more_valuable (double home, double remote, double rate, String hc, String rc) {
      NumberFormat fmt = NumberFormat.getPercentInstance();
      double remote_converted = remote / rate; // convert the remote country to the home country currency
      if (remote_converted > home) {
         String result = fmt.format(1 - (home/remote_converted));
         return result + " higher in " + rc;
       }
       else {
          String result = fmt.format(1 - (remote_converted / home));
          return result + " higher in " + hc;
      }
    }

    // IF REMOTE COUNTRY IS MORE VALUABLE THAN HOME COUNTRY
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
}
