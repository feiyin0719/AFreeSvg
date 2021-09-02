package com.yf.afreesvg.gradient;

import androidx.annotation.ColorLong;
import androidx.annotation.StringDef;

import com.yf.afreesvg.PosMode;
import com.yf.afreesvg.SVGUtils;
import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base gradient
 *
 * @author iffly
 * @since 0.0.1
 */
public abstract class SVGBaseGradient implements SVGGradient {

    //not repeat,it will use end color fill other
    public static final String SPREAD_PAD = "pad";
    //it will repeat fill
    public static final String SPREAD_REPEAT = "repeat";
    //it will reverse fill and repeat
    public static final String SPREAD_REFLECT = "reflect";


    @StringDef({SPREAD_PAD, SPREAD_REPEAT, SPREAD_REFLECT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SPREAD_MODE {
    }

    /**
     * Coordinate mode
     *
     * @see PosMode
     */
    protected @PosMode
    String posMode = PosMode.MODE_BOX;
    /**
     * Repeat method
     * {@link #SPREAD_PAD} not repeat,it will use end color fill other
     * {@link #SPREAD_REFLECT} it will reverse fill and repeat
     * {@link #SPREAD_REPEAT} it will repeat fill
     */
    protected @SPREAD_MODE
    String spreadMode = SPREAD_PAD;
    /**
     * The color offset pos
     */
    protected List<Float> stopOffset = new ArrayList<>();
    /**
     * The offset color
     */
    protected List<Long> stopColor = new ArrayList<>();

    public SVGBaseGradient(@PosMode String posMode) {
        this.posMode = posMode;
    }

    public SVGBaseGradient() {
    }

    /**
     * Get Coordinate mode
     *
     * @return
     * @since 0.0.1
     */
    public @PosMode
    String getPosMode() {
        return posMode;
    }

    /**
     * set Coordinate mode
     *
     * @param posMode
     * @since 0.0.1
     */
    public void setPosMode(@PosMode String posMode) {
        this.posMode = posMode;
    }

    /**
     * Add offset colot
     *
     * @param offset
     * @param color
     * @since 0.0.1
     */
    public void addStopColor(float offset, @ColorLong long color) {
        stopOffset.add(offset);
        stopColor.add(color);
    }

    /**
     * Get color count
     *
     * @return
     * @since 0.0.1
     */
    public int getStopCount() {
        return stopOffset.size();
    }

    /**
     * Get offset of index pos
     *
     * @param index
     * @return
     * @since 0.0.1
     */
    public float getStopOffset(int index) {
        return stopOffset.get(index);
    }

    /**
     * Get color of index pos
     *
     * @param index
     * @return
     * @since 0.0.1
     */
    public @ColorLong
    long getStopColor(int index) {
        return stopColor.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SVGBaseGradient that = (SVGBaseGradient) o;
        return posMode == that.posMode &&
                Objects.equals(spreadMode, that.spreadMode) &&
                Objects.equals(stopOffset, that.stopOffset) &&
                Objects.equals(stopColor, that.stopColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posMode, spreadMode, stopOffset, stopColor);
    }

    /**
     * Get repeat mode
     *
     * @return
     * @since 0.0.1
     */
    public @SPREAD_MODE
    String getSpreadMode() {
        return spreadMode;
    }

    /**
     * Set repeat mode
     *
     * @param spreadMode
     * @since 0.0.1
     */
    public void setSpreadMode(String spreadMode) {
        this.spreadMode = spreadMode;
    }

    /**
     * Add base attr to gradient element
     *
     * @param element  The gradient element
     * @param document The dom document,use it to create element {@link Document}
     * @param convert  The double convert,convert double to string
     * @since 0.0.1
     */
    protected void initBaseGradientAttr(Element element, Document document, DoubleFunction<String> convert) {
        if (PosMode.MODE_USERSPACE.equals(posMode))
            element.setAttribute("gradientUnits", posMode);
        if (!getSpreadMode().equals(SVGBaseGradient.SPREAD_PAD))
            element.setAttribute("spreadMethod", getSpreadMode());
        for (int i = 0; i < getStopCount(); ++i) {
            Element stop = document.createElement("stop");
            long c1 = getStopColor(i);
            stop.setAttribute("offset", "" + getStopOffset(i));
            stop.setAttribute("stop-color", SVGUtils.rgbColorStr(c1));

            if (SVGUtils.colorAlpha(c1) < 255) {
                double alphaPercent = SVGUtils.colorAlpha(c1) / 255.0;
                stop.setAttribute("stop-opacity", convert.apply(alphaPercent));

            }
            element.appendChild(stop);
        }
    }
}
