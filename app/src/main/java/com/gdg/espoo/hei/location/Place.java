package com.gdg.espoo.hei.location;

import com.google.android.gms.maps.model.LatLng;

public class Place {
    public final LatLng location;
    public final String name;

    public Place(LatLng location, String name) {
        this.location = location;
        this.name = name;
    }
}