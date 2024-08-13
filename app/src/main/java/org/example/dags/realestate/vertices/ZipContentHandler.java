package org.example.dags.realestate.vertices;

import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.*;
import org.apache.commons.csv.CSVRecord;
import org.example.dags.realestate.RealEstateCsv;
import org.example.dags.realestate.endpoints.EndpointKind;
import org.example.dags.realestate.landvalue.LandValue;
import org.example.dags.realestate.txn.ResidentialLandTxn;
import org.example.dags.realestate.txn.UsedApartmentTxn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.ZipInputStream;

public class ZipContentHandler {
    static Logger LOG = LoggerFactory.getLogger(ZipContentHandler.class);

    public static final TupleTag<ResidentialLandTxn> residentialLand = new TupleTag<>() {};
    public static final TupleTag<UsedApartmentTxn> usedApartment = new TupleTag<>() {};
    public static final TupleTag<LandValue> landValue = new TupleTag<>() {};

    static class UnzipFn
            extends DoFn<byte[], ResidentialLandTxn> {
        /**
         *
         * @param c
         * @param out
         * @throws IOException
         * @throws Exception
         */
        @ProcessElement
        public void processElement(ProcessContext c, MultiOutputReceiver out) throws IOException, Exception {
            byte[] b = c.element();
            ByteArrayInputStream bis = new ByteArrayInputStream(b);
            ZipInputStream zs = new ZipInputStream(bis);
            RealEstateCsv realEstateCsv = RealEstateCsv.of(zs);

            // Flatten
            for(CSVRecord record : realEstateCsv.records) {
                switch (realEstateCsv.dlEndpoint) {
                    case EndpointKind.RESIDENTIAL_LAND:
                        out.get(residentialLand).output(ResidentialLandTxn.of(record));
                        break;
                    case EndpointKind.USED_APARTMENT:
                        out.get(usedApartment).output(UsedApartmentTxn.of(record));
                        break;
                    case EndpointKind.LAND_VALUE:
                        out.get(landValue).output(LandValue.of(realEstateCsv.fileName, record));
                        break;
                    default:
                        throw new IllegalStateException("Unknown endpoint: " + realEstateCsv.dlEndpoint);
                }
            }

            zs.close();
         }
    }

    public static class Extract
            extends PTransform<PCollection<byte[]>, PCollectionTuple> {
        /**
         *
         * @param input
         * @return
         */
        @Override
        public PCollectionTuple expand(PCollection<byte[]> input) {
            return input
                    .apply("ToEntites", ParDo.of(new UnzipFn())
                            .withOutputTags(residentialLand,
                                    TupleTagList
                                            .of(usedApartment)
                                            .and(landValue)));
        }
    }
}
