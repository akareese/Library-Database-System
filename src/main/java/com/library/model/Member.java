package com.library.model;

public class Member {
    private int memberId;
    private String fullName;
    private String email;
    private String phone;

    public Member() {
    }

    public Member(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public Member(int memberId, String fullName, String email, String phone) {
        this.memberId = memberId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | %s | Email: %s | Phone: %s",
                memberId, fullName, email, phone
        );
    }
}