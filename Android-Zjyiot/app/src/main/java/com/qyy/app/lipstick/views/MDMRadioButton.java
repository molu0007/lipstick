package com.qyy.app.lipstick.views;

/**
 * @author dengwg
 * @date 2018/3/14
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * 解决文字和drawableleft和文字一起时不居中。要设置  android:background="@null"android:button="@null"
 * @author zhousheng
 *
 */
public class MDMRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    public MDMRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public MDMRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MDMRadioButton(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取设置的图片
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            //第一个是left
            Drawable drawableTop = drawables[1];
            if (drawableTop != null) {
                //获取文字的宽度
                float textHeight = getPaint().measureText("zi");
                int drawablePadding = getCompoundDrawablePadding();
                int drawableHeight = 0;
                drawableHeight = drawableTop.getIntrinsicHeight();
                float bodyHeight = textHeight + drawableHeight + drawablePadding;
                int y = getHeight();
                canvas.translate(0,(y - bodyHeight) / 2);
//                canvas.drawText(getText().toString(),0,(y - bodyHeight) / 2,getPaint());
            }
        }
        super.onDraw(canvas);
    }
}