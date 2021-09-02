package com.yf.afreesvg.ktx

import com.yf.afreesvg.gradient.SVGBaseGradient
import com.yf.afreesvg.gradient.SVGLinearGradient
import com.yf.afreesvg.gradient.SVGRadialGradient

fun SVGBaseGradient.color(color: Long, offset: Float) = addStopColor(offset, color)

fun SVGLinearGradient.startX(x: Float) {
    startPoint.x = x
}

fun SVGLinearGradient.startY(y: Float) {
    startPoint.y = y
}

fun SVGLinearGradient.endX(x: Float) {
    endPoint.x = x
}

fun SVGLinearGradient.endY(y: Float) {
    endPoint.y = y
}

fun linearGradient(init: SVGLinearGradient.() -> Unit): SVGLinearGradient {
    val svgLinearGradient = SVGLinearGradient()
    svgLinearGradient.init()
    return svgLinearGradient
}

fun radialGradient(init: SVGRadialGradient.() -> Unit): SVGRadialGradient {
    val svgRadialGradient = SVGRadialGradient()
    svgRadialGradient.init()
    return svgRadialGradient
}


