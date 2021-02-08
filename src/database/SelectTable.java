package database;

import java.io.RandomAccessFile;

public class SelectTable {

    public static void selectQuery(String command) throws Exception{
        String[] subString = command.split(" ");
        String[] conditions;
        String[] select_column;
        String[] temp = command.split("where");
        String[] temp1 = temp[0].split("from");
        String tableName = temp1[1].trim();
        String columns = temp1[0].replace("select","").trim();

        if(tableName.equals("davisbase_tables") || tableName.equals("davisbase_columns")){
            if(columns.contains("*")){
                select_column = new String[1];
                select_column[0] = "*";
            }else{
                select_column = columns.split(",");
                for (int i = 0; i < select_column.length; i++)
                    select_column[i] = select_column[i].trim();
            }if(temp.length > 1){
                String filter = temp[1].trim();
                conditions = DavisBase.parserEquation(filter);
            }else{
                conditions = new String[0];
            }
            if(tableName.equals("davisbase_tables")){
                select("data//davisbase_tables.tbl",tableName,select_column,conditions);
                System.out.println();
                return;
            }else{
                select("data//davisbase_columns.tbl",tableName,select_column,conditions);
                System.out.println();
                return;
            }
        }else{
            if(!DavisBase.ifTableAvailable(tableName)){
                System.out.println("Table "+tableName + " doesn't exist.");
                System.out.println("Please enter the correct table name");
                return;
            }
        }
        if(temp.length >1){
            String filter = temp[1].trim();
            conditions = DavisBase.parserEquation(filter);
        }else{
            conditions = new String[0];
        }
        if(columns.contains("*")){
            select_column = new String[1];
            select_column[0] = "*";
        }else{
            select_column = columns.split(",");
            for(int i =0 ; i<select_column.length; i++)
                select_column[i] = select_column[i].trim();
        }
        select("data//"+tableName+".tbl",tableName,select_column,conditions);
        System.out.println();

    }
    public static void select(String file, String table, String[] columns, String[] comp) throws Exception{
        try{
            Buffer buffer = new Buffer();
            String[] columnName = DataReader.getColName(table);
            String[] dt = DataReader.getDataType(table);

            RandomAccessFile rFile = new RandomAccessFile(file,"rw");
            DataReader.filter(rFile,comp,columnName,dt,buffer);
            buffer.display(columns);
            rFile.close();
        }catch(Exception e){
            System.out.println("Error at select");
            System.out.println(e);
        }
    }
}
