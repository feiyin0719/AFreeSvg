package com.yf.afreesvg.font;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SVGFont {
    @NonNull
    private String fontFamily;
    private int fontSize;
    @NonNull
    private String fontWeight;
    private @FontStyle
    String fontStyle;
    private @FontVariant
    String fontVariant;

    public static final String STYLE_NORMAL = "normal";
    public static final String STYLE_ITALIC = "italic";
    public static final String STYLE_OBLIQUE = "oblique";

    public static final String VARIANT_NORMAL = "normal";
    public static final String VARIANT_SMALL = "small-caps";

    @StringDef({STYLE_NORMAL, STYLE_ITALIC, STYLE_OBLIQUE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontStyle {
    }

    @StringDef({VARIANT_NORMAL, VARIANT_SMALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontVariant {
    }


    private SVGFont(String fontFamily, int fontSize, String fontWeight, String fontStyle, String fontVariant) {
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.fontWeight = fontWeight;
        this.fontStyle = fontStyle;
        this.fontVariant = fontVariant;
    }

    public @NonNull
    String getFontFamily() {
        return fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public @NonNull
    String getFontWeight() {
        return fontWeight;
    }

    public @FontStyle
    String getFontStyle() {
        return fontStyle;
    }

    public @FontVariant
    String getFontVariant() {
        return fontVariant;
    }

    public static class Builder {
        @NonNull
        private String fontFamily;
        private int fontSize = 16;
        @NonNull
        private String fontWeight = "normal";
        private @FontStyle
        String fontStyle = STYLE_NORMAL;
        private @FontVariant
        String fontVariant = VARIANT_NORMAL;

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
            return new SVGFont(fontFamily, fontSize, fontWeight, fontStyle, fontVariant);
        }
    }
}
