package com.compscicenter;

/**
 * Created with IntelliJ IDEA.
 * User: elizabeth
 * Date: 9/23/13
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Query {
    Record rec;
    QueryType type;

    Query (Record newRec, QueryType newType) {
        this.rec = newRec;
        this.type = newType;
    }
}
