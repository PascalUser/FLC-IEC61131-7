package unit.generator.lowers.facades;

import compiler.generator.lowers.facades.IntegerDomainResolver;
import compiler.generator.lowers.utils.IntegerDomain;
import compiler.repository.LexemeInfo;
import compiler.repository.SymbolTable;
import compiler.repository.builders.LexemeInfoBuilder;
import compiler.repository.enums.Subtype;
import compiler.repository.enums.Type;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IntegerDomainResolverTest {
    private static LexemeInfo subrange(Subtype base, String low, String high) {
        return new LexemeInfoBuilder()
                .type(Type.SUBRANGE)
                .subtype(base)
                .inferiorLimits(Collections.singletonList(low))
                .superiorLimits(Collections.singletonList(high))
                .build();
    }

    private static LexemeInfo simple(Subtype subtype) {
        return new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(subtype)
                .build();
    }

    private static IntegerDomain domain(LexemeInfo info) {
        return IntegerDomainResolver.resolve(info, new SymbolTable()).orElseThrow(AssertionError::new);
    }

    @Test
    void Resolve_WithUnsignedSimpleTypes_ReturnsDomainWithBaseWidthAndNoOffset() {
        assertEquals(8, domain(simple(Subtype.USINT)).bits());
        assertEquals(16, domain(simple(Subtype.UINT)).bits());
        assertFalse(domain(simple(Subtype.USINT)).isShifted());
        assertFalse(domain(simple(Subtype.UINT )).isShifted());
    }

    @Test
    void Resolve_WithSignedSimpleTypes_ReturnsDomainWithBaseWidthAndNoOffset() {
        assertEquals(8,  domain(simple(Subtype.SINT)).bits());
        assertEquals(16, domain(simple(Subtype.INT) ).bits());
        assertEquals(32, domain(simple(Subtype.DINT)).bits());
        assertEquals(64, domain(simple(Subtype.LINT)).bits());
        assertTrue(domain(simple(Subtype.SINT)).isShifted());
        assertTrue(domain(simple(Subtype.INT )).isShifted());
        assertTrue(domain(simple(Subtype.DINT)).isShifted());
        assertTrue(domain(simple(Subtype.LINT)).isShifted());
        assertEquals(BigInteger.valueOf(-(1L << 7)),  domain(simple(Subtype.SINT)).offset());
        assertEquals(BigInteger.valueOf(-(1L << 15)), domain(simple(Subtype.INT )).offset());
        assertEquals(BigInteger.valueOf(-(1L << 31)), domain(simple(Subtype.DINT)).offset());
        assertEquals(BigInteger.valueOf(-(1L << 63)), domain(simple(Subtype.LINT)).offset());
    }

    @Test
    void Resolve_WithBooleanOrRealTypes_ReturnsEmptyOptional() {
        for (Subtype s : new Subtype[]{Subtype.REAL, Subtype.BOOL}) {
            assertFalse(IntegerDomainResolver.resolve(simple(s), new SymbolTable()).isPresent(), s.name());
        }
    }

    @Test
    void Resolve_WithNarrowSubrangeFarFromZero_ReturnsDomainWithCalculatedOffsetAndBits() {
        IntegerDomain d = domain(subrange(Subtype.UDINT, "10000000", "10000001"));
        assertEquals(1, d.bits());
        assertEquals(BigInteger.valueOf(10000000), d.offset());
    }

    @Test
    void Resolve_WithValidSubranges_ReturnsDomainWithBitsMatchingSpan() {
        assertEquals(8, domain(subrange(Subtype.INT, "0", "255")).bits());
        assertEquals(9, domain(subrange(Subtype.INT, "0", "256")).bits());
        assertEquals(4, domain(subrange(Subtype.INT, "-5", "5")).bits());
        assertEquals(1, domain(subrange(Subtype.INT, "7", "7")).bits());
    }

    @Test
    void Resolve_WithMalformedSubranges_ReturnsEmptyOptional() {
        assertFalse(IntegerDomainResolver.resolve(subrange(Subtype.INT, "5", "1"), new SymbolTable()).isPresent());
        assertFalse(IntegerDomainResolver.resolve(subrange(Subtype.INT, "", "1"), new SymbolTable()).isPresent());
    }

    @Test
    void Resolve_WithNamedCustomType_ResolvesThroughSymbolTableAndReturnsDomain() {
        SymbolTable table = new SymbolTable();
        table.put("DAY", subrange(Subtype.INT, "0", "31"));
        LexemeInfo variable = new LexemeInfoBuilder()
                .type(Type.SIMPLE)
                .subtype(Subtype.CUSTOM)
                .customType("DAY")
                .build();

        Optional<IntegerDomain> domainObj = IntegerDomainResolver.resolve(variable, table);

        assertTrue(domainObj.isPresent(), "domain should exist");
        IntegerDomain domain = domainObj.get();
        assertEquals(5, domain.bits());
    }
}