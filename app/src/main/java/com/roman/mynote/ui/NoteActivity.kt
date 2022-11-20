package com.roman.mynote.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.roman.mynote.R
import com.roman.mynote.data.model.Note
import com.roman.mynote.databinding.ActivityNoteBinding
import com.roman.mynote.utils.UtilsFunctionsNote
import com.google.android.material.bottomsheet.BottomSheetDialog

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private lateinit var viewmodel: NoteViewModel
    private lateinit var utils: UtilsFunctionsNote
    private var intentnote: Note?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnShowBottomSheet.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            val btnClose = view.findViewById<Button>(R.id.idBtnDismiss)

            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
        }

        //Value state intent
        try{
            intentnote = intent.extras?.get("NOTE") as Note
        }catch (e: Exception){
            intentnote = null
        }

        //Create viewmodel
        viewmodel = ViewModelProvider(this, ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application))
            .get(NoteViewModel::class.java)


        utils = UtilsFunctionsNote()
        //Content of edits
        /*
        if(intentnote != null){
            with(binding){
                editTitle.setText(intentnote!!.title)
                editNote.setText(intentnote!!.note)
            }
        }

         */
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_note, menu)
        return true
    }
    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.save ->{
                val title = binding.editTitle.text.toString()
                val note = binding.editNote.text.toString()
                if(intentnote != null){
                    val updatenote = Note(
                        title,
                        note,
                        utils.getDate(),
                        intentnote?.color!!.toInt(),
                        intentnote!!.pinned,
                        intentnote!!.id)

                    try{
                        viewmodel.update(updatenote)
                    }catch (e: Exception){
                        println(e.message)
                    }
                }else{
                    val newnote = Note(
                        title,
                        note,
                        utils.getDate(),
                        utils.getnumcolor(),false)
                    try{
                        viewmodel.insert(newnote)
                    }catch (e: Exception){
                        print(e.message)
                    }
                }
                finish()
                true
            }
            R.id.delete ->{
                try{
                    if(intentnote != null){
                        showAlertDialog(intentnote)
                    }else{
                        Toast.makeText(this, "No puedes eliminar la nota", Toast.LENGTH_SHORT).show()
                    }
                }catch (e: Exception){
                    print(e.message)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showAlertDialog(note: Note?){
        AlertDialog.Builder(this)
            .setTitle("Warning")
            .setMessage("¿Quieres eliminar esta nota?")
            .setPositiveButton(android.R.string.ok,
            DialogInterface.OnClickListener { dialogInterface, i ->
                viewmodel.delete(note!!)
                finish()})
            .setNegativeButton(android.R.string.cancel,
            DialogInterface.OnClickListener { dialogInterface, i ->  })
            .show()
    }
    */
}