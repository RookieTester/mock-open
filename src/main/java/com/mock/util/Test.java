package com.mock.util;

/**
 * Created by Administrator on 2017/1/4.
 */
public class Test {
    private Test(){
        throw new AssertionError();
    }
    public static void main(String[] args) {
        Test test=new Test();
    }
}
