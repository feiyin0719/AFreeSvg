# AFreeSvg

[![](https://jitpack.io/v/feiyin0719/AFreeSvg.svg)](https://jitpack.io/#feiyin0719/AFreeSvg)

[![Security Status](https://www.murphysec.com/platform3/v3/badge/1618010252381814784.svg?t=1)](https://www.murphysec.com/accept?code=5d39dc82da26158a1589316ba416c941&type=1&from=2&t=2)

一个可以在安卓上绘制svg图片的库
用法类似安卓canvas，通过SVGCanvas绘制图形，然后可以导出svg文件

## 使用方法
gradle添加jitpack仓库和依赖
```
 maven { url 'https://jitpack.io' }
 implementation 'com.github.feiyin0719:AFreeSvg:0.0.6'
```

## 参考代码
```java
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
            SVGLinearGradient svgLinearGradient = new SVGLinearGradient(new PointF(0, 0), new PointF(1, 0));
            svgLinearGradient.addStopColor(0, 0xffff0000);
            svgLinearGradient.addStopColor(0.5f, 0xff00ff00);
            svgLinearGradient.addStopColor(0.75f, 0xff00eeee);
            svgLinearGradient.addStopColor(1, 0xff0000ff);
            paint1.setGradient(svgLinearGradient);
            paint1.setStyle(Paint.Style.FILL_AND_STROKE);
            paint1.setStrokeWidth(2);
            paint1.setARGB(100, 200, 200, 0);

            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint1);
            svgCanvas.drawOval(new RectF(150, 150, 200, 200), paint1);
            SVGPaint paint2 = new SVGPaint();
            paint2.setStyle(Paint.Style.FILL);
            //放射渐变
            SVGRadialGradient gradient = new SVGRadialGradient(0.5f, 0.5f, 1, 0.8f, 0.8f);
            gradient.addStopColor(0.5f, 0xffff0000);
            gradient.addStopColor(1f, 0xff0000ff);
            paint2.setGradient(gradient);
            paint2.setFillRule(SVGPaint.FILL_RULE_EVENODD);
            //save功能
            svgCanvas.save();
            svgCanvas.translate(10, 10);
            //clipShape创建
            SVGShapeGroup clipGroup = new SVGShapeGroup();
            SVGPath clipPath = new SVGPath();
            clipPath.oval(0.2f, 0.2f, 0.2f, 0.2f);
            SVGPath clipPath1 = new SVGPath();
            clipPath1.oval(0.6f, 0.2f, 0.2f, 0.2f);
            clipGroup.addShape(clipPath);
            clipGroup.addShape(clipPath1);
            SVGClipShape clipShape = new SVGClipShape(clipGroup, POS_MODE.MODE_BOX);
            svgCanvas.save();
            //clipShape设置
            svgCanvas.clip(clipShape);
            svgCanvas.drawRect(new RectF(300, 300, 400, 450), paint2);
            svgCanvas.save();
            SVGClipShape clipShape1 = new SVGClipShape(clipPath, POS_MODE.MODE_BOX);
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
                    .setFontStyle(SVGFont.STYLE_ITALIC)
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
            svgCanvas.clip(new SVGClipShape(svgTextPath, POS_MODE.MODE_USERSPACE));
            svgCanvas.drawPath(textPath, paint2);
            svgCanvas.restore();
            svgCanvas.drawPath(textPath, paint);

            //绘制图片
            String url = "https://raw.githubusercontent.com/feiyin0719/AFreeSvg/dev/dog.jpg";
            svgCanvas.drawImage(url, 200, 250, 100, 100, null);
            SVGPath path1 = new SVGPath();
            path1.rect(200, 450, 100, 50);
            SVGTextPath svgTextPath1 = new SVGTextPath.Builder()
                    .setText("SVGDOG")
                    .setPath(path1)
                    .setPaint(textPaint)
                    .setTextLength(200)
                    .build();
            svgCanvas.save();
            svgCanvas.clip(new SVGClipShape(svgTextPath1, POS_MODE.MODE_USERSPACE));
            svgCanvas.drawImage(url, 200, 400, 100, 100, null);
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
```

生成图片如下
![](https://raw.githubusercontent.com/feiyin0719/AFreeSvg/dev/test.jpg)

## api介绍

- **SVGCanvas**

绘制canvas，使用方法类似安卓canvas。

构造函数
  public SVGCanvas(double width, double height, SVGUnits units, boolean compatibleWithAndroid, InputStream inputStream)
  
  1. width 宽度
  2. height 高度
  3. units 单位 可选
  4. compatibleWithAndroid 可选 默认为true 用于指示saveLayer()创建新图层时使用<g>标签还是<svg>标签 true为使用<g> false为<svg>
  5. inputStream 可选 一个现有的svg图片文件流, 若为合法svg图片文件流,会将现有svg图片内容填充至canvas,可用于修改已存在svg

图形绘制api 

      1. drawRect(RectF rectF, SVGPaint paint) //绘制矩形
      2. drawLine(float x1, float y1, float x2, float y2, SVGPaint paint) //绘制线段
      3. drawOval(RectF rectF, SVGPaint paint) //绘制椭圆或者圆
      4. drawPolygon(float[] points, SVGPaint paint) //绘制多边形
      5. drawPolyline(float points[], SVGPaint paint) //绘制多线段 
      6. public void drawArc(float x, float y, float width, float height, float startAngle,float arcAngle, SVGPaint paint) //绘制圆弧
      7. public void drawCurve(float sx, float sy, float ex, float ey, float x, float y, SVGPaint paint) //绘制贝塞尔曲线
      8. public void drawPath(SVGPath path, SVGPaint paint) //绘制普通path
      9. public void drawShape(SVGShape shape, SVGPaint paint) //绘制shape
      10. public void clip(SVGClipShape clipShape) //设置裁剪区域
      11. public void drawText(String text, float x, float y, SVGPaint paint)//绘制文本
      12. public void drawTextOnPath(String text, float x, float y, SVGPath path, SVGPaint paint) //绘制文本在path上
      13. public void drawImage(String uri, float x, float y, float width, float height, SVGPaint paint)//绘制图片
      14. public void drawCircle(float cx, float cy, float r, SVGPaint paint) //绘制圆形

transform clip saveLayer clear 操作api

      1. void clip(SVGClipShape shape) //设置clip区域，后续的绘制操作只会在clip区域上显示
      2. translate scale rotate skew //变换操作，使用方法和canvas一致
      3. save() save(int flags)  //和安卓canvas save restore一致，save后会保存当前canvas状态,flags用来指示保存什么信息，不填则全部保存，SAVE_FLAG_CLIP 只保存clip信息。SAVE_FLAG_MATRIX 只保存transform信息（transform和clip信息）
      4. saveLayer()会创建新图层,并保存当前图层状态 
      5. restore会回退到之前状态 与save saveLayer 对应
      6. clearLayer() 清空当前图层
      7. clear() 清空当前canvas

保存api

      1. getSVGXmlString() //获取svg string
      2. writeSVGXMLToStream(OutputStream outputStream) //保存svg

- **SVGPaint**

绘制画笔类，继承自安卓paint，主要用来添加一些安卓paint不支持的功能，以及解决无法获取渐变色和dash的问题

      1. setDashArray(float[] dashArray) //设置线段dash值
      2. setGradient(SVGGradient gradient) //设置渐变色
      3. setFillRule(String fillRule) //设置填充规则 值意义参考svg    nonzero / evenodd / inherit 默认nonzero
      4. setFillColor(long color)//单独设置fill color，可以实现strokecolor和fillcolor不一样，不设置的话默认使用同一个颜色
      5. setUseGradientStroke(bool useGradientStroke)//设置是否使用渐变色画线，当设置渐变填充时使用，默认为false
      6. setFont(SVGFont font)  setLengthAdjust(@LengthAdjust String lengthAdjust) setTextDecoration(@TextDecoration String textDecoration) setWordSpacing(float wordSpacing) //绘制文本时设置文本font以及相关信息

- **SVGGradient**

渐变色

      1. SVGLinearGradient  //线性渐变
      2. SVGRadialGradient //放射渐变

- **SVGShape**

      1. SVGPath //设置path路径
      2. SVGShapeGroup //shape组，可以同时绘制多个shape 对应于 svg的g
      3. SVGTextPath //文本path 
      4. SVGLine
      5. SVGRect
      6. SVGOval
      7. SVGPolygon
      8. SVGPolyline
  
- **SVGFilter**

 滤镜操作

      1. SVGColorFilter //通过matrix 对图像进行颜色进行变换
      2. SVGConvolveMatrixFilter //对图像进行卷机滤波
      3. SVGGaussianBlurFilter //对图像进行高斯模糊
      4. SVGOffsetFilter //对图像进行偏移滤镜
      5. SVGFilterGroup //组合多个滤镜效果
      
 **SVGBaseFilterEffect** 图像滤镜效果，可配合SVGFilterGroup 组合多个滤镜效果，参考代码
 
 ```java
 SVGFilterGroup filterGroup = new SVGFilterGroup();
 filterGroup.setFilterUnits(SVGModes.MODE_BOX);
 filterGroup.setX(-0.2f);
 filterGroup.setY(-0.2f);
 filterGroup.setWidth(1.5f);
 filterGroup.setHeight(1.5f);
 SVGOffsetFilter.SVGOffsetFilterEffect offsetFilterEffect = new SVGOffsetFilter.SVGOffsetFilterEffect(0.05f, 0.05f);
 offsetFilterEffect.setIn(SVGBaseFilter.ALPHA_VALUE);
 offsetFilterEffect.setResult("offset");
 SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect gaussianBlurFilterEffect = new SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect(3, 3);
 gaussianBlurFilterEffect.setIn(offsetFilterEffect.getResult());
 gaussianBlurFilterEffect.setResult("blur");
 SVGFilterGroup.SVGBlendFilterEffect blendFilterEffect = new SVGFilterGroup.SVGBlendFilterEffect();
 blendFilterEffect.setIn(SVGBaseFilter.GRAPHIC_VALUE);
 blendFilterEffect.setIn2(gaussianBlurFilterEffect.getResult());

 filterGroup.addEffect(offsetFilterEffect);
 filterGroup.addEffect(gaussianBlurFilterEffect);
 filterGroup.addEffect(blendFilterEffect);
 ```
 
     1. SVGColorFilterEffect //颜色滤镜效果类
     2. SVGConvolveMatrixFilterEffect //卷积滤镜效果
     3. SVGGaussianBlurFilterEffect //高斯模糊滤镜效果
     4. SVGOffsetFilterEffect  //偏移滤镜效果
     5. SVGBlendFilterEffect //混合滤镜效果 混合两个输入
     6. SVGMergeFilterEffect //可以同时应用多个滤镜效果
     7. SVGCompositeFilterEffect //组合滤镜效果 将两个输入以一定方式组合
      

- **SVGClipShape**

设置裁剪区域

    1.public SVGClipShape(SVGShape shape, @SVGModes.POS_MODE String posMode)//posMode设置坐标空间。MODE_BOX  相对绘制元素坐标。MODE_USERSPACE绝对坐标，即相对于画布的坐标


- **SVGFont**

  font信息，绘制文本时使用
  
## kotlin dsl支持（需0.0.3以上版本支持）

为了便捷创建 filter shape gradient类，基于kotlin实现dsl，[AFreeSvgKtx](https://github.com/feiyin0719/AFreeSvgKtx.git)，需引入
```
 implementation 'com.github.feiyin0719:AFreeSvgKtx:0.0.3'
```
参考代码如下

创建filter
```kotlin
fun createFilterGroup(): SVGFilterGroup {
    return filterGroup {
        filterUnits = PosMode.MODE_BOX
        x = -0.2f
        y = -0.2f
        width = 1.5f
        height = 1.5f
        offsetFilterNode {
            `in` = SVGBaseFilter.ALPHA_VALUE
            result = "offset"
            dx = 0.05f
            dy = 0.05f
        }
        gaussianFilterNode {
            `in` = "offset"
            result = "blur"
            stdDeviationX = 3f
            stdDeviationY = 3f
        }

        blendFilterNode {
            `in` = SVGBaseFilter.GRAPHIC_VALUE
            in2 = "blur"
        }
    }
}

```
创建 shape
```kotlin

fun createClipShape(): SVGClipShape {
    return clipShape {
        shape = shapeGroup {
            path {
                oval(0.2f, 0.2f, 0.2f, 0.2f)
            }
            path {
                oval(0.6f, 0.2f, 0.2f, 0.2f)
            }
        }
        posMode = PosMode.MODE_BOX
    }
}
```
创建gradient

```kotlin
fun createLinearGradient(): SVGLinearGradient {
    return linearGradient {
        startX(0f)
        startY(0f)
        endX(1f)
        endY(0f)
        color(0xffff0000, 0f)
        color(0xff00ff00, 0.5f)
        color(0xff00eeee, 0.75f)
        color(0xff0000ff, 1f)
    }
}
```

  
## 版本日志
 - **0.0.6**
 1. remove AFreeSvgKtx code to fix release issue
 - **0.0.5**
 1. 兼容低版本color设置
 - **0.0.4**
 1. 支持saveLayer clearLayer clear操作
 2. 支持基于现有svg图片创建功能
- **0.0.3**
 1. 支持kotlin dsl，可以更加简洁明了的创建filter shape gradient（需配合[AFreeSvgKtx](https://github.com/feiyin0719/AFreeSvgKtx.git)使用）
 2. 重构内部代码实现
 3. 修复部分bug

- **0.0.2**

 1. 新增滤镜效果支持
 2. 修复一些bug
 3. 新增单元测试用例
 4. 完善文档注释
 
- **0.0.1**

第一个正式版

 1. 支持线段、矩形、圆形、椭圆、文本、多边形、贝塞尔曲线、图片绘制操作
 2. 支持clip功能
 3. 支持变换操作
 4. 支持save restore
- **0.0.1-beta7**
 1. 增加基础shape rect line等
 2. 重构内部实现
- **0.0.1-beta6**
 1. 增加绘制图片能力
- **0.0.1-beta5**

 1. 增加绘制文本能力
 2. 增加字体类
- **0.0.1-beta4**
 1. 支持分别设置fillColor和strokeColor
 2. 增加渐变填充模式支持
 3. 增加绘制贝塞尔曲线 圆弧 path 支持
 4. 增加clip支持
 5. 增加save restore支持
 6. 重构部分代码实现 修复一些bug
- **0.0.1-beta3**

 1. 新增渐变色支持
 2. 新增绘制多边形、绘制多线段支持
 3. 重构paint实现，新增SVGPaint类，用来解决安卓自带Paint无法获取dash和渐变值
- **0.0.1-beta2**
 1. 解决描边和填充默认颜色问题
- **0.0.1-beta1**
 1. 支持线段、矩形、圆形、椭圆的绘制填充
 2. 支持transform

目前还处于开发阶段，后续会支持更多功能
