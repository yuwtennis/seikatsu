package org.example.dags.realestate;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RealEstateCsv {
    public EndpointKind dlEndpoint;
    public List<CSVRecord> records;

    static Logger LOG = LoggerFactory.getLogger(RealEstateCsv.class);

    public static RealEstateCsv of(ZipInputStream zs ) throws IOException {
        RealEstateCsv realEstateCsv = new RealEstateCsv();
        ZipEntry entry;

        while ((entry = zs.getNextEntry()) != null) {
            if (entry.getName().startsWith("Tokyo_")
                    || entry.getName().contains("TAKUCHI_k")) {
                break;
            }
        }

        CSVParser parser = CSVFormat.RFC4180.builder()
                .setHeader()
                .setIgnoreEmptyLines(true)
                .build()
                .parse(new InputStreamReader(zs, Charset.forName("windows-31j")));

        realEstateCsv.records = parser.getRecords();
        LOG.info("Parsed num of records: {}", realEstateCsv.records.size());

        // Identify the type of record
        if(Objects.equals(parser.getHeaderNames().getFirst(), "種類")) {
            switch (realEstateCsv.records.getFirst().get(0)) {
                case "宅地(土地)", "宅地(土地と建物)":
                    realEstateCsv.dlEndpoint = EndpointKind.RESIDENTIAL_LAND;
                    break;
                case "中古マンション等":
                    realEstateCsv.dlEndpoint = EndpointKind.USED_APARTMENT;
                    break;
                default:
                    throw new IllegalStateException(
                            "Unexpected value: " + realEstateCsv.records.getFirst().get(0));
            }
        } else if(Objects.equals(parser.getHeaderNames().getFirst(), "評価時点")) {
            realEstateCsv.dlEndpoint = EndpointKind.LAND_VALUE;
        } else {
            throw new IllegalStateException("Unable to identify the type of record.");
        }

        LOG.info("Parsed csv file from endpoint name: {}", realEstateCsv.dlEndpoint.value);

        return realEstateCsv;
    }
}
