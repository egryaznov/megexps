package ya.moguchi.internal.multiplier;

import ya.moguchi.exceptions.NoArguments;
import ya.moguchi.internal.FSA;
import ya.moguchi.internal.Link;

import java.util.List;

public class Plus implements Multiplier
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
        final List<Link> outgoingFromInitial = linked.outgoing(q0);
        for (final int terminalState : linked.terminals())
        {
            if (terminalState == q0)
                continue;
            for (final Link fromQ0 : outgoingFromInitial)
            {
                final char symbol = fromQ0.getSymbol();
                final List<Integer> tails = fromQ0.getSinks();
                for (final int tail : tails)
                {
                    if (linked.has(terminalState, symbol, tail))
                        continue;
                    linked.put(terminalState, symbol, tail);
                }
            }
        }
        return linked;
    }
}
