package com.baseapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
open interface DBDAO {
    /* For InviteUser Data*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserData(user: UserProfileDetailsModel)

    @get:Query("SELECT * FROM user_profile_details")
    val userProfileDetails: UserProfileDetailsModel
    //    @Delete
    //    void delete(UserProfileDetailsModel user);
    //
    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //    void insertSavedLocation(List<PopulerRoute> savedLocation);
    //
    //    @Query("SELECT * FROM saved_location")
    //    List<PopulerRoute> getAllSavedLocation();
    //
    //    @Query("SELECT * FROM saved_location WHERE location_name =:locationName")
    //    PopulerRoute getSavedLocation(String locationName);
    //
    //    @Query("DELETE FROM saved_location")
    //    void deleteAllSavedLocation();
    //
    //    @Query("DELETE FROM saved_location WHERE location_name =:field")
    //    void deleteSavedLocationFor(String field);
}
