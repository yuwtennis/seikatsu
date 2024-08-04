package org.example.dags.realestate;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;
import org.example.Magics;

@DefaultCoder(AvroCoder.class)
public class RealEstatesXactRec {
    // 種類
    private final String dealType;

    // 価格情報区分
    private final String priceType;

    // 地域
    private final String landPurpose;

    // 市区町村コード
    private final String districtCode;

    // 都道府県名
    private final String prefectureName;

    // 市区町村名
    private final String districtName;

    // 地区名
    private final String cityName;

    // 最寄駅：名称
    private final String closestStationName;

    // 最寄駅：距離（分）
    private final int durationToClosestStationInMin;

    // 取引価格（総額）
    private final int closedPrice;

    // 坪単価
    private final int unitPriceOfFloorspace;

    // 面積（㎡）
    private final int areaInSquareMeter;

    // 取引価格（㎡単価）
    private final int unitPriceOfSquareMeter;

    // 土地の形状
    private final String shapeOfLand;

    // 間口
    private final float facadeInMeters;

    // 延床面積（％）
    private final int areaTotal;

    // 建築年
    private final int yearBuilt;

    // 築年数
    private final int yearsSinceBuilt;

    // 建物の構造
    private final String architectureType;

    // 用途
    private final String purpose;

    // 今後の利用目的
    private final String futurePurpose;

    // 前面道路：方位
    private final String frontRoadDirection;

    // 前面道路：種類
    private final String frontRoadType;

    // 前面道路：幅員（ｍ）
    private final float frontRoadWidthInMeters;

    // 都市計画
    private final String cityPlan;

    // 建ぺい率（％）
    private final float buildingToLandRatio;

    // 容積率（％）
    private final float FloorToLandRatio;

    // 取引時期
    private final String AgreementPointOfTime;

    //取引の事情等
    private final String agreementNote;

    public static RealEstatesXactRec of(String csvStr) {
        String[] arr = csvStr.split(",");

        return new Builder(
                arr[0],
                arr[1])
                .landPurpose(arr[2])
                .districtCode(arr[3])
                .prefectureName(arr[4])
                .districtName(arr[5])
                .cityName(arr[6])
                .closestStationName(arr[7])
                .durationToClosestStationInMin(arr[8])
                .closedPrice(arr[9])
                .unitPriceOfFloorspace(arr[10])
                .areaInSquareMeter(arr[11])
                .unitPriceOfSquareMeter(arr[12])
                .shapeOfLand(arr[13])
                .facadeInMeters(arr[14])
                .areaTotal(arr[15])
                .yearBuilt(arr[16])
                .yearsSinceBuilt(arr[17])
                .architectureType(arr[18])
                .purpose(arr[19])
                .futurePurpose(arr[20])
                .frontRoadDirection(arr[21])
                .frontRoadType(arr[22])
                .frontRoadWidthInMeters(arr[23])
                .cityPlan(arr[24])
                .buildingToLandRatio(arr[25])
                .FloorToLandRatio(arr[26])
                .AgreementPointOfTime(arr[27])
                .agreementNote(arr[28])
                .build();
    }

    public static class Builder {
        private String dealType;
        private String priceType;
        private String landPurpose;
        private String districtCode;
        private String prefectureName;
        private String districtName;
        private String cityName;
        private String closestStationName;
        private int durationToClosestStationInMin;
        private int closedPrice;
        private int unitPriceOfFloorspace;
        private int areaInSquareMeter;
        private int unitPriceOfSquareMeter;
        private String shapeOfLand;
        private float facadeInMeters;
        private int areaTotal;
        private int yearBuilt;
        private int yearsSinceBuilt;
        private String architectureType;
        private String purpose;
        private String futurePurpose;
        private String frontRoadDirection;
        private String frontRoadType;
        private float frontRoadWidthInMeters;
        private String cityPlan;
        private float buildingToLandRatio;
        private float FloorToLandRatio;
        private String AgreementPointOfTime;
        private String agreementNote;

        // Attributes requiring validation
        public Builder landPurpose(String val) {
            landPurpose = val; return this;
        }

        public Builder districtCode(String val) {
            districtCode = val.isEmpty() ? Magics.IS_EMPTY.value : val ;
            return this;
        }

        public Builder prefectureName(String val) {
            prefectureName = val; return this;
        }

        public Builder districtName(String val) {
            districtName = val; return this;
        }

        public Builder cityName(String val) {
            cityName = val; return this;
        }

        public Builder closestStationName(String val) {
            closestStationName = val.isEmpty() ? Magics.IS_EMPTY.value : val;
            return this;
        }

        public Builder durationToClosestStationInMin(String val) {
            durationToClosestStationInMin = asInt(val);

            return this;
        }

        public Builder closedPrice(String val) {
            closedPrice = asInt(val);
            return this;
        }

        public Builder unitPriceOfFloorspace(String val) {
            unitPriceOfFloorspace = asInt(val);
            return this;
        }

        public Builder areaInSquareMeter(String val) {
            areaInSquareMeter = asInt(val);
            return this;
        }

        public Builder unitPriceOfSquareMeter(String val) {
            unitPriceOfSquareMeter = asInt(val);
            return this;
        }

        public Builder shapeOfLand(String val) {
            shapeOfLand = val.isEmpty() ? Magics.IS_EMPTY.value : val;
            return this;
        }

        public Builder facadeInMeters(String val) {
            facadeInMeters = asFloat(val);
            return this;
        }

        public Builder areaTotal(String val) {
            areaTotal = asInt(val);
            return this;
        }

        public Builder yearBuilt(String val) {
            yearBuilt = asInt(val);
            return this;
        }

        public Builder yearsSinceBuilt(String val) {
            yearsSinceBuilt = asInt(val);
            return this;
        }

        public Builder architectureType(String val) {
            architectureType = val.isEmpty() ? Magics.IS_EMPTY.value : val;
            return this;
        }

        public Builder purpose(String val) {
            purpose = val.isEmpty() ? Magics.IS_EMPTY.value : val;
            return this;
        }

        public Builder futurePurpose(String val) {
            futurePurpose = val.isEmpty() ? Magics.IS_EMPTY.value : val;
            return this;
        }

        public Builder frontRoadDirection(String val) {
            frontRoadDirection = val.isEmpty() ? Magics.IS_EMPTY.value : val;
            return this;
        }

        public Builder frontRoadType(String val) {
            frontRoadType = val.isEmpty() ? Magics.IS_EMPTY.value : val;
            return this;
        }

        public Builder frontRoadWidthInMeters(String val) {
            frontRoadWidthInMeters = asFloat(val);
            return this;
        }

        public Builder cityPlan(String val) {
            cityPlan = val.isEmpty() ? Magics.IS_EMPTY.value : val; return this;
        }

        public Builder buildingToLandRatio(String val) {
            buildingToLandRatio = asFloat(val);

            if (asFloat(val) > 0) {
                buildingToLandRatio /= 100;
            }
            return this;
        }

        public Builder FloorToLandRatio(String val) {
            FloorToLandRatio = asFloat(val);

            if (asFloat(val) > 0) {
                FloorToLandRatio /= 100;
            }

            return this;
        }

        public Builder AgreementPointOfTime(String val) {
            AgreementPointOfTime = val.isEmpty() ? Magics.IS_EMPTY.value : val;
            return this;
        }

        public Builder agreementNote(String val) {
            agreementNote = val.isEmpty() ? Magics.IS_EMPTY.value : val;
            return this;
        }

        // Mandatory args that explains characteristic of the transaction
        public Builder(String dealType, String priceType) {
            this.dealType = dealType;
            this.priceType = priceType;
        }

        public RealEstatesXactRec build() {
            return new RealEstatesXactRec(this);
        }

        private int asInt(String val) {
            int result = 0 ;

            try {
                result = Integer.parseInt(val);
            } catch (NumberFormatException e) {
                result = Integer.parseInt(Magics.IS_UNEXPECTED_NUMERIC_VAL.value);
            }

            return result;
        }

        private float asFloat(String val) {
            float result = 0 ;

            try {
                result = Float.parseFloat(val);
            } catch (NumberFormatException e) {
                result = Float.parseFloat(Magics.IS_UNEXPECTED_NUMERIC_VAL.value);
            }

            return result;
        }
    }

    private RealEstatesXactRec(Builder builder) {
        this.dealType = builder.dealType;
        this.priceType = builder.priceType;
        this.landPurpose = builder.landPurpose;
        this.districtCode = builder.districtCode;
        this.prefectureName = builder.prefectureName;
        this.districtName = builder.districtName;
        this.cityName = builder.cityName;
        this.closestStationName = builder.closestStationName;
        this.durationToClosestStationInMin = builder.durationToClosestStationInMin;
        this.closedPrice = builder.closedPrice;
        this.unitPriceOfFloorspace = builder.unitPriceOfFloorspace;
        this.areaInSquareMeter = builder.areaInSquareMeter;
        this.unitPriceOfSquareMeter = builder.unitPriceOfSquareMeter;
        this.shapeOfLand = builder.shapeOfLand;
        this.facadeInMeters = builder.facadeInMeters;
        this.areaTotal = builder.areaTotal;
        this.yearBuilt = builder.yearBuilt;
        this.yearsSinceBuilt = builder.yearsSinceBuilt;
        this.architectureType = builder.architectureType;
        this.purpose = builder.purpose;
        this.futurePurpose = builder.futurePurpose;
        this.frontRoadDirection = builder.frontRoadDirection;
        this.frontRoadType = builder.frontRoadType;
        this.frontRoadWidthInMeters = builder.frontRoadWidthInMeters;
        this.cityPlan = builder.cityPlan;
        this.buildingToLandRatio = builder.buildingToLandRatio;
        this.FloorToLandRatio = builder.FloorToLandRatio;
        this.AgreementPointOfTime = builder.AgreementPointOfTime;
        this.agreementNote = builder.agreementNote;
    }
}
