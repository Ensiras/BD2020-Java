package interceptor;

import javax.ejb.Stateless;

@Annoying
@Stateless
public class InterceptedClass {

    public void hopefullyThisWontBeIntercepted() {
        System.out.println("Do something");
    }

    public void hopefullyThenAtLeastThisOneIsnt() {
        System.out.println("Secret message.");
    }
}
