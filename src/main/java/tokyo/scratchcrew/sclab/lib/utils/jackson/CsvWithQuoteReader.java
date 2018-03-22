package tokyo.scratchcrew.sclab.lib.utils.jackson;

import java.util.Optional;

/**
 * CVSファイルを読み込むクラスです。<br>
 * 要素はダブルクオートで囲う必要があります。<br>
 * 要素内に改行が含めることができます。
 */
public class CsvWithQuoteReader extends FormattedFileReader {

    @Override
    protected Character separator() {
        return ',';
    }

    @Override
    protected Optional<Character> quoteChar() {
        return Optional.ofNullable('"');
    }
}
