package com.lomboktengahkab.wisataapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.lomboktengahkab.wisataapp.R
import com.lomboktengahkab.wisataapp.data.Category
import com.lomboktengahkab.wisataapp.data.Destination
import com.lomboktengahkab.wisataapp.databinding.ActivityMainBinding
import com.lomboktengahkab.wisataapp.network.CategoryRepository
import com.lomboktengahkab.wisataapp.network.DestinationRepository
import com.lomboktengahkab.wisataapp.ui.adapter.CategoryAdapter
import com.lomboktengahkab.wisataapp.ui.adapter.DestinationAdapter
import com.lomboktengahkab.wisataapp.ui.destination.DestinationListActivity
import com.lomboktengahkab.wisataapp.ui.destination.DestinationListActivity.Companion.EXTRA_CATEGORY_ID
import com.lomboktengahkab.wisataapp.ui.detail.DetailDestination
import com.lomboktengahkab.wisataapp.ui.detail.DetailDestination.Companion.EXTRA_DESTINATION_PARCELABLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var categoryItems: MutableList<Category> = mutableListOf()
    private var destinationItems: MutableList<Destination> = mutableListOf()

    private var categoryAdapter: CategoryAdapter? = null
    private var destinationAdapter: DestinationAdapter? = null

    private val repository: DestinationRepository by lazy {
        DestinationRepository()
    }

    private val categoryRepository: CategoryRepository by lazy {
        CategoryRepository()
    }


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        CoroutineScope(Dispatchers.Main).launch {
            initAllData()
        }


        binding.swipeRefresh.setOnRefreshListener {
            CoroutineScope(Dispatchers.Main).launch {
                initAllData()
            }

        }


    }

    private suspend fun initAllData() {
        initDataCategory()
        initDataDestination()
    }

    private suspend fun initDataDestination() {
        binding.swipeRefresh.isRefreshing = true
        binding.txtLabelMenu.isVisible = false
        binding.txtLabelDestination.isVisible = false

        repository.destinationList
            .catch { exception ->
                binding.swipeRefresh.isRefreshing = false
                Snackbar.make(
                    binding.root,
                    exception.localizedMessage ?: "",
                    Snackbar.LENGTH_LONG
                ).show()
                noDestinationAvailable(true)
                binding.noDataLayout.txtEmptyData.text = getString(R.string.txt_network_error)
            }
            .collect {
                if (it.success) {
                    binding.txtLabelMenu.isVisible = true
                    binding.txtLabelDestination.isVisible = true
                    binding.swipeRefresh.isRefreshing = false
                    destinationItems.clear()
                    destinationItems.addAll(it.data)
                    if (destinationItems.isNullOrEmpty()) {
                        noDestinationAvailable(true)
                        binding.noDataLayout.txtEmptyData.text =
                            getString(R.string.data_not_found)
                    } else {
                        noDestinationAvailable(false)
                        destinationAdapter = DestinationAdapter(destinationItems)

                        destinationAdapter?.onItemClickListener = { destination ->

                            Intent(this@MainActivity, DetailDestination::class.java).apply {
                                putExtra(EXTRA_DESTINATION_PARCELABLE, destination)
                                startActivity(this)
                            }
                        }
                        binding.rvDestination.adapter = destinationAdapter
                    }

                } else {
                    noDestinationAvailable(true)
                    binding.noDataLayout.txtEmptyData.text =
                        getString(R.string.txt_network_error)
                    binding.swipeRefresh.isRefreshing = false
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                }
            }


    }

    private suspend fun initDataCategory() {
        binding.swipeRefresh.isRefreshing = true
        categoryRepository.category
            .catch { exception ->
                binding.swipeRefresh.isRefreshing = false
                Snackbar.make(
                    binding.root,
                    exception.localizedMessage ?: "",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            .collect {
                if (it.success) {
                    binding.swipeRefresh.isRefreshing = false
                    categoryItems.clear()
                    categoryItems.addAll(it.data)
                    if (categoryItems.isNullOrEmpty()) {
                        Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                    } else {
                        categoryAdapter = CategoryAdapter(categoryItems)

                        categoryAdapter?.onItemClickListener = { category ->
                            Intent(this@MainActivity, DestinationListActivity::class.java).apply {
                                putExtra(EXTRA_CATEGORY_ID, category.id.toString())
                                startActivity(this)
                            }
                        }
                        binding.rvCategory.adapter = categoryAdapter
                    }

                } else {
                    binding.swipeRefresh.isRefreshing = false
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                }
            }


    }

    private fun noDestinationAvailable(flag: Boolean) {
        if (flag) {
            binding.rvDestination.visibility = View.GONE
            binding.noDataLayout.root.visibility = View.VISIBLE
        } else {
            binding.rvDestination.visibility = View.VISIBLE
            binding.noDataLayout.root.visibility = View.GONE
        }
    }

}