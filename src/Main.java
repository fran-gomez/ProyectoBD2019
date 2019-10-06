import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("BD-2019");
        LoginScreen ls = new LoginScreen(ventana);

        ventana.setBounds(0, 0, 450, 300);
        ventana.getContentPane().add(ls);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }
}
