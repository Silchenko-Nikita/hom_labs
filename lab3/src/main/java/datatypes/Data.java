package datatypes;

public abstract class Data {
    public abstract Data sum(Matrix matrix);
    public abstract Data sum(Vector vector);
    public abstract Data sum(Numeric numeric);

    public abstract Data mult(Matrix matrix);
    public abstract Data mult(Vector vector);
    public abstract Data mult(Numeric numeric);

    public Data sum(Data data){
        if(data instanceof Matrix){
            return sum((Matrix) data);
        } else if(data instanceof Vector){
            return sum((Vector) data);
        } else if(data instanceof Numeric){
            return sum((Numeric) data);
        }
        return null;
    }

    public Data mult(Data data){
        if(data instanceof Matrix){
            return mult((Matrix) data);
        } else if(data instanceof Vector){
            return mult((Vector) data);
        } else if(data instanceof Numeric){
            return mult((Numeric) data);
        }
        return null;
    }

    public abstract Numeric det();
}
