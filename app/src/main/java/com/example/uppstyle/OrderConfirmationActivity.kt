package com.example.uppstyle

import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import java.util.Random

class OrderConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Determine the layout resource based on the device orientation
        val layoutResId = if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE){
            R.layout.order_confirmation_layout_land
        } else{
            R.layout.order_confirmation_layout
        }
        setContentView(layoutResId)

        // Initialize views
        val invitationTextView = findViewById<TextView>(R.id.invitationTextView)
        val processCompletedTextView = findViewById<TextView>(R.id.processCompletedTextView)
        val orderNumberTextView = findViewById<TextView>(R.id.orderNumberTextView)
        val orderNumber = generateRandomOrderNumber()

        // Set order number text based on device language
        val local = Locale.getDefault()
        if(local.language == "iw"){
            orderNumberTextView.text = " מספר הזמנה: $orderNumber"
        }
        else {
            orderNumberTextView.text = "Order number: $orderNumber"
        }

        // Animate V image
        val vImageView = findViewById<ImageView>(R.id.animationImageView)
        animateVImage(vImageView)

        // Initialize and animate home button
        val homeButton = findViewById<Button>(R.id.homeButton)
        homeButton.post { animateHomeButtonEntry(homeButton) }
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Animate text entries sequentially
        animateTextEntries(invitationTextView, processCompletedTextView, orderNumberTextView)
    }

    // Generate a random order number
    private fun generateRandomOrderNumber(): String {
        val random = Random()
        return (1..10).map { random.nextInt(10) }.joinToString("")
    }

    // Animate V image with translation and scale animations
    private fun animateVImage(imageView: ImageView) {
        val translateAnimator = ObjectAnimator.ofFloat(imageView, "translationY", -100f, 0f).apply {
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            duration = 600
        }

        val scaleAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0.8f, 1f).apply {
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            duration = 500
        }

        AnimatorSet().apply {
            playTogether(translateAnimator, scaleAnimator)
            start()
        }
    }

    // Animate text entries with translation animation
    private fun animateTextEntries(vararg textViews: TextView) {
        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val animatorSet = AnimatorSet()
        val animators = textViews.mapIndexed { index, textView ->
            ObjectAnimator.ofFloat(textView, "translationX", -screenWidth, 0f).apply {
                startDelay = (index * 300).toLong()
                duration = 800
            }
        }
        animatorSet.playSequentially(animators)
        animatorSet.start()
    }

    // Animate home button entry with translation and vibration effect
    private fun animateHomeButtonEntry(button: Button) {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()

        // Entry animation from below the screen
        val entryAnimator = ObjectAnimator.ofFloat(button, "translationY", screenHeight, 0f).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Vibration effect
        val vibrationAnimator = ObjectAnimator.ofFloat(button, "translationX", 0f, 10f, -10f, 10f, -10f, 0f).apply {
            duration = 400
            repeatCount = 10  // Adjust repeat count to ensure vibration lasts approximately 5 seconds
            startDelay = 500  // Start vibrating after the entry animation has finished
        }

        AnimatorSet().apply {
            playTogether(entryAnimator, vibrationAnimator)
            start()
        }
    }
}