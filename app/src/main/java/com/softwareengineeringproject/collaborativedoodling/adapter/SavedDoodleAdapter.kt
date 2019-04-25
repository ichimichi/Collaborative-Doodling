package com.softwareengineeringproject.collaborativedoodling.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.softwareengineeringproject.collaborativedoodling.R
import kotlinx.android.synthetic.main.saved_doodle.view.*

class SavedDoodleAdapter : RecyclerView.Adapter<CustomViewHolder>() {

    val doodleTitles = listOf("doodle 1", "doodle 2", "doodle 3")

    override fun getItemCount(): Int {
        return doodleTitles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.saved_doodle, parent, false)

        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val doodleTitle = doodleTitles.get(position)
        holder.view.doodleTitleTV.text = doodleTitle
    }
}

class CustomViewHolder(val view: View): ViewHolder(view) {

}