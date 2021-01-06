package com.sp.base.util;

import android.content.Context;
import android.location.Location;

import java.text.DecimalFormat;

public class LocationUtil {
    // For getting distance between two locations
    public static String getDistance(Context context, String sLatitude, String sLongitude, String dLatitude, String dLongitude) {

        if (!dLatitude.equals("") && !dLongitude.equals("")) {
            double mLatitude = 0;
            double mLongitude = 0;
            try {
                mLatitude = Double.parseDouble(dLatitude);
                mLongitude = Double.parseDouble(dLongitude);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            Location locationA = new Location("point A");
            if (!sLongitude.equals("")) {
                try {
                    locationA.setLatitude(Double.parseDouble(sLatitude));
                    locationA.setLongitude(Double.parseDouble(sLongitude));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            Location locationB = new Location("point B");
            locationB.setLatitude(mLatitude);
            locationB.setLongitude(mLongitude);

            double distance = locationA.distanceTo(locationB) / 1609;

            String mDistanceStr = new DecimalFormat("###.##").format(distance) + " mi";
            return mDistanceStr.replace(",", ".");
        }
        return "";
    }

}
