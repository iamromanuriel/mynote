package com.roman.mynote.ui.notedetail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.roman.mynote.R
import com.roman.mynote.databinding.FragmentDetailNoteBinding
import com.roman.mynote.utils.ToolbarModel
import com.roman.mynote.utils.set
import com.romanuriel.core.Task
import com.romanuriel.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailFragment : Fragment(R.layout.fragment_detail_note) {
    private val viewModel : NoteDetailViewModel by viewModels()
    private val binding: FragmentDetailNoteBinding by viewBinding()
    private val arg : NoteDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setToolbar()
            setData()
            initGraph()
        }
        observerTask()
    }

    private fun FragmentDetailNoteBinding.setData() = this.apply {
        includeResultMainNote.set(
            CardNoteMainModel(
                "Reunion 18 Abril con Dapesa",
                R.drawable.calendar_with_a_clock_time_tools_icon_icons_com_56831
            )
        )
    }


    private fun FragmentDetailNoteBinding.setToolbar() = this.layoutToolbar.apply {
        set(
            ToolbarModel(
                title = R.string.title_detail,
                action = findNavController()::navigateUp,
                endIcon = R.drawable.baseline_more_vert_24
            )
        )
    }

    private fun observerTask(){
        viewModel.task.observe(this.viewLifecycleOwner){task ->
            when(task){
                is Task.Error ->{toast(task.exception.localizedMessage)}
                is Task.Success ->{}
            }
        }
    }

    private fun FragmentDetailNoteBinding.initGraph() = this.apply {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(10f, "Label 1"))
        entries.add(PieEntry(20f, "Label 2"))
        entries.add(PieEntry(15f, "Label 3"))
        entries.add(PieEntry(30f, "Label 4"))
        entries.add(PieEntry(25f, "Label 5"))

        val dataSet = PieDataSet(entries, "Ejemplo de datos")
        dataSet.colors = listOf(
            Color.parseColor("#FF5733"),
            Color.parseColor("#33FF57"),
            Color.parseColor("#5733FF"),
            Color.parseColor("#FF5733"),
            Color.parseColor("#33FF57")
        ) // Colores personalizados

        val data = PieData(dataSet)

        // Configura el gráfico circular (PieChart)
        val pieChart: PieChart = this.graphic
        pieChart.data = data
        pieChart.description.isEnabled = false // Deshabilitar la descripción predeterminada
        pieChart.centerText = "Gráfico Circular" // Texto en el centro del gráfico
        pieChart.animateY(1000)
    }

}