package com.example.ecommerce;

import java.sql.ResultSet;

public class Login {
    public Customer customerLogin(String userName, String password){
        String loginQuery = "select * from customer where email = '"+userName+"' and password = '"+password+"';";
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable(loginQuery);
        try {
            if(rs.next()){
                return new Customer(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("mobile")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Login login = new Login();
        Customer customer = login.customerLogin("arjun@gmail.com", "abc123");
        System.out.println("Welcome "+ customer.getName());
        //System.out.println(login.customerLogin("ajun@gmail.com", "abc123"));
    }
}
