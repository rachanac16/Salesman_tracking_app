package com.eat.erp;

public final class OfflineDatabase {
    private OfflineDatabase(){}


    public static class Events{
        public static final String TABLE_NAME="EVENTS";
        public static final String EVENT_NO="EVENT_NUMBER";
        public static final String EVENT_NAME="EVENT_NAME";
        public static final String EVENT_TIME="EVENT_TIME";
        public static final String EVENT_JSON="EVENT_JSON";
    }

    public static class VisitData{
        public static final String TABLE_NAME="VISIT_DATA";
        public static final String DAY="DAY";
        public static final String ACTUAL_COUNT="ACTUAL_COUNT";
        public static final String UNPLANNED_COUNT="UNPLANNED_COUNT";
        public static final String TOTAL_COUNT="TOTAL_COUNT";
        public static final String P_CALL="P_CALL";

    }
}
