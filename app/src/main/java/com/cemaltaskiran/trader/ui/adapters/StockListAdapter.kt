package com.cemaltaskiran.trader.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cemaltaskiran.trader.R
import com.cemaltaskiran.trader.data.entities.Stock
import com.cemaltaskiran.trader.databinding.StockListItemBinding

class StockListAdapter(private val listener: StockItemListener) :
    RecyclerView.Adapter<StockListAdapter.StockListViewHolder>() {

    private val items = ArrayList<Stock>()
    private val favorites = ArrayList<Stock>()
    private val positionMap = HashMap<String, Int>()

    fun setItems(items: List<Stock>) {
        this.items.clear()
        this.positionMap.clear()
        for (i in items.indices) {
            val item = items[i]
            this.items.add(item)
            positionMap[item.name] = i
        }
        adjustFavorites()
        notifyDataSetChanged()
    }

    fun setFavorites(items: List<Stock>) {
        this.favorites.clear()
        this.favorites.addAll(items)
        adjustFavorites()
    }

    private fun adjustFavorites() {
        if (favorites.isNullOrEmpty()) {
            return
        }
        favorites.forEach { positionMap[it.name]?.let { index -> items[index].isFav = true } }
        notifyDataSetChanged()
    }

    fun toggleFav(position: Int) {
        if (position < itemCount && position > -1) {
            items[position].isFav = !items[position].isFav
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StockListViewHolder {
        val binding: StockListItemBinding =
            StockListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: StockListViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size


    class StockListViewHolder(
        private val itemBinding: StockListItemBinding,
        listener: StockItemListener
    ) : BaseStockViewHolder(itemBinding, listener) {

        init {
            itemBinding.root.setOnClickListener(this)
        }

        override fun bind(item: Stock) {
            this.stock = item
            itemBinding.tvStockName.text = item.name
            if (item.isFav) {
                itemBinding.imgFav.setImageResource(R.drawable.ic_star_black_24dp)
            } else {
                itemBinding.imgFav.setImageResource(R.drawable.ic_star_border_black_24dp)
            }
        }

    }
}