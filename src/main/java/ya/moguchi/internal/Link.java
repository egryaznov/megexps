package ya.moguchi.internal;

import java.util.List;
import java.util.stream.Collectors;

public class Link
{

    public static Link of(int source, char symbol, List<Integer> sinks)
    {
        return new Link(source, symbol, sinks);
    }

    private final int source;
    private final char symbol;
    private final List<Integer> sinks;

    Link(int source, char symbol, List<Integer> sinks)
    {
        this.source = source;
        this.symbol = symbol;
        this.sinks = sinks;
    }

    public int getSource()
    {
        return source;
    }

    public char getSymbol()
    {
        return symbol;
    }

    public List<Integer> getSinks()
    {
        return sinks;
    }

    public boolean isOutgoing(final int state)
    {
        return source == state;
    }

    @Override
    public String toString()
    {
        return this.sinks.stream()
                  .map(sink -> String.format("%d --%s--> %d", this.source, this.symbol, sink))
                  .collect(Collectors.joining("\n"));
    }
}
