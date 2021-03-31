package ya.moguchi.internal.multiplier;

import ya.moguchi.exceptions.NoArguments;
import ya.moguchi.internal.FSA;
import ya.moguchi.internal.Link;

import java.util.List;

public class Linker implements Multiplier
{
    @Override
    public FSA apply(final List<FSA> automata)
    {
        return automata.stream()
                .reduce((first, second) -> concat(second, first))
                .orElseThrow(NoArguments::new);
    }

    private FSA concat(final FSA first, final FSA second)
    {
        final int initialSecond = second.getInitial();
        final boolean isSecondInitialTerminal = second.isTerminal(initialSecond);
        final List<Link> initialLinks = second.drop(second.getInitial());
        for (int firstTerminal : first.terminals())
        {
            for (Link secondFromInitial : initialLinks)
                if (secondFromInitial.isOutgoing(initialSecond))
                    for (int tail : secondFromInitial.getSinks())
                    {
                        first.insertLink(firstTerminal, secondFromInitial.getSymbol(), tail);
                        if (second.isTerminal(tail))
                            first.makeTerminal(tail);
                    }
                else
                    first.insertLink(secondFromInitial.getSource(), secondFromInitial.getSymbol(), firstTerminal);
            //
            if (!isSecondInitialTerminal)
                first.makeProcessing(firstTerminal);
        }
        first.add(second);
        return first;
    }

}
