package Controller;

import com.example.demo.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import Model.database;
import View.data;
import View.productData;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.xml.JasperPrintFactory;
import net.sf.jasperreports.view.JasperViewer;

public class MainFormController implements Initializable {

    @FXML
    private Button customersBtn;

    @FXML
    private Button dashBoardBtn;

    @FXML
    private AnchorPane dashboard_from;

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
    private Button menuBtn;

    @FXML
    private TextField menu_amount;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<productData, String> menu_col_price;

    @FXML
    private TableColumn<productData, String> menu_col_productName;

    @FXML
    private TableColumn<productData, String> menu_col_quantity;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_receiptBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private TableView<productData> menu_tableView;

    @FXML
    private Label menu_total;

    @FXML
    private Button logoutBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label username;

    private Alert alert;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private Image image;

    private ObservableList<productData>cardListData = FXCollections.observableArrayList();

    //Them san pham
    public void inventoryAddBtn() {
        if(inventory_IdProduct.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_chooseType.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền tất cả các khoảng trống");
            alert.showAndWait();
        }else{
            //Check id cua san pham coi no co bi trung hay khong
            String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '"
                    + inventory_IdProduct.getText() + "'";

            connection = database.connectDB();
            try{
                statement = connection.createStatement();
                resultSet = statement.executeQuery(checkProdID);
                if(resultSet.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(inventory_IdProduct.getText()+ " đã tồn tại !");
                    alert.showAndWait();
                }else{
                    // insert du lieu vo
                    String insertData = "INSERT INTO product "
                            + "(prod_id, prod_name, type,  stock, price, status, image, date)"
                            + "VALUE(?, ?, ?, ?, ?, ?, ?, ?)";
                    preparedStatement = connection.prepareStatement(insertData);
                    preparedStatement.setString(1, inventory_IdProduct.getText());
                    preparedStatement.setString(2, inventory_productName.getText());
                    preparedStatement.setString(3, (String) inventory_chooseType.getSelectionModel().getSelectedItem());
                    preparedStatement.setString(4, inventory_stock.getText());
                    preparedStatement.setString(5, inventory_price.getText());
                    preparedStatement.setString(6, (String) inventory_status.getSelectionModel().getSelectedItem());

                    String path = data.path;
                    path = path.replace("\\", "\\\\");

                    preparedStatement.setString(7, path);

                    //lay cai ngay hien tai
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    preparedStatement.setString(8, String.valueOf(sqlDate));
                    preparedStatement.executeUpdate();

                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Thêm sản phẩm thành công!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearBtn();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //update sản phẩm
    public void inventoryUpdateBtn() {
        if (inventory_IdProduct.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_chooseType.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null || data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền tất cả các khoảng trống");
            alert.showAndWait();
        }else{
            String path = data.path;
            path = path.replace("\\", "\\\\");

            String updateData = "UPDATE product SET "
                    + "prod_id = '" + inventory_IdProduct.getText() + "', prod_name = '"
                    + inventory_productName.getText() + "', type = '"
                    + inventory_chooseType.getSelectionModel().getSelectedItem() + "', stock = '"
                    + inventory_stock.getText() + "', price = '"
                    + inventory_price.getText() + "', status = '"
                    + inventory_status.getSelectionModel().getSelectedItem() + "', image = '"
                    + path + "', date = '"
                    + data.date + "' WHERE id = " + data.id;
            connection = database.connectDB();
            try{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc chắn muốn CẬP NHẬT Id_sp: " + inventory_IdProduct.getText() + "?" );
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    preparedStatement = connection.prepareStatement(updateData);
                    preparedStatement.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cập nhật sản phẩm thành công!");
                    alert.showAndWait();

                    // update lai table view
                    inventoryShowData();
                    //clear cac truong da chon
                    inventoryClearBtn();
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message ");
                    alert.setHeaderText(null);
                    alert.setContentText("Bi hủy bỏ.");
                    alert.showAndWait();
                }


            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inventoryClearBtn(){
        inventory_IdProduct.setText("");
        inventory_productName.setText("");
        inventory_chooseType.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        inventory_imageView.setImage(null);

    }

    //Xóa sản phẩm
    public void inventoryDeleteBtn(){
        if (data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền tất cả các khoảng trống");
            alert.showAndWait();
        }else{
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn XÓA sản phẩm Id_sp: " + inventory_IdProduct.getText() + "?" );
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){
                String deleteData = "DELETE FROM product WHERE id = " + data.id;
                try{
                    preparedStatement = connection.prepareStatement(deleteData);
                    preparedStatement.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Xóa sản phẩm thành công!");
                    alert.showAndWait();

                    // update lai table view
                    inventoryShowData();
                    //clear cac truong da chon
                    inventoryClearBtn();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Bi hủy bỏ.");
                alert.showAndWait();
            }


        }
    }

    //tao hanh vi cho import btn first
    public void inventoryImportBtn(){
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));
        File file = openFile.showOpenDialog(main_form.getScene().getWindow());
        if(file != null) {
            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 145, 143 , false, true);
            inventory_imageView.setImage(image);

        }
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

    //May cai thanh phan ma minh da them trong tableView bam vo la thay no hien ben duoi
    public void inventorySelectData(){
        productData prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();
        if((num - 1 ) < -1 ) return;
        inventory_IdProduct.setText(prodData.getProductId());
        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));

        data.path = prodData.getImage();
        String path = "File:" + prodData.getImage();
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();

        image = new Image(path, 145, 143 , false, true);
        inventory_imageView.setImage(image);
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

    //Lien quan toi phan card

    //Lay data tu menu
    public ObservableList<productData> menuGetData(){
        String sql = "SELECT * FROM product";
        ObservableList<productData> listData = FXCollections.observableArrayList();
        connection = database.connectDB();

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            productData prod;
            while (resultSet.next()){
                // cho nay la lay productData thu 2
                prod = new productData(resultSet.getInt("id"),
                        resultSet.getString("prod_id"),
                        resultSet.getString("prod_name"),
                        resultSet.getString("type"),
                        resultSet.getInt("stock"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"),
                        resultSet.getDate("date"));
                listData.add(prod);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return listData;
    }

    public void menuDisplayCard(){
        cardListData.clear();
        cardListData.addAll(menuGetData());
        int row = 0;
        int column =0;

        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for(int i = 0; i < cardListData.size(); i++){
            try{
                FXMLLoader load = new FXMLLoader(HelloApplication.class.getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
                cardC.setData(cardListData.get(i));

                if (column == 3) {
                    column =0;
                    row +=1;
                }

                menu_gridPane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));

            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public ObservableList<productData> menuGetOrder() {
        customerID();
        ObservableList<productData>listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer WHERE customer_id = "+ cID;

        connection = database.connectDB();
        try {

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            productData prod;
            while(resultSet.next()) {
                prod = new productData(resultSet.getInt("id"),
                        resultSet.getString("prod_id"),
                        resultSet.getString("prod_name"),
                        resultSet.getString("type"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image"),
                        resultSet.getDate("date"));

                listData.add(prod);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    //Hien thi cai bang menu da chon
    private ObservableList<productData> menuOrderListData;
    public void  menuShowOrderData() {
        menuOrderListData = menuGetOrder();

        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_tableView.setItems(menuOrderListData);
    }

    // gan menuSelectedOrder ben On mouse clicked -> cho phép liên kết một sự kiện với một phương thức xử lý xự kiện
    private int getid;
    public void menuSelectOrder(){
        productData prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();

        if((num - 1) < -1) return;
        //Lay id đặt hàng trước
        getid = prod.getId();

    }

    // nay la ban nho nho trong menu ke hien thi tong tien do do
    private double totalP ;
    //Lay gia tien cua san pham
    public void menuGetTotal(){
        //Lay cot price trong csdl -> Tinh tong tien hang
        customerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;
        connection = database.connectDB();

        try {
            preparedStatement = connection.prepareStatement(total);
            resultSet = preparedStatement.executeQuery();

            //hang gia tien
            if (resultSet.next()) {
                totalP = resultSet.getDouble("SUM(price)");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //hien thi menu nho ben menu
    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText(totalP + "00VND");
    }

    //gan on Action cho no
    private double amount;
    private double change;
    public void menuAmount() {
        menuGetTotal();
        if (menu_amount.getText().isEmpty() || totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid =)) ");
            alert.showAndWait();
        }else{
            amount = Double.parseDouble(menu_amount.getText());
            if (amount < totalP){
                menu_amount.setText("");
            }else{
                change = (amount - totalP);
                menu_change.setText(change + "00VND");
            }
        }
    }

    //button 99thanh toan
    public  void menuPayBtn(){
        if (totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn đơn hàng của bạn trước");
            alert.showAndWait();
        }else{
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username)"
                    + "VALUE (?,?,?,?)";
            connection = database.connectDB();

            try{

                if (amount == 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Opps! Lỗi rồi!");
                    alert.showAndWait();
                }else{
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation message");
                    alert.setHeaderText(null);
                    alert.setContentText("Bạn chắc chưa?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if(option.get().equals(ButtonType.OK)) {
                        customerID();
                        menuGetTotal();
                        preparedStatement = connection.prepareStatement(insertPay);
                        preparedStatement.setString(1, String.valueOf(cID));
                        preparedStatement.setString(2, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                        preparedStatement.setString(3, String.valueOf(sqlDate));
                        preparedStatement.setString(4, data.username);

                        preparedStatement.executeUpdate();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Thành công!!!");
                        alert.showAndWait();

                        menuShowOrderData();
                    }else{
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Hủy bỏ? ");
                        alert.showAndWait();

                    }

                }

            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void menuRemoveBtn(){
        if (getid ==0 ) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Chọn sản phẩm mà bạn muốn xóa ! ");
            alert.showAndWait();
        }else{
            String deleteData = "DELETE FROM customer WHERE id =" +getid;
            connection = database.connectDB();
            try{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc chắn xóa sản phẩm này?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    preparedStatement = connection.prepareStatement(deleteData);
                    preparedStatement.executeUpdate();

                }


                menuShowOrderData();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void menuReceiptBtn(){
        if(totalP ==0 || menu_amount.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please order first");
            alert.showAndWait();
        }else{
            HashMap map = new HashMap();
            map.put("getReceipt", (cID-1));

            try {
                JasperDesign jDesign = JRXmlLoader.load("D:\\JavaFX\\NLCS_JavaFx\\src\\main\\resources\\com\\example\\demo\\report.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connection);

                JasperViewer.viewReport(jPrint, false);

                menuRestart();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void menuRestart() {
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("");
        menu_amount.setText("");
        menu_change.setText("");

    }

    private int cID;
    public void customerID(){
        String sql = "SELECT MAX(customer_id) FROM customer";
        connection = database.connectDB();
        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                cID = resultSet.getInt("MAX(customer_id)");
            }
            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            preparedStatement = connection.prepareStatement(checkCID);
            resultSet = preparedStatement.executeQuery();
            int checkID =0;
            if(resultSet.next()){
                checkID = resultSet.getInt("MAX(customer_id)");
            }
            if(cID == 0) {
                cID +=1;
            }else if(cID == checkID){
                cID +=1;
            }
            data.cID = cID;


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //cac switch chuyen from
    public void switchFrom (ActionEvent event){
        if(event.getSource() == dashBoardBtn){
            dashboard_from.setVisible(true);
            inventoryForm.setVisible(false);
            menu_form.setVisible(false);
        }else if(event.getSource() == inventoryBtn ){
            dashboard_from.setVisible(false);
            inventoryForm.setVisible(true);
            menu_form.setVisible(false);

            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();
        } else if (event.getSource() == menuBtn) {
            dashboard_from.setVisible(false);
            inventoryForm.setVisible(false);
            menu_form.setVisible(true);

            menuDisplayCard();
            menuDisplayTotal();
            menuShowOrderData();
        }
    }

    //Dang xuat
    public void logout(){
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information Message");
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

        menuDisplayCard();
        menuGetOrder();
        menuDisplayTotal();
        menuShowOrderData();
    }
}
