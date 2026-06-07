package org.example.dags.realestate.mlit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.beam.sdk.schemas.JavaFieldSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

// TODO: Fix [direct-runner-worker] WARN org.apache.beam.sdk.util.MutationDetectors - Coder of type
// class org.apache.beam.sdk.schemas.SchemaCoder has a #structuralValue method which does not return
// true when the encoding of the elements is equal. Element
// org.example.dags.realestate.mlit.RealEstatesPrice@2cdc717e
// Perhaps use a intermediate object Map<String, String> then pass it to ValueSchema

/** RealEstatesPrice. */
@DefaultSchema(JavaFieldSchema.class)
public class RealEstatesPrice extends Mlit {

  @JsonProperty("PriceCategory")
  public String priceCategory;

  @JsonProperty("Type")
  public String type;

  @JsonProperty("Region")
  public String region;

  @JsonProperty("MunicipalityCode")
  public String municipalityCode; // changed: int → String (e.g. "13101", may have leading zeros)

  @JsonProperty("Prefecture")
  public String prefecture;

  @JsonProperty("Municipality")
  public String municipality;

  @JsonProperty("DistrictName")
  public String districtName;

  @JsonProperty("TradePrice")
  public Long tradePrice; // changed: long → Long (nullable, empty string possible)

  @JsonProperty("PricePerUnit")
  public String pricePerUnit; // changed: long → String (empty string possible)

  @JsonProperty("FloorPlan")
  public String floorPlan;

  @JsonProperty("Area")
  public String area; // changed: int → String (empty string possible)

  @JsonProperty("UnitPrice")
  public String unitPrice; // changed: long → String (empty string possible)

  @JsonProperty("LandShape")
  public String landShape;

  @JsonProperty("Frontage")
  public String frontage; // changed: int → String (empty string possible)

  @JsonProperty("TotalFloorArea")
  public String totalFloorArea; // changed: int → String (empty string possible)

  @JsonProperty("BuildingYear")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年")
  public String buildingYear;

  @JsonProperty("Structure")
  public String structure;

  @JsonProperty("Use")
  public String use;

  @JsonProperty("Purpose")
  public String purpose;

  @JsonProperty("Direction")
  public String direction;

  @JsonProperty("Classification")
  public String classification;

  @JsonProperty("Breadth")
  public String breadth; // changed: int → String (empty string possible)

  @JsonProperty("CityPlanning")
  public String cityPlanning;

  @JsonProperty("CoverageRatio")
  public String coverageRatio; // changed: int → String (empty string possible)

  @JsonProperty("FloorAreaRatio")
  public String floorAreaRatio; // changed: int → String (empty string possible)

  @JsonProperty("Period")
  @JsonDeserialize(converter = JapanesePeriodConverter.class)
  public String period;

  @JsonProperty("Renovation")
  public String renovation;

  @JsonProperty("Remarks")
  public String remarks;

  @JsonProperty("DistrictCode")
  public String districtCode; // changed: long → String (code field, may have leading zeros)
}
