package com.notepoint4u.rxjavaudemy;

public class Student {
    private String name;
    private String email;
    private int age;
    private String registrationNumber;

    public Student(String name, String email, int age, String registrationNumber) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
