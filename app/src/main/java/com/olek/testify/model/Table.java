package com.olek.testify.model;


public enum Table {
     T_COURSE("Course"),
     T_LANGUAGE("Language"),
     T_SUBJECT("Subject"),
     T_GROUP("StudentGroup"),
     T_STUDENT("Student"),
     T_GROUP_COURSES("StudentGroupCourses"),
     T_TEST("Test"),
     T_RESULTS("Results"),
     T_TASK("Task"),
     T_ANSWER("Answer");


    private final String table;

    private Table(final String text) {
        this.table = text;
    }

    @Override
    public String toString() {
        return table;
    }
}

