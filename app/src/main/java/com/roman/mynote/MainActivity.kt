package com.roman.mynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.roman.mynote.adapter.NoteAdapter
import com.roman.mynote.databinding.ActivityMainBinding
import com.roman.mynote.databinding.ActivityNoteBinding
import com.roman.mynote.ui.NoteActivity
import com.roman.mynote.ui.NoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel = ViewModelProvider(this,ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application))
            .get(NoteViewModel::class.java)
        /*
        val recycler = binding.recyclerNote

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this,NoteActivity::class.java))
        }

        viewmodel.allNote.observe(this, Observer { result->
            println(result)
            val manager = GridLayoutManager(this, 2)
            val adapter = NoteAdapter(result)
            recycler.hasFixedSize()
            recycler.layoutManager = manager
            recycler.adapter = adapter
        })
        */
    }
}