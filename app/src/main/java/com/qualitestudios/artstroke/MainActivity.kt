package com.qualitestudios.artstroke

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import kotlinx.android.synthetic.main.activity_backgroundselect.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//async task

      //  ExecuteAsyncTask("Background success").execute()





        drawing_view.setSizeForBrush(20.toFloat())


        window.setFlags(

            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        ib_brush.setOnClickListener()
        {
           // startActivity(Intent(this,Backgroundselect::class.java))
            if(isReadStorageAllowed())
            {
                val changeIntent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(changeIntent,GALLERY)



                //run or code to get image from allery
            }
            else {
                requestStoragePermission()
            }

              //  startActivity(Intent(this,Backgroundselect::class.java))

                //run or code to get image from allery
            }


        btn_undo.setOnClickListener()
        {
drawing_view.onClickUndo()
        }
        btn_redo.setOnClickListener()
        {



            drawing_view.onClickRedo()
        }
        ib_color.setOnClickListener()
        {
            ColorPickerDialog
                .Builder(this)        			// Pass Activity Instance
                .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
                .setDefaultColor(Color.BLACK)        	// Pass Default Color
                .setColorListener { color, colorHex ->
                    // Handle Color Selection
                    drawing_view.setColorBrush(color)
                }
                .show()
        }

        btn_save.setOnClickListener()
        {
            if(isReadStorageAllowed())
            {
                BitMapAsyncTask(getBitMapFromView(id_frame)).execute()
            }
            else{
                requestStoragePermission()
            }
        }

        brushSizeChange.setOnSeekBarChangeListener(object:OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                drawing_view.setSizeForBrush((progress).toFloat())

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //Toast.makeText(applicationContext,"",Toast.LENGTH_SHORT).show()

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
              //  Toast.makeText(applicationContext,"",Toast.LENGTH_SHORT).show()

            }


        })





        }


    private inner class ExecuteAsyncTask(val value:String):AsyncTask<Any,Void,String>()
    {

        override fun onPreExecute() {
            super.onPreExecute()
            Toast.makeText(applicationContext, "Started", Toast.LENGTH_SHORT).show()
        }

        override fun doInBackground(vararg params: Any?): String {
           for(i in 1..10000)
           {
               Log.e("se",i.toString())
           }
            return value
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(applicationContext, "Process Completed", Toast.LENGTH_SHORT).show()

        }


    }



override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(resultCode== Activity.RESULT_OK)
    {
        if(requestCode== GALLERY)
        {

            try{


                if(data!!.data!=null)
                {
                    img_backimage.visibility= View.VISIBLE
                    img_backimage.setImageURI(data.data)
                }
                else
                {Toast.makeText(this,"Error in image",Toast.LENGTH_SHORT).show()

                }
            }
            catch(e:Exception){

            }




        }

    }
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
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
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
        private const val GALLERY=2
    }





    //bitmap yay
    private fun getBitMapFromView(view:View):Bitmap{
        //get a view return bitmap yay
        val returnedBitmap= Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888)

        val canvas= Canvas(returnedBitmap)
        val bgDrawable=view.background
        if(bgDrawable!=null){
            bgDrawable.draw(canvas)
        }
        else{
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap

    }

    private inner class BitMapAsyncTask(val mBitmap:Bitmap):AsyncTask<Any,Void,String>(){


        override fun doInBackground(vararg params: Any?): String {
            var result=""
            if(mBitmap!=null){
                try{
                    val bytes=ByteArrayOutputStream()
                    mBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes)
                    val f= File("Android/data/com.qualitestudios.artstroke"+File.separator+"ArtStroke_"+System.currentTimeMillis()/1000+".png")
                    val fos=FileOutputStream(f)

fos.write(bytes.toByteArray())
                    fos.flush()
                fos.close()
                    result=f.absolutePath




                }
                catch(e:Exception)
                {
                    result=""
                    e.printStackTrace()
                }
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(applicationContext, "Saved:$result", Toast.LENGTH_SHORT).show()
            MediaScannerConnection.scanFile(this@MainActivity, arrayOf(result),null)
            {
                    _,uri->var shareIntent=Intent()
                shareIntent.action=Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_STREAM,uri)
                shareIntent.type="*/*"

                startActivity(
                    Intent.createChooser(
                        shareIntent,"Share"
                    )
                )

            }


        }

    }







}
