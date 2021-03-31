package ya.moguchi.internal.multiplier;

import ya.moguchi.exceptions.NoArguments;
import ya.moguchi.internal.FSA;

import java.util.List;

public class Question implements Multiplier
{
    @Override
    public FSA apply(final List<FSA> automata)
    {
        if (automata.isEmpty())
            throw new NoArguments();
        //
        final var linker = new Linker();
        final FSA linked = linker.apply(automata);
        final int q0 = linked.getInitial();
        linked.makeTerminal(q0);
        return linked;
    }
}
