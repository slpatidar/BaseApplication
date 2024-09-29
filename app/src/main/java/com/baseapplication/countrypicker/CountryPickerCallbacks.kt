package com.baseapplication.countrypicker

open interface CountryPickerCallbacks {
    fun onCountrySelected(country: Country?, flagResId: Int)
}
