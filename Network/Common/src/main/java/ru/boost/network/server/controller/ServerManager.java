package ru.boost.network.server.controller;

import ru.boost.network.common.connection.controller.AbstractConnectionController;
import ru.boost.network.common.connection.controller.CommonConnectionController;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerManager {

    private Map<Integer, ServerController> servers;

    private static ServerManager instance;


    public static ServerManager getInstance(){
        if(instance == null){
            instance = new ServerManager();
        }
        return instance;
    }

    private ServerManager(){
        servers = new HashMap<>();
    }

    public RESTServerController getRESTServerController(int port){
        if(!servers.containsKey(port)){
            try {
                RESTServerController serverController = new RESTServerController(port);
                serverController.start();
                servers.put(port,serverController);
            }
            catch (IOException e){
                return null;
            }
        }
        ServerController serverController = servers.get(port);
        if(serverController instanceof RESTServerController){
            return (RESTServerController) serverController;
        }
        return null;
    }

    public SocketServerController getSocketServerController(int port){
        if(!servers.containsKey(port)){
            try {
                SocketServerController serverController = new SocketServerController(port){
                    @Override
                    public AbstractConnectionController getController(Socket socket) {
                        return new CommonConnectionController(socket);
                    }
                };
                serverController.start();
                servers.put(port,serverController);
            }
            catch (IOException e){
                return null;
            }
        }
        ServerController serverController = servers.get(port);
        if(serverController instanceof SocketServerController){
            return (SocketServerController) serverController;
        }
        return null;
    }

    public void stopServer(int port){
        ServerController controller = servers.get(port);
        if(controller != null){
            controller.stop();
            servers.remove(port);
        }
    }
}
