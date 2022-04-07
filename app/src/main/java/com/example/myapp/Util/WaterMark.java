//package com.example.myapp.Util;
//
//import android.app.Activity;
//import android.graphics.Canvas;
//import android.graphics.ColorFilter;
//import android.graphics.Paint;
//import android.graphics.PixelFormat;
//import android.graphics.drawable.Drawable;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import java.util.List;
//
///**
// * Created by laihm on 2021/11/2
// * 水印
// */
//public class WaterMark {
//
//    //水印文本
//    private List<String> mTextList;
//
//    //水印颜色
//    private int mTextColor;
//
//    //水印角度
//    private float mRotation;
//
//    //字体大小
//    private float mTextSize;
//
//    //多行水印时设置的行距
//    private int mSpacing;
//
//    private static com.example.mvptest.Util.WaterMark mInstance;
//
//    private WaterMark(){
//    }
//
//    public static com.example.mvptest.Util.WaterMark getInstance() {
//        if (mInstance == null){
//            synchronized (com.example.mvptest.Util.WaterMark.class){
//                mInstance = new com.example.mvptest.Util.WaterMark();
//            }
//        }
//        return mInstance;
//    }
//
//    /**
//     * 设置文本1
//     * @param text1
//     * @return
//     */
////    public WaterMark setText1(String text1){
////        mText1 = text1;
////        return mInstance;
////    }
//
//    /**
//     * 设置文本2
//     * @param text2
//     * @return
//     */
////    public WaterMark setText2(String text2){
////        mText2 = text2;
////        return mInstance;
////    }
//
//    /**
//     * 设置文本List
//     * @param textList
//     * @return
//     */
//    public com.example.mvptest.Util.WaterMark setTextList(List<String> textList){
//        mTextList = textList;
//        return mInstance;
//    }
//
//    /**
//     * 设置字体颜色
//     * @param Color
//     * @return
//     */
//    public com.example.mvptest.Util.WaterMark setTextColor(int Color){
//        mTextColor = Color;
//        return mInstance;
//    }
//
//    /**
//     * 设置字体大小
//     * @param size
//     * @return
//     */
//    public com.example.mvptest.Util.WaterMark setTextSize(float size){
//        mTextSize = size;
//        return mInstance;
//    }
//
//    /**
//     * 设置角度
//     * @param degrees
//     * @return
//     */
//    public com.example.mvptest.Util.WaterMark setRotation(float degrees){
//        mRotation = degrees;
//        return mInstance;
//    }
//
//    /**
//     * 设置多行水印时的行距
//     * @param spacing
//     * @return
//     */
//    public com.example.mvptest.Util.WaterMark setSpacing(int spacing){
//        mSpacing = spacing;
//        return mInstance;
//    }
//
//    /**
//     * 显示水印，铺满
//     * @param activity
//     */
//    public void show(Activity activity){
//        show(activity,mTextList);
//    }
//
//    public void show(Activity activity,List<String> mTextList){
//        WaterMarkDrawable drawable = new WaterMarkDrawable();
//        drawable.mTextList = mTextList;
//        drawable.mSpacing = mSpacing;
//        drawable.mTextSize = mTextSize;
//        drawable.mTextColor = mTextColor;
//        drawable.mRotation = mRotation;
//
//        ViewGroup rootView = activity.findViewById(android.R.id.content);
//        FrameLayout layout = new FrameLayout(activity);
//        layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        layout.setBackground(drawable);
//        rootView.addView(layout);
//    }
//
//    private class WaterMarkDrawable extends Drawable{
//
//        private Paint mPaint;
//
//        private List<String> mTextList;
//
//        private int mTextColor;
//
//        private float mTextSize;
//
//        private float mRotation;
//
//        private int mSpacing;
//
//        private WaterMarkDrawable(){
//            mPaint = new Paint();
//        }
//
//        @Override
//        public void draw(@NonNull Canvas canvas) {
//            int width = getBounds().right;
//            int height = getBounds().bottom;
//            int diagonal = (int) Math.sqrt(width * width + height * height);//水印的行距
//
//            mPaint.setColor(mTextColor);
//            mPaint.setTextSize(mTextSize);
//            mPaint.setAntiAlias(true);
//
//            float textWidth = mPaint.measureText(mTextList.get(0));
//
//            canvas.drawColor(0x000000);
//            canvas.rotate(mRotation);
//
//            int index = 0;
//            float fromX;
//            for (int positionY = diagonal / 10; positionY < diagonal; positionY += diagonal/10){
//                fromX = -width + (index++ %2) * textWidth;
//                for (float positionX = fromX; positionX < width; positionX += textWidth * 2){
//                    int spacing = 0;
//                    for (String text:mTextList){
//                        canvas.drawText(text,positionX,positionY+spacing,mPaint);
//                        spacing = spacing+mSpacing;
//                    }
////                    for (int i = 0; i < mTextList.size();i++){
////                        canvas.drawText(mTextList.get(i),positionX,positionY+mSpacing,mPaint);
////                        mSpacing += mSpacing;
////                    }
//                }
//            }
//            canvas.save();
//            canvas.restore();
//        }
//
//        @Override
//        public void setAlpha(int alpha) {
//
//        }
//
//        @Override
//        public void setColorFilter(@Nullable ColorFilter colorFilter) {
//
//        }
//
//        @Override
//        public int getOpacity() {
//            return PixelFormat.TRANSLUCENT;
//        }
//    }
//
//}
