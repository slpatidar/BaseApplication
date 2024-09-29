package com.baseapplication.countrypicker

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.baseapplication.R
import java.util.Locale

class CountryListAdapter(
    private val mContext: Context,
    private val mCountryList: List<Country>?,
    private val showDialingCode: Boolean
) : BaseAdapter() {
    private val mInflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val TAG: String = CountryListAdapter::class.java.getSimpleName()

    public override fun getCount(): Int {
        return mCountryList?.size?:0
    }

    public override fun getItem(position: Int): Country? {
        return mCountryList?.get(position)?:null
    }

    public override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("SetTextI18n")
    public override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var itemView: View = convertView
        val item: Item
        val country: Country? = mCountryList?.get(position)?:null
        if (convertView == null) {
            item = Item()
            itemView = mInflater.inflate(R.layout.item_country, parent, false)
            item.icon = itemView.findViewById<View>(R.id.icon) as ImageView?
            item.name = itemView.findViewById<View>(R.id.name) as TextView?
            itemView.setTag(item)
        } else {
            item = itemView.getTag() as Item
        }
        item.name?.text = Locale(
            mContext.resources.configuration.locale.language,
            country?.isoCode
        ).displayCountry + (if (showDialingCode) " (+" + country?.dialingCode + ")" else "")

        // Load drawable dynamically from country code
        val drawableName: String = country?.isoCode?.lowercase() + "_flag"
        item.icon?.setImageResource(CountryPickerUtils.getMipmapResId(mContext, drawableName))
        return itemView
    }

    class Item() {
        var name: TextView? = null
        var icon: ImageView? = null
    }

}
