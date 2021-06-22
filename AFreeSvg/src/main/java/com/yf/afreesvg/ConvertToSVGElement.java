package com.yf.afreesvg;

import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface ConvertToSVGElement {
    public Element convertToSVGElement(Document document, DoubleFunction<String> convert);
}
