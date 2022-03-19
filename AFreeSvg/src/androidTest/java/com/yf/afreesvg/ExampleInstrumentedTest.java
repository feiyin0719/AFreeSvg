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

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.yf.afreesvg.shape.SVGPath;

import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void testSVGCanvs() {
        // Context of the app under test.
        SVGCanvas svgCanvas = null;
        try {
            svgCanvas = new SVGCanvas(500, 500);
            SVGPaint paint = new SVGPaint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(2);
            paint.setDashArray(new float[]{5, 5, 10});
            svgCanvas.drawLine(10, 10, 200, 200, paint);
            SVGPaint paint1 = new SVGPaint();
            paint1.setStyle(Paint.Style.FILL_AND_STROKE);
            paint1.setARGB(100, 200, 200, 0);
            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint1);
            svgCanvas.drawOval(new RectF(150, 150, 200, 200), paint1);
            Matrix matrix = new Matrix();
            matrix.postTranslate(10, 10);
            svgCanvas.setTransform(matrix);
            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint1);
            String s = svgCanvas.getSVGXmlString();
            Log.i("myyf", s);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pathTest() {
        SVGCanvas svgCanvas = null;
        try {
            svgCanvas = new SVGCanvas(500, 500);
            SVGPaint paint = new SVGPaint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setFillColor(0xff0000ff);
            paint.setDashArray(new float[]{5, 5, 10});
            paint.setColor(Color.RED);
            paint.setStrokeWidth(2);
            svgCanvas.drawLine(10, 10, 200, 200, paint);

            SVGPath svgPath = new SVGPath();
            svgPath.moveTo(200, 200);
            svgPath.oval(200, 200, 50, 50);

            svgPath.rect(100, 50, 50, 50);

            svgPath.moveTo(100, 300);
            svgPath.quadraticBelzierCurve(150, 250, 200, 400);
            svgCanvas.drawPath(svgPath, paint);
            String s = svgCanvas.getSVGXmlString();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}