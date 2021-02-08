package database;

import javax.xml.crypto.Data;
import java.io.RandomAccessFile;

public class ShowTables {

    public static void ShowTabs() throws  Exception{
        String[] columns = {"table_name"};
        String[] sample = new String[0];

        SelectTable.select("data//davisbase_tables.tbl","davisbase_tables",columns,sample);
    }


}
