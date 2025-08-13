package org.example.dags.realestate.txn;

import com.google.api.services.bigquery.model.TableRow;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;
import org.apache.commons.csv.CSVRecord;
import org.example.Magics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.Utils.asFloat;
import static org.example.Utils.asInt;
import static org.example.Utils.validateStr;

/***
 * Residential land deals　published from Ministry of Land,
 * Infrastructure and Transportation
 * i.e. 宅地取引 in Japanese
 */
@DefaultCoder(AvroCoder.class)
public class ResidentialLandTxn {
    /**
     * 種類.
     */
    private String txnType;

    /**
     * 価格情報区分.
     */
    private String priceType;

    /**
     * 地域.
     */
    private String landPurpose;

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
     * // 坪単価.
     */
    private int pricePerPyeong;

    /**
     * // 面積（平方メートル）.
     */
    private int areaSizeInSqm;

    /**
     * // 取引価格（平方メートル単価）.
     */
    private int txnPricePerSqm;

    /**
     * // 土地の形状.
     */
    private String landShape;

    /**
     * 間口.
     */
    private float frontageLengthInMeters;

    /**
     * 延床面積（平方メートル）.
     */
    private int floorAreaTotalInSqm;

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
     * 前面道路：方位.
     */
    private String frontRoadDirection;

    /**
     * 前面道路：種類.
     */
    private String frontRoadType;

    /**
     * 前面道路：幅員（ｍ）.
     */
    private float frontRoadWidthInMeters;

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
    static final Logger LOG = LoggerFactory.getLogger(ResidentialLandTxn.class);

    /**
     *
     * @param record
     * @return ResidentialLandTxn
     * @throws IOException
     */
    public static ResidentialLandTxn of(
            final CSVRecord record) throws IOException {

        ResidentialLandTxn r = new ResidentialLandTxn();
        r.txnType = validateStr(record.get(asInt(Magics.NUM_0.getValue())));
        r.priceType =
                validateStr(record.get(asInt(Magics.NUM_1.getValue())));
        r.landPurpose =
                validateStr(record.get(asInt(Magics.NUM_2.getValue())));
        r.districtCode =
                validateStr(record.get(asInt(Magics.NUM_3.getValue())));
        r.prefectureName =
                validateStr(record.get(asInt(Magics.NUM_4.getValue())));
        r.districtName =
                validateStr(record.get(asInt(Magics.NUM_5.getValue())));
        r.cityName = validateStr(record.get(Magics.NUM_6.getValue()));
        r.closestStationName =
                validateStr(record.get(Magics.NUM_7.getValue()));
        r.durationToClosestStationInMin =
                asInt(record.get(Magics.NUM_8.getValue()));
        r.totalTxnPrice = asInt(record.get(Magics.NUM_9.getValue()));
        r.pricePerPyeong = asInt(record.get(Magics.NUM_10.getValue()));
        r.areaSizeInSqm = asInt(record.get(Magics.NUM_11.getValue()));
        r.txnPricePerSqm = asInt(record.get(Magics.NUM_12.getValue()));
        r.landShape = validateStr(record.get(Magics.NUM_13.getValue()));
        r.frontageLengthInMeters =
                asFloat(record.get(Magics.NUM_14.getValue()));
        r.floorAreaTotalInSqm = asInt(record.get(Magics.NUM_15.getValue()));
        r.yearBuilt = asInt(record.get(Magics.NUM_16.getValue()));
        r.buildingStructure =
                validateStr(record.get(Magics.NUM_17.getValue()));
        r.purpose = validateStr(record.get(Magics.NUM_18.getValue()));
        r.futurePurpose = validateStr(record.get(Magics.NUM_19.getValue()));
        r.frontRoadDirection =
                validateStr(record.get(Magics.NUM_20.getValue()));
        r.frontRoadType = validateStr(record.get(Magics.NUM_21.getValue()));
        r.frontRoadWidthInMeters =
                asFloat(record.get(Magics.NUM_22.getValue()));
        r.cityPlan = validateStr(record.get(Magics.NUM_23.getValue()));
        r.bcrReqmt = asFloat(record.get(Magics.NUM_24.getValue()));
        r.farReqmt = asFloat(record.get(Magics.NUM_25.getValue()));
        r.txnPeriod = validateStr(record.get(Magics.NUM_26.getValue()));
        r.txnRemarks = validateStr(record.get(Magics.NUM_27.getValue()));

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
                .set("landPurpose", landPurpose)
                .set("districtCode", districtCode)
                .set("prefectureName", prefectureName)
                .set("districtName", districtName)
                .set("cityName", cityName)
                .set("closestStationName", closestStationName)
                .set("durationToClosestStationInMin",
                        durationToClosestStationInMin)
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
     * @return String
     */
    public static String parseQuarterDateFormat(final String s) {
        String pattern = "^(\\d+)年(.*?)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        String formattedDate;

        if (!m.find()) {
            throw new IllegalArgumentException(
                    "Unknown quarterly date format: " + s);
        }

        formattedDate = switch (m.group(2)) {
            case "第1四半期" -> m.group(1) + "-01-01";
            case "第2四半期" -> m.group(1) + "-04-01";
            case "第3四半期" -> m.group(1) + "-07-01";
            case "第4四半期" -> m.group(1) + "-10-01";
            default -> throw new IllegalArgumentException(
                    "Unexpected value: " + m.group(2));
        };
        return formattedDate;
    }
}
