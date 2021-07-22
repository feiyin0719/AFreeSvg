package com.yf.afreesvg.filter;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

/**
 * Offset filter
 *
 * @author iffly
 * @since 0.0.2
 */
public class SVGOffsetFilter extends SVGBaseFilter {
    /**
     * Offset filter effect
     */
    protected SVGOffsetFilterEffect offsetFilterEffect;

    public SVGOffsetFilter(float dx, float dy) {
        this(0, 0, 0, 0, dx, dy, GRAPHIC_VALUE);
    }

    public SVGOffsetFilter(float x, float y, float width, float height, float dx, float dy, String in) {
        super(x, y, width, height);
        offsetFilterEffect = new SVGOffsetFilterEffect(dx, dy);
        offsetFilterEffect.setIn(in);
        addEffect(offsetFilterEffect);
    }

    /**
     * Offset filter effect
     *
     * @since 0.0.2
     */
    public static class SVGOffsetFilterEffect extends SVGBaseFilterEffect {
        /**
         * offset dx
         */
        private float dx;
        /**
         * offset dy
         */
        private float dy;

        /**
         * Get offset dx
         *
         * @return
         * @since 0.0.2
         */
        public float getDx() {
            return dx;
        }

        /**
         * Set offset dx
         *
         * @param dx
         * @since 0.0.2
         */
        public void setDx(float dx) {
            this.dx = dx;
        }

        /**
         * Get offset dy
         *
         * @return
         * @since 0.0.2
         */
        public float getDy() {
            return dy;
        }

        /**
         * Set offset dy
         *
         * @param dy
         * @since 0.0.2
         */
        public void setDy(float dy) {
            this.dy = dy;
        }

        public SVGOffsetFilterEffect(float dx, float dy) {
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
            Element element = document.createElement("feOffset");
            addBaseAttr(element);
            element.setAttribute("dx", convert.apply(dx));
            element.setAttribute("dy", convert.apply(dy));
            return element;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            SVGOffsetFilterEffect that = (SVGOffsetFilterEffect) o;
            return Float.compare(that.dx, dx) == 0 &&
                    Float.compare(that.dy, dy) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), dx, dy);
        }
    }

    /**
     * Get offset dx
     *
     * @return
     * @since 0.0.2
     */
    public float getDx() {
        return offsetFilterEffect.getDx();
    }

    /**
     * Set offset dx
     *
     * @param dx
     * @since 0.0.2
     */
    public void setDx(float dx) {
        offsetFilterEffect.setDx(dx);
    }

    /**
     * Get offset dy
     *
     * @return
     * @since 0.0.2
     */
    public float getDy() {
        return offsetFilterEffect.getDy();
    }

    /**
     * Set offset dy
     *
     * @param dy
     * @since 0.0.2
     */
    public void setDy(float dy) {
        offsetFilterEffect.setDy(dy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SVGOffsetFilter that = (SVGOffsetFilter) o;
        return Objects.equals(offsetFilterEffect, that.offsetFilterEffect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), offsetFilterEffect);
    }
}
