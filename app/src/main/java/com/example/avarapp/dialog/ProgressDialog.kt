package com.example.avarapp.dialog

import android.app.Activity
import android.app.AlertDialog
import com.example.avarapp.databinding.ProgressDailogLayoutBinding

object ProgressDialog {

    fun createProgressDialog(activity: Activity) : AlertDialog {
        val builder = AlertDialog.Builder(activity)
        val rootDialogElement = ProgressDailogLayoutBinding.inflate(activity.layoutInflater)
        val view = rootDialogElement.root
        builder.setView(view)

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }
}