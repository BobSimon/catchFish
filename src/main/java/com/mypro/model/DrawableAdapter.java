package com.mypro.model;

import com.mypro.graphics.Canvas;
import com.mypro.graphics.Matrix;
import com.mypro.graphics.Paint;
import com.mypro.graphics.JMatrix;
import com.mypro.model.interfaces.Drawable;

/**
 * @Author: Bob Simon
 * @Description:移动适配器
 * @Date: Created in 11:31 2018\5\3
 */
public abstract class DrawableAdapter implements Drawable{

	private Matrix matrix = new JMatrix();

	@Override
	public Matrix getPicMatrix() {
		// TODO Auto-generated method stub
		return matrix;
	}

	@Override
	public void onDraw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(this.getCurrentPic(),
				this.getPicMatrix(), paint);		
	}

	
}
