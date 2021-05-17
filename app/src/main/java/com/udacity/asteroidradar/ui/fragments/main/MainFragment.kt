package com.udacity.asteroidradar.ui.fragments.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.model.entity.Asteroid
import com.udacity.asteroidradar.ui.fragments.main.adapter.AsteroidsAdapter
import com.udacity.asteroidradar.util.SharedPreferenceManager
import java.util.*

class MainFragment : Fragment() {

    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }

    private lateinit var viewModel: MainViewModel
    private lateinit var factoty: MainViewModelFactory


    private val adapterAsteroid by lazy {
        AsteroidsAdapter { asteroid: Asteroid ->

            val action = MainFragmentDirections.actionShowDetail(asteroid)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root
        factoty = MainViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this@MainFragment, factoty).get(MainViewModel::class.java)



        return view
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        bindUI()
        observeDataAsteroid()

    }

    override fun onStart() {
        super.onStart()

        viewModel.refreshFirstData(SharedPreferenceManager.isLogin)

    }

    private fun bindUI(): Unit = with(binding) {
        lifecycleOwner = this@MainFragment
        viewModel = this@MainFragment.viewModel
        asteroidRecycler.adapter = adapterAsteroid

    }

    private fun observeDataAsteroid(): Unit = with(viewModel) {
        asteroidLIst.observe(viewLifecycleOwner, {
            it?.let { list ->
                adapterAsteroid.submitList(list)
                if (list.isNotEmpty()) {
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
                getViewModelData(viewModel.asteroidLIst)
            }
            R.id.show_saved_asteroid_menu -> {
                getViewModelData(viewModel.asteroidOrderByDayLIst)
            }
            R.id.show_today_asteroid_menu -> {
                getViewModelData(viewModel.asteroidLimitedLIst)
            }
        }
        return true
    }

    private fun getViewModelData(list: LiveData<List<Asteroid>>) {
        list.observe(viewLifecycleOwner,{
            adapterAsteroid.submitList(it)
        })
    }

    companion object {
        private const val TAG = "MainFragments"
    }
}
