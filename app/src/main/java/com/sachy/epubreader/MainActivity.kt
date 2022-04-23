package com.sachy.epubreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import com.folioreader.FolioReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init()
    {
//        val folioReader = FolioReader.get()
//        folioReader.openBook(R.raw.book)

    }
}