package ya.moguchi;

import org.jetbrains.annotations.NotNull;
import ya.moguchi.internal.Pattern;

import java.util.Scanner;

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
        do {
            process(command);
            System.out.print("> ");
            command = s.nextLine();
        }
        while (!command.equalsIgnoreCase("q"));
    }

    private static void process(@NotNull final String command) throws Exception
    {
        if (command.equals("initial"))
            return;
        //
        var tokens = command.split(" ");
        switch (tokens.length)
        {
            case 4: // regex x regex string
                if (tokens[1].equalsIgnoreCase("x"))
                {
                    final var firstPattern = ya.moguchi.internal.Compiler.compile(tokens[0]);
                    final var secondPattern = ya.moguchi.internal.Compiler.compile(tokens[2]);
                    final var intersection = Pattern.intersect(firstPattern, secondPattern);
                    final var patient = tokens[3];
                    System.out.println(intersection.matches(patient));
                }
                else
                    System.out.println("Формат пересечения <megexp> X <megexp> <string>");
                break;
            case 3: // regex N .
            {
                final var compiled = ya.moguchi.internal.Compiler.compile(tokens[0]);
                final int nOfSolutionsToPrint = Integer.parseInt(tokens[1]);
                compiled.solutions()
                        .limit(nOfSolutionsToPrint)
                        .forEach(System.out::println);
                break;
            }
            default: // regex string
                final var compiled = ya.moguchi.internal.Compiler.compile(tokens[0]);
                final var match = tokens[1];
                if ("!".equals(match)) {
                    System.out.println(compiled);
                } else {
                    System.out.println(compiled.matches(tokens[1]));
                }
        }
    }

}
