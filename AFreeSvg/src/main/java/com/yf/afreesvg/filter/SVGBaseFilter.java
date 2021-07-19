package com.yf.afreesvg.filter;

import com.yf.afreesvg.ConvertToSVGElement;
import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.SVGModes;
import com.yf.afreesvg.SVGUtils;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class SVGBaseFilter implements SVGFilter {

    public static final String GRAPHIC_VALUE = "SourceGraphic";
    public static final String ALPHA_VALUE = "SourceAlpha";

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected @SVGModes.POS_MODE
    String filterUnits = SVGModes.MODE_BOX;

    protected String id;

    public SVGBaseFilter(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public SVGBaseFilter() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected List<SVGBaseFilterEffect> effectList = new ArrayList<>();

    public void addEffect(SVGBaseFilterEffect effect) {
        effectList.add(effect);
    }

    protected static abstract class SVGBaseFilterEffect implements ConvertToSVGElement {
        protected String in;
        protected String result;

        protected void addBaseAttr(Element element) {
            if (!SVGUtils.isTextEmpty(in)) {
                element.setAttribute("in", in);
            }
            if (!SVGUtils.isTextEmpty(result)) {
                element.setAttribute("result", result);
            }
        }

        public void setIn(String in) {
            this.in = in;
        }

        public void setResult(String result) {
            this.result = result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SVGBaseFilterEffect effect = (SVGBaseFilterEffect) o;
            return Objects.equals(in, effect.in) &&
                    Objects.equals(result, effect.result);
        }

        @Override
        public int hashCode() {
            return Objects.hash(in, result);
        }
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("filter");
        element.setAttribute("x", convert.apply(x));
        element.setAttribute("y", convert.apply(y));
        element.setAttribute("width", convert.apply(width));
        element.setAttribute("height", convert.apply(height));
        element.setAttribute("filterUnits", filterUnits);
        for (SVGBaseFilterEffect effect : effectList)
            element.appendChild(effect.convertToSVGElement(canvas, document, convert));
        return element;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public @SVGModes.POS_MODE
    String getFilterUnits() {
        return filterUnits;
    }

    public void setFilterUnits(@SVGModes.POS_MODE String filterUnits) {
        this.filterUnits = filterUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGBaseFilter that = (SVGBaseFilter) o;
        return Float.compare(that.x, x) == 0 &&
                Float.compare(that.y, y) == 0 &&
                Float.compare(that.width, width) == 0 &&
                Float.compare(that.height, height) == 0 &&
                Objects.equals(filterUnits, that.filterUnits) &&
                Objects.equals(effectList, that.effectList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, filterUnits, effectList);
    }
}
