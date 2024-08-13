# pipelines-java

## RealEstates

This dag will parse csv data from [RealEstate information](https://www.reinfolib.mlit.go.jp/) from MLIT.  
I did not use the official API since the csv download information includes information which the API does not support.  
e.g. Closes station name.

### Environment variables

| Variable name                   | Description                                             |
|---------------------------------|---------------------------------------------------------|
| residential_land_table_fqdn     | Bigquery table fqdn for storing _residential land_ data |
| used_apartment_table_fqdn       | Bigquery table fqdn for storing _used apartment_ data   |
| land_value_table_fqdn           | Bigquery table fqdn for storing _land value_ data       |
| OCP_APIM_SUBSCRIPTION_KEY_VALUE | Subscription key. See [doc](https://www.reinfolib.mlit.go.jp/help/apiManual/#titleApi3) (Japanese)                    |

### command

```shell
/app/bin/app \
  --dagType=REALESTATE \
  --tempLocation=BQ_GS_TEMP_LOCATION \
  --backtrackedYears=BACKTRACKED_YEARS
```

| Argument name    | Description                                     |
|------------------|-------------------------------------------------|
| dagType          | Set `REALESTATE`                                |
| tempLocation     | Google Storage directory for Bigquery artifacts |
| backtrackedYears | Years to backtrack                              |