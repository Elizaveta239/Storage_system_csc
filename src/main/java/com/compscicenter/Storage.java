package com.compscicenter;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for storage
 *
 */

public class Storage <K, V> {
    //private ConcurrentHashMap storage;
    private ConcurrentHashMap[] storageArr;
    private FileOutputStream logFileOut;
    private ObjectOutputStream objLogFileOut;
    private FileInputStream logFileIn;
    private ObjectInputStream objLogFileIn;
    private Save save;
    private static int NUMBER_OF_NODES = 2;
    private static int NUMBER_OF_LETTERS = 26;
    private static int SIZE_OF_NODE = NUMBER_OF_LETTERS / NUMBER_OF_NODES;
    private static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    Storage() {
        //storage = new ConcurrentHashMap<K, V>();
        storageArr = new ConcurrentHashMap[NUMBER_OF_NODES];
        for (int i = 0; i < NUMBER_OF_NODES; ++i) {
            storageArr[i] = new ConcurrentHashMap<K, V>();
        }
        try {
            //this.logFileOut = new FileOutputStream("log/Storage_system.log");
            //this.objLogFileOut = new ObjectOutputStream(logFileOut);
        } catch (Exception e) {
            System.out.println("Log file not found!");
        }
    }

    public void init() {
        try {
            //File fNew = new File("log/Storage_system.in.log");
            //File fOld = new File("log/Storage_system.in.log");
            //fOld.renameTo(fNew);
            //this.logFileOut = new FileOutputStream(fNew, true);
            this.logFileOut = new FileOutputStream("log/Storage_system.log", true);
            this.objLogFileOut = new ObjectOutputStream(logFileOut);
        } catch (Exception e) {
            System.out.println("Log file not found!");
        }
    }

    public void addLog(Query q) {
        if ((q.type == QueryType.ADD) || (q.type == QueryType.DELETE) || (q.type == QueryType.UPDATE)) {
            save = new Save(q);
            try {
                objLogFileOut.writeObject(save);
                objLogFileOut.flush();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERROR: Unable to write log!");
            }

        }
    }

    private static int indexInAlphabet(Character c) {
        return ALPHABET.indexOf(c);
    }

    private int getStorageId(Record rec) {
        String temp = rec.name.toLowerCase();
        char c = temp.charAt(0);
        int k = indexInAlphabet(c) / SIZE_OF_NODE;
        return indexInAlphabet(c) / SIZE_OF_NODE;
    }

    public int getStorageId(String name) {
        String temp = name.toLowerCase();
        char c = temp.charAt(0);
        return indexInAlphabet(c) / SIZE_OF_NODE;
    }

    public void restoreFromLog() {
        try {
            this.logFileIn = new FileInputStream("log/Storage_system.in.log");
            this.objLogFileIn = new ObjectInputStream(logFileIn);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Unable to read from log file!");
        }
        try {
            Save save;
            while (true) {
                save = (Save) objLogFileIn.readObject();
                this.queryAction(save.q);
                addLog(save.q);
            }
        } catch (EOFException e) {
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Unable to read from log file!");
        }
    }

    public boolean isExist(Record rec) {
        return storageArr[this.getStorageId(rec)].containsKey(rec.name);
    }

    public boolean addRecord(Record rec) {
        boolean isExist = storageArr[this.getStorageId(rec)].containsKey(rec.name);
        storageArr[this.getStorageId(rec)].put(rec.name, rec.number);
        return isExist;
    }

    public Record getRecord(String name) {
        boolean isExist = storageArr[this.getStorageId(name)].containsKey(name);
        if (isExist) {
            return new Record(name, (String)storageArr[this.getStorageId(name)].get(name));
        } else {
            return null;
        }
    }

    public Record deleteRecord(String name) {
        boolean isExist = storageArr[this.getStorageId(name)].containsKey(name);
        if (isExist) {
            return new Record(name, (String)storageArr[this.getStorageId(name)].remove(name));
        } else {
            return null;
        }
    }

    public void queryAction(Query query) {
        switch (query.type) {
            case ADD : {
                if (this.isExist(query.rec)) {
                    System.out.println("This person "  + query.rec.name + " is already exists. Try command 'update'");
                    query.type = QueryType.ERROR;
                } else {
                    this.addRecord(query.rec);
                    System.out.println("The record with person "  + query.rec.name + " was created");
                }
                break;
            }
            case GET : {
                Record rec = this.getRecord(query.rec.name);
                if (rec != null) {
                    System.out.println(rec.number);
                } else {
                    System.out.println("This person doesn't exist!");
                    query.type = QueryType.ERROR;
                }
                break;
            }
            case DELETE : {
                Record rec = this.deleteRecord(query.rec.name);
                if (rec != null) {
                    System.out.println("Person " + rec.name + " with number " + rec.number + " was deleted") ;
                } else {
                    System.out.println("This person doesn't exist!");
                    query.type = QueryType.ERROR;
                }
                break;
            }
            case UPDATE : {
                if (this.isExist(query.rec)) {
                    this.addRecord(query.rec);
                    System.out.println("Record for person " + query.rec.name +" was updated");
                } else {
                    System.out.println("This person doesn't exist!");
                    query.type = QueryType.ERROR;
                }
                break;
            }
            case ERROR : {
                System.out.println("ERROR! Try again!");
                break;
            }
        }

    }
}
