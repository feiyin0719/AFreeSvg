package com.yf.afreesvg.shape;

import com.yf.afreesvg.ConvertToSVGElement;
import com.yf.afreesvg.SVGModes;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SVGClipShape implements ConvertToSVGElement, Cloneable {
    private SVGShape shape;
    private @SVGModes.POS_MODE
    String posMode;

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
    public Element convertToSVGElement(Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("clipPath");
        element.setAttribute("clipPathUnits", posMode);
        if (shape instanceof SVGShapeGroup) {
            Element g = shape.convertToSVGElement(document, convert);
            NodeList list = g.getChildNodes();
            if (list != null && list.getLength() > 0) {
                for (int i = 0; i < list.getLength(); ++i) {
                    Node n = list.item(i);
                    element.appendChild(n);
                }
            }
        } else {
            element.appendChild(shape.convertToSVGElement(document, convert));
        }
        return element;
    }

    public Object clone() {
        return new SVGClipShape(this);
    }
}
