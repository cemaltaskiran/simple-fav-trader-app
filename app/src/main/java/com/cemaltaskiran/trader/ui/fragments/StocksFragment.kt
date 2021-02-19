package com.cemaltaskiran.trader.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cemaltaskiran.trader.data.entities.Stock
import com.cemaltaskiran.trader.databinding.FragmentStocksBinding
import com.cemaltaskiran.trader.ui.adapters.StockItemListener
import com.cemaltaskiran.trader.ui.adapters.StockListAdapter
import com.cemaltaskiran.trader.utils.Resource
import com.cemaltaskiran.trader.viewmodels.StocksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StocksFragment : BaseFragment(), StockItemListener {

    private val viewModel: StocksViewModel by viewModels()
    private lateinit var binding: FragmentStocksBinding
    private lateinit var adapter: StockListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStocksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
    }

    private fun setupRecyclerView() {
        adapter = StockListAdapter(this)
        binding.rvStocks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStocks.adapter = adapter
    }

    private fun bindForMessageMod(messageResourceName: String?) {
        bindMessage(binding.rvStocks, binding.tvText, messageResourceName)
    }

    private fun setupObserver() {
        viewModel.getAllStocks().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    bindForMessageMod("loading")
                }
                Resource.Status.SUCCESS -> {
                    resource.data.let {
                        if (!it.isNullOrEmpty()) {
                            adapter.setItems(it)
                            binding.rvStocks.visibility = View.VISIBLE
                            binding.tvText.visibility = View.GONE
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    bindForMessageMod("error")
                }
            }
        }

        viewModel.getFavStocks().observe(viewLifecycleOwner) {
            it.let { it1 -> adapter.setFavorites(it1) }
        }
    }

    override fun onClickedStock(stock: Stock, position: Int) {
        if (!stock.isFav) {
            viewModel.insertFavStock(stock)
        } else {
            viewModel.removeFavStock(stock)
        }
        adapter.toggleFav(position)
    }
}