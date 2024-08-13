package org.example.dags.realestate.landvalue;

import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.example.Utils.*;

/***
 * Residential land deals　published from Ministry of Land, Infrastructure and Transportation
 * i.e. 宅地取引 in Japanese
 */
@DefaultCoder(AvroCoder.class)
public class LandValue {
    // 鑑定種類
    String landValueType;

    // 価格時点
    int yearEvaluated;

    // 県コード
    String stdLocPrefectureCode;

    // 市区町村コード1
    String stdLocDistrictCode;

    // 地域名
    String stdLocDistrictName;

    // 用途区分
    // TODO Should be enum 用途区分
    String stdLocPurposeCode;

    // 連番
    int stdLocSeqNum;

    // 評価員番号
    String evaluatorId;

    // 分科会番号
    String evaluationGroupId;

    // 資格
    // TODO Should be enum 資格コード
    String position;

    // 役職
    // TODO Should be enum 役職コード
    String responsibility;

    // TEL
    String phoneNumber;

    // 住所
    String address;

    // 提出日
    String dateSubmitted;

    // 鑑定評価額
    int priceEvaluated;

    // 1㎡当たりの価格
    int pricePerSqm;

    // 実施調査日
    String dateInvestigated;

    // 鑑定評価日
    String dateEvaluated;

    static Logger LOG = LoggerFactory.getLogger(LandValue.class);

    /**
     *
     * @param record
     * @return
     */
    public static LandValue of(String fileName, CSVRecord record) throws IOException {
        LandValue r = new LandValue();

        if(fileName.contains("TAKUCHI")) {
            r.landValueType = LandValueType.RESIDENTIAL_LAND.value;
        }

        r.yearEvaluated = asInt(record.get(0));
        r.stdLocPrefectureCode = validateStr(record.get(1));
        r.stdLocDistrictCode = validateStr(record.get(2));
        r.stdLocDistrictName = validateStr(record.get(3));
        r.stdLocPurposeCode = validateStr(record.get(4));
        r.stdLocSeqNum = asInt(record.get(5));
        r.evaluatorId = validateStr(record.get(11));
        r.evaluationGroupId = validateStr(record.get(12));
        r.position = validateStr(record.get(13));
        r.responsibility = validateStr(record.get(14));
        r.phoneNumber = validateStr(record.get(15));
        r.address = validateStr(record.get(16));
        r.dateSubmitted = from4digitStr(record.get(17));
        r.priceEvaluated = asInt(record.get(18));
        r.pricePerSqm = asInt(record.get(19));
        r.dateInvestigated = from4digitStr(record.get(20));
        r.dateEvaluated = from4digitStr(record.get(21));

        return r;
    }

    /**
     *
     * @return
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
}