package com.roman.mynote.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.roman.mynote.R
import com.roman.mynote.databinding.LayoutSimpleInputBinding

class DialogInput(
    context: Context,
    private val title: String,
    private val hint: String? = "",
    private val ok: (String) -> Unit
): Dialog(context), View.OnClickListener{
    private lateinit var binding: LayoutSimpleInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(this.context),
            R.layout.layout_simple_input,
            null,
            false
        )
        setContentView(binding.root)
        window.apply {
            this?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            this?.setBackgroundDrawableResource(R.color.color_dialog_found)
            this?.setGravity(Gravity.CENTER)
        }
        binding.title.text = title
        binding.input.hint = hint
        binding.btnCancel.visibility = View.GONE
        binding.btnOk.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnOk.id ->{
                val text = binding.input.editText?.text.toString()
                if(text.isNotEmpty()){
                    ok(text)
                    dismiss()
                }else{
                    binding.input.error = "Vacio"
                }
            }
        }
    }
}