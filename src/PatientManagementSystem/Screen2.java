package PatientManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Screen2 extends Stage {

    private Integer id_doctor_class;
    private final String name_doctor_class;
    private final String password_doctor_class;
    private  TextField search_tf;
    private TableView<Patient> tableView_Patient;
    private ObservableList<Patient> data_Patient = FXCollections.observableArrayList();

    private TextField idPatient_tf;
    private TextField namePatient_tf;
    private TextField agePatient_tf;
    private DatePicker datePicker;
    private TextField problemPatient_tf;

    private final ToggleGroup toggleGroup_Gender = new ToggleGroup();
    private RadioButton radioButton_female;
    private RadioButton radioButton_male;
    private String genderValue_helper;

    private Statement statementSQL;

   
    private String gender;

    public Screen2(Integer idUser, String nameUser, String passwordUser) throws ClassNotFoundException, SQLException {
        this.id_doctor_class = idUser;
        this.name_doctor_class = nameUser;
        this.password_doctor_class = passwordUser;

        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/hospital?serverTimezone=UTC";
        Connection connectOnDatabase = DriverManager.getConnection(url, "root", "");
        this.statementSQL = connectOnDatabase.createStatement();
        search_tf=new TextField();
        search_tf.setPromptText("Search");
        search_tf.setOnKeyReleased(event -> {
            ObservableList<Patient> resultList=FXCollections.observableArrayList();
            String text=search_tf.getText().trim();
            for (Patient p : data_Patient) {
                
                if     (
                        p.getPatientName().contains(text)||p.getPatientProblem().contains(text)
                        ||
                        p.getPatientGender().contains(text)||p.getPatientEntranceDate().contains(text)
                        ||
                        p.getPatientId().toString().contains(text)
                        
                        )
                {
                    resultList.add(p);
                    tableView_Patient.setItems(resultList);
                    tableView_Patient.refresh();
                }
                
            }


        });
        
        tableView_Patient = new TableView<>();
        tableView_Patient.setPrefSize(900, 600);

        TableColumn<Patient, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory("patientId"));
        idColumn.setPrefWidth(150);

        TableColumn<Patient, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory("patientName"));
        nameColumn.setPrefWidth(150);

        TableColumn<Patient, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory("patientAge"));
        ageColumn.setPrefWidth(150);

        TableColumn<Patient, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("patientGender"));
        genderColumn.setPrefWidth(150);

        TableColumn<Patient, String> problemColumn = new TableColumn<>("Problem");
        problemColumn.setCellValueFactory(new PropertyValueFactory<>("patientProblem"));
        problemColumn.setPrefWidth(150);

        TableColumn<Patient, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("patientEntranceDate"));
        dateColumn.setPrefWidth(150);

        tableView_Patient.getColumns().addAll(idColumn, nameColumn, ageColumn, genderColumn, problemColumn, dateColumn);

        tableView_Patient.getSelectionModel().selectedItemProperty().addListener(event -> {
            Patient patient = tableView_Patient.getSelectionModel().getSelectedItem();
            if (patient != null) {

                idPatient_tf.setText(String.valueOf(patient.getPatientId()));
                namePatient_tf.setText(patient.getPatientName());
                agePatient_tf.setText(String.valueOf(patient.getPatientAge()));
                LocalDate date=LocalDate.parse(patient.getPatientEntranceDate());
                datePicker.setValue(date);
                problemPatient_tf.setText(patient.getPatientProblem());
                gender = patient.getPatientGender();
                if (gender.equalsIgnoreCase("female")) {

                    radioButton_female.setSelected(true);
                    radioButton_male.setSelected(false);

                } else if (gender.equalsIgnoreCase("male")) {

                    radioButton_male.setSelected(true);
                    radioButton_female.setSelected(false);

                }
            }
        });

        Label id_Label = new Label("Patient id ");
        idPatient_tf = new TextField();

        Label name_Label = new Label("Patient name ");
        namePatient_tf = new TextField();

        Label age_Label = new Label("Patient age ");
        agePatient_tf = new TextField();

        Label date_Label = new Label("Patient date ");
        datePicker = new DatePicker(LocalDate.now());

        Label problem_Label = new Label("Patient problem");
        problemPatient_tf = new TextField();

        Label gender_Label = new Label("Patient gender");

        radioButton_female = new RadioButton("female");
        radioButton_female.setToggleGroup(toggleGroup_Gender);
        radioButton_female.setOnAction(event -> {
            genderValue_helper = radioButton_female.getText().trim().toLowerCase();
        });

        radioButton_male = new RadioButton("male");
        radioButton_male.setToggleGroup(toggleGroup_Gender);
        radioButton_male.setOnAction(event -> {
            genderValue_helper = radioButton_male.getText().trim().toLowerCase();
        });

        HBox genderRadioButton = new HBox(10, radioButton_female, radioButton_male);

        GridPane gridPane = new GridPane();
        gridPane.add(id_Label, 0, 0);
        gridPane.add(idPatient_tf, 1, 0);

        gridPane.add(name_Label, 0, 1);
        gridPane.add(namePatient_tf, 1, 1);

        gridPane.add(age_Label, 0, 2);
        gridPane.add(agePatient_tf, 1, 2);

        gridPane.add(date_Label, 0, 3);
        gridPane.add(datePicker, 1, 3);

        gridPane.add(problem_Label, 0, 4);
        gridPane.add(problemPatient_tf, 1, 4);

        gridPane.add(gender_Label, 0, 5);
        gridPane.add(genderRadioButton, 1, 5);

        gridPane.setVgap(100);
        gridPane.setHgap(10);

        HBox gridPane_TableView = new HBox(20, gridPane, tableView_Patient);
        gridPane_TableView.setAlignment(Pos.CENTER);

        Button btn_ShowData = new Button("Show Data");
        btn_ShowData.setOnAction(event -> {
            String selectData = "select * from patient Where patientId in ( select pat_id from patient_doctor where doc_id = " + id_doctor_class + ");";
            try {
                ResultSet database_result = statementSQL.executeQuery(selectData);
                tableView_Patient.getItems().clear();
                while (database_result.next()) {
                    
  
                    Integer patientId_db = database_result.getInt("patientId");
                    String patientName_db  = database_result.getString("patientName");
                    Integer patientAge_db = database_result.getInt("patientAge");
                    String patientGender_db = database_result.getString("patientGender");
                    String patientProblem_db = database_result.getString("patientProblem");
                    String patientEntranceDate_db = database_result.getString("patientEntranceDate");

                    Patient patient = new Patient(patientId_db, patientName_db, patientAge_db, patientGender_db, patientProblem_db, patientEntranceDate_db);
                    data_Patient.add(patient);

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            tableView_Patient.setItems(data_Patient);

        });

        Button btn_AddPatient = new Button("add patient");
        btn_AddPatient.setOnAction(event -> {
            tableView_Patient.getItems().clear();
            Integer id_Insert = Integer.parseInt(idPatient_tf.getText());
            String name_Insert = namePatient_tf.getText();
            Integer age_Insert = Integer.parseInt(agePatient_tf.getText());
            String gender_Insert = this.genderValue_helper;
            String probelm_Insert = problemPatient_tf.getText();
            LocalDate date_Insert = datePicker.getValue();
            String insertData1 = "Insert into patient values ( " + id_Insert + ",'" + name_Insert + "'," + age_Insert + ",'" + gender_Insert + "','" + probelm_Insert + "','" + date_Insert + "');";
            String insertData2 = "Insert into patient_doctor values ( " + id_Insert + "," + id_doctor_class + ");";
            try {
                int result1 = statementSQL.executeUpdate(insertData1);
                int result2 = statementSQL.executeUpdate(insertData2);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            btn_ShowData.getOnAction().handle(event);
            reset();
        });
        Button btn_UpdatePatient = new Button("update patient");
        btn_UpdatePatient.setOnAction(event -> {
            tableView_Patient.getItems().clear();

            Integer id_Update = Integer.parseInt(idPatient_tf.getText());
            String name_Update = namePatient_tf.getText();
            Integer age_Update = Integer.parseInt(agePatient_tf.getText());
            String gender_Update = this.genderValue_helper;
            String probelm_Update = problemPatient_tf.getText();
            LocalDate date_Update = datePicker.getValue();

            String updateDate = "Update patient set "
                    + "patientName='" + name_Update + "',"
                    + "patientAge = " + age_Update + ","
                    + "patientGender = '" + gender + "',"
                    + "patientProblem = '" + probelm_Update + "',"
                    + "patientEntranceDate = '" + date_Update + "'"
                    + "where patientId=" + id_Update + ";";

            try {
                int result = statementSQL.executeUpdate(updateDate);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            tableView_Patient.getItems().clear();
            btn_ShowData.getOnAction().handle(event);
            tableView_Patient.getItems().clear();
            btn_ShowData.getOnAction().handle(event);
            reset();

        });
        Button btn_DeletePatient = new Button("delete patient");
        btn_DeletePatient.setOnAction(event -> {
            tableView_Patient.getItems().clear();

            Integer id_Patient_Delete = Integer.parseInt(idPatient_tf.getText().trim());

            String delete1 = "Delete from patient_drug where pat_id  = " + id_Patient_Delete;

            String delete2 = "Delete from drug where pat_id  = " + id_Patient_Delete;

            String delete3 = "Delete from patient_doctor where pat_id   = " + id_Patient_Delete;

            String delete4 = "Delete from patient where patientId  = " + id_Patient_Delete;

            try {

                int result1 = statementSQL.executeUpdate(delete1);
                int result2 = statementSQL.executeUpdate(delete2);
                int result3 = statementSQL.executeUpdate(delete3);
                int result4 = statementSQL.executeUpdate(delete4);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
           
            btn_ShowData.getOnAction().handle(event);
            reset();

        });
        Button btn_Drug = new Button("=>Drug<=");
        btn_Drug.setOnAction(event -> {
            try {
                Screen3 screen3 = new Screen3(id_doctor_class, Integer.parseInt(idPatient_tf.getText().trim()), namePatient_tf.getText().trim());
                screen3.show();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Screen2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Screen2.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch(Exception ex){
                  Alert alertWarning = new Alert(AlertType.WARNING);
                  alertWarning.setTitle("Warning !");
                  alertWarning.setHeaderText("يجب ان تختار مريض من الجدول أو تدخل رقمه واسمه في حقول الإدخال");
                  alertWarning.showAndWait();
            }
        });
        
        Button logout=new Button("Logout");
        logout.setOnAction(event -> {
               close();
                PatientManagementSystem pms = new PatientManagementSystem();
            try {
                pms.start(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(PatientManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
      
        HBox buttons = new HBox(10, btn_AddPatient, btn_UpdatePatient, btn_DeletePatient, btn_ShowData, btn_Drug,logout);
        buttons.setAlignment(Pos.CENTER);

        VBox gridPane_TableView_Buttons = new VBox(10, search_tf,gridPane_TableView, buttons);

        FlowPane parentLayout = new FlowPane(gridPane_TableView_Buttons);
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.setStyle("-fx-background-color:#a6abe0;");

        Scene scene = new Scene(parentLayout, 1500, 800);
        scene.getStylesheets().add("file:src/PatientManagementSystem/cssScreen2.css");
        setScene(scene);
    }

    private void reset() {
        idPatient_tf.setText("");
        namePatient_tf.setText("");
        agePatient_tf.setText("");
       datePicker.setValue(LocalDate.now());
        problemPatient_tf.setText("");
        radioButton_female.setSelected(false);
        radioButton_male.setSelected(false);
    }

}
