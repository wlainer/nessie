package br.com.crypto.ness.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;
import br.com.crypto.ness.crypt.StringEncrypt;
import br.com.crypto.ness.entities.Config;
import br.com.crypto.ness.utils.ConfigProperties;
import br.com.crypto.ness.utils.Utils;

public class Main extends JFrame {

	private static final long serialVersionUID = 4154995944033203036L;

	private Config config = ConfigProperties.getInstance().getConfig();
	private static JTextArea taInput;
	private JTextArea taOutput;
	private JTextField tfChave;
	private JTextField tfIV;
	private JLabel lInput;
	private JButton bConfig;
	private JButton bTeclado;
	private JLabel lOutput;
	private JScrollPane sOutput;
	private JScrollPane sInput;
	private JLabel lChave;
	private JLabel lIV;
	private JButton bGerarKeys;
	private JButton bTecladoVirtual;
	private JButton bDecriptar;
	private JButton bEncript;
	public String texto;

	public Main() {
		init();
		initValues();
	}

	private void initValues() {
		if (config.isSalvarKey())
			tfChave.setText(config.getKey());

		if (config.isSalvarIV())
			tfIV.setText(config.getIV());
	}

	private void init() {
		registrarEventoFecharJanela();

		JPanel panel = new JPanel(new MigLayout());

		setSize(500, 400);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			setIconImage(ImageIO.read(getClass().getClassLoader().getResource("icon/dragon.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		setTitle("Nessie");
		Utils.centerWindow(this);

		lInput = new JLabel("Entrada");
		panel.add(lInput);
		
		bTeclado = new JButton();
		bTeclado.setPreferredSize(new Dimension(20, 20));
		bTeclado.setBorderPainted(false);
		bTeclado.setFocusPainted(false);
		bTeclado.setContentAreaFilled(false);
		bTeclado.setIcon(getIconTeclado());
		bTeclado.setToolTipText("Teclado Virtual");
		panel.add(bTeclado, "span, split 2, right");

		bTeclado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Settings settings = new Settings();
				Teclado teclado = new Teclado();
			}
		});

		bConfig = new JButton();
		bConfig.setPreferredSize(new Dimension(20, 20));
		bConfig.setBorderPainted(false);
		bConfig.setFocusPainted(false);
		bConfig.setContentAreaFilled(false);
		bConfig.setIcon(getIcon());
		bConfig.setToolTipText("Configurações");
		panel.add(bConfig);

		bConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings settings = new Settings();
			}
		});

		taInput = new JTextArea(6, 40);
		sInput = new JScrollPane(taInput);
		taInput.setWrapStyleWord(true);
		taInput.setLineWrap(true);
		panel.add(sInput, "spanx 2, growx, wrap");

		lOutput = new JLabel("Saida");
		panel.add(lOutput, "wrap");

		taOutput = new JTextArea(6, 40);
		sOutput = new JScrollPane(taOutput);
		taOutput.setWrapStyleWord(true);
		taOutput.setLineWrap(true);
		panel.add(sOutput, "spanx 2, growx, wrap");

		lChave = new JLabel("Chave");
		panel.add(lChave);
		lIV = new JLabel("IV");
		panel.add(lIV, "wrap");

		tfChave = new JTextField(20);
		panel.add(tfChave);

		tfIV = new JTextField(20);
		panel.add(tfIV, "wrap");

		bGerarKeys = new JButton("Gerar Keys");
		panel.add(bGerarKeys);
		bGerarKeys.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KeyGenerator keyGenerator = new KeyGenerator();
			}
		});
		
		bDecriptar = new JButton("Decriptar");
		bDecriptar.setMnemonic('D');
		panel.add(bDecriptar, "span, split 2, right");
		bDecriptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickBotaoDecriptar();
			}
		});

		bEncript = new JButton("Encriptar");
		bEncript.setMnemonic('E');
		panel.add(bEncript);
		bEncript.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickBotaoEncriptar();
			}
		});

		setContentPane(panel);
		pack();
	}
	
	private void registrarEventoFecharJanela() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (config.isSalvarKey())
					config.setKey(tfChave.getText());
				else
					config.setKey("");

				if (config.isSalvarIV())
					config.setIV(tfIV.getText());
				else
					config.setIV("");

				ConfigProperties.saveProperties();
			}
		});
	}

	private void onClickBotaoEncriptar() {
		String text = taInput.getText();
		taInput.setText("");
		String chave = tfChave.getText().trim();
		String iv = tfIV.getText().trim();

		String message = criptografar(text, chave, iv);
		Utils.putOnClipboard(message);
		taOutput.setText(message);

		CleanMessage cl = new CleanMessage(taOutput, Integer.valueOf(config.getTempoApagarMensagem()));
		Thread t = new Thread(cl);
		t.start();
	}

	private void onClickBotaoDecriptar() {
		String text = taInput.getText();
		if (text.isEmpty())
			text = Utils.readClipboard();
		String chave = tfChave.getText().trim();
		String iv = tfIV.getText().trim();

		String message = decriptografar(text, chave, iv);
		taOutput.setText(message);

		CleanMessage cl = new CleanMessage(taOutput, Integer.valueOf(config.getTempoApagarMensagem()));
		Thread t = new Thread(cl);
		t.start();
	}

	private String criptografar(String text, String chave, String iv) {
		try {
			String message = StringEncrypt.encrypt(chave, iv, text);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String decriptografar(String text, String chave, String iv) {
		try {
			String message = StringEncrypt.decrypt(chave, iv, text);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private ImageIcon getIcon() {
		try {
			Image img = ImageIO.read(getClass().getClassLoader().getResource("icon/gear.png"));
			Image newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(newimg);
		} catch (IOException ex) {
			return null;
		}
	}
	
	private ImageIcon getIconTeclado() {
		try {
			Image img = ImageIO.read(getClass().getClassLoader().getResource("icon/tecl.png"));
			Image newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(newimg);
		} catch (IOException ex) {
			return null;
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Main main = new Main();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static void copiaTextoDoTecladoParaInput(String texxto) {
		taInput.setText(texxto);
	}
	
	protected static void limpaTextoInput() {
		taInput.setText("");
	}

	class CleanMessage implements Runnable {

		private JTextArea taInput;
		private int miliseconds;

		public CleanMessage(JTextArea jTextArea, int mililseconds) {
			this.taInput = jTextArea;
			this.miliseconds = mililseconds;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(miliseconds);
				taInput.setText("");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
