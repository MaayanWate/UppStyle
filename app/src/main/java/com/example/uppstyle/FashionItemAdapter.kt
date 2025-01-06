package com.example.uppstyle

import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import android.widget.ImageView
import android.widget.Toast
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.Locale

class FashionItemAdapter(
    private val items: List<FashionItem>, // List of FashionItem objects
    private val clickListener: ItemClickListener // Click listener interface
) : RecyclerView.Adapter<FashionItemAdapter.ViewHolder>() {

    // Create view holder when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate item layout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fashion, parent, false)
        return ViewHolder(view)
    }

    // Bind data to view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    // Return item count
    override fun getItemCount(): Int {
        return items.size
    }

    // View holder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnLongClickListener {
        // Views in the item layout
        private val titleTextView: TextView = itemView.findViewById(R.id.textTitle)
        private val brandTextView: TextView = itemView.findViewById(R.id.brand)
        private val priceTextView: TextView = itemView.findViewById(R.id.price)
        private val tagTextView: TextView = itemView.findViewById(R.id.tag)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val sizeSpinner: Spinner = itemView.findViewById(R.id.size)
        private val quantitySpinner: Spinner = itemView.findViewById(R.id.quantity)
        private val addToCartBT = itemView.findViewById<Button>(R.id.cart_bt)
        val size = arrayListOf("S", "M", "L", "XL", "XXL") // Sizes array
        val quantities = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9) // Quantities array

        init {
            // Initialize spinners and button
            addToCartBT.setOnClickListener(this)
            val adapter = ArrayAdapter<String>(
                itemView.context,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                size
            )
            adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
            sizeSpinner.adapter = adapter
            sizeSpinner.onItemSelectedListener = this
            val adapter2 = ArrayAdapter<Int>(
                itemView.context,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                quantities
            )
            adapter2.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
            quantitySpinner.adapter = adapter2
            quantitySpinner.onItemSelectedListener = this
        }

        // Handle spinner item selection
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (parent?.id == R.id.size) {
                items[adapterPosition].size = size[position] // Set selected size
            } else {
                items[adapterPosition].quantity = quantities[position] // Set selected quantity
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        // Bind data to views
        fun bind(item: FashionItem) {
            val local = Locale.getDefault()
            titleTextView.text = item.title // Set title
            brandTextView.text = item.brand // Set brand
            tagTextView.text = item.tags.joinToString(", ") // Set tags
            priceTextView.text = "$" + item.price // Set price
            imageView.setImageResource(item.imageResId) // Set image
            if(local.language == "iw"){
                addToCartBT.text = if (item.isOnCart) "שנה כמות" else "הוסף לעגלה" // Set button text in Hebrew
            }
            else{
                addToCartBT.text = if (item.isOnCart) "Change Quantity" else "Add to cart" // Set button text in English
            }
            quantitySpinner.setSelection(item.quantity - 1) // Set selected quantity in spinner
            var position = 0;
            when (item.size) {
                "S" -> position = 0;
                "M" -> position = 1;
                "L" -> position = 2;
                "XL" -> position = 3;
                "XXL" -> position = 4;
            }
            sizeSpinner.setSelection(position) // Set selected size in spinner

            // Apply background and toast message based on item selection
            if (item.isOnCart) {
                itemView.setBackgroundResource(R.drawable.selected_item_frame) // Apply background
                // Display toast message
                val local = Locale.getDefault()
                if(local.language == "iw"){
                    Toast.makeText(itemView.context, "נוסף לסל", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(itemView.context, "Added", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Clear background
                itemView.background = null
            }
            itemView.setOnClickListener(this) // Handle item click
            itemView.setOnLongClickListener(this) // Handle item long press
        }

        // Handle button click event
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION ) {
                clickListener.onItemClick(items[position]) // Handle item click event

                // Apply scale animation to the imageView
                val scaleAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.scale_up)
                imageView.startAnimation(scaleAnimation)

                // Delay the execution to give time for the animation to complete
                imageView.postDelayed({
                    // Reverse the scale animation to return the image to its original size
                    val reverseScaleAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.scale_down)
                    imageView.startAnimation(reverseScaleAnimation)
                }, 1000) // Adjust the delay time as needed (in milliseconds)
            }
        }

        // Handle item long press event
        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener.onItemLongPress(items[position]) // Handle item long press event
            }
            return true
        }
    }

    // Click listener interface
    interface ItemClickListener {
        fun onItemClick(item: FashionItem) // Handle item click event
        fun onItemLongPress(item: FashionItem) // Handle item long press event
    }
}