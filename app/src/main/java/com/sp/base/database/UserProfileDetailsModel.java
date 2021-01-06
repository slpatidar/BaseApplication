package com.sp.base.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sp.base.util.SerializedNameKeys;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "user_profile_details")
public class UserProfileDetailsModel {

    @ColumnInfo(name = SerializedNameKeys.first_name)
    @SerializedName(SerializedNameKeys.first_name)
    @Expose
    private String firstName = "";

    @ColumnInfo(name = SerializedNameKeys.middle_name)
    @SerializedName(SerializedNameKeys.middle_name)
    @Expose
    private String middleName = "";

    @ColumnInfo(name = SerializedNameKeys.teke_teke_balance)
    @SerializedName(SerializedNameKeys.teke_teke_balance)
    @Expose
    private String teke_teke_balance = "";

    @ColumnInfo(name = SerializedNameKeys.last_name)
    @SerializedName(SerializedNameKeys.last_name)
    private String lastName = "";

    @ColumnInfo(name = SerializedNameKeys.profile_url)
    @SerializedName(SerializedNameKeys.profile_url)
    private String profile_url = "";

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = SerializedNameKeys.mobile)
    @SerializedName(SerializedNameKeys.mobile)
    private String mobile = "";

    @ColumnInfo(name = SerializedNameKeys.referral_parameter)
    @SerializedName(SerializedNameKeys.referral_parameter)
    private String referral_parameter = "";

    @ColumnInfo(name = SerializedNameKeys.riderId)
    @SerializedName(SerializedNameKeys.riderId)
    private String rider_id = "";

    @ColumnInfo(name = SerializedNameKeys.email)
    @SerializedName(SerializedNameKeys.email)
    private String email = "";

    @ColumnInfo(name = SerializedNameKeys.token)
    @SerializedName(SerializedNameKeys.token)
    private String token = "";
    @SerializedName(SerializedNameKeys.country_code)
    @Expose
    private String country_code;

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }


    public String getTeke_teke_balance() {
        return teke_teke_balance;
    }

    public void setTeke_teke_balance(String teke_teke_balance) {
        this.teke_teke_balance = teke_teke_balance;
    }

    public String getRider_id() {
        return rider_id;
    }

    public void setRider_id(String rider_id) {
        this.rider_id = rider_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    @NotNull
    public String getToken() {
        return token;
    }

    public void setToken(@NotNull String token) {
        this.token = token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReferral_parameter() {
        return referral_parameter;
    }

    public void setReferral_parameter(String referral_parameter) {
        this.referral_parameter = referral_parameter;
    }

    @NotNull
    @Override
    public String toString() {
        return "UserProfileDetails{" +
                "first_name='" + firstName + '\'' +
                ", middle_name='" + middleName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", profile_url='" + profile_url + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", referral_parameter='" + referral_parameter + '\'' +
                '}';
    }
}
