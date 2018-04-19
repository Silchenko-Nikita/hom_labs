package evaluator;

import com.sun.istack.internal.NotNull;
import datatypes.Data;

import java.util.ArrayList;

public class Composite extends Component {
    private ArrayList<Component> children = new ArrayList<Component>();
    Operation operation;

    public Composite(@NotNull String string, @NotNull Evaluator evaluator, @NotNull Operation operation) {
        super(string, evaluator);
        this.operation = operation;
    }

    @Override
    public void add(@NotNull Component c) {
        children.add(c);
    }

    @Override
    public int countLeafs() {
        int num = 0;
        for (Component component: children) {
            num += component.countLeafs();
        }
        return num;
    }

    @Override
    public void remove(@NotNull Component c) {
        children.remove(c);
    }

    @Override
    public Data evaluate() {
        Data data;

        switch (operation){
            case MULT:
                data = children.get(0).evaluate();
                for (int i = 1; i < children.size(); i++) {
                    data = data.mult(children.get(i).evaluate());
                }

                break;
            case ADD:
                data = children.get(0).evaluate();
                for (int i = 1; i < children.size(); i++) {
                    data = data.sum(children.get(i).evaluate());
                }

                break;
            case DET:
                data = children.get(0).evaluate().det();

                break;
            case ASSIGN:
                data = children.get(children.size() - 1).evaluate();

                for (int i = 0; i < children.size() - 1; i++) {
                    evaluator.vars.put(children.get(i).string, data);
                }

                break;
            default:
                return null;
        }
        return data;
    }

    /*@Override
    protected void display(int depth) {
        StringBuilder offsetBuilder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            offsetBuilder.append("  ");
        }

        System.out.println(offsetBuilder + "type: " + type + (name != null ? (", name: " + name): ""));
        for (Component component: children) {
            component.display(depth + 1);
        }
    }*/
}
