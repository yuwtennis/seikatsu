package org.example.dags.realestate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dags.realestate.endpoints.EndpointKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealEstateCsv {
    public EndpointKind dlEndpoint;
    public List<CSVRecord> records;
    public String fileName;

    static Logger LOG = LoggerFactory.getLogger(RealEstateCsv.class);

    /**
     *
     * @param zs
     * @return
     * @throws IOException
     */
    public static RealEstateCsv of(ZipInputStream zs) throws IOException {
        RealEstateCsv realEstateCsv = new RealEstateCsv();
        ZipEntry entry;

        LOG.info("Start unzip");

        while ((entry = zs.getNextEntry()) != null) {
            LOG.info("Reading entry " + entry.getName());
            if (entry.getName().startsWith("Tokyo_")
                    || entry.getName().contains("TAKUCHI_k")) {
                realEstateCsv.fileName = entry.getName();
                break;
            }
        }

        if (realEstateCsv.fileName == null) {
            throw new IOException("No entries or no filename which matches prefix");
        }

        CSVParser parser = CSVParser.parse(
                zs,
                Charset.forName("windows-31j"),
                CSVFormat.RFC4180);

        realEstateCsv.records = parser.getRecords();
        LOG.info("Parsed num of records: {}", realEstateCsv.records.size());

        // Identify the type of record
        if(realEstateCsv.fileName.startsWith("Tokyo")) {
            // NOTE First record is header
            realEstateCsv.records.removeFirst();
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
        } else if(realEstateCsv.fileName.contains("TAKUCHI_k")) {
            realEstateCsv.dlEndpoint = EndpointKind.LAND_VALUE;
        } else {
            throw new IllegalStateException("Unable to identify the type of record.");
        }

        LOG.info("Parsed csv file from endpoint name: {}", realEstateCsv.dlEndpoint.value);

        return realEstateCsv;
    }
}
