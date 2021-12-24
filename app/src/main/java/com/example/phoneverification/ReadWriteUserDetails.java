package com.example.phoneverification;

public class ReadWriteUserDetails {

    public String matchPref, email, phoneNumber, uid, details, age, name, textGender, occupation, habits, roomAvail, smokeGroup, profileImage, location, preciseLocation;

    public ReadWriteUserDetails() {

    }


    public ReadWriteUserDetails(String matchPref, String email, String phoneNumber, String uid, String name, String textGender, String textOccupation, String textHabits, String textRoomAvail, String textSmoke, String profileImage, String userAge, String userDetails, String location, String preciseLocation) {
        this.matchPref = matchPref;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.uid = uid;
        this.name = name;
        this.profileImage = profileImage;
        this.age = userAge;
        this.details = userDetails;
        this.textGender = textGender;
        this.occupation = textOccupation;
        this.habits = textHabits;
        this.roomAvail = textRoomAvail;
        this.smokeGroup = textSmoke;
        this.location = location;
        this.preciseLocation = preciseLocation;
    }

    public String getPreciseLocation() {
        return preciseLocation;
    }

    public void setPreciseLocation(String preciseLocation) {
        this.preciseLocation = preciseLocation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMatchPref() {
        return matchPref;
    }

    public void setMatchPref(String matchPref) {
        this.matchPref = matchPref;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getHabits() {
        return habits;
    }

    public void setHabits(String habits) {
        this.habits = habits;
    }

    public String getRoomAvail() {
        return roomAvail;
    }

    public void setRoomAvail(String roomAvail) {
        this.roomAvail = roomAvail;
    }

    public String getSmokeGroup() {
        return smokeGroup;
    }

    public void setSmokeGroup(String smokeGroup) {
        this.smokeGroup = smokeGroup;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextGender() {
        return textGender;
    }

    public void setTextGender(String textGender) {
        this.textGender = textGender;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
