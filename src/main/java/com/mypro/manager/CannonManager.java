package com.mypro.manager;

import java.util.ArrayList;
import java.util.HashMap;

import com.mypro.graphics.Bitmap;
import com.mypro.main.Constant;
import com.mypro.model.Ammo;
import com.mypro.model.FishingNet;
import com.mypro.model.GamingInfo;
import com.mypro.model.WaterRipple;
import com.mypro.model.componets.Cannon;
import com.mypro.model.componets.ChangeCannonEffect;
import com.mypro.threads.ShotThread;
import com.mypro.tools.Log;
import com.mypro.tools.Tool;

/**
 * @Author: Bob Simon
 * @Description: 大炮管理器
 * @Date: Created in 11:44 2018\5\3
 */
public class CannonManager {
    /**
     * 是否可以更换大炮
     */
    private boolean canChangeCannon = true;
    /**
     * 所有子弹
     * key:大炮质量ID，value:子弹图片数组
     */
    private HashMap<Integer, Bitmap[]> bullet = new HashMap<Integer, Bitmap[]>();
    /**
     * 所有大炮
     * key:大炮质量ID，value:大炮图片数组
     */
    private HashMap<Integer, Cannon> cannon = new HashMap<Integer, Cannon>();
    /**
     * 所有渔网图片
     */
    private Bitmap[] net;
    /**
     * 水波纹下效果图片
     */
    private Bitmap[] waterRipple;
    /**
     * 变换大炮的效果图
     */
    private Bitmap[] changeCannonEffect;
    /**
     * 激光炮
     */
    private Bitmap[] laser;
    /**
     * 是否可以发射炮弹
     */
    private boolean shotable;
    /**
     * 当前使用的大炮ID
     */
    private int currentCannonIndex = 1;
    private static CannonManager cannonManager;


    private CannonManager() {

    }

    /**
     * 初始化大炮管理器
     */
    public void init() {
        try {
            //获取配置文件指定的所有图片
            HashMap<String, Bitmap> allImage = ImageManager.getImageMnagaer().getImagesMapByImageConfig(ImageManager.getImageMnagaer().createImageConfigByPlist("cannon/bulletandnet"), ImageManager.getImageMnagaer().scaleNum);
            allImage.putAll(ImageManager.getImageMnagaer().getImagesMapByImageConfig(ImageManager.getImageMnagaer().createImageConfigByPlist("cannon/fire"), ImageManager.getImageMnagaer().scaleNum));
            //初始化金币数字
            initGoldNum(allImage);
            //初始化子弹
            initAmmo(allImage);
            //初始化渔网
            initNet(allImage);
            //初始化水波纹
            initWaterRipple(ImageManager.getImageMnagaer().getImagesMapByImageConfig(ImageManager.getImageMnagaer().createImageConfigByPlist("cannon/ripple"), ImageManager.getImageMnagaer().scaleNum));
            //初始化大炮
            initCannon(allImage);
            //初始化激光，目前没有实现

            //初始化更换大炮时的效果
            initChangeCannonEffect();

        } catch (Exception e) {
            Log.doLogForException(e);
        }
    }

    /**
     * 初始化金币数字
     * 写在这里是因为这个数字所在的图片是网子和子弹的资源图中，当时欠考虑这个问题了
     *
     * @param allImage
     */
    private void initGoldNum(HashMap<String, Bitmap> allImage) {
        //渔网的图全名(num_9.png)
        StringBuffer numFullName = new StringBuffer();
        //定义名字编号
        int num = 0;
        String numName = "num_";
        ArrayList<Bitmap> allNumList = new ArrayList<Bitmap>();
        //获取当前子弹的所有动作
        while (GamingInfo.getGamingInfo().isGaming()) {
            numFullName.delete(0, numFullName.length());
            numFullName.append(numName + num + ".png");
            Bitmap numImg = allImage.get(numFullName.toString());
            //如果没有解析到内容了
            if (numImg == null) {
                break;
            }
            allNumList.add(numImg);
            num++;
        }
        allNumList.add(allImage.get("num_x.png"));
        //将集合转换为数组
        Bitmap[] imgs = new Bitmap[allNumList.size()];
        for (int i = 0; i < allNumList.size(); i++) {
            imgs[i] = allNumList.get(i);
        }
        ScoreManager.getScoreManager().setGoldNum(imgs);
    }

    /**
     * 初始化更换大炮的效果图
     */
    private void initChangeCannonEffect() {
        HashMap<String, Bitmap> allEffect = ImageManager.getImageMnagaer().getImagesMapByImageConfig(ImageManager.getImageMnagaer().createImageConfigByPlist("cannon/changefire"), ImageManager.getImageMnagaer().scaleNum);
        //效果图全名(paolizi_08.png)
        StringBuffer effectFullName = new StringBuffer();
        //定义名字编号
        int effectNum = 1;
        String effectName = "paolizi";
        ArrayList<Bitmap> allEffectList = new ArrayList<Bitmap>();
        while (GamingInfo.getGamingInfo().isGaming()) {
            effectFullName.delete(0, effectFullName.length());
            if (effectNum < 10) {
                effectFullName.append(effectName + "_0" + effectNum + ".png");
            } else {
                effectFullName.append(effectName + "_" + effectNum + ".png");
            }
            Bitmap effect = allEffect.get(effectFullName.toString());
            if (effect == null) {
                break;
            }
            allEffectList.add(effect);
            effectNum++;
        }
        //将集合转换为数组
        changeCannonEffect = new Bitmap[allEffectList.size()];
        for (int i = 0; i < allEffectList.size(); i++) {
            changeCannonEffect[i] = allEffectList.get(i);
        }
    }

    /**
     * 初始化所有大炮图片
     *
     * @param allImage
     */
    private void initCannon(HashMap<String, Bitmap> allImage) {
        //大炮的图全名(net_11.png)
        StringBuffer cannonFullName = new StringBuffer();
        //定义名字编号,子名称编号
        int cannonNum = 1, subCannonNum = 1;
        String cannonName = "net";
        ArrayList<Bitmap> allCannonList = new ArrayList<Bitmap>();
        //获取当前子弹的所有动作
        while (GamingInfo.getGamingInfo().isGaming()) {
            allCannonList.clear();
            subCannonNum = 1;
            cannonFullName.delete(0, cannonFullName.length());
            cannonFullName.append(cannonName + "_" + cannonNum);
            while (GamingInfo.getGamingInfo().isGaming()) {
                Bitmap cannon = allImage.get(cannonFullName.toString() + subCannonNum + ".png");
                if (cannon == null) {
                    break;
                }
                allCannonList.add(cannon);
                subCannonNum++;
            }
            //如果没有解析到内容了
            if (allCannonList.size() == 0) {
                break;
            }
            //将集合转换为数组
            Bitmap[] cannons = new Bitmap[allCannonList.size()];
            for (int i = 0; i < allCannonList.size(); i++) {
                cannons[i] = allCannonList.get(i);
            }
            //将大炮放入管理器中
            Cannon cannon_obj = new Cannon(cannons);
            cannon_obj.init();
            cannon.put(cannonNum, cannon_obj);
            cannonNum++;
        }
    }

    /**
     * 初始化大炮
     */
    public void initCannon() {
        setShotable(false);
        currentCannonIndex = 1;
        resetCannonMatrix(getCannon(currentCannonIndex));
        LayoutManager.getLayoutManager().initCannon(getCannon(currentCannonIndex));
        setShotable(true);
    }

    /**
     * 初始化渔网
     */
    private void initNet(HashMap<String, Bitmap> allImage) {
        //渔网的图全名(net011.png)
        StringBuffer netFullName = new StringBuffer();
        //定义名字编号
        int netNum = 1;
        String netName = "net";
        ArrayList<Bitmap> allNetList = new ArrayList<Bitmap>();
        //获取当前子弹的所有动作
        while (GamingInfo.getGamingInfo().isGaming()) {
            netFullName.delete(0, netFullName.length());
            netFullName.append(netName + "0" + netNum + ".png");
            Bitmap net = allImage.get(netFullName.toString());
            //如果没有解析到内容了
            if (net == null) {
                break;
            }
            allNetList.add(net);
            netNum++;
        }
        //将集合转换为数组
        net = new Bitmap[allNetList.size()];
        for (int i = 0; i < allNetList.size(); i++) {
            net[i] = allNetList.get(i);
        }
    }

    /**
     * 初始化渔网
     */
    private void initWaterRipple(HashMap<String, Bitmap> allImage) {
        //渔网的图全名(water_11.png)
        StringBuffer rippleFullName = new StringBuffer();
        //定义名字编号
        int rippleNum = 1;
        String rippleName = "water_";
        ArrayList<Bitmap> allRippleList = new ArrayList<Bitmap>();
        //获取当前子弹的所有动作
        while (GamingInfo.getGamingInfo().isGaming()) {
            rippleFullName.delete(0, rippleFullName.length());
            rippleFullName.append(rippleName + rippleNum + ".png");
            Bitmap ripple = allImage.get(rippleFullName.toString());
            //如果没有解析到内容了
            if (ripple == null) {
                break;
            }
            allRippleList.add(ripple);
            rippleNum++;
        }
        //将集合转换为数组
        waterRipple = new Bitmap[allRippleList.size()];
        for (int i = 0; i < allRippleList.size(); i++) {
            waterRipple[i] = allRippleList.get(i);
        }
    }

    /**
     * 初始化所有子弹图片
     */
    private void initAmmo(HashMap<String, Bitmap> allImage) {
        //子弹的图全名(bullet12.png),子弹子名(bullet12_01.png)
        StringBuffer ammoFullName = new StringBuffer();
        StringBuffer subAmmoFullName = new StringBuffer();
        //定义名字编号,子名称编号
        int ammoNum = 1, subAmmoNum = 1;
        String ammoName = "bullet";
        ArrayList<Bitmap> allAmmoList = new ArrayList<Bitmap>();
        //获取当前子弹的所有动作
        while (GamingInfo.getGamingInfo().isGaming()) {
            allAmmoList.clear();
            ammoFullName.delete(0, ammoFullName.length());
            ammoFullName.append(ammoName + "0" + ammoNum + ".png");
            //定义一个用于创建图片的引用
            Bitmap ammo = allImage.get(ammoFullName.toString());
            //如果图片没有找到，退出循环
            if (ammo == null) {
                break;
            }
            allAmmoList.add(ammo);
            subAmmoNum = 1;
            //试图尝试看看有没有同名的子图片
            //这里-4是去掉.png这个几个字符，再继续拼写子名称
            ammoFullName.delete(ammoFullName.length() - 4, ammoFullName.length());
            while (GamingInfo.getGamingInfo().isGaming()) {
                subAmmoFullName.delete(0, subAmmoFullName.length());
                subAmmoFullName.append(ammoFullName.toString() + "_" + subAmmoNum + ".png");
                Bitmap subAmmo = allImage.get(subAmmoFullName.toString());
                if (subAmmo == null) {
                    break;
                }
                allAmmoList.add(subAmmo);
                subAmmoNum++;
            }
            //将集合转换为数组
            Bitmap[] bullets = new Bitmap[allAmmoList.size()];
            for (int i = 0; i < allAmmoList.size(); i++) {
                bullets[i] = allAmmoList.get(i);
            }
            //将子弹放入管理器中
            bullet.put(ammoNum, bullets);
            ammoNum++;
        }
    }

    public static CannonManager getCannonManager() {
        if (cannonManager == null) {
            cannonManager = new CannonManager();
        }
        return cannonManager;
    }

    /**
     * 根据给定大炮ID获取发射的对应子弹的实例
     *
     * @param id
     * @return
     */
    private Ammo getAmmo(int id) {
        Ammo ammo = new Ammo(id);
        ammo.setCurrentPic(this.bullet.get(id), new FishingNet(this.net[id - 1], ammo));
        return ammo;
    }

    /**
     * 根据给定大炮ID获取大炮的实例
     *
     * @param id
     * @return
     */
    private Cannon getCannon(int id) {
        return this.cannon.get(id);

    }

    /**
     * 提高大炮等级
     */
    public void upCannon() {
        if (!canChangeCannon) {
            return;
        }
        canChangeCannon = false;//不许更换大炮
        setShotable(false);
        if (currentCannonIndex + 1 > cannon.size()) {
            currentCannonIndex = 1;
        } else {
            currentCannonIndex++;
        }
        resetCannonMatrix(getCannon(currentCannonIndex));
        playChangeCannonEffect();
        //播放更换大炮的音效
        SoundManager.playSound(SoundManager.SOUND_BGM_CHANGE_CANNON);
        LayoutManager.getLayoutManager().updateCannon(getCannon(currentCannonIndex));
        canChangeCannon = true;
        setShotable(true);
    }

    /**
     * 降低大炮等级
     */
    public void downCannon() {
        if (!canChangeCannon) {
            return;
        }
        canChangeCannon = false;//不许更换大炮
        setShotable(false);
        if (currentCannonIndex - 1 == 0) {
            currentCannonIndex = cannon.size();
        } else {
            currentCannonIndex--;
        }
        resetCannonMatrix(getCannon(currentCannonIndex));
        playChangeCannonEffect();
        //播放更换大炮的音效
        SoundManager.playSound(SoundManager.SOUND_BGM_CHANGE_CANNON);
        LayoutManager.getLayoutManager().updateCannon(getCannon(currentCannonIndex));
        canChangeCannon = true;
        setShotable(true);
    }

    /**
     * 播放大炮转换效果
     */
    private void playChangeCannonEffect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                ChangeCannonEffect effect = new ChangeCannonEffect(changeCannonEffect);
                effect.playEffect();
            }
        }).start();

    }

    /**
     * 射击子弹
     *
     * @param targetX 目标点X坐标
     * @param targetY 目标点y坐标
     */
    public void shot(float targetX, float targetY) {
        if (shotable) {
            //播放水波纹效果
            playRipple(targetX, targetY);
            if (GamingInfo.getGamingInfo().getScore() >= currentCannonIndex) {
                waitReload();
                GamingInfo.getGamingInfo().setScore(GamingInfo.getGamingInfo().getScore() - currentCannonIndex);
                //开炮的声音
                SoundManager.playSound(SoundManager.SOUND_BGM_FIRE);
                this.rotateCannon(targetX, targetY, getCannon(currentCannonIndex));
                //播放大炮发射效果
                getCannon(currentCannonIndex).shot();
                //发射炮弹
                Ammo ammo = getAmmo(currentCannonIndex);
                ShotThread st = new ShotThread(targetX - ammo.getPicWidth() / 2, targetY - ammo.getPicHeight() / 2, ammo, GamingInfo.getGamingInfo().getCannonLayoutX() - ammo.getPicWidth() / 2, GamingInfo.getGamingInfo().getCannonLayoutY() - ammo.getPicHeight() / 2);
                st.start();
            } else {
                //没有金币的声音
                SoundManager.playSound(SoundManager.SOUND_BGM_NO_GOLD);

            }

        }
    }

    /**
     * 上弹时间
     */
    private void waitReload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    shotable = false;
                    Thread.sleep(Constant.CANNON_RELOAD_TIME);
                    shotable = true;
                } catch (Exception e) {
                    Log.doLogForException(e);
                }

            }
        }).start();


    }

    /**
     * 播放水波纹效果
     *
     * @param targetX
     * @param targetY
     */
    private void playRipple(final float targetX, final float targetY) {
        new Thread(new Runnable() {
            public void run() {
                WaterRipple wr = new WaterRipple(waterRipple);
                wr.getPicMatrix().setTranslate(targetX - wr.getPicWidth() / 2, targetY - wr.getPicHeight() / 2);
                GamingInfo.getGamingInfo().getSurface().putDrawablePic(Constant.HUNDRED_WATER_RIPPLE_LAYER, wr);
                for (int i = 0; i < waterRipple.length; i++) {
                    wr.setCurrentId(i);
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        Log.doLogForException(e);
                    }
                }
                GamingInfo.getGamingInfo().getSurface().removeDrawablePic(Constant.HUNDRED_WATER_RIPPLE_LAYER, wr);
            }
        }).start();
    }

    /**
     * 旋转大炮
     */
    private void rotateCannon(float targetX, float targetY, Cannon cannon) {
        try {
            //获取大炮需要旋转的角度
            float gun_angle = Tool.getAngle(targetX, targetY, GamingInfo.getGamingInfo().getScreenWidth() / 2, GamingInfo.getGamingInfo().getScreenHeight());
            cannon.getPicMatrix().reset();
            cannon.getPicMatrix().setTranslate(cannon.getX(), cannon.getY());
            //大炮旋转的算法
            if (gun_angle >= 90) {
                cannon.getPicMatrix().preRotate(90 - gun_angle, cannon.getGun_rotate_point_x(), cannon.getGun_rotate_point_y());
            } else {
                cannon.getPicMatrix().preRotate(-(gun_angle - 90), cannon.getGun_rotate_point_x(), cannon.getGun_rotate_point_y());
            }
        } catch (Exception e) {
            Log.doLogForException(e);
        }
    }

    /**
     * 恢复大炮的初始状态
     */
    private void resetCannonMatrix(Cannon cannon) {
        rotateCannon(GamingInfo.getGamingInfo().getScreenWidth() / 2, 0, cannon);
    }

    /**
     * 设置是否允许发射大炮
     *
     * @param shotable true:允许 false:不允许
     */
    public void setShotable(boolean shotable) {
        this.shotable = shotable;
    }
}