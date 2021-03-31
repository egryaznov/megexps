package ya.moguchi.exceptions;

public class DuplicateDelta extends RuntimeException
{

    public DuplicateDelta(int from, char symbol, int to)
    {
        super(String.format("Delta function already contains triple %d %s %d", from, symbol, to));
    }

}
