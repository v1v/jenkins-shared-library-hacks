import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

class BatStepTests extends base.PipelineTestHelper {
  def script

  @Override
  @Before
  void setUp() throws Exception {
    super.setUp()
    script = loadScript('vars/bat.groovy')
  }

  @Test
  void test_without_returnStdout() throws Exception {
    script.call(script: 'echo hi')
    printCallStack()
    assertTrue(assertMethodCallContainsPattern('bat', 'script=echo hi'))
    assertJobStatusSuccess()
  }

  @Test
  void test_windows_with_returnStdout() throws Exception {
    script.call(script: 'echo hi', returnStdout: true)
    printCallStack()
    assertTrue(assertMethodCallContainsPattern('bat', 'script=@echo off'))
    assertTrue(assertMethodCallContainsPattern('bat', 'echo hi'))
    assertJobStatusSuccess()
  }
}
