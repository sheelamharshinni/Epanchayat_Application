package com.techdatum.epanchayat_application

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.techdatum.epanchayat_application.activity.LoginActivity
import com.techdatum.epanchayat_application.webservices.InterNetReceiver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var navController: NavController? = null
    var isConnected = false

    companion object {
        var epanchayatApplication: MainActivity =
            MainActivity()

        fun getInstance(): MainActivity {
            if (epanchayatApplication == null) {
                epanchayatApplication =
                    MainActivity()
            }
            return epanchayatApplication
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        epanchayatApplication = this
        isConnected = InterNetReceiver.isConnected
        setupNavigation()
        sidemenu()
    }

    @SuppressLint("ResourceAsColor")
    private fun setupNavigation() {

        setSupportActionBar(toolbar)
        if (toolbar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController!!, drawer_layout)
        NavigationUI.setupWithNavController(navigationView, navController!!)

        bottomNavigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.MainBottomFragment -> {
                    item.isChecked = false
                    navController!!.navigate(R.id.RoadsFragment)
                    true
                }
                R.id.drainsfragment -> {
                    navController!!.navigate(R.id.DrainsFragment)
                    true
                }
                R.id.publicinstitution -> {
                    navController!!.navigate(R.id.InstitutionsFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            drawer_layout,
            Navigation.findNavController(this, R.id.nav_host_fragment)
        )
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun sidemenu() {
        lay_roads.setOnClickListener(this)
        lay_drains.setOnClickListener(this)
        lay_publicinstituion.setOnClickListener(this)
        lay_dumpyard.setOnClickListener(this)
        lay_graveyard.setOnClickListener(this)
        lay_Logout.setOnClickListener(this)
        lay_home.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        val id = p0!!.id
        when (id) {
            R.id.lay_roads -> {
                navController!!.navigate(R.id.RoadsFragment)
                drawer_layout.closeDrawer(GravityCompat.START)

            }
            R.id.lay_drains -> {
                navController!!.navigate(R.id.DrainsFragment)
                drawer_layout.closeDrawer(GravityCompat.START)

            }

            R.id.lay_publicinstituion -> {
                navController!!.navigate(R.id.InstitutionsFragment)
                drawer_layout.closeDrawer(GravityCompat.START)

            }
//            R.id.lay_dumpyard -> {
//                navController!!.navigate(R.id.propertymorefieldsFragment)
//                drawer_layout.closeDrawer(GravityCompat.START)
//            }
//            R.id.lay_graveyard -> {
//                navController!!.navigate(R.id.mutationymorefieldsFragment)
//                drawer_layout.closeDrawer(GravityCompat.START)
//            }
            R.id.lay_home -> {
                navController!!.navigate(R.id.defaultFragment)
            }
            R.id.lay_Logout -> {
                logoutdialog()
                drawer_layout.closeDrawer(GravityCompat.START)
            }

        }
    }

    fun logoutdialog() {
        val dialogs = Dialog(this@MainActivity)
        dialogs.setContentView(R.layout.dialog_logout)
        dialogs.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val yesBtn = dialogs.findViewById(R.id.lay_logout_s) as RelativeLayout
        val noBtn = dialogs.findViewById(R.id.lay_cancel) as RelativeLayout
        yesBtn.setOnClickListener {
            //            SaveSharedPreference.getSessionDataInstance().clearAll()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            dialogs.dismiss()
        }
        noBtn.setOnClickListener { dialogs.dismiss() }
        dialogs.show()
    }
}
