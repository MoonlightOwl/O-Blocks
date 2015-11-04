package moonlightowl.openblocks;

import moonlightowl.openblocks.ui.ToolButton;

import java.util.HashMap;

/**
 * OpenBlocks.Blocks
 * Created by MoonlightOwl on 11/4/15.
 * ===
 * Index of all IDE blocks
 */
public class Blocks {
    /** Categories */
    public static int ROBOT = 0, ACTION = 1, CYCLE = 2, LOGIC = 3;
    /** Robot blocks */
    public static int START = 0, END = 1;
    /** Action blocks */
    public static int MOVE = 2, DIG = 3, BUILD = 4, SELECT_SLOT = 5;
    /** Cycle blocks */
    public static int WHILE = 6, FOR = 7, LOOP = 8;
    /** Logic blocks */
    public static int IF = 9, LESS = 10, GREATER = 11, EQUAL = 12,
            LESS_OR_EQUAL = 13, GREATER_OR_EQUAL = 14, NOT_EQUAL = 15, NOT = 16;

    public static Blocks instance;
    public static Blocks getInstance(){
        if(instance == null) instance = new Blocks();
        return instance;
    }

    public HashMap<Integer, Desc> all = new HashMap<>();
    public Blocks() {
        all.put(START, new Desc(START, ROBOT, "Начало"));
        all.put(END, new Desc(END, ROBOT, "Конец"));
        all.put(MOVE, new Desc(MOVE, ACTION, "Двигаться"));
        all.put(DIG, new Desc(DIG, ACTION, "Копать"));
        all.put(BUILD, new Desc(BUILD, ACTION, "Строить"));
        all.put(SELECT_SLOT, new Desc(SELECT_SLOT, ACTION, "Выбрать слот"));
        all.put(WHILE, new Desc(WHILE, CYCLE, "Повторять пока"));
        all.put(FOR, new Desc(FOR, CYCLE, "Повторить N раз"));
        all.put(LOOP, new Desc(LOOP, CYCLE, "Вечный цикл"));
        all.put(IF, new Desc(IF, LOGIC, "Если .. иначе"));
        all.put(LESS, new Desc(LESS, LOGIC, "Меньше"));
        all.put(GREATER, new Desc(GREATER, LOGIC, "Больше"));
        all.put(EQUAL, new Desc(EQUAL, LOGIC, "Равно"));
        all.put(LESS_OR_EQUAL, new Desc(LESS_OR_EQUAL, LOGIC, "Меньше или равно"));
        all.put(GREATER_OR_EQUAL, new Desc(GREATER_OR_EQUAL, LOGIC, "Больше или равно"));
        all.put(NOT_EQUAL, new Desc(NOT_EQUAL, LOGIC, "Не равно"));
        all.put(NOT, new Desc(NOT, LOGIC, "Не"));
    }

    class Desc {
        public int id, category;
        public ToolButton tool;

        public Desc(int id, int category, String name){
            this.id = id; this.category = category;
            this.tool = new ToolButton(name, Assets.toolIcons[id]);
        }
    }
}
