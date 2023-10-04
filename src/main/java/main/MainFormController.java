package main;

import com.example.demo.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import connect.database;

public class MainFormController implements Initializable {

    @FXML
    private Button customersBtn;

    @FXML
    private Button dashBoardBtn;

    @FXML
    private Button inventoryBtn;

    @FXML
    private AnchorPane inventoryForm;

    @FXML
    private TextField inventory_IdProduct;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private ComboBox<?> inventory_chooseType;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private TableColumn<productData, String> inventory_col_ProductName;

    @FXML
    private TableColumn<productData, String> inventory_col_date;

    @FXML
    private TableColumn<productData, String> inventory_col_idProduct;

    @FXML
    private TableColumn<productData, String> inventory_col_price;

    @FXML
    private TableColumn<productData, String> inventory_col_status;

    @FXML
    private TableColumn<productData, String> inventory_col_stock;

    @FXML
    private TableColumn<productData, String> inventory_col_type;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private TextField inventory_price;

    @FXML
    private TextField inventory_productName;

    @FXML
    private ComboBox<?> inventory_status;

    @FXML
    private TextField inventory_stock;

    @FXML
    private TableView<productData> inventory_tableView;

    @FXML
    private Button inventory_updateBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button menuBtn;

    @FXML
    private Label username;

    private Alert alert;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;


    //Them san pham
    public void inventoryAddBtn() {
        if(inventory_IdProduct.getText().isEmpty()
            || inventory_productName.getText().isEmpty()
            || inventory_chooseType.getSelectionModel().getSelectedItem() == null
            || inventory_stock.getText().isEmpty()
            || inventory_price.getText().isEmpty()
            || inventory_status.getSelectionModel().getSelectedItem() == null
            || data.path == null){

        }
    }

    //tao hanh vi cho import btn first
    public void inventoryImportBtn(){
        
    }

    //Merge tat ca cac data lai
    public ObservableList<productData> inventoryDataList() {
        ObservableList<productData>listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";
        connection = database.connectDB();
        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            productData prodData ;
            while (resultSet.next()) {
                //cai bang product trong csdl
                prodData = new productData(resultSet.getInt("id"),
                        resultSet.getString("prod_id"),
                        resultSet.getString("prod_name"),
                        resultSet.getString("type"),
                        resultSet.getInt("stock"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status"),
                        resultSet.getString("image"),
                        resultSet.getDate("date"));

                listData.add(prodData);

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    //Show data tren table view
    private ObservableList<productData>inventoryListData;
    public void inventoryShowData() {
        inventoryListData = inventoryDataList();

        inventory_col_idProduct.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_col_ProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tableView.setItems(inventoryListData);
    }
    //
    private String[] typeList = {"Thức ăn ", "Đồ uống "};
    public void inventoryTypeList() {
        List<String> typeL = new ArrayList<>();
        for (String data : typeList) {
            typeL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventory_chooseType.setItems(listData);
    }
    //
    private  String[] statusList = {"Có sẵn", "Không có sẵn"};
    public void inventoryStatusList() {
        List<String> statusL = new ArrayList<>();
        for (String data : statusList) {
            statusL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(statusL);
        inventory_status.setItems(listData);
    }

    //Dang xuat
    public void logout(){
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Bạn chắc chắn muốn thoát? ");
            Optional<ButtonType> option = alert.showAndWait();
            if(option.get().equals(ButtonType.OK)){
                // to hide main form
                logoutBtn.getScene().getWindow().hide();

                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Sign.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setTitle("CAFE SHOP MANAGEMENT SYSTEM");
                stage.setScene(scene);
                stage.show();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //hien thi username cho Welcome
    public void displayUsername(){
        String user = data.username;
        user = user.substring(0,1).toUpperCase() + user.substring(1);
        username.setText(user);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();
    }
}
