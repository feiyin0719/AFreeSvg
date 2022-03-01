package com.yf.afreesvg.shape;

import android.graphics.PointF;

import androidx.annotation.NonNull;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Polyline shape
 * inherit {@link SVGPolygon}
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGPolyline extends SVGPolygon {

    public SVGPolyline(PointF[] points) {
        super(points);
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("polyline");
        element.setAttribute("points", getPointsStr(points, convert));
        addBaseAttr(element);
        return element;
    }

    @NonNull
    @Override
    public Object clone() {
        return new SVGPolyline(points);
    }
}
