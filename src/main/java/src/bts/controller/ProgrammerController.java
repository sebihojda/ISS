package src.bts.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import src.bts.domain.Bug;
import src.bts.domain.Programmer;
import src.bts.observer.Observer;
import src.bts.service.BugService;
import src.bts.service.EmployeeService;
import src.bts.service.ProgrammerService;
import src.bts.utils.BTSEvent;

import java.util.List;
import java.util.Objects;

public class ProgrammerController implements Observer<BTSEvent> {

    private EmployeeService employeeService;
    private ProgrammerService programmerService;
    private BugService bugService;
    private Programmer programmer;
    private Stage modalStage;
    public void setService(EmployeeService employeeService, ProgrammerService programmerService, BugService bugService, Programmer programmer, Stage dialogStage) {
        this.employeeService = employeeService;
        this.programmerService = programmerService;
        this.bugService = bugService;
        this.bugService.addObserver(this);
        this.programmer = programmer;
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
        List<Bug> bugList = bugService.findAll().stream().filter(bug -> !Objects.equals(bug.getCondition(), "resolved")).toList();
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
    public void handleUpdateEvent(){
        Bug bugToBeSolved = bugTableView.getSelectionModel().getSelectedItem();
        if(bugToBeSolved == null){
            BTS_Action.showMessage(null, Alert.AlertType.ERROR, "Update Error", "Please select a bug before trying to solve one!");
            return;
        }
       try{
           bugService.update(bugToBeSolved.getId(), bugToBeSolved.getName(), bugToBeSolved.getDescription(), bugToBeSolved.getSeverity(), "resolved");
           BTS_Action.showMessage(null, Alert.AlertType.INFORMATION, "Update Status", "Update: Success");
       }catch (Exception ex){
           BTS_Action.showMessage(null, Alert.AlertType.WARNING, "Update Failure", "Failure:\n" + ex.getMessage() + "\nPlease try again!");
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
