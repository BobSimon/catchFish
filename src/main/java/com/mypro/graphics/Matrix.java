package com.mypro.graphics;

/**
 * @Author: Bob Simon
 * @Description:矩阵接口
 * @Date: Created in 10:55 2018\5\3
 */
public interface Matrix {

	/**
	 * 设置平移
	 */
	public void setTranslate(float x, float y);

	/**
	 * 重置矩阵
	 */
	public void reset();

	/**
	 * 缩放
	 */
	public void preScale(float x,float y);

	/**
	 * 旋转
	 */
	public void preRotate(float angle,float x,float y);
}
