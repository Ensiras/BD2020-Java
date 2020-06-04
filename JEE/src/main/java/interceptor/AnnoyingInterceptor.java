package interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Annoying
@Interceptor
public class AnnoyingInterceptor {

    @AroundInvoke
    public Object annoy(InvocationContext ic) {
        System.out.println("A message from the AnnoyingInterceptor: I have successfully intercepted this call, hahaha!");
        try {
            return ic.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
