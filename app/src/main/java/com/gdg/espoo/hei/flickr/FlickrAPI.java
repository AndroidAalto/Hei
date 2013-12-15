package com.gdg.espoo.hei.flickr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xavi on 15/12/13.
 */
public class FlickrAPI {

    public static Bitmap search(String query) throws Exception {
        URL url = null;
        try {
            url = new URL("http://flickr.com/api/search/" + query);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (url != null)
            try {
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        else
            throw new Exception();
        return null;
    }
}
