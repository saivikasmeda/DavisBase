package database;

import java.io.RandomAccessFile;

public class InsertTable {
    public static void insertQuery(String command) throws Exception{
        String[] subString = command.split(" ");
        String insert_tableName = subString[2];
        if( command.contains("values")) {
            String insert_val = command.split("values")[1].trim();
            insert_val = insert_val.substring(1, insert_val.length() - 1);
            String[] insert_values = insert_val.split(",");

            for (int i = 0; i < insert_values.length; i++) {
                insert_values[i] = insert_values[i].trim();
            }

            if (!DavisBase.ifTableAvailable(insert_tableName)) {
                System.out.println("Table " + insert_tableName + " doesn't exist. \n");
                System.out.println("please check command and retry. Refer readme file // Help for insert command format");
                return;
            }
            RandomAccessFile insertFile;
            try {
                insertFile = new RandomAccessFile("data//" + insert_tableName + ".tbl", "rw");
               if(insertInto(insertFile, insert_tableName, insert_values))
                     System.out.println("Inserted Successfully\n");
                insertFile.close();
            } catch (Exception e) {
                System.out.println("Error in insertion");
                e.printStackTrace();
            }
        }else{
            System.out.println("please check command and retry. Refer readme file // Help for insert command format");
        }
    }


    public static boolean insertInto(RandomAccessFile file, String table, String[] values)  throws Exception{
        try {
            String[] dt = DataReader.getDataType(table);
            String[] nullable = DataReader.getNullable(table);

            for (int i = 0; i < nullable.length; i++)
                if (values[i].equals("null") && nullable[i].equals("NO")) {
                    System.out.println("NULL constraint violation");
                    return false;
                }
            int sk = new Integer(values[0]);
            int pageno = DataReader.searchKey(file, sk);
            if (pageno != 0)
                if (Page.hasKey(file, pageno, sk)) {
                    System.out.println("Uniqueness constraint violation");
                    System.out.println();
                    return false;
                }
            if (pageno == 0)
                pageno = 1;


            byte[] bt = new byte[dt.length - 1];
            short plSize = (short) (DataReader.calPayloadSize(table, values, bt));
            int cellSize = plSize + 6;
            int offset = Page.checkLeafSpace(file, pageno, cellSize);

            if (offset != -1) {
                 return Page.insertLfCell(file, pageno, offset, plSize, sk, bt, values, table);
            } else {
                Page.splitlf(file, pageno);
                insertInto(file, table, values);
            }
            return true;
        }catch ( Exception e ){
            System.out.println(e.getLocalizedMessage());
            System.out.println("Check entered command and try again. Refer README file for command format");
            return false;
        }
    }
    public static void insertInto(String table, String[] values) throws Exception
    {
        try
        {
            RandomAccessFile insertfile = new RandomAccessFile("data//"+table+".tbl", "rw");
            insertInto(insertfile, table, values);
            insertfile.close();

        }
        catch(Exception e)
        {
            System.out.println("Error while inserting the data");
            e.printStackTrace();
        }
    }

}
