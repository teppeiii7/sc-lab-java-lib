package tokyo.scratchcrew.sclab.lib.utils.jackson;

import org.junit.Test;

import java.util.Optional;

public class FormattedFileReaderTest {

    @Test
    public void test_builder() {

        // 標準で用意したもの
        FormattedFileReader csv_reader = new FormattedFileReader.Builder().build();

        // 匿名クラスでカスタマイズ
        FormattedFileReader tsv_reader = new FormattedFileReader.Builder().withFormatter(new FileFormatter() {
            @Override
            public Character separator() {
                return '\t';
            }
            @Override
            public Optional<Character> quoteChar() {
                return Optional.ofNullable('"');
            }
        }).build();
    }

}
