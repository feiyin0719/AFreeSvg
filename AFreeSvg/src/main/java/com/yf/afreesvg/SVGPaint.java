package com.yf.afreesvg;

import android.graphics.Paint;

import androidx.annotation.ColorLong;
import androidx.annotation.StringDef;

import com.yf.afreesvg.gradient.SVGGradient;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SVGPaint extends Paint {
    protected float dashArray[];
    protected SVGGradient gradient;
    protected @FILLRULE
    String fillRule = FILL_RULE_DEFAULT;

    protected @ColorLong
    long fillColor;

    protected boolean useGradientStroke = false;

    public static final String FILL_RULE_DEFAULT = "nonzero";
    public static final String FILL_RULE_EVENODD = "evenodd";
    public static final String FILL_RULE_INHERIT = "inherit";

    @StringDef({FILL_RULE_DEFAULT, FILL_RULE_EVENODD, FILL_RULE_INHERIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FILLRULE {
    }

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
        if (gradient == null || !useGradientStroke)
            return super.getAlpha();
        return 255;
    }

    public String getFillRule() {
        return fillRule;
    }

    public void setFillRule(String fillRule) {
        this.fillRule = fillRule;
    }

    public long getFillColor() {
        return fillColor;
    }

    public void setFillColor(@ColorLong long fillColor) {
        this.fillColor = fillColor;
    }

    public int getFillColorAlpha() {
        if (gradient == null)
            return (int) (fillColor >> 24 & 0xff);
        return 255;
    }

    public boolean isUseGradientStroke() {
        return useGradientStroke;
    }

    public void setUseGradientStroke(boolean useGradientStroke) {
        this.useGradientStroke = useGradientStroke;
    }
}
