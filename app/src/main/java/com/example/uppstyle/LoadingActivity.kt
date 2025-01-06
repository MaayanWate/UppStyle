package com.example.uppstyle

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.uppstyle.R
import com.example.uppstyle.MainActivity

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val rotatingCircleImageView: ImageView = findViewById(R.id.rotatingCircleImageView)
        val storeNameTextView: TextView = findViewById(R.id.storeNameTextView)

        // Define the scale animation to gradually increase the size from 0 to 1
        val scaleAnimation = ScaleAnimation(
            0f, 1f,  // Scale from 0 to 1 in X direction
            0f, 1f,  // Scale from 0 to 1 in Y direction
            Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot X coordinate (center)
            Animation.RELATIVE_TO_SELF, 0.5f   // Pivot Y coordinate (center)
        )

        // Set the duration of the animation to 5 seconds (5000 milliseconds)
        scaleAnimation.duration = 5000

        // Set the interpolator to DecelerateInterpolator to create a smooth start and slow down towards the end of the animation
        scaleAnimation.interpolator = DecelerateInterpolator()

        // Set up animation listener to handle animation events
        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // Transition to MainActivity when the animation ends
                val intent = Intent(this@LoadingActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        // Apply the scale animation to the ImageView and TextView
        rotatingCircleImageView.startAnimation(scaleAnimation)
        storeNameTextView.startAnimation(scaleAnimation)

        // Flashing background animation
        val flashingBackground = findViewById<View>(R.id.flashingBackground)

        // Define color flash animator
        val flashAnimator = ObjectAnimator.ofObject(
            flashingBackground,
            "backgroundColor",
            ArgbEvaluator(),
            resources.getColor(R.color.flash_color1), // Start color
            resources.getColor(R.color.flash_color3) // End color
        )
        flashAnimator.duration = 3000 // Set duration of color flash animation
        flashAnimator.repeatCount = ObjectAnimator.INFINITE // Repeat color flash animation infinitely
        flashAnimator.start() // Start color flash animation
    }
}