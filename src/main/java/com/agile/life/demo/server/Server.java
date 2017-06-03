package com.agile.life.demo.server;


import com.agile.life.demo.model.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;

public class Server extends AbstractVerticle {
    private static final String CONTENT_TYPE = "content-type";
    private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("Content-Type"));

        router.get("/saludo").handler(routingContext -> {
            routingContext.response().setStatusCode(200).setStatusMessage("hola todo bn").end();
        });
        router.get("/user").handler(routingContext -> {
            routingContext.response().putHeader(CONTENT_TYPE, CONTENT_TYPE_JSON).end(Json.encodePrettily(new User("name1", "emailA")));
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}
