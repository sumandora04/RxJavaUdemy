package com.notepoint4u.rxjavaudemy;

import java.util.ArrayList;

public class DataSource {

    public static ArrayList<Student> getStudents(){
        ArrayList<Student> studentArrayList = new ArrayList<>();

        studentArrayList.add(new Student("Shilpa","shilpa@gmail.com",26,"123123"));
        studentArrayList.add(new Student("Divya","divya@gmail.com",27,"123123"));
        studentArrayList.add(new Student("Shilpa2","shilpa@gmail.com",22,"123123"));
        studentArrayList.add(new Student("Shilpa","shilpa@gmail.com",26,"123123"));
        studentArrayList.add(new Student("Shilpa","shilpa@gmail.com",26,"123123"));
        studentArrayList.add(new Student("Shilpa5","shilpa@gmail.com",20,"123123"));

        return studentArrayList;
    }
}
