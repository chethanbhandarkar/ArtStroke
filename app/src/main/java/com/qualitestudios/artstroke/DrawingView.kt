package com.qualitestudios.artstroke

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
//creating a view
class DrawingView(context: Context, attrs: AttributeSet) : View(context,attrs){
    //methods variable  this view is for drawing
    //cstompath is class we create
    //when someone creates a drawing view we initialize all
    private var mDrawPath:CustomPath?=null
    //all variable reqired to build
    private var mCanvasBitmap: Bitmap?=null
    private var mDrawPaint: Paint?=null
    private var mCanvasPaint:Paint?=null
    private var mBrushSize:Float=0.toFloat()
    private var color= Color.BLACK

    //canvastodraw
    private var canvas:Canvas?=null

    //we have null variables we will set now
    init{
        setUpDrawing()
    }
    private fun setUpDrawing(){
      mDrawPaint=Paint()
        mDrawPath=CustomPath(color,mBrushSize)
        mDrawPaint!!.color=color
        mDrawPaint!!.style=Paint.Style.STROKE
        mDrawPaint!!.strokeJoin=Paint.Join.ROUND
        mDrawPaint!!.strokeCap=Paint.Cap.ROUND




    }





    //only be usable within drawing view nd use variable
    //custompath inherits from path
    internal inner class CustomPath(var color:Int,
                                    var brushThickness:Float): Path(){

    }


}