package compiler.generator.lowers.facades;

import compiler.generator.lowers.utils.IntegerDomain;
import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;

import java.math.BigInteger;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class IntegerDomainResolver {
    private static final Map<Subtype, Integer> UNSIGNED_BITS = new EnumMap<>(Subtype.class);

    private static final Set<Subtype> INTEGERS = EnumSet.of(
            Subtype.SINT,
            Subtype.INT,
            Subtype.DINT,
            Subtype.LINT,
            Subtype.USINT,
            Subtype.UINT,
            Subtype.UDINT,
            Subtype.ULINT
    );

    static {
        UNSIGNED_BITS.put(Subtype.USINT,  8);
        UNSIGNED_BITS.put(Subtype.UINT,  16);
        UNSIGNED_BITS.put(Subtype.UDINT, 32);
        UNSIGNED_BITS.put(Subtype.ULINT, 64);
    }

    private IntegerDomainResolver() {
        // Utility class - not instantiable
    }

    public static Optional<IntegerDomain> resolve(final LexemeInfo info, final SymbolTable table) {
        LexemeInfo resolved = info;
        while (resolved != null && resolved.subtype == Subtype.CUSTOM) {
            resolved = table.get(resolved.customType);
        }
        if (resolved == null) {
            return Optional.empty();
        }
        if (resolved.type == Type.SIMPLE && UNSIGNED_BITS.containsKey(resolved.subtype)) {
            return Optional.of(new IntegerDomain(BigInteger.ZERO, UNSIGNED_BITS.get(resolved.subtype)));
        }
        if (resolved.type == Type.SUBRANGE && INTEGERS.contains(resolved.subtype)) {
            return subrange(resolved);
        }
        return Optional.empty();
    }

    private static Optional<IntegerDomain> subrange(final LexemeInfo info) {
        try {
            BigInteger low = new BigInteger(info.inferiorLimits.get(0));
            BigInteger high = new BigInteger(info.superiorLimits.get(0));
            if (high.compareTo(low) < 0) {
                return Optional.empty();
            }
            return Optional.of(new IntegerDomain(low, Math.max(1, high.subtract(low).bitLength())));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}