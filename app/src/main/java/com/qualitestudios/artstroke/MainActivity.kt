package com.qualitestudios.artstroke

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        drawing_view.setSizeForBrush(20.toFloat())


        window.setFlags(

            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        ib_brush.setOnClickListener()
        {
            if(isReadStorageAllowed())
            {
                //run or code to get image from allery
            }
            else{
                requestStoragePermission()
            }
           // showBrushSizeChooserDialog()

        }
        brushSizeChange.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                drawing_view.setSizeForBrush((progress).toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext, "Size change", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext, "Size changed", Toast.LENGTH_SHORT).show()
            }


        })


    }



    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).toString()
            )
        )
        {


            Toast.makeText(this, "need Permission ", Toast.LENGTH_SHORT).show()


        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), STORAGE_PERMISSION_CODE)

}
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission accepted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun isReadStorageAllowed():Boolean{

        val result=ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        return result==PackageManager.PERMISSION_GRANTED
    }
    companion object {
        private const val STORAGE_PERMISSION_CODE = 1
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
