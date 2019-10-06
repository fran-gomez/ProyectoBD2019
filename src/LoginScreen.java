import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    private class LoginListener implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {

            String srv = "localhost:3306";
            String bd = "vuelos";
            String uname = username.getText();
            String psswd = password.getText();
            Connection conexion;

            try {
                conexion = DriverManager.getConnection("jdbc:mysql://" + srv + "/" + bd + "?serverTimezone=America/Argentina/Buenos_Aires", uname, psswd);

                if (conexion != null) {
                    limpiarUI();
                    if (uname.equals("admin"))
                        new AdminUI(ventana, conexion);
                    else if (uname.equals("empleado"))
                        new EmpleadoUI(ventana, conexion);
                    else
                        System.out.println("Not yet implemented");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

}
