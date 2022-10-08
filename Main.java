import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your expression: ");
        String s = br.readLine();
//        System.out.print("Enter Integer: ");
//        try {
//            int i = Integer.parseInt(br.readLine());
//        } catch(NumberFormatException nfe) {
//            System.err.println("Invalid Format!");
//        }
        String sss = calc(s);
        System.out.println("Hello calc! " + sss);
    }
    public static String calc(String input)
    {
         // boolean rome = false;
        String first = input.substring(0, input.indexOf(" "));
        RomeDigit aRome = RomeDigit.valueOf(first);
//        byte a = Byte.parseByte(first);
        String operator = input.substring(first.length()+1, input.indexOf(" ", first.length()+1));
        String second = input.substring(first.length()+operator.length()+2);
        RomeDigit bRome = RomeDigit.valueOf(second);
//        byte b = Byte.parseByte(second);
        int result = 1;
        switch (operator) {
            case "+":
                result = aRome.getArabicNumber() + bRome.getArabicNumber();
                break;
            case "-":
                if (aRome.getArabicNumber() <= bRome.getArabicNumber()) {
                    System.out.println("throws Exception //т.к. в римской системе нет отрицательных чисел и нуля");
                } else {
                    result = aRome.getArabicNumber() - bRome.getArabicNumber();
                }
                break;
            case "*":
                result = aRome.getArabicNumber() * bRome.getArabicNumber();
                break;
            case "/":
                result = aRome.getArabicNumber() / bRome.getArabicNumber();
                break;
            default:
                System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
//      return "Input: " + a + operator + b + " Result: " + result;
        return "Input: " + aRome + operator + bRome + " Result: " + RomanNumber.toRoman(result);
    }
}
