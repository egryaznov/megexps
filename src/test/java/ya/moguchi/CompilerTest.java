package ya.moguchi;

import org.junit.Test;
import ya.moguchi.exceptions.EmptyPattern;
import ya.moguchi.exceptions.MalformedPattern;
import ya.moguchi.exceptions.UnmatchedBracket;
import ya.moguchi.internal.Compiler;
import ya.moguchi.internal.Pattern;

import static org.junit.Assert.*;

public class CompilerTest
{

    @Test
    public void test64() throws Exception
    {
        final var leftPattern = ya.moguchi.internal.Compiler.compile("ab.*");
        final var rightPattern = ya.moguchi.internal.Compiler.compile(".*ab");
        final var patient = "asdfab";
        assertFalse(Pattern.intersect(leftPattern, rightPattern).matches(patient));
    }

    @Test
    public void test63() throws Exception
    {
        final var leftPattern = ya.moguchi.internal.Compiler.compile("ab.*");
        final var rightPattern = ya.moguchi.internal.Compiler.compile(".*ab");
        final var patient = "abasdf";
        assertFalse(Pattern.intersect(leftPattern, rightPattern).matches(patient));
    }

    @Test
    public void test62() throws Exception
    {
        final var leftPattern = ya.moguchi.internal.Compiler.compile("ab.*");
        final var rightPattern = ya.moguchi.internal.Compiler.compile(".*ab");
        final var patient = "abasdfab";
        assertTrue(Pattern.intersect(leftPattern, rightPattern).matches(patient));
    }

    @Test
    public void test61() throws Exception
    {
        final var zoomer = "20..";
        final var patient = "2019";
        final var compiled = ya.moguchi.internal.Compiler.compile(zoomer);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test60() throws Exception
    {
        final var pattern = "Ca......sm";
        final var patient = "Capitalism";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test59() throws Exception
    {
        final var pattern = "Анта(на)+риву";
        final var patient = "Антананананариву";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test58() throws Exception
    {
        final var pattern = "201..";
        final var patient = "2019";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }

    @Test
    public void test57() throws Exception
    {
        final var pattern = ".*";
        final var patient = "а мы опят стоим и в трюме вода";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test56() throws Exception
    {
        final var pattern = "2..3";
        final var patient = "2003";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test55() throws Exception
    {
        final var pattern = "misss?pelled";
        final var patient = "misspelled";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test54() throws Exception
    {
        final var pattern = "201.";
        final var patient = "2019";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test53() throws Exception
    {
        final var pattern = "cd.*";
        final var patient = "cdasdf";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test08() throws Exception
    {
        final var pattern = "(ab)*";
        final var patient = "abababab";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test52() throws Exception
    {
        final var pattern = "(ab*)+";
        final var patient = "(a(b)*)+";
        final var compiled = ya.moguchi.internal.Compiler.enrich(pattern);
        assertEquals(compiled, patient);
    }

    @Test
    public void test51() throws Exception
    {
        final var pattern = "(ab)+";
        final var patient = "(ab)+";
        final var compiled = ya.moguchi.internal.Compiler.enrich(pattern);
        assertEquals(compiled, patient);
    }

    @Test
    public void test50() throws Exception
    {
        final var pattern = "a?b";
        final var patient = "(a)?b";
        final var compiled = ya.moguchi.internal.Compiler.enrich(pattern);
        assertEquals(compiled, patient);
    }

    @Test
    public void test49() throws Exception
    {
        final var pattern = "a?";
        final var patient = "(a)?";
        final var compiled = ya.moguchi.internal.Compiler.enrich(pattern);
        assertEquals(compiled, patient);
    }

    @Test
    public void test48() throws Exception
    {
        final var pattern = "ab?";
        final var patient = "a(b)?";
        final var compiled = ya.moguchi.internal.Compiler.enrich(pattern);
        assertEquals(compiled, patient);
    }

    @Test
    public void test47() throws Exception
    {
        final var pattern = "a+b?";
        final var patient = "(a)+(b)?";
        final var compiled = ya.moguchi.internal.Compiler.enrich(pattern);
        assertEquals(compiled, patient);
    }

    @Test
    public void test46() throws Exception
    {
        final var pattern = "(ab+)*";
        final var patient = "abbbbba";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }

    @Test
    public void test45() throws Exception
    {
        final var pattern = "(ab+)*";
        final var patient = "abbbbbabbb";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test44() throws Exception
    {
        final var pattern = "(ab+)*";
        final var patient = "abbbbb";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test43() throws Exception
    {
        final var pattern = "(ab+)*";
        final var patient = "";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test42() throws Exception
    {
        final var pattern = "(ab+)*";
        final var patient = "ab";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test41() throws Exception
    {
        final var pattern = "ab+";
        final var patient = "ab";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test40() throws Exception
    {
        final var pattern = "ab+";
        final var patient = "a";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }

    @Test
    public void test38() throws Exception
    {
        final var pattern = "ab*";
        final var patient = "ab";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test39() throws Exception
    {
        final var pattern = "ab+";
        final var patient = "a";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }


    @Test
    public void test37() throws Exception
    {
        final var pattern = "ab+";
        final var patient = "ab";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }


    @Test
    public void test36() throws Exception
    {
        final var pattern = "ab+";
        final var patient = "abbbbbbb";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }


    @Test
    public void test34()
    {
        final var pattern = "abc+d";
        final var enriched = Compiler.enrich(pattern);
        assertEquals("ab(c)+d", enriched);
    }

    @Test
    public void test35()
    {
        final var pattern = "abc?d";
        final var enriched = Compiler.enrich(pattern);
        assertEquals("ab(c)?d", enriched);
    }

    @Test
    public void test33()
    {
        final var pattern = "abc*d";
        final var enriched = Compiler.enrich(pattern);
        assertEquals("ab(c)*d", enriched);
    }

    @Test
    public void test32()
    {
        final var pattern = "abc*";
        final var enriched = Compiler.enrich(pattern);
        assertEquals("ab(c)*", enriched);
    }

    @Test
    public void test31()
    {
        final var pattern = "abc+";
        final var enriched = Compiler.enrich(pattern);
        assertEquals("ab(c)+", enriched);
    }

    @Test
    public void test30()
    {
        final var pattern = "abc?";
        final var enriched = Compiler.enrich(pattern);
        assertEquals("ab(c)?", enriched);
    }

    @Test
    public void test29()
    {
        final var pattern = "a(bc+)*d";
        final var enriched = Compiler.enrich(pattern);
        assertEquals("a(b(c)+)*d", enriched);
    }

    @Test
    public void test28()
    {
        final var pattern = "abc+";
        final var enriched = Compiler.enrich(pattern);
        assertEquals("ab(c)+", enriched);
    }

    @Test
    public void test27()
    {
        final var pattern = "ab+";
        final var enriched = Compiler.enrich(pattern);
        assertEquals("a(b)+", enriched);
    }

    @Test
    public void test26()
    {
        final var pattern = "ab+";
        final var enriched = Compiler.enrich(pattern);
        assertEquals("a(b)+", enriched);
    }

    @Test
    public void test24() throws Exception
    {
        final var pattern = "(bc)*d";
        final var patient = "bcbcbcbcd";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test23() throws Exception
    {
        final var pattern = "(bc)*d";
        final var patient = "d";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test25() throws Exception
    {
        final var pattern = "a(bc)*";
        final var patient = "abcbcbc";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test22() throws Exception
    {
        final var pattern = "a(bc)*";
        final var patient = "a";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test21() throws Exception
    {
        final var pattern = "a(bc)*";
        final var patient = "abcbc";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test20() throws Exception
    {
        final var pattern = "a(bc)*d";
        final var patient = "abcbcd";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test19() throws Exception
    {
        final var pattern = "a(bc)*d";
        final var patient = "abcd";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test18() throws Exception
    {
        final var pattern = "a(bc)*d";
        final var patient = "ad";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test17() throws Exception
    {
        final var pattern = "(ab)*";
        final var patient = "abba";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }

    @Test
    public void test16() throws Exception
    {
        final var pattern = "(ab)*";
        final var patient = "ab";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test15() throws Exception
    {
        final var pattern = "(ab)*";
        final var patient = "bbbbbaaaa";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }

    @Test
    public void test14() throws Exception
    {
        final var pattern = "(ab)*";
        final var patient = "bbbbb";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }

    @Test
    public void test13() throws Exception
    {
        final var pattern = "(ab)*";
        final var patient = "aaaaa";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }

    @Test
    public void test12() throws Exception
    {
        final var pattern = "(ab)*";
        final var patient = "abababababa";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }

    @Test
    public void test11() throws Exception
    {
        final var pattern = "(ab)*";
        final var patient = "abababababa";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }

    @Test
    public void test07() throws Exception
    {
        final var pattern = "(ab)*";
        final var patient = "";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test10() throws Exception
    {
        final var pattern = "(ab)+";
        final var patient = "ababa";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }

    @Test
    public void test09() throws Exception
    {
        final var pattern = "(ab)+";
        final var patient = "";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertFalse(compiled.matches(patient));
    }

    @Test
    public void test06() throws Exception
    {
        final var pattern = "(ab)+";
        final var patient = "abababababababab";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(patient));
    }

    @Test
    public void test01() throws Exception
    {
        final var pattern = "somestring";
        final var compiled = ya.moguchi.internal.Compiler.compile(pattern);
        assertTrue(compiled.matches(pattern));
    }

    @Test
    public void test02()
    {
        final var pattern = "";
        try
        {
            ya.moguchi.internal.Compiler.compile(pattern);
            fail();
        }
        catch (MalformedPattern mp)
        {
            assertTrue(mp instanceof EmptyPattern);
        }
    }

    @Test
    public void test03()
    {
        final var pattern = "asdf(asdf";
        try
        {
            ya.moguchi.internal.Compiler.compile(pattern);
            fail();
        }
        catch (MalformedPattern mp)
        {
            assertTrue(mp instanceof UnmatchedBracket);
        }
    }

    @Test
    public void test04()
    {
        final var pattern = "asdf()asdf";
        try
        {
            ya.moguchi.internal.Compiler.compile(pattern);
            fail();
        }
        catch (MalformedPattern mp)
        {
            assertEquals(mp.getMessage(), "Ожидался мультипликатор после группы");
        }
    }

    @Test
    public void test05()
    {
        final var pattern = "asdf)asdf";
        try
        {
            ya.moguchi.internal.Compiler.compile(pattern);
            fail();
        }
        catch (MalformedPattern mp)
        {
            assertTrue(mp instanceof UnmatchedBracket);
        }
    }

}
