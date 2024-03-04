package ru.boost.network.common.connection.handler.rest;

import com.sun.net.httpserver.HttpHandler;

public interface RESTHandlerInitiateFunction<T extends HttpHandler > {
    String getPath();
    T getHandler();
}
