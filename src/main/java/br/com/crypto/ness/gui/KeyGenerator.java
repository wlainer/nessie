package br.com.crypto.ness.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;
import br.com.crypto.ness.crypt.StringEncrypt;
import br.com.crypto.ness.utils.Utils;

public class KeyGenerator extends JFrame {

	private static final long serialVersionUID = 7674310773150458079L;
	private JTextField tfChave;
	private JTextField tfIV;
	private JTextField tfChaveGenerated;
	private JTextField tfIVGenerated;
	private JLabel lKey;
	private JLabel lIV;
	private JLabel lKeyGeneraed;
	private JLabel lIVGenerated;
	private JButton bGenerate;

	public KeyGenerator() {
		init();
	}

	private void init() {
		JPanel panel = new JPanel(new MigLayout());

		setAlwaysOnTop(true);
		setSize(380, 400);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setTitle("KeyGenerator");
		Utils.centerWindow(this);

		lKey = new JLabel("Key");
		panel.add(lKey);

		lIV = new JLabel("IV");
		panel.add(lIV, "wrap");

		tfChave = new JTextField(15);
		panel.add(tfChave);

		tfIV = new JTextField(15);
		panel.add(tfIV, "wrap");

		lKeyGeneraed = new JLabel("Key Gerada");
		panel.add(lKeyGeneraed);

		lIVGenerated = new JLabel("IV Gerado");
		panel.add(lIVGenerated, "wrap");

		tfChaveGenerated = new JTextField(15);
		panel.add(tfChaveGenerated);

		tfIVGenerated = new JTextField(15);
		panel.add(tfIVGenerated, "wrap");

		bGenerate = new JButton("Gerar Keys e IV");
		panel.add(bGenerate, "span, split 2, right");
		bGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateKeyAndIV();
			}
		});

		setContentPane(panel);
		pack();
	}

	private void generateKeyAndIV() {
		String key = tfChave.getText().trim();
		String iv = tfIV.getText().trim();

		String hashKey = key.isEmpty() ? "" : StringEncrypt.getSHA256FromKey(key);
		String hashIV = iv.isEmpty() ? "" : StringEncrypt.getSHA256FromKey(iv);

		tfChaveGenerated.setText(hashKey);
		tfIVGenerated.setText(hashIV);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			KeyGenerator keyGenerator = new KeyGenerator();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
