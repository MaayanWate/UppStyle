package com.example.uppstyle
import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {

    private var cart: HashMap<String, FashionItem> = hashMapOf()
    private var paymentBT: Button? = null
    private var adapter: CheckoutFashionItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout_activity)

        cart = intent.getSerializableExtra("cart") as HashMap<String, FashionItem>
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        val items = cart.values.toList()
        adapter = CheckoutFashionItemAdapter(cart.values.toList())
        recyclerView.adapter = adapter

        paymentBT = findViewById(R.id.pay)
        var sum = 0f
        items.forEach {
            sum += (it.quantity * it.price.toFloat())
        }
        val local = Locale.getDefault()
        if(local.language == "iw"){
            paymentBT?.text = "התקדם לתשלום: " + "$sum $"
        }

        else{
            paymentBT?.text = "Proceed to payment: $sum $"
        }


        paymentBT?.setOnClickListener {
            PaymentDialogFragment.createInstance(sum).show(supportFragmentManager, "")
        }

        findViewById<ImageView>(R.id.back).setOnClickListener {
            animateBackButtonAndFinish()
        }
    }

    private fun animateBackButtonAndFinish() {
        val backButton = findViewById<ImageView>(R.id.back)

        // Create ObjectAnimator for translationX
        val translateAnimator = ObjectAnimator.ofFloat(backButton, "translationX", 0f, 100f).apply {
            duration = 250 // Set duration
        }

        // Start the animation
        translateAnimator.start()

        // Set a delay before finishing the activity to allow the animation to complete
        backButton.postDelayed({ finish() }, translateAnimator.duration)
    }
}
