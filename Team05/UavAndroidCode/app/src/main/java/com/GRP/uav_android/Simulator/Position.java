package com.GRP.uav_android.Simulator;

public class Position {
    private double longtitude;
    private double latitude;


    public Position(double lat, double longt){
        latitude = lat;
        longtitude = longt;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
