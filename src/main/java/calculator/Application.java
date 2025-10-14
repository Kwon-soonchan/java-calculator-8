package calculator;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        System.out.println("계산할 문자열을 입력하세요.");
        String input = Console.readLine();

        // 구분자와 숫자 리스트 작성
        List<Character> delimiter_list = new ArrayList<Character>();
        List<Integer> number_list = new ArrayList<Integer>();

        // 기본 구분자 추가
        delimiter_list.add(',');
        delimiter_list.add(':');

        // 숫자가 여러자리수로 들어올 수 있어서 String으로 작성
        String num = "";

        try {
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                // 입력 문자열이 "/"로 시작한다면 //?\n에서 ? 의 위치인 2번 인덱스 값을 구분자로 추가
                if (i == 0) {
                    if (c == '/') {
                        delimiter_list.add(input.charAt(2));
                        i += 4;
                        continue;
                    }
                }

                // 현재 문자열이 숫자라면 num으로 이어붙이고, 아니라면 그동안 이어붙혔던 num을 number_list에 추가함과 동시에 초기화
                if (Character.isDigit(c)) {
                    num += c;
                } else {
                    if (delimiter_list.contains(c)) {
                        number_list.add(Integer.parseInt(num));
                        num = "";
                    } else {
                        throw new IllegalArgumentException("delimiter_list에 존재하지 않는 구분자입니다.");
                    }
                }
            }

            if (num != "") {
                number_list.add(Integer.parseInt(num));
            }

            int sum = 0;
            for (int i : number_list) {
                sum += i;
            }

            System.out.println("결과 : " + sum);

        } catch (IllegalArgumentException e) {
            System.err.println("오류발생 : " + e.getMessage());
        }

    }
}
