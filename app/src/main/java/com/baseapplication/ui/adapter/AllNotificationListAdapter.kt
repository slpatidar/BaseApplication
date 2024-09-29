package com.onetuchservice.business.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baseapplication.R
import com.baseapplication.ui.adapter.AllNotificationData
import com.onetuchservice.business.ui.model.ListData

class AllNotificationListAdapter( val mContext: Context) :
    PagedListAdapter<ListData, AllNotificationListAdapter.ViewHolder>(
        DIFF_CALLBACk
    ) {
    private var mOnClickListener: OnClickListener? = null
    var listDataPagedList: PagedList<ListData>? = null



    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.row_list, null)
        return ViewHolder(view)
    }

    public override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notificationData: ListData? = getItem(position) // mArrayList.get(position);
        //        Glide.with(mContext)
//                .load(notificationData.getIcon())// or URI/path
//                .into(holder.imgIcon);
        Log.e("SP", "onBindViewHolder: $notificationData" )
        if (notificationData != null) holder.txtessage.setText(notificationData.name)
        //        holder.txtTime.setText(notificationData.getNotification_date() + " " + notificationData.getNotification_time());

//        holder.layRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnClickListener.onClick(notificationData);
//            }
//        });
    }

    fun submitList(results: ArrayList<ListData>?) {
        Log.e("SP", "submitList: "+results?.size )
        if (results != null) {
            for (listData: ListData? in results) {
                listDataPagedList?.add(listData)
            }
        }
        submitList(listDataPagedList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgIcon: AppCompatImageView? = null
        var txtessage: AppCompatTextView
        var txtTime: AppCompatTextView? = null
        var layRow: RelativeLayout? = null

        init {
            //            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtessage = itemView.findViewById(R.id.txtMessage)
            //            layRow = itemView.findViewById(R.id.layRow);
//            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }

    fun setOnclicklistener(onclicklistener: OnClickListener?) {
        mOnClickListener = onclicklistener
    }

    open interface OnClickListener {
        fun onClick(notificationData: AllNotificationData?)
    }

    companion object {
        private val DIFF_CALLBACk: DiffUtil.ItemCallback<ListData> =
            object : DiffUtil.ItemCallback<ListData>() {
                public override fun areItemsTheSame(oldItem: ListData, newItem: ListData): Boolean {
                    return oldItem.name === newItem.name
                }

                @SuppressLint("DiffUtilEquals")
                public override fun areContentsTheSame(
                    oldItem: ListData,
                    newItem: ListData
                ): Boolean {
                    return (oldItem == newItem)
                }
            }
    }




//    companion object CREATOR : Parcelable.Creator<AllNotificationListAdapter> {
//        override fun createFromParcel(parcel: Parcel): AllNotificationListAdapter {
//            return AllNotificationListAdapter(parcel)
//        }
//
//        override fun newArray(size: Int): Array<AllNotificationListAdapter?> {
//            return arrayOfNulls(size)
//        }
//    }

}
