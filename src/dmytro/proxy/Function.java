package dmytro.proxy;

public class Function implements Evaluatable {
    private double a;

    public Function(double a) {
        this.a = a;
    }

    @Override
    public double evalf(double x) {
        return Math.sin(x) / a;
    }
}
