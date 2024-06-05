package src.bts.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.bts.BTSApplication;
import src.bts.domain.Employee;
import src.bts.domain.Programmer;
import src.bts.domain.Tester;
import src.bts.service.BugService;
import src.bts.service.EmployeeService;
import src.bts.service.ProgrammerService;
import src.bts.service.TesterService;

import java.io.IOException;

public class MainController {

    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;

    private Employee employee;
    private EmployeeService employeeService;
    private TesterService testerService;
    private ProgrammerService programmerService;

    private BugService bugService;

    public void setService(EmployeeService employeeService, TesterService testerService, ProgrammerService programmerService, BugService bugService){
        this.employeeService = employeeService;
        this.testerService = testerService;
        this.programmerService = programmerService;
        this.bugService = bugService;
    }

    public void handleLogInEvent(){
        String username = null;
        String password = null;
        try {
            username = usernameField.getText();
            password = passwordField.getText();
        }
        catch (Exception e){
            BTS_Action.showMessage(null, Alert.AlertType.ERROR, "Login Input Failure","Error:\n" + e.getMessage());
            return;
        }

        try {
            this.employee = employeeService.findOneByUsernameAndPassword(username, password);
            Tester tester = testerService.findOne(employee.getId());
            Programmer programmer = programmerService.findOne(employee.getId());
            if(tester != null ){
                tester.setUsername(employee.getUsername());
                tester.setPassword(employee.getPassword());
                initTesterPage(tester);
            }else if(programmer != null){
                programmer.setUsername(employee.getUsername());
                programmer.setPassword(employee.getPassword());
                initProgrammerPage(programmer);
            }else{
                throw new IllegalArgumentException("(MainController) No employee in the db!");
            }
        }catch (Exception e){
            BTS_Action.showMessage(null, Alert.AlertType.ERROR, "Login Input Failure","Error:\n" + e.getMessage());
        }
    }

    public void initTesterPage(Tester tester){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BTSApplication.class.getResource("iss-w1.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
            stage.setScene(scene);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tester Page - " + tester.getUsername());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            dialogStage.setScene(scene);

            TesterController testerController = fxmlLoader.getController();
            testerController.setService(employeeService, testerService, bugService, tester, dialogStage);
            dialogStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initProgrammerPage(Programmer programmer){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BTSApplication.class.getResource("iss-w2.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
            stage.setScene(scene);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Programmer Page - " + programmer.getUsername());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            dialogStage.setScene(scene);

            ProgrammerController programmerController = fxmlLoader.getController();
            programmerController.setService(employeeService, programmerService, bugService, programmer, dialogStage);
            dialogStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
