package com.key_key.mortal_enemies_list.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.key_key.mortal_enemies_list.R
import com.key_key.mortal_enemies_list.databinding.ItemNoteBinding
import com.key_key.mortal_enemies_list.model.Note


class ListAdapter(val onItemViewClick: (Note) -> Unit) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notes[position])


    inner class ViewHolder(private val itemBinding: ItemNoteBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(note: Note) = with(itemView) {
            itemBinding.title.text = note.title
            itemBinding.body.text = note.body
            itemBinding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    when (note.color) {
                        Note.Color.WHITE -> R.color.white
                        Note.Color.YELLOW -> R.color.yellow
                        Note.Color.GREEN -> R.color.green
                        Note.Color.BLUE -> R.color.blue
                        Note.Color.RED -> R.color.red
                        Note.Color.VIOLET -> R.color.violet
                        Note.Color.PINK -> R.color.pink
                    }
                )
            )

            setOnClickListener { onItemViewClick(note) }
        }
    }

}