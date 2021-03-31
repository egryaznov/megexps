package ya.moguchi.internal;

import ya.moguchi.exceptions.DuplicateDelta;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Delta
{
    private final Map<Integer, Map<Character, List<Integer>>> function;
    private final Map<Integer, List<Link>> preimage; // f^{-1}(s) = ссылки, входящие в состояние s

    Delta()
    {
        function = new HashMap<>();
        preimage = new HashMap<>();
    }

    boolean has(int from, char symbol, int to)
    {
        if (function.containsKey(from))
        {
            final var charToSink = function.get(from);
            if (charToSink.containsKey(symbol))
                return charToSink.get(symbol).indexOf(to) >= 0;
        }
        return false;
    }

    boolean has(int from, char symbol)
    {
        return function.getOrDefault(from, new HashMap<>()).containsKey(symbol);
    }

    void put(int from, char symbol, int to)
    {
        final Link link = new Link(from, symbol, of(to));
        if (function.containsKey(from))
        {
            final var internal = function.get(from);
            if (!internal.containsKey(symbol))
            {
                internal.put(symbol, of(to));
                if (preimage.containsKey(to))
                    preimage.get(to).add(link);
                else
                    preimage.put(to, of(link));
            }
            else
            {
                final var sinks = internal.get(symbol);
                if (sinks.contains(to))
                    throw new DuplicateDelta(from, symbol, to);
                else
                    sinks.add(to);
            }
        }
        else
        {
            final var singleLink = new HashMap<Character, List<Integer>>();
            singleLink.put(symbol, of(to));
            function.put(from, singleLink);
            if (preimage.containsKey(to))
                preimage.get(to).add(link);
            else
                preimage.put(to, of(link));
        }
    }

    private <T> List<T> of(T element)
    {
        final var result = new LinkedList<T>();
        result.add(element);
        return result;
    }

    List<Integer> apply(int state, char symbol)
    {
        if (function.containsKey(state))
        {
            final var internal = function.get(state);
            if (internal.containsKey(symbol))
                return internal.get(symbol);
        }
        return Collections.emptyList();
    }

    List<Link> outgoing(final int state)
    {
        final List<Link> result = new LinkedList<>();
        if (function.containsKey(state))
        {
            final var charToSink = function.get(state);
            charToSink.forEach((sym, sink) ->
                    result.add(new Link(state, sym, sink)));
        }
        return result;
    }

    List<Link> remove(final int state)
    {
        final List<Link> outgoing = outgoing(state);
        function.remove(state);
        final List<Link> ingoing = ingoing(state);
        ingoing.forEach(link ->
                function.get(link.getSource())
                        .get(link.getSymbol())
                        .remove((Integer) state));
        outgoing.addAll(ingoing);
        return outgoing;
    }

    private List<Link> ingoing(int state)
    {
        return preimage.getOrDefault(state, Collections.emptyList());
    }

    private Stream<Link> links()
    {
        return function.entrySet()
                .stream()
                .flatMap(sourceToMap ->
                        sourceToMap.getValue().entrySet()
                                .stream()
                                .map(charToSinks ->
                                        new Link(sourceToMap.getKey(), charToSinks.getKey(),
                                                charToSinks.getValue())));
    }

    @Override
    public String toString()
    {
        return links()
                .map(Link::toString)
                .collect(Collectors.joining("\n"));
    }

    List<Link> links(int head)
    {
        final LinkedList<Link> result = new LinkedList<>(ingoing(head));
        result.addAll(outgoing(head));
        return result;
    }

    void add(Delta other)
    {
        function.putAll(other.function);
        preimage.putAll(other.preimage);
    }
}