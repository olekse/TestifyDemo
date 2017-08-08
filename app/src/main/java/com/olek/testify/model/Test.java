package com.olek.testify.model;


import java.io.Serializable;

/*
	ID INT NOT NULL,
 NAME VARCHAR (36)     NOT NULL,
DESCRIPTION TEXT NOT NULL,
ID_COURSE INT NOT NULL,
PRIMARY KEY (ID),
FOREIGN KEY (ID_COURSE) REFERENCES Course(ID)
 */
@MLHTable(tableName = Table.T_TEST)
public class Test extends TableAdapter implements Serializable{

    @MLHField(fieldName = "ID")
    private int id;
    @MLHField(fieldName = "NAME")
    private String name;
    @MLHField(fieldName = "DESCRIPTION")
    private String description;
    @MLHField(fieldName = "ID_COURSE")
    private int id_course;

    public Test() {
    }

    public Test(int id, String name, String description, int id_course) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.id_course = id_course;
    }

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
