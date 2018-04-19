import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Vector {
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

    public double mult(Vector vector2) throws IllegalArgumentException {
        if (this.length != vector2.length) {
            throw new IllegalArgumentException("vectors must have the same length");
        }

        double sum = 0;
        for (int i = 0; i < length; i++) {
            sum += vector[i] * vector2.vector[i];
        }
        return sum;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "vector=" + Arrays.toString(vector) +
                ", length=" + length +
                '}';
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
