package com.sachy.epubreader.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.sachy.epubreader.R
import com.sachy.epubreader.aboutUs.AboutUsActivity
import com.sachy.epubreader.databinding.ActivityHomeBinding
import com.sachy.epubreader.utils.ToastUtil

class HomeActivity : AppCompatActivity() {
    var doubleBackToExitPressedOnce = false
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding :ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        init()
    }

    /**
     * Side navigation and Home screen*/
    private fun init()
    {
        setupNavDrawer()
        setRecyclerView()
    }

    /**Back button proper handling*/
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        ToastUtil.showToast(this, "Please click BACK again to exit")
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)

    }
    /** Hamburg icon click handling*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId: Int = item.itemId
        if (itemId == android.R.id.home) {
            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    /**Setting up navigation drawer toggle for opening and closing the drawer*/
    private fun setupNavDrawer() {

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                }
                R.id.about_us ->{
                 startActivity(Intent(this,AboutUsActivity::class.java))
                }

            }
            true
        }
    }

    /**Set Recyclerview*/
    private fun setRecyclerView()
    {
        binding.progressBar.visibility= View.GONE
        val bookList=ArrayList<BookPojo>()
        bookList.add(BookPojo("Alice in wonderland","It's good book to read",""))
        bookList.add(BookPojo("User","It's good book to read,Now you can read it.",""))
        bookList.add(BookPojo("Wonderland","It's good book to read,Let's go fot it. Wonderland\",\"It's good book to read,Let's go fot it.c",""))

        binding.rvHome.adapter = HomeAdapter( bookList)
        binding.rvHome.layoutManager = GridLayoutManager(this,2)
    }

}