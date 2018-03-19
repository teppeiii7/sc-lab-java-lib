package tokyo.scratchcrew.sclab.utils.jackson;

import java.util.Optional;

/**
 * CVSファイルを読み込むクラスです。<br>
 * 囲い文字は使用しません。<br>
 * 要素内に改行を含めることができません。
 */
public class CsvWithoutQuoteReader extends FormattedFileReader {

    @Override
    protected Character separator() {
        return ',';
    }

    @Override
    protected Optional<Character> quoteChar() {
        return Optional.ofNullable(null);
    }
}
