package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {
    private TableView<Product> productTable;
    public VBox createTable( ObservableList<Product> data){//creating data as well
        //need to put
        //1.colums
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn<>("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn(("PRICE"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));



        //add those things to table
        productTable = new TableView<>();//created table
        productTable.getColumns().addAll(id,name, price);//added columns
        productTable.setItems(data);//added data
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);//extra columns removed

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(productTable);
        return vbox;

    }



    //display all data from database
    public VBox getAllProducts(){
        ObservableList<Product> data = Product.getAllProducts();//fetch all products
        return createTable(data);
    }

    //give selected product from table
    public Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();
    }
    public VBox getProductsInCart(ObservableList<Product> data){
        return  createTable(data);
    }
}
