package com.compscicenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: elizabeth
 * Date: 9/30/13
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */

public class StorageTest  {
    private Storage storage;
    private static int N = 100;
    private static String[] names = new String[N];
    private static String[] numbers = new String[N];

    @Before
    public void tesUp() throws Exception {
        storage = new Storage();
        generatePeople();
    }

    private static void generatePeople() {
        for (int i = 0; i < N; ++i) {
            names[i] = "person" + i;
            numbers[i] = Integer.toString(i);
        }
    }

    private static void generateAlphabetPeople() {
        char a = 'a';
        for (int i = 0; i < 26; ++i) {
            names[i] = Character.toString(a);
            numbers[i] = Integer.toString(i);
            a++;
        }
    }

    @Test
    public void testAddRecord() throws Exception {
        for (int i = 0; i < N; i++) {
            storage.addRecord(new Record(names[i], numbers[i]));
            assertEquals(storage.getRecord(names[i]).number, numbers[i]);
        }
    }

    @Test
    public void testGetStorageID() throws Exception {
        generateAlphabetPeople();
        for (int i = 0; i < 26; i++) {
            if (i < 13) {
                assertEquals(storage.getStorageId(names[i]), 0);
            } else {
                assertEquals(storage.getStorageId(names[i]), 1);
            }
        }
    }

    @Test
    public void testDeleteRecord() throws Exception {
        for (int i = 0; i < N; i++) {
            storage.deleteRecord(names[i]);
            assertEquals(storage.getRecord(names[i]), null);
        }
    }



}
