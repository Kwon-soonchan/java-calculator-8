package calculator.View;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public static String readInput() {

        System.out.println("계산할 문자열을 입력하세요.");

        return Console.readLine();
    }
}
