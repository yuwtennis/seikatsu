package org.example.dags.realestate;

import com.google.api.services.bigquery.model.TableRow;
import com.google.common.collect.ImmutableList;
import org.apache.avro.generic.GenericRecord;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.example.dags.Dag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RealEstateDag implements Dag {
    static Logger LOG = LoggerFactory.getLogger(RealEstateDag.class);

    public void process(Pipeline p) {

        final List<String> urls = ImmutableList.of(
                "https://www.reinfolib.mlit.go.jp/in-api/api-aur/aur/csv/transactionPrices?language=ja&areaCondition=address&prefecture=13&transactionPrice=true&closedPrice=true&kind=residential&seasonFrom=20221&seasonTo=20224",
                "https://www.reinfolib.mlit.go.jp/in-api/api-aur/aur/csv/transactionPrices?language=ja&areaCondition=address&prefecture=13&transactionPrice=true&closedPrice=true&kind=residential&seasonFrom=20231&seasonTo=20234"
        );

        LOG.info("Start running {}", RealEstateDag.class.getSimpleName());

        // 1. Start by getting the actual url provided by the server
        PCollection<String> dlUrls = p.apply(Create.of(urls))
                .apply(new GetDlUrlVertices.DownloadUrl());

        // 2. Next get the zip contents
        PCollection<RealEstatesXactRec> xacts =  dlUrls.apply(new ExtractZipContentsVertices.Extract());

        // 3. Insert into Bigquery
        xacts.apply(BigQueryIO
                        .<RealEstatesXactRec>write()
                        .to(RealEstateEnv.FullyQualifiedTblName)
                        .withFormatFunction(e -> {
                            assert e != null;
                            return e.toTableRow();
                        })
                        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                );

        p.run().waitUntilFinish();
    }
}
