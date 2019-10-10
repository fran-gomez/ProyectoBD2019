import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

// autentificacion de empleados y consultas sobre disponibilidad de vuelos
public class EmpleadoUI {

	private String nombre, apellido;
	private Connection connection;

	//Componentes gráficas
	private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelSobreTabbedPane;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField tfDestino;
    private javax.swing.JTextField tfFechaIda;
    private javax.swing.JTextField tfFechaVuelta;
    private javax.swing.JTextField tfOrigen;
    private javax.swing.JTabbedPane tabbedPane;

    public EmpleadoUI(JFrame ventana, Connection conexion, int legajo, String password) {
    	try {
    		this.connection = conexion;
    		Statement statement = conexion.createStatement();
    	    String loginSQL = "SELECT legajo, password, nombre, apellido FROM empleados WHERE legajo = " + legajo;
    	    ResultSet resultSetLogin = statement.executeQuery(loginSQL);
    	    
    	    if(resultSetLogin.next()){
    	   		String passwordVerdadero = resultSetLogin.getString("password");
    	    	String nombre = resultSetLogin.getString("nombre");
    		    String apellido = resultSetLogin.getString("apellido");

    		    if(!passwordVerdadero.equals(password)){
    	   			System.out.println("Password incorrecto");
    	   		} else {
    	   			this.nombre = nombre;
    	   			this.apellido = apellido;
    	   			initUI(ventana);
    	   		}
    		}else{
    			System.out.println("No se encontró el nombre de usuario");
    		}
    
        	resultSetLogin.close();
        	statement.close();
    	
    	} catch (SQLException e) {
    		System.out.println("ERRORRRRR");
    		e.printStackTrace();
    	}
    }


	//TODO almeacenar con md5 los passwords
	private String md5(String str){
		try{
			byte[] bytes = str.getBytes("UTF-8");

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hashedString = md.digest(bytes);
			return new String(hashedString);
		}catch(Exception e){
			System.out.println("error en md5");
			return null;
		}
	}

	private void initUI(JFrame ventana) {

		ventana.getContentPane().removeAll();
		ventana.repaint();

	    jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tfOrigen = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfFechaIda = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tfDestino = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tfFechaVuelta = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        panelSobreTabbedPane = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();

        ventana.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        ventana.setMaximumSize(new java.awt.Dimension(500, 500));
        ventana.setMinimumSize(new java.awt.Dimension(500, 500));
        ventana.setPreferredSize(new java.awt.Dimension(630, 600));
        ventana.setResizable(false);

        jPanel1.setAlignmentX(0.0F);
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jPanel9.setMaximumSize(new java.awt.Dimension(600, 40));
        jPanel9.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel9.setPreferredSize(new java.awt.Dimension(600, 40));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Bienvenido " + nombre + " " + apellido);
        jPanel9.add(jLabel3);

        jPanel1.add(jPanel9);

        jPanel2.setPreferredSize(new java.awt.Dimension(600, 50));
        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("  ciudad origen");
        jPanel6.add(jLabel6);

        tfOrigen.setColumns(10);
        jPanel6.add(tfOrigen);

        jPanel2.add(jPanel6);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("fecha ida (dd/mm/yyyy)");
        jPanel7.add(jLabel2);

        tfFechaIda.setColumns(14);
        jPanel7.add(tfFechaIda);

        jPanel2.add(jPanel7);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("ciudad destino");
        jPanel5.add(jLabel5);

        tfDestino.setColumns(10);
        jPanel5.add(tfDestino);

        jPanel2.add(jPanel5);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("fecha vuelta (opcional)");
        jPanel10.add(jLabel4);

        tfFechaVuelta.setColumns(14);
        jPanel10.add(tfFechaVuelta);

        jPanel2.add(jPanel10);

        jPanel1.add(jPanel2);

        jPanel8.setPreferredSize(new java.awt.Dimension(600, 30));

        btnBuscar.setText("Buscar");

        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });;
        jPanel8.add(btnBuscar);

        jPanel1.add(jPanel8);

        panelSobreTabbedPane.setAlignmentX(0.0F);
        panelSobreTabbedPane.setAlignmentY(0.0F);
        panelSobreTabbedPane.setPreferredSize(new java.awt.Dimension(600, 480));
        panelSobreTabbedPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        tabbedPane.setAlignmentX(0.0F);
        tabbedPane.setAlignmentY(0.0F);
        tabbedPane.setMinimumSize(new java.awt.Dimension(0, 0));
        tabbedPane.setPreferredSize(new java.awt.Dimension(600, 450));

        panelSobreTabbedPane.add(tabbedPane);

        jPanel1.add(panelSobreTabbedPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(ventana.getContentPane());
        ventana.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 947, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ventana.pack();
	}

	private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) { 
		tabbedPane.removeAll(); 

        String origen = tfOrigen.getText();
        String destino = tfDestino.getText();
        String fechaIda = tfFechaIda.getText();
        String fechaVuelta = tfFechaVuelta.getText();
 
        if(origen == null || origen.equals("")){
        	//TODO no ingresó origen
        	return;
        }
        
        if(destino == null || destino.equals("")){
        	//TODO no ingreso destino
        	return;
        }

        if(fechaIda == null || fechaIda.equals("")){
        	//TODO no ingresó fecha ida
        	return;
        }

		SimpleDateFormat formatInput = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatOuput = new SimpleDateFormat("yyyy-MM-dd");
		Date dtI = null, dtV = null;
		String formattedFechaIda = null, formattedFechaVuelta = null;

		try{
			dtI = formatInput.parse(fechaIda);
        	formattedFechaIda = formatOuput.format(formatInput.parse(fechaIda));
        		
        } catch (ParseException e){
        	e.printStackTrace();
        }

	    String consultaString = 
	    "SELECT vuelo, fecha, hora_sale, hora_llega, modelo, estimado, nom_sale, nom_llega" +
		" FROM vuelos_disponibles" +
		" WHERE dia = ? and fecha = ? and ciu_sale = ? and ciu_llega = ?" +
		" GROUP BY vuelo";
	    
	    // Buscando ida
	    try{
			PreparedStatement statement = connection.prepareStatement(consultaString);

			statement.setString(1, dayOfWeek(dtI));
			statement.setString(2, formattedFechaIda);
			statement.setString(3, origen.toLowerCase());
			statement.setString(4, destino.toLowerCase());

		    ResultSet resultSet = statement.executeQuery();
		    
			populateVuelos(resultSet, "ida");

	    	resultSet.close();
	    	statement.close();

    	} catch (ParseException e){
    		e.printStackTrace();
    	} catch (SQLException e1){
    		e1.printStackTrace();
    	}

		// Buscando vuelta
		if(fechaVuelta != null && !fechaVuelta.equals("")){
			try{
              	dtV = formatInput.parse(fechaVuelta);
        		formattedFechaVuelta = formatOuput.format(formatInput.parse(fechaVuelta));
            
        	} catch (ParseException e){
        		e.printStackTrace();
        	}
        	
		    try{
				PreparedStatement statement = connection.prepareStatement(consultaString);

				statement.setString(1, dayOfWeek(dtV));
				statement.setString(2, formattedFechaVuelta);
				statement.setString(3, destino.toLowerCase());
				statement.setString(4, origen.toLowerCase());

			    ResultSet resultSet = statement.executeQuery();
			    
				populateVuelos(resultSet, "vuelta");

		    	resultSet.close();
		    	statement.close();

	    	} catch (ParseException e){
	    		e.printStackTrace();
	    	} catch (SQLException e1){
	    		e1.printStackTrace();
	    	}
              	
        }
    }

    private void populateVuelos(ResultSet resultado, String tabName){
    	JPanel jPanel19 = new javax.swing.JPanel();
        JPanel panelEtiquetasTabla = new javax.swing.JPanel();
        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel7 = new javax.swing.JLabel();
        JLabel jLabel8 = new javax.swing.JLabel();
        JLabel jLabel9 = new javax.swing.JLabel();
        JLabel jLabel10 = new javax.swing.JLabel();
        JLabel jLabel11 = new javax.swing.JLabel();
        JLabel jLabel12 = new javax.swing.JLabel();
        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        JPanel containerVuelos = new javax.swing.JPanel();

    	panelEtiquetasTabla.setPreferredSize(new java.awt.Dimension(600, 30));
        panelEtiquetasTabla.setLayout(new java.awt.GridLayout(0, 8));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("N vuelo");
        jLabel1.setAlignmentY(0.0F);
        panelEtiquetasTabla.add(jLabel1);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Hora salida");
        jLabel7.setAlignmentY(0.0F);
        panelEtiquetasTabla.add(jLabel7);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Hora llegada");
        jLabel8.setAlignmentY(0.0F);
        panelEtiquetasTabla.add(jLabel8);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Tiempo Estimado");
        jLabel9.setAlignmentY(0.0F);
        panelEtiquetasTabla.add(jLabel9);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Modelo avion");
        jLabel10.setAlignmentY(0.0F);
        panelEtiquetasTabla.add(jLabel10);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Salida");
        jLabel11.setAlignmentY(0.0F);
        panelEtiquetasTabla.add(jLabel11);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Destino");
        jLabel12.setAlignmentY(0.0F);
        panelEtiquetasTabla.add(jLabel12);

        jPanel19.add(panelEtiquetasTabla);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 400));

        containerVuelos.setPreferredSize(new java.awt.Dimension(0, 0));

        jScrollPane1.setViewportView(containerVuelos);

        jPanel19.add(jScrollPane1);

        tabbedPane.addTab(tabName, jPanel19);
        tabbedPane.revalidate();
        tabbedPane.repaint();

    	try {
    		containerVuelos.removeAll();
	    	while (resultado.next()){
		    	containerVuelos.add(createVueloPanel(
		    		resultado.getString("vuelo"), 
		    		resultado.getString("fecha"),
		    		resultado.getString("hora_sale"),
		    		resultado.getString("hora_llega"),
		    		resultado.getString("estimado"),
		    		resultado.getString("modelo"),
		    		resultado.getString("nom_sale"),
		    		resultado.getString("nom_llega")));
	    	}

	    	containerVuelos.revalidate();
	    	containerVuelos.repaint();
    	} catch(SQLException e){
    		e.printStackTrace();
    	}
	}

    private JPanel createVueloPanel(String vuelo, String fecha, String hr_sale, String hr_llega, String estimado, String modelo, String salida, String destino){
    	JPanel vueloPanel = new JPanel();

    	vueloPanel.setPreferredSize(new java.awt.Dimension(600, 30));
        vueloPanel.setLayout(new java.awt.GridLayout(0, 8));

    	JLabel jLabel13 = new javax.swing.JLabel();
    	JLabel jLabel14 = new javax.swing.JLabel();
    	JLabel jLabel15 = new javax.swing.JLabel();
    	JLabel jLabel16 = new javax.swing.JLabel();
    	JLabel jLabel17 = new javax.swing.JLabel();
    	JLabel jLabel18 = new javax.swing.JLabel();
    	JLabel jLabel19 = new javax.swing.JLabel();
    	JButton jButton1 = new javax.swing.JButton();

    	vueloPanel.setPreferredSize(new java.awt.Dimension(600, 30));
        vueloPanel.setLayout(new java.awt.GridLayout(0, 8));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText(vuelo);
        vueloPanel.add(jLabel13);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText(hr_sale);
        vueloPanel.add(jLabel14);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText(hr_llega);
        vueloPanel.add(jLabel15);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText(estimado);
        vueloPanel.add(jLabel16);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText(modelo);
        vueloPanel.add(jLabel17);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText(salida);
        vueloPanel.add(jLabel18);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText(destino);
        vueloPanel.add(jLabel19);

        jButton1.setText("Ir");

        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectVuelo(vuelo, fecha);
            }
        });;

        vueloPanel.add(jButton1);
    	return vueloPanel;
    }

    private void selectVuelo(String vuelo, String fecha){
    	if(tabbedPane.getTitleAt(tabbedPane.getTabCount() - 1).indexOf("vuelo") >= 0) {
    		tabbedPane.remove(tabbedPane.getTabCount() - 1);
    	}

    	JPanel container = new JPanel();

    	JPanel panelEtiquetas = new JPanel();
    	panelEtiquetas.setPreferredSize(new java.awt.Dimension(600, 30));
        panelEtiquetas.setLayout(new java.awt.GridLayout(0, 3));

        JLabel etiquetaNombreClase = new JLabel();
        etiquetaNombreClase.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaNombreClase.setText("Clase");
        etiquetaNombreClase.setAlignmentY(0.0F);
        panelEtiquetas.add(etiquetaNombreClase);

		JLabel etiquetaPrecio = new JLabel();
        etiquetaPrecio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaPrecio.setText("Precio");
        etiquetaPrecio.setAlignmentY(0.0F);
        panelEtiquetas.add(etiquetaPrecio);

		JLabel etiquetaCantDisponibles = new JLabel();
        etiquetaCantDisponibles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaCantDisponibles.setText("Asientos Disponibles");
        etiquetaCantDisponibles.setAlignmentY(0.0F);
        panelEtiquetas.add(etiquetaCantDisponibles);

    	JScrollPane scrollPane = new JScrollPane();
    	scrollPane.setPreferredSize(new java.awt.Dimension(600, 400));
    	JPanel panelClases = new JPanel();
    	panelClases.setPreferredSize(new java.awt.Dimension(0,0));

		String consultaString = 
	    "SELECT " +
    	" brinda.clase, brinda.precio, brinda.cant_asientos + (brinda.cant_asientos * clases.porcentaje) - count(rvc.numero) as cant_disponibles" +
		" FROM instancias_vuelo as iv" +
        " JOIN salidas          on iv.vuelo = salidas.vuelo and iv.dia = salidas.dia" +
        " JOIN vuelos_programados as vp   on vp.numero = salidas.vuelo"+
        " JOIN brinda           on brinda.vuelo = salidas.vuelo and brinda.dia = salidas.dia"+
        " LEFT JOIN reserva_vuelo_clase as rvc on rvc.vuelo = salidas.vuelo and rvc.fecha_vuelo = iv.fecha and rvc.clase = brinda.clase"+
        " JOIN clases           on clases.nombre = brinda.clase"+
		" WHERE iv.vuelo = ? and iv.fecha = ?" +
		" group by salidas.vuelo, iv.fecha, brinda.clase";
	    
	   
	   	// Buscando ida
	    try{
			PreparedStatement statement = connection.prepareStatement(consultaString);

			statement.setString(1, vuelo);
			statement.setString(2, fecha);

		    ResultSet resultSet = statement.executeQuery();
		    
			while(resultSet.next()){
					panelClases.add(createPaneClase(resultSet.getString("clase"),
													resultSet.getString("precio"),
													resultSet.getString("cant_disponibles")));
			}

	    	resultSet.close();
	    	statement.close();

    	} catch (SQLException e1){
    		e1.printStackTrace();
    	}


    	scrollPane.setViewportView(panelClases);

    	container.add(panelEtiquetas);
    	container.add(scrollPane);

		tabbedPane.add("vuelo " + vuelo, container);
		tabbedPane.revalidate();
        tabbedPane.repaint();

        tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);

    }

    private JPanel createPaneClase(String nombre, String precio, String cant_disp){
    	JPanel panelClase = new JPanel();
    	JLabel labelNombre = new JLabel();
    	JLabel labelPrecio = new JLabel();
	    JLabel labelCantDisp = new JLabel();

	    panelClase.setLayout(new java.awt.GridLayout(0, 3));

		panelClase.setPreferredSize(new java.awt.Dimension(600, 60));
		panelClase.setBackground(new java.awt.Color(204,204,204));

	    labelNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNombre.setText(nombre);
        panelClase.add(labelNombre);

        labelPrecio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPrecio.setText(precio);
        labelPrecio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelClase.add(labelPrecio);

        labelCantDisp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCantDisp.setText(( (int) Float.parseFloat(cant_disp)) + "");
        panelClase.add(labelCantDisp);

		return panelClase;
    }

    private String dayOfWeek(Date date) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		switch(calendar.get(Calendar.DAY_OF_WEEK)){
			case 1:
				return "do";
			case 2:
				return "lu";
			case 3:
				return "ma";
			case 4:
				return "mi";
			case 5:
				return "ju";
			case 6:
				return "vi";
			default:
				return "sa";
		}
    }
}