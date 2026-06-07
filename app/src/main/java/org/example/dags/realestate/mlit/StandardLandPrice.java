package org.example.dags.realestate.mlit;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.beam.sdk.schemas.JavaFieldSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

// TODO: Fix [direct-runner-worker] WARN org.apache.beam.sdk.util .MutationDetectors - Coder of type
// class org.apache.beam.sdk.schemas.SchemaCoder has a #structuralValue method which does not return
// true when the encoding of the elements is equal. Element
// org.example.dags.realestate.mlit.StandardLandPrice@4a6c27d4

/**
 * StandardLandPrice is a class representing the standard land price data.
 */
@DefaultSchema(JavaFieldSchema.class)
public class StandardLandPrice extends Mlit {

  @JsonProperty("価格時点")
  public String priceDate; // e.g. "2025"

  @JsonProperty("標準地番号 市区町村コード 県コード")
  public String prefectureCode; // e.g. "47"

  @JsonProperty("標準地番号 市区町村コード 市区町村コード")
  public String municipalityCode; // e.g. "201"

  @JsonProperty("標準地番号 地域名")
  public String regionName; // e.g. "那覇"

  @JsonProperty("標準地番号 用途区分")
  public String landUseCode; // e.g. "工業地"

  @JsonProperty("標準地番号 連番")
  public Integer sequenceNumber; // e.g. 1

  @JsonProperty("1㎡当たりの価格")
  public Long pricePerSqm; // e.g. 226000

  @JsonProperty("路線価 年")
  public Integer routeValueYear; // e.g. 2024

  @JsonProperty("路線価 相続税路線価")
  public Long inheritanceTaxRouteValue; // e.g. 170000

  @JsonProperty("路線価 倍率")
  public Double routeValueMultiplier; // e.g. 0

  @JsonProperty("路線価 倍率種別")
  public String routeValueMultiplierType;

  @JsonProperty("標準地 所在地 所在地番")
  public String lotNumber; // e.g. "港町３丁目７番１０"

  @JsonProperty("標準地 所在地 住居表示")
  public String residentialAddress; // e.g. "港町３－７－５４"

  @JsonProperty("標準地 所在地 仮換地番号")
  public String provisionalLotNumber;

  @JsonProperty("標準地 地積 地積")
  public Integer landArea; // e.g. 1504

  @JsonProperty("標準地 地積 内私道分")
  public Integer publicRoadArea; // e.g. 0

  @JsonProperty("標準地 形状 形状")
  public String landShape; // e.g. "長方形"

  @JsonProperty("標準地 形状 形状比 間口")
  public Double frontageRatio; // e.g. 1.2

  @JsonProperty("標準地 形状 形状比 奥行")
  public Double depthRatio; // e.g. 1

  @JsonProperty("標準地 形状 方位")
  public String orientation;

  @JsonProperty("標準地 形状 平坦")
  public String flatness;

  @JsonProperty("標準地 形状 傾斜度")
  public Double slopeAngle; // e.g. 0

  @JsonProperty("標準地 土地利用の現況 現況")
  public String currentLandUse; // e.g. "倉庫兼事務所"

  @JsonProperty("標準地 土地利用の現況 構造") //
  public String structureCode;

  @JsonProperty("標準地 土地利用の現況 地上階数")
  public Integer aboveGroundFloors; // e.g. 0

  @JsonProperty("標準地 土地利用の現況 地下階数")
  public Integer undergroundFloors; // e.g. 0

  @JsonProperty("標準地 周辺の利用状況")
  public String surroundingLandUse; // e.g. "配送センター、倉庫等が建ち並ぶ臨海工業地域"

  @JsonProperty("標準地 接面道路の状況 前面道路 方位")
  public String frontRoadDirection; // e.g. "北"

  @JsonProperty("標準地 接面道路の状況 前面道路 駅前区分")
  public String frontRoadStationCategory; // e.g. "その他（記載無含）"

  @JsonProperty("標準地 接面道路の状況 前面道路 高低位置")
  public String frontRoadElevationPosition;

  @JsonProperty("標準地 接面道路の状況 前面道路 道路幅員")
  public Float frontRoadWidth; // e.g. 18

  @JsonProperty("標準地 接面道路の状況 前面道路 舗装状況")
  public String frontRoadPavingStatus; // e.g. "舗装"

  @JsonProperty("標準地 接面道路の状況 前面道路 道路種別")
  public String frontRoadType; // e.g. "市道"

  @JsonProperty("標準地 接面道路の状況 側道方位")
  public String sideRoadDirection; // e.g. "接面道路無（記載無含）"

  @JsonProperty("標準地 接面道路の状況 側道等接面状況")
  public String sideRoadContactStatus; // e.g. "記載無し"

  @JsonProperty("標準地 供給処理施設 水道")
  public Integer waterSupply; // e.g. 1

  @JsonProperty("標準地 供給処理施設 ガス")
  public Integer gasSupply; // e.g. 0

  @JsonProperty("標準地 供給処理施設 下水道")
  public Integer sewerage; // e.g. 1

  @JsonProperty("標準地 交通施設の状況 交通施設")
  public String nearestTransportFacility; // e.g. "市場南口停"

  @JsonProperty("標準地 交通施設の状況 距離")
  public Integer distanceToTransport; // e.g. 150

  @JsonProperty("標準地 交通施設の状況 近接区分")
  public String transportProximityCategory; // e.g. "その他（記載無含）"

  @JsonProperty("標準地 法令上の規制等 区域区分")
  public String zoneClassification; // e.g. "市街化区域"

  @JsonProperty("標準地 法令上の規制等 用途地域")
  public String useDistrict; // e.g. "準工業地域"

  @JsonProperty("標準地 法令上の規制等 指定建ぺい率") // was: 標準地 法令上の規制等 指定建蔽率
  public Integer designatedBuildingCoverageRatio; // e.g. 60

  @JsonProperty("標準地 法令上の規制等 指定容積率")
  public Integer designatedFloorAreaRatio; // e.g. 200

  @JsonProperty("標準地 法令上の規制等 防火地域")
  public String firePreventionDistrict; // e.g. "無指定（記載無含）"

  @JsonProperty("標準地 法令上の規制等 森林法")
  public String forestryLaw;

  @JsonProperty("標準地 法令上の規制等 自然公園法")
  public String naturalParkLaw;

  @JsonProperty("標準地 法令上の規制等 その他 その他地域地区等1")
  public String otherZoneDistrict1; // e.g. "臨港地区 （臨港地区）"

  @JsonProperty("標準地 法令上の規制等 その他 その他地域地区等2")
  public String otherZoneDistrict2;

  @JsonProperty("標準地 法令上の規制等 その他 その他地域地区等3")
  public String otherZoneDistrict3;

  @JsonProperty("標準地 法令上の規制等 その他 高度地区1 種")
  public Integer heightDistrict1Type; // e.g. 0

  @JsonProperty("標準地 法令上の規制等 その他 高度地区1 高度区分")
  public String heightDistrict1Category;

  @JsonProperty("標準地 法令上の規制等 その他 高度地区1 高度")
  public Integer heightDistrict1Height; // e.g. 0

  @JsonProperty("標準地 法令上の規制等 その他 高度地区2 種")
  public Integer heightDistrict2Type; // e.g. 0

  @JsonProperty("標準地 法令上の規制等 その他 高度地区2 高度区分")
  public String heightDistrict2Category;

  @JsonProperty("標準地 法令上の規制等 その他 高度地区2 高度")
  public Integer heightDistrict2Height; // e.g. 0

  @JsonProperty("標準地 法令上の規制等 その他 基準建ぺい率") // was: 標準地 法令上の規制等 その他 基準建蔽率
  public Integer standardBuildingCoverageRatio;

  @JsonProperty("標準地 法令上の規制等 その他 基準容積率")
  public Integer standardFloorAreaRatio; // e.g. 0

  @JsonProperty("標準地 法令上の規制等 自然環境等コード1")
  public String naturalEnvironmentCode1;

  @JsonProperty("標準地 法令上の規制等 自然環境等コード2")
  public String naturalEnvironmentCode2;

  @JsonProperty("標準地 法令上の規制等 自然環境等コード3")
  public String naturalEnvironmentCode3;

  @JsonProperty("標準地 法令上の規制等 自然環境等文言")
  public String naturalEnvironmentDescription;

  @JsonProperty("鑑定評価手法の適用 取引事例比較法比準価格")
  public Long comparableSalesPrice; // e.g. 226000

  @JsonProperty("鑑定評価手法の適用 控除法 控除後価格")
  public Long deductionMethodPrice; // e.g. 0

  @JsonProperty("鑑定評価手法の適用 収益還元法 収益価格")
  public Long incomeApproachPrice; // e.g. 0

  @JsonProperty("鑑定評価手法の適用 原価法 積算価格")
  public Long costApproachPrice; // e.g. 0

  @JsonProperty("鑑定評価手法の適用 開発法 開発法による価格")
  public Long developmentMethodPrice; // e.g. 0

  @JsonProperty("比準価格算定内訳事例a 取引価格")
  public Long caseATransactionPrice; // e.g. 121103

  @JsonProperty("比準価格算定内訳事例a 推定価格")
  public Long caseAEstimatedPrice; // e.g. 132512

  @JsonProperty("比準価格算定内訳事例a 標準価格")
  public Long caseAStandardPrice; // e.g. 227293

  @JsonProperty("比準価格算定内訳事例a 査定価格")
  public Long caseAAssessedPrice; // e.g. 227000

  @JsonProperty("比準価格算定内訳事例b 取引価格")
  public Long caseBTransactionPrice; // e.g. 237955

  @JsonProperty("比準価格算定内訳事例b 推定価格")
  public Long caseBEstimatedPrice; // e.g. 270317

  @JsonProperty("比準価格算定内訳事例b 標準価格")
  public Long caseBStandardPrice; // e.g. 253343

  @JsonProperty("比準価格算定内訳事例b 査定価格")
  public Long caseBAssessedPrice; // e.g. 253000

  @JsonProperty("比準価格算定内訳事例c 取引価格")
  public Long caseCTransactionPrice; // e.g. 205328

  @JsonProperty("比準価格算定内訳事例c 推定価格")
  public Long caseCEstimatedPrice; // e.g. 217059

  @JsonProperty("比準価格算定内訳事例c 標準価格")
  public Long caseCStandardPrice; // e.g. 228965

  @JsonProperty("比準価格算定内訳事例c 査定価格")
  public Long caseCAssessedPrice; // e.g. 229000

  @JsonProperty("比準価格算定内訳事例d 取引価格")
  public Long caseDTransactionPrice; // e.g. 257629

  @JsonProperty("比準価格算定内訳事例d 推定価格")
  public Long caseDEstimatedPrice; // e.g. 302972

  @JsonProperty("比準価格算定内訳事例d 標準価格")
  public Long caseDStandardPrice; // e.g. 309155

  @JsonProperty("比準価格算定内訳事例d 査定価格")
  public Long caseDAssessedPrice; // e.g. 309000

  @JsonProperty("比準価格算定内訳事例e 取引価格")
  public Long caseETransactionPrice; // e.g. 175636

  @JsonProperty("比準価格算定内訳事例e 推定価格")
  public Long caseEEstimatedPrice; // e.g. 196382

  @JsonProperty("比準価格算定内訳事例e 標準価格")
  public Long caseEStandardPrice; // e.g. 221151

  @JsonProperty("比準価格算定内訳事例e 査定価格")
  public Long caseEAssessedPrice; // e.g. 221000

  @JsonProperty("積算価格算定内訳素地の取得価格")
  public Long rawLandAcquisitionPrice; // e.g. 0

  @JsonProperty("積算価格算定内訳造成工事費")
  public Long developmentConstructionCost; // e.g. 0

  @JsonProperty("積算価格算定内訳再調達原価")
  public Long replacementCost; // e.g. 0

  @JsonProperty("収益価格算定内訳総収益")
  public Long totalRevenue; // e.g. 0

  @JsonProperty("収益価格算定内訳総費用")
  public String totalExpenses; // empty string possible

  @JsonProperty("収益価格算定内訳純収益")
  public Long netIncome; // e.g. 0

  @JsonProperty("収益価格算定内訳建物に帰属する純収益")
  public Long buildingNetIncome; // e.g. 0

  @JsonProperty("収益価格算定内訳土地に帰属する純収益")
  public Long landNetIncome; // e.g. 0

  @JsonProperty("収益価格算定内訳未収入期間修正後の純収益")
  public Long adjustedNetIncome; // e.g. 0

  @JsonProperty("収益価格算定内訳還元利回り")
  public Double capitalizationRate; // e.g. 0

  @JsonProperty("開発法価格算定内訳 収入の現価の総和")
  public Long totalPresentValueOfRevenue; // e.g. 0

  @JsonProperty("開発法価格算定内訳 支出の現価の総和")
  public Long totalPresentValueOfExpenses; // e.g. 0

  @JsonProperty("開発法価格算定内訳 投下資本収益率")
  public Double returnOnInvestedCapital; // e.g. 0

  @JsonProperty("開発法価格算定内訳 販売単価(住宅)")
  public Long residentialUnitSalePrice; // e.g. 0

  @JsonProperty("開発法価格算定内訳 分譲可能床面積")
  public String leasableFloorArea; // empty string possible

  @JsonProperty("開発法価格算定内訳 建築工事費")
  public Long constructionCost; // e.g. 0

  @JsonProperty("開発法価格算定内訳 延床面積")
  public Float totalFloorArea; // e.g. 0

  @JsonProperty("公示価格")
  public Long officialLandPrice; // e.g. 226000

  @JsonProperty("変動率")
  public Double changeRate; // e.g. 7.6

  @JsonProperty("位置座標 緯度") // was: 緯度
  public Double latitude;

  @JsonProperty("位置座標 経度") // was: 経度
  public Double longitude;
}
