package ya.moguchi.internal;

import ya.moguchi.exceptions.EmptyPattern;
import ya.moguchi.exceptions.MalformedPattern;
import ya.moguchi.exceptions.UnmatchedBracket;
import ya.moguchi.internal.multiplier.Linker;
import ya.moguchi.internal.multiplier.Multiplier;
import ya.moguchi.internal.multiplier.Plus;
import ya.moguchi.internal.multiplier.Question;
import ya.moguchi.internal.multiplier.Star;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class Compiler
{

    private static final Map<Character, Multiplier> charToMultiplier = new HashMap<>();

    static
    {
        charToMultiplier.put('*', new Star());
        charToMultiplier.put('+', new Plus());
        charToMultiplier.put('?', new Question());
    }

    private static FSA compileSimple(final StringBuilder simple)
    {
        var delta = new Delta();
        final int initial = Sequence.getInstance().nextId();
        int state = initial;
        final var states = new HashSet<Integer>();
        states.add(state);
        for (int i = 0; i < simple.length(); i++)
        {
            final char symbol = simple.charAt(i);
            final int next = Sequence.getInstance().nextId();
            delta.put(state, symbol, next);
            state = next;
            states.add(state);
        }
        final var singleton = new HashSet<Integer>();
        singleton.add(state);
        return new FSA(states, initial, singleton, delta);
    }

    public static Pattern compile(final String originalPattern) throws MalformedPattern
    {
        if (originalPattern.isEmpty())
            throw new EmptyPattern();
        //
        int depth = 0;
        var simplePattern = new StringBuilder();
        final var bracketedPattern = new StringBuilder(originalPattern);
        bracketedPattern.append(')');
        bracketedPattern.insert(0, '(');
        final var pattern = enrich(bracketedPattern.toString());
        final Stack internal = new Stack();
        internal.push(new Linker());
        for (int i = pattern.length() - 1; i >= 0; i--)
        {
            final char symbol = pattern.charAt(i);
            final boolean isMultiplier = charToMultiplier.containsKey(symbol);
            final boolean isOpeningBracket = symbol == '(';
            final boolean isClosingBracket = symbol == ')';
            if (isMultiplier)
            {
                if (populated(simplePattern))
                {
                    internal.push(compileSimple(simplePattern));
                    simplePattern = new StringBuilder();
                }
                internal.push(charToMultiplier.get(symbol));
            }
            else if (isClosingBracket)
                depth += 1;
            else if (isOpeningBracket)
            {
                if (populated(simplePattern))
                {
                    internal.push(compileSimple(simplePattern));
                    simplePattern = new StringBuilder();
                }
                depth -= 1;
                if (depth < 0)
                    throw new UnmatchedBracket();
                internal.push(performOneOperation(internal));
            }
            else
                simplePattern.insert(0, symbol);
        }
        if (depth > 0)
            throw new UnmatchedBracket();
        final FSA compiled = (FSA) internal.pop();
        return Pattern.of(compiled, originalPattern);
    }

    /**
     * Обращает в скобки единичные буквы с мультипликаторами.
     * (ab*)+ => (a(b)*)+
     *
     * @param pattern шаблон, в котором надо расставить скобки
     */
    public static String enrich(final String pattern)
    {
        final StringBuilder result = new StringBuilder();
        boolean prevMultiplier = false;
        for (int i = pattern.length() - 1; i >= 0; --i)
        {
            final char symbol = pattern.charAt(i);
            final boolean curSimpleAtom = (symbol != '(') && (symbol != ')') && !charToMultiplier.containsKey(symbol);
            if (prevMultiplier && curSimpleAtom)
            {
                result.append(')');
                result.append(symbol);
                result.append('(');
            }
            else
                result.append(symbol);
            prevMultiplier = charToMultiplier.containsKey(symbol);
        }
        return result.reverse().toString();
    }

    private static FSA performOneOperation(Stack internal) throws MalformedPattern
    {
        final var automata = new LinkedList<FSA>();
        Object arg;
        while ((arg = internal.pop()) instanceof FSA)
        {
            automata.addFirst((FSA) arg);
            if (internal.isEmpty())
                throw new MalformedPattern("Ожидался мультипликатор после группы");
        }
        return ((Multiplier) arg).apply(automata);
    }

    private static boolean populated(final StringBuilder pattern)
    {
        return pattern.length() > 0;
    }

}
