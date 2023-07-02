package com.example.avarapp.dialog

import android.app.AlertDialog
import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.avarapp.R
import com.example.avarapp.model.Word


class WordDialog {
    fun showDialog(context: Context, word: Word) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        val rootView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
        val rcView = rootView.findViewById<LinearLayout>(R.id.d_layout)
        dialog.setView(rootView)
        setViewListener(rcView,word)
        dialog.show()
    }

    private fun setViewListener(rcView: LinearLayout, word: Word) {
        with(rcView){
            findViewById<TextView>(R.id.tvAvName).text = word.avname
            findViewById<TextView>(R.id.tvRusName).text = word.rusname
            findViewById<TextView>(R.id.tvTrName).text = word.trname
            findViewById<TextView>(R.id.tvEnName).text = word.enname
            val tvDesc = findViewById<TextView>(R.id.word_description)
            tvDesc.movementMethod = ScrollingMovementMethod()
            tvDesc.text = word.avexample
        }
    }
}