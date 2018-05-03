package com.mypro.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @Author: Bob Simon
 * @Description:
 * @Date: Created in 13:06 2018\5\3
 */
public class Bitmap implements Serializable {
    
private BufferedImage image;

public Bitmap(BufferedImage image) {
    this.image = image;
}

public Image getImage() {
    return image;
}

    /**
     * 获取宽度
     * @return
     */
    public int getWidth() {
    // TODO Auto-generated method stub
    return image.getWidth(null);
}

/**
 * ��ȡͼƬ�߶�
 *
 * @return
 */
public int getHeight() {
    // TODO Auto-generated method stub
    return image.getHeight(null);
}

/**
 * ����ͼƬ
 *
 * @param src
 * @param width
 * @param height
 * @param tf
 */
public static Bitmap createScaledBitmap(Bitmap src, int width, int height, boolean tf) {
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics g = img.getGraphics();
    g.drawImage(src.image, 0, 0, width, height, 0, 0, src.getWidth(), src.getHeight(), null);
    return new Bitmap(img);
}

/**
 * ����ͼƬ
 *
 * @param src
 * @return
 */
public static Bitmap createBitmap(Bitmap src) {
    return src.copy();
}

public Bitmap copy() {
    BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
    Graphics g = image.getGraphics();
    g.drawImage(this.image, 0, 0, null);
    return new Bitmap(image);
}

/**
 * ����������ɫ
 *
 * @param x
 * @param y
 * @return
 */
  public int getPixel(int x, int y) {
    return image.getRGB(x, y);
  }

 /**
  * ����������ɫ
  *
  * @param x
  * @param y
  *  @param color
  */
 public void setPixel(int x, int y, int color) {
    image.setRGB(x, y, color);
  }
}



