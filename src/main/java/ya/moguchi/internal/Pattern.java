package ya.moguchi.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pattern
{

    public static Pattern intersect(final Pattern first, final Pattern second)
    {
        // final var fsa = first.automaton.intersection(second.automaton);
        final var bothSidesNow = new LinkedList<FSA>(first.automata);
        bothSidesNow.addAll(second.automata);
        return new Pattern(bothSidesNow, String.join(" & ", first.pattern, second.pattern));
    }

    public static Pattern of(final FSA automaton, final String pattern)
    {
        return new Pattern(List.of(automaton), pattern);
    }

    private final List<FSA> automata;
    private final String pattern;

    private Pattern(final List<FSA> automata, final String pattern)
    {
        this.automata = automata;
        this.pattern = pattern;
    }

    public boolean matches(final String word)
    {
        return automata.stream()
                .allMatch(automaton -> matchesSingle(automaton, word));
    }

    private boolean matchesSingle(final FSA automaton, final String word)
    {
        final Queue<Integer> states = new LinkedList<>();
        states.add(automaton.getInitial());
        for (int i = 0; i < word.length(); i++)
        {
            final char symbol = word.charAt(i);
            final int queueLen = states.size();
            for (int j = 0; j < queueLen; j++)
                automaton.advance(states.poll(), symbol)
                        .forEach(states::offer);
        }
        return states.stream()
                .anyMatch(automaton::isTerminal);
    }

    @Override
    public String toString()
    {
        return String.join(" & ",
                this.automata.stream()
                        .map(FSA::toString)
                        .collect(Collectors.toList())
        );
    }

    public String getPattern()
    {
        return pattern;
    }

    public Stream<String> solutions()
    {
        System.out.println("only gets solutions of first FSA!");
        return this.automata.get(0).solutions();
    }

}
