package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.Main;
import sample.model.Zadach;
import sample.requests.model.ZadachEntity;
import sample.utils.RequestUtil;

public class HomeScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button PlannedButton;

    @FXML
    private Button CategoryButton;

    @FXML
    private Button ImportantButton;

    @FXML
    private Button AboutUsButton;

    @FXML
    private Button HelpButton;

    @FXML
    private Button CreateTaskButton;
    @FXML
    private TableView<Zadach> zadachTableView;
    @FXML
    private TableColumn<Zadach, String> categoryColumn;
    @FXML
    private TableColumn<Zadach, String> importantColumn;
    @FXML
    private TableColumn<Zadach, String> nameColumn;
    private Main main;
    public HomeScreenController() {
    }

    @FXML
    void changepages(ActionEvent event) throws IOException {

    }

    @FXML
    void initialize() {
        CategoryButton.setOnAction(event -> {
            System.out.println("Вы нажали на кнопку войти");
        });
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());
        importantColumn.setCellValueFactory(cellData -> cellData.getValue().getImportantProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        zadachTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue )-> setItem(newValue));

    }
    private void setItem(Zadach newValue) {
    }
    public void setMainApp(Main main) {
        this.main = main;
        zadachTableView.setItems(this.main.getZadachData());
    }
    @FXML
    public void syncZadachData() {
        main.getZadachData().clear();

        RequestUtil requestUtil = new RequestUtil("/zadachs", "GET");
        requestUtil.run();
        Gson gson = new Gson();
        System.out.println(requestUtil.getResponse());
        List<ZadachEntity> zadachEntities = gson.fromJson(requestUtil.getResponse(),
                TypeToken.getParameterized(List.class, ZadachEntity.class).getType());

        if (zadachEntities != null) {
            for (ZadachEntity zadachEntity : zadachEntities) {
                System.out.println(zadachEntity);
                Zadach teatr = new Zadach(zadachEntity);
                main.getZadachData().add(teatr);
            }
        }
    }
}





