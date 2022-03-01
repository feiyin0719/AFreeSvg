package com.yf.afreesvg.shape;

import androidx.annotation.NonNull;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The circle shape
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGCircle extends SVGBaseShape {
    /**
     * centerX
     */
    private float cx;
    /**
     * centerY
     */
    private float cy;
    /**
     * radius
     */
    private float r;

    public SVGCircle(float cx, float cy, float r) {
        this.cx = cx;
        this.cy = cy;
        this.r = r;
    }

    /**
     * The centerX
     *
     * @return
     * @since 0.0.2
     */
    public float getCx() {
        return cx;
    }

    /**
     * The centerY
     *
     * @return
     * @since 0.0.2
     */
    public float getCy() {
        return cy;
    }

    /**
     * Radius
     *
     * @return
     * @since 0.0.2
     */
    public float getR() {
        return r;
    }


    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("circle");
        element.setAttribute("cx", convert.apply(cx));
        element.setAttribute("cy", convert.apply(cy));
        element.setAttribute("r", convert.apply(r));
        addBaseAttr(element);
        return element;
    }

    @NonNull
    @Override
    public Object clone() {
        return new SVGCircle(cx, cy, r);
    }
}
