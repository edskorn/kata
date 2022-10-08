import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
    public static final String errFormat = "Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *), разделенные одинарными пробелами";
    public static final String errRange = "Калькулятор принимает на вход числа от 1 до 10 включительно и от I до X";
    public static final String errRomanNegative = "В римской системе нет отрицательных чисел и нуля";
    public static final String errDifferentNumberSystem = "Используются одновременно разные системы счисления";
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // убрать бесконечный цикл при отправке на проверку
        while (true) {
            System.out.print("Введите выражение: ");
            String inputString = br.readLine();
            if (inputString.equals("exit")) break;
            try {
                System.out.println(calc(inputString));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static String calc(String input) throws IOException
    {
        RomeDigit aRome;
        RomeDigit bRome;
        int aArab;
        int bArab;
        String[] split = input.split(" ");

        // Проверяем что на входе - 3 блока или нет (2 операнда + 1 оператор)
        if (split.length!=3) throw new IOException(errFormat);

        try {
            // Парсим первый операнд - если римский, то продолжаем, если нет идём в catch проверить является ли арабским.
            aRome = RomeDigit.valueOf(split[0]);

            //Парсим второй операнд в римское число
            try {
                bRome = RomeDigit.valueOf(split[2]);
            } catch (IllegalArgumentException e) {
                try {
                    // Проверим не является ли второй операнд числом из другой системы счисления
                    Integer.parseInt(split[2]);
                    throw new IOException(errDifferentNumberSystem);
                }
                catch (NumberFormatException ee) {
                    throw new IOException(errRange);
                }
            }

            //Проводим вычисление
            int result = calcAction(aRome.getArabicNumber(), bRome.getArabicNumber(), split[1], true);

            //Выводим результат римскими цифрами
            return "Input: " + aRome + split[1] + bRome + " Result: " + RomanNumber.toRoman(result);

        } catch (IllegalArgumentException e) {
            try {
                // Парсим первый операнд в арабское число
                aArab = Integer.parseInt(split[0]);
                if (aArab<1 || aArab>10) throw new IOException(errRange);

                //Парсим второй операнд в арабское число
                try {
                    bArab = Integer.parseInt(split[2]);
                    if (bArab<1 || bArab>10) throw new IOException(errRange);
                }
                catch (NumberFormatException ee) {
                    try {
                        // Проверим не является ли второй операнд числом из другой системы счисления
                        RomeDigit.valueOf(split[2]);
                        throw new IOException(errDifferentNumberSystem);
                    } catch (IllegalArgumentException eee) {
                        throw new IOException(errRange);
                    }
                }

                //Проводим вычисление
                int result = calcAction(aArab, bArab, split[1], false);

                //Выводим результат арабскими цифрами
                return "Input: " + aArab + split[1] + bArab + " Result: " + result;
            }
            catch (NumberFormatException ee) {
                throw new IOException(errFormat);
            }
        }
    }

    public static int calcAction(int a, int b, String operator, boolean roman) throws IOException
    {
        int result;
        switch (operator) {
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "*" -> result = a * b;
            case "/" -> result = a / b;
            default -> throw new IOException(errFormat);
        }
        if (roman && result<1) throw new IOException(errRomanNegative);

        return result;
    }
}
