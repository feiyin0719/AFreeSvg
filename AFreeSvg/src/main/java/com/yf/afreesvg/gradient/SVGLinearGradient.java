package com.yf.afreesvg.gradient;

import android.graphics.PointF;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.SVGModes;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

/**
 * LinearGradient
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGLinearGradient extends SVGBaseGradient {

    /**
     * start point
     */
    private PointF startPoint;
    /**
     * end point
     */
    private PointF endPoint;

    public SVGLinearGradient(PointF startPoint, PointF endPoint) {
        this(startPoint, endPoint, SVGModes.MODE_BOX);
    }

    public SVGLinearGradient(PointF startPoint, PointF endPoint, @SVGModes.POS_MODE String mode) {
        super(mode);
        this.startPoint = startPoint;
        this.endPoint = endPoint;

    }

    public SVGLinearGradient() {
        this(new PointF(0, 0), new PointF(0, 0), SVGModes.MODE_BOX);
    }

    /**
     * Get start point
     *
     * @return
     * @since 0.0.1
     */
    public PointF getStartPoint() {
        return startPoint;
    }

    /**
     * set start point
     *
     * @param startPoint
     * @since 0.0.1
     */
    public void setStartPoint(PointF startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * get end point
     *
     * @return
     * @since 0.0.1
     */
    public PointF getEndPoint() {
        return endPoint;
    }

    /**
     * set end point
     *
     * @param endPoint
     * @since 0.0.1
     */
    public void setEndPoint(PointF endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGLinearGradient that = (SVGLinearGradient) o;
        return super.equals(that) &&
                Objects.equals(startPoint, that.startPoint) &&
                Objects.equals(endPoint, that.endPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startPoint, endPoint);
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("linearGradient");

        element.setAttribute("x1", convert.apply(startPoint.x));
        element.setAttribute("y1", convert.apply(startPoint.y));
        element.setAttribute("x2", convert.apply(endPoint.x));
        element.setAttribute("y2", convert.apply(endPoint.y));
        initBaseGradientAttr(element, document, convert);
        return element;
    }
}
