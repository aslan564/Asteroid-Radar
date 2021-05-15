package com.udacity.asteroidradar.ui.fragments.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private val binding by lazy { FragmentDetailBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUI()
    }

    private fun bindUI(): Unit = with(binding) {
        lifecycleOwner = this@DetailFragment

        arguments?.let { bundle->
          val dataAsteroid=  DetailFragmentArgs.fromBundle(bundle).selectedAsteroid
            dataAsteroid?.let {
                asteroid=dataAsteroid
            }
        }

        helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
