fun <I, O : Any> depaginate(
        request: (String?) -> I,
        function: (I) -> O,
        nextToken: (O) -> String?)
        : Sequence<O> {
    var nextToken: String? = null
    var firstPage = true
    return generateSequence {
        if (nextToken == null && !firstPage) {
            null
        } else {
            var response = function(request(nextToken))
            firstPage = false
            nextToken = nextToken(response)
            response
        }
    }
}

