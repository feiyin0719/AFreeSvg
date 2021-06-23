package com.yf.afreesvg;

import android.graphics.Paint;

import androidx.annotation.ColorLong;
import androidx.annotation.StringDef;

import com.yf.afreesvg.font.SVGFont;
import com.yf.afreesvg.gradient.SVGGradient;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SVGPaint extends Paint {
    protected float dashArray[];
    protected SVGGradient gradient;
    protected @FillRule
    String fillRule = FILL_RULE_DEFAULT;

    protected @ColorLong
    long fillColor;

    protected boolean useGradientStroke = false;

    protected SVGFont font;
    protected @LengthAdjust
    String lengthAdjust = LENGTH_ADJUST_SPACING;

    protected @TextDecoration
    String textDecoration = TEXT_DECORATION_NONE;

    protected float wordSpacing = 0;

    public static final String FILL_RULE_DEFAULT = "nonzero";
    public static final String FILL_RULE_EVENODD = "evenodd";
    public static final String FILL_RULE_INHERIT = "inherit";


    public static final String LENGTH_ADJUST_SPACING = "spacing";
    public static final String LENGTH_ADJUST_SPACINGANDGLYPHS = "spacingAndGlyphs";

    public static final String TEXT_DECORATION_NONE = "none";
    public static final String TEXT_DECORATION_UNDERLINE = "underline";
    public static final String TEXT_DECORATION_OVERLINE = "overline";
    public static final String TEXT_DECORATION_LINETHROUGH = "line-through";
    public static final String TEXT_DECORATION_BLINK = "blink";

    @StringDef({LENGTH_ADJUST_SPACING, LENGTH_ADJUST_SPACINGANDGLYPHS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LengthAdjust {
    }

    @StringDef({FILL_RULE_DEFAULT, FILL_RULE_EVENODD, FILL_RULE_INHERIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FillRule {
    }

    @StringDef({TEXT_DECORATION_NONE, TEXT_DECORATION_UNDERLINE,
            TEXT_DECORATION_OVERLINE, TEXT_DECORATION_LINETHROUGH, TEXT_DECORATION_BLINK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextDecoration {

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

    public SVGFont getFont() {
        return font;
    }

    public void setFont(SVGFont font) {
        this.font = font;
    }

    public @LengthAdjust
    String getLengthAdjust() {
        return lengthAdjust;
    }

    public void setLengthAdjust(@LengthAdjust String lengthAdjust) {
        this.lengthAdjust = lengthAdjust;
    }

    public @TextDecoration
    String getTextDecoration() {
        return textDecoration;
    }

    public void setTextDecoration(@TextDecoration String textDecoration) {
        this.textDecoration = textDecoration;
    }

    @Override
    public float getWordSpacing() {
        return wordSpacing;
    }

    @Override
    public void setWordSpacing(float wordSpacing) {
        this.wordSpacing = wordSpacing;
    }
}
