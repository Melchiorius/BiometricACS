package ru.boost.network.client.controller;

import ru.boost.network.client.Client;
import ru.boost.network.client.handler.rest.RESTPersonAll;
import ru.boost.network.client.handler.rest.RESTPersonDelete;
import ru.boost.network.client.handler.rest.RESTPersonUpdate;
import ru.boost.network.client.model.ClientModel;
import ru.boost.network.common.businesslogic.entity.Person;
import ru.boost.network.common.businesslogic.setttings.Settings;
import ru.boost.ui.controller.DesktopController;

public class ClientController {

    private static ClientModel model;
    private static Client client;

    public static void main(String[] args) {
        model = new ClientModel();
        loadData();
        DesktopController.getInstance();
        client = new Client(Settings.RESTHost,Settings.VideoPort);
        client.start();
    }

    public static void loadData(){
        model.setData(RESTPersonAll.get());
    }

    public static Person[] getData(){
        return model.getData();
    }

    public static void updateData(long id, String name, boolean access){
        RESTPersonUpdate.update(id,name,access);
    }

    public static void deleteData(long id){
        RESTPersonDelete.delete(id);
    }

}
