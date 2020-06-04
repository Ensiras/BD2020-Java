package interceptor;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class InterceptedClassIT {

    @Inject
    InterceptedClass interceptedClass;

    @Inject
    InterceptedMethodOnly interceptedMethodOnly;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(Annoying.class.getPackage())
                .addAsManifestResource(new StringAsset("<interceptors>" +
                        "        <class>interceptor.AnnoyingInterceptor</class>" +
                        "<class>interceptor.ChangesMessagesInterceptor</class>" +
                        "    </interceptors>"), "beans.xml");
    }

    @Test
    public void annoyingInterceptorTest() {
        interceptedClass.hopefullyThisWontBeIntercepted();
        interceptedClass.hopefullyThenAtLeastThisOneIsnt();
    }

    @Test
    public void changesMessagesInterceptorTest() {
        interceptedMethodOnly.thisMessageWillRemainASecret("No one will intercept this!");
        interceptedMethodOnly.thisMessageWillBeInterceptedAndAltered("Very important message, must be delivered");
    }
}
