package org.example.dags.realestate;

import com.google.api.services.bigquery.model.TableRow;
import com.google.common.hash.Hashing;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;

import java.nio.charset.StandardCharsets;

import static org.example.Utils.*;

@DefaultCoder(AvroCoder.class)
public class RealEstatesXactRec {
    // 種類
    public String dealType;

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
    public int closedPrice;

    // 坪単価
    public int unitPriceOfFloorspace;

    // 面積（㎡）
    public int areaInSquareMeter;

    // 取引価格（㎡単価）
    public int unitPriceOfSquareMeter;

    // 土地の形状
    public String shapeOfLand;

    // 間口
    public float facadeInMeters;

    // 延床面積（％）
    public int areaTotal;

    // 建築年
    public int yearBuilt;
    
    // 建物の構造
    public String architectureType;

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
    public float buildingToLandRatio;

    // 容積率（％）
    public float floorToLandRatio;

    // 取引時期
    public String agreementPointOfTime;

    //取引の事情等
    public String agreementNote;

    /***
     *
     * @param csvLine
     * @return
     */
    public static RealEstatesXactRec of(String csvLine) {
        String[] fields = csvLine.split(",");
        
        RealEstatesXactRec r = new RealEstatesXactRec();
        r.dealType = validateStr(fields[0]);
        r.priceType = validateStr(fields[1]);
        r.landPurpose = validateStr(fields[2]);
        r.districtCode = validateStr(fields[3]);
        r.prefectureName = validateStr(fields[4]);
        r.districtName = validateStr(fields[5]);
        r.cityName = validateStr(fields[6]);
        r.closestStationName = validateStr(fields[7]);
        r.durationToClosestStationInMin = asInt(fields[8]);
        r.closedPrice = asInt(fields[9]);
        r.unitPriceOfFloorspace = asInt(fields[10]);
        r.areaInSquareMeter = asInt(fields[11]);
        r.unitPriceOfSquareMeter = asInt(fields[12]);
        r.shapeOfLand = validateStr(fields[13]);
        r.facadeInMeters = asFloat(fields[14]);
        r.areaTotal = asInt(fields[15]);
        r.yearBuilt = asInt(fields[16]);
        r.architectureType = validateStr(fields[17]);
        r.purpose = validateStr(fields[18]);
        r.futurePurpose = validateStr(fields[19]);
        r.frontRoadDirection = validateStr(fields[20]);
        r.frontRoadType = validateStr(fields[21]);
        r.frontRoadWidthInMeters = asFloat(fields[22]);
        r.cityPlan = validateStr(fields[23]);
        r.buildingToLandRatio = asFloat(fields[24]);
        r.floorToLandRatio = asFloat(fields[25]);
        r.agreementPointOfTime = validateStr(fields[26]);
        r.agreementNote = validateStr(fields[27]);

        return r;
    }

    public TableRow toTableRow() {
        return new TableRow()
                .set("dealType", dealType)
                .set("priceType", priceType)
                .set("landPurpose", landPurpose)
                .set("districtCode", districtCode)
                .set("prefectureName", prefectureName)
                .set("districtName", districtName)
                .set("cityName", cityName)
                .set("closestStationName", closestStationName)
                .set("durationToClosestStationInMin", durationToClosestStationInMin)
                .set("closedPrice", closedPrice)
                .set("unitPriceOfFloorspace", unitPriceOfFloorspace)
                .set("areaInSquareMeter", areaInSquareMeter)
                .set("unitPriceOfSquareMeter", unitPriceOfSquareMeter)
                .set("shapeOfLand", shapeOfLand)
                .set("facadeInMeters", facadeInMeters)
                .set("areaTotal", areaTotal)
                .set("yearBuilt", yearBuilt)
                .set("architectureType", architectureType)
                .set("purpose", purpose)
                .set("futurePurpose", futurePurpose)
                .set("frontRoadDirection", frontRoadDirection)
                .set("frontRoadType", frontRoadType)
                .set("frontRoadWidthInMeters", frontRoadWidthInMeters)
                .set("cityPlan", cityPlan)
                .set("buildingToLandRatio", buildingToLandRatio)
                .set("floorToLandRatio", floorToLandRatio)
                .set("agreementPointOfTime", agreementPointOfTime)
                .set("agreementNote", agreementNote);
    }

}
