package com.yf.afreesvg.gradient;

import java.util.Objects;

public class SVGRadialGradient extends SVGBaseGradient {

    private float cx, cy, r, fx, fy;

    public SVGRadialGradient() {
    }

    public SVGRadialGradient(float cx, float cy, float r, float fx, float fy, @POS_MODE int posMode) {
        super(posMode);
        this.cx = cx;
        this.cy = cy;
        this.r = r;
        this.fx = fx;
        this.fy = fy;
    }

    public SVGRadialGradient(float cx, float cy, float r, float fx, float fy) {
        this(cx, cy, r, fx, fy, MODE_DEFAULT);
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getFx() {
        return fx;
    }

    public void setFx(float fx) {
        this.fx = fx;
    }

    public float getFy() {
        return fy;
    }

    public void setFy(float fy) {
        this.fy = fy;
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
}
