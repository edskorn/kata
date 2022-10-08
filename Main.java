import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // убрать бесконечный цикл при отправке на проверку
        while (true) {
            System.out.print("Enter your expression: ");
            String s = br.readLine();
            if (s.equals("exit")) break;
            try {
                String sss = calc(s);
                System.out.println("Hello calc! " + sss);
            } catch (IOException e) {
                System.out.println("Что-то пошло не так. Сорян, бро.");
                System.out.println(e.getMessage());
            }
        }
    }
    public static String calc(String input) throws IOException
    {
        // заменить на константы
        String errFormat = "Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *), разделенные одинарными пробелами";
        String errRange = "Калькулятор принимает на вход числа от 1 до 10 включительно и от I до X";
        String errRomanNegative = "В римской системе нет отрицательных чисел и нуля";
        String errDifferentNumberSystem = "Используются одновременно разные системы счисления";
        RomeDigit aRome;
        RomeDigit bRome;
        int aArab;
        int bArab;

        String[] split = input.split(" ");
        if (split.length!=3) throw new IOException(errFormat);

        try {
            // Парсим первый операнд - если римский, то продолжаем, если нет идём в catch проверить арабский ли.
            aRome = RomeDigit.valueOf(split[0]);

            //Парсим второй операнд
            try {
                bRome = RomeDigit.valueOf(split[2]);
            } catch (IllegalArgumentException e) {
                try {
                    Integer.parseInt(split[2]);
                    throw new IOException(errDifferentNumberSystem);
                }
                catch (NumberFormatException ee) {
                    throw new IOException(errRange);
                }
            }

            //Проводим вычисление, если оператор допустимый
            int result;
            switch (split[1]) {
                case "+" -> result = aRome.getArabicNumber() + bRome.getArabicNumber();
                case "-" -> {
                    if (aRome.getArabicNumber() <= bRome.getArabicNumber()) {
                        throw new IOException(errRomanNegative);
                    }
                    result = aRome.getArabicNumber() - bRome.getArabicNumber();
                }
                case "*" -> result = aRome.getArabicNumber() * bRome.getArabicNumber();
                case "/" -> {
                    result = aRome.getArabicNumber() / bRome.getArabicNumber();
                    if (result<1) {
                        throw new IOException(errRomanNegative);
                    }
                }
                default ->
                        throw new IOException(errFormat);
            }

            //Выводим результат римскими цифрами
            return "Input: " + aRome + split[1] + bRome + " Result: " + RomanNumber.toRoman(result);

        } catch (IllegalArgumentException e) {
            try {
                aArab = Integer.parseInt(split[0]);
                if (aArab<1 || aArab>10) throw new IOException(errRange);

                //Парсим второй операнд
                try {
                    bArab = Integer.parseInt(split[2]);
                    if (bArab<1 || bArab>10) throw new IOException(errRange);
                }
                catch (NumberFormatException ee) {
                    try {
                        RomeDigit.valueOf(split[2]);
                        throw new IOException(errDifferentNumberSystem);
                    } catch (IllegalArgumentException eee) {
                        throw new IOException(errRange);
                    }
                }

                int result;
                switch (split[1]) {
                    case "+" -> result = aArab + bArab;
                    case "-" -> result = aArab - bArab;
                    case "*" -> result = aArab * bArab;
                    case "/" -> result = aArab / bArab;
                    default -> throw new IOException(errFormat);
                }

                //Выводим результат арабскими цифрами
                return "Input: " + aArab + split[1] + bArab + " Result: " + result;
            }
            catch (NumberFormatException ee) {
                throw new IOException(errFormat);
            }
        }
    }
}
