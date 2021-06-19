package com.yf.afreesvg.example;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.yf.afreesvg.SVGCanvas;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SVGCanvas svgCanvas = null;
        try {
            svgCanvas = new SVGCanvas(500, 500);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(2);
            svgCanvas.drawLine(10, 10, 200, 200, paint, new float[]{5, 5, 10});
            Paint paint1 = new Paint();
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
}