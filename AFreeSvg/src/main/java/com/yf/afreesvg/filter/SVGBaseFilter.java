package com.yf.afreesvg.filter;

import com.yf.afreesvg.ConvertToSVGElement;
import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.PosMode;
import com.yf.afreesvg.SVGUtils;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Filter base class
 * Add filter to element,It used by {@link com.yf.afreesvg.SVGPaint#setFilter(SVGFilter)}
 * It needs to be combined with {@link SVGBaseFilterEffect} to generate a filter
 *
 * @author iffly
 * @since 0.0.2
 */
public abstract class SVGBaseFilter implements SVGFilter {
    /**
     *
     */
    public static final String GRAPHIC_VALUE = "SourceGraphic";
    public static final String ALPHA_VALUE = "SourceAlpha";
    /**
     * pos x
     */
    protected float x;
    /**
     * pos y
     */
    protected float y;
    /**
     * width
     */
    protected float width;
    /**
     * height
     */
    protected float height;
    /**
     * Coordinate mode
     *
     * @see PosMode
     */
    protected @PosMode
    String filterUnits = PosMode.MODE_BOX;
    /**
     * filter id
     */
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

    /**
     * Filter effect list
     */
    protected List<SVGBaseFilterEffect> effectList = new ArrayList<>();

    /**
     * Add filter effect to filter
     *
     * @param effect
     * @since 0.0.2
     */
    public void addEffect(SVGBaseFilterEffect effect) {
        effectList.add(effect);
    }

    /**
     * Filter effect
     * it will generate filter effect element such as "feColorMatrix|feGaussianBlur"
     * It is the child element of filter
     * It needs to be combined with {@link SVGBaseFilter} to generate a filter
     *
     * @since 0.0.2
     */
    protected static abstract class SVGBaseFilterEffect implements ConvertToSVGElement {
        /**
         * The effect input
         * The value can be {@link #GRAPHIC_VALUE},{@link #ALPHA_VALUE},and other effect {@link #result}
         */
        protected String in;
        /**
         * The effect output
         * It used by when multiple effect combinations,
         * We can set it to next effect {@link #in}
         */
        protected String result;

        /**
         * Add base attr such as in and result
         *
         * @param element
         * @since 0.0.2
         */
        protected void addBaseAttr(Element element) {
            if (!SVGUtils.isTextEmpty(in)) {
                element.setAttribute("in", in);
            }
            if (!SVGUtils.isTextEmpty(result)) {
                element.setAttribute("result", result);
            }
        }

        /**
         * Set in
         *
         * @param in
         * @since 0.0.2
         */
        public void setIn(String in) {
            this.in = in;
        }

        /**
         * Set result
         *
         * @param result
         * @since 0.0.2
         */
        public void setResult(String result) {
            this.result = result;
        }

        /**
         * Get in
         *
         * @return
         * @since 0.0.2
         */
        public String getIn() {
            return in;
        }

        /**
         * Get result
         *
         * @return
         * @since 0.0.2
         */
        public String getResult() {
            return result;
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

    /**
     * Get x
     *
     * @return
     * @since 0.0.2
     */
    public float getX() {
        return x;
    }

    /**
     * Set x
     *
     * @param x
     * @since 0.0.2
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Get y
     *
     * @return
     * @since 0.0.2
     */
    public float getY() {
        return y;
    }

    /**
     * Set y
     *
     * @param y
     * @since 0.0.2
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Get width
     *
     * @return
     * @since 0.0.2
     */
    public float getWidth() {
        return width;
    }

    /**
     * Set width
     *
     * @param width
     * @since 0.0.2
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Get height
     *
     * @return
     * @since 0.0.2
     */
    public float getHeight() {
        return height;
    }

    /**
     * set height
     *
     * @param height
     * @since 0.0.2
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Get Coordinate mode
     *
     * @return {@link PosMode}
     * @since 0.0.2
     */
    public @PosMode
    String getFilterUnits() {
        return filterUnits;
    }

    /**
     * Set Coordinate mode
     *
     * @param filterUnits
     * @since 0.0.2
     */
    public void setFilterUnits(@PosMode String filterUnits) {
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
