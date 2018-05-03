package com.mypro.model.componets;

import com.mypro.graphics.Bitmap;
import com.mypro.manager.ImageManager;
import com.mypro.model.GamingInfo;
import com.mypro.tools.Log;

/**
 * ���ڵ���
 * @author Xiloerfan
 *
 */
public class Bottom extends Componet{
	private Bitmap pic;
	public Bottom(){
		try{
		pic = ImageManager.getImageMnagaer().getscaleImageByScreenFromAssets("componet/bottom.png");
		this.setLayoutX(GamingInfo.getGamingInfo().getScreenWidth()/2-getPicWidth()/2);
		this.setLayoutY(GamingInfo.getGamingInfo().getScreenHeight()-getPicHeight());
		this.getPicMatrix().setTranslate(this.getLayoutX(),this.getLayoutY());
		}catch(Exception e){
			Log.e("Bottom", e.toString());
		}
	}

	@Override
	public Bitmap getCurrentPic() {
		return pic;
	}

	@Override
	public int getPicWidth() {
		return pic.getWidth();
	}

	@Override
	public int getPicHeight() {
		return pic.getHeight();
	}

}
