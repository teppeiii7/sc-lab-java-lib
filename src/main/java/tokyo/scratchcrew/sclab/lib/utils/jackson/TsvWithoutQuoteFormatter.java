package tokyo.scratchcrew.sclab.lib.utils.jackson;

import java.util.Optional;

/**
 * TVSファイルを読み込むクラスです。<br>
 * 囲い文字は使用しません。<br>
 */
public class TsvWithoutQuoteFormatter implements FileFormatter {

    @Override
    public Character separator() {
        return '\t';
    }

    @Override
    public Optional<Character> quoteChar() {
        return Optional.ofNullable(null);
    }
}
