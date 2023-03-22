package dmytro.proxy;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        Function function = new Function(2.0);
        ClassLoader functionLoader = function.getClass().getClassLoader();
        Class<?>[] interfaces = function.getClass().getInterfaces();
        Evaluatable proxyFunc = (Evaluatable) Proxy.newProxyInstance(functionLoader, interfaces, new FunctionInvocationHandler(function));

        proxyFunc.evalf(5.0);
    }
}
