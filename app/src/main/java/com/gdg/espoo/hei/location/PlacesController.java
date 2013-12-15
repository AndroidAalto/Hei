package com.gdg.espoo.hei.location;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gdg.espoo.hei.util.VolleySingleton;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlacesController {
    private static final String PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final int DEFAULT_PLACES_AMOUNT = 4;
    private final RequestQueue requestQueue;
    private final String googleMapsApiKey;

    public PlacesController(Context context, String key) {
        requestQueue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        googleMapsApiKey = key;
    }

    public void getCurrentPlace(Location location, PlacesListener listener) {
        getPlacesByProximity(location, 1, listener);
    }

    public void getPlacesByProximity(Location location, PlacesListener listener) {
        getPlacesByProximity(location, DEFAULT_PLACES_AMOUNT, listener);
    }

    public void getPlacesByProximity(Location location, final int amount, final PlacesListener listener) {
        StringBuilder sb = new StringBuilder(PLACES_API_URL);
        sb.append("location=");
        sb.append(location.getLatitude());
        sb.append(",");
        sb.append(location.getLongitude());
        sb.append("&types=park");
        sb.append("&rankby=distance");
        sb.append("&sensor=true");
        sb.append("&key=");
        sb.append(googleMapsApiKey);

        Log.d("request", sb.toString());

        requestQueue.add(new JsonObjectRequest(Request.Method.GET, sb.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    List<Place> places = new ArrayList<Place>(amount);
                    for (int i = 0; i < amount && i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        String name = result.getString("name");
                        JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                        double latitude = location.getDouble("lat");
                        double longitude = location.getDouble("lng");
                        places.add(new Place(new LatLng(latitude, longitude), name));
                    }
                    listener.onPlacesResult(places);
                } catch (JSONException e) {
                    listener.onPlacesError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onPlacesError();
            }
        }));
    }

    public interface PlacesListener {
        void onPlacesResult(List<Place> places);
        void onPlacesError();
    }
}
