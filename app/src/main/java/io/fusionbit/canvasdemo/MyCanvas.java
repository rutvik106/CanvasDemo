package io.fusionbit.canvasdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rutvik on 12/17/2016 at 4:26 PM.
 */

public class MyCanvas extends View
{

    final Context context;

    Bitmap background;

    List<Bitmap> scaledBitmapList = new ArrayList<>();

    Rect src;

    RectF dest;

    final int width, height;

    public MyCanvas(Context context, WindowManager windowManager)
    {
        super(context);
        this.context = context;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        src = new Rect(0, 0, width, height);
        dest = new RectF(0, 0, width, height);

    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas)
    {
        if (background != null)
        {
            canvas.drawBitmap(background, src, dest, null);
        }
        for (Bitmap b : scaledBitmapList)
        {
            canvas.drawBitmap(b, src, dest, null);
        }
    }

    public Bitmap getBitmap()
    {

        this.setDrawingCacheEnabled(false);
        this.setDrawingCacheEnabled(true);

        return Bitmap.createBitmap(this.getDrawingCache());

    }

    public Bitmap getScaleBitmap()
    {
        this.setDrawingCacheEnabled(false);
        this.setDrawingCacheEnabled(true);

        return Bitmap.createScaledBitmap(this.getDrawingCache(), width, height, true);
    }

    public void clearCanvas()
    {
        scaledBitmapList.clear();
        invalidate();
    }

    public void addImageOverCanvas(int drawable)
    {
        final Bitmap image = BitmapFactory.decodeResource(context.getResources(), drawable);
        scaledBitmapList.add(Bitmap.createScaledBitmap(image, width, height, false));
        invalidate();
    }

    public void setBackground(int drawable)
    {
        background = BitmapFactory.decodeResource(context.getResources(), drawable);
        invalidate();
    }

}
