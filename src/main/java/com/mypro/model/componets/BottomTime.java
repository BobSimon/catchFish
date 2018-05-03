package com.mypro.model.componets;

import java.util.HashMap;

import com.mypro.graphics.Bitmap;
import com.mypro.graphics.Canvas;
import com.mypro.graphics.Paint;
import com.mypro.manager.ImageManager;
import com.mypro.model.GamingInfo;
import com.mypro.tools.Log;

/**
 * 金币显示组件
 * @author Bob Simon
 *
 */
public class BottomTime extends Componet {
	private int[] num_index = new int[1];//所有数字的索引，这里第一个元素代表得分的最大位数，往后类推
	private Bitmap pic;
	private Bitmap[] num;
	private int numShowX, numShowY;//数字显示的X和Y坐标
	private int numPicWidth;     //数字宽度，所有数字宽度是一样的

	public BottomTime() {
		try {
			initNum();
			pic = ImageManager.getImageMnagaer().getscaleImageByScreenFromAssets("componet/bottom_time.png");
			numPicWidth = num[0].getWidth();
		} catch (Exception e) {
			Log.doLogForException(e);
		}

	}

	public void setPosition(int layoutX, int layoutY) {
		this.setLayoutX(layoutX);
		this.setLayoutY(layoutY);
		numShowX = layoutX + pic.getWidth() / 3;
		numShowY = layoutY + pic.getHeight() / 4;
		this.getPicMatrix().setTranslate(this.getLayoutX(), this.getLayoutY());
	}

	/**
	 * 初始化显示的数字
	 */
	private void initNum() {
		HashMap<String, Bitmap> allNum = ImageManager.getImageMnagaer().getImagesMapByImageConfig(ImageManager.getImageMnagaer().createImageConfigByPlist("componet/num_gold"), ImageManager.getImageMnagaer().scaleNum);
		//效果图全名(num_0.png)
		StringBuffer numFullName = new StringBuffer();
		String numName = "num_";
		num = new Bitmap[10];
		for (int num = 0; num < 10 && GamingInfo.getGamingInfo().isGaming(); num++) {
			numFullName.delete(0, numFullName.length());
			numFullName.append(numName + num + ".png");
			this.num[num] = allNum.get(numFullName.toString());
		}
	}

	@Override
	public void onDraw(Canvas canvas, Paint paint) {
		super.onDraw(canvas, paint);
		for (int i = 0; i < num_index.length; i++) {
			canvas.drawBitmap(num[num_index[i]], numShowX + (i * numPicWidth), numShowY, paint);
		}
	}

	/**
	 * 更新数字索引
	 */
	public void updateNumIndex(int time) {
		String num = time + "";
		num_index = new int[num.length()];
		int index = 0;
		for (char n : num.toCharArray()) {
			num_index[index] = n - 48;
			index++;
		}
	}

	@Override
	public Bitmap getCurrentPic() {
		// TODO Auto-generated method stub
		return pic;
	}

	@Override
	public int getPicWidth() {
		// TODO Auto-generated method stub
		return pic.getWidth();
	}

	@Override
	public int getPicHeight() {
		// TODO Auto-generated method stub
		return pic.getHeight();
	}

}