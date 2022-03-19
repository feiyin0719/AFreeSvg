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

package com.yf.afreesvg.font;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Font info class
 * When drawText,use it set font info.
 * It used by {@link com.yf.afreesvg.SVGPaint#setFont(SVGFont)}
 *
 * @author iffly
 * @since 0.0.1
 */
public class SVGFont {
    /**
     * Font family name
     */
    @NonNull
    private final String fontFamily;
    /**
     * Font size
     */
    private final int fontSize;
    /**
     * Font weight
     * The values range
     * "normal | bold | bolder | lighter | 100 | 200 | 300 | 400 | 500 | 600 | 700 | 800 | 900"
     */
    @NonNull
    private final String fontWeight;
    /**
     * Font style
     *
     * @see FontStyle
     */
    private @FontStyle
    final
    String fontStyle;
    /**
     * Font variant
     *
     * @see FontVariant
     */
    private @FontVariant
    final
    String fontVariant;

    @StringDef({FontStyle.STYLE_NORMAL, FontStyle.STYLE_ITALIC, FontStyle.STYLE_OBLIQUE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontStyle {
        String STYLE_NORMAL = "normal";
        String STYLE_ITALIC = "italic";
        String STYLE_OBLIQUE = "oblique";
    }

    @StringDef({FontVariant.VARIANT_NORMAL, FontVariant.VARIANT_SMALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontVariant {
        String VARIANT_NORMAL = "normal";
        String VARIANT_SMALL = "small-caps";
    }


    private SVGFont(String fontFamily, int fontSize, String fontWeight, String fontStyle, String fontVariant) {
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.fontWeight = fontWeight;
        this.fontStyle = fontStyle;
        this.fontVariant = fontVariant;
    }

    /**
     * Get fontFamily
     *
     * @return
     * @since 0.0.1
     */
    public @NonNull
    String getFontFamily() {
        return fontFamily;
    }

    /**
     * Get font size
     *
     * @return
     * @since 0.0.1
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Get fontWeight
     * The value range
     * "normal | bold | bolder | lighter | 100 | 200 | 300 | 400 | 500 | 600 | 700 | 800 | 900"
     *
     * @return
     * @since 0.0.1
     */
    public @NonNull
    String getFontWeight() {
        return fontWeight;
    }

    /**
     * Get fontStyle
     *
     * @return
     * @since 0.0.1
     */
    public @FontStyle
    String getFontStyle() {
        return fontStyle;
    }

    /**
     * Get fontVariant
     *
     * @return
     * @since 0.0.1
     */
    public @FontVariant
    String getFontVariant() {
        return fontVariant;
    }

    /**
     * Font builder
     * Use it to create font
     * Example code:
     * <pre>
     *    SVGFont font = new SVGFont.Builder().setFontFamily("sans-serif")
     *                     .setFontStyle(SVGFont.STYLE_ITALIC)
     *                     .setFontWeight("bold")
     *                     .setFontSize(24)
     *                     .build();
     *
     * </pre>
     *
     * @since 0.0.1
     */
    public static class Builder {
        @NonNull
        private String fontFamily;
        private int fontSize = 16;
        @NonNull
        private String fontWeight = "normal";
        private @FontStyle
        String fontStyle = FontStyle.STYLE_NORMAL;
        private @FontVariant
        String fontVariant = FontVariant.VARIANT_NORMAL;

        public Builder setFontFamily(@NonNull String fontFamily) {
            this.fontFamily = fontFamily;
            return this;
        }

        public Builder setFontSize(int fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder setFontWeight(@NonNull String fontWeight) {
            this.fontWeight = fontWeight;
            return this;
        }

        public Builder setFontStyle(@FontStyle String fontStyle) {
            this.fontStyle = fontStyle;
            return this;
        }

        public Builder setFontVariant(@FontVariant String fontVariant) {
            this.fontVariant = fontVariant;
            return this;
        }

        public SVGFont build() {
            if (fontFamily == null) {
                throw new IllegalArgumentException("fontFamily cannot be null");
            }
            return new SVGFont(fontFamily, fontSize, fontWeight, fontStyle, fontVariant);
        }
    }
}
