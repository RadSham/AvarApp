package com.example.avarapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avarapp.MainActivity
import com.example.avarapp.R
import com.example.avarapp.adapter.WordAdapter
import com.example.avarapp.databinding.ActivityDictionaryBinding
import com.example.avarapp.model.Word
import com.example.avarapp.viewmodel.DictionaryViewModel
import java.util.*
import kotlin.collections.ArrayList

class DictionaryActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityDictionaryBinding
    private val dictionaryViewModel: DictionaryViewModel by viewModels()
    private lateinit var adapter: WordAdapter
    private var wordArray = ArrayList<Word>()

    var selectedFirstLanguage = "Авар мацI"
    var selectedSecondLanguage = "Русский язык"
    private val languages = arrayOf("Авар мацI", "Русский язык", "English", "Turkce")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDictionaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
        initView()
        initViewModel()
        initRecyclerView()
        initSearchView()
        buttonMenuOnClick()
        initAdapter()
    }

    private fun init() {
    }

    private fun initView() = with(binding) {
        //spinners
        val aa =
            ArrayAdapter(this@DictionaryActivity, android.R.layout.simple_spinner_item, languages)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(spinner)
        {
            id = FIRST_SPINNER_ID
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@DictionaryActivity
            prompt = "Select your favourite language"
            gravity = Gravity.CENTER
        }

        with(spinner2)
        {
            id = SECOND_SPINNER_ID
            adapter = aa
            setSelection(1, false)
            onItemSelectedListener = this@DictionaryActivity
            prompt = "Select your favourite language"
            gravity = Gravity.CENTER
        }

    }


    private fun buttonMenuOnClick() = with(binding) {

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

    private fun initRecyclerView() {
        adapter = WordAdapter(this, wordArray)
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@DictionaryActivity)
            rcView.adapter = adapter
        }
    }

    private fun initAdapter() {
        dictionaryViewModel.loadAllWords(this)
    }

    private fun filterWords(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<Word>()

        // running a for loop to compare elements.
        for (item in wordArray) {
            // checking if the entered string matched with any item of our recycler view.
            when (selectedFirstLanguage) {
                "Авар мацI" -> if (item.avname.contains(text.lowercase(Locale.getDefault()))) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item)
                }
                "Русский язык" -> if (item.rusname.contains(text.lowercase(Locale.getDefault()))) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item)
                }
                "Turkce" -> if (item.trname.contains(text.lowercase(Locale.getDefault()))) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item)
                }
                "English" -> if (item.enname.contains(text.lowercase(Locale.getDefault()))) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item)
                }
            }

        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            showToast(this, "No Data Found..")
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }


    private fun initSearchView() {
        binding.svDictionary.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterWords(newText)
                }
                return false
            }

        })
    }

    private fun initViewModel() {
        dictionaryViewModel.liveWordsData.observe(this) {
            println("initViewModel")
            wordArray = it
            adapter.updateAdapter(wordArray)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            when (parent.id) {
                0 -> {
                    selectedFirstLanguage = languages[position]
                    filterWords(binding.svDictionary.query.toString())
                }
                1 -> {
                    selectedSecondLanguage = languages[position]
                    filterWords(binding.svDictionary.query.toString())
                }
            }
        }
        myLog("$selectedFirstLanguage $selectedSecondLanguage")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        showToast(message = "Nothing selected")
    }


    fun showToast(
        context: Context = applicationContext,
        message: String,
        duration: Int = Toast.LENGTH_LONG
    ) {
        Toast.makeText(context, message, duration).show()
    }

    fun myLog(message: Any) {
        Log.d("MyLog", message.toString())
    }

    companion object {
        private const val FIRST_SPINNER_ID = 0
        private const val SECOND_SPINNER_ID = 1
    }
}