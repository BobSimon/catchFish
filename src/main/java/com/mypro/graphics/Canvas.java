package com.mypro.graphics;

/**
 * @Author: Bob Simon
 * @Description:画板
 * @Date: Created in 10:52 2018\5\3
 */
public interface Canvas {

    /**
     *
     * @param bitmap
     * @param matrix
     * @param paint
     */
    void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint);

    /**
     * 
     * @param bitmap
     * @param x
     * @param y
     * @param paint
     */
    void drawBitmap(Bitmap bitmap, float x, float y, Paint paint);
}
