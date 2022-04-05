package com.lomboktengahkab.wisataapp.ui.destination

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.lomboktengahkab.wisataapp.R
import com.lomboktengahkab.wisataapp.data.Destination
import com.lomboktengahkab.wisataapp.databinding.ActivityDestinationListBinding
import com.lomboktengahkab.wisataapp.network.DestinationRepository
import com.lomboktengahkab.wisataapp.ui.adapter.DestinationAdapter
import com.lomboktengahkab.wisataapp.ui.detail.DetailDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DestinationListActivity : AppCompatActivity() {


    private var destinationItems: MutableList<Destination> = mutableListOf()

    private var destinationAdapter: DestinationAdapter? = null

    private val repository: DestinationRepository by lazy {
        DestinationRepository()
    }

    private lateinit var binding: ActivityDestinationListBinding

    companion object {
        const val EXTRA_CATEGORY_ID = "extra_category_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent.getStringExtra(EXTRA_CATEGORY_ID)

        intent?.let {
            initDataDestination(it)

            binding.swipeRefresh.setOnRefreshListener {
                initDataDestination(it)
            }

        }
    }

    private fun initDataDestination(id : String) {
        binding.swipeRefresh.isRefreshing = true

        CoroutineScope(Dispatchers.Main).launch {
            repository.destinationListByCategory(id)
                .catch { exception ->
                    binding.swipeRefresh.isRefreshing = false
                    Snackbar.make(
                        binding.root,
                        exception.localizedMessage ?: "",
                        Snackbar.LENGTH_LONG
                    ).show()
                    noDestinationAvailable(true)
                    binding.noDataLayout.txtEmptyData.text =
                        getString(R.string.txt_network_error)
                }
                .collect {
                    if (it.success) {
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
                                Intent(this@DestinationListActivity, DetailDestination::class.java).apply {
                                    putExtra(DetailDestination.EXTRA_DESTINATION_PARCELABLE, destination)
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