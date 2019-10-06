import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
        login.addKeyListener(new EnterListener());
        password.addKeyListener(new EnterListener());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(username);
        this.add(password);
        this.add(login);
    }

    private void armarUIprincipal() {
        username.setVisible(false);
        password.setVisible(false);
        login.setVisible(false);

        new SelectScreen(ventana);
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
                if (conexion != null)
                    armarUIprincipal();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    private class EnterListener implements KeyListener {
        protected String srv = "localhost:3306";
        protected String bd = "vuelos";
        protected String uname = username.getText();
        protected String psswd = password.getText();
        protected Connection conexion;

        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == '\n') {
                srv = "localhost:3306";
                bd = "vuelos";
                uname = username.getText();
                psswd = password.getText();
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == '\n') {
                try {
                    conexion = DriverManager.getConnection("jdbc:mysql://" + srv + "/" + bd + "?serverTimezone=America/Argentina/Buenos_Aires", uname, psswd);
                    if (conexion != null)
                        armarUIprincipal();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
