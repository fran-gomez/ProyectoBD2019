import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;

public class LoginScreen extends JPanel {

    protected JTextField username;
    protected JPasswordField password;
    protected JButton login;

    protected JFrame ventana;

    public LoginScreen(JFrame ventana) {
        super();

        this.ventana = ventana;
        username = new JTextField();
        password = new JPasswordField();
        login = new JButton("Login");

        login.addActionListener(new LoginListener());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(username);
        this.add(password);
        this.add(login);
    }

    private void limpiarUI() {
        username.setVisible(false);
        password.setVisible(false);
        login.setVisible(false);
    }

    private boolean esNumero(String str) { 
        try {  
            Integer.parseInt(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
    }

    private class LoginListener implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {

            String srv = "localhost:3306";
            String bd = "vuelos";
            String uname = username.getText();
            String psswd = password.getText();
            Connection conexion;

            try {
                if (uname.equals("admin")) {
                    conexion = DriverManager.getConnection("jdbc:mysql://" + srv + "/" + bd + "?serverTimezone=America/Argentina/Buenos_Aires", uname, psswd);
                    
                    if (conexion != null){
                        limpiarUI();
                        if (uname.equals("admin")){
                            new AdminUI(ventana, conexion);
                       }
                    }

                } else if(esNumero(uname)){ // ha de ser un legajo de un empleado
                    conexion = DriverManager.getConnection("jdbc:mysql://" + srv + "/" + bd + "?serverTimezone=America/Argentina/Buenos_Aires", "empleado", "empleado");

                    if (conexion != null) {
                        new EmpleadoUI(ventana, conexion, Integer.parseInt(uname), psswd);
                    }

                }

            } catch (SQLInvalidAuthorizationSpecException e) {
                JOptionPane.showMessageDialog(null,
                        "Error en la contraseña de " + uname,
                        "BD-2019",
                        JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

}