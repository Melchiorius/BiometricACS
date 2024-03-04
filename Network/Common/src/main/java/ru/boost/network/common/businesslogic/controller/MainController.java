package ru.boost.network.common.businesslogic.controller;

import ru.boost.network.common.businesslogic.model.BaseModel;
import ru.boost.network.common.businesslogic.setttings.Settings;
import ru.boost.network.common.businesslogic.utils.ObjectMapper;
import ru.boost.network.server.controller.RESTServerController;
import ru.boost.network.server.controller.VideoSenderServerController;

import java.io.IOException;

public class MainController {

    private static BaseModel baseModel;
    private static VideoSenderServerController videoSender;

    public static void main(String[] args) throws IOException {
        Settings.load();
        baseModel = new BaseModel();
        RESTServerController restServerController = new RESTServerController(Settings.RESTPort);
        restServerController.start();
        videoSender = new VideoSenderServerController(Settings.VideoPort);
        videoSender.start();
        while(baseModel.isRunning()){

        }
    }

    public static String getAllData(){
        return ObjectMapper.Persons2Json(baseModel.getAllPerson());
    }

    public static void updateData(long id, String name, boolean access){
        baseModel.updatePerson(id,name,access);
    }

    public static void deleteData(long id){
        baseModel.deletePerson(id);
    }


}
