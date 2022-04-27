package com.sachy.epubreader.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sachy.epubreader.R

class HomeAdapter(private val bookList:ArrayList<BookPojo>) :RecyclerView.Adapter<HomeAdapter.ViewHolder>()
{
    class ViewHolder(view:View):RecyclerView.ViewHolder(view)
    {
        var ivImage: ImageView = view.findViewById(R.id.ivBook)
        var tvBookName: TextView = view.findViewById(R.id.tvBookName)
        var tvBookDetail:TextView=view.findViewById(R.id.tvBookDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvBookName.text=bookList[position].bookName
        holder.tvBookDetail.text=bookList[position].bookDetail
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}