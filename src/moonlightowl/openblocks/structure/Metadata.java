package moonlightowl.openblocks.structure;

/**
 * OpenBlocks.Metadata
 * Created by MoonlightOwl on 12/20/15.
 * ===
 * Additional block information
 */

public interface Metadata {
    String getValue();
    void setValue(String value);
    void fetchValue();
}
