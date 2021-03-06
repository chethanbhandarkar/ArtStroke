package com.qualitestudios.artstroke

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
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
     var color= Color.BLACK
    private val mPaths=ArrayList<CustomPath>()
    private val uPaths=ArrayList<CustomPath>()

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
        mCanvasPaint=Paint(Paint.DITHER_FLAG)
        mBrushSize=20.toFloat()




    }

    fun onClickUndo(){
        if(mPaths.size>0)
        {
            uPaths.add(mPaths.removeAt(mPaths.size-1))
            invalidate()
        }
    }

    fun onClickRedo()
    {
        if(uPaths.size>0)
        {
            mPaths.add(uPaths.removeAt(uPaths.size-1))
            invalidate()
        }

    }
// called wen size of our screen chnges
    //we are creating canvas ech time size changed
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //set canvas
        mCanvasBitmap= Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        canvas=Canvas(mCanvasBitmap!!)

    }
//change canvas to canvas? if fails
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!,0f,0f,mCanvasPaint)


    for(path in mPaths)
    {
        mDrawPaint!!.strokeWidth=path.brushThickness
        mDrawPaint!!.color=path.color
        canvas.drawPath(path, mDrawPaint!!)
    }


    if(!mDrawPath!!.isEmpty) {
mDrawPaint!!.strokeWidth=mDrawPath!!.brushThickness
        mDrawPaint!!.color=mDrawPaint!!.color

        canvas.drawPath(mDrawPath!!, mDrawPaint!!)
    }
    }
//motion event
    override fun onTouchEvent(event: MotionEvent?): Boolean {
      val touchX=event?.x
        val touchY=event?.y
        when(event?.action)
        {
            MotionEvent.ACTION_DOWN->{
                mDrawPath!!.color=color
                mDrawPath!!.brushThickness=mBrushSize
                mDrawPath!!.reset()
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.moveTo(touchX,touchY)
                    }
                }
            }
            MotionEvent.ACTION_MOVE->{
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.lineTo(touchX,touchY)
                    }
                }


            }
            MotionEvent.ACTION_UP->{
                mPaths.add(mDrawPath!!)
                mDrawPath=CustomPath(color,mBrushSize)

            }
            else->return false

        }

invalidate()

        return true
    }

    fun setSizeForBrush(newSize:Float)
    {
        mBrushSize=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,newSize,resources.displayMetrics)

        mDrawPaint!!.strokeWidth=mBrushSize


    }
    fun setColorBrush(color:Int)
    {
        mDrawPaint!!.color=color
    }

    //only be usable within drawing view nd use variable
    //custompath inherits from path
    internal inner class CustomPath(var color:Int,
                                    var brushThickness:Float): Path(){

    }


}