package TestPages;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseClass;
import Listeners.TestListener;
import Pages.E2E;


@Listeners(TestListener.class)
@Test(groups = {"E2E"})
public class E2ETest extends BaseClass {

    @Test(groups = {"E2E"}, description = "Complete E2E Flow: Home → PLP → PDP → MiniCart → Checkout")
    public void Complete_E2E_Flow_Test() {
        try {
            E2E flow = new E2E(driver);
            flow.runCompleteE2EFlow();
        } catch (Exception e) {
            Assert.fail("❌ E2E Test Failed: " + e.getMessage(), e);
        }
    }
}