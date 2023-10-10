package com.vasyancoder.remindme.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.vasyancoder.remindme.data.model.Note

class NoteDiffUtil : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}