package com.yf.afreesvg;

import android.graphics.Paint;

public class SVGPaint extends Paint {
    protected float dashArray[];
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

}
