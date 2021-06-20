package com.yf.afreesvg.gradient;

import androidx.annotation.ColorLong;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SVGBaseGradient implements SVGGradient {
    public static final int MODE_DEFAULT = 0;
    public static final int MODE_USERSPACE = 1;

    @IntDef({MODE_DEFAULT, MODE_USERSPACE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface POSMODE {
    }


    protected @POSMODE
    int posMode = MODE_DEFAULT;

    protected List<Float> stopOffset = new ArrayList<>();
    protected List<Long> stopColor = new ArrayList<>();

    public SVGBaseGradient(int posMode) {
        this.posMode = posMode;
    }

    public SVGBaseGradient() {
    }

    public int getPosMode() {
        return posMode;
    }

    public void setPosMode(int posMode) {
        this.posMode = posMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGBaseGradient that = (SVGBaseGradient) o;
        return posMode == that.posMode &&
                Objects.equals(stopOffset, that.stopOffset) &&
                Objects.equals(stopColor, that.stopColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posMode, stopOffset, stopColor);
    }

    public void addStopColor(float offset, @ColorLong long color) {
        stopOffset.add(offset);
        stopColor.add(color);
    }

    public int getStopCount() {
        return stopOffset.size();
    }

    public float getStopOffset(int index) {
        return stopOffset.get(index);
    }

    public @ColorLong
    long getStopColor(int index) {
        return stopColor.get(index);
    }
}
