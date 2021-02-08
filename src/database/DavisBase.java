package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Scanner;



public class DavisBase {
    static Scanner scanner = new Scanner(System.in).useDelimiter(";");
    static String prompt = "davissql> ";
    static String version = "v1.0";
    static boolean isExit = false;
//    static String currentDB = "user_data";

    public static void main(String[] args){
        InitializeDB.initDB();
        promptScreen();
        String userCommand = "";

        while(!isExit){
            System.out.print(prompt);
            userCommand = scanner.next().replace("\n"," ").replace("\r"," ");
            userCommand = userCommand.trim().toLowerCase();
            ExecuteCommandHandler.parse(userCommand);
        }
        System.out.println("Thank you for using DavisBase. Now exiting");

    }

    public static void promptScreen(){
        System.out.println(line("-",80));
        System.out.println("Welcome to DavisBase");
        System.out.println("DavisBase Version " + version);
        System.out.println("\n Type \"help\" to display supported commands.");
        System.out.println(line("-",80));
    }
    public static String line(String s, int num){
        String res = "";
        for (int i=0;i<num;i++){
            res +=s;
        }
        return res;
    }

    public static void help() {
        System.out.println(line("*",80));
        System.out.println("SUPPORTED COMMANDS");
        System.out.println("All commands below are case insensitive");
        System.out.println();
        System.out.println("\tSHOW TABLES;                                                 Display all the tables in the database.");
        System.out.println("\tCREATE TABLE table_name (<column_name datatype>);            Create a new table in the database.");
        System.out.println("\tINSERT INTO table_name VALUES (value1,value2,..);            Insert a new record into the table.");
        System.out.println("\tSELECT * FROM table_name;                                    Display all records in the table.");
        System.out.println("\tSELECT * FROM table_name WHERE column_name operator value;   Display records in the table where the given condition is satisfied.");
        System.out.println("\tDROP TABLE table_name;                                       Remove table data and its schema.");
        System.out.println("\tVERSION;                                                     Show the program version.");
        System.out.println("\tHELP;                                                        Show this help information.");
        System.out.println("\tEXIT;                                                        Exit DavisBase.");
        System.out.println();
        System.out.println();
        System.out.println(line("*",80));
    }

    public static boolean ifTableAvailable(String table){
        try{
            File userdataFolder = new File("data");
            String[] usertables;
            usertables = userdataFolder.list();
            String tableName = table + ".tbl";
//            System.out.println(tableName);
//            System.out.println(usertables.length);
            for(int i=0;i<usertables.length;i++){
//                System.out.println(usertables[i]);
                if(usertables[i].equals(tableName))
//                    System.out.println("returnign");
                    return true;
            }
        }catch(SecurityException sec){
            System.out.println("Unable to create data container directory: "+ sec);
        }
        return false;
    }

    public static String[] parserEquation(String equation){
        String commands[] = new String[3];
        String temp[] = new String[2];
        if(equation.contains("=")){
            temp = equation.split("=");
            commands[0] = temp[0].trim();
            commands[1] = "=";
            commands[2] = temp[1].trim();
        }
        if(equation.contains(">")){
            temp = equation.split(">");
            commands[0] = temp[0].trim();
            commands[1] = ">";
            commands[2] = temp[1].trim();
        }
        if (equation.contains("<")) {
            temp = equation.split("<");
            commands[0] = temp[0].trim();
            commands[1] = "<";
            commands[2] = temp[1].trim();
        }

        if (equation.contains(">=")) {
            temp = equation.split(">=");
            commands[0] = temp[0].trim();
            commands[1] = ">=";
            commands[2] = temp[1].trim();
        }

        if (equation.contains("<=")) {
            temp = equation.split("<=");
            commands[0] = temp[0].trim();
            commands[1] = "<=";
            commands[2] = temp[1].trim();
        }
        if (equation.contains("<>")) {
            temp = equation.split("<>");
            commands[0] = temp[0].trim();
            commands[1] = "<>";
            commands[2] = temp[1].trim();
        }
        return commands;
    }


}
