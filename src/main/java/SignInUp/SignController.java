package SignInUp;

import com.example.demo.HelloApplication;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.data;
import connect.database;

public class SignController implements Initializable {
    @FXML
    private TextField fp_ans;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_continue;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_questionFrom;

    @FXML
    private TextField fp_user;

    @FXML
    private AnchorPane np_From;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePass;

    @FXML
    private PasswordField np_confirmPass;

    @FXML
    private PasswordField np_newPass;

    @FXML
    private AnchorPane si_From;

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private PasswordField si_passwd;

    @FXML
    private TextField si_user;

    @FXML
    private Button side_Already;

    @FXML
    private Button side_CreateAcc;

    @FXML
    private AnchorPane side_From;

    @FXML
    private AnchorPane su_From;

    @FXML
    private TextField su_ans;

    @FXML
    private TextField su_email;

    @FXML
    private PasswordField su_pass;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signUp;

    @FXML
    private TextField su_user;


    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Alert alert;

    //Kt ben form dang nhap

    //nut btn ben dang nhap
    public void loginBtn() {
        if(si_user.getText().isEmpty() || si_passwd.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText(null);
            alert.setContentText("Không đúng  Username hoặc Password");
        }else{
            String selctData = "SELECT username , password FROM employee WHERE username = ? and password = ? ";
            connection = database.connectDB();
            try {
                preparedStatement = connection.prepareStatement(selctData);
                preparedStatement.setString(1, si_user.getText());
                preparedStatement.setString(2, si_passwd.getText());

                resultSet = preparedStatement.executeQuery();
                // neu ma dk thanh cong thi dn ben nay okei
                if(resultSet.next()) {

                    // cho ma welcom + ten username
                    data.username = si_user.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Đăng nhập tài khoản thành công ! "); // dang nhap thanh cong
                    alert.showAndWait();

                    //dang nhap thanh cong bam login thi nhay vo trang quan ly nayyyy
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainFrom.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
                    stage.setTitle("Coffee Shop Management System");
                    stage.setScene(scene);
                    stage.show();

                    si_loginBtn.getScene().getWindow().hide();

                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error  Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Không đúng Username hoặc Password "); // dang ky thanh cong
                    alert.showAndWait();
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //quen mat khau
    public void forgotPassQuestionList(){
        List<String> listQ = new ArrayList<>();
        for(String data: questionList) {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);

    }

    //nut tiep tuc
    public void continueBtn() {
        if(fp_user.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_ans.getText().isEmpty() ){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ tất cả các khoảng trống ");
            alert.showAndWait();
        }else{
            String selectData = "SELECT username, question, answer FROM employee WHERE username =? AND question= ? AND answer = ? ";
            connection = database.connectDB();
            try{
                preparedStatement = connection.prepareStatement(selectData);
                preparedStatement.setString(1, fp_user.getText());
                preparedStatement.setString(2, (String) fp_question.getSelectionModel().getSelectedItem());
                preparedStatement.setString(3, fp_ans.getText());
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    np_From.setVisible(true);
                    fp_questionFrom.setVisible(false);
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Thông tin không chính xác");
                    alert.showAndWait();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //thay doi mat khau
    public void changePassBtn(){
        if(np_newPass.getText().isEmpty() || np_confirmPass.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền tất cả các khoảng trống");
            alert.showAndWait();
        }else{
            if(np_newPass.getText().equals(np_confirmPass.getText())){
                String getDate = "SELECT date FROM employee WHERE username = '"
                        + fp_user.getText() + "'";
                connection = database.connectDB();
                try{
                    preparedStatement = connection.prepareStatement(getDate);
                    resultSet = preparedStatement.executeQuery();
                    String date = "";

                    if(resultSet.next()){
                        date = resultSet.getString("date");
                    }
                    String updatePass = "UPDATE employee SET password = '" +
                            np_newPass.getText() + "', question = '"
                            + fp_question.getSelectionModel().getSelectedItem()+"',answer = '"
                            + fp_ans.getText()+"', date = '"
                            + date +"' WHERE username = '"
                            + fp_user.getText()+ "'";
                    preparedStatement = connection.prepareStatement(updatePass);
                    preparedStatement.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Đổi mật khẩu thành công!");
                    alert.showAndWait();

                    si_From.setVisible(true);
                    np_From.setVisible(false);

                    np_confirmPass.setText("");
                    np_newPass.setText("");
                    fp_question.getSelectionModel().clearSelection();
                    fp_ans.setText("");
                    fp_user.setText("");

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Không phù hợp");
                alert.showAndWait();
            }


        }
    }

    // 2 cai nut back
    public void backToLoginFrom() {
        si_From.setVisible(true);
        fp_questionFrom.setVisible(false);
    }

    public void backToQuestionFrom() {
        fp_questionFrom.setVisible(true);
        np_From.setVisible(false);
    }


    //Kt ben phan form DK
    //Kt xem email co hop le khong
    private boolean validatorEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-z0-9._]*@[a-zA-z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(su_email.getText());
        if(m.find() && m.group().equals(su_email.getText())){
            return true;
        }else{
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Validate Email");
            alert1.setHeaderText(null);
            alert1.setContentText("Vui lòng nhập lại Email");
            alert1.showAndWait();
        }
        return false;
    }


    //Kt nut dang ky coi co day du cac yeu to hay khong
    public void regBtn() {
        if(su_user.getText().isEmpty() || su_pass.getText().isEmpty()
                ||  su_question.getSelectionModel().getSelectedItem() == null
                || su_ans.getText().isEmpty() || su_email.getText().isEmpty()){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ tất cả các khoảng trống "); // VUi long dien tat ca cac dong trong
            alert.showAndWait();

        }else {
            String regData = "INSERT INTO employee(username, password, question, answer, email, date)"
                    + "VALUE(?, ?, ?, ?, ?, ?)";
            connection = database.connectDB();

            try{
                //Check xem co trung username khong
                String checkUsername = "SELECT username FROM employee WHERE username  = '"+su_user.getText() + "'";
                preparedStatement = connection.prepareStatement(checkUsername);
                resultSet = preparedStatement.executeQuery();

                if (validatorEmail() == false){
                    System.out.println("Heyy! please re-enter your email");

                }else if (resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_user.getText() + " tên đăng nhập đã tồn tại vui lòng chọn tên khác");
                    alert.showAndWait();
                }else if(su_pass.getText().length() < 8) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Vui lòng nhập ít nhất 8 ký tự "); // VUi long dien tat ca cac dong trong
                    alert.showAndWait();
                }else{
                    preparedStatement = connection.prepareStatement(regData);
                    preparedStatement.setString(1, su_user.getText());
                    preparedStatement.setString(2, su_pass.getText());
                    preparedStatement.setString(3, (String)su_question.getSelectionModel().getSelectedItem());
                    preparedStatement.setString(4, su_ans.getText());
                    preparedStatement.setString(5, su_email.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    preparedStatement.setString(6, String.valueOf(sqlDate));
                    preparedStatement.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Đăng ký tài khoản thành công ! "); // dang ky thanh cong
                    alert.showAndWait();

                    su_user.setText("");
                    su_pass.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_ans.setText("");

                    TranslateTransition slider = new TranslateTransition();

                    slider.setNode(side_From);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));

                    slider.setOnFinished((ActionEvent e) -> {
                        side_Already.setVisible(false);
                        side_CreateAcc.setVisible(true);
                    });
                    slider.play();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //Phan Form dang ky
    private String[] questionList = {"Nghệ sĩ yêu thích của bạn là ai?",  "Món ăn yêu thích của bạn là gì ? ", "Màu sắc nào bạn thích nhất "};

    public void reLquestionList() {
        List<String> ListQ = new ArrayList<>();
        for (String data: questionList) {
            ListQ.add(data);
        }
        ObservableList listData = FXCollections.observableList(ListQ);
        su_question.setItems(listData);
    }


    //switch chuyen qua chuyen lai
    //switch ben dang nhap
    public void switchForgotPass() {
        fp_questionFrom.setVisible(true);
        si_From.setVisible(false);
        //question quen mat khau
        forgotPassQuestionList();
    }


    //switch ben dang ky
    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if(event.getSource() == side_CreateAcc) {
            slider.setNode(side_From);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) ->{
                side_Already.setVisible(true);
                side_CreateAcc.setVisible(false);
                fp_questionFrom.setVisible(false);
                si_From.setVisible(true);
                np_From.setVisible(false);
                reLquestionList();
            });
            slider.play();
        } else if (event.getSource() == side_Already) {
            slider.setNode(side_From);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) ->{
                side_Already.setVisible(false);
                side_CreateAcc.setVisible(true);
                fp_questionFrom.setVisible(false);
                si_From.setVisible(true);
                np_From.setVisible(false);
            });
            slider.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
