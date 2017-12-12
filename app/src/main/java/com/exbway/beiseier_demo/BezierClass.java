package com.exbway.beiseier_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 张政浩 on 2017/12/9.
 */

public class BezierClass extends View {

    private Paint mPaint;
    private Path mPath;
    private Paint paint;

    private int viewWidth, viewHeight; //控件的宽和高
    private float commandX, commandY; //控制点的坐标
    private float waterHeight;  //水位高度

    private boolean isInc;// 判断控制点是该右移还是左移

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //起始点位置
        mPath.moveTo(-1/4f*viewWidth,waterHeight);
        //水波浪
        mPath.quadTo(commandX,commandY,viewWidth+1/4f*viewWidth,waterHeight);
        //水波浪下方闭合区域
        mPath.lineTo(viewWidth+1/4f*viewWidth,viewHeight);
        mPath.lineTo(-1 / 4F * viewWidth, viewHeight);
        mPath.close();
        //绘制路径
        canvas.drawPath(mPath, mPaint);
        //绘制红色水位高度辅助线
        canvas.drawLine(0,waterHeight,viewWidth,waterHeight,paint);
        //产生波浪左右涌动的感觉
        if (commandX >= viewWidth + 1 / 4F * viewWidth) {//控制点坐标大于等于终点坐标改标识
            isInc = false;
        } else if (commandX <= -1 / 4F * viewWidth) {//控制点坐标小于等于起点坐标改标识
            isInc = true;
        }
        commandX = isInc ? commandX + 20 : commandX - 20;
        //水位不断加高  当距离控件顶端还有1/8的高度时，不再上升
        if (commandY >= 1 / 8f * viewHeight) {
            commandY -= 2;
            waterHeight -= 2;
        }
        //路径重置
        mPath.reset();
        // 重绘
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;
        commandY = 5 / 8f * viewHeight;
        waterHeight = 13 / 16f * viewHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, 300);
//        } else if (wSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, hSpecSize);
//        } else if (hSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(wSpecSize, 300);
//        }
    }


    public BezierClass(Context context) {
        super(context);
        init();
    }

    public BezierClass(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierClass(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#AFDEE4"));
        mPath = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5f);
    }

}
