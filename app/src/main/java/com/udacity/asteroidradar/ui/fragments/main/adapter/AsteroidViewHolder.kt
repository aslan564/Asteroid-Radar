package com.udacity.asteroidradar.ui.fragments.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.LayoutItemAsteroidBinding
import com.udacity.asteroidradar.model.entity.Asteroid

class AsteroidViewHolder private constructor(private val binding: LayoutItemAsteroidBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        asteroidDb: Asteroid,
        onClickAsteroid: (Asteroid) -> Unit
    ):Unit= with(binding) {
        asteroid=asteroidDb
        root.setOnClickListener{
            onClickAsteroid(asteroidDb)
        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent:ViewGroup):AsteroidViewHolder{
            val inflater = LayoutInflater.from(parent.context)
            val view = LayoutItemAsteroidBinding.inflate(inflater, parent, false)
            return AsteroidViewHolder(view)
        }
    }
}