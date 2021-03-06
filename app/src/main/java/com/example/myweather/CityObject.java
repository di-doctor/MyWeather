package com.example.myweather;

import java.io.Serializable;

public class CityObject implements Comparable<CityObject>, Serializable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(CityObject o) {
        if (o != null) {
            return getName().compareTo( o.getName());
        } else return 0;

    }
}
