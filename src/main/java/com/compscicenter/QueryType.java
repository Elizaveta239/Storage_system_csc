package com.compscicenter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: elizabeth
 * Date: 9/23/13
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public enum QueryType implements Serializable{
    ADD,
    GET,
    DELETE,
    EXIT,
    UPDATE,
    ERROR;
}
