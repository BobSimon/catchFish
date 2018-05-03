package com.mypro.model;


import com.mypro.graphics.Bitmap;

/**
 * @Author: Bob Simon
 * @Description:
 * @Date:Created in 2018-05-03 11:40
 * @Modified By:
 **/
public class BackGround extends DrawableAdapter {

    private Bitmap background;

    public void setCurrentPic(Bitmap background) {
        this.background = background;
    }

    @Override
    public Bitmap getCurrentPic() {
        return background;
    }

    @Override
    public int getPicWidth() {
        return background.getWidth();
    }

    @Override
    public int getPicHeight() {
        return background.getHeight();
    }

}