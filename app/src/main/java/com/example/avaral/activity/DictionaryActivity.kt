package com.example.avaral.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.avaral.MainActivity
import com.example.avaral.R
import com.example.avaral.databinding.ActivityDictiionaryBinding
import com.example.avaral.viewmodel.DictionaryViewModel
import kotlinx.coroutines.launch

class DictionaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDictiionaryBinding
    private lateinit var mainViewModel: DictionaryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDictiionaryBinding.inflate(layoutInflater)
        mainViewModel = DictionaryViewModel()
        val view = binding.root
        setContentView(view)
        init()
        initViewModel()
        buttonMenuOnClick()

    }

    private fun init() {
    }

    private fun buttonMenuOnClick() = with(binding) {

        button.setOnClickListener {
            mainViewModel.loadEnWord("go")
        }

        bNavView.menu.findItem(R.id.id_dictionary).isChecked = true

        bNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.id_new_ad -> {

                    true
                }
                R.id.id_my_ads -> {
                    true
                }
                R.id.id_dictionary -> {

                    true
                }
                R.id.id_home -> {
                    val i = Intent(this@DictionaryActivity, MainActivity::class.java)
                    startActivity(i)
                    true
                }
                else -> false
            }

        }
    }


    private fun initViewModel() {
        lifecycleScope.launch {
            mainViewModel.flowWordsList.collect {
                it.forEach {
                    val str = binding.textView.text
                    val str2 = binding.textView2.text
                    binding.textView.text = "$str\n${it.avcat}"
                    binding.textView2.text = "$str\n${it.avderivatives}"
                }
            }
        }
    }
}