import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.net.URI;
import java.net.URISyntaxException;
import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;
import com.heroku.sdk.jdbc.DatabaseUrl;
import com.google.gson.Gson;




public class Main {   
    
    

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    get("/hello", (req, res) -> "Hello World");

//    get("/", (request, response) -> {
//            Map<String, Object> attributes = new HashMap<>();
//            attributes.put("message", "Hello World!");
//
//            return new ModelAndView(attributes, "index.ftl");
//        }, new FreeMarkerEngine());
    


    get("/db", (req, res) -> {
      Connection connection = null;
      Map<String, Object> attributes = new HashMap<>();
      try {
        connection = DatabaseUrl.extract().getConnection();

        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS guitarists (tick timestamp)");
        //stmt.executeUpdate("INSERT INTO guitarists VALUES (now())");
        ResultSet rs = stmt.executeQuery("SELECT * FROM guitarists");

        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          output.add( "Read from DB: " + rs.getTimestamp("tick"));
        }

        attributes.put("results", output);
        return new ModelAndView(attributes, "db.ftl");
        
      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e);       
        return new ModelAndView(attributes, "error.ftl");
        
      } finally {
        if (connection != null) try{connection.close();} catch(SQLException e){}
      }

    }, new FreeMarkerEngine());
    

    //INSERT INTO guitarists (firstname, lastname) VALUES(firstname, lastname);    
    //post("/api", (req, res) -> userService.createUser(
    
    /*    
    post("/api", (req, res) -> userService.createUser(
        String firstName = req.queryParams("firstname");
        String lastname = req.queryParams("lastname");
  
        //todo : send to database
        Connection connection = null;
        Map<String, Object> attributes = new HashMap<>();
        try {
            connection = DatabaseUrl.extract().getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
            stmt.executeUpdate("INSERT firstname, lastname INTO mytable VALUES (" firstname + "," + lastname +")");
            ResultSet rs = stmt.executeQuery("SELECT * FROM mytable");
            ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
            output.add( "Read from DB: " + rs.getTimestamp("tick"));
        }
        attributes.put("results", output);
        return new ModelAndView(attributes, "db.ftl");
        }
        catch (Exception e) {
        attributes.put("message", "There was an error: " + e);
        return new ModelAndView(attributes, "error.ftl");
        }
        finally {
            if (connection != null) try{connection.close();} catch(SQLException e){}
        }
*/

                //String instructiontype  = req.queryParams("instructiontype");
                //String zip = req.queryParams("zip");
                //String guitartype = req.queryParams("guitartype");
                //String genre = req.queryParams("genre");
                //String agerange = req.queryParams("agerange");
                //String skill = req.queryParams("skill");
            	//String focus = req.queryParams("focus");


                
      post("/saveuser", (req, res) -> {
        
                String firstname = req.queryParams("firstname");        
            	String lastname = req.queryParams("lastname");
                System.out.print("firstname is");
                System.out.print("lastname is");
                  //make new db connection, create a new hashmap to be used later for results
                Connection connection = null;
                Map<String, Object> attributes = new HashMap<>();  
                
                
                try{
                    connection = DatabaseUrl.extract().getConnection();
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS guitarists (tick timestamp)");
                                        
                    stmt.executeUpdate("INSERT INTO guitarists (firstname) VALUES (" +firstname+ ")"); 
                    
                    
                    //PreparedStatement pstmt = connection.prepareStatement("INSERT INTO 'guitarists'(firstname,lastname)VALUE(?,?)");
                    //            pstmt.setString(1, firstname);
                    //            pstmt.setString(2, lastname);

    
                    //stmt.executeUpdate("INSERT INTO guitarists VALUES (now())");
                    //stmt.executeUpdate("INSERT INTO guitarists (firstname, lastname) VALUES ('" +firstname+ "','" +lastname+ "')");               
                    //stmt.executeUpdate("INSERT INTO guitarists (firstname, lastname) VALUES('Mike','Bloomfield')");
                    
                    
                      //now that data has been inserted, query for all records in this table and make an arraylist of objects
                    ResultSet rs = stmt.executeQuery("SELECT * FROM guitarists");
                    ArrayList<String> output = new ArrayList<>(); 
                        while (rs.next()) {
                        output.add("Read from DB: " + rs.getTimestamp("tick"));
                        }
                }
                
                catch (Exception e) {
                    attributes.put("message", "There was an error: " + e);
                    return new ModelAndView(attributes, "profile.html");
                }
                
                finally {
                    if (connection != null) try{connection.close();} catch(SQLException e){}
                }
                
                //res.redirect("db.ftl");
                return attributes;                
            });  
                
        
  }//end of main()
}//end Main Class