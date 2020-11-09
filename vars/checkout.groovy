/**
  Workaround https://issuetracker.google.com/issues/146072599
*/
def call(args) {
  echo('Override built-in checkout step')
  def ret
  retry(3) {
    try {
      sleep(10)
      ret = steps.checkout(args)
    } catch(e) {
      throw e
    }
  }
  return ret
}
