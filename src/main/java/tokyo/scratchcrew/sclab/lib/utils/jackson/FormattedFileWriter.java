package tokyo.scratchcrew.sclab.lib.utils.jackson;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * CSV/TSVなどの定型ファイルを出力するクラスです。
 *
 * @author teppeiii7
 *
 */
public class FormattedFileWriter extends FormattedFileConfig {

    private FormattedFileWriter(Builder builder) {
        super(builder);
    }

    /**
     * 定型ファイル形式のオブジェクトを返します。</>
     * ファイルへの出力は呼び出し元で実行してください。
     *
     * @param clazz
     * @param items
     * @param cs
     * @param withHeader
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> Object write(Class<T> clazz, List<T> items, Charset cs, boolean withHeader) throws IOException {
        ObjectWriter objectWriter = buildObjectWriter(clazz, withHeader);
        return objectWriter.writeValueAsString(items);
    }

    /**
     * 引数のfilePathに出力します。
     *
     * @param clazz
     * @param items
     * @param filePath
     * @param cs
     * @param withHeader
     * @param <T>
     * @throws IOException
     */
    public <T> void writeFile(Class<T> clazz, List<T> items, String filePath, Charset cs, boolean withHeader)
            throws IOException {
        ObjectWriter objectWriter = buildObjectWriter(clazz, withHeader);
        File file = new File(filePath);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), cs)) {
            objectWriter.writeValue(writer, items);
        }
    }

    /**
     * 引数のfileに出力します。
     *
     * @param clazz
     * @param items
     * @param file
     * @param cs
     * @param withHeader
     * @param <T>
     * @throws IOException
     */
    public <T> void writeFile(Class<T> clazz, List<T> items, File file, Charset cs, boolean withHeader)
            throws IOException {
        ObjectWriter objectWriter = buildObjectWriter(clazz, withHeader);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), cs)) {
            objectWriter.writeValue(writer, items);
        }
    }

    /**
     * ObjectWriterを生成します。
     *
     * @param clazz
     * @param withHeader
     * @param <T>
     * @return
     */
    private <T> ObjectWriter buildObjectWriter(Class<T> clazz, boolean withHeader) {
        CsvSchema csvSchema = mapper.schemaFor(clazz);
        if (withHeader) {
            csvSchema = csvSchema.withHeader();
        }
        csvSchema = super.addCsvSchemaWithFormatter(csvSchema);
        return mapper.writer(csvSchema);
    }

    /**
     * Builderです。
     */
    public static class Builder extends BaseBuilder<FormattedFileWriter> {
        public Builder() {
            super();
        }
        @Override
        public FormattedFileWriter build() {
            return new FormattedFileWriter(this);
        }
    }
}