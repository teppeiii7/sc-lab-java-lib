package tokyo.scratchcrew.sclab.lib.utils.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

/**
 * Builderの基底クラスです。
 *
 * @param <T>
 */
abstract class BaseBuilder<T> {
    CsvMapper mapper;
    FileFormatter formatter;

    BaseBuilder() {
        this.mapper = new CsvMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.formatter = new CsvWithoutQuoteFormatter();
    }

    abstract T build();

    /**
     * CsvMapperの初期設定は {@link BaseBuilder()} を参照してください。
     *
     * @param mapper
     * @return
     */
    public BaseBuilder<T> withMapper(CsvMapper mapper) {
        this.mapper = mapper;
        return this;
    }

    /**
     * FileFormatterの初期設定は {@link BaseBuilder()} を参照してください。
     *
     * @param formatter
     * @return
     */
    public BaseBuilder<T> withFormatter(FileFormatter formatter) {
        this.formatter = formatter;
        return this;
    }

}
