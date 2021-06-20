package com.yf.afreesvg;

import android.graphics.Paint;

import com.yf.afreesvg.gradient.SVGGradient;

public class SVGPaint extends Paint {
    protected float dashArray[];
    protected SVGGradient gradient;

    public SVGPaint() {
        super();
    }

    public SVGPaint(int flags) {
        super(flags);
    }

    public float[] getDashArray() {
        return dashArray;
    }

    public void setDashArray(float[] dashArray) {
        this.dashArray = dashArray;
    }

    public SVGGradient getGradient() {
        return gradient;
    }

    public void setGradient(SVGGradient gradient) {
        this.gradient = gradient;
    }

    @Override
    public int getAlpha() {
        if (gradient == null)
            return super.getAlpha();
        return 255;
    }
}
