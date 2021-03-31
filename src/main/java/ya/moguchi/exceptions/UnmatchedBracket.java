package ya.moguchi.exceptions;

public class UnmatchedBracket extends MalformedPattern
{
    public UnmatchedBracket()
    {
        super("Неправильная расстановка скобок в шаблоне");
    }
}
