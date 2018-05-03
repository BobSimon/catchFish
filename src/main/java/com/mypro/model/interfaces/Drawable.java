package com.mypro.model.interfaces;

import com.mypro.graphics.Bitmap;
import com.mypro.graphics.Canvas;
import com.mypro.graphics.Matrix;
import com.mypro.graphics.Paint;

/**
 * @Author: Bob Simon
 * @Description: 移动接口
 * @Date: Created in 11:56 2018\5\3 0003
 */
public interface Drawable {

    Matrix getPicMatrix();//获取图片旋转的矩阵表示

    Bitmap getCurrentPic();//获取当前动作图片的资源

    int getPicWidth();//返回图片的宽度

    int getPicHeight();//返回图片的高度

    void onDraw(Canvas canvas, Paint paint);//绘制的回调方法
}