package com.example.chatbrainy.view.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.chatbrainy.R
import com.google.android.material.snackbar.Snackbar
import java.util.*


class ChatConfigFragment : Fragment() {

    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat_config, container, false)
        sharedPreferences = activity?.applicationContext?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val builder = AlertDialog.Builder(context)


        //Button Back /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        view.findViewById<ImageButton>(R.id.btnBack).setOnClickListener{
            activity?.supportFragmentManager?.popBackStack()
        }

        //Spinner Model Select ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        val spinnerModel : Spinner = view.findViewById(R.id.spinner)
        val tvTextModel : TextView = view.findViewById(R.id.tvTextModel)
        val titlemodels : Array<String> = resources.getStringArray(R.array.model_list)
        val textmodels : Array<String> = resources.getStringArray(R.array.text_model_list)
        val arrayAdapter : ArrayAdapter<String> = ArrayAdapter(activity?.applicationContext!!, R.layout.text_spinner, titlemodels)
        spinnerModel.adapter = arrayAdapter

        for ((indice, item) in titlemodels.withIndex()) {
            if (item == sharedPreferences!!.getString("model", "text-davinci-003")) {
                spinnerModel.setSelection(indice)
            }
        }

        spinnerModel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tvTextModel.setText(textmodels.get(position))
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }


        //Seekbar Temperature ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        val seekbarTemp : SeekBar = view.findViewById(R.id.seekBarTemp)
        val tvTempValue : TextView = view.findViewById(R.id.tvTempValue)

        var tempPref = sharedPreferences!!.getFloat("temperature", 0.7F)
        seekbarTemp.setProgress((tempPref*100).toInt())
        tvTempValue.setText("${tempPref}")

        seekbarTemp.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvTempValue.setText("${p1.toDouble()/100}")
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })


        //Seekbar Maximum Length ////////////////////////////////////////////////////////////////////////////////////////////////////////
        val seekbarLength : SeekBar = view.findViewById(R.id.seekBarLength)
        val tvLengthValue : TextView = view.findViewById(R.id.tvLengthValue)
        seekbarLength.max = 1000

        var length = sharedPreferences!!.getInt("max_tokens", 250)
        seekbarLength.setProgress(length)
        tvLengthValue.setText("${length}")

        seekbarLength.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvLengthValue.setText("${p1}")
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        //Btn SAVE ////////////////////////////////////////////////////////////////////////////////////////////////////////
        val btnSave : Button = view.findViewById(R.id.btnSave)
        btnSave.setOnClickListener{
            val editor = sharedPreferences!!.edit()
            editor.putString("model", spinnerModel.selectedItem.toString())
            editor.putInt("max_tokens", tvLengthValue.text.toString().toInt())
            editor.putFloat("temperature", tvTempValue.text.toString().toFloat())
            editor.apply()
            activity?.supportFragmentManager?.popBackStack()
            Toast.makeText(activity?.applicationContext, "Preferências salvas", Toast.LENGTH_SHORT).show()
        }

        //Btn Reset ////////////////////////////////////////////////////////////////////////////////////////////////////////
        val btnReset : Button = view.findViewById(R.id.btnReset)
        btnReset.setOnClickListener{
            val editor = sharedPreferences!!.edit()
            editor.putString("model", "text-davinci-003")
            editor.putInt("max_tokens", 250)
            editor.putFloat("temperature", 0.7F)
            editor.apply()
            activity?.supportFragmentManager?.popBackStack()
            Toast.makeText(activity?.applicationContext, "Padrões restaurados", Toast.LENGTH_SHORT).show()
        }

        //Help text Temperature ////////////////////////////////////////////////////////////////////////////////////////////////////////
        val tvTemperature : TextView = view.findViewById(R.id.tvTemperature)
        tvTemperature.setOnClickListener{
            builder.setMessage(resources.getString(R.string.help_temperature))
            builder.setPositiveButton("Ok"){dialog, which -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        //Help text Length ////////////////////////////////////////////////////////////////////////////////////////////////////////
        val tvLength : TextView = view.findViewById(R.id.tvLength)
        tvLength.setOnClickListener{
            builder.setMessage(resources.getString(R.string.help_length))
            builder.setPositiveButton("Ok"){dialog, which -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }


        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatConfigFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

}