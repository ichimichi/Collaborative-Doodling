package com.softwareengineeringproject.collaborativedoodling.util

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.google.firebase.database.*
import com.softwareengineeringproject.collaborativedoodling.model.Instruction
import android.graphics.Bitmap


class PaintView : View {

    companion object {
        var BRUSH_SIZE = 10.0F;
        val DEFAULT_COLOR = Color.RED
        val DEFAULT_BG_COLOR = Color.WHITE
        val TOUCH_TOLERANCE: Float = 4.0F;
    }

    private var mX: Float? = null
    private var mY: Float? = null
    private var mPath: Path? = null
    private var mPaint: Paint? = null
    private var paths: ArrayList<FingerPath> = ArrayList()
    private var currentColor: Int? = null
    private var backgroundColor: Int? = DEFAULT_BG_COLOR
    private var strokeWidth: Float? = null
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private var mBitmapPaint: Paint? = Paint(Paint.DITHER_FLAG)
    private var database: FirebaseDatabase? = null
    private var drawingInstruction: DatabaseReference? = null
    val instruction: Instruction = Instruction()


    constructor(context: Context?) : super(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.isDither = true
        mPaint!!.color = DEFAULT_COLOR
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeJoin = Paint.Join.ROUND
        mPaint!!.strokeCap = Paint.Cap.ROUND
        mPaint!!.xfermode = null
        mPaint!!.alpha = 0xff
    }

    fun init(metrics: DisplayMetrics) {
        Log.v("PaintView","init()")
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)

        currentColor = DEFAULT_COLOR
        strokeWidth = BRUSH_SIZE

        database = FirebaseDatabase.getInstance()
        drawingInstruction = database!!.getReference("drawingInstruction")

        drawingInstruction!!.addValueEventListener( object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "Failed to read value.", error.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Instruction::class.java)
                Log.d("command", value.toString())



                when(value!!.command){
                    "init"->{
                    }
                    "touchStart"->{
                        touchStart(value.x!!,value.y!!)
                        invalidate()

                    }
                    "touchMove"->{
                        touchMove(value.x!!,value.y!!)
                        invalidate()

                    }
                    "touchUp"->{
                        touchUp()
                        invalidate()
                        instruction.command = "init"
                        drawingInstruction!!.setValue(instruction)
                    }
                    "changeColor"->{
                        currentColor = value!!.color
                    }
                }
            }
        })
    }

    fun clear() {
        backgroundColor = DEFAULT_BG_COLOR
        paths.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
        Log.v("PaintView","onDraw()")

        canvas!!.save()
        mCanvas!!.drawColor(backgroundColor!!)

        for (fp: FingerPath in paths) {
            mPaint!!.color = fp.color
            mPaint!!.strokeWidth = fp.strokeWidth

            mCanvas!!.drawPath(fp.path, mPaint)


        }

        canvas.drawBitmap(mBitmap!!, 0F, 0F, mBitmapPaint)
        canvas.restore()
    }

    private fun touchStart(x: Float, y: Float) {
        Log.v("PaintView","touchStart()")

        mPath = Path()
        var fp: FingerPath = FingerPath(currentColor!!, strokeWidth!!, mPath!!)
        paths.add(fp)

        mPath!!.reset()
        mPath!!.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        Log.v("PaintView","touchMove()")

        var dx: Float = Math.abs(x - mX!!)
        var dy: Float = Math.abs(y - mY!!)



        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath!!.quadTo(mX!!, mY!!, (x + mX!!) / 2, (y + mY!!) / 2)
            mX = x
            mY = y
        }

    }

    private fun touchUp() {
        Log.v("PaintView","touchUp()")

        mPath!!.lineTo(mX!!,mY!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.getX()
        val y = event.getY()

        Log.v("PaintView","onTouchEvent()")


        instruction.x = x
        instruction.y = y


        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                instruction.command = "touchStart"
                drawingInstruction!!.setValue(instruction)

                touchStart(x,y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                instruction.command = "touchMove"
                drawingInstruction!!.setValue(instruction)

                touchMove(x,y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                instruction.command = "touchUp"
                drawingInstruction!!.setValue(instruction)

                touchUp()
                invalidate()
                instruction.command = "init"
                drawingInstruction!!.setValue(instruction)

            }

        }
        return true
    }

    fun changeColor(color : String){
        currentColor = Color.parseColor(color)
        instruction.color = currentColor
        instruction.command = "changeColor"
        drawingInstruction!!.setValue(instruction)
    }

}