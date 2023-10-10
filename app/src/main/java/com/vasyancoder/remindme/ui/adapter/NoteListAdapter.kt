package com.vasyancoder.remindme.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vasyancoder.remindme.data.model.Note
import com.vasyancoder.remindme.databinding.ItemNoteBinding

class NoteListAdapter : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NoteDiffUtil()) {

    class NoteViewHolder(val binding: ItemNoteBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
                false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val noteItem = getItem(position)
        with(holder.binding) {
            titleText.text = noteItem.title
            contentText.text = noteItem.contentNote
        }
    }
}