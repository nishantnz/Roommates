package com.example.phoneverification;

public class ReadWriteUserDetails {

    public String uid, details, age, name, textGender, occupation, habits, roomAvail, smokeGroup, profileImage;

    public ReadWriteUserDetails() {

    }

    public ReadWriteUserDetails(String uid, String name, String textGender, String textOccupation, String textHabits, String textRoomAvail, String textSmoke, String profileImage, String userAge, String userDetails) {
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
    }

    //    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
    public String getTextGender() {
        return textGender;
    }

    public void setTextGender(String textGender) {
        this.textGender = textGender;
    }
//
//    public String getProfileImage() {
//        return profileImage;
//    }
//
//    public void setProfileImage(String profileImage) {
//        this.profileImage = profileImage;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getDetails() {
//        return details;
//    }
//
//    public void setDetails(String details) {
//        this.details = details;
//    }
//
//    public String getAge() {
//        return age;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }
}
