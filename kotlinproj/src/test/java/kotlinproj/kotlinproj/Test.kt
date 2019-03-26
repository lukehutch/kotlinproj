package serverx.server

import io.vertx.core.AbstractVerticle
import io.vertx.core.CompositeFuture
import io.vertx.core.Future
import io.vertx.core.http.HttpServer

class Test : AbstractVerticle {

	constructor() {}

	suspend fun awaitingFuture() {
		val httpServerFuture = Future.future<HttpServer>()
		vertx.createHttpServer()
				.requestHandler { req -> req.response().end("Hello!") }
				.listen(8000, httpServerFuture)

		val httpServer = httpServerFuture.await()
		println("HTTP server port: ${httpServer.actualPort()}")

		val result = CompositeFuture.all(httpServerFuture, httpServerFuture).await()
		if (result.succeeded()) {
			println("The server is now running!")
		} else {
			result.cause().printStackTrace()
		}
	}

}