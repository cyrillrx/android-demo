package com.cyrillrx.android.demo;

/**
 * POJO that represents a sample view / activity
 * Created on 02/10/2014.
 */
public class Sample {

    private String title;
    private String subtitle;
    private Class clazz;

    public Sample(String title, String subtitle, Class clazz) {
        this.title = title;
        this.subtitle = subtitle;
        this.clazz = clazz;
    }

    public String getTitle() { return title; }

    public String getSubtitle() { return subtitle; }

    public Class getClazz() { return clazz; }
}
