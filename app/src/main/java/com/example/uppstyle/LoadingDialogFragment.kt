package com.example.uppstyle

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class LoadingDialogFragment : DialogFragment() {

    // Override the onCreateDialog method to create the loading dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create an AlertDialog.Builder object
        val builder = AlertDialog.Builder(requireActivity())

        // Set the view of the dialog to the loading_layout XML layout resource
        builder.setView(R.layout.loading_layout)

        // Create and return the dialog
        return builder.create()
    }
}