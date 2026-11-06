package compiler.generator.lowers.utils;

import java.math.BigInteger;

public final class IntegerDomain {
    private final BigInteger offset;
    private final int bits;

    public IntegerDomain(final BigInteger offset, final int bits) {
        this.offset = offset;
        this.bits = bits;
    }

    public BigInteger offset() {
        return offset;
    }

    public int bits() {
        return bits;
    }

    public boolean isShifted() {
        return offset.signum() != 0;
    }
}