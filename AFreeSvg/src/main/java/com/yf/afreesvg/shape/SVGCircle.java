package com.yf.afreesvg.shape;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SVGCircle extends SVGBaseShape {
    private float cx;
    private float cy;
    private float r;

    public SVGCircle(float cx, float cy, float r) {
        this.cx = cx;
        this.cy = cy;
        this.r = r;
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

    @Override
    public Object clone() {
        return new SVGCircle(cx, cy, r);
    }
}
