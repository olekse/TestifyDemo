package com.olek.testify.model;

/*
   ID   INT              NOT NULL,
   NAME VARCHAR (36)     NOT NULL,
   SURNAME TEXT      NOT NULL,

   PRIMARY KEY (ID)
 */

@MLHTable(tableName = Table.T_SUBJECT)
public class Subject extends TableAdapter {

    @MLHField(fieldName = "ID")
    private int id;
    @MLHField(fieldName = "NAME")
    private String name;
    @MLHField(fieldName = "DESCRIPTION")
    private String description;

    public Subject() {
    }

    public Subject(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
