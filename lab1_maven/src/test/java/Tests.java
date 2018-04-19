import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class Tests {

    @BeforeAll
    static void initAll() {
    }

    @BeforeEach
    void init() {
    }

    @Test
    void vectorConstructorIllegalArgumentsTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            double[] v = {};
            new Vector(v);
        });
        assertEquals("vector must have positive length", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Vector(null);
        });
        assertEquals("vector must not be null", exception.getMessage());
    }

    @Test
    void vectorConstructorSuccessTest() {
        double[] va1 = {3, 1, 4};

        Vector vector1 = new Vector(va1);

        assertEquals(3, vector1.getLength());
        assertEquals(3, vector1.getElement(0));
        assertEquals(1, vector1.getElement(1));
    }

    @Test
    void vectorMultSuccessTest() {

        double[] va1 = {3, 1, 4};
        double[] va2 = {1, 5, 4};

        Vector vector1 = new Vector(va1);
        Vector vector2 = new Vector(va2);

        assertEquals(24, vector1.mult(vector2));
    }

    @Test
    void vectorMultDifferentSizesTest() {

        double[] va1 = {3, 1, 4};
        double[] va2 = {1, 4};

        Vector vector1 = new Vector(va1);
        Vector vector2 = new Vector(va2);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            vector1.mult(vector2);
        });
        assertEquals("vectors must have the same length", exception.getMessage());
    }

    @Test
    void vectorEqualsTest() {
        double[] va1 = {3, 1, 4};
        double[] va2 = {3, 1, 4};
        double[] va3 = {2, 0, 1};
        double[][] m1 = {
                {1, 3, 2},
                {3, 4, 2},
                {3, 1, 2},
        };

        Vector vector1 = new Vector(va1);
        Vector vector2 = new Vector(va2);
        Vector vector3 = new Vector(va3);

        assertEquals(vector1, vector2);
        assertNotEquals(vector1, m1);
        assertNotEquals(null, vector1);
        assertNotEquals(vector1, vector3);
    }

    @Test
    void vectorToStringTest() {
        double[] va = {3, 1, 4};

        Vector vector = new Vector(va);

        assertEquals("Vector{vector=[3.0, 1.0, 4.0], length=3}", vector.toString());
    }

    @Test
    void matrixConstructorIllegalArgumentsTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            double[][] v = {{}};
            new Matrix(v);
        });
        assertEquals("matrix must have positive dimensions", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Matrix(null);
        });
        assertEquals("matrix must not be null", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            double[][] v = {{1, 2, 3, 4}, {1, 2, 3}};
            new Matrix(v);
        });
        assertEquals("matrix must contain rows with the same number of cols", exception.getMessage());
    }

    @Test
    void matrixConstructorSuccessTest() {
        double[][] m = {
                {1, 3, 2, 2},
                {3, 4, 2, 2},
                {3, 1, 2, 3},
        };

        Matrix matrix = new Matrix(m);

        assertEquals(3, matrix.getnRows());
        assertEquals(4, matrix.getnCols());
        assertEquals(4, matrix.getElement(1, 1));
        assertEquals(2, matrix.getElement(1, 2));
    }

    @Test
    void matrixEqualsTest() {
        double[][] m1 = {
                {1, 3, 2},
                {3, 4, 2},
                {3, 1, 2},
        };

        double[][] m2 = {
                {1, 3, 2},
                {3, 4, 2},
                {3, 1, 2},
        };

        double[][] m3 = {
                {4, 3, 2},
                {3, 0, 2},
                {4, 1, 2},
        };

        Matrix matrix1 = new Matrix(m1);
        Matrix matrix2 = new Matrix(m2);
        Matrix matrix3 = new Matrix(m3);

        assertEquals(matrix1, matrix2);
        assertNotEquals(matrix1, matrix3);
    }

    @Test
    void matrixSumDifferentSizesTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {

            double[][] m1 = {
                    {1, 3, 2},
                    {3, 4, 2},
                    {3, 1, 2},
            };

            double[][] m2 = {
                    {4, 1, 1, 3},
                    {2, 1, 4, 5},
                    {4, 2, 3, 1},
            };

            Matrix matrix1 = new Matrix(m1);
            Matrix matrix2 = new Matrix(m2);

            matrix1.add(matrix2);
        });
        assertEquals("matrices must have the same size", exception.getMessage());
    }

    @Test
    void matrixSumSuccessTest() {
        double[] vector1 = {3, 1, 4};

        double[][] m = {
                {1, 3, 2},
                {3, 4, 2},
                {3, 1, 2},
        };

        double[][] m1 = {
                {4, 1, 1},
                {2, 1, 4},
                {4, 2, 3},
        };

        double[][] sum = {
                {5, 4, 3},
                {5, 5, 6},
                {7, 3, 5},
        };

        Matrix matrix = new Matrix(m);
        Matrix matrix1 = new Matrix(m1);

        assertNotEquals(vector1, m1);
        assertNotEquals(null, vector1);
        assertEquals(new Matrix(sum), matrix.add(matrix1));
    }

    @Test
    void matrixDetNonSquareTest() {

        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            double[][] m = {
                    {4, 1, 1, 3},
                    {2, 1, 4, 5},
                    {4, 2, 3, 1},
            };

            Matrix matrix = new Matrix(m);

            matrix.det();
        });
        assertEquals("matrix must be square to find det", exception.getMessage());
    }

    @Test
    void matrixDetSuccessTest() {

        double[][] m = {
                {5, 2},
                {4, 4}
        };

        double[][] m1 = {
                {5, 2, 3},
                {3, 4, 4},
                {1, 3, 2}
        };

        double[][] m2 = {
                {5},
        };

        Matrix matrix = new Matrix(m);
        assertEquals(12, matrix.det());


        Matrix matrix1 = new Matrix(m1);
        assertEquals(-9, matrix1.det());

        Matrix matrix2 = new Matrix(m2);
        assertEquals(5, matrix2.det());
    }

    @Test
    void matrixToStringTest() {
        double[][] m = {
                {1, 3, 2},
                {3, 4, 2},
                {3, 1, 2},
        };

        Matrix matrix = new Matrix(m);

        assertEquals("Matrix{matrix={\n" +
                " 1.0 3.0 2.0\n" +
                " 3.0 4.0 2.0\n" +
                " 3.0 1.0 2.0\n" +
                "}, nCols=3, nRows=3}", matrix.toString());
    }


    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }

}