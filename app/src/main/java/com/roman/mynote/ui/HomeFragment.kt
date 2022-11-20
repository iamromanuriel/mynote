package com.roman.mynote.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.roman.mynote.R
import com.roman.mynote.adapter.NoteAdapter
import com.roman.mynote.data.model.Note
import com.roman.mynote.databinding.BottomSheetDialogBinding
import com.roman.mynote.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? =null
    private val binding get() = _binding!!
    private lateinit var viewmodel : NoteViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(this)
            .get(NoteViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        with(binding){
            viewmodel.allNote.observe(viewLifecycleOwner, Observer { result ->
                val manager = GridLayoutManager(context,2)
                val adapter = NoteAdapter(result)
                recyclerNote.hasFixedSize()
                recyclerNote.layoutManager = manager
                recyclerNote.adapter = adapter

                adapter.onItemClick = {noteItem ->
                    val dialog = BottomSheetDialog(requireContext())
                    val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
                    val bindingBottomSheetDialog = BottomSheetDialogBinding.bind(view)


                    bindingBottomSheetDialog.noteTitle.text = noteItem.title
                    bindingBottomSheetDialog.noteDate.text = noteItem.date

                    bindingBottomSheetDialog.btnClose.setOnClickListener { dialog.dismiss() }


                    bindingBottomSheetDialog.btnDelete.setOnClickListener {
                        showAlertDialog(noteItem)
                        dialog.dismiss()
                    }
                    bindingBottomSheetDialog.idBtnDismiss.setOnClickListener {
                        //navController.navigate(R.id.action_homeFragment_to_noteFragment)
                        var action =
                        dialog.dismiss()
                    }

                    dialog.setCancelable(true)
                    dialog.setCanceledOnTouchOutside(true)
                    dialog.setContentView(bindingBottomSheetDialog.root)
                    dialog.show()
                }
            })
        }
        binding.btnAdd.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_noteFragment)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun showAlertDialog(note: Note){
        AlertDialog.Builder(requireContext())
            .setTitle("Advertencia")
            .setMessage("¿Estas seguro de eliminar la nota?")
            .setPositiveButton(android.R.string.ok,
            DialogInterface.OnClickListener { dialogInterface, i ->
                viewmodel.delete(note)
            })
            .setNegativeButton(android.R.string.cancel,
            DialogInterface.OnClickListener { dialogInterface, i ->  })
            .show()
    }
}