package src.bts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.bts.controller.MainController;
import src.bts.repository.BugDBRepository;
import src.bts.repository.EmployeeDBRepository;
import src.bts.repository.ProgrammerDBRepository;
import src.bts.repository.TesterDBRepository;
import src.bts.service.BugService;
import src.bts.service.EmployeeService;
import src.bts.service.ProgrammerService;
import src.bts.service.TesterService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BTSApplication extends Application {
    private EmployeeDBRepository employeeDBRepository;
    private TesterDBRepository testerDBRepository;
    private ProgrammerDBRepository programmerDBRepository;
    private BugDBRepository bugDBRepository;
    private EmployeeService employeeService;
    private TesterService testerService;
    private ProgrammerService programmerService;
    private BugService bugService;

    @Override
    public void start(Stage stage) throws Exception {
        Properties props = new Properties();
        try {
            props.load(new FileReader("db.config"));
        } catch (IOException e) {
            System.out.println("Cannot find db.config " + e);
        }

        employeeDBRepository = new EmployeeDBRepository(props);
        testerDBRepository = new TesterDBRepository(props);
        programmerDBRepository = new ProgrammerDBRepository(props);
        bugDBRepository = new BugDBRepository(props);
        employeeService = new EmployeeService(employeeDBRepository);
        testerService = new TesterService(testerDBRepository);
        programmerService = new ProgrammerService(programmerDBRepository);
        bugService = new BugService(bugDBRepository);
        initView(stage);
        stage.setTitle("BTS");
        stage.show();
    }

    private void initView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BTSApplication.class.getResource("iss-login.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 1024, 768));
        MainController mainController = fxmlLoader.getController();
        mainController.setService(employeeService, testerService, programmerService, bugService);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
