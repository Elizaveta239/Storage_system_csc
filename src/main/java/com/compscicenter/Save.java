package com.compscicenter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: elizabeth
 * Date: 9/29/13
 * Time: 12:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class Save implements Serializable {
    public Query q;

    Save(Query newQuery) {
        this.q = newQuery;
    }
}
