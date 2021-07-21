package com.yf.afreesvg.shape;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

/**
 * Rect shape
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGRect extends SVGBaseShape {
    /**
     * left x pos
     */
    private float x;
    /**
     * top y pos
     */
    private float y;
    /**
     * width
     */
    private float width;
    /**
     * height
     */
    private float height;

    public SVGRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Left x pos
     *
     * @return
     * @since 0.0.1
     */
    public float getX() {
        return x;
    }

    /**
     * Top y pos
     *
     * @return
     * @since 0.0.1
     */
    public float getY() {
        return y;
    }

    /**
     * Get width
     *
     * @return
     * @since 0.0.1
     */
    public float getWidth() {
        return width;
    }

    /**
     * Get height
     *
     * @return
     * @since 0.0.1
     */
    public float getHeight() {
        return height;
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("rect");
        element.setAttribute("x", convert.apply(x));
        element.setAttribute("y", convert.apply(y));
        element.setAttribute("width", convert.apply(width));
        element.setAttribute("height", convert.apply(height));
        addBaseAttr(element);
        return element;
    }

    @Override
    public Object clone() {
        return new SVGRect(x, y, width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGRect rect = (SVGRect) o;
        return Float.compare(rect.x, x) == 0 &&
                Float.compare(rect.y, y) == 0 &&
                Float.compare(rect.width, width) == 0 &&
                Float.compare(rect.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }
}
