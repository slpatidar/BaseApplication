package com.sp.base.util;

public class SerializedNameKeys {
    public static final String key = "key";
    public static final String statusCode = "code";
    public static final String statusMessage = "message";
    public static final String data = "data";
    public static final String id = "id";
    public static final String riderId = "rider_id";
    public static final String latitude = "latitude";
    public static final String longitude = "longitude";
    public static final String address = "address";
    public static final String created = "created_at";
    public static final String updated = "updated_at";
    public static final String locations = "locations";
    public static final String relative_url = "relative_url";
    public static final String icon = "icon";
    public static final String promo_amount = "promo_amount";
    public static final String promo_code = "promo_code";

    /*NumberVerifyRequest*/
    public static final String mobile = "mobile";
    public static final String booking_amount = "booking_amount";
    public static final String country_code = "country_code";

    /*NumberVerifyResponse*/
    public static final String otp = "otp";
    public static final String expire_in = "expire_in";

    /*Social login Request*/
    public static final String type = "type";
    public static final String token = "token";
    public static final String device_type = "device_type";
    public static final String device_token = "device_token";
    public static final String referral_code = "referral_code";

    /*Social login Response*/
    public static final String first_name = "first_name";
    public static final String middle_name = "middle_name";
    public static final String last_name = "last_name";
    public static final String profile_url = "profile_url";
    public static final String referral_parameter = "referal_parameter";
    public static final String teke_teke_balance = "teke_teke_balance";

    /*Add Location Request */
    public static final String bookmark_type = "bookmark_type";
    public static final String location_name = "location_name";

    /* Add Location Response*/
    public static final String added_id = "added_id";
    public static final String callID = "calId";

    /* Get Route list Request */
    public static final String location = "location";
    public static final String lat = "lat";
    public static final String from = "from";
    public static final String to = "to";

    /* Get Route list Response */
    public static final String route_list = "routelist";
    public static final String route_name = "route_name";
    public static final String distance = "distance";
    public static final String stations = "stations";

    /* Station Details */
    public static final String station_id = "station_id";
    public static final String name = "name";
    public static final String seq_num = "seq_num";
    public static final String google_predicted_time = "google_predicted_time";
    public static final String estimate_time = "estimate_time";

    /* Ride Timing Request */
    public static final String route_id = "route_id";
    public static final String source_st_id = "source_station_id";
    public static final String destination_st_id = "destination_station_id";
    public static final String day_index = "day_index";
    public static final String current_date = "current_date";
    public static final String current_time = "current_time";

    /* Ride Timing Response */
    public static final String vehicle_list = "rides";

    /* Vehicle Details Model */
    public static final String status = "status";
    public static final String ride_price = "ride_price";
    public static final String amount_to_be_paid = "amount_to_be_paid";
    public static final String ride_id = "ride_id";
    public static final String remaining_amount = "remaining_amount";
    public static final String booking_fee = "booking_fee";
    public static final String full_amount = "full_amount";
    public static final String transaction_amount = "transaction_amount";

    /* Delete Bookmark Location Request */
    public static final String bookmark_location_id = "bookmark_location_id";

    /* Confirm Booking Request */
    public static final String is_lipamathree = "is_lipamathree";
    public static final String payment_method = "payment_method";
    public static final String date = "date";
    public static final String booking_date = "booking_date";
    public static final String time = "time";
    public static final String rider_name = "rider_name";
    public static final String no_of_booked_seat = "no_of_booked_seat";
    public static final String check_out_status = "check_out_status";

    /* Confirm Booking response */
    public static final String booking_id = "booking_id";
    public static final String booking_status = "booking_status";
    public static final String payment_status = "payment_status";
    public static final String ride_status = "ride_status";
    public static final String driver_info = "driverinfo";
    public static final String pickup_station_name = "pickup_station_name";
    public static final String stationes = "stations";
    public static final String ride_start_time = "ride_start_time";
    public static final String ride_end_time = "ride_end_time";
    public static final String pickUpObj = "pickUpObj";
    public static final String dropOffObj = "dropOffObj";
    public static final String pay_type = "pay_type";//pay_during_ride / pay_now
    public static final String pay_now_discount_per = "pay_now_discount_per";

    /* Booking History response */
    public static final String bookingdetails = "bookingdetails";
    public static final String upcoming = "upcoming";
    public static final String past = "past";
    public static final String vehicle_name = "vehicle_name";
    public static final String vehicle_type = "vehicle_type";
    public static final String vehicle_code = "vehicle_code";
    public static final String vehicle_model = "vehicle_model";
    public static final String driver_id = "driver_id";
    public static final String vehicle_img = "vehicle_img";
    public static final String driverinfo = "driverinfo";
    public static final String arrived_time = "arrived_time";
    public static final String boarding_pass = "boarding_pass";
    public static final String vehicle_capacity = "vehicle_capacity";
    public static final String booking_count = "booking_count";
    public static final String ride_date = "ride_date";
    public static final String user_id = "user_id";
    public static final String vehicle_reg_no = "vehicle_reg_no";

    /* Cancel Ride Request */
    public static final String rider_id = "rider_id";
    public static final String pickupStationId = "pickupStationId";
    public static final String dropOffStationId = "dropOffStationId";
    public static final String cancel_reason = "cancel_reason";
    public static final String cancel_date = "cancel_date";
    public static final String cancel_time = "cancel_time";
    public static final String rating = "rating";
    public static final String from_lat = "from_lat";
    public static final String from_location = "from_location";
    public static final String from_long = "from_long";
    public static final String to_lat = "to_lat";
    public static final String to_long = "to_long";
    public static final String to_location = "to_location";

    /* Trip Issue Request */
    public static final String booking_time = "booking_time";
    public static final String dropoff_station_name = "dropoff_station_name";
    public static final String complaint_type = "complaint_type";
    public static final String complaint = "complaint";

    /* Referral List Response */
    public static final String total_earning = "total_earning";
    public static final String total_invites = "total_invites";
    public static final String users = "users";
    public static final String email = "email";
    public static final String profile_img = "profile_img";
    public static final String earning_amount = "earning_amount";
    public static final String created_at = "created_at";

    /* Promo Code Response */
    public static final String promo_value = "promo_value";

    /* Transactions History Request */
    public static final String from_date = "from_date";
    public static final String to_date = "to_date";

    /* Transaction History Response */
    public static final String txn_type = "txn_type";
    public static final String amount = "amount";
    public static final String transaction_date = "transaction_date";
    public static final String description = "description";
    public static final String txn_date_time = "txn_date_time";
    public static final String transaction_history = "txn_history";
    public static final String notification_id = "notification_id";
    public static final String title = "title";
    public static final String msg = "msg";
    public static final String date_time = "date_time";
    public static final String notifications = "notifications";
    public static final String pay_number = "pay_number";

    /*........................MPESA payment request....................*/
    public static final String device_id = "device_id";
    public static final String payer_mobile_number = "payer_mobile_number";
    public static final String account_number = "account_number";
    public static final String currency = "currency";
    public static final String payment_request_reference_UUID = "payment_request_reference_UUID";
    public static final String payment_origin = "payment_origin";
    public static final String payment_type = "payment_type";
    public static final String payment_source = "payment_source";
    public static final String reference_id = "reference_id";
}
