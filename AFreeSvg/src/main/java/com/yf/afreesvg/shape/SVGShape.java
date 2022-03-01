package com.yf.afreesvg.shape;

import androidx.annotation.NonNull;

import com.yf.afreesvg.ConvertToSVGElement;
import com.yf.afreesvg.SVGCanvas;

/**
 * The shape interface
 *
 * @author yinfei
 * @since 0.0.1
 */
public interface SVGShape extends ConvertToSVGElement, Cloneable {
    /**
     * clone
     * need deep clone when push in save stacks,{@link SVGCanvas#save()}
     *
     * @return
     */
    @NonNull
    public Object clone();
}
