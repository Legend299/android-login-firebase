package com.LoginFirebase.Models;

public class User {

    private String Id;
    private String Name;
    private String LastName;
    private String Email;
    private String Password;
    private String Age;
    private String Phone;
    private String Address;
    private String Imagen;

    public User() {
    }

    public User(String id, String name, String lastName, String email, String password, String age, String phone, String address, String imagen) {
        Id = id;
        Name = name;
        LastName = lastName;
        Email = email;
        Password = password;
        Age = age;
        Phone = phone;
        Address = address;
        Imagen = imagen;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImagen(){
        return Imagen;
    }

    public void setImagen(String imagen){
        Imagen = imagen;
    }
}
