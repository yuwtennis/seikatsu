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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.Utils.*;

/***
 * Residential land deals
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

    /***
     *
     * @param csvLine
     * @return
     */
    public static ResidentialLandTxn of(String csvLine) throws IOException {

        List<String> fields = CSVParser.parse(csvLine, CSVFormat.RFC4180)
                .getRecords().get(0).toList();

        ResidentialLandTxn r = new ResidentialLandTxn();
        r.txnType = validateStr(fields.get(0));
        r.priceType = validateStr(fields.get(1));
        r.landPurpose = validateStr(fields.get(2));
        r.districtCode = validateStr(fields.get(3));
        r.prefectureName = validateStr(fields.get(4));
        r.districtName = validateStr(fields.get(5));
        r.cityName = validateStr(fields.get(6));
        r.closestStationName = validateStr(fields.get(7));
        r.durationToClosestStationInMin = asInt(fields.get(8));
        r.totalTxnPrice = asInt(fields.get(9));
        r.pricePerPyeong = asInt(fields.get(10));
        r.areaSizeInSqm = asInt(fields.get(11));
        r.txnPricePerSqm = asInt(fields.get(12));
        r.landShape = validateStr(fields.get(13));
        r.frontageLengthInMeters = asFloat(fields.get(14));
        r.floorAreaTotalInSqm = asInt(fields.get(15));
        r.yearBuilt = asInt(fields.get(16));
        r.buildingStructure = validateStr(fields.get(17));
        r.purpose = validateStr(fields.get(18));
        r.futurePurpose = validateStr(fields.get(19));
        r.frontRoadDirection = validateStr(fields.get(20));
        r.frontRoadType = validateStr(fields.get(21));
        r.frontRoadWidthInMeters = asFloat(fields.get(22));
        r.cityPlan = validateStr(fields.get(23));
        r.bcrReqmt = asFloat(fields.get(24));
        r.farReqmt = asFloat(fields.get(25));
        r.txnPeriod = validateStr(fields.get(26));
        r.txnRemarks = validateStr(fields.get(27));


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