import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminUI extends JPanel {

    protected Connection conexion;

    protected JTextField sentencias;
    protected JTable resultados;
    protected JList<String> tablas, campos;

    protected JFrame ventana;

    public AdminUI(JFrame ventana, Connection conexion) {

        try {
            this.conexion = conexion;
            this.ventana = ventana;

            sentencias = new JTextField();
            resultados = new JTable();

            tablas = new JList<>(armarTablas().split(" "));
            campos = new JList<>();

            this.setLayout(new BorderLayout());
            this.add(sentencias, BorderLayout.NORTH);
            this.add(resultados, BorderLayout.CENTER);
            this.add(tablas, BorderLayout.WEST);

            ventana.getContentPane().add(this);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private String armarTablas() throws SQLException {
        String tablas = "Test string";
        DatabaseMetaData db = conexion.getMetaData();
        ResultSet rs;

        rs = db.getTables(null, null, "%", null);
        while (rs.next())
            tablas = rs.getString(3);
        rs.close();

        return tablas;
    }
}
