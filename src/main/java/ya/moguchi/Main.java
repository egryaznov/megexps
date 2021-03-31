package ya.moguchi;

import org.jetbrains.annotations.NotNull;
import ya.moguchi.internal.Pattern;

import java.util.Scanner;
import java.util.Stack;

/**
 * TODO:
 * 2 Написать алгоритм пересечения автоматов
 * 3 Добавить поддержку ИЛИ |
 * Бонус: Написать обратную конвертацию из автоматов в регулярки
 */
public class Main
{

    public static void main(String[] args) throws Exception
    {
        run();
    }

    private static void run() throws Exception
    {
        System.out.println("Megexps v0.1, Enter command:");
        final var s = new Scanner(System.in);
        var command = "initial";
        final Stack<String> history = new Stack<>();
        do {
            if (process(command, history))
                history.add(command);
            System.out.print("> ");
            command = s.nextLine();
        }
        while (!command.equalsIgnoreCase("q"));
    }

    private static boolean process(@NotNull final String command, final Stack<String> history) throws Exception
    {
        if (command.equals("initial"))
            return false;
        //
        boolean isNewCommand = false;
        var tokens = command.split(" ");
        switch (tokens.length)
        {
            case 4:
                if (tokens[1].equalsIgnoreCase("x"))
                {
                    final var firstPattern = ya.moguchi.internal.Compiler.compile(tokens[0]);
                    final var secondPattern = ya.moguchi.internal.Compiler.compile(tokens[2]);
                    final var intersection = Pattern.intersect(firstPattern, secondPattern);
                    final var patient = tokens[3];
                    System.out.println(intersection.matches(patient));
                }
                else
                    System.out.println("Формат пересечения <megexp> X <megexp> <patient>");
                break;
            case 1:
                if (tokens[0].equalsIgnoreCase("u"))
                {
                    if (history.isEmpty())
                        System.out.println("История пуста");
                    else
                    {
                        final String up = history.pop();
                        System.out.printf("> %s\n", up);
                        process(up, history);
                    }
                }
                else
                    System.out.println("Введите 2 аргумента");
                break;
            default:
                final var compiled = ya.moguchi.internal.Compiler.compile(tokens[0]);
                final var match = tokens[1];
                switch (match)
                {
                    case "!":
                        System.out.println(compiled);
                        break;
                    case ".":
                        compiled.solutions()
                                .limit(10)
                                .forEach(System.out::println);
                        break;
                    default:
                        System.out.println(compiled.matches(tokens[1]));
                }
                isNewCommand = true;
        }
        return isNewCommand;
    }

}