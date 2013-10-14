package com.compscicenter;

import java.util.Scanner;

/**
 * Class for parsing requests from command line
 *
 */
public class Parser {
    private Scanner scanner;

    Parser() {
        scanner = new Scanner(System.in);
    }

    public boolean hasNextLine() {
        return this.scanner.hasNextLine();
    }

    public Integer nextInt() {
        return this.scanner.nextInt();
    }

    protected Query getString() {
        String str = this.scanner.next();
        str = str.toLowerCase();

        QueryType type;
        if (str.startsWith("exit")) {
            type = QueryType.EXIT;
        } else if (str.startsWith("add")) {
            type = QueryType.ADD;
        } else if (str.startsWith("update")) {
            type = QueryType.UPDATE;
        } else if (str.startsWith("get")) {
            type = QueryType.GET;
        } else if (str.startsWith("delete")) {
            type = QueryType.DELETE;
        } else {
            type = QueryType.ERROR;
        }

        if ((type == QueryType.EXIT) || (type == QueryType.ERROR)) {
            this.scanner.nextLine();
            return new Query(null, type);
        }

        String name;
        if (this.scanner.hasNext()) {
            name = this.scanner.next();
            if (!Character.isLetter(name.charAt(0))) {
                this.scanner.nextLine();
                return new Query(null, QueryType.ERROR);
            }
            if ((type == QueryType.DELETE) || (type == QueryType.GET)) {
                return new Query(new Record(name, null), type);
            }
        } else {
            this.scanner.nextLine();
            return new Query(null, QueryType.ERROR);
        }

        if (this.scanner.hasNext()) {
            String number = this.scanner.next();
            if (number.matches("\\+?[0-9]+")) {
                return new Query(new Record(name, number), type);
            } else {
                this.scanner.nextLine();
                return new Query(null, QueryType.ERROR);
            }
        } else {
            this.scanner.nextLine();
            return new Query(null, QueryType.ERROR);
        }
    }

}
