package com.key_key.mortal_enemies_list.view.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.key_key.mortal_enemies_list.R
import com.key_key.mortal_enemies_list.databinding.ActivityNoteBinding
import com.key_key.mortal_enemies_list.model.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {
    private val bind: ActivityNoteBinding by lazy { ActivityNoteBinding.inflate(layoutInflater) }

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MMM, HH:mm:ss"
        private const val SAVE_DELAY = 2000L

        fun start(context: Context, note: Note? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            context.startActivity(intent)
        }
    }

    private var note: Note? = null
    private lateinit var noteViewModel: NoteViewModel
    private val editTextListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) { saveNote() }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        note = intent.getParcelableExtra(EXTRA_NOTE)
        initActionBar()
        initView()
    }

    private fun initActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = note?.let {
            "saved: " + SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(it.lastChanged)
        } ?: getString(R.string.app_name)
    }

    private fun initView() {
        note?.let { note ->
            bind.editTextTitle.setText(note.title)
            bind.editTextBody.setText(note.body)
            val color = when (note.color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
                Note.Color.PINK -> R.color.pink
            }
            bind.toolbar.setBackgroundColor(ContextCompat.getColor(this, color))
        }
        bind.editTextTitle.addTextChangedListener(editTextListener)
        bind.editTextBody.addTextChangedListener(editTextListener)
    }

    fun saveNote() {
        val title = bind.editTextTitle.text.toString()
        val body = bind.editTextBody.text.toString()
        if (title.length < 3) return

        Handler().postDelayed({
            note = note?.copy(title = title, body = body, lastChanged = Date()) ?: Note(id = UUID.randomUUID().toString(), title = title, body = body)
            note?.let {
                noteViewModel.save(it)
                initActionBar()
            }
        }, SAVE_DELAY)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> { onBackPressed(); true }
        else -> super.onOptionsItemSelected(item)
    }

}
