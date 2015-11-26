package moonlightowl.openblocks.io.lua;

import java.util.ArrayList;

/**
 * OpenBlocks.NameGen
 * Created by MoonlightOwl on 11/26/15.
 * ===
 * Wants a free name?
 */

public class NameGen {
    private static ArrayList<Character> word = new ArrayList<>();

    public static void init(){
        word.clear(); word.add('a');
    }

    private static void inc(int index) {
        if(index < word.size()) {
            char ch = word.get(index);
            if (ch < 'z') word.set(index, (char) (ch + 1));
            else {
                word.set(index, 'a');
                inc(index + 1);
            }
        } else word.add('a');
    }

    public static String getName() {
        String name = word.stream()
                .map(Object::toString)
                .reduce((acc, character) -> acc + character).get();
        inc(0);
        return name;
    }
    public static String lastName() {
        return word.stream()
                .map(Object::toString)
                .reduce((acc, character) -> acc + character).get();
    }
}
