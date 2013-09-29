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

import java.io.File;

public class Main {



    private static void printInfoShort() {
        System.out.println("Storage system 'Telephone directory'");
        System.out.println("Elizaveta Shashkova, CSC, 2013");
    }

    public static void printInfoFull() {
        System.out.println("There are 4 types of commands: add, update, delete, get");
        System.out.println("add <name> <number>");
        System.out.println("update <name> <number>");
        System.out.println("delete <name>");
        System.out.println("get <name>");
        System.out.println("Print exit for exit.");
    }

    private static void logInfo(Parser p, Storage s) {
        File f = new File("log/Storage_system.log");
        if (f.exists()) {
            File fNew = new File("log/Storage_system.in.log");
            f.renameTo(fNew);
            s.init();

            System.out.println("Log file exists on your device");
            System.out.println("Would you like to restore your Storage system from it?(1 - true/0 - false)");
            Integer ans = p.nextInt();
            if (ans == 1) {
                System.out.println("Restoring...");
                s.restoreFromLog();
                System.out.println("Restoring is complete!");
            }
            fNew.deleteOnExit();
        } else {
            s.init();
        }
    }

    public static void main(String[] args) {

	    Parser parser = new Parser();
        Storage storage = new Storage();
        printInfoShort();
        logInfo(parser, storage);

        //storage.init();
        printInfoFull();

        while (parser.hasNextLine()) {
            Query query = parser.getString();
            if (query.type == QueryType.EXIT) {
                System.out.println("Bye-bye!");
                break;
            } else {
                storage.queryAction(query);
                storage.addLog(query);
                System.out.println("Print a command:");
            }
        }
    }
}
