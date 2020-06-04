package interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@ChangesMessages
@Interceptor // Also register in beans.xml (otherwise won't work, no error will be shown!)
public class ChangesMessagesInterceptor {

    @AroundInvoke
    public Object changeMessage(InvocationContext ic) {
        Object[] parameters = {"Unimportant message, carry on."};
        ic.setParameters(parameters); // Set original parameter of method to changed one
        try {
            ic.proceed();
        } catch (Exception e) {
            System.err.println("Oops!");
        }
        return null;
    }
}
