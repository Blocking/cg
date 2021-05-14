package com.example.cg.parse;

import com.example.cg.bean.ModelDoc;

import java.sql.*;

/**
 * @author zhangxiaoyu
 * @date 2021/3/25
 */
public class TablePaser implements Parser{




    @Override
    public ModelDoc parse() {

        return null;
    }

    public static void main(String[] args) {

        Connection conn;
        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://117.78.0.112:33060/dim?" +
                            "user=root&password=root_Zxy");

            // Do something with the Connection
            final PreparedStatement preparedStatement = conn.prepareStatement("show tables ");
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

}
