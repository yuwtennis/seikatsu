package org.example.dags.realestate.txn;

import static org.example.Utils.*;

import com.google.api.services.bigquery.model.TableRow;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Residential land deals　published from Ministry of Land, Infrastructure and Transportation
 * i.e. 宅地取引 in Japanese
 */
@DefaultCoder(AvroCoder.class)
public class ResidentialLandTxn {
    // 種類
    public String txnType;

    // 価格情報区分
    public String priceType;

    // 地域
    public String landPurpose;

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

    // 坪単価
    public int pricePerPyeong;

    // 面積（平方メートル）
    public int areaSizeInSqm;

    // 取引価格（平方メートル単価）
    public int txnPricePerSqm;

    // 土地の形状
    public String landShape;

    // 間口
    public float frontageLengthInMeters;

    // 延床面積（平方メートル）
    public int floorAreaTotalInSqm;

    // 建築年
    public int yearBuilt;
    
    // 建物の構造
    public String buildingStructure;

    // 用途
    public String purpose;

    // 今後の利用目的
    public String futurePurpose;

    // 前面道路：方位
    public String frontRoadDirection;

    // 前面道路：種類
    public String frontRoadType;

    // 前面道路：幅員（ｍ）
    public float frontRoadWidthInMeters;

    // 都市計画
    public String cityPlan;

    // 建ぺい率（％）
    public float bcrReqmt;

    // 容積率（％）
    public float farReqmt;

    // 取引時期
    public String txnPeriod;

    //取引の事情等
    public String txnRemarks;

    // 四半期の開始日付
    public String startOfQuarter;

    static Logger LOG = LoggerFactory.getLogger(ResidentialLandTxn.class);

    /**
     *
     * @param record
     * @return
     */
    public static ResidentialLandTxn of(CSVRecord record) throws IOException {

        ResidentialLandTxn r = new ResidentialLandTxn();
        r.txnType = validateStr(record.get(0));
        r.priceType = validateStr(record.get(1));
        r.landPurpose = validateStr(record.get(2));
        r.districtCode = validateStr(record.get(3));
        r.prefectureName = validateStr(record.get(4));
        r.districtName = validateStr(record.get(5));
        r.cityName = validateStr(record.get(6));
        r.closestStationName = validateStr(record.get(7));
        r.durationToClosestStationInMin = asInt(record.get(8));
        r.totalTxnPrice = asInt(record.get(9));
        r.pricePerPyeong = asInt(record.get(10));
        r.areaSizeInSqm = asInt(record.get(11));
        r.txnPricePerSqm = asInt(record.get(12));
        r.landShape = validateStr(record.get(13));
        r.frontageLengthInMeters = asFloat(record.get(14));
        r.floorAreaTotalInSqm = asInt(record.get(15));
        r.yearBuilt = asInt(record.get(16));
        r.buildingStructure = validateStr(record.get(17));
        r.purpose = validateStr(record.get(18));
        r.futurePurpose = validateStr(record.get(19));
        r.frontRoadDirection = validateStr(record.get(20));
        r.frontRoadType = validateStr(record.get(21));
        r.frontRoadWidthInMeters = asFloat(record.get(22));
        r.cityPlan = validateStr(record.get(23));
        r.bcrReqmt = asFloat(record.get(24));
        r.farReqmt = asFloat(record.get(25));
        r.txnPeriod = validateStr(record.get(26));
        r.txnRemarks = validateStr(record.get(27));

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
                .set("landPurpose", landPurpose)
                .set("districtCode", districtCode)
                .set("prefectureName", prefectureName)
                .set("districtName", districtName)
                .set("cityName", cityName)
                .set("closestStationName", closestStationName)
                .set("durationToClosestStationInMin", durationToClosestStationInMin)
                .set("totalTxnPrice", totalTxnPrice)
                .set("pyeongUnitPrice", pricePerPyeong)
                .set("areaSizeInSqm", areaSizeInSqm)
                .set("sqmTxnPrice", txnPricePerSqm)
                .set("landShape", landShape)
                .set("frontageLengthInMeters", frontageLengthInMeters)
                .set("floorAreaTotalInSqm", floorAreaTotalInSqm)
                .set("yearBuilt", yearBuilt)
                .set("buildingStructure", buildingStructure)
                .set("purpose", purpose)
                .set("futurePurpose", futurePurpose)
                .set("frontRoadDirection", frontRoadDirection)
                .set("frontRoadType", frontRoadType)
                .set("frontRoadWidthInMeters", frontRoadWidthInMeters)
                .set("cityPlan", cityPlan)
                .set("bcrReqmt", bcrReqmt)
                .set("farReqmt", farReqmt)
                .set("txnPeriod", txnPeriod)
                .set("txnRemarks", txnRemarks)
                .set("startOfQuarter", startOfQuarter);
    }

    /**
     *
     * @param s
     * @return
     */
    public static String parseQuarterDateFormat(String s) {
        String pattern = "^(\\d+)年(.*?)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        String formattedDate;

        if (!m.find()) {
            throw new IllegalArgumentException("Unknown quarterly date format: " + s);
        }

        formattedDate = switch (m.group(2)) {
            case "第1四半期" -> m.group(1) + "-01-01";
            case "第2四半期" -> m.group(1) + "-04-01";
            case "第3四半期" -> m.group(1) + "-07-01";
            case "第4四半期" -> m.group(1) + "-10-01";
            default -> throw new IllegalArgumentException("Unexpected value: " + m.group(2));
        };
        return formattedDate;
    }
}