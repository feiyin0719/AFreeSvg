package com.yf.afreesvg.gradient;

import android.graphics.PointF;

import java.util.Objects;

public class SVGLinearGradient extends SVGBaseGradient {


    private PointF startPoint;
    private PointF endPoint;

    public SVGLinearGradient(PointF startPoint, PointF endPoint) {
        this(startPoint, endPoint, MODE_DEFAULT);
    }

    public SVGLinearGradient(PointF startPoint, PointF endPoint, @POS_MODE int mode) {
        super(mode);
        this.startPoint = startPoint;
        this.endPoint = endPoint;

    }

    public SVGLinearGradient() {
        this(new PointF(0, 0), new PointF(0, 0), MODE_DEFAULT);
    }

    public PointF getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(PointF startPoint) {
        this.startPoint = startPoint;
    }

    public PointF getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(PointF endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGLinearGradient that = (SVGLinearGradient) o;
        return super.equals(that) &&
                Objects.equals(startPoint, that.startPoint) &&
                Objects.equals(endPoint, that.endPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startPoint, endPoint);
    }
}
