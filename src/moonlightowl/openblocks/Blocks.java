package moonlightowl.openblocks;

import moonlightowl.openblocks.structure.Block;
import moonlightowl.openblocks.structure.action.*;
import moonlightowl.openblocks.structure.cycle.*;
import moonlightowl.openblocks.structure.logic.*;
import moonlightowl.openblocks.structure.robot.*;
import moonlightowl.openblocks.structure.value.*;

import java.util.function.Supplier;

/**
 * OpenBlocks.Blocks
 * Created by MoonlightOwl on 11/4/15.
 * ===
 * Index of all IDE blocks
 */
public class Blocks {
    /** Categories */
    public enum Category { ROBOT, ACTION, CYCLE, LOGIC, VALUE }
    public enum Id {
        START(Category.ROBOT, "Начало", Start::new),
        END(Category.ROBOT, "Конец", End::new),
        FORWARD(Category.ACTION, "Вперед", Forward::new),
        BACK(Category.ACTION, "Назад", Back::new),
        UP(Category.ACTION, "Вверх", Up::new),
        DOWN(Category.ACTION, "Вниз", Down::new),
        LEFT(Category.ACTION, "Поворот влево", Left::new),
        RIGHT(Category.ACTION, "Поворот вправо", Right::new),
        AROUND(Category.ACTION, "Разворот на 180", Around::new),
        DIG(Category.ACTION, "Копать вперед", Dig::new),
        DIGUP(Category.ACTION, "Копать вверх", DigUp::new),
        DIGDOWN(Category.ACTION, "Копать вниз", DigDown::new),
        BUILD(Category.ACTION, "Строить впереди", Build::new),
        BUILDUP(Category.ACTION, "Строить сверху", BuildUp::new),
        BUILDDOWN(Category.ACTION, "Строить снизу", BuildDown::new),
        DETECT(Category.ACTION, "Блок на пути?", Detect::new),
        DETECTUP(Category.ACTION, "Блок сверху?", DetectUp::new),
        DETECTDOWN(Category.ACTION, "Блок снизу?", DetectDown::new),
        INPUT(Category.ACTION, "Ввод с консоли", Input::new),
        PRINT(Category.ACTION, "Печать в консоль", Print::new),
        SELECTSLOT(Category.ACTION, "Выбрать слот", SelectSlot::new),
        FOR(Category.CYCLE, "Повторить N раз", For::new),
        IF(Category.LOGIC, "Если ... иначе", If::new),
        NOT(Category.LOGIC, "Не", Not::new),
        EQUALS(Category.LOGIC, "Равно", Equals::new),
        NOTEQUALS(Category.LOGIC, "Не равно", NotEquals::new),
        LESS(Category.LOGIC, "Меньше", Less::new),
        LESSOREQUALS(Category.LOGIC, "Меньше или равно", LessOrEquals::new),
        GREATER(Category.LOGIC, "Больше", Greater::new),
        GREATEROREQUALS(Category.LOGIC, "Больше или равно", GreaterOrEquals::new),
        AND(Category.LOGIC, "И", And::new),
        OR(Category.LOGIC, "ИЛИ", Or::new),
        CONST(Category.VALUE, "Константа", Const::new);

        public final int id;
        public final Category category;
        public final String name;
        private Supplier<Block> supplier;

        Id(Category category, String name, Supplier<Block> supplier){
            this.category = category; this.name = name; this.supplier = supplier;
            this.id = ordinal();
        }
        public Block getInstance(){ return supplier.get(); }
    }

//        all.put(SELECT_SLOT, new Desc(SELECT_SLOT, Category.ACTION, "Выбрать слот"));
//        all.put(WHILE, new Desc(WHILE, Category.CYCLE, "Повторять пока"));
//        all.put(FOR, new Desc(FOR, Category.CYCLE, "Повторить N раз"));
//        all.put(LOOP, new Desc(LOOP, Category.CYCLE, "Вечный цикл"));
//        all.put(LESS, new Desc(LESS, Category.LOGIC, "Меньше"));
//        all.put(GREATER, new Desc(GREATER, Category.LOGIC, "Больше"));
//        all.put(EQUAL, new Desc(EQUAL, Category.LOGIC, "Равно"));
//        all.put(LESS_OR_EQUAL, new Desc(LESS_OR_EQUAL, Category.LOGIC, "Меньше или равно"));
//        all.put(GREATER_OR_EQUAL, new Desc(GREATER_OR_EQUAL, Category.LOGIC, "Больше или равно"));
//        all.put(NOT_EQUAL, new Desc(NOT_EQUAL, Category.LOGIC, "Не равно"));
}
