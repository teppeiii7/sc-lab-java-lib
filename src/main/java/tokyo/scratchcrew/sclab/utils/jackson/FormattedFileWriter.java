package tokyo.scratchcrew.sclab.utils.jackson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * CSV/TSVなどの定型ファイルを出力するクラスです。
 * 
 * @author teppeiii7
 *
 */
public abstract class FormattedFileWriter {

    private CsvMapper mapper; // thread safe.

    @PostConstruct
    public void init() {
        mapper = new CsvMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    protected abstract Character separator();

    protected abstract Optional<Character> quoteChar();

    public <T> Object write(Class<T> clazz, List<T> items, Charset cs, boolean withHeader) throws IOException {
        ObjectWriter objectWriter = buildObjectWriter(clazz, withHeader);
        return objectWriter.writeValueAsString(items);
    }

    public <T> void writeFile(Class<T> clazz, List<T> items, String filePathName, Charset cs, boolean withHeader)
                    throws IOException {

        ObjectWriter objectWriter = buildObjectWriter(clazz, withHeader);

        File file = new File(filePathName);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), cs)) {
            objectWriter.writeValue(writer, items);
        }
    }
    
    public <T> void writeFile(Class<T> clazz, List<T> items, File file, Charset cs, boolean withHeader)
                    throws IOException {

        ObjectWriter objectWriter = buildObjectWriter(clazz, withHeader);

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), cs)) {
            objectWriter.writeValue(writer, items);
        }
    }

    private <T> ObjectWriter buildObjectWriter(Class<T> clazz, boolean withHeader) {
        CsvSchema csvSchema = mapper.schemaFor(clazz);
        if (withHeader) {
            csvSchema = csvSchema.withHeader();
        }

        // 区切り定義(,タブetc..)を定義します
        csvSchema = csvSchema.withColumnSeparator(separator());

        // 囲い文字を定義します
        if (quoteChar().isPresent()) {
            csvSchema = csvSchema.withQuoteChar(quoteChar().get());
        } else {
            csvSchema = csvSchema.withoutQuoteChar();
        }

        return mapper.writer(csvSchema);
    }

}