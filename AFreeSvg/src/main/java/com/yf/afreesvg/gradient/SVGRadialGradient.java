package com.yf.afreesvg.gradient;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.PosMode;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

/**
 * RadialGradient
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGRadialGradient extends SVGBaseGradient {
    /**
     * outer circle centerX
     */
    private float cx;
    /**
     * outer circle centerY
     */
    private float cy;
    /**
     * outer circle radius
     */
    private float r;
    /**
     * inner circle centerX
     */
    private float fx;
    /**
     * inner circle centerY
     */
    private float fy;
    /**
     * inner circle radius
     *
     * @since 0.0.2
     */
    private float fr;

    public SVGRadialGradient() {
    }

    public SVGRadialGradient(float cx, float cy, float r, float fx, float fy, @PosMode String posMode) {
        super(posMode);
        this.cx = cx;
        this.cy = cy;
        this.r = r;
        this.fx = fx;
        this.fy = fy;
    }

    public SVGRadialGradient(float cx, float cy, float r, float fx, float fy) {
        this(cx, cy, r, fx, fy, PosMode.MODE_BOX);
    }

    /**
     * Get outer circle centerX
     *
     * @return
     * @since 0.0.1
     */
    public float getCx() {
        return cx;
    }

    /**
     * Set outer circle centerX
     *
     * @param cx
     * @since 0.0.1
     */
    public void setCx(float cx) {
        this.cx = cx;
    }

    /**
     * Get outer circle centerY
     *
     * @return
     * @since 0.0.1
     */
    public float getCy() {
        return cy;
    }

    /**
     * Set outer  circle centerY
     *
     * @param cy
     * @since 0.0.1
     */
    public void setCy(float cy) {
        this.cy = cy;
    }

    /**
     * Get outer circle radius
     *
     * @return
     * @since 0.0.1
     */
    public float getR() {
        return r;
    }

    /**
     * Set outer circle radius
     *
     * @param r
     * @since 0.0.1
     */
    public void setR(float r) {
        this.r = r;
    }

    /**
     * Get inner circle centerX
     *
     * @return
     * @since 0.0.1
     */
    public float getFx() {
        return fx;
    }

    /**
     * Set inner circle centerX
     *
     * @param fx
     * @since 0.0.1
     */
    public void setFx(float fx) {
        this.fx = fx;
    }

    /**
     * Get inner circle centerY
     *
     * @return
     * @since 0.0.1
     */
    public float getFy() {
        return fy;
    }

    /**
     * Set inner circle centerY
     *
     * @param fy
     * @since 0.0.1
     */
    public void setFy(float fy) {
        this.fy = fy;
    }

    /**
     * Get inner circle radius
     *
     * @return
     * @since 0.0.2
     */
    public float getFr() {
        return fr;
    }

    /**
     * Set inner circle radius
     *
     * @param fr
     * @since 0.0.2
     */

    public void setFr(float fr) {
        this.fr = fr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SVGRadialGradient that = (SVGRadialGradient) o;
        return super.equals(that) &&
                Float.compare(that.cx, cx) == 0 &&
                Float.compare(that.cy, cy) == 0 &&
                Float.compare(that.r, r) == 0 &&
                Float.compare(that.fx, fx) == 0 &&
                Float.compare(that.fy, fy) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cx, cy, r, fx, fy);
    }

    @Override
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert) {
        Element element = document.createElement("radialGradient");

        element.setAttribute("cx", convert.apply(getCx()));
        element.setAttribute("cy", convert.apply(getCy()));
        element.setAttribute("r", convert.apply(getR()));
        element.setAttribute("fx", convert.apply(getFx()));
        element.setAttribute("fy", convert.apply(getFy()));
        element.setAttribute("fr", convert.apply(getFr()));
        initBaseGradientAttr(element, document, convert);

        return element;
    }
}
