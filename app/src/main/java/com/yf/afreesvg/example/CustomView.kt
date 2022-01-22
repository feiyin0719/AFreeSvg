package com.yf.afreesvg.example

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CustomView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.RED
        val rect = Rect(100, 100, 200, 200)
        canvas?.let { canvas ->
            canvas.drawRect(rect, paint)
//            canvas.translate(150f, 150f)
//            canvas.drawRect(rect, paint)
            canvas.saveLayer(RectF(250f,250f,350f,350f),paint)
            canvas.drawRect(Rect(250,250,350,350),paint)
//            canvas.restore()
            paint.color = Color.BLUE
//            canvas.drawRect(rect,paint)

            canvas.saveLayer(null,paint)
            canvas.drawRect(Rect(60,60,90,90),paint)
            canvas.restore()

            canvas.restore()


        }
    }
}