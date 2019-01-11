package r2ms.gui.modEu;
/**
 * 
 * @author Guillermo Pascual and Diego Rivera
 * 
 * Version: 20/12/2018
 *
 */
public class GuiEu {
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
				try {
					
					/**
					 * The main will only call an object of Window1 and show it to the user. The rest of the program will be developed
					 * from that first window with calls to the rest of the windows from its different methods.
					 */
					Window1 frame1 = new Window1();
					frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		    }
		});


	}
	
}
