package interfaz;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import mensajeria.Mensajero;

public class Grafica extends JPanel {

	public final static String IMC="IMC";

	public final static String PRESION="PRESION";

	public final static int ALTO=295;
	
	public final static int ANCHO=285;
	
	private String tipo;


	public Grafica(String nTipo) 
	{
		tipo=nTipo;

		//		setBounds(100, 100, 600, 402);
		setVisible( true );

		//		JPanel contentPane = new JPanel();
		//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//		setContentPane(contentPane);
		////		contentPane.setLayout(new BorderLayout(0, 0));
		//		contentPane.setLayout(new GridLayout(1, 2));
		//
		//		JPanel panel = new JPanel();
		//		panel.setBorder(new TitledBorder(null, "Reportes IMC", TitledBorder.LEADING,
		//				TitledBorder.TOP, null, null));
		//		contentPane.add(panel);
		////		contentPane.add(panel, BorderLayout.WEST);
		////		panel.setPreferredSize(new Dimension(ANCHO, 600));
		//
		//		JPanel panel_1 = new JPanel();
		//		panel_1.setBorder(new TitledBorder(null, "Reportes Presion Arterial",
		//				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//		contentPane.add(panel_1);
		////		contentPane.add(panel_1, BorderLayout.EAST);
		////		panel_1.setLayout(new GridLayout(3, 2, 0, 0));
		////		panel_1.setPreferredSize(new Dimension(ANCHO, 600));
		////		paint(getGraphics());

	}

	public void paint( Graphics g )
	{
		super.paint( g ); // call superclass's paint method
		Graphics2D g2d = ( Graphics2D ) g; // cast g to Graphics2D

		//		g2d.setPaint( new GradientPaint( 5, 30, Color.BLUE, 35, 100,Color.YELLOW, true ) ); 
		//		g2d.fill( new Ellipse2D.Double( 5, 30, 65, 100 ) ); 
		//		g2d.setPaint( Color.RED ); 
		//		g2d.setStroke( new BasicStroke( 10.0f ) ); 
		//		g2d.draw( new Rectangle2D.Double( 80, 30, 65, 100 ) );

		//		float dashes[] = { 10 }; 

		g2d.setPaint( Color.GRAY ); 
		g2d.setStroke( new BasicStroke( 1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10) ); 

		for(int i=25; i<300; i=i+25)
		{
			g2d.draw( new Line2D.Double( 1, i, ANCHO, i ) );
		}

		g2d.setPaint( Color.BLACK ); 
		g2d.setStroke( new BasicStroke( 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10) ); 
		g2d.draw( new Line2D.Double( 1, ALTO, ANCHO, ALTO ) );
		g2d.draw( new Line2D.Double( 1, ALTO, 1, 5 ) );

		Mensajero mensajero=Mensajero.getInstance();

		if(tipo.equals(IMC))
		{
			try {

//				ArrayList<String> reportes=mensajero.recibirDatosIMC();
				
//				FUNCIONA GRAFICANDO VALORES
				ArrayList<String> reportes=new ArrayList<String>();
				reportes.add("90");
				reportes.add("80");
				reportes.add("56");
				reportes.add("90");
				reportes.add("45");
				
				
				double factor=ALTO/Double.parseDouble(reportes.get(0));
				double avance = ANCHO/(reportes.size()-1);
				double x=1;
				double y=ALTO;
				
				g2d.setPaint( Color.BLUE );
				g2d.setStroke( new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10) );
				for(int i=1; i<reportes.size(); i++)
				{
					g2d.draw( new Line2D.Double( x, y, x+avance, ALTO-Double.parseDouble(reportes.get(i))*factor ) );
					x=x+avance;
					y=ALTO-Double.parseDouble(reportes.get(i))*factor;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				g2d.setPaint( Color.RED );
				g2d.setStroke( new BasicStroke( 3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10) );
				g2d.draw( new Line2D.Double( ANCHO, ALTO, 1, 5 ) );
				g2d.draw( new Line2D.Double( ANCHO, 5, 1, ALTO ) );
			}
		}
		else
		{
			try {
				
//				ArrayList<ArrayList<String>> recibido=mensajero.recibirDatosTension();
//				ArrayList<String> siastole= recibido.get(0);
//				ArrayList<String> diastole= recibido.get(1);
//				ArrayList<String> pulsos= recibido.get(2);

				ArrayList<String> siastole= new ArrayList<String>();
				ArrayList<String> diastole= new ArrayList<String>();
				ArrayList<String> pulsos= new ArrayList<String>();
				
				siastole.add("87");
				diastole.add("92");
				pulsos.add("132");
				
				siastole.add("84");
				siastole.add("87");
				siastole.add("82");
				siastole.add("67");
				
				diastole.add("92");
				diastole.add("82");
				diastole.add("79");
				diastole.add("86");
				
				pulsos.add("99");
				pulsos.add("92");
				pulsos.add("83");
				pulsos.add("132");
				
				double factorSiastole=ALTO/Double.parseDouble(siastole.get(0));
				double factorDiastole=ALTO/Double.parseDouble(diastole.get(0));
				double factorPulso=ALTO/Double.parseDouble(pulsos.get(0));

				double avance = ANCHO/(siastole.size()-1);

				double x=1;
				double y=ALTO;
				
//				System.out.println("DEFINIO VARIABLES");
				
				g2d.setPaint( Color.RED );
				g2d.setStroke( new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10) );
				for(int i=1; i<siastole.size(); i++)
				{
					g2d.draw( new Line2D.Double( x, y, x+avance, ALTO-Double.parseDouble(siastole.get(i))*factorSiastole ) );
					x=x+avance;
					y=ALTO-Double.parseDouble(siastole.get(i))*factorSiastole;
				}

//				System.out.println("PASO SIASTOLE");
				
				avance = ANCHO/(diastole.size()-1);
				x=1;
				y=ALTO;

				g2d.setPaint( Color.ORANGE );
				g2d.setStroke( new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10) );
				for(int i=1; i<diastole.size(); i++)
				{
					g2d.draw( new Line2D.Double( x, y, x+avance, ALTO-Double.parseDouble(diastole.get(i))*factorDiastole ) );
					x=x+avance;
					y=ALTO-Double.parseDouble(diastole.get(i))*factorDiastole;
				}

//				System.out.println("PASO DIASTOLE");
				
				avance = ANCHO/(pulsos.size()-1);
				x=1;
				y=ALTO;

				g2d.setPaint( Color.MAGENTA );
				g2d.setStroke( new BasicStroke( 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10) );
				for(int i=1; i<pulsos.size(); i++)
				{
					g2d.draw( new Line2D.Double( x, y, x+avance, ALTO-Double.parseDouble(pulsos.get(i))*factorPulso ) );
					x=x+avance;
					y=ALTO-Double.parseDouble(pulsos.get(i))*factorPulso;
				}
				
//				System.out.println("PASO PULSOS");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				g2d.setPaint( Color.RED );
				g2d.setStroke( new BasicStroke( 3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10) );
				g2d.draw( new Line2D.Double( ANCHO, ALTO, 1, 5 ) );
				g2d.draw( new Line2D.Double( ANCHO, 5, 1, ALTO ) );
			}
		}
	}
}
