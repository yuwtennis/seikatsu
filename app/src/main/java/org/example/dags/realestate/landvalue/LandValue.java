package org.example.dags.realestate.landvalue;

import com.google.api.services.bigquery.model.TableRow;

import java.io.IOException;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;
import org.apache.commons.csv.CSVRecord;
import org.example.Magics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.Utils.asInt;
import static org.example.Utils.from4digitStr;
import static org.example.Utils.validateStr;

/***
 * Residential land deals　published from Ministry of Land,
 * Infrastructure and Transportation
 * i.e. 宅地取引 in Japanese
 */
@DefaultCoder(AvroCoder.class)
public class LandValue {
    /**
     * 鑑定種類.
     */
    private String landValueType;

    /**
     * 価格時点.
     */
    private int yearEvaluated;

    /**
     * 県コード.
     */
    private String stdLocPrefectureCode;

    /**
     * 市区町村コード1.
     */
    private String stdLocDistrictCode;

    /**
     * 地域名.
     */
    private String stdLocDistrictName;

    // TODO Should be enum 用途区分
    /**
     * 用途区分.
     */
    private String stdLocPurposeCode;

    /**
     * 連番.
     */
    private int stdLocSeqNum;

    /**
     * 評価員番号.
     */
    private String evaluatorId;

    /**
     * 分科会番号.
     */
    private String evaluationGroupId;

    // TODO Should be enum 資格コード
    /**
     * 資格.
     */
    private String position;

    // TODO Should be enum 役職コード
    /**
     * 役職.
     */
    private String responsibility;

    /**
     * TEL.
     */
    private String phoneNumber;

    /**
     * 住所.
     */
    private String address;

    /**
     * 提出日.
     */
    private String dateSubmitted;

    /**
     * 鑑定評価額.
     */
    private int priceEvaluated;

    /**
     * 1㎡当たりの価格.
     */
    private int pricePerSqm;

    /**
     * 実施調査日.
     */
    private String dateInvestigated;

    /**
     * 鑑定評価日.
     */
    private String dateEvaluated;

    /**
     *
     */
    static final Logger LOG = LoggerFactory.getLogger(LandValue.class);

    /**
     *
     * @param fileName
     * @param record
     * @return LandValue
     * @throws IOException
     */
    public static LandValue of(
            final String fileName,
            final CSVRecord record) throws IOException {
        LandValue r = new LandValue();

        if (fileName.contains("TAKUCHI")) {
            r.landValueType = LandValueType.RESIDENTIAL_LAND.getValue();
        }

        r.yearEvaluated = asInt(record.get(Magics.NUM_0.getValue()));
        r.stdLocPrefectureCode =
                validateStr(record.get(Magics.NUM_1.getValue()));
        r.stdLocDistrictCode =
                validateStr(record.get(Magics.NUM_2.getValue()));
        r.stdLocDistrictName =
                validateStr(record.get(Magics.NUM_3.getValue()));
        r.stdLocPurposeCode =
                validateStr(record.get(Magics.NUM_4.getValue()));
        r.stdLocSeqNum = asInt(record.get(Magics.NUM_5.getValue()));
        r.evaluatorId = validateStr(record.get(Magics.NUM_11.getValue()));
        r.evaluationGroupId =
                validateStr(record.get(Magics.NUM_12.getValue()));
        r.position = validateStr(record.get(Magics.NUM_13.getValue()));
        r.responsibility =
                validateStr(record.get(Magics.NUM_14.getValue()));
        r.phoneNumber = validateStr(record.get(Magics.NUM_15.getValue()));
        r.address = validateStr(record.get(Magics.NUM_16.getValue()));
        r.dateSubmitted =
                from4digitStr(record.get(Magics.NUM_17.getValue()));
        r.priceEvaluated = asInt(record.get(Magics.NUM_18.getValue()));
        r.pricePerSqm = asInt(record.get(Magics.NUM_19.getValue()));
        r.dateInvestigated =
                from4digitStr(record.get(Magics.NUM_20.getValue()));
        r.dateEvaluated =
                from4digitStr(record.get(Magics.NUM_21.getValue()));

        return r;
    }

    /**
     *
     * @return TableRow
     */
    public TableRow toTableRow() {
        return new TableRow()
                .set("landValueType", landValueType)
                .set("yearEvaluated", yearEvaluated)
                .set("stdLocPrefectureCode", stdLocPrefectureCode)
                .set("stdLocDistrictCode", stdLocDistrictCode)
                .set("stdLocDistrictName", stdLocDistrictName)
                .set("stdLocPurposeCode", stdLocPurposeCode)
                .set("stdLocSeqNum", stdLocSeqNum)
                .set("evaluatorId", evaluatorId)
                .set("evaluationGroupId", evaluationGroupId)
                .set("position", position)
                .set("responsibility", responsibility)
                .set("phoneNumber", phoneNumber)
                .set("address", address)
                .set("dateSubmitted", dateSubmitted)
                .set("priceEvaluated", priceEvaluated)
                .set("pricePerSqm", pricePerSqm)
                .set("dateInvestigated", dateInvestigated)
                .set("dateEvaluated", dateEvaluated);
    }

    /**
     *
     * @return int
     */
    public int getPricePerSqm() {
        return pricePerSqm;
    }
}
