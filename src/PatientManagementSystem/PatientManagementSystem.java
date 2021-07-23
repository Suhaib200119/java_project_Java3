package PatientManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientManagementSystem extends Application {

    Statement statementSQL;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/hospital?serverTimezone=UTC";
        Connection connectOnDatabase = DriverManager.getConnection(url, "root", "");
        this.statementSQL = connectOnDatabase.createStatement();

        primaryStage.setTitle("Login screen");//title
        Image img=new Image("file:src/PatientManagementSystem/image.jpg");
        ImageView imgv=new ImageView(img);
        imgv.setFitHeight(250);
        imgv.setFitWidth(250);
        imgv.setStyle("-fx-background-radius: 50px;-fx-border-radius: 50px;");
       
        
        TextField username_tf = new TextField();//Text Field ==> username
        username_tf.setPromptText("Enter your username");//hint
        username_tf.setId("username");

        PasswordField password_pf = new PasswordField();//Password Field ==> password
        password_pf.setPromptText("Enter your password");//hint
        password_pf.setId("password");

        Label massage_label = new Label();

        Button btn_Login = new Button("login..");//button ==> login
        btn_Login.setOnAction(event -> {

            String username_Value = username_tf.getText().trim();
            String password_Value = password_pf.getText().trim();

            if (username_Value.length() == 0 || password_Value.length() == 0) {
                massage_label.setText("You must Enter data !");
            } else {
                String selectData = "select * from doctor where doctorName = '" + username_Value + "' and doctorPassword = '" + password_Value + "';";

                Integer doctor_Id = 0;
                String doctor_Name = "";
                String doctor_Password = "";
                try {
                    ResultSet database_result = statementSQL.executeQuery(selectData);

                    while (database_result.next()) {
                        doctor_Id = database_result.getInt("doctorId");
                        doctor_Name = database_result.getString("doctorName");
                        doctor_Password = database_result.getString("doctorPassword");

                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (doctor_Name.equalsIgnoreCase(username_Value) && doctor_Password.equalsIgnoreCase(password_Value)) {
                    try {
                        Screen2 screen = new Screen2(doctor_Id, doctor_Name, doctor_Password);
                        screen.show();
                        primaryStage.close();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(PatientManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(PatientManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    massage_label.setText("invalid data!!");
                }
            }
        });

        VBox items = new VBox(20, imgv,username_tf, password_pf, btn_Login, massage_label);//vbox
        items.setAlignment(Pos.CENTER);
        items.setPrefWidth(400);

        FlowPane parent_Layput = new FlowPane(items);//flowpane
        parent_Layput.setAlignment(Pos.CENTER);

        Scene scene = new Scene(parent_Layput, 500, 500);//scene
        scene.getStylesheets().add("File:src/patientmanagementsystem/CssLoginScreen.css");

        primaryStage.setScene(scene);//set scene

        primaryStage.show();//show scene

    }

}
