
 
fun diff(current: String, proposed: String): String {
    val currentContentFile = createTempFile()
    currentContentFile.writeText(current)
    val proposedContentFile = createTempFile()
    proposedContentFile.writeText(proposed)
 
    return "diff -w %s %s".format(currentContentFile, proposedContentFile).shell().orDie().stdout
}
