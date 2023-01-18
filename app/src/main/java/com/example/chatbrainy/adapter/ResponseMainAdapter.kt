package com.example.chatbrainy.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbrainy.R
import com.example.chatbrainy.model.Chat

class ResponseMainAdapter(val items: List<Chat>): RecyclerView.Adapter<ResponseMainAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_main, parent,false)
        return ViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        val scale: Float = Resources.getSystem().displayMetrics.density
        val dp20 = (20 * scale + 0.5f)
        val dp100 = (100 * scale + 0.5f)

        if (item.response == true){
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.rootView.context, R.color.cleanBlue))

            var textResponse = item.apiResponse?.choices?.get(0)?.text

            if(textResponse ==null || textResponse==""){
                holder.tvText.text="Digite novamente"
            }else{
                var stringSplice = textResponse?.split("\n\n")

                var stringChange : String? =""
                for (i in 1 until stringSplice!!.size){
                    if (i != 1){
                        stringChange = stringChange+"\n\n"+stringSplice[i]
                    }else{
                        stringChange = stringChange+stringSplice[i]
                    }
                }
                holder.tvText.text=stringChange
            }


            holder.tvText.setPadding(dp20.toInt(),0,dp100.toInt(),0)
            holder.tvText.textAlignment=View.TEXT_ALIGNMENT_TEXT_START

        }else{
            holder.tvText.text = item.answer
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView = itemView
        val layout = itemView.findViewById<ConstraintLayout>(R.id.layoutRecycler)
        val tvText = itemView.findViewById<TextView>(R.id.tvTextRecycler)
    }

}