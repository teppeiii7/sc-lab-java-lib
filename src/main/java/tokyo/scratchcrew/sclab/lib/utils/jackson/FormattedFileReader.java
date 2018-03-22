package tokyo.scratchcrew.sclab.lib.utils.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * CSV/TSVなどの定型ファイルを読むクラスです。
 * 
 * @author teppeiii7
 *
 */
public abstract class FormattedFileReader {

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

    /**
     * fieldsに定義した要素名から取得して、インスタンスを含んだコレクションを返却します。
     * ファイルにヘッダが存在する場合は、skipHeaderにtrueを設定してください。
     *
     * @param clazz 戻り値のコレクションが内包するクラスです。
     * @param file
     * @param cs
     * @param fields CSVカラムの定義です。clazzのフィールド名と一致させてください。
     * @param skipHeader ヘッダを1行読み飛ばすかの判断条件します。
     * @return
     * @throws java.io.IOException
     */
    public <T> List<T> read(Class<T> clazz, InputStream input, Charset cs, List<String> fields, boolean skipHeader)
                    throws IOException {
        CsvSchema csvSchema = buildCsvSchemaForFields(fields, skipHeader);
        ObjectReader objectReader = buildObjectReader(clazz, csvSchema);
        return readInputStream(input, cs, objectReader);
    }

    /**
     * clazzから要素名を取得して、インスタンスを含んだコレクションを返却します。<br>
     * clazz要素名の昇順(a→z)で結果が格納されます。<br>
     * 扱いには十分気をつけてください。
     *
     * @param clazz 戻り値のコレクションが内包するクラスです。
     * @param input
     * @param cs
     * @return
     * @throws java.io.IOException
     */
    public <T> List<T> read(Class<T> clazz, InputStream input, Charset cs) throws IOException {
        CsvSchema csvSchema = buildCsvSchemaForCsvMapper(clazz);
        ObjectReader objectReader = buildObjectReader(clazz, csvSchema);
        return readInputStream(input, cs, objectReader);
    }

    /**
     * clazzからCsvSchemaを生成します。
     * 
     * @param clazz
     * @return
     */
    private <T> CsvSchema buildCsvSchemaForCsvMapper(Class<T> clazz) {
        return mapper.schemaFor(clazz);
    }

    /**
     * fieldsから要素名を取得してCsvSchemaを生成します。
     * 
     * @param fields
     * @param skipHeader
     * @return
     */
    private <T> CsvSchema buildCsvSchemaForFields(List<String> fields, boolean skipHeader) {
        // fieldsからラップするカラム名を順番に定義してスキーマを生成します。
        CsvSchema.Builder builder = CsvSchema.builder();
        fields.forEach(field -> builder.addColumn(field));
        builder.setSkipFirstDataRow(skipHeader);
        return builder.build();
    }

    /**
     * ObjectReaderを生成します。
     * 
     * @param clazz
     * @param csvSchema
     * @return
     */
    private <T> ObjectReader buildObjectReader(Class<T> clazz, CsvSchema csvSchema) {

        // 区切り定義(,タブetc..)を定義します
        csvSchema = csvSchema.withColumnSeparator(separator());

        // 囲い文字を定義します
        if (quoteChar().isPresent()) {
            csvSchema = csvSchema.withQuoteChar(quoteChar().get());
        } else {
            csvSchema = csvSchema.withoutQuoteChar();
        }
        return mapper.readerFor(clazz).with(csvSchema);
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
}
