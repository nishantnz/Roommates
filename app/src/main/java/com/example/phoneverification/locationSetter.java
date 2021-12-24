package com.example.phoneverification;

public class locationSetter {
    public String location, uid;

    public locationSetter(String location, String uid) {
        this.location = location;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
