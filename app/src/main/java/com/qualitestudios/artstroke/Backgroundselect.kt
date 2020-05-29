package com.qualitestudios.artstroke

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_backgroundselect.*

class Backgroundselect : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backgroundselect)
        btn_change_image.setOnClickListener()
        {



            if(isReadStorageAllowed())
            {
val changeIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(changeIntent, GALLERY)



                //run or code to get image from allery
            }
            else{
                requestStoragePermission()
            }







        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK)
        {
            if(requestCode==GALLERY)
            {

                try{


                    if(data!!.data!=null)
                    {
id_image.visibility= View.VISIBLE
                        id_image.setImageURI(data.data)
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
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission accepted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun isReadStorageAllowed():Boolean{

        val result= ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        return result== PackageManager.PERMISSION_GRANTED
    }
    companion object {
        private const val STORAGE_PERMISSION_CODE = 1
        private const val GALLERY=2
    }
















}
