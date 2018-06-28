package tokyo.scratchcrew.sclab.lib.utils.jackson;

import java.util.Optional;

public interface FileFormatter {

    /**
     * 区切り文字を返却します。
     *
     * @return
     */
    Character separator();

    /**
     * 囲い文字を返却します。
     *
     * @return
     */
    Optional<Character> quoteChar();

}
