// Licensed to Elasticsearch B.V. under one or more contributor
// license agreements. See the NOTICE file distributed with
// this work for additional information regarding copyright
// ownership. Elasticsearch B.V. licenses this file to you under
// the Apache License, Version 2.0 (the "License"); you may
// not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import mock.StepsMock
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertTrue
import static org.hamcrest.CoreMatchers.is

public class BuildStepTests extends base.PipelineTestHelper {
  def script

  @Override
  @Before
  void setUp() throws Exception {
    super.setUp()
    binding.setProperty('steps', new StepsMock())
    script = loadScript('vars/build.groovy')
  }

  @Test
  void test_success() throws Exception {
    def result = script.call(job: 'foo')
    printCallStack()
    //assertTrue(result != null)
    //assertTrue(assertMethodCallContainsPattern('log', "/job/foo/1/display/redirect"))
    //assertJobStatusSuccess()
  }

  @Test
  void test_nested_job() throws Exception {
    def result = script.call(job: 'nested/foo')
    printCallStack()
    //assertTrue(result != null)
    //assertTrue(assertMethodCallContainsPattern('log', "/job/nested/job/foo/1/display/redirect"))
    //assertJobStatusSuccess()
  }

  @Test
  void test_exception() throws Exception {
    def result = script.getRedirectLink('nested » foo #1', 'nested/foo')
    assertTrue(result.contains("job/nested/job/foo/1/display/redirect"))
  }

  @Test
  void testExceptionWithoutTheFormat() throws Exception {
    def result = script.getRedirectLink('nested » foo', 'nested/foo')
    assertTrue(result.contains("Can not determine redirect link"))
  }

  @Test
  void testAnotherObject() throws Exception {
    def result = script.getRedirectLink('AnotherObject', 'foo')
    assertTrue(result.contains("Can not determine redirect link"))
  }

  @Test
  void test_propagage_failure_with_null_object() throws Exception {
    script.propagateFailure(null)
    printCallStack()
    assertTrue(assertMethodCallContainsPattern('echo', 'buildInfo is not an object'))
  }

  @Test
  void test_propagage_failure_with_an_object() throws Exception {
    try {
      script.propagateFailure(StepsMock.mockRunWrapperWithFailure('foo/bar', 'Issue: checkout timeout'))
    } catch (e) {
      assertTrue(e instanceof Exception)
    }
    printCallStack()
    assertFalse(assertMethodCallContainsPattern('echo', 'buildInfo is not an object'))
  }
}
