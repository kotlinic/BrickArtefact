package com.plugin.lib;

public class MyClass {
    public void init(int max)
    {
        System.out.println("init..."+max);
    }
    public static void main(String[] args) {
        System.out.println("zm123");
        new MyClass().init(666);
    }

    public String desc(){
        System.out.println("desc..."+this.toString());
        return this.toString();
    }
}
