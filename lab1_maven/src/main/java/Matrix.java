import java.util.Arrays;
import java.util.Objects;
import java.util.Random;


public class Matrix {
    private double matrix[][];
    private int nCols;
    private int nRows;

    public Matrix(double[][] matrix) throws IllegalArgumentException{
        verifyMatrix(matrix);

        this.matrix = matrix;

        nRows = matrix.length;
        nCols = matrix[0].length;
    }

    public Matrix(int nRows, int nCols) throws IllegalArgumentException {
        if (nCols <= 0 || nRows <= 0){
            throw new IllegalArgumentException("matrix must have positive dimensions");
        }

        matrix = new double[nRows][nCols];

        Random r = new Random();
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                matrix[i][j] = r.nextDouble();
            }
        }
    }
    
    private static void verifyMatrix(double[][] matrix) throws IllegalArgumentException{
        if (matrix == null){
            throw new IllegalArgumentException("matrix must not be null");
        }

        int nRows = matrix.length;
        int nCols = matrix[0].length;

        if (nCols == 0 || nRows == 0){
            throw new IllegalArgumentException("matrix must have positive dimensions");
        }

        for (double[] aMatrix : matrix) {
            if (aMatrix.length != nCols) {
                throw new IllegalArgumentException("matrix must contain rows with the same number of cols");
            }
        }
    }

    private static void verifyPos(double[][] matrix, int row, int col) throws IllegalArgumentException {

        int nRows = matrix.length;
        int nCols = matrix[0].length;

        if (row < 0 || col < 0 || row >= nRows || col >= nCols){
            throw new IllegalArgumentException("element coordinates must fit the matrix dimensions");
        }
    }

    public Matrix add(Matrix matrix2) throws IllegalArgumentException {
        if (matrix2.getnCols() != this.getnCols() || matrix2.getnRows() != this.getnRows()) {
            throw new IllegalArgumentException("matrices must have the same size");
        }

        double[][] newMatrix = new double[nRows][nCols];

        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.nCols; j++) {
                newMatrix[i][j] = this.matrix[i][j] + matrix2.matrix[i][j];
            }
        }
        return new Matrix(newMatrix);
    }

    private static double[][] getMinor(double[][] matrix, int row, int col) throws IllegalArgumentException {
        verifyMatrix(matrix);
        verifyPos(matrix, row, col);

        int nRows = matrix.length;
        int nCols = matrix[0].length;

        double[][] minor = new double[nRows - 1][nCols - 1];

        int currRow = 0;
        int currCol = 0;
        for (int i = 0; i < nRows; i++) {
            if (i == row) {
                continue;
            }

            currCol = 0;
            for (int j = 0; j < nCols; j++) {
                if (col == j) {
                    continue;
                }

                minor[currRow][currCol] = matrix[i][j];

                currCol++;
            }
            currRow++;
        }

        return minor;
    }

    private static double det(double[][] matrix) throws IllegalArgumentException{
        verifyMatrix(matrix);

        int nRows = matrix.length;
        int nCols = matrix[0].length;

        if (nRows != nCols){
            throw new IllegalArgumentException("matrix must be square to find det");
        }

        if (nCols == 1){
            return matrix[0][0];
        }

        double det = 0;
        for (int i = 0; i < nRows; i++) {
            double[][] minor = getMinor(matrix,0, i);
            det += (i % 2 == 0 ? 1 : -1) * matrix[0][i] * det(minor);
        }

        return det;
    }

    public double det() throws UnsupportedOperationException{
        if (!isSquare()){
            throw new UnsupportedOperationException("matrix must be square to find det");
        }

        return det(matrix);
    }

    public boolean isSquare(){
        return matrix.length == matrix[0].length;
    }

    @Override
    public String toString() {

        StringBuilder mString = new StringBuilder("{\n");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                mString.append(" ").append(matrix[i][j]);
            }
            mString.append("\n");
        }
        mString.append("}");
        
        return "Matrix{" +
                "matrix=" + mString +
                ", nCols=" + nCols +
                ", nRows=" + nRows +
                '}';
    }

    public double getElement(int row, int col) throws IllegalArgumentException {
        verifyPos(this.matrix, row, col);

        return matrix[row][col];
    }

    public int getnCols() {
        return nCols;
    }

    public int getnRows() {
        return nRows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix1 = (Matrix) o;

        boolean eq = true;
        for (int i = 0; i < matrix.length; i++) {
            if (!Arrays.equals(matrix[i], matrix1.matrix[i])){
                eq = false;
                break;
            }
        }

        return nCols == matrix1.nCols && nRows == matrix1.nRows && eq;
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(nCols, nRows);
        result = 31 * result + Arrays.hashCode(matrix);
        return result;
    }
}
