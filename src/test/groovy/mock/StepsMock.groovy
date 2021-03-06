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

package mock

import hudson.model.Run
import hudson.tasks.test.AbstractTestResultAction
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * Mock Steps class.
 */
public class StepsMock implements Serializable {

  public RunWrapper build(Map args = [:]) {
    return mockRunWrapper(args.job)
  }

  public Object checkout(scm) {
    throw new Exception('Force a failure')
  }

  public Object git(scm) {
    throw new Exception('Force a failure')
  }

  private RunWrapper mockRunWrapper(String jobName) throws Exception {
    final RunWrapper runWrapper = mock(RunWrapper.class)
    // It ends with the '/'. See https://github.com/jenkinsci/jenkins/blob/ad1ca7101b9b180dc677eef914b1cbd8208d00c8/core/src/main/java/hudson/model/Run.java#L1028
    when(runWrapper.getAbsoluteUrl()).thenReturn("<jenkins_url>/job/${transformJobName(jobName)}/1/".toString())
    when(runWrapper.getFullProjectName()).thenReturn(jobName)
    when(runWrapper.getNumber()).thenReturn(1)
    when(runWrapper.getDisplayName()).thenReturn("#1")
    when(runWrapper.getCurrentResult()).thenReturn('SUCCESS')
    return runWrapper
  }

  private static transformJobName(String jobName) {
    return jobName.replaceAll("/","/job/")
  }

  public static RunWrapper mockRunWrapperWithFailure(String jobName, String description = '') throws Exception {
    final RunWrapper runWrapper = mock(RunWrapper.class)
    // It ends with the '/'. See https://github.com/jenkinsci/jenkins/blob/ad1ca7101b9b180dc677eef914b1cbd8208d00c8/core/src/main/java/hudson/model/Run.java#L1028
    when(runWrapper.getAbsoluteUrl()).thenReturn("<jenkins_url>/job/${transformJobName(jobName)}/1/".toString())
    when(runWrapper.getCurrentResult()).thenReturn('FAILURE')
    when(runWrapper.getDescription()).thenReturn(description)
    when(runWrapper.getDisplayName()).thenReturn('#1')
    when(runWrapper.getFullProjectName()).thenReturn(jobName)
    when(runWrapper.getNumber()).thenReturn(1)
    when(runWrapper.getProjectName()).thenReturn(jobName.tokenize('/').last())
    return runWrapper
  }
}
