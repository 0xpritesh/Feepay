package com.app.feepay.JavaClasses;

public class RegistrationUserDataHelperClass {
    String FeeAmount, ChildName, Address, Email, SchoolName, Class, Medium;
    String Phone, Password;
    String ProfilePic;




    public RegistrationUserDataHelperClass(String childName, String schoolName, String email, String address, String feeAmount, String mClass, String medium, String phone, String password) {
        FeeAmount = feeAmount;
        ChildName = childName;
        Address = address;
        Email = email;
        SchoolName = schoolName;
        Class = mClass;
        Medium = medium;
        Phone = phone;
        Password = password;
    }
    public RegistrationUserDataHelperClass(String imageUrl){
        this.ProfilePic = imageUrl;
    }



    public String getFeeAmount() {
        return FeeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        FeeAmount = feeAmount;
    }




    public String getChildName() {
        return ChildName;
    }

    public void setChildName(String childName) {
        ChildName = childName;
    }




    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }





    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }




    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }





    public String getChildClass() {
        return Class;
    }

    public void setChildClass(String mClass) {
        Class = mClass;
    }





    public String getMedium() {
        return Medium;
    }

    public void setMedium(String medium) {
        Medium = medium;
    }






    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }





    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }




    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }


}



