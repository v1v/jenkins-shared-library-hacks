/**
  Enforce label argument when using the sh built-in step.
*/
def call(Map args = [:]) {
  echo('Override built-in sh step')
  if (args.label?.trim()) {
    return steps.sh(args)
  } else {
    error('Labels are mandatory')
  }
}

def call(script) {
  return call(script: script)
}
