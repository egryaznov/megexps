package ya.moguchi.internal;

public class Sequence
{

    private static final Sequence singleton = new Sequence();

    public static Sequence getInstance()
    {
        return singleton;
    }

    private int id;

    public int nextId()
    {
        return id++;
    }

}
