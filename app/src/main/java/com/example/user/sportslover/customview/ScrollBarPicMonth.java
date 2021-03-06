package com.example.user.sportslover.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.user.sportslover.R;
import com.example.user.sportslover.bean.ScrollBarBean;
import com.example.user.sportslover.bean.ScrollPerBarBean;

/**
 * Created by user on 17-10-10.
 */

public class ScrollBarPicMonth extends View {

    private int leftPadding;// 柱子的左右边距
    private int barPadding;// 柱子之间的间距
    private int barbottom;// 柱子的底端位置
    private int bartop;// 柱子的顶端位置
    private int perBarWidth;// 每个柱子的宽度
    private int width;// 控件的宽度
    private int height;// 控件的高度
    private int defaultHeight;// 默认高度
    private ScrollBarBean scrollBarBean;
    private Paint barPaint;// 柱状图画笔
    private TextPaint textPaint;
    private OnClickListener listener;
    private TextPaint textPaint1;// 灰色画笔
    private TextPaint textPaint2;// 蓝色画笔
    private Paint.FontMetrics metrics1;
    private Paint.FontMetrics metrics2;
    private int baseline1;
    private int baseline2;
    private boolean flag;//是否有选中的柱子
    private RectF rectF = new RectF();

    public ScrollBarPicMonth(Context context) {
        this(context, null);
    }

    public ScrollBarPicMonth(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBarPicMonth(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        leftPadding = (int) getResources().getDimension(R.dimen.px2dp_42);
        barPadding = (int) getResources().getDimension(R.dimen.px2dp_80);
        bartop = (int) getResources().getDimension(R.dimen.px2dp_66);
        barbottom = (int) getResources().getDimension(R.dimen.px2dp_74);
        defaultHeight = (int) getResources().getDimension(R.dimen.px2dp_10);
        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        barPaint.setColor(Color.WHITE);
        // barPaint.setStrokeCap(Cap.SQUARE);//自己埋了个大炕
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.WHITE);
        barPaint.setStrokeWidth(perBarWidth);
        textPaint1 = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint1.setTextSize(getResources().getDimension(R.dimen.px2sp_18));
        textPaint1.setColor(Color.WHITE);
        textPaint1.setTextAlign(Paint.Align.CENTER);
        textPaint2 = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint2.setTextSize(getResources().getDimension(R.dimen.px2dp_22));
        textPaint2.setColor(Color.WHITE);
        textPaint2.setTextAlign(Paint.Align.CENTER);
        metrics1 = textPaint1.getFontMetrics();
        metrics2 = textPaint2.getFontMetrics();
        baseline1 = (int) ((-metrics1.ascent - metrics1.descent) / 2);
        baseline2 = (int) ((-metrics2.ascent - metrics2.descent) / 2);
        invalidate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        perBarWidth = (w_screen - 2 * leftPadding - 7 * barPadding) / 7;
        int width;
        width = (perBarWidth + barPadding) * 30;// this的宽度
        if(scrollBarBean != null)
            if (scrollBarBean.getLists().size() != 0)
                width = (perBarWidth + barPadding) * scrollBarBean.getLists().size();// this的宽度
        setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        barPaint.setStrokeWidth(perBarWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (scrollBarBean == null)
            return;
        canvas.drawColor(Color.TRANSPARENT);
        ScrollPerBarBean perBarBean = null;
        for (int i = 0; i < scrollBarBean.getLists().size(); i++) {
            perBarBean = scrollBarBean.getLists().get(i);

            if (perBarBean.isFlag())// 选中状态
            {
                // 绘制日期
                canvas.drawText(perBarBean.getTopString(), barPadding / 2 + perBarWidth
                                / 2 + (barPadding + perBarWidth) * i, height - barbottom
                                - perBarBean.getActualHeight() - baseline1 - defaultHeight,
                        textPaint1);
                barPaint.setColor(Color.WHITE);

            } else {
                barPaint.setColor(Color.WHITE);
                textPaint1.setColor(Color.WHITE);
            }
            /*// 先绘制预留高度
            canvas.drawLine(barPadding / 2 + perBarWidth / 2
                            + (barPadding + perBarWidth) * i, height - barbottom,
                    barPadding / 2 + perBarWidth / 2
                            + (barPadding + perBarWidth) * i, height
                            - barbottom - defaultHeight, barPaint);*/
            rectF.left = barPadding / 2  + (barPadding + perBarWidth) * i;
            rectF.right = barPadding / 2 + perBarWidth + (barPadding + perBarWidth) * i;
            rectF.top = height - barbottom;
            rectF.bottom = height - barbottom - defaultHeight;
            canvas.drawRoundRect(rectF, perBarWidth/2, perBarWidth/2, barPaint);

            /*// 绘制柱状图
            canvas.drawLine(barPadding / 2 + perBarWidth / 2
                    + (barPadding + perBarWidth) * i, height - barbottom
                    - defaultHeight, barPadding / 2 + perBarWidth / 2
                    + (barPadding + perBarWidth) * i, height - barbottom
                    - perBarBean.getActualHeight() - defaultHeight, barPaint);*/
            rectF.left = barPadding / 2 + (barPadding + perBarWidth) * i;
            rectF.right = barPadding / 2 + perBarWidth + (barPadding + perBarWidth) * i;
            rectF.top = height - barbottom - perBarBean.getActualHeight() - defaultHeight;
            rectF.bottom = height - barbottom;
            canvas.drawRoundRect(rectF, perBarWidth/2, perBarWidth/2, barPaint);


            // 绘制收益
            canvas.drawText(
                    perBarBean.getBottomString(),
                    barPadding / 2 + perBarWidth / 2
                            + (barPadding + perBarWidth) * i,
                    height - barbottom
                            + getResources().getDimension(R.dimen.px2dp_20)
                            + baseline2 + defaultHeight, textPaint2);
            textPaint1.setColor(Color.WHITE);

            // 设置左边界
            perBarBean.setLeftX(barPadding / 2 + perBarWidth / 2
                    + (barPadding + perBarWidth) * i - perBarWidth / 2);
            // 设置右边界
            perBarBean.setRightX(barPadding / 2 + perBarWidth / 2
                    + (barPadding + perBarWidth) * i + perBarWidth / 2);
            // 设置上边界
            perBarBean.setTopY(height - perBarBean.getActualHeight()
                    - barbottom - defaultHeight);

        }
        super.onDraw(canvas);
    }

    /**
     * 得到柱子之间的宽度（父容器使用）
     *
     * @return
     */
    public int getPadding() {
        return barPadding;
    }

    /**
     * 得到每个柱子的宽度（父容器使用）
     *
     * @return
     */
    public int getPerbarWidth() {
        return perBarWidth;
    }

    public void setDatas(ScrollBarBean scrollBarBean) {
        this.scrollBarBean = scrollBarBean;
        ScrollPerBarBean perBarBean = null;
        for (int i = 0; i < scrollBarBean.getLists().size(); i++) {
            perBarBean = scrollBarBean.getLists().get(i);
            perBarBean.setActualHeight(perBarBean.getRatio()
                    * (height - barbottom - bartop - defaultHeight)
                    / scrollBarBean.getTotal());
//          if (i == scrollBarBean.getLists().size() - 1) {
//              perBarBean.setFlag(true);
//          }
            if(perBarBean.isFlag())
            {
                flag=true;
            }
        }
        if(!flag)
        {
            perBarBean.setFlag(true);
        }
        init();
    }

    int x = 0;
    int y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                y = (int) event.getY();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onclick(int i);
    }

    public void clickcallback(boolean flag) {
        if (!flag) {
            ScrollPerBarBean perBarBean = null;
            for (int i = 0; i < scrollBarBean.getLists().size(); i++) {
                perBarBean = scrollBarBean.getLists().get(i);
                if (perBarBean.getLeftX() < x && x < perBarBean.getRightX()
                        && y > perBarBean.getTopY() && y < height) {
                    perBarBean.setFlag(true);
                    for (int j = 0; j < scrollBarBean.getLists().size(); j++) {
                        if (i != j) {
                            scrollBarBean.getLists().get(j).setFlag(false);
                        }
                    }
                    if (listener != null) {
                        listener.onclick(i);
                    }
                    invalidate();
                }
            }
        }
    }
}
