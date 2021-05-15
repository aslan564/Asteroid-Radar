package com.udacity.asteroidradar.ui.fragments.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.model.entity.Asteroid
import com.udacity.asteroidradar.ui.fragments.main.adapter.AsteroidsAdapter
import com.udacity.asteroidradar.util.Sorted
import com.udacity.asteroidradar.util.statusVisibility
import java.util.*

class MainFragment : Fragment() {

    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }
    private val adapterAsteroid by lazy {
        AsteroidsAdapter { asteroid: Asteroid ->
            val action = MainFragmentDirections.actionShowDetail(asteroid)
            findNavController().navigate(action)
        }
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        bindUI()

    }

    override fun onStart() {
        super.onStart()
        observeDataAsteroid()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindUI(): Unit = with(binding) {
        lifecycleOwner = this@MainFragment
        viewModel = this@MainFragment.viewModel
        asteroidRecycler.adapter = adapterAsteroid


    }

    private fun observeDataAsteroid(): Unit = with(viewModel) {
        getViewModelData(Sorted.ALL)
        asteroidLIst.observe(viewLifecycleOwner,{
            it?.let{list->
                adapterAsteroid.submitList(list)
            }
        })

        uiState.observe(viewLifecycleOwner, {
            it?.let { status ->
                if (statusVisibility(status)) {
                    binding.statusLoadingWheel.visibility = View.GONE
                } else {
                    binding.statusLoadingWheel.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.show_all_asteroid_menu -> {
                getViewModelData(Sorted.DATE)
            }
            R.id.show_saved_asteroid_menu -> {
                getViewModelData(Sorted.ALL)
            }
            R.id.show_today_asteroid_menu -> {
                getViewModelData(Sorted.TODAY)
            }
        }
        return true
    }

    private fun getViewModelData(sorted:Sorted) {
       viewModel.sortedAsteroidList(sorted)
    }
}
