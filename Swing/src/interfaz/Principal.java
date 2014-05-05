package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sistema.Ticker;

import javax.swing.border.TitledBorder;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.FlowLayout;

import javax.swing.JButton;

import mensajeria.Mensajero;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Principal extends JFrame {

	private Ticker ticker;

	private Mensajero mensajero;

	private JPanel contentPane;
	private JTextField pesoField;
	private JTextField alturaField;
	private JTextField diastoleField;
	private JTextField sistoleField;
	private JTextField pulsoField;

	/**
	 * Create the frame.
	 */
	public Principal() {
		ticker = new Ticker(this);
		ticker.start();
		
		mensajero = Mensajero.getInstance();
//		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "IMC", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.WEST);
		panel.setPreferredSize(new Dimension(285, 600));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Tensi\u00F3n",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new GridLayout(3, 2, 0, 0));
		panel_1.setPreferredSize(new Dimension(285, 600));

		JLabel lblDistole = new JLabel("Di\u00E1stole");
		panel_1.add(lblDistole);

		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_4.getLayout();
		flowLayout_2.setVgap(45);
		panel_1.add(panel_4);

		diastoleField = new JTextField();
		panel_4.add(diastoleField);
		diastoleField.setColumns(10);

		JLabel lblSstole = new JLabel("S\u00EDstole");
		panel_1.add(lblSstole);

		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_5.getLayout();
		flowLayout_3.setVgap(45);
		panel_1.add(panel_5);

		sistoleField = new JTextField();
		panel_5.add(sistoleField);
		sistoleField.setColumns(10);

		JLabel lblPulso = new JLabel("Pulso");
		panel_1.add(lblPulso);

		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_6.getLayout();
		flowLayout_4.setVgap(45);
		panel_1.add(panel_6);

		pulsoField = new JTextField();
		panel_6.add(pulsoField);
		pulsoField.setColumns(10);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblPeso = new JLabel("Peso");
		panel.add(lblPeso);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setVgap(70);
		panel.add(panel_2);

		pesoField = new JTextField();
		panel_2.add(pesoField);
		pesoField.setColumns(10);

		JLabel lblAltura = new JLabel("Altura");
		panel.add(lblAltura);

		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setVgap(70);
		panel.add(panel_3);

		alturaField = new JTextField();
		panel_3.add(alturaField);
		alturaField.setColumns(10);

		JPanel panel_7 = new JPanel();
		contentPane.add(panel_7, BorderLayout.SOUTH);
		panel_7.setLayout(new GridLayout(0, 2, 0, 0));

		JButton btnEnviarImc = new JButton("Enviar IMC");
		btnEnviarImc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int altura = -1;
					if(!alturaField.getText().equals("")) {
						altura = Integer.parseInt(alturaField.getText());
					}
					String res = mensajero.enviarIMC(
							Double.parseDouble(pesoField.getText()),
							altura);
					JOptionPane.showMessageDialog(null, res);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		panel_7.add(btnEnviarImc);

		JButton btnEnviarTensin = new JButton("Enviar Tensi\u00F3n");
		btnEnviarTensin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String res = mensajero.enviarTension(
							Integer.parseInt(diastoleField.getText()),
							Integer.parseInt(sistoleField.getText()),
							Integer.parseInt(pulsoField.getText()));
					JOptionPane.showMessageDialog(null, res);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		panel_7.add(btnEnviarTensin);
	}

	public void avisar() {
		JOptionPane.showMessageDialog(null,
				"Atenci�n: Debe tomarse las medidas.");
	}
}
