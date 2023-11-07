package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import Model.productData;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

import Model.database;
import Model.data;
public class cardProductController implements Initializable {
    @FXML
    private AnchorPane card_Form;

    @FXML
    private Button prod_addBtn;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Spinner<Integer> prod_spinner;

    private productData prodData;
    private Image image;
    private String prodID;
    private String type;
    private String prod_image;
    private String prod_date;
    private SpinnerValueFactory<Integer> spin;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Alert alert;
    public void setData(productData prodData) {
        this.prodData = prodData;

        prod_image = prodData.getImage();
        prod_date  = String.valueOf(prodData.getDate());
        type = prodData.getType();
        prodID = prodData.getProductId();
        prod_name.setText(prodData.getProductName());
        prod_price.setText(String.valueOf(prodData.getPrice())+ "00VND");
        String path = "File:" + prodData.getImage();
        image = new Image(path, 200, 105, false, true);
        prod_imageView.setImage(image);
        pr = prodData.getPrice();

    }
    private int qty;
    public void setQuantity(){
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }
    private double totalP;
    private double pr;
    //Them ?
    public void addBtn(){

        MainFormController mFrom = new MainFormController();
        mFrom.customerID();

        qty = prod_spinner.getValue(); // san pham con bao nhieu
        String check = "";
        String checkAvailable = "SELECT status FROM product WHERE  prod_id = '"
            +  prodID+ "'";

        connection = database.connectDB();
        try{
            int checkStck = 0;
            String checkStock = "SELECT stock FROM product WHERE prod_id = '"
                    + prodID + "' ";

            preparedStatement = connection.prepareStatement(checkStock);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                checkStck = resultSet.getInt("stock");
            }

            if (checkStck == 0) {
                String updateStock = "UPDATE product SET prod_name = '"
                        + prod_name.getText()+"', type = '"
                        + type +"', stock = 0 , price = " + pr
                        + ",  status = 'Unavailable', image = '"
                        + prod_image +"', date = '"
                        + prod_date+"' WHERE prod_id = '"
                        + prodID+ "' ";
                preparedStatement = connection.prepareStatement(updateStock);
                preparedStatement.executeUpdate();
            }

            preparedStatement = connection.prepareStatement(checkAvailable);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                check = resultSet.getString("status");
            }

            if(!check.equals("Có sẵn")|| qty ==0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Opp! Lỗi rồi!");
                alert.showAndWait();
            }else{

                if(checkStck < qty) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Sản phẩm hết hàng");
                    alert.showAndWait();
                }else{
                    prod_image = prod_image.replace("\\", "\\\\");
                    String insertData = "INSERT INTO customer "
                            +"(customer_id, prod_id, prod_name, type, quantity, price, date, image, em_username )"
                            + "VALUES(?,?,?,?,?,?,?,?,?)";

                    preparedStatement = connection.prepareStatement(insertData);
                    preparedStatement.setString(1, String.valueOf(data.cID));
                    preparedStatement.setString(2, prodID);
                    preparedStatement.setString(3, prod_name.getText());
                    preparedStatement.setString(4, type);
                    preparedStatement.setString(5, String.valueOf(qty));
                    totalP  = (qty * pr);
                    preparedStatement.setString(6, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    preparedStatement.setString(7, String.valueOf(sqlDate));
                    preparedStatement.setString(8, prod_image);
                    preparedStatement.setString(9, data.username);

                    preparedStatement.executeUpdate();

                    int upStock = checkStck - qty;


                    System.out.println("Date " + prod_date);
                    System.out.println("Image " + prod_image);

                    String updateStock = "UPDATE product SET prod_name = '"
                            + prod_name.getText()+"', type = '"
                            + type +"', stock =  "
                            + upStock + ", price = " + pr
                            + ",  status = '"
                            + check + "', image = '"
                            + prod_image +"', date = '"
                            + prod_date+"' WHERE prod_id = '"
                            + prodID+ "' ";

                    preparedStatement = connection.prepareStatement(updateStock);
                    preparedStatement.executeUpdate();

                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Thêm thành công");
                    alert.showAndWait();

                    mFrom.menuGetTotal();

                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setQuantity();
    }
}
