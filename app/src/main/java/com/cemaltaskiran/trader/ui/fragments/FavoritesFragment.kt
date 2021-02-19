package com.cemaltaskiran.trader.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cemaltaskiran.trader.data.entities.Stock
import com.cemaltaskiran.trader.databinding.FragmentFavoritesBinding
import com.cemaltaskiran.trader.ui.adapters.FavoriteListAdapter
import com.cemaltaskiran.trader.ui.adapters.StockItemListener
import com.cemaltaskiran.trader.utils.Resource
import com.cemaltaskiran.trader.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class FavoritesFragment : BaseFragment(), StockItemListener {

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: FavoriteListAdapter

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
    }

    override fun onResume() {
        super.onResume()
        startIntervalJob()
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

    override fun onClickedStock(stock: Stock, position: Int) {
        viewModel.removeFavStock(stock)
        viewModel.updateFavorites().observe(viewLifecycleOwner) {
            if (it.status == Resource.Status.ERROR) {
                bindMessage("no_fav_stock")
            }
        }
        adapter.toggleFav(position)
    }

    private fun startIntervalJob() {
        uiScope.launch(Dispatchers.IO) {
            while (isActive) {
                viewModel.updatePrices()
                delay(3000)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = FavoriteListAdapter(this)
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.adapter = adapter
    }

    private fun bindMessage(messageResourceName: String?) {
        bindMessage(binding.rvFavorites, binding.tvText, messageResourceName)
    }

    private fun setupObserver() {
        viewModel.prices.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    bindMessage("loading")
                }
                Resource.Status.SUCCESS -> {
                    resource.data.let {
                        if (!it.isNullOrEmpty()) {
                            adapter.setItems(it)
                            binding.rvFavorites.visibility = View.VISIBLE
                            binding.tvText.visibility = View.GONE
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    bindMessage(resource.message)
                }
            }
        }
    }
}