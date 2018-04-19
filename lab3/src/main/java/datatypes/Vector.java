package datatypes;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class Vector extends Data {
    private double vector[];
    private int length;

    public Vector(double[] vector) throws IllegalArgumentException{
        verifyVector(vector);

        this.vector = vector;
        this.length = vector.length;
    }

    public Vector(int length) throws IllegalArgumentException {
        if (length  <= 0){
            throw new IllegalArgumentException("vector must have positive length");
        }

        vector = new double[length];

        Random r = new Random();
        for (int i = 0; i < length; i++) {
            vector[i] = r.nextDouble();
        }
    }

    private static void verifyVector(double[] vector) throws IllegalArgumentException{
        if (vector == null){
            throw new IllegalArgumentException("vector must not be null");
        }

        if (vector.length == 0) {
            throw new IllegalArgumentException("vector must have positive length");
        }
    }

    private static void verifyPos(double[] vector, int i) throws IllegalArgumentException {

        if (i < 0 || i >= vector.length){
            throw new IllegalArgumentException("element index must fit the vector length");
        }
    }

    public double getElement(int i) throws IllegalArgumentException {
        verifyPos(this.vector, i);

        return vector[i];
    }

    @Override
    public Data sum(Matrix matrix) {
        throw new NotImplementedException();
    }

    @Override
    public Data sum(Vector vector2) {
        if (this.length != vector2.length) {
            throw new IllegalArgumentException("vectors must have the same length");
        }

        double[] resArray = new double[this.length];

        for (int i = 0; i < length; i++) {
            resArray[i] = vector[i] + vector2.vector[i];
        }
        return new Vector(resArray);
    }

    @Override
    public Data sum(Numeric numeric) {
        double[] resArray = new double[this.length];

        for (int i = 0; i < length; i++) {
            resArray[i] = vector[i] + numeric.getVal();
        }
        return new Vector(resArray);
    }

    @Override
    public Data mult(Matrix matrix) {
        throw new NotImplementedException();
    }

    @Override
    public Data mult(Vector vector2) {
        if (this.length != vector2.length) {
            throw new IllegalArgumentException("vectors must have the same length");
        }

        double sum = 0;
        for (int i = 0; i < length; i++) {
            sum += vector[i] * vector2.vector[i];
        }
        return new Numeric(sum);
    }

    @Override
    public Data mult(Numeric numeric) {
        double[] resArray = new double[this.length];

        for (int i = 0; i < length; i++) {
            resArray[i] = vector[i] * numeric.getVal();
        }
        return new Vector(resArray);
    }

    @Override
    public Numeric det() {
        throw new NotImplementedException();
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        StringBuilder mString = new StringBuilder("[");
        for (int i = 0; i < vector.length; i++) {
            mString.append(String.format(Locale.ROOT, "%.2f", vector[i]));

            if (i < vector.length - 1){
                mString.append(", ");
            }
        }
        mString.append("]");
        return mString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector1 = (Vector) o;
        return length == vector1.length &&
                Arrays.equals(vector, vector1.vector);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(length);
        result = 31 * result + Arrays.hashCode(vector);
        return result;
    }
}
