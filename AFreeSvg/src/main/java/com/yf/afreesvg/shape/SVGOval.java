package com.yf.afreesvg.shape;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

public class SVGOval extends SVGBaseShape {
    private float cx;
    private float cy;
    private float rx;
    private float ry;

    public SVGOval(float cx, float cy, float rx, float ry) {
        this.cx = cx;
        this.cy = cy;
        this.rx = rx;
        this.ry = ry;
    }

    public float getCx() {
        return cx;
    }

    public float getCy() {
        return cy;
    }

    public float getRx() {
        return rx;
    }

    public float getRy() {
        return ry;
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("ellipse");
        element.setAttribute("cx", convert.apply(cx));
        element.setAttribute("cy", convert.apply(cy));
        element.setAttribute("rx", convert.apply(rx));
        element.setAttribute("ry", convert.apply(ry));
        addBaseAttr(element);
        return element;
    }

    @Override
    public Object clone() {
        return new SVGOval(cx, cy, rx, ry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGOval oval = (SVGOval) o;
        return Float.compare(oval.cx, cx) == 0 &&
                Float.compare(oval.cy, cy) == 0 &&
                Float.compare(oval.rx, rx) == 0 &&
                Float.compare(oval.ry, ry) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cx, cy, rx, ry);
    }
}
