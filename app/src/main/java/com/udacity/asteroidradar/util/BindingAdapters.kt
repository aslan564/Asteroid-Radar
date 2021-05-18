package com.udacity.asteroidradar.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.apply {
            setImageResource(R.drawable.ic_status_potentially_hazardous)
            contentDescription = imageView.context.getString(
                R.string.potentially_hazardous_asteroid_image
            )
        }
    } else {
        imageView.apply{
            setImageResource(R.drawable.ic_status_normal)
            contentDescription = imageView.context.getString(
                R.string.potentially_hazardous_asteroid_image
            )
        }
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {

    if (isHazardous) {
        imageView.apply {
            setImageResource(R.drawable.asteroid_hazardous)
            contentDescription = imageView.context.getString(
                R.string.potentially_hazardous_asteroid_image
            )
        }
    } else {
        imageView.apply {
            setImageResource(R.drawable.asteroid_safe)
            contentDescription = imageView.context.getString(
                R.string.not_hazardous_asteroid_image
            )
        }
    }
}

@BindingAdapter("asteroidProgressBarStatus")
fun bindAsteroidVisibility(progressBar: ProgressBar,isVisiblePB:Boolean){
    if (isVisiblePB) {
        progressBar.visibility=View.GONE
    }else{
        progressBar.visibility=View.VISIBLE
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("downloadImagePicasso")
fun downloadImageWithPicasso(imageView: ImageView, url: String?) {
    url?.let {
        imageView.contentDescription=imageView.context.getString(R.string.asteroid_image)
        Picasso.with(imageView.context).load(it).error(R.drawable.ic_broken).placeholder(R.drawable.ic_broken).into(imageView)
    }
}
