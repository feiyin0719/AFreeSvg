package com.yf.afreesvg;

import android.graphics.Paint;

import androidx.annotation.ColorLong;
import androidx.annotation.StringDef;

import com.yf.afreesvg.filter.SVGFilter;
import com.yf.afreesvg.font.SVGFont;
import com.yf.afreesvg.gradient.SVGGradient;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The SVGPaint
 * Use it to set draw style,it inherit {@link Paint}
 * if you want to learn about the format and attrs of svg,plz refer to http://www.verydoc.net/svg/
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGPaint extends Paint {
    /**
     * The stroke dash
     */
    protected float dashArray[];
    /**
     * The gradient color
     */
    protected SVGGradient gradient;
    /**
     * The fillRule
     *
     * @see #FILL_RULE_DEFAULT
     * @see #FILL_RULE_EVENODD
     */
    protected @FillRule
    String fillRule = FILL_RULE_DEFAULT;
    /**
     * The fill color
     * Use it can different of strokeColor
     */
    protected @ColorLong
    long fillColor;
    /**
     * Stroke use gradient
     * true and {@link #gradient} is not null means that stroke use gradient
     */
    protected boolean useGradientStroke = false;
    /**
     * The text font
     * use it to {@link SVGCanvas#drawText(String, float, float, SVGPaint)}
     *
     * @see SVGFont
     */
    protected SVGFont font;
    /**
     * The text lengthAdjust
     *
     * @see #LENGTH_ADJUST_SPACING
     * @see #LENGTH_ADJUST_SPACINGANDGLYPHS
     */
    protected @LengthAdjust
    String lengthAdjust = LENGTH_ADJUST_SPACING;
    /**
     * The text Decoration
     */
    protected @TextDecoration
    String textDecoration = TEXT_DECORATION_NONE;
    /**
     * The text word space
     */
    protected float wordSpacing = 0;
    /**
     * The filter
     *
     * @see SVGFilter
     * @since 0.0.2
     */
    protected SVGFilter filter;

    public static final String FILL_RULE_DEFAULT = "nonzero";
    public static final String FILL_RULE_EVENODD = "evenodd";


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

    @StringDef({FILL_RULE_DEFAULT, FILL_RULE_EVENODD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FillRule {
    }

    @StringDef({TEXT_DECORATION_NONE, TEXT_DECORATION_UNDERLINE,
            TEXT_DECORATION_OVERLINE, TEXT_DECORATION_LINETHROUGH, TEXT_DECORATION_BLINK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextDecoration {

    }

    /**
     * Construct
     * refer to {@link Paint#Paint()}
     *
     * @since 0.0.1
     */
    public SVGPaint() {
        super();
    }

    /**
     * Construct
     * refer to {@link Paint#Paint(int)}
     *
     * @param flags initial flag bits, as if they were passed via setFlags().
     * @since 0.0.1
     */
    public SVGPaint(int flags) {
        super(flags);
    }

    /**
     * Get stroke dash
     *
     * @return stroke dash
     * @since 0.0.1
     */
    public float[] getDashArray() {
        return dashArray;
    }

    /**
     * Set stroke dash
     *
     * @param dashArray
     * @since 0.0.1
     */
    public void setDashArray(float[] dashArray) {
        this.dashArray = dashArray;
    }

    /**
     * Get gradient color
     *
     * @return The gradient {@link SVGGradient}
     * @since 0.0.1
     */
    public SVGGradient getGradient() {
        return gradient;
    }

    /**
     * Set gradient color
     *
     * @param gradient {@link SVGGradient}
     * @since 0.0.1
     */

    public void setGradient(SVGGradient gradient) {
        this.gradient = gradient;
    }

    /**
     * Get alpha
     * override it to return 255 when {@link #useGradientStroke} is true
     *
     * @return The stroke color alpha
     * @since 0.0.1
     */
    @Override
    public int getAlpha() {
        if (gradient == null || !useGradientStroke)
            return super.getAlpha();
        return 255;
    }

    /**
     * Get fill rule
     *
     * @return The fill rule
     * @since 0.0.1
     */
    public String getFillRule() {
        return fillRule;
    }

    /**
     * Set fullRule
     *
     * @param fillRule The fill rule,{@link #FILL_RULE_DEFAULT} {@link #FILL_RULE_EVENODD}
     */
    public void setFillRule(String fillRule) {
        this.fillRule = fillRule;
    }

    /**
     * Get fill color
     *
     * @return The fill color
     * @since 0.0.1
     */
    public long getFillColor() {
        return fillColor;
    }

    /**
     * Set fill color
     *
     * @param fillColor
     * @since 0.0.1
     */
    public void setFillColor(@ColorLong long fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * Get fill color alpha
     *
     * @return fill color alpha
     * @since 0.0.1
     */
    public int getFillColorAlpha() {
        if (gradient == null)
            return (int) (fillColor >> 24 & 0xff);
        return 255;
    }

    /**
     * Get stroke use gradient
     *
     * @return true and {@link #gradient} not null means use gradient
     * @since 0.0.1
     */
    public boolean isUseGradientStroke() {
        return useGradientStroke;
    }

    /**
     * Set stroke use gradient
     *
     * @param useGradientStroke true and {@link #gradient} not null means use gradient
     * @since 0.0.1
     */
    public void setUseGradientStroke(boolean useGradientStroke) {
        this.useGradientStroke = useGradientStroke;
    }

    /**
     * Get text font
     *
     * @return font {@link SVGFont}
     * @see SVGCanvas#drawText(String, float, float, SVGPaint)
     * @since 0.0.1
     */
    public SVGFont getFont() {
        return font;
    }

    /**
     * Set text font
     *
     * @param font {@link SVGFont}
     * @since 0.0.1
     */
    public void setFont(SVGFont font) {
        this.font = font;
    }

    /**
     * Get text lengthAdjust
     *
     * @return text lengthAdjust
     * @since 0.0.1
     */
    public @LengthAdjust
    String getLengthAdjust() {
        return lengthAdjust;
    }

    /**
     * Set text lengthAdjust
     *
     * @param lengthAdjust
     * @since 0.0.1
     */
    public void setLengthAdjust(@LengthAdjust String lengthAdjust) {
        this.lengthAdjust = lengthAdjust;
    }

    /**
     * Get textDecoration
     *
     * @return textDecoration
     * @since 0.0.1
     */

    public @TextDecoration
    String getTextDecoration() {
        return textDecoration;
    }

    /**
     * Set textDecoration
     *
     * @param textDecoration
     * @since 0.0.1
     */
    public void setTextDecoration(@TextDecoration String textDecoration) {
        this.textDecoration = textDecoration;
    }

    /**
     * Get wordSpacing for drawText
     *
     * @return
     * @since 0.0.1
     */
    @Override
    public float getWordSpacing() {
        return wordSpacing;
    }

    /**
     * Set wordSpacing
     *
     * @param wordSpacing
     * @since 0.0.1
     */
    @Override
    public void setWordSpacing(float wordSpacing) {
        this.wordSpacing = wordSpacing;
    }

    /**
     * Get filter
     *
     * @return The filter {@link SVGFilter}
     * @since 0.0.2
     */
    public SVGFilter getFilter() {
        return filter;
    }

    /**
     * Set filter
     *
     * @param filter {@link SVGFilter}
     * @since 0.0.2
     */
    public void setFilter(SVGFilter filter) {
        this.filter = filter;
    }
}
