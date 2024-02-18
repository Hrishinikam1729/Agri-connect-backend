package com.demo.Dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.demo.Model.Products;

@Repository
public class SEARCH_DAOImpl implements SEARCH_DAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Products> getProducts(String itemName) {
        String tableNameQuery = "SELECT GetTableName(?)"; // Query to get the table name(s)

        // Execute the query to get the table name(s)
        String tableNames = jdbcTemplate.queryForObject(tableNameQuery, String.class, itemName);

        // Split the table names into separate strings
        String[] tableNameArray = tableNames.split(",");

        // Initialize a list to store all products from all tables
        List<Products> productsList = new ArrayList<>();

        // Iterate over each table name and retrieve data from the corresponding table
        for (String tableName : tableNameArray) {
            // Construct the query to retrieve data from the table where the title matches
            String query = "SELECT * FROM " + tableName + " WHERE " + getColumnName(tableName) + " = ?";

            // Use RowMapper to map the query result to Products objects
            List<Products> productsFromTable = jdbcTemplate.query(query, new ProductsRowMapper(tableName), itemName);

            // Add the retrieved products to the main list
            productsList.addAll(productsFromTable);
        }

        // Now 'productsList' contains the mapped Products objects from all tables
        return productsList;
    }

    // Method to get the column name based on the table name
    private String getColumnName(String tableName) {
        switch (tableName) {
            case "artificial_plant":
                return "aptitle";
            case "pebbles_and_stones":
                return "pstitle";
            case "plant_food":
                return "pftitle";
            case "plants":
                return "ptitle";
            case "plastic_pots":
                return "ppotitle";
            case "pot_planters":
                return "pptitle";
            case "pump_spray":
                return "pstitle";
            case "seed_bulb":
                return "sbtitle";
            default:
                return ""; // Handle default case if needed
        }
    }

    // RowMapper implementation to map rows from the result set to Products objects
    private class ProductsRowMapper implements RowMapper<Products> {

        private String tableName;

        public ProductsRowMapper(String tableName) {
            this.tableName = tableName;
        }

        @Override
        public Products mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            Products product = new Products();

            // Adjust column names based on the selected table name
            switch (tableName) {
                case "artificial_plant":
                    product.setPid(rs.getString("apid"));
                    product.setPtitle(rs.getString("aptitle"));
                    product.setPprice(rs.getString("apprice"));
                    product.setPimage(rs.getString("apimages"));
                    break;
                case "pebbles_and_stones":
                    product.setPid(rs.getString("psid"));
                    product.setPtitle(rs.getString("pstitle"));
                    product.setPprice(rs.getString("psprice"));
                    product.setPimage(rs.getString("psimages"));
                    break;
                case "plant_food":
                    product.setPid(rs.getString("pfid"));
                    product.setPtitle(rs.getString("pftitle"));
                    product.setPprice(rs.getString("pfprice"));
                    product.setPimage(rs.getString("pfimages"));
                    break;
                case "plants":
                    product.setPid(rs.getString("pid"));
                    product.setPtitle(rs.getString("ptitle"));
                    product.setPprice(rs.getString("pprice"));
                    product.setPimage(rs.getString("pimages"));
                    break;
                case "plastic_pots":
                    product.setPid(rs.getString("ppoid"));
                    product.setPtitle(rs.getString("ppotitle"));
                    product.setPprice(rs.getString("ppodescription"));
                    product.setPimage(rs.getString("ppoimages"));
                    break;
                case "pot_planters":
                    product.setPid(rs.getString("ppid"));
                    product.setPtitle(rs.getString("pptitle"));
                    product.setPprice(rs.getString("ppprice"));
                    product.setPimage(rs.getString("ppimages"));
                    break;
                case "pump_spray":
                    product.setPid(rs.getString("psid"));
                    product.setPtitle(rs.getString("pstitle"));
                    product.setPprice(rs.getString("psprice"));
                    product.setPimage(rs.getString("psimages"));
                    break;
                case "seed_bulb":
                    product.setPid(rs.getString("sbid"));
                    product.setPtitle(rs.getString("sbtitle"));
                    product.setPprice(rs.getString("sbprice"));
                    product.setPimage(rs.getString("sbimages"));
                    break;
                // Add cases for other tables similarly
                default:
                    // Handle default case if needed
                    break;
            }

            return product;
        }
    }
}
