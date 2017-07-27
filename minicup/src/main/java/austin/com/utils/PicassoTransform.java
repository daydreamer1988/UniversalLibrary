package austin.com.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;

/**
 * Created by gy on 2017/7/27.
 */


/**
 * Picasso
 * .with(this)
 * .load("http://i6.qhimg.com/t015dbfc00a77e3911e.jpg")
 * .transform(new Transformation() {
     * @Override public Bitmap transform(Bitmap source) {
     * return PicassoTransform.transform(source);
     * }
     * @Override public String key() {
     * return null;
     * }})
 * .into(imageView);
 *
 *
 */

public class PicassoTransform {

    public static Bitmap getCircleTransform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);//定义一个渲染器
        paint.setShader(shader);//设置渲染器
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);//绘制图形
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        canvas.drawCircle(r, r, r-10, paint);

        squaredBitmap.recycle();
        return bitmap;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap getBlurBitmap(Context context, Bitmap source) {
        // Create another bitmap that will hold the results of the filter.

        Bitmap blurredBitmap = source.copy(Bitmap.Config.ARGB_8888, true);

        RenderScript rs = RenderScript.create(context);
        // Allocate memory for Renderscript to work with
        Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
        Allocation output = Allocation.createTyped(rs, input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);
        // Set the blur radius
        script.setRadius(10);
        // Start the ScriptIntrinisicBlur
        script.forEach(output);
        // Copy the output to the blurred bitmap
        output.copyTo(blurredBitmap);

        source.recycle();
        return blurredBitmap;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap getCircleBlurBitmap(Context context, Bitmap source){
        Bitmap circleTransform = getCircleTransform(source);
        Bitmap blurredBitmap = circleTransform.copy(Bitmap.Config.ARGB_8888, true);

        RenderScript rs = RenderScript.create(context);
        // Allocate memory for Renderscript to work with
        Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
        Allocation output = Allocation.createTyped(rs, input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);
        // Set the blur radius
        script.setRadius(10);
        // Start the ScriptIntrinisicBlur
        script.forEach(output);
        // Copy the output to the blurred bitmap
        output.copyTo(blurredBitmap);

        circleTransform.recycle();
        return blurredBitmap;
    }



}
