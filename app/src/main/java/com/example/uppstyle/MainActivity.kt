package com.example.uppstyle

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AccelerateDecelerateInterpolator
import android.animation.AnimatorSet

class MainActivity : AppCompatActivity() {

    private lateinit var btnViewGallery: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Determine the current orientation
        val orientation = resources.configuration.orientation

        // Load the appropriate layout based on orientation
        if (orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main)
        } else {
            setContentView(R.layout.activity_main)
        }

        val textTitle = findViewById<TextView>(R.id.textTitle)
        val arrowImageView = findViewById<ImageView>(R.id.arrowImageView)
        btnViewGallery = findViewById(R.id.btnViewGallery)

        // Initially hide the text, arrow, and button
        textTitle.visibility = View.INVISIBLE
        arrowImageView.visibility = View.INVISIBLE
        btnViewGallery.visibility = View.INVISIBLE

        // Wait until the layout is done loading
        textTitle.postDelayed({
            // Animate title text
            animateTextTitle(textTitle)

            // Delay button animation to start after title animation
            btnViewGallery.postDelayed({
                // Animate button
                animateButton(btnViewGallery)
            }, 500) // Adjust delay as needed
        }, 500) // Adjust delay as needed

        btnViewGallery.setOnClickListener {
            startActivity(Intent(this, GalleryActivity::class.java))
        }

        // Add touch listener to the button for scaling animation
        btnViewGallery.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Animate button scaling when pressed
                    animateButtonScale(1.8f)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Animate button scaling back to original size when released or canceled
                    animateButtonScale(1.0f)
                }
            }
            false
        }
    }

    private fun animateTextTitle(textView: TextView) {
        textView.visibility = View.VISIBLE
        val screenWidth = resources.displayMetrics.widthPixels
        textView.translationX = -screenWidth.toFloat() // Start from the left outside of the screen

        val animator = ObjectAnimator.ofFloat(textView, "translationX", 0f).apply {
            duration = 1500 // duration of the animation in milliseconds
            interpolator = AccelerateDecelerateInterpolator()
        }
        animator.start()

        // Animate arrow with vertical movement after title animation
        animateArrowVerticalMovement()
    }

    private fun animateButton(button: Button) {
        button.visibility = View.VISIBLE
        val screenWidth = resources.displayMetrics.widthPixels
        button.translationX = screenWidth.toFloat() // Start from the right outside of the screen

        val animator = ObjectAnimator.ofFloat(button, "translationX", 0f).apply {
            duration = 1000 // duration of the animation in milliseconds
            interpolator = AccelerateDecelerateInterpolator()
        }
        animator.start()
    }

    private fun animateArrowVerticalMovement() {
        val arrowImageView = findViewById<ImageView>(R.id.arrowImageView)
        arrowImageView.visibility = View.VISIBLE

        // Define the range of the vertical movement
        val moveAmount = 30f // amount to move up and down

        val verticalAnimator = ObjectAnimator.ofFloat(arrowImageView, "translationY", -moveAmount, moveAmount).apply {
            duration = 700 // duration of the animation for one cycle up and down
            repeatCount = ObjectAnimator.INFINITE // continuously repeat
            repeatMode = ObjectAnimator.REVERSE
        }

        // Start the vertical movement animation
        verticalAnimator.start()
    }

    private fun animateButtonScale(scaleFactor: Float) {
        // Scale button using ObjectAnimator
        val scaleAnimatorX = ObjectAnimator.ofFloat(btnViewGallery, "scaleX", scaleFactor)
        val scaleAnimatorY = ObjectAnimator.ofFloat(btnViewGallery, "scaleY", scaleFactor)

        // Set animation duration and interpolator
        val duration = 100L // Duration in milliseconds
        scaleAnimatorX.duration = duration
        scaleAnimatorY.duration = duration
        scaleAnimatorX.interpolator = AccelerateDecelerateInterpolator()
        scaleAnimatorY.interpolator = AccelerateDecelerateInterpolator()

        // Create animator set
        val animatorSet = AnimatorSet()
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY)

        // Start the animation
        animatorSet.start()
    }
}
