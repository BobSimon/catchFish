package com.mypro.graphics;


/**
 * @Author: Bob Simon
 * @Description:画笔属性
 * @Date: Created in 10:58 2018\5\3
 */

public interface Paint {

    /**
     * 设置Paint的字体
     */
    void setTypeface(Object obj);

    void setAntiAlias(boolean tf);

    void setFilterBitmap(boolean tf);

    void setDither(boolean tf);

    /**
     * 根据不同分辨率设置字体大小
     * @param size
     */
    void setTextSize(int size);

    void setColor(int color);
}
