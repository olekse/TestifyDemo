package com.olek.testify.model;

/*
	ID_StudentGroup INT NOT NULL,
	ID_COURSE INT NOT NULL,
    PRIMARY KEY (ID_StudentGroup, ID_COURSE),
FOREIGN KEY (ID_StudentGroup) REFERENCES StudentGroup(ID),
FOREIGN KEY (ID_COURSE) REFERENCES Course(ID)
 */

@MLHTable(tableName = Table.T_GROUP_COURSES)
public class GroupCourses extends TableAdapter {

    @MLHField(fieldName = "ID_GROUP")
    private int id_group;
    @MLHField(fieldName = "ID_COURSE")
    private int id_course;

    public GroupCourses() {
    }

    public GroupCourses(int id_group, int id_course) {
        this.id_group = id_group;
        this.id_course = id_course;
    }

    public int getId_group() {
        return id_group;
    }

    public void setId_group(int id_group) {
        this.id_group = id_group;
    }

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }
}
