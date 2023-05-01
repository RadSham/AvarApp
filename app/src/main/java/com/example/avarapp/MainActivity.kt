package com.example.avarapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import com.example.avarapp.R
import com.example.avarapp.activity.DictionaryActivity
import com.example.avarapp.databinding.ActivityMainBinding
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private var currentCategory: String? = null
    private lateinit var tvAccount: TextView
    private lateinit var imAccount: ImageView
    lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        MobileAds.initialize(this) {}
        init()
        initSupportActionBar(binding)
    }

    private fun init() {
        currentCategory = getString(R.string.def)
        buttonMenuOnClick()
        onActivityResult()
        navViewSettings()
    }

    fun initSupportActionBar(binding: ActivityMainBinding) {
        setSupportActionBar(binding.mainContent.toolbar)
        val toggle =
            ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.mainContent.toolbar,
                R.string.open,
                R.string.close
            )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)
        tvAccount = binding.navView.getHeaderView(0).findViewById(R.id.tvAccountEmail)
        imAccount = binding.navView.getHeaderView(0).findViewById(R.id.imAccountImage)
    }


    private fun navViewSettings() = with(binding) {
        val menu = navView.menu
        val adsCat = menu.findItem(R.id.ads_cat)
        val spanAdsCat = SpannableString(adsCat.title)
        if (adsCat.title != null) {
            spanAdsCat.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.red
                    )
                ), 0, adsCat.title!!.length, 0
            )
        }
        adsCat.title = spanAdsCat

        val accCat = menu.findItem(R.id.acc_cat)
        val spanAccCat = SpannableString(accCat.title)
        if (accCat.title != null) {
            spanAccCat.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.green_main
                    )
                ), 0, accCat.title!!.length, 0
            )
        }
        accCat.title = spanAccCat
    }

    private fun onActivityResult() {
        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                    val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        if (account != null) {
//                            dialogHelper.accHelper.signInFirebaseWithGoogle(account.idToken.toString())
                        }
                    } catch (e: ApiException) {
                        Toast.makeText(this, "Api error : ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }


    private fun buttonMenuOnClick() = with(binding) {
        mainContent.bNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.id_new_ad -> {

                    true
                }
                R.id.id_my_ads -> {
                    true
                }
                R.id.id_dictionary -> {
                    val i = Intent(this@MainActivity, DictionaryActivity::class.java)
                    googleSignInLauncher.launch(i)
                    true
                }
                R.id.id_home -> {
                    currentCategory = getString(R.string.def)
//                    firebaseViewModel.loadAllAdsFirstPage(filterDb)
                    mainContent.toolbar.title = getString(R.string.def)
                    true
                }
                else -> false
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }


}