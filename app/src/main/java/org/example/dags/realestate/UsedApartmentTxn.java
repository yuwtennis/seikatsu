package org.example.dags.realestate;

import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.example.Utils.*;
import static org.example.dags.realestate.ResidentialLandTxn.parseQuarterDateFormat;

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

    /***
     *
     * @param csvLine
     * @return
     */
    public static UsedApartmentTxn of(String csvLine) throws IOException {

        List<String> fields = CSVParser.parse(csvLine, CSVFormat.RFC4180)
                .getRecords().get(0).toList();

        UsedApartmentTxn r = new UsedApartmentTxn();
        r.txnType = validateStr(fields.get(0));
        r.priceType = validateStr(fields.get(1));
        r.districtCode = validateStr(fields.get(2));
        r.prefectureName = validateStr(fields.get(3));
        r.districtName = validateStr(fields.get(4));
        r.cityName = validateStr(fields.get(5));
        r.closestStationName = validateStr(fields.get(6));
        r.durationToClosestStationInMin = asInt(fields.get(7));
        r.totalTxnPrice = asInt(fields.get(8));
        r.floorPlan = validateStr(fields.get(9));
        r.areaSizeInSqm = asInt(fields.get(10));
        r.yearBuilt = asInt(fields.get(11));
        r.buildingStructure = validateStr(fields.get(12));
        r.purpose = validateStr(fields.get(13));
        r.futurePurpose = validateStr(fields.get(14));
        r.cityPlan = validateStr(fields.get(15));
        r.bcrReqmt = asFloat(fields.get(16));
        r.farReqmt = asFloat(fields.get(17));
        r.txnPeriod = validateStr(fields.get(18));
        r.isRefurbished = validateStr(fields.get(19));
        r.txnRemarks = validateStr(fields.get(20));

        try {
            r.startOfQuarter = parseQuarterDateFormat(r.txnPeriod);
        } catch (IllegalArgumentException e) {
            LOG.warn(e.getMessage());
            r.startOfQuarter = "";
        }

        return r;
    }

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