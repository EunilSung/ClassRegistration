package com.example.classregistration;

public class Course {
    int courseID;
    String semester;
    String department;
    String name;
    int credit;
    String mon, tue, wed, thu, fri;
    int capacity, numOfApplicant;


    public Course(int courseID, String department, String name, int credit, int capacity, int numOfApplicant) {
        this.courseID = courseID;
        this.department = department;
        this.name = name;
        this.credit = credit;
        this.capacity = capacity;
        this.numOfApplicant = numOfApplicant;
    }

    public Course(int courseID, String mon, String tue, String wed, String thu, String fri, String name, String semester) {
        this.courseID = courseID;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.name = name;
        this.semester = semester;
    }

    public Course(int courseID, String semester, String department, String name, int credit, String mon, String tue, String wed, String thu, String fri) {
        this.courseID = courseID;
        this.semester = semester;
        this.department = department;
        this.name = name;
        this.credit = credit;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
    }


    public String getSchedule() {
        StringBuilder builder = new StringBuilder();
        if (!mon.equals("")) {
            builder.append("MON:" + mon + "  ");
        }
        if (!tue.equals("")) {
            builder.append("TUE:" + tue + "  ");
        }
        if (!wed.equals("")) {
            builder.append("WED:" + wed + "  ");
        }
        if (!thu.equals("")) {
            builder.append("THU:" + thu + "  ");
        }
        if (!fri.equals("")) {
            builder.append("FRI:" + fri + "  ");
        }
        return builder.toString().trim();
    }
}

