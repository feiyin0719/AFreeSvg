package com.yf.afreesvg.ktx

import android.graphics.PointF
import com.yf.afreesvg.PosMode
import com.yf.afreesvg.shape.*

fun shapeGroup(init: SVGShapeGroup.() -> Unit): SVGShapeGroup {
    val group = SVGShapeGroup()
    group.init()
    return group
}

abstract class SVGShapeBuilder {
    abstract fun build(): SVGShape
}

class SVGLineBuilder : SVGShapeBuilder() {
    var startX: Float = 0f
    var startY: Float = 0f
    var endX: Float = 0f
    var endY: Float = 0f
    override fun build() = SVGLine(startX, startY, endX, endY)
}

inline fun <T : SVGShapeBuilder> SVGShapeGroup.initShape(builder: T, init: T.() -> Unit) {
    builder.init()
    addShape(builder.build())
}

inline fun SVGShapeGroup.line(init: SVGLineBuilder.() -> Unit) = initShape(SVGLineBuilder(), init)

class SVGCircleBuilder : SVGShapeBuilder() {
    var cx: Float = 0f
    var cy: Float = 0f
    var r: Float = 0f
    override fun build() = SVGCircle(cx, cy, r)
}

inline fun SVGShapeGroup.circle(init: SVGCircleBuilder.() -> Unit) =
    initShape(SVGCircleBuilder(), init)

class SVGOvalBuilder : SVGShapeBuilder() {
    var cx: Float = 0f
    var cy: Float = 0f
    var rx: Float = 0f
    var ry: Float = 0f
    override fun build() = SVGOval(cx, cy, rx, ry)
}

inline fun SVGShapeGroup.oval(init: SVGOvalBuilder.() -> Unit) = initShape(SVGOvalBuilder(), init)

class SVGRectBuilder : SVGShapeBuilder() {
    var x = 0f
    var y = 0f
    var width = 0f
    var height = 0f
    override fun build() = SVGRect(x, y, width, height)
}

inline fun SVGShapeGroup.rect(init: SVGRectBuilder.() -> Unit) = initShape(SVGRectBuilder(), init)

class SVGPolygonBuilder : SVGShapeBuilder() {
    private var points = mutableListOf<PointF>()
    fun point(x: Float, y: Float) = points.add(PointF(x, y))
    override fun build(): SVGShape = SVGPolygon(points.toTypedArray())
}

inline fun SVGShapeGroup.polygon(init: SVGPolygonBuilder.() -> Unit) =
    initShape(SVGPolygonBuilder(), init)

class SVGPolylineBuilder : SVGShapeBuilder() {
    private var points = mutableListOf<PointF>()
    fun point(x: Float, y: Float) = points.add(PointF(x, y))
    override fun build(): SVGShape = SVGPolyline(points.toTypedArray())
}

inline fun SVGShapeGroup.polyline(init: SVGPolylineBuilder.() -> Unit) =
    initShape(SVGPolylineBuilder(), init)

inline fun SVGShapeGroup.path(init: SVGPath.() -> Unit) {
    val path = SVGPath()
    path.init()
    addShape(path)
}

inline fun SVGShapeGroup.textPath(init: SVGTextPath.Builder.() -> Unit) {
    val builder = SVGTextPath.Builder()
    builder.init()
    addShape(builder.build())
}

val s = shapeGroup {

    path {
        lineTo(1f, 2f)
        moveTo(3f, 4f)
    }
}

class SVGClipShapeBuilder {
    lateinit var shape: SVGShape
    var posMode: String = PosMode.MODE_USERSPACE
    fun build() = SVGClipShape(shape, posMode)
}

fun clipShape(init: SVGClipShapeBuilder.() -> Unit): SVGClipShape {
    val builder = SVGClipShapeBuilder()
    builder.init()
    return builder.build()
}



