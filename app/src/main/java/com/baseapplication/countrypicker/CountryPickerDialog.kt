package com.baseapplication.countrypicker

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatDialog
import androidx.core.view.ViewCompat
import com.baseapplication.R
import java.text.Collator
import java.util.Collections
import java.util.Locale

class CountryPickerDialog @JvmOverloads constructor(
    context: Context?, private val mPickerCallbacks: CountryPickerCallbacks,
    private val mHeadingCountryCode: String? = null, private val showDialingCode: Boolean = true
) : AppCompatDialog(
    (context)!!
) {
    private val mCountryList: List<Country>?
    private var mListView: ListView? = null

    /**
     * You can set the heading country in headingCountryCode to show
     * your favorite country as the head of the list
     *
     * @param context
     * @param mPickerCallbacks
     * @param mHeadingCountryCode
     */
    init {
        mCountryList = CountryPickerUtils.parseCountries(
            CountryPickerUtils.getCountriesJSON(
                getContext()
            )
        )
        Collections.sort(mCountryList, object : Comparator<Country> {
            public override fun compare(country1: Country, country2: Country): Int {
                val locale: Locale = getContext().getResources().getConfiguration().locale
                val collator: Collator = Collator.getInstance(locale)
                collator.setStrength(Collator.PRIMARY)
                return collator.compare(
                    Locale(locale.getLanguage(), country1.isoCode).getDisplayCountry(),
                    Locale(locale.getLanguage(), country2.isoCode).getDisplayCountry()
                )
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_picker)
        ViewCompat.setElevation(getWindow()!!.getDecorView(), 3f)
        mListView = findViewById(R.id.country_picker_listview)
        val adapter: CountryListAdapter =
            CountryListAdapter(getContext(), mCountryList, showDialingCode)
        mListView!!.setAdapter(adapter)
        mListView!!.setOnItemClickListener(object : OnItemClickListener {
            public override fun onItemClick(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                dismiss()
                val country: Country? = mCountryList!!.get(position)
                mPickerCallbacks.onCountrySelected(
                    country, CountryPickerUtils.getMipmapResId(
                        getContext(),
                        country?.isoCode?.lowercase() + "_flag"
                    )
                )
            }
        })
        scrollToHeadingCountry()
    }

    private fun scrollToHeadingCountry() {
        if (mHeadingCountryCode != null) {
            for (i in 0 until mListView!!.getCount()) {
                if (((mListView!!.getItemAtPosition(i) as Country).isoCode?.lowercase(Locale.getDefault())
                            == mHeadingCountryCode.lowercase(Locale.getDefault()))
                ) {
                    mListView!!.setSelection(i)
                }
            }
        }
    }

    fun getCountryFromIsoCode(isoCode: String): Country? {
        for (i in mCountryList!!.indices) {
            if ((mCountryList.get(i)?.isoCode == isoCode.uppercase(Locale.getDefault()))) {
                return mCountryList.get(i)
            }
        }
        return null
    }
}
