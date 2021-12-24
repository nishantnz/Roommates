package com.example.phoneverification;

public class userPreferencesStore {
    public String matchPref2, prefUid, prefTextGender, prefOccupation, prefDrinking, prefSmoking, location;

    public userPreferencesStore() {

    }


    public userPreferencesStore(String matchPref2, String uid, String textGender, String textOccupation, String textPrefDrinking, String prefTextSmoking, String location) {
        this.matchPref2 = matchPref2;
        this.prefUid = uid;
        this.prefTextGender = textGender;
        this.prefOccupation = textOccupation;
        this.prefDrinking = textPrefDrinking;
        this.prefSmoking = prefTextSmoking;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrefDrinking() {
        return prefDrinking;
    }

    public void setPrefDrinking(String prefDrinking) {
        this.prefDrinking = prefDrinking;
    }

    public String getPrefSmoking() {
        return prefSmoking;
    }

    public void setPrefSmoking(String prefSmoking) {
        this.prefSmoking = prefSmoking;
    }

    public String getMatchPref2() {
        return matchPref2;
    }

    public void setMatchPref2(String matchPref2) {
        this.matchPref2 = matchPref2;
    }

    public String getPrefUid() {
        return prefUid;
    }

    public void setPrefUid(String prefUid) {
        this.prefUid = prefUid;
    }

    public String getPrefTextGender() {
        return prefTextGender;
    }

    public void setPrefTextGender(String prefTextGender) {
        this.prefTextGender = prefTextGender;
    }

    public String getPrefOccupation() {
        return prefOccupation;
    }

    public void setPrefOccupation(String prefOccupation) {
        this.prefOccupation = prefOccupation;
    }


}
