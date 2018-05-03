package com.mypro.tools;

/**
 * @Author: Bob Simon
 * @Description: 圆与矩形碰撞检测类
 * @Date: Created in 11:11 2018\5\3
 */
public class CircleRectangleIntersect {

    /**
     * @param x 圆心x
     * @param y 圆心y
     * @param X 矩形中心X
     * @param Y 矩形中心Y
     * @param c 矩形高度
     * @param k 矩形宽度
     * @param r 圆半径 void 2010-6-18 author mayankai
     */

// 上下左右四边，此函数似乎有点问题
    private static boolean sxzy(double x, double y, double X, double Y, double c,
                                double k, double r) {
        double xX = x - X;
        double yY = y - Y;
        if (((Math.abs(xX) <= k / 2 + r) && (Math.abs(yY) <= c / 2))
                || (Math.abs(xX) <= k / 2 && (y >= Y + c / 2 && y <= Y + c / 2 + r))
                || (Math.abs(xX) <= k / 2 && (y >= Y - c / 2 - r && y <= Y - c / 2))) {
            //System.out.println("符合上下左右四边");
            return true;
        } else {
            //System.out.println("不符合上下左右四边");
            return false;
        }
    }

    // 左上角
    private static boolean zsj(double x, double y, double X, double Y, double c,
                               double k, double r) {
        if ((x >= X - k / 2 - r && x <= X - k / 2)
                && (y >= Y - c / 2 - r && y <= Y - c / 2)
                && zxjl(x, y, X, Y, c, k, r)) {
            //System.out.println("符合左上角");
            return true;
        } else {
            //System.out.println("不符合左上角");
            return false;
        }
    }

    // 右上角
    private static boolean ysj(double x, double y, double X, double Y, double c,
                               double k, double r) {
        if ((x >= X + k / 2 && x <= X + k / 2 + r)
                && (y >= Y - c / 2 - r && y <= Y - c / 2)
                && zxjl(x, y, X, Y, c, k, r)) {
            //System.out.println("符合右上角");
            return true;
        } else {
            //System.out.println("不符合右上角");
            return false;
        }
    }

    // 左下角
    private static boolean zxj(double x, double y, double X, double Y, double c,
                               double k, double r) {
        if ((x >= X - k / 2 - r && x <= X - k / 2)
                && (y >= Y + c / 2 && y <= Y + c / 2 + r)
                && zxjl(x, y, X, Y, c, k, r)) {
            //System.out.println("符合左下角");
            return true;
        } else {
            //System.out.println("不符合左下角");
            return false;
        }
    }

    // 右下角
    private static boolean yxj(double x, double y, double X, double Y, double c,
                               double k, double r) {
        if ((x >= X + k / 2 && x <= X + k / 2 + r)
                && (y >= Y + c / 2 && y <= Y + c / 2 + r)
                && zxjl(x, y, X, Y, c, k, r)) {
            //System.out.println("符合右下角");
            return true;
        } else {
            //System.out.println("不符合右下角");
            return false;
        }
    }

    // 圆心和矩形对角线相交点(中心距离)
    private static boolean zxjl(double x, double y, double X, double Y, double c,
                                double k, double r) {
        double zx = X - k / 2;// 左x
        double yx = X + k / 2;// 右x
        double sy = Y - c / 2;// 上y
        double xy = Y + c / 2;// 下y
        //System.out.println("左上角：" + zx + ":" + sy);
        //System.out.println("左下角：" + zx + ":" + xy);
        //System.out.println("右上角：" + yx + ":" + sy);
        //System.out.println("右下角：" + yx + ":" + xy);

        if ((Math.pow((x - zx), 2) + Math.pow((y - sy), 2) <= Math.pow(r, 2))
                || (Math.pow((x - yx), 2) + Math.pow((y - sy), 2) <= Math.pow(
                r, 2))
                || (Math.pow((x - zx), 2) + Math.pow((y - xy), 2) <= Math.pow(
                r, 2))
                || (Math.pow((x - yx), 2) + Math.pow((y - xy), 2) <= Math.pow(
                r, 2))) {
            //System.out.println("符合中心距");
            return true;
        } else {
            //System.out.println("不符合中心距");
            return false;
        }
    }

    /**
     * 判断圆与矩形是否相交
     *
     * @param x 圆心x
     * @param y 圆心y
     * @param X 矩形中心X
     * @param Y 矩形中心Y
     * @param c 矩形高度
     * @param k 矩形宽度
     * @param r 圆半径
     * @return true:相交 false:不相交
     */
    public static boolean isIntersect(double x,double y,double X,double Y,double c,double k,double r) {
        if (sxzy(x, y, X, Y, c, k, r) || zsj(x, y, X, Y, c, k, r)
                || ysj(x, y, X, Y, c, k, r) || zxj(x, y, X, Y, c, k, r)
                || yxj(x, y, X, Y, c, k, r)) {
            //System.out.println("相交");
            return true;
        } else {
            //System.out.println("不相交");
            return false;
        }
    }

} 