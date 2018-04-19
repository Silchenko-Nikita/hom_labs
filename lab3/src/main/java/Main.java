import evaluator.Evaluator;

public class Main {

    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        System.out.println(evaluator.evaluate("A = 100"));
        System.out.println(evaluator.evaluate("B = 32"));
        System.out.println(evaluator.evaluate("C = A + B"));
        System.out.println(evaluator.evaluate("[-1, 23, 13] + A"));
        System.out.println(evaluator.evaluate(
                "[[-3, 2.321321, -5], [14.2, 2, -1]] + [[12, 2, 1232], [-4.2, 2, -1]] + [[1, 2, -23], [-4.2, 2, -1]]"));
        System.out.println(evaluator.evaluate(
                "[[1]]"));
        System.out.println(evaluator.evaluate(
                "det([[1, 2], [32,1]])"));
    }
}
