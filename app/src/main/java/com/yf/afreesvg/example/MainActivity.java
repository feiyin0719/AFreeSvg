package com.yf.afreesvg.example;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.yf.afreesvg.PosMode;
import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.SVGPaint;
import com.yf.afreesvg.filter.SVGFilterGroup;
import com.yf.afreesvg.font.SVGFont;
import com.yf.afreesvg.gradient.SVGLinearGradient;
import com.yf.afreesvg.gradient.SVGRadialGradient;
import com.yf.afreesvg.shape.SVGClipShape;
import com.yf.afreesvg.shape.SVGPath;
import com.yf.afreesvg.shape.SVGShapeGroup;
import com.yf.afreesvg.shape.SVGTextPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
            SVGPaint paint = new SVGPaint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setFillColor(0xff0000ff);
            paint.setDashArray(new float[]{5, 5, 10});
            paint.setColor(Color.RED);
            paint.setStrokeWidth(2);
            svgCanvas.drawLine(10, 10, 200, 200, paint);

            SVGPaint paint1 = new SVGPaint();
            //线性渐变
            SVGLinearGradient svgLinearGradient = SVGKtTestKt.createLinearGradient();
            paint1.setGradient(svgLinearGradient);
            paint1.setStyle(Paint.Style.FILL_AND_STROKE);
            paint1.setStrokeWidth(2);
            paint1.setARGB(100, 200, 200, 0);

            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint1);
            svgCanvas.drawOval(new RectF(150, 150, 200, 400), paint1);
            svgCanvas.drawCircle(240, 240, 50, paint1);
            SVGPaint paint2 = new SVGPaint();
            paint2.setStyle(Paint.Style.FILL);
            //放射渐变
            SVGRadialGradient gradient = new SVGRadialGradient(0.5f, 0.5f, 1, 0.8f, 0.8f);
            gradient.addStopColor(0.5f, 0xffff0000);
            gradient.addStopColor(1f, 0xff0000ff);
            paint2.setGradient(gradient);
            paint2.setFillRule(SVGPaint.FillRule.FILL_RULE_EVENODD);
            //save功能
            svgCanvas.save();
            svgCanvas.translate(10, 10);
            //clipShape创建
            SVGClipShape clipShape = SVGKtTestKt.createClipShape();
            svgCanvas.save();
            //clipShape设置
            svgCanvas.clip(clipShape);
            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint2);
            svgCanvas.save();
            SVGClipShape clipShape1 = SVGKtTestKt.createClipShape1();
            svgCanvas.clip(clipShape1);
            svgCanvas.restore();
            //绘制多条线段
            svgCanvas.drawPolyline(new float[]{20, 20, 40, 25, 60, 40, 80, 120, 120, 140, 200, 180}, paint);
            svgCanvas.restore();
            //绘制多边形
            svgCanvas.drawPolygon(new float[]{100, 10, 40, 198, 190, 78, 10, 78, 160, 198}, paint2);
            //创建path
            SVGPath svgPath = new SVGPath();
            svgPath.moveTo(200, 200);
            svgPath.oval(200, 200, 50, 50);

            svgPath.rect(100, 50, 50, 50);

            svgPath.moveTo(100, 300);
            svgPath.quadraticBelzierCurve(150, 250, 200, 400);
            //绘制path
            svgCanvas.drawPath(svgPath, paint);
            //绘制贝塞尔曲线
            svgCanvas.drawCurve(50, 50, 200, 50, 100, 25, paint);
            //绘制圆弧
            svgCanvas.drawArc(300, 100, 50, 50, 90, 270, paint);

            SVGPath path = new SVGPath();
            path.rect(20, 20, 100, 400);
            svgCanvas.restore();
            SVGShapeGroup group = new SVGShapeGroup();
            group.addShape(path);
            group.addShape(svgPath);
            //绘制shape
            svgCanvas.drawShape(group, paint);

            //绘制文字
            SVGPaint textPaint = new SVGPaint();
            textPaint.setStyle(Paint.Style.FILL);

            textPaint.setGradient(svgLinearGradient);
            textPaint.setFont(new SVGFont.Builder().setFontFamily("sans-serif")
                    .setFontStyle(SVGFont.FontStyle.STYLE_ITALIC)
                    .setFontWeight("bold")
                    .setFontSize(24)
                    .build());
            svgCanvas.drawText("hello world", 200, 20, textPaint, "");

            SVGPath textPath = new SVGPath();
            textPath.oval(100, 400, 100, 100);
//            svgCanvas.drawTextOnPath("hello", 0, 0, 0, 0, textPath, textPaint, null);
            svgCanvas.drawTextOnPath("world", 0, 0, 80, 0, textPath, textPaint, null);
            //设置文本clip
            svgCanvas.save();
            SVGTextPath svgTextPath = new SVGTextPath.Builder()
                    .setPath(textPath)
                    .setPaint(textPaint)
                    .setText("hello").build();
            svgCanvas.clip(new SVGClipShape(svgTextPath, PosMode.MODE_USERSPACE));
            svgCanvas.drawPath(textPath, paint2);
            svgCanvas.restore();
            svgCanvas.drawPath(textPath, paint);

            //绘制图片
            String url = "https://raw.githubusercontent.com/feiyin0719/AFreeSvg/dev/dog.jpg";
            SVGPaint imagePaint = new SVGPaint();
            SVGFilterGroup filterGroup = SVGKtTestKt.createFilterGroup();

            imagePaint.setFilter(filterGroup);

            paint1.setFilter(filterGroup);
            svgCanvas.drawOval(new RectF(300, 20, 380, 100), paint1);
            svgCanvas.drawImage(url, 200, 250, 100, 100, imagePaint);
            SVGPath path1 = new SVGPath();
            path1.rect(200, 450, 100, 50);
            SVGTextPath svgTextPath1 = new SVGTextPath.Builder()
                    .setText("SVGDOG")
                    .setPath(path1)
                    .setPaint(textPaint)
                    .setTextLength(200)
                    .build();
            svgCanvas.save();
            svgCanvas.clip(new SVGClipShape(svgTextPath1, PosMode.MODE_USERSPACE));
            svgCanvas.drawImage(url, 200, 400, 100, 100, paint);
            svgCanvas.restore();
            String s = svgCanvas.getSVGXmlString();
            Log.i("myyf", s);
            File file = new File(getExternalCacheDir(), "test.svg");
            svgCanvas.writeSVGXMLToStream(new FileOutputStream(file));


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}