package ya.moguchi.internal.multiplier;

import ya.moguchi.internal.FSA;

import java.util.List;

public class Star implements Multiplier
{
    @Override
    public FSA apply(final List<FSA> automata)
    {
        final var plus = new Plus();
        final var question = new Question();
        return question.apply(List.of(plus.apply(automata)));
    }
}
