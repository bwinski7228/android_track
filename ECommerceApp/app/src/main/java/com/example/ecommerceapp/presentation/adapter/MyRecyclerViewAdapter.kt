package com.example.ecommerceapp.presentation.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.data.model.ShopItem
import com.example.ecommerceapp.databinding.ListItemBinding
import kotlinx.android.synthetic.main.list_item.view.*
import java.io.InputStream
import java.net.URL

class MyRecyclerViewAdapter(private val clickListener:(ShopItem)->Unit): RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<ShopItem>() {
        override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val shopItem = differ.currentList[position]
        holder.bind(shopItem, clickListener)
    }

    inner class MyViewHolder(val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(shopItem: ShopItem, clickListener:(ShopItem)->Unit) {
            binding.nameTextView.text = shopItem.title
            binding.textViewPrice.text = "$" + shopItem.price.toString()
            //binding.imageViewProjectIcon.setImageResource(ShopItem.image)

            Glide.with(binding.imageViewProjectIcon.context)
                .load(shopItem.image)
                .into(binding.imageViewProjectIcon)

            /*binding.buttonAddToCart.setOnClickListener {
                onItemClickListener?.let {
                    it(shopItem)
                }
            }*/
            //binding.imageViewProjectIcon.src = LoadImageFromWebOperations(shopItem.image)
            
            binding.buttonAddToCart.setOnClickListener{
                clickListener(shopItem)
            }
            /*if (ShopItem.promo) {
                binding.textViewPrice.setTextColor(Color.parseColor("#F44336"))
            }*/
        }

        fun LoadImageFromWebOperations(url: kotlin.String?): Drawable? {
            return try {
                val `is`: InputStream = URL(url).getContent() as InputStream
                Drawable.createFromStream(`is`, "src name")
            } catch (e: java.lang.Exception) {
                null
            }
        }
    }

    private var onItemClickListener :((ShopItem)->Unit)?=null

    fun setOnItemClickListener(listener : (ShopItem)->Unit){
        onItemClickListener = listener
    }

}
