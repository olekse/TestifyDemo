package com.olek.testify.model;

/*

ID   INT              NOT NULL,
   NAME VARCHAR (36)     NOT NULL,
   SURNAME VARCHAR (36)  NOT NULL,
   EMAIL VARCHAR (46)    NOT NULL,
   IMAGE BLOB NOT NULL,
   ID_StudentGroup INT,
   PRIMARY KEY (ID),
   FOREIGN KEY (ID_StudentGroup) REFERENCES StudentGroup(ID)
 */

@MLHTable(tableName = Table.T_STUDENT)
public class Student extends TableAdapter {

    @MLHField(fieldName = "ID")
    private int id;
    @MLHField(fieldName = "NAME")
    private String name;
    @MLHField(fieldName = "SURNAME")
    private String surname;
    @MLHField(fieldName = "IMAGE")
    private byte[] image;
    @MLHField(fieldName = "ID_StudentGroup")
    private Integer id_studentGroup;
    @MLHField(fieldName = "EMAIL")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Student() {
    }

    public Student(int id, String name, String surname, byte[] image, int id_studentGroup) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.id_studentGroup = id_studentGroup;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId_studentGroup() {
        return id_studentGroup;
    }

    public void setId_studentGroup(int id_studentGroup) {
        this.id_studentGroup = id_studentGroup;
    }
}
