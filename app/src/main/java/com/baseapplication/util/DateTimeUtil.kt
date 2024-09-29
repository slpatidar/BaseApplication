package com.baseapplication.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class DateTimeUtil() {
    //change date to milliseconds
    fun dateToMilliSeconds(mDateFormat: String?, mDate: String?): Long {
        val formatter: SimpleDateFormat = SimpleDateFormat(mDateFormat)
        formatter.setLenient(false)
        val mSelectedDate: Date?
        try {
            mSelectedDate = formatter.parse(mDate)
            if (mSelectedDate != null) {
                return mSelectedDate.getTime()
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

    companion object {
        var TAG: String = DateTimeUtil::class.java.getSimpleName()
        fun convertDateFormate(
            inputformate: String?,
            outputFormate: String?,
            inputValue: String?
        ): String? {
            val inFormat: SimpleDateFormat = SimpleDateFormat(inputformate)
            var date: Date? = null
            try {
                date = inFormat.parse(inputValue)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            var result: String? = ""
            val outFormat: SimpleDateFormat = SimpleDateFormat(outputFormate)
            try {
                result = outFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                result = inputValue
            }
            return result
        }

        fun getCurrentDate(formate: String?): String? {
            val calander: Calendar = Calendar.getInstance()
            var date: String? = null
            val simpledateformat: SimpleDateFormat = SimpleDateFormat(formate)
            try {
                date = simpledateformat.format(calander.getTime())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return date
        }

        val currentTime: String?
            get() {
                val calander: Calendar = Calendar.getInstance()
                var date: String? = null
                val simpledateformat: SimpleDateFormat = SimpleDateFormat("HH:mm")
                try {
                    date = simpledateformat.format(calander.getTime())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return date
            }

        @Throws(ParseException::class)
        fun isValidDate(pDateString: String, formate: String?): Boolean {
            var pDateString: String = pDateString
            var date: Date? = null
            try {
                if (pDateString.contains("/")) {
                    val spilt: Array<String> =
                        pDateString.split("/".toRegex()).dropLastWhile({ it.isEmpty() })
                            .toTypedArray()
                    if (spilt.size > 1 && spilt.get(0).toInt() < 13) {
                        val lastDayOfMonth: String = getDate(spilt.get(0), spilt.get(1))
                        pDateString = lastDayOfMonth + "/" + spilt.get(0) + "/20" + spilt.get(1)
                        date = SimpleDateFormat(formate).parse(pDateString)
                        return Date().before(date)
                    }
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return false
        }

        private fun getDate(sMonth: String, sYear: String): String {
            var date: Date? = null
            var DATE_FORMAT: DateFormat? = null
            try {
                val month: Int = sMonth.toInt()
                val year: Int = ("20" + sYear).toInt()
                val calendar: Calendar = Calendar.getInstance()
                calendar.set(Calendar.MONTH, month - 1)
                calendar.set(Calendar.YEAR, year)
                calendar.add(Calendar.MONTH, 1)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.add(Calendar.DATE, -1)
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE))
                date = calendar.getTime()
                DATE_FORMAT = SimpleDateFormat("dd")
                LogUtil.printLog("last day of month ", DATE_FORMAT.format(date))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return DATE_FORMAT!!.format(date)
        }

        val timeZone: String
            get() {
                val cal: Calendar = Calendar.getInstance()
                val tz: TimeZone = cal.getTimeZone()
                LogUtil.printLog(TAG, "time zone  : " + tz.getDisplayName())
                LogUtil.printLog(TAG, "time zone  : " + tz.getID())
                return tz.getID()
            }

        fun convertTime24To12(time: Int): String {
            var result: String = ""
            try {
                val sdf: SimpleDateFormat
                if (time.toString().length == 3) {
                    sdf = SimpleDateFormat("Hmm")
                } else {
                    sdf = SimpleDateFormat("HHmm")
                }
                val dateObj: Date = sdf.parse("" + time)
                result = SimpleDateFormat("hh:mm aa").format(dateObj)
            } catch (e: ParseException) {
                e.printStackTrace()
                result = "" + time
            }
            if (result.equals("0:00 PM", ignoreCase = true)) {
                result = "12:00 PM"
            }
            LogUtil.printLog(TAG, time.toString() + " convertTime24To12 : " + result)
            return result
        }
    }
}
