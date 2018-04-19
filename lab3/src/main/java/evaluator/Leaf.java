package evaluator;

import com.sun.istack.internal.NotNull;
import datatypes.Data;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Leaf extends Component {
    DataType dataType;

    public Leaf(@NotNull String string, @NotNull Evaluator evaluator, @NotNull DataType dataType) {
        super(string, evaluator);
        this.dataType = dataType;
    }

    @Override
    public int countLeafs() {
        return 1;
    }

    @Override
    public void add(Component c) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(Component c) {
        throw new NotImplementedException();
    }

    @Override
    public Data evaluate() {
        switch (dataType){
            case MATRIX:
                return evaluator.evaluateMatrix(string);
            case VECTOR:
                return evaluator.evaluateVector(string);
            case NUMERIC:
                return evaluator.evaluateNumeric(string);
            case VAR:
                Data val = evaluator.vars.get(string);
                if (val == null) {
                    throw new RuntimeException("no such variable");
                }
                return val;
            default:
                return null;
        }
    }

    /*@Override
    protected void display(int depth) {
        StringBuilder offsetBuilder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            offsetBuilder.append("  ");
        }

        System.out.println(offsetBuilder + "type: " + type + ", name: " + name + ", code: " + code);
    }*/
}
