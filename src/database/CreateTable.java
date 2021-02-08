package database;

import java.io.File;
import java.io.RandomAccessFile;

public class CreateTable {

    public static final int pageSize = 512;

    public static void createQuery(String command) throws Exception{
        String[] subString = command.split(" ");
        if(subString[1].equals("table")){
            String tableName = subString[2];
            String[] temp = command.split(tableName);
            String tableSchema = temp[1].trim();
            int len = tableSchema.length();
            if(tableSchema.charAt(0) == '(' && tableSchema.charAt(len-1) == ')'){
                String[] columnames = tableSchema.substring(1,tableSchema.length()-1).split(",");
                for(int i =0;i< columnames.length;i++){
                    columnames[i] = columnames[i].trim();
                }
                if(!DavisBase.ifTableAvailable(tableName)){
//                    System.out.println("in if is table avaible");
                    createTable(tableName,columnames);
                    System.out.println("Table " +tableName +" created successfully. \n");
                }else{
                    System.out.println("Else");
                    System.out.println("Table "+tableName + " already exists. \n");
                }
            }else{
                System.out.println("Incorrect input. Please check the readme.txt file to know about the supported commands\n");
            }
        }else{
            System.out.println("Incorrect input. Please check the readme.txt file to know about the supported commands\n");
        }
    }



    public static void createTable(String tableName, String[] columns) throws Exception{
        try{
            RandomAccessFile createTableFile = new RandomAccessFile("data//"+tableName+".tbl","rw");
            createTableFile.setLength(pageSize);
            createTableFile.seek(0);
            createTableFile.writeByte(0x0D);
            createTableFile.close();

            createTableFile = new RandomAccessFile("data//davisbase_tables.tbl","rw");
            int num = DataReader.pages(createTableFile);
            int page = 1;
            for(int i = 1 ;4<= num; i++){
                int rightmost = Page.getRt(createTableFile,i);
                if(rightmost==0)
                    page = i;
            }
            int[] keys = Page.getKey(createTableFile,page);
            int l = keys[0];
            for(int i = 0; i<keys.length;i++)
                if(l<keys[i])
                    l=keys[i];
            createTableFile.close();
            String[] values = {Integer.toString(l+1),tableName};
            InsertTable.insertInto("davisbase_tables",values);
            RandomAccessFile catalogfile = new RandomAccessFile("data//davisbase_columns.tbl", "rw");
            Buffer buffer = new Buffer();
            String[] columnName = {"rowid", "table_name", "column_name", "data_type", "ordinal_position", "is_nullable"};
            String[] comparision = {};
            DataReader.filter(catalogfile, comparision, columnName, buffer);
            l = buffer.content.size();

            for(int i = 0; i < columns.length; i++){
                l = l + 1;
                String[] sub = columns[i].split(" ");
                String n = "YES";
                if(sub.length > 2)
                    n = "NO";
                String col_name = sub[0];
                String dt = sub[1].toUpperCase();
                String pos = Integer.toString(i+1);
                String[] v = {Integer.toString(l), tableName, col_name, dt, pos, n};
                InsertTable.insertInto("davisbase_columns", v);
            }
            catalogfile.close();
            createTableFile.close();
        }catch(Exception e){
            System.out.println("Error at createTable");
            e.printStackTrace();
        }
    }

    public static  void dropQuery (String command) throws Exception{
        String[] subs = command.split(" ");
        if(subs[1].equals("table")){
            String dropTable = subs[2];
            if (DavisBase.ifTableAvailable(dropTable)){
                dropTable(dropTable);
                System.out.println("Table "+dropTable+" dropped successfully.\n");
            }
            else{
                System.out.println("Table " + dropTable + " does not exist.\n");
            }

        }
        else{
            System.out.println("Incorrect input. Please check the readme.txt file to know the supported commands\n");
        }
    }

    public static void dropTable(String tableName) throws Exception{
        try {
            RandomAccessFile dropFile = new RandomAccessFile("data//davisbase_tables.tbl","rw");
            int num = DataReader.pages(dropFile);
            for(int page = 1; page<= num;page++){
                dropFile.seek((page-1)*pageSize);
                byte kind = dropFile.readByte();
                if(kind==0x05)
                    continue;
                else{
                    short[] cells = Page.getCellArr(dropFile,page);
                    int i = 0;
                    for(int j = 0;j<cells.length;j++){
                        long loc = Page.getCellLoc(dropFile,page,j);
                        String[] pl = DataReader.retrievePayload(dropFile,loc);
                        String tb = pl[1];
                        if(!tb.equals(tableName)){
                            Page.setCellOffset(dropFile,page,i,cells[j]);
                            i++;
                        }
                    }
                    Page.setCellNum(dropFile,page,(byte)i);
                }
            }
            dropFile = new RandomAccessFile("data//davisbase_columns.tbl","rw");
            num = DataReader.pages(dropFile);
            for(int page = 1; page <= num; page ++){
                dropFile.seek((page-1)*pageSize);
                byte kind = dropFile.readByte();
                if(kind == 0x05)
                    continue;
                else{
                    short[] cells = Page.getCellArr(dropFile, page);
                    int i = 0;
                    for(int j = 0; j < cells.length; j++){
                        long location = Page.getCellLoc(dropFile, page, j);
                        String[] pl = DataReader.retrievePayload(dropFile, location);
                        String tb = pl[1];

                        if(!tb.equals(tableName))
                        {
                            Page.setCellOffset(dropFile, page, i, cells[j]);
                            i++;
                        }
                    }
                    Page.setCellNum(dropFile, page, (byte)i);
                }
            }
            dropFile.close();
           File drop = new File("data//", tableName+".tbl");
            drop.delete();
        }catch(Exception e ){
            System.out.println("error during drop");
            System.out.println(e);
        }
    }
}
