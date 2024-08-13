package org.example.dags.realestate.txn;

import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.example.Utils.*;
import static org.example.dags.realestate.txn.ResidentialLandTxn.parseQuarterDateFormat;

/***
 * Used apartment transaction
 * i.e. 中古マンション in Japanese
 */
@DefaultCoder(AvroCoder.class)
public class UsedApartmentTxn {
    // 種類
    public String txnType;

    // 価格情報区分
    public String priceType;

    // 市区町村コード
    public String districtCode;

    // 都道府県名
    public String prefectureName;

    // 市区町村名
    public String districtName;

    // 地区名
    public String cityName;

    // 最寄駅：名称
    public String closestStationName;

    // 最寄駅：距離（分）
    public int durationToClosestStationInMin;

    // 取引価格（総額）
    public int totalTxnPrice;

    // 間取り
    public String floorPlan;

    // 面積（平方メートル）
    public int areaSizeInSqm;

    // 建築年
    public int yearBuilt;
    
    // 建物の構造
    public String buildingStructure;

    // 用途
    public String purpose;

    // 今後の利用目的
    public String futurePurpose;

    // 都市計画
    public String cityPlan;

    // 建ぺい率（％）
    public float bcrReqmt;

    // 容積率（％）
    public float farReqmt;

    // 取引時期
    public String txnPeriod;

    // 改装
    public String isRefurbished;

    //取引の事情等
    public String txnRemarks;

    // 四半期の開始日付
    public String startOfQuarter;

    static Logger LOG = LoggerFactory.getLogger(UsedApartmentTxn.class);

    /**
     *
     * @param record
     * @return
     */
    public static UsedApartmentTxn of(CSVRecord record) throws IOException {
        
        UsedApartmentTxn r = new UsedApartmentTxn();
        r.txnType = validateStr(record.get(0));
        r.priceType = validateStr(record.get(1));
        r.districtCode = validateStr(record.get(2));
        r.prefectureName = validateStr(record.get(3));
        r.districtName = validateStr(record.get(4));
        r.cityName = validateStr(record.get(5));
        r.closestStationName = validateStr(record.get(6));
        r.durationToClosestStationInMin = asInt(record.get(7));
        r.totalTxnPrice = asInt(record.get(8));
        r.floorPlan = validateStr(record.get(9));
        r.areaSizeInSqm = asInt(record.get(10));
        r.yearBuilt = asInt(record.get(11));
        r.buildingStructure = validateStr(record.get(12));
        r.purpose = validateStr(record.get(13));
        r.futurePurpose = validateStr(record.get(14));
        r.cityPlan = validateStr(record.get(15));
        r.bcrReqmt = asFloat(record.get(16));
        r.farReqmt = asFloat(record.get(17));
        r.txnPeriod = validateStr(record.get(18));
        r.isRefurbished = validateStr(record.get(19));
        r.txnRemarks = validateStr(record.get(20));

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
     * @return
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
                .set("durationToClosestStationInMin", durationToClosestStationInMin)
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