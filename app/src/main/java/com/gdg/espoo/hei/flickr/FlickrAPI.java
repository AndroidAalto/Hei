package com.gdg.espoo.hei.flickr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FlickrAPI {

    final static private String APIKey = "&api_key=APIKEY";
    //final static private String secret = "SECRET";

    final static private String APIURL = "http://api.flickr.com/services/rest/?method=";
    final static private String method = "flickr.photos.search";

    public static Bitmap search(double latitude, double longitude) throws Exception {
        URL url = null;
        try {
            url = new URL(APIURL + method + APIKey + "&lat=" + String.valueOf(latitude) +
                    "&lon=" + String.valueOf(longitude));
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
