package datatypes;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Locale;
import java.util.Objects;

public class Numeric extends Data {
    private double val;

    public Numeric(double val){
        this.val = val;
    }

    public double getVal() {
        return val;
    }

    @Override
    public Data sum(Numeric numeric) {
        return new Numeric(val + numeric.val);
    }

    @Override
    public Data sum(Vector vector) {
        return vector.sum(this);
    }

    @Override
    public Data sum(Matrix matrix) throws IllegalArgumentException {
        return matrix.sum(this);
    }

    @Override
    public Data mult(Numeric numeric) {
        return new Numeric(val * numeric.val);
    }

    @Override
    public Data mult(Vector vector) {
        return vector.mult(this);
    }

    @Override
    public Data mult(Matrix matrix) {
        return matrix.mult(this);
    }

    @Override
    public Numeric det() {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%.2f", val);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Numeric numeric = (Numeric) o;
        return Double.compare(numeric.val, val) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(val);
    }
}
