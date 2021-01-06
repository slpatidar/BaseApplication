package com.sp.base.countrypicker;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.view.ViewCompat;

import com.sp.base.R;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class CountryPickerDialog extends AppCompatDialog {

    private List<Country> mCountryList;
    private CountryPickerCallbacks mPickerCallbacks;
    private ListView mListView;
    private String mHeadingCountryCode;
    private boolean showDialingCode;

    public CountryPickerDialog(Context context, CountryPickerCallbacks mPickerCallbacks) {
        this(context, mPickerCallbacks, null, true);
    }

    public CountryPickerDialog(Context context, CountryPickerCallbacks mPickerCallbacks, @Nullable String mHeadingCountryCode) {
        this(context, mPickerCallbacks, mHeadingCountryCode, true);
    }

    /**
     * You can set the heading country in headingCountryCode to show
     * your favorite country as the head of the list
     *
     * @param context
     * @param mPickerCallbacks
     * @param mHeadingCountryCode
     */
    public CountryPickerDialog(Context context, CountryPickerCallbacks mPickerCallbacks,
                               @Nullable String mHeadingCountryCode, boolean showDialingCode) {
        super(context);
        this.mPickerCallbacks = mPickerCallbacks;
        this.mHeadingCountryCode = mHeadingCountryCode;
        this.showDialingCode = showDialingCode;
        mCountryList = CountryPickerUtils.parseCountries(CountryPickerUtils.getCountriesJSON(this.getContext()));
        Collections.sort(mCountryList, new Comparator<Country>() {
            @Override
            public int compare(Country country1, Country country2) {
                final Locale locale = getContext().getResources().getConfiguration().locale;
                final Collator collator = Collator.getInstance(locale);
                collator.setStrength(Collator.PRIMARY);
                return collator.compare(
                        new Locale(locale.getLanguage(), country1.getIsoCode()).getDisplayCountry(),
                        new Locale(locale.getLanguage(), country2.getIsoCode()).getDisplayCountry());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_picker);
        ViewCompat.setElevation(getWindow().getDecorView(), 3);
        mListView = findViewById(R.id.country_picker_listview);

        CountryListAdapter adapter = new CountryListAdapter(this.getContext(), mCountryList, showDialingCode);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                Country country = mCountryList.get(position);
                mPickerCallbacks.onCountrySelected(country, CountryPickerUtils.getMipmapResId(getContext(),
                        country.getIsoCode().toLowerCase(Locale.ENGLISH) + "_flag"));
            }
        });

        scrollToHeadingCountry();
    }

    private void scrollToHeadingCountry() {
        if (mHeadingCountryCode != null) {
            for (int i = 0; i < mListView.getCount(); i++) {
                if (((Country) mListView.getItemAtPosition(i)).getIsoCode().toLowerCase()
                        .equals(mHeadingCountryCode.toLowerCase())) {
                    mListView.setSelection(i);
                }
            }
        }
    }

    public Country getCountryFromIsoCode(String isoCode) {
        for (int i = 0; i < mCountryList.size(); i++) {
            if (mCountryList.get(i).getIsoCode().equals(isoCode.toUpperCase())) {
                return mCountryList.get(i);
            }
        }
        return null;
    }
}
