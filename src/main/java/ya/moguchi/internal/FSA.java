package ya.moguchi.internal;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class FSA
{

    private static final char JOKER = '.';

    private final Set<Integer> states; // Q
    private final int initial; // q0
    private final Set<Integer> terminals; // A
    private final Delta delta;

    FSA(Set<Integer> states, int initial, Set<Integer> terminals, Delta delta)
    {
        this.states = states;
        this.initial = initial;
        this.terminals = terminals;
        this.delta = delta;
    }

    public boolean isTerminal(final int state)
    {
        return terminals.contains(state);
    }

    List<Integer> advance(final int source, final char symbol)
    {
        final List<Integer> result = new LinkedList<>();
        result.addAll(delta.apply(source, symbol));
        result.addAll(delta.apply(source, JOKER));
        return result;
    }

    public int getInitial()
    {
        return initial;
    }

    public List<Link> drop(final int state)
    {
        states.remove(state);
        terminals.remove(state);
        return delta.remove(state);
    }

    public List<Integer> terminals()
    {
        return new LinkedList<>(terminals);
    }

    public void makeProcessing(final int state)
    {
        terminals.remove(state);
    }

    public void makeTerminal(final int state)
    {
        terminals.add(state);
    }

    @Override
    public String toString()
    {
        return "Initial " + this.initial + "\n" +
                "Terminals " + terminals + "\n" +
                delta;
    }

    public void insertLink(int head, char symbol, int tail)
    {
        states.add(head);
        states.add(tail);
        delta.put(head, symbol, tail);
    }

    public List<Link> outgoing(final int head)
    {
        return delta.outgoing(head);
    }

    public boolean has(int head, char symbol, int tail)
    {
        return delta.has(head, symbol, tail);
    }

    public void put(int head, char symbol, int tail)
    {
        delta.put(head, symbol, tail);
    }

    public void add(FSA second)
    {
        states.addAll(second.states);
        terminals.addAll(second.terminals);
        delta.add(second.delta);
    }

    public Stream<String> solutions()
    {
        return Stream.generate(new BFSTraverser(this));
    }

    /**
     * WARNING: Not finished!
     * @param other
     * @return
     */
    public FSA intersection(final FSA other)
    {
        final Set<Integer> newStates = new HashSet<>();
        final Set<Integer> newTerminals = new HashSet<>();
        final Delta newDelta = new Delta();
        final LinkedList<Integer> aProcessing = new LinkedList<>();
        final LinkedList<Integer> bProcessing = new LinkedList<>();
        aProcessing.offer(this.getInitial());
        bProcessing.offer(other.getInitial());
        final Set<Integer> visited = new HashSet<>();
        int initial = -1;
        while (!aProcessing.isEmpty() && !bProcessing.isEmpty())
        {
            final int aCur = aProcessing.remove();
            final int bCur = bProcessing.remove();
            if (visited.contains(aCur) && visited.contains(bCur))
                continue;
            final int cCur = Sequence.getInstance().nextId();
            if (this.isTerminal(aCur) && other.isTerminal(bCur))
                newTerminals.add(cCur);
            final boolean initialNotSet = initial == -1;
            if (initialNotSet)
                initial = cCur;
            newStates.add(cCur);
            for (Link link : this.outgoing(aCur))
            {
                final char symbol = link.getSymbol();
                if (other.delta.has(bCur, symbol))
                {
                    final int nextState = Sequence.getInstance().nextId();
                    newDelta.put(cCur, symbol, nextState);
                    final List<Integer> sinks = other.delta.apply(bCur, symbol);
                    aProcessing.addAll(sinks);
                    bProcessing.addAll(sinks);
                    for (Integer sink : sinks)
                        newDelta.put(cCur, symbol, sink);
                }
            }
            visited.add(aCur);
            visited.add(bCur);
        }
        return new FSA(newStates, initial, newTerminals, newDelta);
    }

}
