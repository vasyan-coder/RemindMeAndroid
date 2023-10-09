package com.vasyancoder.remindme.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vasyancoder.remindme.R
import com.vasyancoder.remindme.databinding.FragmentGreetingBinding

class ViewPagerAdapter(
    private val context: Context
) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    class PagerViewHolder(val binding: FragmentGreetingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = FragmentGreetingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PagerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return PAGE_SUM
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        var displayText = ""
        var titleText = ""
        when (position) {
            0 -> {
                displayText = context.getString(R.string.greeting_display_1)
                titleText = ""
            }

            1 -> {
                displayText = context.getString(R.string.greeting_display_2)
                titleText = context.getString(R.string.greeting_title_2)
            }

            2 -> {
                displayText = context.getString(R.string.greeting_display_3)
                titleText = context.getString(R.string.greeting_title_3)
            }
        }

        with(holder.binding) {
            displayGreetingText.text = displayText
            titleGreetingText.text = titleText
        }
    }

    companion object {
        private const val PAGE_SUM = 3
    }
}