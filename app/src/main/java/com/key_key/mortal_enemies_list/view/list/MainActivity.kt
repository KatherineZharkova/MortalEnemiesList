package com.key_key.mortal_enemies_list.view.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.key_key.mortal_enemies_list.databinding.ActivityMainBinding
import com.key_key.mortal_enemies_list.view.note.NoteActivity

class MainActivity : AppCompatActivity() {
    private val bind: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: ListViewModel by lazy { ViewModelProvider(this).get(ListViewModel::class.java) }
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        setSupportActionBar(bind.toolbar)
        setObserver()
        initView()
    }

    private fun setObserver() {
        viewModel.listLiveData().observe(this) { listAdapter.notes = it.notesList }
    }

    private fun initView() {
        bind.fab.setOnClickListener { NoteActivity.start(this) }
        listAdapter = ListAdapter() { NoteActivity.start(this, it) }
        bind.recyclerView.adapter = listAdapter
    }



}
