package com.compscicenter;

/*
* Storage system
* Created for Computer Science Center course "Databases"
*
* This storage systen is a telephone directory.
* There are 4 standart CRUD requests.
*
* Elizaveta Shashkova
*
* */

public class Main {

    private static void queryAction(Query query, Storage storage) {
        switch (query.type) {
            case ADD : {
                if (storage.isExist(query.rec)) {
                    System.out.println("This person is already exists. Try command 'update'");
                } else {
                    storage.addRecord(query.rec);
                    System.out.println("The record with this person was created");
                }
                break;
            }
            case GET : {
                Record rec = storage.getRecord(query.rec.name);
                if (rec != null) {
                    System.out.println(rec.number);
                } else {
                    System.out.println("This person doesn't exist!");
                }
                break;
            }
            case DELETE : {
                Record rec = storage.deleteRecord(query.rec.name);
                if (rec != null) {
                    System.out.println("Person " + rec.name + " with number " + rec.number + " was deleted") ;
                } else {
                    System.out.println("This person doesn't exist!");
                }
                break;
            }
            case UPDATE : {
                if (storage.isExist(query.rec)) {
                    storage.addRecord(query.rec);
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

    private static void printInfo() {
        System.out.println("Storage system 'Telephone directory'");
        System.out.println("Elizaveta Shashkova, CSC, 2013");
        System.out.println("There are 4 types of commands: add, update, delete, get");
        System.out.println("add <name> <number>");
        System.out.println("update <name> <number>");
        System.out.println("delete <name>");
        System.out.println("get <name>");
        System.out.println("Print exit for exit.");
    }

    public static void main(String[] args) {

	    Parser parser = new Parser();
        Storage storage = new Storage();
        printInfo();

        while (parser.scanner.hasNextLine()) {
            Query query = parser.getString();
            if (query.type == QueryType.EXIT) {
                System.out.println("Bye-bye!");
                break;
            } else {
                queryAction(query, storage);
                System.out.println("Print a command:");
            }
        }
    }
}
