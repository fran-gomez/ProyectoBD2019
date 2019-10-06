import javax.swing.*;

public class SelectScreen extends JPanel {

    protected JButton showDB, enterData;
    protected JFrame ventana;

    public SelectScreen(JFrame ventana) {
        this.ventana = ventana;
        showDB = new JButton("Mostrar tablas");
        showDB.addActionListener(null);

        enterData = new JButton("Ingresar reserva");
        enterData.addActionListener(null);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(showDB);
        this.add(enterData);

        ventana.getContentPane().add(this);
    }
}
