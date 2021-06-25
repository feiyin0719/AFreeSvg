package com.yf.afreesvg.shape;

import com.yf.afreesvg.ConvertToSVGElement;
import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.SVGModes;
import com.yf.afreesvg.SVGPaint;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SVGClipShape implements ConvertToSVGElement, Cloneable {
    private SVGShape shape;
    private @SVGModes.POS_MODE
    String posMode = SVGModes.MODE_USERSPACE;

    public SVGClipShape(SVGShape shape, @SVGModes.POS_MODE String posMode) {
        this.shape = shape;
        this.posMode = posMode;
    }

    public SVGClipShape(SVGClipShape clipShape) {
        this.shape = (SVGShape) clipShape.shape.clone();
        this.posMode = clipShape.posMode;
    }

    public SVGShape getShape() {
        return shape;
    }

    public void setShape(SVGShape shape) {
        this.shape = shape;
    }

    public @SVGModes.POS_MODE
    String getPosMode() {
        return posMode;
    }

    public void setPosMode(@SVGModes.POS_MODE String posMode) {
        this.posMode = posMode;
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("clipPath");
        element.setAttribute("clipPathUnits", posMode);
        if (shape instanceof SVGShapeGroup) {
            Element g = shape.convertToSVGElement(canvas, document, convert);
            NodeList list = g.getChildNodes();
            if (list != null && list.getLength() > 0) {
                for (int i = 0; i < list.getLength(); ++i) {
                    Node n = list.item(i);
                    if (n instanceof Element) {
                        Element element1 = (Element) n;
                        element.appendChild(element1);
                    }
                }
            }
        } else {
            Element element1 = shape.convertToSVGElement(canvas, document, convert);
            element.appendChild(element1);
        }
        return element;
    }

    public Object clone() {
        return new SVGClipShape(this);
    }
}
