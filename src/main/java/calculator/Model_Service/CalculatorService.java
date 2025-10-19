package calculator.Model_Service;

import java.util.regex.Pattern;

public class CalculatorService {
    public long calculate(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String deli = ",|:";
        // patter.quote()를 사용하면 escape 처리를 자동화 할 수 있다!
//        char[] escape_word_list = {'.', '*', '+', '?', '^', '$', '|', '{', '}', '[', ']', '(', ')', '\\'};

        // 입력된 문자열이 "//" 로 시작한다면 커스텀 구분자 추가 후 input을 "//?\n"가 없는 형태로 수정
        if (input.startsWith("//")) {
            String[] parts = input.split("\n|\\\\n", 2);
            if (parts.length < 2) {
                throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다. (\\n 없음)");
            }

            String customDelimiter = parts[0].substring(2);
            input = parts[1];

            if (customDelimiter.startsWith("[") && customDelimiter.endsWith("]")) {
                customDelimiter = customDelimiter.substring(1, customDelimiter.length() - 1);
            }

            if (customDelimiter.equals("-")) {
                throw new IllegalArgumentException("-는 구분자로 받을 수 없습니다.");
            }

            try {
                Integer.parseInt(customDelimiter);
                throw new IllegalArgumentException("숫자는 구분자로 받을 수 없습니다.");
            } catch (NumberFormatException e) {

            }

            deli += "|" + Pattern.quote(customDelimiter);
        }

        String[] num_list = input.split(deli);

        long sum = 0;
        for (int i = 0; i < num_list.length; i++) {
            if (num_list[i].isEmpty()) {
                continue;
            }

            try {
                if (Long.parseLong(num_list[i]) < 0) {
                    throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
                } else {
                    sum += Long.parseLong(num_list[i]);
                }
            } catch (NumberFormatException e) {
                // 실수나 문자가 들어오면 예외처리
                throw new IllegalArgumentException("유효하지 않은 숫자 형식입니다.", e);
            }
        }

        return sum;
    }
}
