package com.dapm2.sink.utils;

public class AppConstants {
    //Status
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_ARCHIVED = "archived";
    public static final String STATUS_DELETED = "deleted";
    //Anonymization
    public static final String SUPPRESSION = "suppression";
    public static final String PSEUDONYMIZATION = "pseudonymization";
    public static final String Raw_Data = "rawData";
    public static final String Anonymized_Data = "anonymizedData";
    public static final String Mapping_Table_For_ = "mappingTableFor_";
    public static final String Ingested_Raw_Data_For_ = "ingestedRawDataFor_";
    public static final String MAPPING_Table_ID = "mappingTableID";
    public static final String MAPPING_Table_REFERENCE = "mappingTableRef";
    public static final boolean ANONYMIZE_STATUS_TRUE = true;
    public static final boolean ANONYMIZE_STATUS_FALSE = false;
    // Database credentials (hard‐coded or read from env vars)
    public static final String JDBC_URL      = "jdbc:postgresql://localhost:5432/dapm_ingestion";
    public static final String JDBC_USER     = "dapm_user";
    public static final String JDBC_PASSWORD = "123456";
}
