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
import spark.Request;
import spark.Response;
import spark.QueryParamsMap;
import spark.Route;





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
          //output.add( "Read from DB: " + rs.getTimestamp("tick"));
          output.add( "Read from DB: " + rs.getString("firstname"));
          output.add( "Read from DB: " + rs.getString("lastname"));
          output.add( "Read from DB: " + rs.getString("instructiontype"));
          output.add( "Read from DB: " + rs.getString("zip"));
          output.add( "Read from DB: " + rs.getString("guitartype"));
          output.add( "Read from DB: " + rs.getString("genre"));
          output.add( "Read from DB: " + rs.getString("agerange"));
          output.add( "Read from DB: " + rs.getString("skill"));
          output.add( "Read from DB: " + rs.getString("focus"));
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
    

    
//***LOGIN FUNCTION ...  
    get("/login", (Request req, Response res) -> {

    String loginuser = req.queryParams("loginuser"); //maruby
    String loginpassword = req.queryParams("loginpassword");  //drummer1
    
    System.out.println("**login method**loginuser=" + loginuser); //maruby
    
      Connection connection = null;
      Map<String, Object> attributes = new HashMap<>();
      try {
        connection = DatabaseUrl.extract().getConnection();
        Statement stmt = connection.createStatement();
        
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM guitarists WHERE username ='" +loginuser+ "')" );
            
            //if (loginpassword.equals(rs.getString("password") ) ){

                    ArrayList<String> output = new ArrayList<>();
                    while (rs.next()) {
                      output.add( "First Name: " + rs.getString("firstname"));
                      output.add( "Last Name: " + rs.getString("lastname"));
                      output.add( "Email: " + rs.getString("email"));
                      output.add( "Password: " + rs.getString("password"));          
                      output.add( "Instruction Type: " + rs.getString("instructiontype"));
                      output.add( "Zip: " + rs.getString("zip"));
                      output.add( "Guitar Type: " + rs.getString("guitartype"));
                      output.add( "Musical Genre: " + rs.getString("genre"));
                      output.add( "Age Range: " + rs.getString("agerange"));
                      output.add( "Skill Level: " + rs.getString("skill"));
                      output.add( "Areas of Focus: " + rs.getString("focus"));
                    }
                    attributes.put("results", output);
                    return new ModelAndView(attributes, "db.ftl");
           // }

      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e);       
        return new ModelAndView(attributes, "error.ftl");
        
      } finally {
        if (connection != null) try{connection.close();} catch(SQLException e){}
      }
  //  return new ModelAndView(attributes, "db.ftl");  //will redirect to db.ftl and be blank
    }, new FreeMarkerEngine());    
    
    
  
    
    
    
    
    // Post method to add a user to the database.
      post("/saveuser", (Request req, Response res) -> {           
                
                String firstname = req.queryParams("firstname");        
            	String lastname = req.queryParams("lastname");
                String email = req.queryParams("email");
                String username = req.queryParams("username");
                String password = req.queryParams("password");
                String instructiontype  = req.queryParams("instructiontype");
                String zip = req.queryParams("zip");
                String guitartype = req.queryParams("guitartype");
                String genre = req.queryParams("genre");
                String agerange = req.queryParams("agerange");
                String skill = req.queryParams("skill");
            	String focus = req.queryParams("focus");
                
                System.out.println("*** firstname: " + firstname);
                
                  //make new db connection, create a new hashmap to be used later for results
                Connection connection = null;
                Map<String, Object> attributes = new HashMap<>();  
                
                
                try{
                    connection = DatabaseUrl.extract().getConnection();
                    
                    
                    Statement stmt = connection.createStatement();                   
                    stmt.execute("INSERT INTO guitarists"
                                +"(firstname,lastname,email,password,genre,focus,guitartype,agerange,skill,instructiontype,username,zip)"                                
                                +" VALUES( '" +firstname+ "','" +lastname+ "','" +email+ "','" +password+ "','" +genre+ "','" +focus+ "','"
                                +guitartype+ "','" +agerange+ "','" +skill+ "','" +instructiontype+ "','" +username+ "','" +zip+ "')"    );                             
                    

                    /*     Preppared Query that is no in use at the moment....
                    PreparedStatement pstmt = connection.prepareStatement(    "INSERT INTO guitarists"
                            + "(firstname,lastname,email,password,focus,genre,guitartype,instructiontype,skill,zip,agerange)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?)"   );
                               pstmt.setString(1, firstname);
                               pstmt.setString(2, lastname);
                               pstmt.setString(3, email);
                               pstmt.setString(4, password);
                               pstmt.setString(5, focus);
                               pstmt.setString(6, genre);
                               pstmt.setString(7, guitartype);
                               pstmt.setString(8, instructiontype);
                               pstmt.setString(9, skill);
                               pstmt.setString(10, zip);
                               pstmt.setString(11, agerange);
                        */
                    
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