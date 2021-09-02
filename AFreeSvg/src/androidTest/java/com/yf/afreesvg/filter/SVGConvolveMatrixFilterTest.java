package com.yf.afreesvg.filter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SVGConvolveMatrixFilterTest extends SVGFilterBaseTest {

    protected int order = 3;

    protected float[] kernelMatrix;


    protected @SVGConvolveMatrixFilter.SVGConvolveMatrixFilterEffect.EdgeMode
    String edgeMode;

    protected boolean preserveAlpha = false;

    public SVGConvolveMatrixFilterTest(int order, float[] kernelMatrix, String edgeMode, boolean preserveAlpha) {
        this.order = order;
        this.kernelMatrix = kernelMatrix;
        this.edgeMode = edgeMode;
        this.preserveAlpha = preserveAlpha;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {3, new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, SVGConvolveMatrixFilter.SVGConvolveMatrixFilterEffect.EdgeMode.EDGE_MODE_DUPLICATE, false},
                {4, new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, SVGConvolveMatrixFilter.SVGConvolveMatrixFilterEffect.EdgeMode.EDGE_MODE_NONE, true},
                {4, new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}, SVGConvolveMatrixFilter.SVGConvolveMatrixFilterEffect.EdgeMode.EDGE_MODE_WRAP, true},

        });
    }

    @Test
    public void convertToSVGElement() {
        SVGConvolveMatrixFilter filter = new SVGConvolveMatrixFilter(kernelMatrix);
        filter.setOrder(order);
        filter.setPreserveAlpha(preserveAlpha);
        filter.setEdgeMode(edgeMode);
        Element element = filter.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        Assert.assertNotNull(element);
        filterBaseTest(element);
    }

    @Test
    public void effectConvertToSVGElement() {
        SVGConvolveMatrixFilter.SVGConvolveMatrixFilterEffect effect = new SVGConvolveMatrixFilter.SVGConvolveMatrixFilterEffect(kernelMatrix);
        effect.setOrder(order);
        effect.setPreserveAlpha(preserveAlpha);
        effect.setEdgeMode(edgeMode);
        Element element = effect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        Assert.assertNotNull(element);
        Assert.assertEquals("feConvolveMatrix", element.getTagName());
        if (order != 3)
            Assert.assertEquals(order, Integer.parseInt(element.getAttribute("order")));
        else
            Assert.assertFalse(element.hasAttribute("order"));
        if (edgeMode.equals(SVGConvolveMatrixFilter.SVGConvolveMatrixFilterEffect.EdgeMode.EDGE_MODE_DUPLICATE))
            Assert.assertFalse(element.hasAttribute("edgeMode"));
        else
            Assert.assertEquals(edgeMode, element.getAttribute("edgeMode"));
        effectBaseTest(effect);

    }

}