package PatientManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Screen3 extends Stage {

    private  Integer id_doctor_class;
    private Integer id_patient_class;
    private String name_patient_class;

    public Screen3() {
       
    }

    TableView<Drug> tableView_Drugs;
    ObservableList<Drug> dataDrugs = FXCollections.observableArrayList();
    Statement statementSQL;

    TextField idDurg_tf;
    TextField nameDurg_tf;
//    TextField dozeDurg_tf;
    Slider slider;
    Label l;

    public Screen3(Integer iddoctor, Integer idpatient, String namepatient) throws ClassNotFoundException, SQLException {
        this.id_doctor_class = iddoctor;
        this.id_patient_class = idpatient;
        this.name_patient_class = namepatient;
        

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/hospital?serverTimezone=UTC";
            Connection connectOnDatabase = DriverManager.getConnection(url, "root", "");
            this.statementSQL = connectOnDatabase.createStatement();

            Label patientInformation_Label = new Label("==> patient Name: " + name_patient_class + " patient Id: "+id_patient_class+" <==");
            patientInformation_Label.setStyle("-fx-text-fill:white;");

            tableView_Drugs = new TableView<>();
            tableView_Drugs.setPrefSize(450, 600);
            TableColumn<Drug, Integer> idColumn = new TableColumn<>("Id");
            idColumn.setCellValueFactory(new PropertyValueFactory("idDrug"));
            idColumn.setPrefWidth(150);

            TableColumn<Drug, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory("nameDrug"));
            nameColumn.setPrefWidth(150);

            TableColumn<Drug, Double> DozeColumn = new TableColumn<>("Doze");
            DozeColumn.setCellValueFactory(new PropertyValueFactory("dozeDrug"));
            DozeColumn.setPrefWidth(150);

            tableView_Drugs.getColumns().addAll(idColumn, nameColumn, DozeColumn);
            tableView_Drugs.getSelectionModel().selectedItemProperty().addListener(event -> {
                Drug drug = tableView_Drugs.getSelectionModel().getSelectedItem();
                if (drug != null) {
                    idDurg_tf.setText(String.valueOf(drug.getIdDrug()));
                    nameDurg_tf.setText(drug.getNameDrug());
                    slider.setValue(drug.getDozeDrug());
                    l.setText(drug.getDozeDrug()+"");
                    
                }
            }
            );

            GridPane gridPane = new GridPane();

            Label idLable = new Label("durg Id");
            idDurg_tf = new TextField();

            Label nameLable = new Label("durg Name");
            nameDurg_tf = new TextField();

            Label dozeLable = new Label("durg Doze");

            gridPane.add(idLable, 0, 0);
            gridPane.add(idDurg_tf, 1, 0);

            gridPane.add(nameLable, 0, 1);
            gridPane.add(nameDurg_tf, 1, 1);
            l=new Label();
            slider=new Slider(1,10,0);
            slider.prefWidth(50);
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setMajorTickUnit(2);
            slider.setMinorTickCount(5);
            slider.valueProperty().addListener(Event -> {
            double n = slider.getValue();
            double nnew=(double)Math.round(n*100)/100;
            l.setText(nnew+"");
        });
           HBox hb=new HBox(slider,l);
            gridPane.add(dozeLable, 0, 2);
            gridPane.add(hb, 1, 2);

            gridPane.setVgap(100);
            gridPane.setHgap(10);
            gridPane.setPadding(new Insets(160, 0, 0, 0));

            HBox gridPane_TableView = new HBox(20, gridPane, tableView_Drugs);
            VBox gridPane_TableView_label = new VBox(20, patientInformation_Label, gridPane_TableView);
            gridPane_TableView_label.setAlignment(Pos.CENTER);

            Button btnShowAssignedDrugs = new Button("Show Assigned Drugs ");
            btnShowAssignedDrugs.setOnAction(event -> {
                String selectData = "select * from drug where pat_id = " + this.id_patient_class;
                try {
                    ResultSet database_result = statementSQL.executeQuery(selectData);
                    tableView_Drugs.getItems().clear();
                    while (database_result.next()) {

                        Integer durgId_db = database_result.getInt("durgId");
                        String durgName_db = database_result.getString("durgName");
                        Double durgDoze_db = database_result.getDouble("durgDoze");
                        Drug rug = new Drug(durgId_db, durgName_db, durgDoze_db);

                        dataDrugs.add(rug);

                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                tableView_Drugs.setItems(dataDrugs);

            });

            Button btn_AssignDrug = new Button("Assign Drug");
            btn_AssignDrug.setOnAction(event -> {
                tableView_Drugs.getItems().clear();
                Integer id_Insert = Integer.parseInt(idDurg_tf.getText().trim());
                String name_Insert = nameDurg_tf.getText().trim();
                Double doze_Insert = Double.parseDouble(l.getText());
                String insertData1 = "Insert into drug values ( " + id_Insert + "," + this.id_patient_class + ",'" + name_Insert + "'," + doze_Insert + ");";
                String insertData2 = "Insert into patient_drug values ( " + idpatient + "," + id_Insert + ");";
                try {
                    int result1 = statementSQL.executeUpdate(insertData1);
                    int result2 = statementSQL.executeUpdate(insertData2);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                btnShowAssignedDrugs.getOnAction().handle(event);
                reset();

            });
            Button btn_DeleteDrug = new Button("Delete Drug ");
            btn_DeleteDrug.setOnAction(event -> {
                tableView_Drugs.getItems().clear();

                Integer id_Durg_Delete = Integer.parseInt(idDurg_tf.getText().trim());

                String deleteData1 = "delete from patient_drug where drug_id = " + id_Durg_Delete;
                String deleteData2 = "delete from drug where durgId = " + id_Durg_Delete;

                try {
                    int result1 = statementSQL.executeUpdate(deleteData1);
                    int result2 = statementSQL.executeUpdate(deleteData2);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                btnShowAssignedDrugs.getOnAction().handle(event);
                reset();

            });

            HBox buttons = new HBox(10, btn_AssignDrug, btn_DeleteDrug, btnShowAssignedDrugs);
            buttons.setAlignment(Pos.CENTER);

            VBox gridPane_TableView_label_Buttons = new VBox(20, gridPane_TableView_label, buttons);

            FlowPane parentLayout = new FlowPane(gridPane_TableView_label_Buttons);
            parentLayout.setAlignment(Pos.CENTER);
            parentLayout.setStyle("-fx-background-color:#a6abe0;");

            Scene scene = new Scene(parentLayout, 1500, 800);
            scene.getStylesheets().add("file:src/PatientManagementSystem/cssScreen2.css");
            setScene(scene);
        }

    

    private void reset() {
        idDurg_tf.setText("");
        nameDurg_tf.setText("");
        slider.setValue(1);
        l.setText("");
    }

}
