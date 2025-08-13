package org.example.dags.realestate.vertices;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;
import org.apache.commons.csv.CSVRecord;
import org.example.dags.realestate.RealEstateCsv;
import org.example.dags.realestate.endpoints.EndpointKind;
import org.example.dags.realestate.landvalue.LandValue;
import org.example.dags.realestate.txn.ResidentialLandTxn;
import org.example.dags.realestate.txn.UsedApartmentTxn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ZipContentHandler {
    private ZipContentHandler() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     */
    static final Logger LOG = LoggerFactory.getLogger(ZipContentHandler.class);

    /**
     *
     */
    public static final TupleTag<ResidentialLandTxn>
            RESIDENTIAL_LAND_TXN_TUPLE_TAG =
            new TupleTag<>() {
            };

    /**
     *
     */
    public static final TupleTag<UsedApartmentTxn> USED_APARTENT_TXN_TUPLE_TAG =
            new TupleTag<>() {
            };

    /**
     *
     */
    public static final TupleTag<LandValue> LANDVALUE_TUPLE_TAG =
            new TupleTag<>() {
            };

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
        public void processElement(final ProcessContext c,
                                   final MultiOutputReceiver out)
                throws IOException, Exception {
            byte[] b = c.element();
            ByteArrayInputStream bis = new ByteArrayInputStream(b);

            try (ZipInputStream zs = new ZipInputStream(bis)) {
                RealEstateCsv realEstateCsv = RealEstateCsv.of(zs);

                // Flatten
                for (CSVRecord record : realEstateCsv.getRecords()) {
                    switch (realEstateCsv.getDlEndpoint()) {
                        case EndpointKind.RESIDENTIAL_LAND:
                            out.get(RESIDENTIAL_LAND_TXN_TUPLE_TAG)
                                    .output(ResidentialLandTxn.of(record));
                            break;
                        case EndpointKind.USED_APARTMENT:
                            out.get(USED_APARTENT_TXN_TUPLE_TAG)
                                    .output(UsedApartmentTxn.of(record));
                            break;
                        case EndpointKind.LAND_VALUE:
                            out.get(LANDVALUE_TUPLE_TAG)
                                    .output(
                                            LandValue.of(
                                                    realEstateCsv.getFileName(),
                                                    record));
                            break;
                        default:
                            throw new IllegalStateException(
                                    "Unknown endpoint: "
                                            + realEstateCsv.getDlEndpoint());
                    }
                }
            } catch (IOException e) {
                LOG.error("Error reading file", e);
            }
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
        public PCollectionTuple expand(final PCollection<byte[]> input) {
            return input
                    .apply("ToEntites", ParDo.of(new UnzipFn())
                            .withOutputTags(RESIDENTIAL_LAND_TXN_TUPLE_TAG,
                                    TupleTagList
                                            .of(USED_APARTENT_TXN_TUPLE_TAG)
                                            .and(LANDVALUE_TUPLE_TAG)));
        }
    }
}
