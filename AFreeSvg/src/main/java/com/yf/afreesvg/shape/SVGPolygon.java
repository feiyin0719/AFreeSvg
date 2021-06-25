package com.yf.afreesvg.shape;

import android.graphics.PointF;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;

public class SVGPolygon extends SVGBaseShape {

    protected PointF[] points;

    public SVGPolygon(PointF[] points) {
        this.points = points;
    }

    public PointF[] getPoints() {
        return points;
    }

    @Override
    public Object clone() {
        return new SVGPolygon(points);
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("polygon");
        element.setAttribute("points", getPointsStr(points, convert));
        addBaseAttr(element);
        return element;
    }

    protected String getPointsStr(PointF points[], DoubleFunction<String> convert) {
        StringBuilder sb = new StringBuilder();
        if (points.length > 0) {
            for (int i = 0; i < points.length; ++i)
                sb.append(" " + convert.apply(points[i].x) + "," + convert.apply(points[i].y));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGPolygon polygon = (SVGPolygon) o;
        return Arrays.equals(points, polygon.points);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(points);
    }
}
