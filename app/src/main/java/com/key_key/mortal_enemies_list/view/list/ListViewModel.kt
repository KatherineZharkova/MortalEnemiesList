package com.key_key.mortal_enemies_list.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.key_key.mortal_enemies_list.model.ListViewState
import com.key_key.mortal_enemies_list.model.NotesList

class ListViewModel : ViewModel() {
    private val listLiveData = MutableLiveData<ListViewState>()
    fun listLiveData() = listLiveData as LiveData<ListViewState>

    init {
        NotesList.notesLiveData().observeForever {
            listLiveData.value = listLiveData.value?.copy(notesList = it) ?: ListViewState(it)
        }
    }

}