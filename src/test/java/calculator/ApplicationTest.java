package calculator;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import calculator.Model_Service.CalculatorService;
import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {

    CalculatorService calculator;

    // 테스트 전 calculator 객체 생성
    @BeforeEach
    void setUp() {
        // 각 단위 테스트가 실행되기 전에, calculator를 새 객체로 초기화
        calculator = new CalculatorService();
    }

    // ---------------------------------------------------------------------------
    // [1] 통합 테스트 (Application 전체를 실행하여 입출력 확인)
    // ---------------------------------------------------------------------------

    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    // ---------------------------------------------------------------------------
    // [2] 단위 테스트 (CalculatorService의 로직만 직접 검증)
    // Controller에서 자체적으로 try-catch가 진행되므로,
    // assertSimpleTest가 아니라 Service 단위에서 직접 예외를 확인한다.
    // ---------------------------------------------------------------------------

    @Test
    @DisplayName("음수 입력 시 예외 발생 (단위 테스트)")
    void 예외_테스트() {
        String input = "-1,2,3";
        assertThatThrownBy(() -> calculator.calculate(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("요구사항: 음수 기호(-)를 커스텀 구분자로 사용 시 예외 발생")
    void 음수_기호를_구분자로_사용시_예외_발생() {
        String input = "//-\n1-2-3";
        assertThatThrownBy(() -> calculator.calculate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("-는 구분자로 받을 수 없습니다.");
    }

    @Test
    @DisplayName("요구사항: 숫자를 커스텀 구분자로 사용 시 예외 발생")
    void 숫자를_구분자로_사용시_예외_발생() {
        String input = "//1\n11213";
        assertThatThrownBy(() -> calculator.calculate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자는 구분자로 받을 수 없습니다.");
    }

    @Test
    @DisplayName("요구사항: 여러 글자 커스텀 구분자 테스트 (Pattern.quote 필요)")
    void 여러_글자_커스텀_구분자_테스트() {
        String input = "//[!!]\\n1!!2!!3";
        Long result = calculator.calculate(input);
        assertThat(result).isEqualTo(6);
    }

    @Test
    @DisplayName("요구사항: 정규식 특수문자 구분자 테스트 (Pattern.quote 필요)")
    void 정규식_특수문자_구분자_테스트() {
        // '*'는 정규식에서 0번 이상 반복을 의미하지만, Pattern.quote가 처리해야 함
        String input = "//*\\n1*2*3";
        Long result = calculator.calculate(input);
        assertThat(result).isEqualTo(6);
    }

    @Test
    @DisplayName("빈 문자열이나 null 입력 시 0 반환")
    void 빈_문자열_null_입력시_0_반환() {
        assertThat(calculator.calculate("")).isEqualTo(0);
        assertThat(calculator.calculate(null)).isEqualTo(0);
    }

    @Test
    @DisplayName("숫자가 아닌 값(문자, 실수) 포함 시 예외 발생")
    void 숫자가_아닌_값_포함시_예외_발생() {
        // "사용자가 잘못된 값을 입력할 경우" 요구사항에 따라
        // Integer.parseInt가 던지는 NumberFormatException을
        // Service에서 IllegalArgumentException으로 바꿔 던지는 것이 좋습니다.

        String input_char = "1,a,2";
        String input_double = "1.5,2";

        assertThatThrownBy(() -> calculator.calculate(input_char))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> calculator.calculate(input_double))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
