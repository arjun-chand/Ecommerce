package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class UserInterface {
    GridPane loginPage;
    Button signInButton;
    Label welcomeLabel;
    HBox headerBar;
    HBox footerBar;
    Customer loggedInCustomer;
    ProductList productList = new ProductList();
    VBox productPage;
    VBox body;
    Button placeOrderButton = new Button("Place Order");

    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        //giving size
        root.setPrefSize(600, 400);
        //adding pane
        //root.getChildren().add(loginPage);//method to add notes as children to Pane
        root.setTop(headerBar);
        //root.setCenter(loginPage);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        productPage = productList.getAllProducts();//show in the table
        root.setCenter(body);
        productPage = productList.getAllProducts();
        body.getChildren().add(productPage);
        root.setBottom(footerBar);
        return root;
    }

    public UserInterface(){

        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }

    private void createLoginPage(){

        Text userNameText = new Text("User Name   ");
        Text passwordText = new Text("Password  ");

        TextField userName = new TextField("arjun@gmail.com");
        userName.setPromptText("Type your user name here");
        PasswordField password = new PasswordField();
        password.setText("abc123");
        password.setPromptText("Type your password here");
        Label messageLabel = new Label("Hi");
        Button loginButton = new Button("Login");

        //now adding all things in Grid Pain acc to col, row
        loginPage = new GridPane();
        //loginPage.setStyle("-fx-background-color:grey;");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText,0,0);
        loginPage.add(userName,1,0);
        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);
        loginPage.add(messageLabel, 0, 2);
        loginPage.add(loginButton,1,2);


        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();//fetch text
                messageLabel.setText(name);
                String pass = password.getText();
                Login login = new Login();
                loggedInCustomer = login.customerLogin(name, pass);

                if (loggedInCustomer != null){//login Sucessfull
                    messageLabel.setText("Welcome "+loggedInCustomer.getName());
                    welcomeLabel.setText("Welcome "+loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear(); //remove everything from body after log in
                    body.getChildren().add(productPage);//add products after removing everything from body or home
                }
                else {
                    messageLabel.setText("Login Failed !! please give correct username and password");
                }
            }
        });



    }

    private void createHeaderBar(){
        Button homeButton =  new Button();
        homeButton.setPadding(new Insets(0));
        homeButton.setAlignment(Pos.CENTER_LEFT);
        Image image = new Image("D:\\projects\\Ecommerce\\src\\homelogo.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(60);
        homeButton.setGraphic(imageView);

        TextField searhBar = new TextField();
        searhBar.setPromptText("Search here");
        searhBar.setPrefWidth(280);

        Button searchButton = new Button("Search");

        signInButton = new Button("Sign In");
        welcomeLabel = new Label();

        Button cartButton = new Button("Cart");
        Image cartimage = new Image("D:\\projects\\Ecommerce\\src\\cartlogo.png");
        ImageView imageView1 = new ImageView();
        imageView1.setImage(cartimage);
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(20);
        cartButton.setGraphic(imageView1);

        Button orderButton = new Button("your Orders");
        headerBar = new HBox();
        //headerBar.setStyle("-fx-background-color:grey;");
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton, searhBar, searchButton, signInButton, cartButton, orderButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();//remove everything
                body.getChildren().add(loginPage);//put login page
                headerBar.getChildren().remove(signInButton);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductsInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);
                welcomeLabel.setVisible(false);
            }
        });
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //need list of product and customer
                if (itemsInCart == null){
                    //please select a product first to place order
                    showDialog("please add some products to the cart to place order");
                    return;
                }
                if(loggedInCustomer == null){
                    showDialog("Please login first to place order");
                    return;
                }
                int count = Order.placeMultipleOrder(loggedInCustomer, itemsInCart);
                if(count != 0){
                    showDialog("Order for "+ count +" products placed successfully!!");
                }
                else{
                    showDialog("Order failed!!");
                }

            }
        });
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if(loggedInCustomer == null && headerBar.getChildren().indexOf(signInButton) == -1){

                        headerBar.getChildren().add(signInButton);
                }
            }
        });
    }

    private void createFooterBar(){

        Button buyNowButton = new Button("Buy now");
        Button addToCartButton = new Button("Add to Cart");

        footerBar = new HBox();
        //headerBar.setStyle("-fx-background-color:grey;");
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product= productList.getSelectedProduct();
                if (product == null){
                    //please select a product first to place order
                    showDialog("please select a product first to place order");
                    return;
                }
                if(loggedInCustomer == null){
                    showDialog("Please login first to place order");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer, product);
                if(status == true){
                    showDialog("Order placed successfully!!");
                }
                else{
                    showDialog("Order failed!!");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product= productList.getSelectedProduct();
                if (product == null){
                    //please select a product first to place order
                    showDialog("please select a product first to add it to cart");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Selected Item has been added to Cart successfully");
            }
        });

    }
    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}
