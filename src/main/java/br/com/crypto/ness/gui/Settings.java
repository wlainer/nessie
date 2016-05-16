package br.com.crypto.ness.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;
import br.com.crypto.ness.entities.Config;
import br.com.crypto.ness.utils.ConfigProperties;
import br.com.crypto.ness.utils.Utils;

public class Settings extends JFrame {

	private static final long serialVersionUID = 292866822976057980L;
	private Config config = ConfigProperties.getInstance().getConfig();
	private JCheckBox cbSalvarKey;
	private JCheckBox cbSalvarIV;
	private JTextField tfMili;
	private JLabel lSalvarKey;
	private JLabel lSalvarIV;
	private JLabel lTempoMsg;
	private JLabel lMilisegundos;

	public Settings() {
		init();
		initValues();
	}

	private void initValues() {
		cbSalvarKey.setSelected(config.isSalvarKey());
		cbSalvarIV.setSelected(config.isSalvarIV());
		tfMili.setText(config.getTempoApagarMensagem());
	}

	private void init() {
		registrarEventoFecharJanela();

		JPanel panel = new JPanel(new MigLayout());

		setAlwaysOnTop(true);
		setSize(380, 400);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setTitle("Settings");
		Utils.centerWindow(this);

		cbSalvarKey = new JCheckBox();
		lSalvarKey = new JLabel("Salvar key?");
		panel.add(cbSalvarKey);
		panel.add(lSalvarKey, "wrap");

		cbSalvarIV = new JCheckBox();
		lSalvarIV = new JLabel("Salvar IV?");
		panel.add(cbSalvarIV);
		panel.add(lSalvarIV, "wrap");

		lTempoMsg = new JLabel("Apagar mensagem após?");
		lMilisegundos = new JLabel("milisegundos");
		tfMili = new JTextField(5);
		panel.add(lTempoMsg, "span2");
		panel.add(tfMili);
		panel.add(lMilisegundos);

		setContentPane(panel);
		pack();
	}

	private void registrarEventoFecharJanela() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				config.setSalvarIV(cbSalvarIV.isSelected());
				config.setSalvarKey(cbSalvarKey.isSelected());
				config.setTempoApagarMensagem(tfMili.getText().trim());
			}
		});
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Settings settings = new Settings();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
