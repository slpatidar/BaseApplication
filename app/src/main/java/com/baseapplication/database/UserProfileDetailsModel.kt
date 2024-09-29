package com.baseapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.baseapplication.util.SerializedNameKeys

@Entity(tableName = "user_profile_details")
class UserProfileDetailsModel() {
    @ColumnInfo(name = SerializedNameKeys.first_name)
    @SerializedName(SerializedNameKeys.first_name)
    @Expose
    var firstName: String = ""

    @ColumnInfo(name = SerializedNameKeys.middle_name)
    @SerializedName(SerializedNameKeys.middle_name)
    @Expose
    var middleName: String = ""

    @ColumnInfo(name = SerializedNameKeys.teke_teke_balance)
    @SerializedName(SerializedNameKeys.teke_teke_balance)
    @Expose
    var teke_teke_balance: String = ""

    @ColumnInfo(name = SerializedNameKeys.last_name)
    @SerializedName(SerializedNameKeys.last_name)
    var lastName: String = ""

    @ColumnInfo(name = SerializedNameKeys.profile_url)
    @SerializedName(SerializedNameKeys.profile_url)
    var profile_url: String = ""

    @PrimaryKey
    @ColumnInfo(name = SerializedNameKeys.mobile)
    @SerializedName(SerializedNameKeys.mobile)
    var mobile: String = ""

    @ColumnInfo(name = SerializedNameKeys.referral_parameter)
    @SerializedName(SerializedNameKeys.referral_parameter)
    var referral_parameter: String = ""

    @ColumnInfo(name = SerializedNameKeys.riderId)
    @SerializedName(SerializedNameKeys.riderId)
    var rider_id: String = ""

    @ColumnInfo(name = SerializedNameKeys.email)
    @SerializedName(SerializedNameKeys.email)
    var email: String = ""

    @ColumnInfo(name = SerializedNameKeys.token)
    @SerializedName(SerializedNameKeys.token)
    var token: String = ""

    @SerializedName(SerializedNameKeys.country_code)
    @Expose
    var country_code: String? = null
    public override fun toString(): String {
        return ("UserProfileDetails{" +
                "first_name='" + firstName + '\'' +
                ", middle_name='" + middleName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", profile_url='" + profile_url + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", referral_parameter='" + referral_parameter + '\'' +
                '}')
    }
}
