package src.bts.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import src.bts.domain.Bug;
import src.bts.domain.Tester;
import src.bts.observer.Observer;
import src.bts.service.BugService;
import src.bts.service.EmployeeService;
import src.bts.service.TesterService;
import src.bts.utils.BTSEvent;

import java.util.List;

public class TesterController implements Observer<BTSEvent> {
    private EmployeeService employeeService;
    private TesterService testerService;
    private BugService bugService;
    private Tester tester;
    private Stage modalStage;

    public void setService(EmployeeService employeeService, TesterService testerService, BugService bugService, Tester tester, Stage dialogStage) {
        this.employeeService = employeeService;
        this.testerService = testerService;
        this.bugService = bugService;
        this.bugService.addObserver(this);
        this.tester = tester;
        this.modalStage = dialogStage;
        initBugsData();
    }

    @FXML
    TableView<Bug> bugTableView;
    @FXML
    TableColumn<Bug, String> bugNameColumn;
    @FXML
    TableColumn<Bug, String> bugDescriptionColumn;
    @FXML
    TableColumn<Bug, Integer> bugSeverityColumn;
    @FXML
    TableColumn<Bug, String> bugConditionColumn;
    ObservableList<Bug> bug_model = FXCollections.observableArrayList();

    public void initBugsData(){
        List<Bug> bugList = bugService.findAll();
        bug_model.setAll(bugList);
    }

    @FXML
    public void initialize(){
        bugNameColumn.setCellValueFactory(new PropertyValueFactory<Bug, String>("name"));
        bugDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Bug, String>("description"));
        bugSeverityColumn.setCellValueFactory(new PropertyValueFactory<Bug, Integer>("severity"));
        bugConditionColumn.setCellValueFactory(new PropertyValueFactory<Bug, String>("condition"));
        bugTableView.setItems(bug_model);
    }

    @FXML
    TextField nameField;
    @FXML
    TextField descriptionField;
    @FXML
    TextField severityField;
    @FXML
    TextField conditionField;

    @FXML
    public void handleCreateEvent(){
        try{
            String name = nameField.getText();
            String description = descriptionField.getText();
            Integer severity = Integer.parseInt(severityField.getText());
            String condition = conditionField.getText();
            bugService.add(name, description, severity, condition);
            BTS_Action.showMessage(null, Alert.AlertType.INFORMATION, "Save Status", "Save: Success");
        }
        catch (Exception ex){
            BTS_Action.showMessage(null, Alert.AlertType.WARNING, "Save Failure", "Failure:\n" + ex.getMessage() + "\nPlease try again!");
        }
    }

    @FXML
    public void handleDeleteEvent(){
        Bug selectedBug = bugTableView.getSelectionModel().getSelectedItem();
        if(selectedBug == null){
            BTS_Action.showMessage(null, Alert.AlertType.ERROR, "Delete Error", "Please select a bug before pressing delete!");
        }
        else{
            Long selectedBugId = selectedBug.getId();
            try {
                bugService.remove(selectedBugId);
                BTS_Action.showMessage(null, Alert.AlertType.INFORMATION, "Delete Status", "Delete: Success");
            }catch (Exception e){
                BTS_Action.showMessage(null, Alert.AlertType.WARNING, "Delete Failure", "Failure:\n" + e.getMessage() + "\nPlease try again!");
            }
        }
    }

    public void handleLogoutEvent(){
        this.bugService.removeObserver(this);
        modalStage.close();
    }

    @Override
    public void update(BTSEvent btsEvent) {
        if(btsEvent.getServiceType().toString().equals("Bug"))
            initBugsData();
    }
}
