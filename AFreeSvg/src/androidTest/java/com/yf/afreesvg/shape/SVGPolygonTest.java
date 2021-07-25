package com.yf.afreesvg.shape;

import android.graphics.PointF;

import com.yf.afreesvg.util.DoubleFunction;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SVGPolygonTest extends SVGShapeBaseTest {
    private PointF[] points;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new PointF[]{new PointF(0, 0), new PointF(0, 1), new PointF(1, 1)}},
                {new PointF[]{new PointF(0, 0), new PointF(0, 1), new PointF(1, 1), new PointF(1, 0)}},
                {new PointF[]{new PointF(0, 0), new PointF(0, 1), new PointF(1, 1), new PointF(1, 0)}},
                {new PointF[]{new PointF(0, 0), new PointF(0, 1), new PointF(1, 1), new PointF(1, 0), new PointF(2, 0)}}
        });
    }

    public SVGPolygonTest(PointF[] points) {
        this.points = points;
    }

    public String getPointStr(DoubleFunction convert) {
        StringBuilder sb = new StringBuilder();
        if (points.length > 0) {
            for (int i = 0; i < points.length; ++i)
                sb.append(" " + convert.apply(points[i].x) + "," + convert.apply(points[i].y));
        }
        return sb.toString();
    }

    @Test
    public void convertToSVGElement() {
        SVGPolygon polygon = new SVGPolygon(points);
        Assert.assertArrayEquals(points, polygon.getPoints());
        Element element = polygon.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        Assert.assertEquals("polygon", element.getTagName());
        String str = element.getAttribute("points");

        Assert.assertNotNull(str);
        Assert.assertEquals(getPointStr(canvas.getGeomDoubleConverter()), str);

    }
}