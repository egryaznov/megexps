package ya.moguchi.internal;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

public class BFSTraverser implements Supplier<String>
{

    private FSA fsa;
    private Queue<Solution> solutions;

    BFSTraverser(final FSA fsa)
    {
        this.fsa = fsa;
        this.solutions = new LinkedList<>();
        this.solutions.add(Solution.of(fsa.getInitial()));
    }

    @Override
    public String get()
    {
        String result = null;
        while (!solutions.isEmpty())
        {
            final var at = solutions.poll();
            int state = at.getState();
            final String raw = at.getRaw();
            for (Link link : fsa.outgoing(at.getState()))
                for (Integer sink : link.getSinks())
                    solutions.offer(Solution.of(sink, raw + link.getSymbol()));
            if (fsa.isTerminal(state))
            {
                result = raw;
                break;
            }
        }
        return result;
    }

}
