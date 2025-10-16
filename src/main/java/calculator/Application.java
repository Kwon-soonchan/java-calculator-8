package calculator;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        System.out.println("계산할 문자열을 입력하세요.");
        String input = Console.readLine();

        String deli = ",|:";
        char[] escape_word_list = {'.', '*', '+', '?', '^', '$', '|', '{', '}', '[', ']', '(', ')', '\\'};

        // 입력된 문자열이 "/" 로 시작한다면 커스텀 구분자 추가 후 input을 "//?\n"가 없는 형태로 수정
        if (input.startsWith("/")) {
            char customDelimiter = input.charAt(2);
            boolean isEscape = false;
            for (char escape_char : escape_word_list) {
                if (escape_char == customDelimiter) {
                    isEscape = true;
                    break;
                }
            }
            if (isEscape) {
                deli += "|\\" + customDelimiter;
            } else {
                deli += "|" + customDelimiter;
            }
            input = input.substring(5);

        }

//        try {
        String[] num_list = input.split(deli);

        int sum = 0;
        for (int i = 0; i < num_list.length; i++) {
            if (Integer.parseInt(num_list[i]) < 0) {
                throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
            } else {
                sum += Integer.parseInt(num_list[i]);
            }
        }

        System.out.println("결과 : " + sum);

//        } catch (IllegalArgumentException e) {
//            System.err.println("오류발생 : " + e.getMessage());
//        }
    }
}
