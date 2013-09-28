package com.compscicenter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: elizabeth
 * Date: 9/23/13
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class Record implements Serializable {
    String name;
    String number;

    Record(String newName, String newNumber) {
        this.name = newName;
        this.number = newNumber;
    }

}

