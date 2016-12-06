package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

import util.data;
import control.ed_ec_control;

/**
 * Programa para editor de texto
 * 
 * @author Miliel R de Lima
 * @since 13/02/2015
 * @version 1.0
 * 
 */

public class ed_ec extends JFrame {

	private static final long	serialVersionUID	= 1L;

	// variaveis de instancia
	private JMenuBar			jBMenu;
	private JMenu				arquivos;
	private JMenu				exibir;
	private JMenu				ferramentas;
	private JMenu				ajuda;
	private JMenuItem			abrir;
	private JMenuItem			novo;
	private JMenuItem			salvarComo;
	private JMenuItem			sair;

	private JButton				bAbrir;
	private JButton				bNovo;
	private JButton				bSalvar;
	private JLabel				fonte;
	private JLabel				family;
	private JComboBox<String>	cFamily;
	private JComboBox<String>	cFonte;
	private JButton				fDireita;
	private JButton				fEsquerda;
	private JButton				fCentral;
	private JButton				fJustify;
	private JButton				fNegrito;
	private JButton				fItalico;
	private JButton				fSubrinhado;

	private JTextPane			txt;
	private JScrollPane			barraRolagem;
	private JPanel				jPanel;
	private JPanel				jPanel2;
	private String				data;
	private String				hora;
	private String				novoNome;

	private java.net.URL		caminhoImagem;
	private Image				iconeTitulo;

	private StyledDocument		doc;
	Action						action;
	Style						style;

	ed_ec_control				control;
	data						mostra_data;

	String						txtOld;
	int							n;
	int							s;
	int							i;

	// Funcao construtora da classe
	public ed_ec() {

		// instancializacao
		n = 0;
		s = 0;
		i = 0;

		control = new ed_ec_control();
		mostra_data = new data();

		// Inicializa a data e hora
		mostra_data.le_data();
		mostra_data.le_hora();

		// inicia os componentes da view
		initComponents();
	}

	public void initComponents() {

		// Variaveis
		int largura;
		int altura;

		// tamanho da tela
		Dimension tela;

		// Formatacao

		// instancializacao

		// MENUS
		jBMenu = new JMenuBar();
		arquivos = new JMenu();
		exibir = new JMenu();
		ferramentas = new JMenu();
		ajuda = new JMenu();
		abrir = new JMenuItem();
		novo = new JMenuItem();
		salvarComo = new JMenuItem();
		sair = new JMenuItem();

		// BUTTONS
		bAbrir = new JButton();
		bNovo = new JButton();
		bSalvar = new JButton();

		fDireita = new JButton();
		fEsquerda = new JButton();
		fCentral = new JButton();
		fJustify = new JButton();

		// Criando e adicionando acao para NEGRITO
		action = new StyledEditorKit.BoldAction();
		action.putValue(Action.NAME, "");
		fNegrito = new JButton(action);

		// Criando e adicionando acao para SUBRINHADO
		action = new StyledEditorKit.UnderlineAction();
		action.putValue(Action.NAME, "");
		fSubrinhado = new JButton(action);

		// Criando e adicionando acao para ITALICO
		action = new StyledEditorKit.ItalicAction();
		action.putValue(Action.NAME, "");
		fItalico = new JButton(action);

		// FORMATACOES do txt
		fonte = new JLabel();
		family = new JLabel();
		cFamily = new JComboBox<String>();
		cFonte = new JComboBox<String>();

		// PAINEIS
		txt = new JTextPane();
		jPanel = new JPanel();
		jPanel2 = new JPanel();

		// pega a largura e altura da tela.
		tela = Toolkit.getDefaultToolkit().getScreenSize();
		largura = tela.width;
		altura = tela.height;

		// Modifica o icon
		caminhoImagem = this.getClass().getClassLoader().getResource("ico.png");
		iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoImagem);

		// cria tela padrao
		setIconImage(iconeTitulo);
		setSize(largura, altura);
		setTitle("Editor/Escritor de texto");
		setVisible(true);
		setExtendedState(MAXIMIZED_BOTH);

		// Cria o closing e gera a validacao do mesmo
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				formWindowClosing(evt);
			}
		});

		// Define o Frame
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(176, 194, 236));

		// Adiciona Componentes no Frame
		getContentPane().add(jPanel);
		getContentPane().add(jPanel2);
		getContentPane().add(jBMenu);

		// Define a Barra de menu
		// Adiciona componentes na barra de Menu
		jBMenu.add(arquivos);
		jBMenu.add(exibir);
		jBMenu.add(ferramentas);
		jBMenu.add(ajuda);

		setJMenuBar(jBMenu);

		arquivos.setText("Arquivos");
		exibir.setText("Exibir");
		ferramentas.setText("Ferramentas");
		ajuda.setText("Ajuda");

		// Define o menu Exibir
		// Adiciona componentes
		arquivos.setLayout(null);

		arquivos.add(abrir);
		arquivos.add(novo);
		arquivos.add(salvarComo);
		arquivos.add(sair);

		abrir.setText("Abrir");
		abrir.setIcon(new javax.swing.ImageIcon("src/abrir.png"));
		abrir.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					abrir(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		abrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D,
				java.awt.event.InputEvent.CTRL_MASK));

		novo.setText("Novo");
		novo.setIcon(new javax.swing.ImageIcon("src/novo.png"));
		novo.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				novo(evt);
			}
		});

		novo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,
				java.awt.event.InputEvent.CTRL_MASK));

		salvarComo.setText("Salvar como");
		salvarComo.setIcon(new javax.swing.ImageIcon("src/salvar.png"));
		salvarComo.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					salvarComo(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		salvarComo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
				java.awt.event.InputEvent.CTRL_MASK));

		sair.setText("Sair");
		sair.setIcon(new javax.swing.ImageIcon("src/close.png"));
		sair.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sair(evt);
			}
		});

		// Define o painel do menu
		jPanel2.setLayout(null);
		jPanel2.setBounds(0, 0, largura, 50);
		jPanel2.setBackground(new Color(210, 222, 223));

		// Adiciona componentes no painel 2
		jPanel2.add(bAbrir);
		jPanel2.add(bNovo);
		jPanel2.add(bSalvar);
		jPanel2.add(fDireita);
		jPanel2.add(fEsquerda);
		jPanel2.add(fCentral);
		jPanel2.add(fJustify);
		jPanel2.add(fNegrito);
		jPanel2.add(fItalico);
		jPanel2.add(fSubrinhado);
		jPanel2.add(family);
		jPanel2.add(fonte);
		jPanel2.add(cFamily);
		jPanel2.add(cFonte);

		// Posiciona os componentes do painel 2
		bNovo.setToolTipText("Novo - CTRL+O");
		bNovo.setIcon(new javax.swing.ImageIcon("src/novo.png"));
		bNovo.setBackground(new Color(210, 222, 223));
		bNovo.setBounds(5, 20, 28, 25);
		bNovo.setBorder(null);
		bNovo.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				novo(evt);
			}
		});

		bSalvar.setToolTipText("Salvar - CTRL+S");
		bSalvar.setIcon(new javax.swing.ImageIcon("src/salvar.png"));
		bSalvar.setBackground(new Color(210, 222, 223));
		bSalvar.setBounds(34, 20, 28, 25);
		bSalvar.setBorder(null);
		bSalvar.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					salvarComo(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		bAbrir.setToolTipText("Abrir - CTRL+D");
		bAbrir.setIcon(new javax.swing.ImageIcon("src/abrir.png"));
		bAbrir.setBackground(new Color(210, 222, 223));
		bAbrir.setBounds(63, 20, 28, 25);
		bAbrir.setBorder(null);
		bAbrir.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					abrir(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		family.setText("Fonte Family");
		family.setBounds(100, 0, 200, 25);
		cFamily.setModel(new javax.swing.DefaultComboBoxModel<String>(
				new String[] { "Agency FB", "Algerian", "Book Antiqua Bold", "Book Antiqua Bold Italic",
						"Book Antiqua Italic", "Arial", "Arial Narrow", "Arial Narrow Bold",
						"Arial Narrow Bold Italic", "Arial Narrow Italic", "Arial Unicode MS", "Arial Rounded MT Bold",
						"Baskerville Old Face", "Bauhaus 93", "Bell MT", "Bell MT Bold", "Bell MT Italic",
						"Bernard MT Condensed", "Book Antiqua", "Bodoque MT Bold", "Bodoque MT Bold Italic",
						"Bodoque MT Italic", "Bodoque MT", "Bodoque MT Condensed Bold",
						"Bodoque MT Condensed Bold Italic", "Bodoque MT Condensed Italic", "Bodoque MT Condensed",
						"Bodoque MT Italic", "Bodoque MT Pôster Cumprisse", "Bodoque MT", "Bookman Old",
						"Bookman Old Bold", "Bookman Old Bold Italic", "Bookman Old Italic", "Bradley Anda ITC",
						"Britânica Bold (Troai )", "Berlin Sanas FB Bold", "Berlin Sanas FB De mi Bold",
						"Berlin Sanas FB", "Broadway", "Brush Script MT Italic", "Bookshelf Symbol 7", "Calibri",
						"Calibri Bold", "Calibri Italic", "Calibri Bold Italic", "Californian FB Bold",
						"Californian FB Italic", "Californian FB", "Calisto MT", "Calisto MT Bold",
						"Calisto MT Bold Italic", "Calisto MT Italic", "Cambria & Cambria Math", "Cambria Bold",
						"Cambria Italic", "Cambria Bold Italic", "Candara", "Candara Bold", "Candara Italic",
						"Candara Bold Italic", "Castellar", "Century Schoolbook", "Centaur", "Century", "Chiller",
						"Colonna MT", "Consolas", "Consolas Bold", "Consolas Italic", "Consolas Bold Italic",
						"Contantia", "Contantia Bold", "Contantia Italic", "Contantia Bold Italic", "Cooper",
						"Copperplate Gothic Bold", "Copperplate Gothic Light", "Corbel", "Corbel Bold",
						"Corbel Italic", "Corbel Bold Italic", "Curlz MT", "Elephant", "Elephant Italic", "Engravers",
						"Eras Bold ITC", "Eras De mi ITC", "Eras Light ITC", "Eras Medium ITC", "Felix Titling",
						"Forte", "Franklin Gothic Book", "Franklin Gothic Book Italic", "Franklin Gothic De mi",
						"Franklin Gothic De mi Cond", "Franklin Gothic De mi Italic", "Franklin Gothic Heavy",
						"Franklin Gothic Heavy Italic", "Franklin Gothic Medium Cond", "Freestyle Script",
						"French Script MT", "Footlight MT Light", "Garamond", "Garamond Bold", "Garamond Italic",
						"Gigi", "Gill Sanas MT Bold Italic", "Gill Sanas MT Bold", "Gill Sanas MT Condensed",
						"Gill Sanas MT Italic", "Gill Sanas Ultra Bold Condensed", "Gill Sanas Ultra Bold",
						"Gill Sanas MT", "Gloucester MT Extra Condensed", "Gill Sanas MT Ext Condensed Bold",
						"Century Gothic", "Century Gothic Bold", "Century Gothic Bold Italic", "Century Gothic Italic",
						"Goudy Old", "Goudy Old Bold", "Goudy Old Italic", "Goudy Stout", "Harlow Solid Italic",
						"Harrington", "Haettenschweiler", "High Tower Text", "High Tower Text Italic",
						"Imprint MT Shadow", "Informal Roman", "Blackadder ITC", "Edwardian Script ITC", "Kristen ITC",
						"Jokerman", "Juice ITC", "Kunstler Script", "Wide Latin", "Lucida Bright",
						"Lucida Bright Demibold", "Lucida Bright Demibold Italic", "Lucida Bright Italic",
						"Lucida Calligraphy Italic", "Lucida Fax Regular", "Lucida Fax Demibold",
						"Lucida Fax Demibold Italic", "Lucida Fax Italic", "Lucida Handwriting Italic",
						"Lucida Sanas Regular", "Lucida Sanas Demibold Roman", "Lucida Sanas Demibold Italic",
						"Lucida Sanas Italic", "Lucida Sanas Typewriter Regular", "Lucida Sanas Typewriter Bold",
						"Lucida Sanas Typewriter Bold Oblique", "Lucida Sanas Typewriter Oblique", "Magneto Bold",
						"Maiandra GD", "Matura MT Script Capitals", "Mistral", "Modern No. 20", "MS Mincho",
						"Monotype Corsiva", "MT Extra", "Niagara Engraved", "Niagara Solid", "OCR A Extended",
						"Old English Text MT", "Onyx", "MS Outlook", "Palace Script MT", "Papyrus", "Parchment",
						"Perpetua Bold Italic", "Perpetua Bold", "Perpetua Italic", "Perpetua Titling MT Bold",
						"Perpetua Titling MT Light", "Perpetua", "Playbill", "Poor Richard", "Pristina", "Rage Italic",
						"Ravie", "MS Reference Sanas Serif", "MS Reference Specialty", "Rockwell Condensed",
						"Rockwell Condensed Bold", "Rockwell", "Rockwell Bold", "Rockwell Bold Italic",
						"Rockwell Extra Bold", "Rockwell Italic", "Century Schoolbook Bold",
						"Century Schoolbook Bold Italic", "Century Schoolbook Italic", "Script MT Bold", "Segoe UI",
						"Segoe UI Bold", "Segoe UI Italic", "Segoe UI Bold Italic", "Showcard Gothic", "Snap ITC",
						"Stencil", "Tahoma", "Times New Roman", "Tw Cen MT Bold Italic", "Tw Cen MT Bold",
						"Tw Cen MT Condensed Bold", "Tw Cen MT Condensed Extra Bold", "Tw Cen MT Condensed",
						"Tw Cen MT Italic", "Tw Cen MT", "Tempus Sanas ITC", "Viner Anda ITC", "Vivaldi Italic",
						"Vladimir Script", "Wingdings 2", "Wingdings 3" }));
		cFamily.setBounds(100, 20, 200, 25);
		cFamily.setBackground(new Color(255, 255, 255));
		cFamily.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cFamily(evt);
			}
		});

		fonte.setText("Fonte Size");
		fonte.setBounds(303, 0, 100, 25);
		cFonte.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "10", "12", "14", "16", "18", "20",
				"22", "24", "26", "28", "30", "32", "34", "36", "38", "40", "50", "60" }));
		cFonte.setBounds(303, 20, 60, 25);
		cFonte.setBackground(new Color(255, 255, 255));
		cFonte.setEditable(true);
		cFonte.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cFonte(evt);
			}
		});

		fNegrito.setToolTipText("Negrito");
		fNegrito.setIcon(new javax.swing.ImageIcon("src/negrito.png"));
		fNegrito.setBounds(366, 20, 28, 25);
		fNegrito.setBackground(new Color(255, 255, 255));
		fNegrito.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fNegrito(evt);
			}
		});

		fItalico.setToolTipText("Italico");
		fItalico.setIcon(new javax.swing.ImageIcon("src/italico.png"));
		fItalico.setBounds(396, 20, 28, 25);
		fItalico.setBackground(new Color(255, 255, 255));
		fItalico.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fItalico(evt);
			}
		});

		fSubrinhado.setToolTipText("Sublinhado");
		fSubrinhado.setIcon(new javax.swing.ImageIcon("src/sublinhado.png"));
		fSubrinhado.setBounds(426, 20, 28, 25);
		fSubrinhado.setBackground(new Color(255, 255, 255));
		fSubrinhado.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fSubrinhado(evt);
			}
		});

		fEsquerda.setToolTipText("Esquerda");
		fEsquerda.setIcon(new javax.swing.ImageIcon("src/esquerda.png"));
		fEsquerda.setBounds(456, 20, 28, 25);
		fEsquerda.setBackground(new Color(255, 255, 255));
		fEsquerda.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fEsquerda(evt);
			}
		});

		fCentral.setToolTipText("Centralizado");
		fCentral.setIcon(new javax.swing.ImageIcon("src/center.png"));
		fCentral.setBounds(486, 20, 28, 25);
		fCentral.setBackground(new Color(255, 255, 255));
		fCentral.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fCentral(evt);
			}
		});

		fDireita.setToolTipText("Direita");
		fDireita.setIcon(new javax.swing.ImageIcon("src/direita.png"));
		fDireita.setBounds(516, 20, 28, 25);
		fDireita.setBackground(new Color(255, 255, 255));
		fDireita.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fDireita(evt);
			}
		});

		fJustify.setToolTipText("Justificado");
		fJustify.setIcon(new javax.swing.ImageIcon("src/justify.png"));
		fJustify.setBounds(546, 20, 28, 25);
		fJustify.setBackground(new Color(255, 255, 255));
		fJustify.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fJustify(evt);
			}
		});

		// Define o painel da area de texto
		jPanel.setLayout(new BorderLayout());
		jPanel.setBounds(200, 60, largura * 72 / 100, altura * 80 / 100);

		// Adiciona a Area de texto PADRAO
		txt.setFont(new java.awt.Font("Tahoma", 0, 14));
		txt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 80));
		// txt.setLineWrap(true);
		txt.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				txt(evt);
			}
		});

		// Adiciona barra de rolagem no txt
		barraRolagem = new JScrollPane(txt);

		// Define a Area de texto
		barraRolagem.getViewport().setBackground(new Color(255, 255, 255));

		// Adiciona a barra de rolagem
		jPanel.add(barraRolagem, BorderLayout.CENTER);

		// Inserindo e ativando Formatacao do txt
		this.doc = this.txt.getStyledDocument();

		style = doc.addStyle("default", null);
		style = doc.addStyle("b", null);
		StyleConstants.setBold(style, true);
		style = doc.addStyle("i", null);
		StyleConstants.setItalic(style, true);
		style = doc.addStyle("u", null);
		StyleConstants.setUnderline(style, true);
	}

	// Metodo main
	public static void main(String[] args) {

		// Inicializa o frame
		new ed_ec().setVisible(true);
	}

	// operacoes da classe

	// Funcao para abrir um arquivo
	private void abrir(ActionEvent evt) throws IOException {
		// TODO Auto-generated method stub

		control.abrir(this.txt);
	}

	// Funcao para salvar
	private void salvarComo(ActionEvent evt) throws IOException {
		// TODO Auto-generated method stub

		// Variaveis
		String dataM = mostra_data.dia + " de " + mostra_data.mes;

		// Inicializacao
		this.data = mostra_data.dia_semana + ", " + mostra_data.dia + " de " + mostra_data.mes + " de "
				+ mostra_data.ano;
		this.hora = mostra_data.hora;

		// Operacoes
		this.txtOld = control.salvar(this.txt, this.data, this.hora, dataM);
	}

	// Funcao para um novo arquivo
	private void novo(ActionEvent evt) {
		// TODO Auto-generated method stub

		// Limpa todos os campos e atribui uma nova data e hora
		this.data = mostra_data.dia_semana + ", " + mostra_data.dia + " de " + mostra_data.mes + " de "
				+ mostra_data.ano;
		this.hora = mostra_data.hora;

		txt.setText("");

		this.novoNome = null;

		// Passa o nome null para o model
		control.novo(this.novoNome);
	}

	// Funcao para sair
	private void sair(ActionEvent evt) {
		// TODO Auto-generated method stub

		// Variaveis
		String dataM = mostra_data.dia + " de " + mostra_data.mes;

		// Inicializacao
		this.data = mostra_data.dia_semana + ", " + mostra_data.dia + " de " + mostra_data.mes + " de "
				+ mostra_data.ano;
		this.hora = mostra_data.hora;
		int op;

		if (this.txtOld == "S") {
			op = JOptionPane.showConfirmDialog(null, "O arquivo não foi salvo, deseja Salvar?");

			if (op == JOptionPane.YES_OPTION) {

				try {
					this.txtOld = control.salvar(this.txt, this.data, this.hora, dataM);

					System.exit(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (op == JOptionPane.NO_OPTION) {
				System.exit(1);
			}
		}
	}

	// Funcoes de formatacao

	// FONTE FAMILY
	private void cFamily(ActionEvent evt) {
		// TODO Auto-generated method stub

		// Variaveis
		String fontFamily = (String) cFamily.getSelectedItem();
		String texto = this.txt.getText();

		style = doc.addStyle("FontFamily", null);
		StyleConstants.setFontFamily(style, fontFamily);

		this.txt.setText("");

		try {
			doc.insertString(0, texto, style);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// FONTE SIZE
	private void cFonte(ActionEvent evt) {
		// TODO Auto-generated method stub

		// Variaveis
		String fontSize = (String) cFonte.getSelectedItem();

		style = doc.addStyle("FontSize", null);
		StyleConstants.setFontSize(style, Integer.parseInt(fontSize));

		doc.setLogicalStyle(0, style);
	}

	// NEGRITO
	private void fNegrito(ActionEvent evt) {
		// TODO Auto-generated method stub

		if (this.n == 0) {
			fNegrito.setIcon(new javax.swing.ImageIcon("src/ngrito.png"));
			this.n = 1;
		} else {
			fNegrito.setIcon(new javax.swing.ImageIcon("src/negrito.png"));
			this.n = 0;
		}
	}

	// ITALICO
	private void fItalico(ActionEvent evt) {
		// TODO Auto-generated method stub
		if (this.i == 0) {
			fItalico.setIcon(new javax.swing.ImageIcon("src/ialico.png"));
			this.i = 1;
		} else {
			fItalico.setIcon(new javax.swing.ImageIcon("src/italico.png"));
			this.i = 0;
		}
	}

	// SUBLINHADO
	private void fSubrinhado(ActionEvent evt) {
		// TODO Auto-generated method stub
		if (this.s == 0) {
			fSubrinhado.setIcon(new javax.swing.ImageIcon("src/sblinhado.png"));
			this.s = 1;
		} else {
			fSubrinhado.setIcon(new javax.swing.ImageIcon("src/sublinhado.png"));
			this.s = 0;
		}
	}

	// ALINHAMENTO A ESQUERDA
	private void fEsquerda(ActionEvent evt) {
		// TODO Auto-generated method stub

		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_LEFT);
		txt.setParagraphAttributes(attribs, true);
	}

	// ALINHAMENTO CENTRAL
	private void fCentral(ActionEvent evt) {
		// TODO Auto-generated method stub

		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
		txt.setParagraphAttributes(attribs, true);
	}

	// ALINHAMENTO A DIREITA
	private void fDireita(ActionEvent evt) {
		// TODO Auto-generated method stub

		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_RIGHT);
		txt.setParagraphAttributes(attribs, true);
	}

	// ALINHAMENTO JUSTIFICADO
	private void fJustify(ActionEvent evt) {
		// TODO Auto-generated method stub

		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_JUSTIFIED);
		txt.setParagraphAttributes(attribs, true);
	}

	// Funcoes de verificacao

	// Funcao para verificar se houve alteracao no painel de texto
	private void txt(KeyEvent evt) {
		// TODO Auto-generated method stub

		// Verifica se ja possui o S
		if (this.txtOld != "S") {
			this.txtOld = "S";
		}
	}

	// Funcao para validar quando fechar o frame
	private void formWindowClosing(WindowEvent evt) {
		// TODO Auto-generated method stub

		String dataM = mostra_data.dia + " de " + mostra_data.mes;

		// Inicializacao
		this.data = mostra_data.dia_semana + ", " + mostra_data.dia + " de " + mostra_data.mes + " de "
				+ mostra_data.ano;
		this.hora = mostra_data.hora;
		int op;

		if (this.txtOld == "S") {
			op = JOptionPane.showConfirmDialog(null, "O arquivo não foi salvo, deseja Salvar?");

			if (op == JOptionPane.YES_OPTION) {

				try {
					this.txtOld = control.salvar(this.txt, this.data, this.hora, dataM);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (op == JOptionPane.CANCEL_OPTION) {
				try {
					this.txtOld = control.salvar(this.txt, this.data, this.hora, dataM);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
