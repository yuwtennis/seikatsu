package org.example.dags.realestate;

public class RealEstateEnv {
    public static final String FULLY_QUALIFIED_TABLE_NAME = System.getenv("real_estate_table_fqdn");
    public static final int BACKTRACKED_YEARS = 5;
}
