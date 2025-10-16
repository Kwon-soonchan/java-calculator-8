package calculator.View;

public class OutputView {
    public static void printResult(int sum) {
        System.out.println("결과 : " + sum);
    }

    public static void printError(String message) {
        System.err.println("오류발생 : " + message);
    }
}
