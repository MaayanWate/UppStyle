package com.example.uppstyle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import android.widget.ImageView
import java.util.Locale

class CheckoutFashionItemAdapter(
    private val items: List<FashionItem>, // List of FashionItem objects
) : RecyclerView.Adapter<CheckoutFashionItemAdapter.ViewHolder>() {

    // Create view holder when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate item layout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_check_out_fashion, parent, false)
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
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Views in the item layout
        private val titleTextView: TextView = itemView.findViewById(R.id.textTitle)
        private val brandTextView: TextView = itemView.findViewById(R.id.brand)
        private val tagTextView: TextView = itemView.findViewById(R.id.tag)
        private val priceTextView: TextView = itemView.findViewById(R.id.price)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val size: TextView = itemView.findViewById(R.id.size)
        private val quantity: TextView = itemView.findViewById(R.id.quantity)

        // Bind data to views
        fun bind(item: FashionItem) {
            val local = Locale.getDefault()
            titleTextView.text = item.title // Set title
            brandTextView.text = item.brand // Set brand
            tagTextView.text = item.tags.joinToString(", ") // Set tags
            priceTextView.text = item.price.toString() + "$" // Set price
            imageView.setImageResource(item.imageResId) // Set image
            if(local.language == "iw"){
                size.text = "מידה: " + item.size // Set size in Hebrew
                quantity.text = "כמות: "+ item.quantity.toString() // Set quantity in Hebrew
            }
            else{
                size.text = "Size: " + item.size // Set size in English
                quantity.text = "Quantity: "+ item.quantity.toString() // Set quantity in English
            }
        }
    }
}