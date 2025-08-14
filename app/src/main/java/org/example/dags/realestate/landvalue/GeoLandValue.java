package org.example.dags.realestate.landvalue;

import static org.example.Utils.asInt;

import com.google.api.services.bigquery.model.TableRow;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;
import org.example.Magics;
import org.geojson.Feature;
import org.geojson.Point;

@DefaultCoder(AvroCoder.class)
public class GeoLandValue {
    /**
     *
     */
    private String geometry;

    /**
     *
     */
    private String pointId;

    /**
     *
     */
    private String cityCode;

    /**
     *
     */
    private String locationNumber;

    /**
     *
     */
    private int pricePerSqm;

    /**
     *
     */
    private String prefectureName;

    /**
     *
     */
    private String placeName;

    /**
     *
     */
    private String closestStationName;

    /**
     *
     */
    private int durationToClosestStationInMin;

    /**
     *
     */
    private String targetYear;

    /**
     *
     * @param f
     * @return GeoLandValue
     */
    public static GeoLandValue of(final Feature f) throws ParseException {
        GeoLandValue gLV = new GeoLandValue();
        Locale locale = new Locale("ja", "JP", "JP");
        Calendar calendar = Calendar.getInstance(locale);
        DateFormat japaseseFormat = new SimpleDateFormat("GGGGy年M月d日", locale);
        calendar.setLenient(false);
        DateFormat dateYear = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());

        Point point = (Point) f.getGeometry();
        gLV.geometry = String.format("POINT(%s %s)",
                point.getCoordinates().getLongitude(),
                point.getCoordinates().getLatitude());
        gLV.pointId = f.getProperties().get("point_id").toString();
        gLV.cityCode = f.getProperties().get("city_code").toString();
        gLV.locationNumber =
                f.getProperties().get("location_number_ja").toString();
        gLV.pricePerSqm = parsePrice(f.getProperty("u_current_years_price_ja"));
        gLV.prefectureName =
                f.getProperties().get("prefecture_name_ja").toString();
        gLV.placeName = f.getProperties().get("place_name_ja").toString();
        gLV.closestStationName = f.getProperties().get(
                "nearest_station_name_ja").toString();
        gLV.targetYear =
                dateYear.format(
                        japaseseFormat
                                .parse(
                                        f.getProperties()
                                                .get("target_year_name_ja")
                                                .toString()));
        gLV.durationToClosestStationInMin = parseDistance(
                f.getProperties()
                        .get("u_road_distance_to_nearest_station_name_ja")
                        .toString()) / asInt(
                Magics.DISTANCE_MOVED_PER_MINUTE.getValue());

        return gLV;
    }

    /**
     *
     * @return TableRow
     */
    public TableRow toTableRow() {
        TableRow row = new TableRow();
        row.set("geometry", this.geometry);
        row.set("pointId", this.pointId);
        row.set("cityCode", this.cityCode);
        row.set("locationNumber", this.locationNumber);
        row.set("prefectureName", this.prefectureName);
        row.set("placeName", this.placeName);
        row.set("pricePerSqm", this.pricePerSqm);
        row.set("closestStationName", this.closestStationName);
        row.set("targetYear", this.targetYear);
        row.set("durationToClosestStationInMin",
                this.durationToClosestStationInMin);

        return row;
    }

    private static int parsePrice(final String s) {
        String pattern = "^([0-9,]+)\\(円/㎡\\)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);

        if (!m.find()) {
            throw new IllegalArgumentException("Unknown price format: " + s);
        }

        return Integer.parseInt(m.group(1).replaceAll(",", ""));
    }

    private static int parseDistance(final String s) {
        String pattern = "^([0-9,]+)m$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);

        if (!m.find()) {
            throw new IllegalArgumentException("Unknown distance: " + s);
        }

        return Integer.parseInt(m.group(1).replaceAll(",", ""));
    }

    /**
     *
     * @return int
     */
    public int getPricePerSqm() {
        return pricePerSqm;
    }

    /**
     *
     * @return String
     */
    public String getLocationNumber() {
        return locationNumber;
    }
}
