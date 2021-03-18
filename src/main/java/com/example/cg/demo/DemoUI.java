package com.example.cg.demo;

import com.example.cg.model.DemoModel;
import com.example.cg.view.RootPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author zhangxiaoyu
 * @date 2021/3/10
 */
public class DemoUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        DemoModel model = new DemoModel();
        RootPane rootPanel = new RootPane(model);

        Scene scene = new Scene(rootPanel);

        primaryStage.titleProperty().bind(model.getFormInstance().titleProperty());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
