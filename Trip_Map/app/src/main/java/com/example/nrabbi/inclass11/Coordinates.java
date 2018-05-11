// Nazmul Rabbi & Dyrell Cole
// In Class Assignment 11
// Group 20
// Coordinates.java

package com.example.nrabbi.inclass11;

import android.support.annotation.NonNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Coordinates {
    double lat;
    double longitude;

    public Coordinates(double lat, double longitude) {
        this.lat = lat;
        this.longitude = longitude;
    }
    
    public List<HashMap<String, String>> toList(){
        HashMap<String, String> map = new HashMap<>();
        
        map.put(String.valueOf(lat), String.valueOf(longitude));

        List<HashMap<String, String>> list = new List<HashMap<String, String>>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<HashMap<String, String>> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(HashMap<String, String> stringStringHashMap) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends HashMap<String, String>> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends HashMap<String, String>> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public HashMap<String, String> get(int i) {
                return null;
            }

            @Override
            public HashMap<String, String> set(int i, HashMap<String, String> stringStringHashMap) {
                return null;
            }

            @Override
            public void add(int i, HashMap<String, String> stringStringHashMap) {

            }

            @Override
            public HashMap<String, String> remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<HashMap<String, String>> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<HashMap<String, String>> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<HashMap<String, String>> subList(int i, int i1) {
                return null;
            }
        };
        
        list.add(map);
        
        return list;
    }

    public double getLat() {
        return lat;
    }

    public double getLongitude() {
        return longitude;
    }
}
