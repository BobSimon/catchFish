package com.mypro.model;

import com.mypro.graphics.Bitmap;
import com.mypro.main.Constant;
import com.mypro.manager.ParticleEffectManager;
import com.mypro.tools.Log;

/**
 * @Author: Bob Simon
 * @Description:渔网
 * @Date: Created in 11:28 2018\5\3
 */
public class FishingNet extends DrawableAdapter{
	private Bitmap net;
	private Ammo ammo;//对应的子弹
	public FishingNet(Bitmap net,Ammo ammo){
		this.net = net;
		this.ammo = ammo;
	}
	@Override
	public Bitmap getCurrentPic() {
		// TODO Auto-generated method stub
		return net;
	}

	@Override
	public int getPicWidth() {
		// TODO Auto-generated method stub
		return net.getWidth();
	}

	@Override
	public int getPicHeight() {
		// TODO Auto-generated method stub
		return net.getHeight();
	}
	/**
	 * 播放渔网动画
	 */
	public void playNetAct(float netX,float netY){
		try {
			float scale = 0.2f;
			float picW = this.getPicWidth()/2,picH = this.getPicHeight()/2;
			this.getPicMatrix().setTranslate(netX-picW*scale, netY-picH*scale);
			this.getPicMatrix().preScale(scale, scale);
			//将渔网放置到绘图层中等待被绘制
			GamingInfo.getGamingInfo().getSurface().putDrawablePic(Constant.FISH_NET_LAYER, this);
			Thread.sleep(25);
			for(int i = 2;i<=6;i++){
				scale = i*0.2f;
				this.getPicMatrix().setTranslate(netX-picW*scale, netY-picH*scale);
				this.getPicMatrix().preScale(scale, scale);
				Thread.sleep(25);
			}
			this.getPicMatrix().setTranslate(netX-picW*1.25f,netY-picH*1.25f);
			this.getPicMatrix().preScale(1.25f, 1.25f);
			Thread.sleep(25);
			this.getPicMatrix().setTranslate(netX-picW*1.10f,netY-picH*1.10f);
			this.getPicMatrix().preScale(1.10f, 1.10f);
			Thread.sleep(25);
			this.getPicMatrix().setTranslate(netX-picW,netY-picH);
			NetParticleEffect npe = ParticleEffectManager.getParticleEffectManager().getNetEffect();
			//播放渔网粒子
			npe.playEffect(netX, netY,ammo.getAmmoQuality());
			//渔网显示时间
			Thread.sleep(Constant.FISHING_NET_SHOW_TINE);
			npe.stopEffect();
			//将渔网清出绘图层
			GamingInfo.getGamingInfo().getSurface()
					.removeDrawablePic(Constant.FISH_NET_LAYER, this);
		} catch (Exception e) {
			Log.doLogForException(e);
		}


	}
}