package com.example.user.sportslover.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.user.sportslover.R;

import java.text.DecimalFormat;

public class CircularRingPercentageView extends View {
    private Paint paint;
    private int circleWidth;
    private int roundBackgroundColor;
    private int textColor;
    private float textSize;
    private float roundWidth;
    private float progress = 0;
    private int[] colors = {0xff6ee4bc, 0xff64d2d0, 0xff5bc0e5};
    private int radius;
    private RectF oval;
    private Paint mPaintText;
    private int maxColorNumber = 100;
    private float singlPoint = 9;
    private float lineWidth = 0.3f;
    private int circleCenter;
    private SweepGradient sweepGradient;
    private boolean isLine = false;
    private boolean isBackground = true;
    private boolean isText = false;
    private float arcAngle = 360;
    private float textTarget = 100f;
    private DecimalFormat textFormat = new DecimalFormat("#0.0");

    /**
     * 分割的数量
     *
     * @param maxColorNumber 数量
     */
    public void setMaxColorNumber(int maxColorNumber) {
        this.maxColorNumber = maxColorNumber;
        singlPoint = arcAngle / (float) maxColorNumber;
        invalidate();
    }

    /**
     * 是否是线条
     *
     * @param line true 是 false否
     */
    public void setLine(boolean line) {
        isLine = line;
        invalidate();
    }

    /**
     * 是否有背景
     *
     * @param background true 是 false否
     */
    public void setBackground(boolean background) {
        isBackground = background;
        invalidate();
    }

    /**
     * 是否有刻度
     *
     * @param isText true 是 false否
     */
    public void setText(boolean isText) {
        this.isText = isText;
        invalidate();
    }

    public int getCircleWidth() {
        return circleWidth;
    }

    public CircularRingPercentageView(Context context) {
        this(context, null);
    }

    public CircularRingPercentageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CircularRingPercentageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularRing);
        maxColorNumber = mTypedArray.getInt(R.styleable.CircularRing_circleNumber, 40);
        circleWidth = mTypedArray.getDimensionPixelOffset(R.styleable.CircularRing_circleWidth, getDpValue(240));
        roundBackgroundColor = mTypedArray.getColor(R.styleable.CircularRing_roundColor, 0xff848484);
        textColor = mTypedArray.getColor(R.styleable.CircularRing_circleTextColor, 0xffffffff);
        roundWidth = mTypedArray.getDimension(R.styleable.CircularRing_circleRoundWidth, 40);
        textSize = mTypedArray.getDimension(R.styleable.CircularRing_circleTextSize, getDpValue(8));
        colors[0] = mTypedArray.getColor(R.styleable.CircularRing_circleColor1, 0xff6ee4bc);
        colors[1] = mTypedArray.getColor(R.styleable.CircularRing_circleColor2, 0xff64d2d0);
        colors[2] = mTypedArray.getColor(R.styleable.CircularRing_circleColor3, 0xff5bc0e5);
        initView();
        mTypedArray.recycle();
    }


    /**
     * 空白出颜色背景
     *
     * @param roundBackgroundColor
     */
    public void setRoundBackgroundColor(int roundBackgroundColor) {
        this.roundBackgroundColor = roundBackgroundColor;
        paint.setColor(roundBackgroundColor);
        invalidate();
    }

    /**
     * 刻度字体颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        mPaintText.setColor(textColor);
        invalidate();
    }

    /**
     * 刻度字体大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        mPaintText.setTextSize(textSize);
        invalidate();
    }

    /**
     * 满偏刻度
     *
     * @param textTarget
     */
    public void setTextTarget(float textTarget) {
        this.textTarget = textTarget;
        invalidate();
    }

    /**
     * 渐变颜色
     *
     * @param colors
     */
    public void setColors(int[] colors) {
        if (colors.length < 2) {
            throw new IllegalArgumentException("colors length < 2");
        }
        this.colors = colors;
        sweepGradientInit();
        invalidate();
    }


    /**
     * 间隔角度大小
     *
     * @param lineWidth
     */
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        invalidate();
    }


    private int getDpValue(int w) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w, getContext().getResources().getDisplayMetrics());
    }

    /**
     * 圆环宽度
     *
     * @param roundWidth 宽度
     */
    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
        if (roundWidth > circleCenter) {
            this.roundWidth = circleCenter;
        }
        radius = (int) (circleCenter - this.roundWidth / 2); // 圆环的半径
        oval.left = circleCenter - radius;
        oval.right = circleCenter + radius;
        oval.bottom = circleCenter + radius;
        oval.top = circleCenter - radius;
        paint.setStrokeWidth(this.roundWidth);
        invalidate();
    }

    /**
     * 圆环的直径
     *
     * @param circleWidth 直径
     */
    public void setCircleWidth(int circleWidth) {
        this.circleWidth = circleWidth;
        circleCenter = circleWidth / 2;

        if (roundWidth > circleCenter) {
            roundWidth = circleCenter;
        }
        setRoundWidth(roundWidth);
        sweepGradient = new SweepGradient(this.circleWidth / 2, this.circleWidth / 2, colors, null);
        //旋转 不然是从0度开始渐变
        Matrix matrix = new Matrix();
        matrix.setRotate(-90f, this.circleWidth / 2, this.circleWidth / 2);
        sweepGradient.setLocalMatrix(matrix);
    }

    /**
     * 渐变初始化
     */
    public void sweepGradientInit() {
        //渐变颜色
        sweepGradient = new SweepGradient(this.circleWidth / 2, this.circleWidth / 2, colors, null);
        //旋转 不然是从0度开始渐变
        Matrix matrix = new Matrix();
        matrix.setRotate(-90f, this.circleWidth / 2, this.circleWidth / 2);
        sweepGradient.setLocalMatrix(matrix);
    }

    public void initView() {

        circleCenter = circleWidth / 2;//半径
        singlPoint = arcAngle / (float) maxColorNumber;
        radius = (int) (circleCenter - roundWidth / 2); // 圆环的半径
        sweepGradientInit();
        mPaintText = new Paint();
        mPaintText.setColor(textColor);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(textSize);
        mPaintText.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(roundBackgroundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);

        // 用于定义的圆弧的形状和大小的界限
        oval = new RectF(circleCenter - radius, circleCenter - radius, circleCenter + radius, circleCenter + radius);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //背景渐变颜色
        paint.setShader(sweepGradient);
        canvas.drawArc(oval, -90f, (float) (progress * arcAngle / 100), false, paint);
        paint.setShader(null);

        //是否是线条模式
        if (isLine) {
            float start = -90f;
            float p = ((float) maxColorNumber / (float) 100);
            p = (int) (progress * p);
            for (int i = 0; i < p; i++) {
                paint.setColor(roundBackgroundColor);
                canvas.drawArc(oval, start + singlPoint - lineWidth, lineWidth, false, paint); // 绘制间隔快
                start = (start + singlPoint);
            }
        }
        //绘制剩下的空白区域
        if (isBackground){
            paint.setStrokeWidth(getDpValue(1));
            paint.setColor(roundBackgroundColor);
            canvas.drawArc(oval, -90, -(100 - progress) * arcAngle / 100, false, paint);
            paint.setStrokeWidth(roundWidth);
        }

        if (isText) {
            //绘制文字刻度
            for (int i = 0; i <= 10; i++) {
                canvas.save();// 保存当前画布
                canvas.rotate(arcAngle / 10 * i - arcAngle / 2, circleCenter, circleCenter);
                canvas.drawText(textFormat.format(i * textTarget / 10) + "", circleCenter, circleCenter - radius + roundWidth / 2 + getDpValue(4) + textSize, mPaintText);
                canvas.drawLine(circleCenter, circleCenter - radius + roundWidth / 2, circleCenter, circleCenter - radius - roundWidth / 2 + getDpValue(-8), mPaintText);
                canvas.restore();//
            }

            //绘制细刻度
            for (int i = 0; i <= 100; i++) {
                canvas.save();// 保存当前画布
                canvas.rotate(arcAngle / 100 * i - arcAngle / 2, circleCenter, circleCenter);
                canvas.drawLine(circleCenter, circleCenter - radius + roundWidth / 2, circleCenter, circleCenter - radius - roundWidth / 2, mPaintText);
                canvas.restore();//
            }
        }
    }


    OnProgressScore onProgressScore;

    public interface OnProgressScore {
        void setProgressScore(float score);

    }

    public synchronized void setProgress(final float p) {
        progress = p;
        postInvalidate();
    }

    /**
     * @param p
     */
    public synchronized void setProgress(final float p, OnProgressScore onProgressScore) {
        this.onProgressScore = onProgressScore;
        progress = p;
        postInvalidate();
    }

}
