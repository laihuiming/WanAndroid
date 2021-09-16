package com.example.myapp.Util;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by laihm on 2021/9/14
 */
public class ColorUtil {
    /**
     * 生成随机颜色
     *
     * @return
     */
    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(200);
        int green = random.nextInt(200);
        int blue = random.nextInt(200);
        return Color.rgb(red, green, blue);
    }
}
