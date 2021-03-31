package ya.moguchi.exceptions;

public class EmptyPattern extends MalformedPattern
{

    public EmptyPattern()
    {
        super("Передан пустой шаблон на компиляцию");
    }

}
