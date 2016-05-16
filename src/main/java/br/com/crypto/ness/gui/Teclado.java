package br.com.crypto.ness.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import br.com.crypto.ness.utils.Utils;

public class Teclado extends JFrame implements KeyListener, MouseListener {

	private static final long serialVersionUID = -3546416797375918495L;
	String firstRow[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "+", "Cop" };// BackSpace
	// String secondRow[] = {"Q","W","E","R","T","Y","U","I","O","P","(",")","Limp"};
	// String thirdRow[] = {"A","S","D","F","G","H","J","K","L",":","\""};
	// String fourthRow[] = {"Z","X","C","V","B","N","M",",",".","?","   ^" };
	String secondRow[] = { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "(", ")", "Limp" };
	String thirdRow[] = { "a", "s", "d", "f", "g", "h", "j", "k", "l", "ç", "/", ":", "\"" };
	String fourthRow[] = { "z", "x", "c", "v", "b", "n", "m", ",", ".", "?", "   ^" };
	String fifthRow[] = { " ", "<", "\\/", ">" };
	String textoSaida = "";
	String noShift = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./";
	String specialChars = "~-+[]\\;',.?";
	JButton first[];
	JButton second[];
	JButton third[];
	JButton fourth[];
	JButton fifth[];
	Color cc = new JButton().getBackground();
	JTextArea text = new JTextArea();

	public void keyPressed(KeyEvent evt) {
	}

	public void keyReleased(KeyEvent evt) {
	}

	public static void main(String[] args) {
		new Teclado().setVisible(true);
	}

	public Teclado() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setPreferredSize(new Dimension(850, 200));
		this.setLocation(50, 50);
		try {
			setIconImage(ImageIO.read(getClass().getClassLoader().getResource("icon/tecl.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		initWidgets();
	}

	private void initWidgets() {
		text.setPreferredSize(new Dimension(800, 200));
		JLabel info = new JLabel("<html>Utilize o mouse...<br> Cop: copia para Entrada. <br>Limp: limpa o texto. <br> </html>");

		info.setFont(new Font("Verdana", Font.BOLD, 11));
		setLayout(new BorderLayout());
		JPanel jpNorth = new JPanel();
		JPanel jpCenter = new JPanel();
		JPanel jpKeyboard = new JPanel();
		JPanel jpNote = new JPanel();
		add(jpNorth, BorderLayout.NORTH);
		add(jpNote);
		add(jpCenter, BorderLayout.CENTER);
		add(jpKeyboard, BorderLayout.SOUTH);

		jpNorth.setLayout(new BorderLayout());
		jpNorth.add(info, BorderLayout.WEST);
		jpNorth.add(info, BorderLayout.SOUTH);

		jpCenter.setLayout(new BorderLayout());
		jpCenter.add(text, BorderLayout.WEST);
		jpCenter.add(text, BorderLayout.CENTER);

		jpKeyboard.setLayout(new GridLayout(5, 1));
		pack();

		first = new JButton[firstRow.length];
		JPanel p = new JPanel(new GridLayout(1, firstRow.length));
		for (int i = 0; i < firstRow.length; ++i) {
			JButton b = new JButton(firstRow[i]);
			b.setPreferredSize(new Dimension(50, 20));
			b.setFont(new Font("Verdana", Font.BOLD, 11)); // fonte da letra da tecla
			first[i] = b;
			p.add(first[i]);
		}
		jpKeyboard.add(p);

		second = new JButton[secondRow.length];
		p = new JPanel(new GridLayout(1, secondRow.length));
		for (int i = 0; i < secondRow.length; ++i) {
			second[i] = new JButton(secondRow[i]);
			p.add(second[i]);
		}

		jpKeyboard.add(p);
		third = new JButton[thirdRow.length];
		p = new JPanel(new GridLayout(1, thirdRow.length));

		for (int i = 0; i < thirdRow.length; ++i) {
			third[i] = new JButton(thirdRow[i]);
			p.add(third[i]);
		}

		jpKeyboard.add(p);
		fourth = new JButton[fourthRow.length];
		p = new JPanel(new GridLayout(1, fourthRow.length));

		for (int i = 0; i < fourthRow.length; ++i) {
			fourth[i] = new JButton(fourthRow[i]);
			p.add(fourth[i]);
			if (i == fourthRow.length - 2) {
				p.add(new JPanel());
			}
		}

		p.add(new JPanel());
		jpKeyboard.add(p);
		fifth = new JButton[fifthRow.length];
		p = new JPanel(new GridLayout(1, fifthRow.length));

		for (int i = 0; i < 1; ++i) {
			JPanel spacePanel = new JPanel();
			p.add(spacePanel);
		}

		for (int i = 0; i < fifthRow.length; ++i) {
			if (i == 0) {
				JButton b = new JButton(fifthRow[i]);
				fifth[i] = b;
				p.add(new JPanel());
				p.add(new JPanel());
				p.add(new JPanel());
				p.add(new JPanel());
				p.add(new JPanel());
				p.add(new JPanel());
				p.add(new JPanel());
				p.add(new JPanel());
				Container glassPane = (Container) getRootPane().getGlassPane();
				glassPane.setVisible(true);
				glassPane.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.weightx = 1.0;
				gbc.weighty = 1.0;
				gbc.fill = GridBagConstraints.NONE;
				gbc.insets = new Insets(getBounds().y - 350, 180, 0, 20);
				gbc.anchor = GridBagConstraints.SOUTHWEST;
				b.setPreferredSize(new Dimension(350, 20));
				glassPane.add(fifth[i], gbc);
			} else {
				fifth[i] = new JButton(fifthRow[i]);
				p.add(fifth[i]);
			}
			if (i == 0) {
				JPanel spacePanel = new JPanel();
				p.add(spacePanel);
			}
		}

		jpKeyboard.add(p);
		getContentPane().addKeyListener(this);
		getContentPane().addMouseListener(this);
		text.addKeyListener(this);
		text.addMouseListener(this);
		for (JButton b : first) {
			b.addKeyListener(this);
			b.addMouseListener(this);
		}
		for (JButton b : second) {
			b.addKeyListener(this);
			b.addMouseListener(this);
		}
		for (JButton b : third) {
			b.addKeyListener(this);
			b.addMouseListener(this);
		}
		for (JButton b : fourth) {
			b.addKeyListener(this);
			b.addMouseListener(this);
		}
		for (JButton b : fifth) {
			b.addKeyListener(this);
			b.addMouseListener(this);
		}

		setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

		String letra = "";

		try {
			JButton componenteDaTela = (JButton) e.getComponent();
			letra = componenteDaTela.getText();
		} catch (Exception e1) {
		}

		if (!"Limp".equalsIgnoreCase(letra) && !"Cop".equalsIgnoreCase(letra)) {
			text.setText(text.getText() + letra);
			textoSaida = textoSaida.concat(letra);
		} else if ("Limp".equalsIgnoreCase(letra)) {
			text.setText("");
			textoSaida = "";
			Main.limpaTextoInput();

		} else if ("Cop".equalsIgnoreCase(letra)) {
			Utils.putOnClipboard(textoSaida);
			text.setText("");
			Main.copiaTextoDoTecladoParaInput(textoSaida);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
