package com.example.classregistration;

import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<List<Course>> listCourse;

    public Schedule() {
        listCourse = new ArrayList<>();
        listCourse.add(new ArrayList<Course>());
        listCourse.add(new ArrayList<Course>());
    }

    public boolean isAlreadyIn(Course courseAdd) {
        int semester = Integer.parseInt(courseAdd.semester);
        for (int i = 0; i < listCourse.get(semester - 1).size(); i++) {
            if (listCourse.get(semester - 1).get(i).courseID == courseAdd.courseID)
                return true;
        }
        return false;
    }


    public boolean isDuplicated(Course courseAdd) {
        ArrayList<String> monAdd = getCourseScheduleAsList(courseAdd.mon);
        ArrayList<String> tueAdd = getCourseScheduleAsList(courseAdd.tue);
        ArrayList<String> wedAdd = getCourseScheduleAsList(courseAdd.wed);
        ArrayList<String> thuAdd = getCourseScheduleAsList(courseAdd.thu);
        ArrayList<String> friAdd = getCourseScheduleAsList(courseAdd.fri);
        int semester = Integer.parseInt(courseAdd.semester);
        for (int i = 0; i < listCourse.get(semester - 1).size(); i++) {
            ArrayList<String> mon = getCourseScheduleAsList(listCourse.get(semester - 1).get(i).mon);
            ArrayList<String> tue = getCourseScheduleAsList(listCourse.get(semester - 1).get(i).tue);
            ArrayList<String> wed = getCourseScheduleAsList(listCourse.get(semester - 1).get(i).wed);
            ArrayList<String> thu = getCourseScheduleAsList(listCourse.get(semester - 1).get(i).thu);
            ArrayList<String> fri = getCourseScheduleAsList(listCourse.get(semester - 1).get(i).fri);
            if (isDuplicate(mon, monAdd) || isDuplicate(tue, tueAdd) || isDuplicate(wed, wedAdd) || isDuplicate(thu, thuAdd) || isDuplicate(fri, friAdd))
            return true;
        }
        return false;
    }

    public void addCourse(Course courseAdd) {
        int semester = Integer.parseInt(courseAdd.semester);
        listCourse.get(semester - 1).add(courseAdd);
    }

    private ArrayList<String> getCourseScheduleAsList(String schedule) {
        ArrayList<String> listSchedule = new ArrayList<>();
        for (int i = 0; i < schedule.length(); i = i + 2) {
            listSchedule.add(schedule.substring(i, i + 2));
        }
        return listSchedule;
    }

    private boolean isDuplicate(List<String> a, List<String> b) {
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (a.get(i).equals(b.get(j)))
                    return true;
            }
        }
        return false;
    }

    public void setTextViewForDays(TextView[] textViewMon, TextView[] textViewTue, TextView[] textViewWed, TextView[] textViewThu, TextView[] textViewFri, Context context, String semesterString) {
        int semester = Integer.parseInt(semesterString);
        resetTextView(textViewMon, context);
        resetTextView(textViewTue, context);
        resetTextView(textViewWed, context);
        resetTextView(textViewThu, context);
        resetTextView(textViewFri, context);
        for (int i = 0; i < listCourse.get(semester - 1).size(); i++) {
            ArrayList<String> mon = getCourseScheduleAsList(listCourse.get(semester - 1).get(i).mon);
            ArrayList<String> tue = getCourseScheduleAsList(listCourse.get(semester - 1).get(i).tue);
            ArrayList<String> wed = getCourseScheduleAsList(listCourse.get(semester - 1).get(i).wed);
            ArrayList<String> thu = getCourseScheduleAsList(listCourse.get(semester - 1).get(i).thu);
            ArrayList<String> fri = getCourseScheduleAsList(listCourse.get(semester - 1).get(i).fri);
            setTextViewWithList(mon, textViewMon, listCourse.get(semester - 1).get(i).name, context);
            setTextViewWithList(tue, textViewTue, listCourse.get(semester - 1).get(i).name, context);
            setTextViewWithList(wed, textViewWed, listCourse.get(semester - 1).get(i).name, context);
            setTextViewWithList(thu, textViewThu, listCourse.get(semester - 1).get(i).name, context);
            setTextViewWithList(fri, textViewFri, listCourse.get(semester - 1).get(i).name, context);
        }
    }
    private void resetTextView(TextView[] textViews, Context context) {
        for (int i = 0; i < ScheduleFragment.NUM_OF_TIME_SLOTS; i++) {
            textViews[i].setText("");
            textViews[i].setBackground(context.getResources().getDrawable(R.drawable.cell_schedule));
        }
    }
    private void setTextViewWithList(ArrayList<String> list, TextView[] textViews, String text, Context context) {
        for (int i = 0; i < list.size(); i++) {
            int timeSlot = Integer.parseInt(list.get(i));
            textViews[timeSlot].setText(text);
            textViews[timeSlot].setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }
}