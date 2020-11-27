NAME = 'sh'
DSL = '''pipeline {
  agent any
  stages {
    stage('linux') {
      steps {
        script {
          def ret = sh(script: 'echo hi', returnStatus: true)
          echo "Returned: ${ret}"
          if (ret > 0) {
            echo('Failed since label was not there')
          }
          ret = sh(script: 'echo hi', returnStatus: true, label: 'yeah')
          if (ret == 0) {
            echo('Passed since label was there')
          }
        }
      }
    }
  }
}'''

pipelineJob(NAME) {
  definition {
    cps {
      script(DSL.stripIndent())
    }
  }
}
