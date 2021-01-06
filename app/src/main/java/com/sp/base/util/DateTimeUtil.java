package com.sp.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {
    static String TAG = DateTimeUtil.class.getSimpleName();

    public static String convertDateFormate(String inputformate, String outputFormate, String inputValue) {
        SimpleDateFormat inFormat = new SimpleDateFormat(inputformate);
        Date date = null;
        try {
            date = inFormat.parse(inputValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = "";
        SimpleDateFormat outFormat = new SimpleDateFormat(outputFormate);
        try {
            result = outFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            result = inputValue;
        }
        return result;
    }

    public static String getCurrentDate(String formate) {
        Calendar calander = Calendar.getInstance();
        String date = null;
        SimpleDateFormat simpledateformat = new SimpleDateFormat(formate);
        try {
            date = simpledateformat.format(calander.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurrentTime() {
        Calendar calander = Calendar.getInstance();
        String date = null;
        SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm");
        try {
            date = simpledateformat.format(calander.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    //change date to milliseconds
    public long dateToMilliSeconds(String mDateFormat, String mDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(mDateFormat);
        formatter.setLenient(false);
        Date mSelectedDate;
        try {
            mSelectedDate = formatter.parse(mDate);
            if (mSelectedDate != null) {
                return mSelectedDate.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isValidDate(String pDateString, String formate) throws ParseException {
        Date date = null;
        try {
            if (pDateString.contains("/")) {
                String[] spilt = pDateString.split("/");
                if (spilt.length > 1 && Integer.parseInt(spilt[0]) < 13) {
                    String lastDayOfMonth = getDate(spilt[0], spilt[1]);
                    pDateString = lastDayOfMonth + "/" + spilt[0] + "/20" + spilt[1];
                    date = new SimpleDateFormat(formate).parse(pDateString);
                    return new Date().before(date);
                }
            }
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getDate(String sMonth, String sYear) {
        Date date = null;
        DateFormat DATE_FORMAT = null;
        try {
            int month = Integer.parseInt(sMonth);
            int year = Integer.parseInt("20" + sYear);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.YEAR, year);
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);

            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
            date = calendar.getTime();
            DATE_FORMAT = new SimpleDateFormat("dd");
            LogUtil.printLog("last day of month ", DATE_FORMAT.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return DATE_FORMAT.format(date);
    }

    public static String getTimeZone() {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        LogUtil.printLog(TAG, "time zone  : " + tz.getDisplayName());
        LogUtil.printLog(TAG, "time zone  : " + tz.getID());
        return tz.getID();
    }

    public static String convertTime24To12(Integer time) {
        String result = "";
        try {
            final SimpleDateFormat sdf;
            if (time.toString().length() == 3) {
                sdf = new SimpleDateFormat("Hmm");
            } else {
                sdf = new SimpleDateFormat("HHmm");
            }
            final Date dateObj = sdf.parse("" + time);
            result = new SimpleDateFormat("hh:mm aa").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
            result = "" + time;
        }
        if (result.equalsIgnoreCase("0:00 PM")) {
            result = "12:00 PM";
        }

        LogUtil.printLog(TAG, time + " convertTime24To12 : " + result);
        return result;
    }

}
