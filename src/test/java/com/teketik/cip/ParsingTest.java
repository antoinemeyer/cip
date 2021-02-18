package com.teketik.cip;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.teketik.cip.CorrelatedIterable;
import com.teketik.cip.CorrelatedPayload;
import com.teketik.cip.IteratorDefinition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParsingTest {


    @Test
    public void test() throws IOException {
        try (
            final Reader aReader = Files.newBufferedReader(new File("src/test/resources/a.csv").toPath());
            final Reader bReader = Files.newBufferedReader(new File("src/test/resources/b.csv").toPath());
        ) {
            final CsvToBean<AEntry> aCsvToBean = makeACsvToBean(aReader, AEntry.class);
            final CsvToBean<BEntry> bCsvToBean = makeACsvToBean(bReader, BEntry.class);

            final CorrelatedIterable correlatedIterable = new CorrelatedIterable(
                    new IteratorDefinition<AEntry>(aCsvToBean.iterator(), AEntry.class),
                    new IteratorDefinition<BEntry>(bCsvToBean.iterator(), BEntry.class)
            );


            final Iterator<CorrelatedPayload> iterator = correlatedIterable.iterator();
            Assertions.assertEquals(
                    new CorrelatedPayload("1", mapOf(
                            AEntry.class, listOf(
                                    new AEntry("a", "1", "ak1v1"),
                                    new AEntry("a", "1", "ak1v2")
                            ),
                            BEntry.class, listOf(
                                    new BEntry("1", "bk1v1"),
                                    new BEntry("1", "bk1v2")
                            )
                    )),
                    iterator.next()
            );

            Assertions.assertEquals(
                    new CorrelatedPayload("2", mapOf(
                            AEntry.class, listOf(
                                    new AEntry("a", "2", "ak2v1"),
                                    new AEntry("a", "2", "ak2v2")
                            ),
                            BEntry.class, listOf(
                                    new BEntry("2", "bk2v1"),
                                    new BEntry("2", "bk2v2")
                            )
                    )),
                    iterator.next()
            );

            Assertions.assertFalse(iterator.hasNext());
        }



    }

    private <T, U> Map<T, U> mapOf(T t1, U u1, T t2, U u2) {
        final HashMap<T, U> map = new HashMap<T, U>();
        map.put(t1, u1);
        map.put(t2, u2);
        return map;
    }

    private <T> List<T> listOf(T... ts) {
        return Arrays.asList(ts);
    }

    private <T> CsvToBean<T> makeACsvToBean(final Reader aReader, Class<T> type) {
        final ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<T>();
        strategy.setType(type);
        return new CsvToBeanBuilder(aReader)
                .withMappingStrategy(strategy)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
    }

}
