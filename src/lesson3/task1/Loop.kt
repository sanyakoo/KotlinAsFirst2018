@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import java.lang.Math.pow
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var numscounter = 0
    var ournumber = n
    if (n == 0) return 1
    while (abs(ournumber) > 0) {
        numscounter++
        ournumber /= 10
    }
    return numscounter
}
/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var exprevnum = 1
    var prevnum = 1
    var currentnum = 0
    if ((n == 1) || (n == 2)) return 1
    else for (i in 1..n-2) {
        currentnum = exprevnum + prevnum
        prevnum = exprevnum
        exprevnum = currentnum
    }
    return currentnum
}
/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var multiply = 1
    while (((multiply % m) != 0) || ((multiply % n) != 0)) multiply++
    return multiply
}
/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var ourmindivisor = 2
    while (n % ourmindivisor != 0) ourmindivisor++
    return ourmindivisor
}
/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var ourmaxdivisor = n - 1
    while (n % ourmaxdivisor !=0) ourmaxdivisor--
    return ourmaxdivisor
}
/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean =
        ((minDivisor(m) != minDivisor(n)) && ((maxOf(m,n) % minOf(m,n)) != 0)) || (minOf(m,n) == 1)

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var oursquare = 1
    if (n == Int.MAX_VALUE) return false
    while ( sqr(oursquare+1) <= n)  {
        oursquare++
    }
    return (sqr(oursquare)  >= m )
}
/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var stepsnum = 0
    var interimnum = x
    while (interimnum != 1) {
        stepsnum++
        if ((interimnum % 2) == 0)  interimnum /= 2
        else interimnum = 3 * interimnum + 1
    }
    return stepsnum
}
/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    fun SinEl(y: Double, n: Int) : Double {
        return  pow(y, n * 1.0) /  factorial(n)
    }
    var k = 1.0
    var n = 1
    var sin1 = 0.0
    var sin2 = x % (2 * PI)
    while (abs(sin2 - sin1) >= eps) {
        sin1 = sin2
        n += 2
        sin2 = sin1 + SinEl(x, n) * pow(-1.0,k)
        k++
    }
    return sin2
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    fun CosEl(y: Double, n: Int) : Double {
        return  pow(y, n * 1.0) /  factorial(n)
    }
    var k = 1.0
    var n = 0
    var cos1 = 0.0
    var cos2 = 1.0
    var z = x % (2 * PI)
    while (abs(cos2 - cos1) >= eps) {
        cos1 = cos2
        n += 2
        cos2 = cos1 + CosEl(z , n) * pow(-1.0,k)
        k++
    }
    return cos2
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var k = 0
    var x = n
    var m = 0.0
    while (x >= 1) {
        x /= 10
        k++
    }
    x = n
    var a : Double
    for (i in k downTo 1) {
        a = (x % 10) * pow(10.0, (i - 1.0))
        m += a
        x /= 10
    }
    return m.toInt()
}
/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = revert(n) == n

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var num = n
    val lastnumber = num % 10
    while (num != 0) {
        if (lastnumber != num % 10) {
            return true
        }
        num /= 10
    }
    return false
}
/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int = numberSearch(n, ::sqr)

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int = numberSearch(n, ::fib)
