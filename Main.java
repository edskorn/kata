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
        String errFormat = "Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *), разделенные одним пробелом";
        String errRange = "Калькулятор принимает на вход числа от 1 до 10 включительно и от I до X";
        String errRomanNegative = "В римской системе нет отрицательных чисел и нуля";
        String errDifferentNumberSystem = "Используются одновременно разные системы счисления";
        RomeDigit aRome;
        RomeDigit bRome;
        int aArab;
        int bArab;

        if (!input.contains(" ") || input.indexOf(" ")==0) throw new IOException(errFormat);

        String first = input.substring(0, input.indexOf(" "));
        try {
            // Парсим первый операнд - если римский, то продолжаем, если нет идём в catch проверить арабский ли.
            aRome = RomeDigit.valueOf(first);

            // Парсим оператор
            if ((input.indexOf(" ", first.length()+1)==-1) || (input.indexOf(" ", first.length()+1)==first.length()+1)) {
                throw new IOException(errFormat);
            }
            String operator = input.substring(first.length()+1, input.indexOf(" ", first.length()+1));
            // Проверяем, не заканчивается ли выражение на операторе
            if (input.length()-first.length()-operator.length()-2==0) throw new IOException(errFormat);

            //Парсим второй операнд
            String second = input.substring(first.length()+operator.length()+2);
            try {
                bRome = RomeDigit.valueOf(second);
            } catch (IllegalArgumentException e) {
                try {
                    Integer.parseInt(second);
                    throw new IOException(errDifferentNumberSystem);
                }
                catch (NumberFormatException ee) {
                    throw new IOException(errRange);
                }
            }

            //Проводим вычисление, если оператор допустимый
            int result;
            switch (operator) {
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
            return "Input: " + aRome + operator + bRome + " Result: " + RomanNumber.toRoman(result);

        } catch (IllegalArgumentException e) {
            try {
                aArab = Integer.parseInt(first);
                if (aArab<1 || aArab>10) throw new IOException(errRange);

                // Парсим оператор
                if ((input.indexOf(" ", first.length()+1)==-1) || (input.indexOf(" ", first.length()+1)==first.length()+1)) {
                    throw new IOException(errFormat);
                }
                String operator = input.substring(first.length()+1, input.indexOf(" ", first.length()+1));
                // Проверяем, не заканчивается ли выражение на операторе
                if (input.length()-first.length()-operator.length()-2==0) throw new IOException(errFormat);

                //Парсим второй операнд
                String second = input.substring(first.length()+operator.length()+2);
                try {
                    bArab = Integer.parseInt(second);
                    if (bArab<1 || bArab>10) throw new IOException(errRange);
                }
                catch (NumberFormatException ee) {
                    try {
                        RomeDigit.valueOf(second);
                        throw new IOException(errDifferentNumberSystem);
                    } catch (IllegalArgumentException eee) {
                        throw new IOException(errRange);
                    }
                }

                int result;
                switch (operator) {
                    case "+" -> result = aArab + bArab;
                    case "-" -> result = aArab - bArab;
                    case "*" -> result = aArab * bArab;
                    case "/" -> result = aArab / bArab;
                    default -> throw new IOException(errFormat);
                }

                //Выводим результат арабскими цифрами
                return "Input: " + aArab + operator + bArab + " Result: " + result;
            }
            catch (NumberFormatException ee) {
                throw new IOException(errFormat);
            }
        }
    }
}
