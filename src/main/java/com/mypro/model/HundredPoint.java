package com.mypro.model;

import com.mypro.graphics.Bitmap;

/**
 * @Author: Bob Simon
 * @Description:
 * @Date: Created in 11:25 2018\5\3
 */
public class HundredPoint extends DrawableAdapter{

	private Bitmap[] imgs;

	private int currentPicId;

	public HundredPoint(Bitmap[] imgs){
		this.imgs = imgs;
	}
	public int getActPicLength(){
		return imgs.length;
	}
	public void setCurrentPicId(int currentPicId){
		this.currentPicId = currentPicId;
	}
	@Override
	public Bitmap getCurrentPic() {
		// TODO Auto-generated method stub
		return imgs[currentPicId];
	}

	@Override
	public int getPicWidth() {
		// TODO Auto-generated method stub
		return getCurrentPic().getWidth();
	}

	@Override
	public int getPicHeight() {
		// TODO Auto-generated method stub
		return getCurrentPic().getHeight();
	}

}
