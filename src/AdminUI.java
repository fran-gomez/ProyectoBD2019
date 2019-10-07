import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

            tablas = new JList<>(obtenerTablas());
            campos = new JList<>();

            sentencias.addKeyListener(new EnterListener());
            tablas.addListSelectionListener(new SeleccionListener(this));

            this.setLayout(new BorderLayout());
            this.add(sentencias, BorderLayout.NORTH);
            this.add(resultados, BorderLayout.CENTER);
            this.add(tablas, BorderLayout.WEST);
            this.add(campos, BorderLayout.SOUTH);

            ventana.getContentPane().add(this);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private String[] obtenerTablas() throws SQLException {
        String tablas[] = new String[15];
        DatabaseMetaData db = conexion.getMetaData();
        ResultSet rs;

        rs = db.getTables(null, null, "%", null);
        int k = 0;
        while (rs.next()) {
            tablas[k++] = rs.getString(3);
        }
        rs.close();

        return tablas;
    }

    private void ejecutarSentencia(String sent) {
        try {
            String comando = sent.split(" ")[0];
            ResultSet resultado;
            Statement sentencia = conexion.createStatement();

            if (!sentencia.execute(sent));

                System.out.println(comando);
            // Si la sentencia retorna valor, lo recuperamos y luego cerramos
            if (!comando.toLowerCase().equals("insert") &&
                    !comando.toLowerCase().equals("delete") &&
                    !comando.toLowerCase().equals("update")) {
                resultado = sentencia.getResultSet();

                int k = 0;
                while (resultado.next()) {
                    System.out.println(resultado.getString(k++));
                }
            }

            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class EnterListener implements KeyListener {

        public void keyTyped(KeyEvent keyEvent) {}

        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == '\n')
                ejecutarSentencia(sentencias.getText());
        }

        public void keyReleased(KeyEvent keyEvent) {}
    }

    private class SeleccionListener implements ListSelectionListener {
        protected JPanel panel;
        public SeleccionListener(AdminUI adminUI) {
            panel = adminUI;
        }

        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            try {
                int i = 1;
                String tabla = tablas.getSelectedValue();
                String[] camposTabla;
                Statement sentencia = conexion.createStatement();
                ResultSet rs = sentencia.executeQuery("describe " + tabla);

                camposTabla = new String[21];
                while (rs.next()) {
                    camposTabla[i] = rs.getString("Field");
                    i++;
                }

                campos.removeSelectionInterval(0, i-1);
                campos.setListData(camposTabla);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
