import javax.swing.*;

public class Main {
    public static void main(String[] args) {
    	 try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e){
        	e.printStackTrace();
        }

    	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	JFrame ventana = new JFrame("BD-2019");
        		LoginScreen ls = new LoginScreen(ventana);

 				ventana.setBounds(0, 0, 400, 400);
		        ventana.getContentPane().add(ls);
		        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		        ventana.setVisible(true);

        		ventana.setMaximumSize(new java.awt.Dimension(400, 400));
        		ventana.setMinimumSize(new java.awt.Dimension(400, 400));
        		ventana.setPreferredSize(new java.awt.Dimension(400, 400));
        		ventana.setResizable(false);
        		ventana.getContentPane().setLayout(new java.awt.FlowLayout());
            }
        });
        
    }
}
