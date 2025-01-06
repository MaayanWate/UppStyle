package com.example.uppstyle

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.view.animation.AccelerateDecelerateInterpolator
import java.util.Locale
import android.os.Handler


class GalleryActivity : AppCompatActivity(), FashionItemAdapter.ItemClickListener {

    private val cart: HashMap<String, FashionItem> = hashMapOf()
    private lateinit var counterText: TextView
    private lateinit var backToStartImageView: ImageView

    val local = Locale.getDefault()

    var adapter: FashionItemAdapter? = null

    private val checkoutLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                cart.clear()
                counterText.text = "0"
                adapter?.notifyDataSetChanged()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_activity)
        counterText = findViewById(R.id.counter)
        backToStartImageView = findViewById(R.id.backToStart)

        backToStartImageView.setOnClickListener {
            animateBackToStart(backToStartImageView)
            showConfirmationDialog()
        }

        val cartView = findViewById<FrameLayout>(R.id.cart)
        cartView.setOnClickListener { v -> openCheckOut() }

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create sample fashion items
        val fashionItems = getSampleFashionItems()

        // Set up adapter
        adapter = FashionItemAdapter(fashionItems, this)
        recyclerView.adapter = adapter
    }

    private fun showConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        if(local.language == "iw"){
            alertDialogBuilder.setTitle("אזהרה")
            alertDialogBuilder.setMessage("אם תחזור למסך הבית, ההזמנה תמחק את המוצרים מהסל. האם אתה רוצה להמשיך בכל זאת?")
            alertDialogBuilder.setPositiveButton("כן") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                goToMainActivity()
            }
            alertDialogBuilder.setNegativeButton("לא") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
        }
        else {
            alertDialogBuilder.setTitle("Warning")
            alertDialogBuilder.setMessage("If you return to the home screen, the invitation will delete the selections. Do you want to proceed anyway?")
            alertDialogBuilder.setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                goToMainActivity()
            }
            alertDialogBuilder.setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openCheckOut() {
        val intent = Intent(this, CheckoutActivity::class.java)
        intent.putExtra("cart", cart)
        checkoutLauncher.launch(intent)
    }

    // Sample fashion items
    private fun getSampleFashionItems(): List<FashionItem> {
        if(local.language == "iw"){
            return listOf(
                FashionItem("חולצה אופנתית", "מותג A", 29.99f, "S", R.drawable.shirt_1, listOf("תגים:", "חולצה", "מותג A")),
                FashionItem("מכנסיים מסוגננות", "מותג B", 39.99f, "S", R.drawable.pans_1, listOf("תגים:", "מכנסיים", "מותג B")),
                FashionItem("שמלה אלגנטית", "מותג C", 49.99f, "S", R.drawable.dress_1, listOf("תגים:", "שמלה", "מותג C")),
                FashionItem("חצאית אופנתית", "מותג D", 34.99f, "S", R.drawable.skirt_1, listOf("תגים:", "חצאית", "מותג D")),
                FashionItem("נעליים קלאסיות", "מותג E", 59.99f, "S", R.drawable.shoes_1, listOf("תגים:", "נעליים", "מותג E")),
                FashionItem("שמלת ג׳ינס שיקית", "מותג F", 45.99f, "S", R.drawable.dress_2, listOf("תגים:", "שמלה", "ג׳ינס", "מותג F")),
                FashionItem("שמלת קיץ", "מותג G", 39.99f, "S", R.drawable.dress_3, listOf("תגים:", "שמלה", "קיץ", "מותג G")),
                FashionItem("נעלי עקב שמנת", "מותג H", 69.99f, "S", R.drawable.shoes_2, listOf("תגים:", "נעליים", "עקבים", "מותג H")),
                FashionItem("חצאית ירוק זית", "מותג I", 32.99f, "S", R.drawable.skirt_2, listOf("תגים:", "חצאית", "ירוק", "מותג I")),
                FashionItem("חולצה כתומה נוחה", "מותג J", 25.99f, "S", R.drawable.shirt_2, listOf("תגים:", "חולצה", "כתום", "מותג J"))
            )
        }
        else {
            return listOf(
                FashionItem(
                    "Trendy Shirt",
                    "Brand A",
                    29.99f,
                    "S",
                    R.drawable.shirt_1,
                    listOf("Tags:", "shirt", "brand A")
                ),
                FashionItem(
                    "Stylish Pants",
                    "Brand B",
                    39.99f,
                    "S",
                    R.drawable.pans_1,
                    listOf("Tags:", "pants", "brand B")
                ),
                FashionItem(
                    "Elegant Dress",
                    "Brand C",
                    49.99f,
                    "S",
                    R.drawable.dress_1,
                    listOf("Tags:", "dress", "brand C")
                ),
                FashionItem(
                    "Fashionable Skirt",
                    "Brand D",
                    34.99f,
                    "S",
                    R.drawable.skirt_1,
                    listOf("Tags:", "skirt", "brand D")
                ),
                FashionItem(
                    "Classic Shoes",
                    "Brand E",
                    59.99f,
                    "S",
                    R.drawable.shoes_1,
                    listOf("Tags:", "shoes", "brand E")
                ),
                FashionItem(
                    "Chic Jeans Dress",
                    "Brand F",
                    45.99f,
                    "S",
                    R.drawable.dress_2,
                    listOf("Tags:", "dress", "jeans", "brand F")
                ),
                FashionItem(
                    "Breezy Summer Dress",
                    "Brand G",
                    39.99f,
                    "S",
                    R.drawable.dress_3,
                    listOf("Tags:", "dress", "summer", "brand G")
                ),
                FashionItem(
                    "Cream High Heels",
                    "Brand H",
                    69.99f,
                    "S",
                    R.drawable.shoes_2,
                    listOf("Tags:", "shoes", "heels", "brand H")
                ),
                FashionItem(
                    "Olive Green Skirt",
                    "Brand I",
                    32.99f,
                    "S",
                    R.drawable.skirt_2,
                    listOf("Tags:", "skirt", "green", "brand I")
                ),
                FashionItem(
                    "Comfortable Orange Shirt",
                    "Brand J",
                    25.99f,
                    "S",
                    R.drawable.shirt_2,
                    listOf("Tags:", "shirt", "orange", "brand J")
                )
            )
        }
    }

    // Handle item click
    override fun onItemClick(item: FashionItem) {
        if(local.language == "iw"){
            Toast.makeText(this, " לחצת על: ${item.title}", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "Clicked on ${item.title}", Toast.LENGTH_SHORT).show()
        }
        // You can perform any additional actions here, such as adding the item to the cart
        cart[item.title] = item
        counterText.text = cart.values.sumOf { it.quantity }.toString ()
        item.isOnCart = true
        adapter?.notifyDataSetChanged()
    }

    // Handle item long press
    override fun onItemLongPress(item: FashionItem) {
        val context = this
        if(local.language == "iw") {
            Toast.makeText(context, " בוצעה לחיצה ארוכה על ${item.title}", Toast.LENGTH_SHORT)
                .show()
            // You can perform any additional actions here, such as showing a confirmation dialog to remove the item from the cart
            AlertDialog.Builder(this)
                .setTitle("הסר מהסל")
                .setMessage(" בטוח שתרצה להסיר את המוצר ${item.title} מסל הקניות שלך? ")
                .setPositiveButton(
                    "כן"
                ) { dialog, which ->
                    if (cart.containsKey(item.title)) {
                        cart.remove(item.title)
                        item.isOnCart = false
                        adapter?.notifyDataSetChanged()
                        counterText.text = cart.values.sumOf { it.quantity }.toString()
                    } else {
                        Toast.makeText(
                            context,
                            "המוצר הזה לא נמצא בסל הקניות שלך",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton(
                    "לא"
                ) { dialog, which -> dialog?.dismiss() }.show()
        }
        else{

            Toast.makeText(context, "Long pressed on ${item.title}", Toast.LENGTH_SHORT)
                .show()
            // You can perform any additional actions here, such as showing a confirmation dialog to remove the item from the cart
            AlertDialog.Builder(this)
                .setTitle("Remove from Cart")
                .setMessage("Are you sure you want to remove ${item.title} from the cart?")
                .setPositiveButton(
                    "Yes"
                ) { dialog, which ->
                    if (cart.containsKey(item.title)) {
                        cart.remove(item.title)
                        item.isOnCart = false
                        adapter?.notifyDataSetChanged()
                        counterText.text = cart.values.sumOf { it.quantity }.toString()
                    } else {
                        Toast.makeText(
                            context,
                            "This item doesn't exist in the cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton(
                    "No"
                ) { dialog, which -> dialog?.dismiss() }.show()

        }

    }


    private fun animateBackToStart(imageView: ImageView) {
        val animator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 150f).apply {
            duration = 500 // Animation duration in milliseconds
            interpolator = AccelerateDecelerateInterpolator() // Animation interpolator
        }
        animator.start()

        // Delay the animation reversal by 2 seconds
        Handler().postDelayed({
            val reverseAnimator = ObjectAnimator.ofFloat(imageView, "translationX", 150f, 0f).apply {
                duration = 500 // Animation duration in milliseconds
                interpolator = AccelerateDecelerateInterpolator() // Animation interpolator
            }
            reverseAnimator.start()
        }, 2000) // 2000 milliseconds = 2 seconds
    }
}