package br.com.rentakelly

fun retryer(times: Int = 3, action: () -> Unit) {
    Thread.sleep(3000)
    for (i in 0 until times) {
        try {
            action.invoke()
            return
        } catch (o: Throwable) {
            if (i == times - 1) {
                throw o
            }
            Thread.sleep(3000)
        }
    }

}
