package ya.moguchi.internal.multiplier;

import ya.moguchi.internal.FSA;

import java.util.List;

public interface Multiplier
{

    FSA apply(List<FSA> automata);

}
