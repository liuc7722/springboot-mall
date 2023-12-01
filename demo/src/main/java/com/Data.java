package com;

public class Data {
    private int id;
    private String name;

    public Data(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getid(){
        return id;
    }

    public String getname(){
        return name;
    }

    public void setid(int id){
        this.id = id;
    }

    public void setname(String name){
        this.name = name;
    }
}
