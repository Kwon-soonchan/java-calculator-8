package calculator.Model_Service;

import java.util.regex.Pattern;

public class CalculatorService {
    public int calculate(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String deli = ",|:";
        // patter.quote()를 사용하면 escape 처리를 자동화 할 수 있다!
//        char[] escape_word_list = {'.', '*', '+', '?', '^', '$', '|', '{', '}', '[', ']', '(', ')', '\\'};

        // 입력된 문자열이 "//" 로 시작한다면 커스텀 구분자 추가 후 input을 "//?\n"가 없는 형태로 수정
        if (input.startsWith("//")) {
            int newLineIndex = input.indexOf("\n");
            if (newLineIndex == -1) {
                throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다. (\\n 없음)");
            }

            String customDelimiter = input.substring(2, newLineIndex);

            if (customDelimiter.equals("-")) {
                throw new IllegalArgumentException("-는 구분자로 받을 수 없습니다.");
            }

            try {
                Integer.parseInt(customDelimiter);
                throw new IllegalArgumentException("숫자는 구분자로 받을 수 없습니다.");
            } catch (NumberFormatException e) {

            }

            deli += "|" + Pattern.quote(customDelimiter);

            input = input.substring(newLineIndex + 1);

        }

        String[] num_list = input.split(deli);

        int sum = 0;
        for (int i = 0; i < num_list.length; i++) {
            if (Integer.parseInt(num_list[i]) <= 0) {
                throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
            } else {
                sum += Integer.parseInt(num_list[i]);
            }
        }

        return sum;
    }
}
