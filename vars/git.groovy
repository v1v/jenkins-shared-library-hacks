/**
  Override git built-in step to retry up to 3 times with a sleep of 10 seconds.

  Workaround https://issuetracker.google.com/issues/146072599
*/
def call(args) {
  echo('Override built-in git step.')
  def ret
  retry(3) {
    try {
      sleep(10)
      ret = steps.git(args)
    } catch(e) {
      throw e
    }
  }
  return ret
}
