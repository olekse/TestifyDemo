package com.olek.testify.model;

/*
   ID   INT              NOT NULL,
   SEMESTER INT		 NOT NULL,
   ID_SUBJECT INT      NOT NULL,

   PRIMARY KEY (ID),
 	FOREIGN KEY (ID_SUBJECT) REFERENCES Subject(ID)
 */
@MLHTable(tableName = Table.T_COURSE)
public class Course extends TableAdapter {

    @MLHField(fieldName = "ID")
    private Integer id;
    @MLHField(fieldName = "SEMESTER")
    private Integer semester;
    @MLHField(fieldName = "ID_SUBJECT")
    private Integer id_subject;

    public Course() {
    }

    public Course(int id, int semester, int id_subject) {
        this.id = id;
        this.semester = semester;
        this.id_subject = id_subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getId_subject() {
        return id_subject;
    }

    public void setId_subject(int id_subject) {
        this.id_subject = id_subject;
    }
}
