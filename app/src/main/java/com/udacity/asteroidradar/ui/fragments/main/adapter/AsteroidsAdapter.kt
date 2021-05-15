package com.udacity.asteroidradar.ui.fragments.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.udacity.asteroidradar.model.entity.Asteroid
import com.udacity.asteroidradar.model.entity.AsteroidDiffUtil


class AsteroidsAdapter(val onClickAsteroidAdapter:(Asteroid)->Unit) : ListAdapter<Asteroid, AsteroidViewHolder>(AsteroidDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
       return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(getItem(position),onClickAsteroidAdapter)
    }
}