package compiler.generator.lowers.facades;

import compiler.repository.LexemeInfo;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;

import java.util.EnumMap;
import java.util.Map;

public final class UnsignedIntegerTypes {
    private static final Map<Subtype, Integer> BITS = new EnumMap<>(Subtype.class);

    static {
        BITS.put(Subtype.USINT,  8);
        BITS.put(Subtype.UINT,  16);
        BITS.put(Subtype.UDINT, 32);
        BITS.put(Subtype.ULINT, 64);
    }

    private UnsignedIntegerTypes() {
        // Utility class - not instantiable
    }

    public static boolean accepts(final LexemeInfo info) {
        boolean simpleOrSubrange = info.type == Type.SIMPLE || info.type == Type.SUBRANGE;
        return simpleOrSubrange && BITS.containsKey(info.subtype);
    }

    public static int bits(final Subtype subtype) {
        Integer bits = BITS.get(subtype);
        if (bits == null) {
            throw new IllegalArgumentException("Not an unsigned integer type: " + subtype);
        }
        return bits;
    }
}
