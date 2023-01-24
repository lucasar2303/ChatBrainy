package com.example.chatbrainy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.chatbrainy.R
import com.example.chatbrainy.model.OnBoardingData

class OnBoardingViewPagerAdapter(private var context: Context, private var onBoardingDataList: List<OnBoardingData> ) : PagerAdapter() {
    override fun getCount(): Int {
        return onBoardingDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.onboarding_screen_layout, null)
        val title: TextView
        val desc: TextView
       // val image: ImageView

        title = view.findViewById(R.id.title)
        desc = view.findViewById(R.id.desc)
       // image = view.findViewById(R.id.imageView)

        title.text = onBoardingDataList[position].title
        desc.text = onBoardingDataList[position].desc
       // image.setImageResource(onBoardingDataList[position].image)



        container.addView(view)
        return view
    }
}