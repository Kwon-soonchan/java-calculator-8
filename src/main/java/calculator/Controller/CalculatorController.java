package calculator.Controller;

import calculator.Model_Service.CalculatorService;
import calculator.View.InputView;
import calculator.View.OutputView;

public class CalculatorController {
    public void run() {
//        try {
        String input = InputView.readInput();

        CalculatorService calculator = new CalculatorService();
        Long result = calculator.calculate(input);

        OutputView.printResult(result);

//        } catch (IllegalArgumentException e) {
//        OutputView.printError(e.getMessage());
//        }
    }
}
