package ya.moguchi;

import org.junit.Test;
import ya.moguchi.internal.Sequence;

import java.util.LinkedList;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SequenceTest
{

    @Test
    public void uniqueIdsTest()
    {
        final int N = 10;
        final var ids = new LinkedList<Integer>();
        for (int i = 0; i < N; i++)
            ids.add(Sequence.getInstance().nextId());
        assertEquals(Set.copyOf(ids).size(), ids.size());
    }

}
