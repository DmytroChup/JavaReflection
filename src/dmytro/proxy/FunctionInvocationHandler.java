package dmytro.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class FunctionInvocationHandler implements InvocationHandler {
    private Function function;

    public FunctionInvocationHandler(Function function) {
        this.function = function;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Method: " + method.getName());
        System.out.print("Params: ");
        for(Object o: args) {
            System.out.print(o + " ");
        }
        Object res = 0;
        double start = System.nanoTime();
        res = method.invoke(function, args);
        double end = System.nanoTime();
        System.out.println("\nTime: " + (end - start) + "ns");
        System.out.println("Result: " + res);

        return res;
    }
}
