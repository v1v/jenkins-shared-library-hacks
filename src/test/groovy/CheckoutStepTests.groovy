import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

class CheckoutStepTests extends base.PipelineTestHelper {
  def script

  @Override
  @Before
  void setUp() throws Exception {
    super.setUp()
    script = loadScript('vars/checkout.groovy')
  }

  @Test
  void test_checkout() throws Exception {
    try {
      script.call()
    } catch(e){
      //NOOP
    }
    printCallStack()
    assertTrue(assertMethodCallContainsPattern('echo', 'Override built-in checkout step'))
  }
}
