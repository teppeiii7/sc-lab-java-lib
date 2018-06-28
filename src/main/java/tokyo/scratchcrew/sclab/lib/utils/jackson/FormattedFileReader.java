package tokyo.scratchcrew.sclab.lib.utils.jackson;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * CSV/TSVなどの定型ファイルを読むクラスです。
 *
 * @author teppeiii7
 *
 */
public class FormattedFileReader extends FormattedFileConfig {

    private FormattedFileReader(Builder builder) {
        super(builder);
    }

    /**
     * fieldsに定義した順序で要素名を取得してclazzにラップしたインスタンスのコレクションを返却します。
     * ファイルにヘッダが存在する場合は、skipHeaderにtrueを設定してください。
     *
     * @param clazz 戻り値のコレクションが内包するクラスです。
     * @param input
     * @param cs
     * @param fields CSVカラムの定義です。clazzのフィールド名と一致させてください。
     * @param skipHeader ヘッダを1行読み飛ばすかの判断条件します。
     * @return
     * @throws IOException
     */
    public <T> List<T> read(Class<T> clazz, InputStream input, Charset cs, List<String> fields, boolean skipHeader)
            throws IOException {
        CsvSchema csvSchema = buildCsvSchemaForFields(fields);
        csvSchema = addCsvSchemaWithFormatter(csvSchema);
        csvSchema = addCsvSchemaWithHeader(csvSchema, skipHeader);
        ObjectReader objectReader = mapper.readerFor(clazz).with(csvSchema);
        return readInputStream(input, cs, objectReader);
    }

    /**
     * fieldsからラップする要素名を順番に取得してCsvSchemaを生成します。
     *
     * @param fields
     * @return
     */
    private <T> CsvSchema buildCsvSchemaForFields(List<String> fields) {
        CsvSchema.Builder builder = CsvSchema.builder();
        fields.forEach(field -> builder.addColumn(field));
        return builder.build();
    }

    /**
     * clazzから要素名を取得して、インスタンスを含んだコレクションを返却します。<br>
     * clazz要素名の昇順(a→z)で結果が格納されます。<br>
     * 扱いには十分気をつけてください。
     *
     * @param clazz 戻り値のコレクションが内包するクラスです。
     * @param input
     * @param cs
     * @param skipHeader
     * @return
     * @throws IOException
     */
    public <T> List<T> read(Class<T> clazz, InputStream input, Charset cs, boolean skipHeader) throws IOException {
        CsvSchema.Builder builder = CsvSchema.builder();
        CsvSchema csvSchema = mapper.schemaFor(clazz);
        csvSchema = super.addCsvSchemaWithFormatter(csvSchema);
        csvSchema = addCsvSchemaWithHeader(csvSchema, skipHeader);
        ObjectReader objectReader = mapper.readerFor(clazz).with(csvSchema);
        return readInputStream(input, cs, objectReader);
    }

    /**
     * CsvSchemaに1行目のスキップ有無の設定を追加します。
     *
     * @param csvSchema
     * @param skipHeader true:1行目をスキップする, false:1行目をスキップしない
     * @return
     */
    private CsvSchema addCsvSchemaWithHeader(CsvSchema csvSchema, boolean skipHeader) {
        return csvSchema.withSkipFirstDataRow(skipHeader);
    }

    /**
     * InputStreamを読み込んでコレクションを返却します。
     *
     * @param input
     * @param cs
     * @param objectReader
     * @return
     * @throws IOException
     */
    private <T> List<T> readInputStream(InputStream input, Charset cs, ObjectReader objectReader) throws IOException {
        // 入力ストリームをObjectReaderで読み込んでclazzで定義した配列を取得します。
        Iterator<T> iterator = objectReader.readValues(new InputStreamReader(input, cs));
        List<T> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        return list;
    }

    /**
     * Builderです。
     */
    public static class Builder extends BaseBuilder<FormattedFileReader> {
        public Builder() {
            super();
        }
        @Override
        public FormattedFileReader build() {
            return new FormattedFileReader(this);
        }
    }
}
