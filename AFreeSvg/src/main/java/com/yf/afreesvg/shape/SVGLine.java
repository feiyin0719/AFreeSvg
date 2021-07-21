package com.yf.afreesvg.shape;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

/**
 * The line shape
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGLine extends SVGBaseShape {
    /**
     * StartX
     */
    private float x1;
    /**
     * StartY
     */
    private float y1;
    /**
     * EndX
     */
    private float x2;
    /**
     * EndY
     */
    private float y2;

    public SVGLine(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Get startX
     *
     * @return
     */
    public float getX1() {
        return x1;
    }

    /**
     * Get startY
     *
     * @return
     */
    public float getY1() {
        return y1;
    }

    /**
     * Get endX
     *
     * @return
     */
    public float getX2() {
        return x2;
    }

    /**
     * Get endY
     *
     * @return
     */
    public float getY2() {
        return y2;
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("line");
        element.setAttribute("x1", convert.apply(x1));
        element.setAttribute("y1", convert.apply(y1));
        element.setAttribute("x2", convert.apply(x2));
        element.setAttribute("y2", convert.apply(y2));
        addBaseAttr(element);
        return element;
    }

    @Override
    public Object clone() {
        return new SVGLine(x1, y1, x2, y2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SVGLine svgLine = (SVGLine) o;
        return Float.compare(svgLine.x1, x1) == 0 &&
                Float.compare(svgLine.y1, y1) == 0 &&
                Float.compare(svgLine.x2, x2) == 0 &&
                Float.compare(svgLine.y2, y2) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x1, y1, x2, y2);
    }
}
