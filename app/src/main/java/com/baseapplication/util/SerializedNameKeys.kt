package com.baseapplication.util

object SerializedNameKeys {
    val key: String = "key"
    val statusCode: String = "code"
    val statusMessage: String = "message"
    const val data: String = "data"
    const  val id: String = "id"
    const  val riderId: String = "rider_id"
    val latitude: String = "latitude"
    val longitude: String = "longitude"
    val address: String = "address"
    val created: String = "created_at"
    val updated: String = "updated_at"
    val locations: String = "locations"
    val relative_url: String = "relative_url"
    val icon: String = "icon"
    val promo_amount: String = "promo_amount"
    val promo_code: String = "promo_code"

    /*NumberVerifyRequest*/
    const val mobile: String = "mobile"
    val booking_amount: String = "booking_amount"
    const val country_code: String = "country_code"

    /*NumberVerifyResponse*/
    val otp: String = "otp"
    val expire_in: String = "expire_in"

    /*Social login Request*/
    val type: String = "type"
    const val token: String = "token"
    val device_type: String = "device_type"
    val device_token: String = "device_token"
    val referral_code: String = "referral_code"

    /*Social login Response*/
    const val first_name: String = "first_name"
    const val middle_name: String = "middle_name"
    const val last_name: String = "last_name"
    const    val profile_url: String = "profile_url"
    const val referral_parameter: String = "referal_parameter"
    const  val teke_teke_balance: String = "teke_teke_balance"

    /*Add Location Request */
    val bookmark_type: String = "bookmark_type"
    val location_name: String = "location_name"

    /* Add Location Response*/
    val added_id: String = "added_id"
    val callID: String = "calId"

    /* Get Route list Request */
    const  val location: String = "location"
    val lat: String = "lat"
    const val from: String = "from"
    const val to: String = "to"

    /* Get Route list Response */
    val route_list: String = "routelist"
    val route_name: String = "route_name"
    val distance: String = "distance"
    val stations: String = "stations"

    /* Station Details */
    val station_id: String = "station_id"
    const val name: String = "name"
    val seq_num: String = "seq_num"
    val google_predicted_time: String = "google_predicted_time"
    val estimate_time: String = "estimate_time"

    /* Ride Timing Request */
    val route_id: String = "route_id"
    val source_st_id: String = "source_station_id"
    val destination_st_id: String = "destination_station_id"
    val day_index: String = "day_index"
    val current_date: String = "current_date"
    val current_time: String = "current_time"

    /* Ride Timing Response */
    val vehicle_list: String = "rides"

    /* Vehicle Details Model */
    val status: String = "status"
    val ride_price: String = "ride_price"
    val amount_to_be_paid: String = "amount_to_be_paid"
    val ride_id: String = "ride_id"
    val remaining_amount: String = "remaining_amount"
    val booking_fee: String = "booking_fee"
    val full_amount: String = "full_amount"
    val transaction_amount: String = "transaction_amount"

    /* Delete Bookmark Location Request */
    val bookmark_location_id: String = "bookmark_location_id"

    /* Confirm Booking Request */
    val is_lipamathree: String = "is_lipamathree"
    val payment_method: String = "payment_method"
    val date: String = "date"
    val booking_date: String = "booking_date"
    val time: String = "time"
    val rider_name: String = "rider_name"
    val no_of_booked_seat: String = "no_of_booked_seat"
    val check_out_status: String = "check_out_status"

    /* Confirm Booking response */
    val booking_id: String = "booking_id"
    val booking_status: String = "booking_status"
    val payment_status: String = "payment_status"
    val ride_status: String = "ride_status"
    val driver_info: String = "driverinfo"
    val pickup_station_name: String = "pickup_station_name"
    val stationes: String = "stations"
    val ride_start_time: String = "ride_start_time"
    val ride_end_time: String = "ride_end_time"
    val pickUpObj: String = "pickUpObj"
    val dropOffObj: String = "dropOffObj"
    val pay_type: String = "pay_type" //pay_during_ride / pay_now
    val pay_now_discount_per: String = "pay_now_discount_per"

    /* Booking History response */
    val bookingdetails: String = "bookingdetails"
    val upcoming: String = "upcoming"
    val past: String = "past"
    val vehicle_name: String = "vehicle_name"
    val vehicle_type: String = "vehicle_type"
    val vehicle_code: String = "vehicle_code"
    val vehicle_model: String = "vehicle_model"
    val driver_id: String = "driver_id"
    val vehicle_img: String = "vehicle_img"
    val driverinfo: String = "driverinfo"
    val arrived_time: String = "arrived_time"
    val boarding_pass: String = "boarding_pass"
    val vehicle_capacity: String = "vehicle_capacity"
    val booking_count: String = "booking_count"
    val ride_date: String = "ride_date"
    val user_id: String = "user_id"
    val vehicle_reg_no: String = "vehicle_reg_no"

    /* Cancel Ride Request */
    val rider_id: String = "rider_id"
    val pickupStationId: String = "pickupStationId"
    val dropOffStationId: String = "dropOffStationId"
    val cancel_reason: String = "cancel_reason"
    val cancel_date: String = "cancel_date"
    val cancel_time: String = "cancel_time"
    val rating: String = "rating"
    val from_lat: String = "from_lat"
    val from_location: String = "from_location"
    val from_long: String = "from_long"
    val to_lat: String = "to_lat"
    val to_long: String = "to_long"
    val to_location: String = "to_location"

    /* Trip Issue Request */
    val booking_time: String = "booking_time"
    val dropoff_station_name: String = "dropoff_station_name"
    val complaint_type: String = "complaint_type"
    val complaint: String = "complaint"

    /* Referral List Response */
    val total_earning: String = "total_earning"
    val total_invites: String = "total_invites"
    val users: String = "users"
    const val email: String = "email"
    val profile_img: String = "profile_img"
    val earning_amount: String = "earning_amount"
    val created_at: String = "created_at"

    /* Promo Code Response */
    val promo_value: String = "promo_value"

    /* Transactions History Request */
    val from_date: String = "from_date"
    val to_date: String = "to_date"

    /* Transaction History Response */
    val txn_type: String = "txn_type"
    val amount: String = "amount"
    val transaction_date: String = "transaction_date"
    val description: String = "description"
    val txn_date_time: String = "txn_date_time"
    val transaction_history: String = "txn_history"
    val notification_id: String = "notification_id"
    val title: String = "title"
    val msg: String = "msg"
    val date_time: String = "date_time"
    val notifications: String = "notifications"
    val pay_number: String = "pay_number"

    /*........................MPESA payment request....................*/
    val device_id: String = "device_id"
    val payer_mobile_number: String = "payer_mobile_number"
    val account_number: String = "account_number"
    val currency: String = "currency"
    val payment_request_reference_UUID: String = "payment_request_reference_UUID"
    val payment_origin: String = "payment_origin"
    val payment_type: String = "payment_type"
    val payment_source: String = "payment_source"
    val reference_id: String = "reference_id"
}
