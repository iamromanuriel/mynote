package com.roman.mynote.ui

import android.content.DialogInterface
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.provider.Settings.System.getString
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.roman.mynote.R
import com.roman.mynote.data.model.Note
import com.roman.mynote.databinding.FragmentNoteBinding
import com.roman.mynote.utils.UtilsFunctionsNote
import java.lang.Exception


class NoteFragment : Fragment() {
    private var _binding : FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel :NoteViewModel
    private lateinit var navController: NavController
    private var utils = UtilsFunctionsNote()

    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.setTheme(R.style.Theme_MyNote)
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(this)
            .get(NoteViewModel::class.java)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_note,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.save ->{
                val title = binding.editTitle.text.toString()
                val note = binding.editNote.text.toString()

                saveNew(title, note)
                true
            }
            R.id.delete ->{
                showAlertDialog()
                //Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    fun saveNew(title: String, note: String){
        val newnote = Note(
            title,
            note,
            utils.getDate(),
            utils.getnumcolor())
        try{
            viewmodel.insert(newnote)
            navController.navigate(R.id.action_noteFragment_to_homeFragment)
        }catch (e: Exception){
            println(e.message)
        }
    }

    fun showAlertDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle("Alerta")
            .setMessage("¿Esta seguro?")
            .setPositiveButton(android.R.string.ok,
            DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(context, "Se ha hecho correctamente", Toast.LENGTH_SHORT).show()
            })
            .setNegativeButton(android.R.string.cancel,
            DialogInterface.OnClickListener { dialogInterface, i ->  })
            .show()
    }
}