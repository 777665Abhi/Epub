package com.sachy.epubreader.aboutUs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sachy.epubreader.R
import com.sachy.epubreader.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Data binding*/
        binding = DataBindingUtil.setContentView(
            this,R.layout.activity_about_us )

        //Set Toolbar
        this@AboutUsActivity.title = "Add DTR"
        //enabling back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }
}