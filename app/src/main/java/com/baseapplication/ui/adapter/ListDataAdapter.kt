package com.baseapplication.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baseapplication.R
import com.baseapplication.ui.APICallingActivity
import com.onetuchservice.business.ui.model.ListData

class ListDataAdapter(context: Context) : PagedListAdapter<ListData, ListDataAdapter.ListDataViewHolder>(DIFF_CALLBACK) {

    // ViewHolder class
    class ListDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listData: ListData) {
            itemView?.findViewById<TextView>(R.id.txtMessage)?.text = listData.name // Bind data to your view
        }
    }

    // Inflate the item layout and return a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_list, parent, false)
        return ListDataViewHolder(view)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: ListDataViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    // DiffUtil callback for efficiently updating only changed items
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListData>() {
            override fun areItemsTheSame(oldItem: ListData, newItem: ListData): Boolean =
                oldItem.name== newItem.name

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ListData, newItem: ListData): Boolean =
                oldItem == newItem
        }
    }
}
