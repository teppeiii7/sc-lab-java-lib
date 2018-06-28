package tokyo.scratchcrew.sclab.lib.utils.jackson;


import java.util.Optional;

/**
 * 要素はダブルクオートで囲う必要があります。<br>
 * 要素内に改行が含めることができます。
 */
public class CsvWithoutQuoteFormatter implements FileFormatter {

    @Override
    public Character separator() {
        return ',';
    }

    @Override
    public Optional<Character> quoteChar() {
        return Optional.ofNullable('"');
    }
}
