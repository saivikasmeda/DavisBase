package database;

public class ExecuteCommandHandler {

    public static void parse(String query) {
        try {
            String[] subString = query.split(" ");
            switch (subString[0]) {
                case "create":
                    CreateTable.createQuery(query);
                    break;

                case "insert":
                    InsertTable.insertQuery(query);
                    break;

                case "select":
                    SelectTable.selectQuery(query);
                    break;

                case "drop":
                    CreateTable.dropQuery(query);
                    break;

                case "show":
                    String table = subString[1];
                    if (table.equals("tables")) {
                        ShowTables.ShowTabs();
                    } else {
                        System.out.println("\n Incorrect input. Please check the README.txt file to know the format of the command.");
                    }
                    break;

                case "exit":
                    DavisBase.isExit = true;
                    System.out.println();
                    break;
                case "help":
                    help();
                    break;
                case "version":
                    System.out.println("\nDavisBase: Version 1.0\n");
                    break;

                default:
                    System.out.println("\nIncorrect input. Please check the readme.txt file to know the supported commands\n");
                    break;
            }
        }catch ( Exception e ){
            System.out.println(e.getLocalizedMessage());
            System.out.println("Check entered command and try again. Refer README file for command format");
        }
    }
    public static void help() {
        System.out.println(DavisBase.line("*",80));
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
        System.out.println(DavisBase.line("*",80));
    }
}

