package com.yf.afreesvg.shape;

import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SVGShapeGroup implements SVGShape {
    private List<SVGShape> list;

    public SVGShapeGroup() {
        list = new ArrayList<>();
    }

    public SVGShapeGroup(SVGShapeGroup shapeGroup) {
        list = new ArrayList<>();
        for (SVGShape shape : shapeGroup.list) {
            list.add((SVGShape) shape.clone());
        }
    }

    public void addShape(SVGShape shape) {
        list.add(shape);
    }


    @Override
    public Element convertToSVGElement(Document document, DoubleFunction<String> convert) {
        Element g = document.createElement("g");
        Iterator<SVGShape> iterator = list.iterator();
        while (iterator.hasNext()) {
            SVGShape shape = iterator.next();
            g.appendChild(shape.convertToSVGElement(document, convert));
        }
        return g;
    }

    @Override
    public Object clone() {
        return new SVGShapeGroup(this);
    }
}
