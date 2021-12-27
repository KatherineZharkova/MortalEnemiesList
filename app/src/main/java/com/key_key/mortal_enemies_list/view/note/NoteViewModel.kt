package com.key_key.mortal_enemies_list.view.note

import androidx.lifecycle.ViewModel
import com.key_key.mortal_enemies_list.model.Note
import com.key_key.mortal_enemies_list.model.NotesList

// NoteViewModel-to-Note interaction
class NoteViewModel : ViewModel() {
    private var pendingNote: Note? = null

    fun save(note: Note){
        pendingNote = note
        NotesList.saveNote(note)
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesList.saveNote(it)
        }
    }

}
