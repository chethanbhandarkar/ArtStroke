package com.qualitestudios.artstroke

import android.app.Application
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
drawing_view.setSizeForBrush(20.toFloat())
        window.setFlags(

            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        ib_brush.setOnClickListener()
        {
            showBrushSizeChooserDialog()
        }
        brushSizeChange.setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            drawing_view.setSizeForBrush((progress).toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
               Toast.makeText(applicationContext,"Size change",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext,"Size changed",Toast.LENGTH_SHORT).show()
            }


        })





    }


    private fun showBrushSizeChooserDialog()
    {
        val brushDialog= Dialog(this)
    brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush Size")
val smallBtn=brushDialog.ib_small_brush
        smallBtn.setOnClickListener()
            {
                drawing_view.setSizeForBrush(10.toFloat())
                brushDialog.dismiss()
            }
        val mediumBtn=brushDialog.ib_medium_brush
        mediumBtn.setOnClickListener()
        {
            drawing_view.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }
        val largeBtn=brushDialog.ib_large_brush
        largeBtn.setOnClickListener()
        {
            drawing_view.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }

        brushDialog.show()

    }
}
