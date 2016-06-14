package com.example.lsh.cycleclubapp;

/**
 * Created by user on 2015-08-18.
 */
public class selectedItem {
    private static int count=0;

    public static int setSelectedNum(int num){
        count=num;
        return count;
    }
    public static int getSelectedNum(){
        return count;
    }
}
