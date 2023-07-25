

import java.sql.*;
public class H2Database {

// C:\\PROGRA~2\\H2\\bin\\h2-2.2.220.jar

    public static void main(String[] args) throws SQLException {
            try{Class.forName("org.h2.Driver");}
            catch(ClassNotFoundException e){e.printStackTrace();}
        Connection conn = null;

       try{ // Create a new H2 Database in memory
        String url = "jdbc:h2:mem:test3";
        String user = "admin";
        String password = "";
        conn = DriverManager.getConnection(url, user, password);

        // Create a PreparedStatement with a dynamic number of columns
        int numColumns = 4;
        String tableName = "MY_TABLE";
        String columnNames = "";
        for (int i = 1; i <= numColumns; i++) {
            columnNames += "A" + i + " VARCHAR(255), ";
        }
        columnNames = columnNames.substring(0, columnNames.length() - 2);
        PreparedStatement create = conn.prepareStatement("CREATE TABLE " + tableName + " (" + columnNames + ")");

        // // Set the values of the column names and data types
        // for (int i = 1; i <= numColumns; i++) {
        //     create.setString(i, "A" + i);
        //     create.setString(i + numColumns, "VARCHAR(255)");
        // }

        // Execute the PreparedStatement to create the table
        create.executeUpdate();

        // Create a PreparedStatement with a dynamic number of columns
        numColumns = 4;
        tableName = "MY_TABLE";
        columnNames = "";
        String columnValues = "";
        for (int i = 1; i <= numColumns; i++) {
            columnNames += "A" + i + ", ";
            columnValues += "?, ";
        }
        columnNames = columnNames.substring(0, columnNames.length() - 2);
        columnValues = columnValues.substring(0, columnValues.length() - 2);
        PreparedStatement insert = conn.prepareStatement("INSERT INTO " + tableName + " (" + columnNames + ") VALUES (" + columnValues + ")");
        
        long startIn = System.currentTimeMillis();
        // Insert 10 rows of data
        int j = 0;
        for (j = 0; j < 100000; j++) {
            // Set the values of the column values
            for (int i = 1; i <= numColumns; i++) {
                insert.setString(i, "Value " + (i+j));
            }

            // Execute the PreparedStatement to insert the data
            insert.executeUpdate();
        }
        long endIn = System.currentTimeMillis();
        
        PreparedStatement select = conn.prepareStatement("SELECT * FROM " + tableName);

        ResultSet results = select.executeQuery();
        
        ///// Print out tuples /////
        // while (results.next()) {
        //     for (int i = 1; i <= numColumns; i++) {
        //         System.out.print(results.getString(i) + " ");
        //     }
        //     System.out.println();
        // }System.out.println("Time print(" + j + "): " + (endPrint - endIn) + "ms");
        // long endPrint = System.currentTimeMillis();
        //System.out.println("Time print(" + j + "): " + (endPrint - endIn) + "ms");
        
        // // Get the metadata for the database connection
        // DatabaseMetaData metaData = conn.getMetaData();

        // // Get the names of all tables in the database
        // ResultSet tables = metaData.getTables(null, null, "%", null);

        // // Iterate over the tables and print their names and number of entries
        // while (tables.next()) {
        //     String name = tables.getString("TABLE_NAME");
        //     ResultSet count = conn.createStatement().executeQuery("SELECT COUNT(*) FROM " + name);
        //     count.next();
        //     int numEntries = count.getInt(1);
        //     System.out.println(name + " (" + numEntries + " entries)");
        // }
        
        System.out.println("Time insert(" + j + "): " + (endIn - startIn) + "ms");
        
        
       }catch(SQLException e){e.printStackTrace();}

        // Close the connection
        finally { 
            if (conn != null) {conn.close();}}
    }

}