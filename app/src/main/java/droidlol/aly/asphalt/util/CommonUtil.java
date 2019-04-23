package droidlol.aly.asphalt.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtil {

    public static File scaleDown(File file, float maxImageSize,
                                 boolean filter, Bitmap bitmap) {

        if (null == bitmap) {
            return null;
        }

        float ratio = Math.min(
                (float) maxImageSize / bitmap.getWidth(),
                (float) maxImageSize / bitmap.getHeight());
        int width = Math.round((float) ratio * bitmap.getWidth());
        int height = Math.round((float) ratio * bitmap.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, width,
                height, filter);

        try {
            FileOutputStream fOut = new FileOutputStream(file);
            newBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            fOut.flush();
            fOut.close();

            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String dateToString(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());

        return df.format(date);
    }

    public static String formatStrings(String source, String value) {
        return String.format(source, value);
    }
}
