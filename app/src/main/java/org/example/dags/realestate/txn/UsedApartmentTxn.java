package org.example.dags.realestate.txn;

import static org.example.Utils.asFloat;
import static org.example.Utils.asInt;
import static org.example.Utils.validateStr;
import static org.example.dags.realestate.txn.ResidentialLandTxn.parseQuarterDateFormat;

import com.google.api.services.bigquery.model.TableRow;

import java.io.IOException;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;
import org.apache.commons.csv.CSVRecord;
import org.example.Magics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Used apartment transaction
 * i.e. 中古マンション in Japanese
 */
@DefaultCoder(AvroCoder.class)
public class UsedApartmentTxn {
    /**
     * 種類.
     */
    private String txnType;

    /**
     * 価格情報区分.
     */
    private String priceType;

    /**
     * 市区町村コード.
     */
    private String districtCode;

    /**
     * 都道府県名.
     */
    private String prefectureName;

    /**
     * 市区町村名.
     */
    private String districtName;

    /**
     * 地区名.
     */
    private String cityName;

    /**
     * 最寄駅：名称.
     */
    private String closestStationName;

    /**
     * 最寄駅：距離（分）.
     */
    private int durationToClosestStationInMin;

    /**
     * 取引価格（総額）.
     */
    private int totalTxnPrice;

    /**
     * 間取り.
     */
    private String floorPlan;

    /**
     * 面積（平方メートル）.
     */
    private int areaSizeInSqm;

    /**
     * 建築年.
     */
    private int yearBuilt;

    /**
     * 建物の構造.
     */
    private String buildingStructure;

    /**
     * 用途.
     */
    private String purpose;

    /**
     * 今後の利用目的.
     */
    private String futurePurpose;

    /**
     * 都市計画.
     */
    private String cityPlan;

    /**
     * 建ぺい率（％）.
     */
    private float bcrReqmt;

    /**
     * 容積率（％）.
     */
    private float farReqmt;

    /**
     * 取引時期.
     */
    private String txnPeriod;

    /**
     * 改装.
     */
    private String isRefurbished;

    /**
     * 取引の事情等.
     */
    private String txnRemarks;

    /**
     * 四半期の開始日付.
     */
    private String startOfQuarter;

    /**
     *
     */
    static final Logger LOG = LoggerFactory.getLogger(UsedApartmentTxn.class);

    /**
     *
     * @param record
     * @return UsedApartmentTxn
     */
    public static UsedApartmentTxn of(
            final CSVRecord record) throws IOException {
        UsedApartmentTxn r = new UsedApartmentTxn();
        r.txnType = validateStr(record.get(Magics.NUM_0.getValue()));
        r.priceType = validateStr(record.get(Magics.NUM_1.getValue()));
        r.districtCode = validateStr(record.get(Magics.NUM_2.getValue()));
        r.prefectureName = validateStr(record.get(Magics.NUM_3.getValue()));
        r.districtName = validateStr(record.get(Magics.NUM_4.getValue()));
        r.cityName = validateStr(record.get(Magics.NUM_5.getValue()));
        r.closestStationName =
                validateStr(record.get(Magics.NUM_6.getValue()));
        r.durationToClosestStationInMin =
                asInt(record.get(Magics.NUM_7.getValue()));
        r.totalTxnPrice = asInt(record.get(Magics.NUM_8.getValue()));
        r.floorPlan = validateStr(record.get(Magics.NUM_9.getValue()));
        r.areaSizeInSqm = asInt(record.get(Magics.NUM_10.getValue()));
        r.yearBuilt =
                asInt(record.get(Magics.NUM_11.getValue())
                        .replaceAll("年", ""));
        r.buildingStructure =
                validateStr(record.get(Magics.NUM_12.getValue()));
        r.purpose = validateStr(record.get(Magics.NUM_13.getValue()));
        r.futurePurpose = validateStr(record.get(Magics.NUM_14.getValue()));
        r.cityPlan = validateStr(record.get(Magics.NUM_15.getValue()));
        r.bcrReqmt = asFloat(record.get(Magics.NUM_16.getValue()));
        r.farReqmt = asFloat(record.get(Magics.NUM_17.getValue()));
        r.txnPeriod = validateStr(record.get(Magics.NUM_18.getValue()));
        r.isRefurbished = validateStr(record.get(Magics.NUM_19.getValue()));
        r.txnRemarks = validateStr(record.get(Magics.NUM_20.getValue()));

        try {
            r.startOfQuarter = parseQuarterDateFormat(r.txnPeriod);
        } catch (IllegalArgumentException e) {
            LOG.warn(e.getMessage());
            r.startOfQuarter = "";
        }

        return r;
    }

    /**
     *
     * @return TableRow
     */
    public TableRow toTableRow() {
        return new TableRow()
                .set("txnType", txnType)
                .set("priceType", priceType)
                .set("districtCode", districtCode)
                .set("prefectureName", prefectureName)
                .set("districtName", districtName)
                .set("cityName", cityName)
                .set("closestStationName", closestStationName)
                .set("durationToClosestStationInMin",
                        durationToClosestStationInMin)
                .set("totalTxnPrice", totalTxnPrice)
                .set("floorPlan", floorPlan)
                .set("areaSizeInSqm", areaSizeInSqm)
                .set("yearBuilt", yearBuilt)
                .set("buildingStructure", buildingStructure)
                .set("purpose", purpose)
                .set("futurePurpose", futurePurpose)
                .set("cityPlan", cityPlan)
                .set("bcrReqmt", bcrReqmt)
                .set("farReqmt", farReqmt)
                .set("txnPeriod", txnPeriod)
                .set("isRefurbished", isRefurbished)
                .set("txnRemarks", txnRemarks)
                .set("startOfQuarter", startOfQuarter);
    }
}
