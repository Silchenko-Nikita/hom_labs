import datatypes.Data;
import evaluator.Evaluator;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.assertEquals;

public class Steps {

    private Evaluator evaluator;
    private Data result;
    private Exception exception;

    // Given

    @Given("a new evaluator")
    public void newEvaluator() {
        evaluator = new Evaluator();
    }

    @Given("an evaluator with a created variable $varname with value $value")
    public void givenEvaluatorWithCreatedVariableWithValue(String varname, String value) {
        evaluator = new Evaluator();
        evaluator.evaluate(varname + " = " + value);
    }

    @Given("a created variable $varname with value $value")
    public void givenCreatedVariableWithValue(String varname, String value) {
        evaluator.evaluate(varname + " = " + value);
    }

    // When

    @When("I evaluate string $string")
    public void iEvaluateString(String string) {
        try{
            result = evaluator.evaluate(string);
        } catch (Exception e){
            exception = e;
        }
    }

    // Then

    @Then("I should get result $value")
    public void iShouldGetResult(String value) {
        assertEquals(value, result.toString());
    }

    @Then("I should get an error message $message")
    public void iShouldGetErrorMessage(String message) {
        assertEquals(message, exception.getMessage());
    }

    @Then("should be created var $varname with value $value")
    public void varCreated(String varname, String value) {
        assertEquals(value, evaluator.getVar(varname).toString());
    }
}
