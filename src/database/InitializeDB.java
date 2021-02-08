package database;

import java.io.File;
import java.io.RandomAccessFile;

public class InitializeDB {

    private static RandomAccessFile davisTables;
    private static RandomAccessFile davisColumns;
    public static final int pageSize = 512;

    public static void initDB() {
        try {
            File dataFile = new File("data");
            if (!dataFile.exists()) {
                dataFile.mkdir();
                System.out.println("Data Folder is not yet created, creating now ");
                initializeDataStore();
            }else{
                boolean tables = false;
                boolean columns = false;
                String davis_tables = "davisbase_tables.tbl";
                String davis_columns = "davisbase_columns.tbl";
                String[] dataList = dataFile.list();

                for(int i=0;i<dataList.length;i++){
                    if(dataList[i].equals(davis_tables))
                        tables = true;
                }
                if(!tables){
                    System.out.println("Table davisbase_tables.tbl not yet created, initializing davisbase_tables");
                    System.out.println();
                    initializeDataStore();
                }
                for(int i=0;i<dataList.length;i++){
                    if(dataList[i].equals(davis_columns))
                        columns = true;
                }
                if(!columns){
                    System.out.println("Table davisbase_columns.tbl not yet created, initializing davisbase_columns");
                    System.out.println();
                    initializeDataStore();
                }

            }

        } catch (SecurityException sec) {
            System.out.println("Security exception " + sec);
        }

    }

    public static void initializeDataStore()  {
        try {
            File dataFile = new File("data");
            String[] oldFiles;
            oldFiles = dataFile.list();
            for (int i = 0; i < oldFiles.length; i++) {
                File anOldFile = new File(dataFile, oldFiles[i]);
                anOldFile.delete();
            }
        } catch (SecurityException sec) {
            System.out.println("Unable to create data directory: " + sec);
        }

        try {
            davisTables = new RandomAccessFile("data//davisbase_tables.tbl", "rw");
            davisTables.setLength(pageSize);
            davisTables.seek(0);
            davisTables.write(0x0D);
            davisTables.write(0x02);
            int[] offset = new int[2];
            int size1 = 24;
            int size2 = 25;
            offset[0] = pageSize - size1;
            offset[1] = offset[0] - size2;
            davisTables.writeShort(offset[1]);
            davisTables.writeInt(0);
            davisTables.writeInt(10);
            davisTables.writeShort(offset[1]);
            davisTables.writeShort(offset[0]);
            davisTables.seek(offset[0]);
            davisTables.writeShort(20);
            davisTables.writeInt(1);
            davisTables.writeByte(1);
            davisTables.writeByte(28);
            davisTables.writeBytes("davisbase_tables");
            davisTables.seek(offset[1]);
            davisTables.writeShort(21);
            davisTables.writeInt(2);
            davisTables.writeByte(1);
            davisTables.writeByte(29);
            davisTables.writeBytes("davisbase_columns");
        } catch (Exception e) {
            System.out.println("Unable to create the database_tables file");
            System.out.println(e);
        }
        try {
            davisColumns = new RandomAccessFile("data//davisbase_columns.tbl", "rw");
            davisColumns.setLength(pageSize);
            davisColumns.seek(0);
            davisColumns.writeByte(0x0D);
            davisColumns.writeByte(0x08);
            int[] offset = new int[10];
            offset[0] = pageSize - 43;
            offset[1] = offset[0] - 47;
            offset[2] = offset[1] - 44;
            offset[3] = offset[2] - 48;
            offset[4] = offset[3] - 49;
            offset[5] = offset[4] - 47;
            offset[6] = offset[5] - 57;
            offset[7] = offset[6] - 49;
            offset[8] = offset[7] - 49;
            davisColumns.writeShort(offset[8]);
            davisColumns.writeInt(0);
            davisColumns.writeInt(0);

            for (int i = 0; i < 9; i++)
                davisColumns.writeShort(offset[i]);


            davisColumns.seek(offset[0]);
            davisColumns.writeShort(33);
            davisColumns.writeInt(1);
            davisColumns.writeByte(5);
            davisColumns.writeByte(28);
            davisColumns.writeByte(17);
            davisColumns.writeByte(15);
            davisColumns.writeByte(4);
            davisColumns.writeByte(14);

            davisColumns.writeBytes("davisbase_tables");
            davisColumns.writeBytes("rowid");
            davisColumns.writeBytes("INT");
            davisColumns.writeByte(1);
            davisColumns.writeBytes("NO");


            davisColumns.seek(offset[1]);
            davisColumns.writeShort(39);
            davisColumns.writeInt(2);
            davisColumns.writeByte(5);
            davisColumns.writeByte(28);
            davisColumns.writeByte(22);
            davisColumns.writeByte(16);
            davisColumns.writeByte(4);
            davisColumns.writeByte(14);

            davisColumns.writeBytes("davisbase_tables");
            davisColumns.writeBytes("table_name");
            davisColumns.writeBytes("TEXT");
            davisColumns.writeByte(2);
            davisColumns.writeBytes("NO");


            davisColumns.seek(offset[2]);
            davisColumns.writeShort(34);
            davisColumns.writeInt(3);
            davisColumns.writeByte(5);
            davisColumns.writeByte(29);
            davisColumns.writeByte(17);
            davisColumns.writeByte(15);
            davisColumns.writeByte(4);
            davisColumns.writeByte(14);

            davisColumns.writeBytes("davisbase_columns");
            davisColumns.writeBytes("rowid");
            davisColumns.writeBytes("INT");
            davisColumns.writeByte(1);
            davisColumns.writeBytes("NO");


            davisColumns.seek(offset[3]);
            davisColumns.writeShort(40);
            davisColumns.writeInt(4);
            davisColumns.writeByte(5);
            davisColumns.writeByte(29);
            davisColumns.writeByte(22);
            davisColumns.writeByte(16);
            davisColumns.writeByte(4);
            davisColumns.writeByte(14);

            davisColumns.writeBytes("davisbase_columns");
            davisColumns.writeBytes("table_name");
            davisColumns.writeBytes("TEXT");
            davisColumns.writeByte(2);
            davisColumns.writeBytes("NO");


            davisColumns.seek(offset[4]);
            davisColumns.writeShort(41);
            davisColumns.writeInt(5);
            davisColumns.writeByte(5);
            davisColumns.writeByte(29);
            davisColumns.writeByte(23);
            davisColumns.writeByte(16);
            davisColumns.writeByte(4);
            davisColumns.writeByte(14);

            davisColumns.writeBytes("davisbase_columns");
            davisColumns.writeBytes("column_name");
            davisColumns.writeBytes("TEXT");
            davisColumns.writeByte(3);
            davisColumns.writeBytes("NO");


            davisColumns.seek(offset[5]);
            davisColumns.writeShort(39);
            davisColumns.writeInt(6);
            davisColumns.writeByte(5);
            davisColumns.writeByte(29);
            davisColumns.writeByte(21);
            davisColumns.writeByte(16);
            davisColumns.writeByte(4);
            davisColumns.writeByte(14);
            davisColumns.writeBytes("davisbase_columns");
            davisColumns.writeBytes("data_type");
            davisColumns.writeBytes("TEXT");
            davisColumns.writeByte(4);
            davisColumns.writeBytes("NO");


            davisColumns.seek(offset[6]);
            davisColumns.writeShort(49);
            davisColumns.writeInt(7);
            davisColumns.writeByte(5);
            davisColumns.writeByte(29);
            davisColumns.writeByte(28);
            davisColumns.writeByte(19);
            davisColumns.writeByte(4);
            davisColumns.writeByte(14);

            davisColumns.writeBytes("davisbase_columns");
            davisColumns.writeBytes("ordinal_position");
            davisColumns.writeBytes("TINYINT");
            davisColumns.writeByte(5);
            davisColumns.writeBytes("NO");

            davisColumns.seek(offset[7]);
            davisColumns.writeShort(41);
            davisColumns.writeInt(8);
            davisColumns.writeByte(5);
            davisColumns.writeByte(29);
            davisColumns.writeByte(23);
            davisColumns.writeByte(16);
            davisColumns.writeByte(4);
            davisColumns.writeByte(14);
            davisColumns.writeBytes("davisbase_columns");
            davisColumns.writeBytes("is_nullable");
            davisColumns.writeBytes("TEXT");
            davisColumns.writeByte(6);
            davisColumns.writeBytes("NO");
        } catch (Exception e) {
            System.out.println("Unable to create the database_columns file");
            System.out.println(e);
        }


    }
}
