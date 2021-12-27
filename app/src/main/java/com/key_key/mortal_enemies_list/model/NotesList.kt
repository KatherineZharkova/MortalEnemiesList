package com.key_key.mortal_enemies_list.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

// data storage or entity for data store
object NotesList {
    private val notesList = mutableListOf<Note>()
    private val notesLiveData = MutableLiveData<List<Note>>()
    fun notesLiveData() = notesLiveData as LiveData<List<Note>>

    init {
        notesList.add(
            Note(
                id = UUID.randomUUID().toString(), title = "Кот", color = Note.Color.VIOLET,
                body = "Кот — самый надежный товарищ! Он никому не расскажет, как ты ешь по ночам… Он будет есть вместе с тобой."
            )
        )
        notesLiveData.value = notesList
    }

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notesList
    }

    private fun addOrReplace(note: Note) {
        for (i in notesList.indices) {
            if (notesList[i] == note) {
                notesList[i] = note
                return
            }
        }
        notesList.add(note)
    }

    override fun toString(): String = notesList.joinToString()

}