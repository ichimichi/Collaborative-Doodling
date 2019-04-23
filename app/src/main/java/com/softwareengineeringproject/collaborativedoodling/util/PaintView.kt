package com.softwareengineeringproject.collaborativedoodling.util

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import java.util.concurrent.CancellationException

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
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)

        currentColor = DEFAULT_COLOR
        strokeWidth = BRUSH_SIZE
    }

    fun clear() {
        backgroundColor = DEFAULT_COLOR
        paths.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
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
        mPath = Path()
        var fp: FingerPath = FingerPath(currentColor!!, strokeWidth!!, mPath!!)
        paths.add(fp)

        mPath!!.reset()
        mPath!!.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        var dx: Float = Math.abs(x - mX!!)
        var dy: Float = Math.abs(y - mY!!)

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath!!.quadTo(mX!!, mY!!, (x + mX!!) / 2, (y + mY!!) / 2)
            mX = x
            mY = y
        }

    }

    private fun touchUp() {
        mPath!!.lineTo(mX!!,mY!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var x = event!!.getX()
        var y = event!!.getY()

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                touchStart(x,y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x,y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }

        }
        return true
    }
}