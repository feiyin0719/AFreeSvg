package com.yf.afreesvg.filter;

import org.junit.Test;
import org.w3c.dom.Element;

import static org.junit.Assert.*;

public class SVGFilterGroupTest extends SVGFilterBaseTest {

    @Test
    public void convertToSVGElement() {
        SVGFilterGroup group = new SVGFilterGroup();
        group.addEffect(new SVGOffsetFilter.SVGOffsetFilterEffect(5f, 10f));
        group.addEffect(new SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect(5f, 3f));
        Element element = group.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        filterBaseTest(element);
        assertEquals(2, element.getChildNodes().getLength());
        group.addEffect(new SVGFilterGroup.SVGMergeFilterEffect().addMergeNode("in1").addMergeNode("in2"));
        Element element1 = group.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        filterBaseTest(element1);
        assertEquals(3, element1.getChildNodes().getLength());
    }

    public void mergeNodeTest(Element child, String inExcepted) {
        assertNotNull(child);
        assertEquals("feMergeNode", child.getTagName());
        assertEquals(inExcepted, child.getAttribute("in"));
    }

    @Test
    public void mergeEffectConvertToElement() {
        SVGFilterGroup.SVGMergeFilterEffect mergeFilterEffect = new SVGFilterGroup.SVGMergeFilterEffect();
        mergeFilterEffect.addMergeNode("in1");
        mergeFilterEffect.addMergeNode("in2");
        Element element = mergeFilterEffect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);
        assertEquals("feMerge", element.getTagName());
        assertEquals(2, element.getChildNodes().getLength());
        mergeNodeTest((Element) element.getFirstChild(), "in1");
        mergeNodeTest((Element) element.getLastChild(), "in2");
    }

    @Test
    public void blendEffectConvertToElement() {
        SVGFilterGroup.SVGBlendFilterEffect blendFilterEffect = new SVGFilterGroup.SVGBlendFilterEffect();
        blendFilterEffect.setIn("in1");
        blendFilterEffect.setIn2("in2");
        assertNotNull(blendFilterEffect.getMode());
        assertEquals(SVGFilterGroup.SVGBlendFilterEffect.MODE_NORMAL, blendFilterEffect.getMode());
        blendFilterEffect.setMode(SVGFilterGroup.SVGBlendFilterEffect.MODE_DARKEN);
        assertEquals(SVGFilterGroup.SVGBlendFilterEffect.MODE_DARKEN, blendFilterEffect.getMode());
        Element element = blendFilterEffect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertEquals("feBlend", element.getTagName());
        assertEquals("in1", element.getAttribute("in"));
        assertEquals("in2", element.getAttribute("in2"));
        assertEquals(SVGFilterGroup.SVGBlendFilterEffect.MODE_DARKEN, element.getAttribute("mode"));
        effectBaseTest(blendFilterEffect);
    }

    @Test
    public void compositeEffectConvertToElement() {
        SVGFilterGroup.SVGCompositeFilterEffect compositeFilterEffect = new SVGFilterGroup.SVGCompositeFilterEffect();
        compositeFilterEffect.setIn2("in2");
        compositeFilterEffect.setIn("in");
        assertNotNull(compositeFilterEffect.getOperate());
        compositeFilterEffect.setOperate(SVGFilterGroup.SVGCompositeFilterEffect.OPERATE_IN);
        assertEquals(SVGFilterGroup.SVGCompositeFilterEffect.OPERATE_IN, compositeFilterEffect.getOperate());
        compositeFilterEffect.setK1(0.1f);
        compositeFilterEffect.setK2(0.1f);
        compositeFilterEffect.setK3(0.1f);
        compositeFilterEffect.setK4(0.1f);
        Element element = compositeFilterEffect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertEquals("in", element.getAttribute("in"));
        assertEquals("in2", element.getAttribute("in2"));
        assertEquals(SVGFilterGroup.SVGCompositeFilterEffect.OPERATE_IN, element.getAttribute("operate"));
        assertEquals(false, element.hasAttribute("k1"));
        compositeFilterEffect.setOperate(SVGFilterGroup.SVGCompositeFilterEffect.OPERATE_ARITHMETIC);
        Element element1 = compositeFilterEffect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertEquals(canvas.getGeomDoubleConverter().apply(0.1f), element1.getAttribute("k1"));
        assertEquals(canvas.getGeomDoubleConverter().apply(0.1f), element1.getAttribute("k2"));
        assertEquals(canvas.getGeomDoubleConverter().apply(0.1f), element1.getAttribute("k3"));
        assertEquals(canvas.getGeomDoubleConverter().apply(0.1f), element1.getAttribute("k4"));


    }
}