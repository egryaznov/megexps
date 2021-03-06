# MEGEXPs
Я написал движок регулярных выражений на чистой Яве.

Поддерживаются следующие возможности:
* Стандартные мультипликаторы `?`, `+`, `.`, `*`
* Группирование `(asdf)`
* Печать внутреннего конечного автомата
* Печать первых `n` строк, подходящих под регулярку
* Пересечение регулярок

# Инструкция
Собрать проект:
```
gradle build
```
Запустить в командной строке:
```
gradle run
```

# Как пользоваться
Чтобы проверить, подходит ли строка под шаблон, запустите программу и в терминале сначала наберите шаблон а затем через пробел строку:
```
> regexp? regex
```

Чтобы напечатать внутренний конечный автомат, из которого состоит регулярка, наберите:
```
> regexp !
```

Чтобы пересечь две регулярки, наберите:
```
> regexp x regexp string
```
Например, в результате пересечения следующих регулярок получится шаблон, под который подходят все строки, начинающиеся с `ab` и заканчивающиеся на `ba`:
```
> ab.* x .*ba abASDFba
true
```

Чтобы напечатать первые N решений регулярки, наберите:
```
> regexp N .
```