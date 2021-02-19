package com.cemaltaskiran.trader.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cemaltaskiran.trader.data.entities.Stock

abstract class BaseStockViewHolder(
    itemBinding: ViewBinding,
    private val listener: StockItemListener
) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

    lateinit var stock: Stock

    abstract fun bind(item: Stock)

    override fun onClick(v: View?) {
        listener.onClickedStock(stock, adapterPosition)
    }
}