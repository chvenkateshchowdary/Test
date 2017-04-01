package com.example.cyclone.cyclone.data;

import android.provider.BaseColumns;


/**
 * Created by ALAAP on 3/2/2017.
 */

public class LogContract {
    private LogContract() {
    }

    public static final class logEntry implements BaseColumns {

        public final static String Coloumn_name_ID = "_ID";
        public final static String Table_Name = "logs";
        public final static String Coloumn_Name_Of_Application = "name";
        public final static String Coloumn_Time_Used = "Time_Used";
        public final static String Coloumn_Image = "app_Image";

    }
}