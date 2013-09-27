package com.compscicenter;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for parsing requests from command line
 *
 */
public class Parser {
    Scanner scanner;
    Pattern nameNumber;

    Parser() {
        scanner = new Scanner(System.in);
        nameNumber = Pattern.compile("[a-zA-Z]+ [0-9]+");

    }

    protected Query getString() {
        String str = this.scanner.next();
        str.toLowerCase();
        str.trim();
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
