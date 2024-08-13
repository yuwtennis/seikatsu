package org.example.dags.realestate;

public class BqMetaData {
    public static final String FQTN_RESIDENTIAL_LAND = System.getenv("residential_land_table_fqdn");
    public static final String FQTN_USED_APARTMENT = System.getenv("used_apartment_table_fqdn");
    public static final String FQTN_LAND_VALUE = System.getenv("land_value_table_fqdn");
}
