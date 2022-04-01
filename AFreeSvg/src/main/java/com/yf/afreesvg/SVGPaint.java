/*
 * Copyright (c) 2022.  by iffly Limited.  All rights reserved.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 */

package com.yf.afreesvg;

import android.graphics.Paint;

import androidx.annotation.ColorInt;
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
     * @see FillRule
     */
    protected @FillRule
    String fillRule = FillRule.FILL_RULE_DEFAULT;
    /**
     * The fill color
     * Use it can different of strokeColor
     */
    protected @ColorInt
    int fillColor;
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
     * @see LengthAdjust
     */
    protected @LengthAdjust
    String lengthAdjust = LengthAdjust.LENGTH_ADJUST_SPACING;
    /**
     * The text Decoration
     */
    protected @TextDecoration
    String textDecoration = TextDecoration.TEXT_DECORATION_NONE;
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


    @StringDef({LengthAdjust.LENGTH_ADJUST_SPACING, LengthAdjust.LENGTH_ADJUST_SPACINGANDGLYPHS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LengthAdjust {
        String LENGTH_ADJUST_SPACING = "spacing";
        String LENGTH_ADJUST_SPACINGANDGLYPHS = "spacingAndGlyphs";
    }

    @StringDef({FillRule.FILL_RULE_DEFAULT, FillRule.FILL_RULE_EVENODD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FillRule {
        String FILL_RULE_DEFAULT = "nonzero";
        String FILL_RULE_EVENODD = "evenodd";
    }

    @StringDef({TextDecoration.TEXT_DECORATION_NONE, TextDecoration.TEXT_DECORATION_UNDERLINE,
            TextDecoration.TEXT_DECORATION_OVERLINE, TextDecoration.TEXT_DECORATION_LINETHROUGH, TextDecoration.TEXT_DECORATION_BLINK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextDecoration {

        String TEXT_DECORATION_NONE = "none";
        String TEXT_DECORATION_UNDERLINE = "underline";
        String TEXT_DECORATION_OVERLINE = "overline";
        String TEXT_DECORATION_LINETHROUGH = "line-through";
        String TEXT_DECORATION_BLINK = "blink";
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
     * Set fillRule
     *
     * @param fillRule The fill rule,{@link FillRule}
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
    public int getFillColor() {
        return fillColor;
    }

    /**
     * Set fill color
     *
     * @param fillColor
     * @since 0.0.1
     */
    public void setFillColor(@ColorInt int fillColor) {
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
