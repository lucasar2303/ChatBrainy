package com.example.chatbrainy.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.size
import androidx.viewpager.widget.ViewPager
import com.example.chatbrainy.R
import com.example.chatbrainy.adapter.OnBoardingViewPagerAdapter
import com.example.chatbrainy.databinding.ActivityOnBoardingBinding
import com.example.chatbrainy.model.OnBoardingData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    var tablayout: TabLayout? = null
    var onBoardingViewPager: ViewPager? = null
    var nextBtn: TextView? = null
    var position = 0
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tablayout = binding.tabIndicator
        nextBtn = binding.btnNext

        val onBoardingData:MutableList<OnBoardingData> = ArrayList()

        onBoardingData.add(OnBoardingData(resources.getString(R.string.titleOnBoarding1), resources.getString(R.string.textOnBoarding1), R.drawable.interrogation ))
        onBoardingData.add(OnBoardingData(resources.getString(R.string.titleOnBoarding2), resources.getString(R.string.textOnBoarding2), R.drawable.ia))
        onBoardingData.add(OnBoardingData(resources.getString(R.string.titleOnBoarding3), resources.getString(R.string.textOnBoarding3), R.drawable.talkto))

        setOnBoardingViewPagerAdapter(onBoardingData)

        position = onBoardingViewPager!!.currentItem

        nextBtn?.setOnClickListener{
            if (position<onBoardingData.size){
                position++
                onBoardingViewPager!!.currentItem=position
            }
            if (position == onBoardingData.size){
                savePrefData()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        tablayout!!.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == onBoardingData.size-1){
                    nextBtn!!.text=resources.getString(R.string.nextOnBoarding1)
                }else{
                    nextBtn!!.text=resources.getString(R.string.nextOnBoarding2)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){

        onBoardingViewPager = findViewById(R.id.screenPager)
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        tablayout?.setupWithViewPager(onBoardingViewPager)

    }

    private fun savePrefData(){
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("FirstTimeOnBoarding", true)
        editor.apply()
    }
}