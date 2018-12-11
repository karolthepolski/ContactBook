package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
	
	private final String url = "jdbc:postgresql://localhost:5432/Uppgift1";
    private final String user = "postgres";
    private final String password = "postgres1";
     
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
     
    public int getLogin(String username, String password) {
        String SQL = "SELECT id FROM users WHERE username = ? AND password = ?";        
        int id = 0;
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) { 
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            id = rs.getInt(1);                  
        } catch (SQLException ex) {
            //System.out.println(ex.getMessage());
        }
        return id;
    } 
    
    public int getUserId(String username) {
	   String SQL = "SELECT id FROM users WHERE username = ?";        
       int id = 0;
       try (Connection conn = getConnection();
           PreparedStatement pstmt = conn.prepareStatement(SQL)) { 
           pstmt.setString(1, username);
           ResultSet rs = pstmt.executeQuery();
           rs.next();
           id = rs.getInt(1);                  
       } catch (SQLException ex) {
           //System.out.println(ex.getMessage());
       }
       return id;
    }
    
    public boolean register(String username, String password) {
    	Boolean success = false;
    	if(getUserId(username)==0) {    	    		
        	String SQL = "INSERT INTO users (username, password) VALUES (?,?);";                
        	try (Connection conn = getConnection();
        		PreparedStatement pstmt = conn.prepareStatement(SQL)) { 
            	pstmt.setString(1, username);
            	pstmt.setString(2, password);
            	pstmt.executeUpdate();  
            	success = true;
        	} catch (SQLException ex) {
        	//System.out.println(ex.getMessage());
        	} 
    	}
		return success;
    }
    
    public void addContact(int user_id, String contact_firstname, String contact_lastname, String contact_number, String contact_epost) {	    		
       	String SQL = "INSERT INTO user_contacts (user_id, contact_firstname, contact_lastname, contact_number, contact_email) VALUES (?,?,?,?,?);";       
       	
       	try (Connection conn = getConnection();
       		PreparedStatement pstmt = conn.prepareStatement(SQL)) { 
           	pstmt.setInt(1, user_id);
           	pstmt.setString(2, contact_firstname);
           	pstmt.setString(3, contact_lastname);
           	pstmt.setString(4, contact_number);
           	pstmt.setString(5, contact_epost);
           	pstmt.executeUpdate();  
           	System.out.println(pstmt);
       	} catch (SQLException ex) {
       	//System.out.println(ex.getMessage());
       	} 
   }
   
    public ObservableList<Contact> getUserContacts(int user_id) {	  
	   
	   ObservableList<Contact> contacts = FXCollections.observableArrayList();
	   
	   String SQL = "SELECT * FROM user_contacts WHERE user_id = ?";        
	   
	   
       try (Connection conn = getConnection();
           PreparedStatement pstmt = conn.prepareStatement(SQL)) { 
           pstmt.setInt(1, user_id);
           ResultSet rs = pstmt.executeQuery();
           while (rs.next()) {   
        	   Contact nContact = new Contact(
        			   rs.getString("contact_firstname"), 
        			   rs.getString("contact_lastname"), 
        			   rs.getString("contact_email"), 
        			   rs.getString("contact_number"), 
        			   rs.getString("contact_id")); 
        	   contacts.add(nContact);
           }                                      
       } catch (SQLException ex) {
          // System.out.println(ex.getMessage());
       }
       return contacts; 
  }   

    public void deleteContact(int contact_id) {
	   String SQL = "DELETE FROM user_contacts WHERE contact_id = ?;";       
      	
      	try (Connection conn = getConnection();
      		PreparedStatement pstmt = conn.prepareStatement(SQL)) { 
          	pstmt.setInt(1, contact_id);
          	pstmt.executeUpdate();  
      	} catch (SQLException ex) {
      	//System.out.println(ex.getMessage());
      	} 
   }
}

