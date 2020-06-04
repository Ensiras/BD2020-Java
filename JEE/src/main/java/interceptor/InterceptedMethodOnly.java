package interceptor;

import javax.ejb.Stateless;


@Stateless
public class InterceptedMethodOnly {

    public void thisMessageWillRemainASecret(String message) {
        System.out.println(message);
    }

    @ChangesMessages // Just this method will be affected by the interceptor
    public void thisMessageWillBeInterceptedAndAltered(String message) {
        System.out.println(message);
    }
}
