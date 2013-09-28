package com.compscicenter;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for storage
 *
 */

public class Storage <K, V> {
    private ConcurrentHashMap storage;
    private FileOutputStream logFileOut;
    private ObjectOutputStream objLogFileOut;
    private FileInputStream logFileIn;
    private ObjectInputStream objLogFileIn;
    private Save save;

    Storage() {
        storage = new ConcurrentHashMap<K, V>();
        try {
            //this.logFileOut = new FileOutputStream("log/Storage_system.log");
            //this.objLogFileOut = new ObjectOutputStream(logFileOut);
        } catch (Exception e) {
            System.out.println("Log file not found!");
        }
    }

    public void init() {
        try {
            this.logFileOut = new FileOutputStream("log/Storage_system.log");
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

    public void restoreFromLog() {
        try {
            this.logFileIn = new FileInputStream("log/Storage_system.log.in");
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
            }
        } catch (EOFException e) {
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Unable to read from log file!");
        }


    }

    public boolean isExist(Record rec) {
        return storage.containsKey(rec.name);
    }

    public boolean addRecord(Record rec) {
        boolean isExist = storage.containsKey(rec.name);
        storage.put(rec.name, rec.number);
        return isExist;
    }

    public Record getRecord(String name) {
        boolean isExist = storage.containsKey(name);
        if (isExist) {
            return new Record(name, (String)storage.get(name));
        } else {
            return null;
        }
    }

    public Record deleteRecord(String name) {
        boolean isExist = storage.containsKey(name);
        if (isExist) {
            return new Record(name, (String)storage.remove(name));
        } else {
            return null;
        }
    }

    public void queryAction(Query query) {
        switch (query.type) {
            case ADD : {
                if (this.isExist(query.rec)) {
                    System.out.println("This person "  + query.rec.name + " is already exists. Try command 'update'");
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
                }
                break;
            }
            case DELETE : {
                Record rec = this.deleteRecord(query.rec.name);
                if (rec != null) {
                    System.out.println("Person " + rec.name + " with number " + rec.number + " was deleted") ;
                } else {
                    System.out.println("This person doesn't exist!");
                }
                break;
            }
            case UPDATE : {
                if (this.isExist(query.rec)) {
                    this.addRecord(query.rec);
                    System.out.println("Record for person " + query.rec.name +" was updated");
                } else {
                    System.out.println("This person doesn't exist!");
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
