package ru.wizand.contactsman.db.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {

    //1 - Constants for Database
//    public static final String TABLE_NAME = "contacts";

//    public static final String COLUMN_ID = "contact_id";
//    public static final String COLUMN_NAME = "contact_name";
//    public static final String COLUMN_EMAIL = "contact_email";
//    public static final String COLUMN_PHONE = "contact_phone";

    // 2- Variables
    @ColumnInfo(name = "contact_name")
    private String name;

    @ColumnInfo(name = "contact_email")
    private String email;

    @ColumnInfo(name = "contact_id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contact_phone")
    private String phone;

    // 3 - Constructor
    @Ignore
    public Contact() {

    }

    @Ignore
    public Contact(String name, String email, int id, String phone) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.phone = phone;
    }

    public Contact(String name, String email, String phone, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.phone = phone;
    }

    //4 - Getters and Setters
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    // 5- SQL Querry
    //It is not need for room database (only for sqlite)
//    public static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + "("
//                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                    + COLUMN_NAME + " TEXT,"
//                    + COLUMN_EMAIL + " TEXT,"
//                    + COLUMN_PHONE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
//                    + ")";







}
