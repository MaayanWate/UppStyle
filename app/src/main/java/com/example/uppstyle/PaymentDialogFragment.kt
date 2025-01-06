package com.example.uppstyle

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import android.os.Handler
import android.os.Looper
import android.content.res.Configuration
import java.util.Locale

class PaymentDialogFragment : DialogFragment() {

    // Get the default locale of the device
    val local = Locale.getDefault()

    companion object {
        // Create a new instance of PaymentDialogFragment with a specified sum
        fun createInstance(sum: Float): PaymentDialogFragment {
            val arguments = Bundle()
            arguments.putFloat("sum", sum)
            return PaymentDialogFragment().apply { this.arguments = arguments }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout based on the device orientation
        val orientation = resources.configuration.orientation
        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.payment_layout_land, container, false)
        } else {
            inflater.inflate(R.layout.payment_layout, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sum = arguments?.getFloat("sum")
        // Set the text and click listener for the approve button
        view.findViewById<Button>(R.id.approve).apply {
            val buttonText = if(local.language == "iw") " ללחוץ לתשלום $sum $" else "Click to pay $sum $"
            text = buttonText
            setOnClickListener {
                showConfirmationDialog(sum)
            }
        }
        // Set click listener for the back button
        view.findViewById<Button>(R.id.back_to_checkout).setOnClickListener {
            dismiss()
            val intent = Intent(context, CheckoutActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }

    // Show the payment confirmation dialog
    private fun showConfirmationDialog(sum: Float?) {
        AlertDialog.Builder(requireContext()).apply {
            if (local.language == "iw") {
                setTitle("אישור תשלום")
                setMessage(" אתה בטוח שתרצה לשלם $sum $?")
                setPositiveButton("כן") { dialog, which ->
                    processPayment()
                }
                setNegativeButton("לא", null)
                show()
            } else {
                setTitle("Payment Confirmation")
                setMessage("Are you sure you want to pay $sum $?")
                setPositiveButton("Yes") { dialog, which ->
                    processPayment()
                }
                setNegativeButton("No", null)
                show()
            }
        }
    }

    // Process the payment
    private fun processPayment() {
        // Show loading dialog
        val loadingDialog = LoadingDialogFragment()
        loadingDialog.show(parentFragmentManager, "LoadingDialog")

        // Simulate a loading delay of 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            loadingDialog.dismiss() // Dismiss the loading dialog after the delay

            // Start OrderConfirmationActivity directly
            val intent = Intent(context, OrderConfirmationActivity::class.java)
            startActivity(intent)

            // Show toast message based on device language
            if (local.language == "iw") {
                Toast.makeText(context, "פעולת התשלום שלך הצליחה", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(context, "Your payment operation successfully", Toast.LENGTH_LONG)
                    .show()
            }
            activity?.setResult(Activity.RESULT_OK)
            activity?.finish()
        }, 3000)
    }

    // Resize the dialog window on resume
    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}