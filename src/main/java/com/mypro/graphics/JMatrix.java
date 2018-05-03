package com.mypro.graphics;

import java.awt.geom.AffineTransform;

/**
 * @Author: Bob Simon
 * @Description:矩阵，用于实现图片线性变换
 * @Date: Created in 10:53 2018\5\3
 */
public class JMatrix implements Matrix {
    public AffineTransform trans = new AffineTransform();

    @Override
    public void reset() {
    }

    @Override
    public void setTranslate(float x, float y) {
        trans.setToTranslation(x, y);
    }

    @Override
    public void preScale(float x, float y) {
        trans.scale(x, y);

    }

    @Override
    public void preRotate(float angle, float x, float y) {
        trans.rotate(Math.toRadians(angle), x, y);
    }

}
