import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
	

*/

public class EmpleadoUI {

    public EmpleadoUI(JFrame ventana, Connection conexion, int legajo, String password) {
    	try {
    		Statement statement = conexion.createStatement();
    	    String loginSQL = "SELECT legajo, password, nombre, apellido FROM empleados WHERE legajo = " + legajo;
    	    ResultSet resultSetLogin = statement.executeQuery(loginSQL);
    	    
    	    if(resultSetLogin.next()){
    	   		String passwordVerdadero = resultSetLogin.getString("password");
    	    	String nombre = resultSetLogin.getString("nombre");
    		    String apellido = resultSetLogin.getString("apellido");

    		    if(!passwordVerdadero.equals(password)){
    	   			System.out.println("password incorrecto");
    	   		} else {
    	   			System.out.println("Bienvenido " + nombre + " " + apellido);
    	   		}
    		}else{
    			System.out.println("No se encontro el nombre de usuario");
    		}
    
        	resultSetLogin.close();
        	statement.close();
    	}catch (SQLException e){
    		System.out.println("ERRORRRRR");
    		e.printStackTrace();
    	}
    }
}
