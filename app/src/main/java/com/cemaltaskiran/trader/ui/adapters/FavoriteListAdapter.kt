package com.cemaltaskiran.trader.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cemaltaskiran.trader.R
import com.cemaltaskiran.trader.data.entities.Stock
import com.cemaltaskiran.trader.databinding.FavoriteListItemBinding

class FavoriteListAdapter(private val listener: StockItemListener) :
    RecyclerView.Adapter<BaseStockViewHolder>() {

    private val items = ArrayList<Stock>()
    private val positionMap = HashMap<String, Int>()

    fun setItems(items: List<Stock>) {
        this.items.clear()
        this.positionMap.clear()
        for (i in items.indices) {
            val item = items[i]
            this.items.add(item)
            positionMap[item.name] = i
        }
        notifyDataSetChanged()
    }

    fun toggleFav(position: Int) {
        if (position < itemCount && position > -1) {
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        val binding: FavoriteListItemBinding =
            FavoriteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteListViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseStockViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class FavoriteListViewHolder(
        private val itemBinding: FavoriteListItemBinding,
        listener: StockItemListener
    ) : BaseStockViewHolder(itemBinding, listener) {

        init {
            itemBinding.root.setOnClickListener(this)
        }

        override fun bind(item: Stock) {
            this.stock = item
            itemBinding.tvStockName.text = item.name
            itemBinding.tvStockPrice.text = item.price?.toString()
            itemBinding.imgFav.setImageResource(R.drawable.ic_star_black_24dp)
        }

    }

}