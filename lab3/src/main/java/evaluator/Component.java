package evaluator;

import com.sun.istack.internal.NotNull;
import datatypes.Data;

public abstract class Component {
    protected String string;
    protected Evaluator evaluator;

    public Component(@NotNull String string, @NotNull Evaluator evaluator){
        this.string = string;
        this.evaluator = evaluator;
    }

    public abstract void add(@NotNull Component c);

    public abstract int countLeafs();
    public abstract void remove(Component c);

    public abstract Data evaluate();
//    protected abstract void display(int depth);
}

