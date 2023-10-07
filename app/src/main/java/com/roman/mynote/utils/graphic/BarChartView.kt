package com.roman.mynote.utils.graphic

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.BarChart

class BarChartView(context: Context, attrs: AttributeSet): BarChart(context, attrs) {
    init {
        setDrawBarShadow(false)
        setDrawValueAboveBar(true)
        description.isEnabled = false
        setMaxVisibleValueCount(60)
        setPinchZoom(false)
        setDrawGridBackground(false)
    }
}