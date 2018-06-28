package tokyo.scratchcrew.sclab.lib.utils.jackson;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

class FormattedFileConfig {

    CsvMapper mapper; // thread safe.
    FileFormatter formatter;

    FormattedFileConfig(BaseBuilder builder) {
        this.mapper = builder.mapper;
        this.formatter = builder.formatter;
    }

    /**
     * CsvSchemaにFileFormatterの情報を追加します。
     *
     * @param csvSchema
     * @return
     */
    CsvSchema addCsvSchemaWithFormatter(CsvSchema csvSchema) {
        // 区切り定義(カンマ,タブetc..)を定義します
        csvSchema = csvSchema.withColumnSeparator(formatter.separator());

        // 囲い文字を定義します
        if (formatter.quoteChar().isPresent()) {
            csvSchema = csvSchema.withQuoteChar(formatter.quoteChar().get());
        } else {
            csvSchema = csvSchema.withoutQuoteChar();
        }
        return csvSchema;
    }

}
