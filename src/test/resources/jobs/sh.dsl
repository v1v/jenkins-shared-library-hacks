NAME = 'sh'
DSL = '''pipeline {
  agent any
  stages {
    stage('linux') {
      steps {
        script {
          try {
            sh(script: 'echo hi', returnStatus: true)
          } catch(err) {
            echo(err.toString())
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
