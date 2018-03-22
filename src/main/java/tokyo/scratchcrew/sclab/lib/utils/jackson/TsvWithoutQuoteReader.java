package tokyo.scratchcrew.sclab.lib.utils.jackson;

import java.util.Optional;

/**
 * TVSファイルを読み込むクラスです。<br>
 * 囲い文字は使用しません。<br>
 */
public class TsvWithoutQuoteReader extends FormattedFileReader {

    @Override
    protected Character separator() {
        return '\t';
    }

    @Override
    protected Optional<Character> quoteChar() {
        return Optional.ofNullable(null);
    }
}
