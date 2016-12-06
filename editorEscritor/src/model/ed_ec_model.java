package model;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 * Classe para defenir um editor
 * 
 * @author Miliel R de Lima
 * @since 13/02/2015
 * @version 1.0
 * 
 */

public class ed_ec_model {

	// Variaveis de instancia
	private PrintWriter	gravarArq;

	private String		caminho;
	private String		nome;

	JTextArea			jArea;
	File				file;
	String				verifica;

	// Funcao construtora da classe
	public ed_ec_model() {

		// Inicializacao
		this.caminho = "";
	}

	// Operacoes da classe

	// Fun��o para um novo arquivo
	public void novo(String nome) {

		this.nome = nome;
	}

	// Funcao para abrir um novo arquivo
	public void abrir(JTextPane txt) throws IOException {

		// Variaveis
		String arquivo = null;
		String texto = "";

		// recebe o arquivo
		arquivo = getArquivo(null);

		if (!(arquivo == null)) {

			// Inicializa a leitura
			BufferedReader br = new BufferedReader(new FileReader(arquivo));

			// Le o arquivo
			while (br.ready()) {
				String linha = br.readLine();
				texto += linha;
			}

			br.close();

			// Atribui a aerea de texto o arquivo lido
			txt.setText(texto);

			this.file = new File(arquivo);

			this.verifica = "N";
		}
	}

	// Funcao para abrir um diretorio
	public static String getArquivo(Component parentComponent) {

		JFileChooser arquivo = new JFileChooser();

		arquivo.setDialogTitle("Selecione um arquivo");
		arquivo.isDirectorySelectionEnabled();

		// POSSIBILITA ESCOLHER APENAS DIRETORIOS.
		arquivo.setFileSelectionMode(JFileChooser.OPEN_DIALOG);

		String caminho = null;
		// MOSTRA OS DIRETORIOS PARA SELE��O.
		int retorno = arquivo.showOpenDialog(parentComponent);

		File file = arquivo.getSelectedFile();

		if (retorno == JFileChooser.APPROVE_OPTION) {
			caminho = file.getPath();
		} else {
			caminho = null;
		}
		return caminho;
	}

	// funcao para selecionar diret�rio
	public static String getCaminho(Component parentComponent, String titulo) {

		JFileChooser arquivo = new JFileChooser();

		arquivo.setDialogTitle(titulo);
		arquivo.isDirectorySelectionEnabled();

		// POSSIBILITA ESCOLHER APENAS DIRETORIOS.
		arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		String caminho = null;
		// MOSTRA OS DIRETORIOS PARA SELE��O.
		int retorno = arquivo.showOpenDialog(parentComponent);

		File file = arquivo.getSelectedFile();

		if (retorno == JFileChooser.APPROVE_OPTION) {
			caminho = file.getPath();
		} else {
			caminho = null;
		}
		return caminho;
	}

	// Funcao para salvar
	public String salvar(JTextPane txt, String data, String hora, String dataM) throws IOException {

		// Variaveis
		String texto = txt.getText();
		String txtOld = null;
		String tipoArq = null;

		FileWriter arq;

		if (this.verifica != "N") {

			// Verifica o diret�rio
			file = new File(this.caminho + "\\" + dataM + " - " + this.nome);
		}

		if (!file.exists() && this.nome == null) {

			// Abre o diret�rio
			this.caminho = getCaminho(null, "Selecione um caminho");

			this.nome = JOptionPane.showInputDialog("Informe um nome para o arquivo");

			// Pega o tipo da estencao do arquivo
			tipoArq = tipo();

			// Verifica o diret�rio
			file = new File(this.caminho + "\\" + dataM + " - " + this.nome);

			if (!file.exists() && this.caminho != null && this.nome != null) {

				if (!tipoArq.equals("html")) {

					// Cria um novo arquivo
					arq = new FileWriter(this.caminho + "\\" + dataM + " - " + this.nome + ".txt");
					gravarArq = new PrintWriter(arq);
					gravarArq.println(data + " ---------- " + hora);
					gravarArq.println("\n\t" + texto);
					arq.close();
				} else {

					// Corrige erros de formata��o para caracteres especiais
					texto = filterTxt(texto);

					// Cria um novo arquivo
					arq = new FileWriter(this.caminho + "\\" + dataM + " - " + this.nome);
					gravarArq = new PrintWriter(arq);
					gravarArq.printf("<h4>" + data + "&nbsp;&nbsp;&nbsp;" + hora + "</h4>"
							+ "<pre style='padding-left:20px;'>" + texto + "</pre>");
					arq.close();
				}

				JOptionPane.showMessageDialog(null, "Salvo com sucesso!");

				// limpa a variavel do closing
				txtOld = "";
			}
		} else {

			int opcao = JOptionPane.showConfirmDialog(null, "Ja existe um arquivo com o mesmo nome: " + this.nome
					+ " - " + dataM + " \n Deseja substituir?");

			if (opcao == JOptionPane.YES_OPTION) {

				File caminho = this.file;

				this.file.delete();

				// Pega o tipo da estencao do arquivo
				tipoArq = tipo();

				if (!tipoArq.equals("html")) {
					arq = new FileWriter(caminho);
					gravarArq = new PrintWriter(arq);
					gravarArq.printf(data + " ---------- " + hora);
					gravarArq.println("\t" + texto);
					arq.close();
				} else {

					// Corrige erros de formata��o para caracteres especiais
					texto = filterTxt(texto);

					arq = new FileWriter(caminho);
					gravarArq = new PrintWriter(arq);
					gravarArq.printf("<h4>" + data + "&nbsp;&nbsp;&nbsp;" + hora + "</h4>"
							+ "<pre style='padding-left:20px;'>" + texto + "</pre>");
					arq.close();
				}

				// limpa a variavel do closing
				txtOld = "";
			}
		}
		return txtOld;
	}

	// Funcao para pegar o tipo da estencao do arquivo
	public String tipo() {

		// Variaveis
		String nome = null;
		int um;

		um = this.nome.indexOf(".");
		nome = this.nome.substring(um + 1);

		return nome;
	}

	// Funcao para filtrar o txt
	public String filterTxt(String txt) {

		txt = txt.replace("�", "&Aacute;");
		txt = txt.replace("�", "&aacute;");
		txt = txt.replace("�", "&Acirc;");
		txt = txt.replace("�", "&acirc;");
		txt = txt.replace("�", "&Agrave;");
		txt = txt.replace("�", "&agrave;");
		txt = txt.replace("�", "&Aring;");
		txt = txt.replace("�", "&aring;");
		txt = txt.replace("�", "&Atilde;");
		txt = txt.replace("�", "&atilde;");
		txt = txt.replace("�", "&Auml;");
		txt = txt.replace("�", "&auml;");
		txt = txt.replace("�", "&AElig;");
		txt = txt.replace("�", "&aelig;");
		txt = txt.replace("�", "&Eacute;");
		txt = txt.replace("�", "&eacute;");
		txt = txt.replace("�", "&Ecirc;");
		txt = txt.replace("�", "&ecirc;");
		txt = txt.replace("�", "&Egrave;");
		txt = txt.replace("�", "&Euml;");
		txt = txt.replace("�", "&euml;");
		txt = txt.replace("�", "&ETH;");
		txt = txt.replace("�", "&eth;");
		txt = txt.replace("�", "&Iacute;");
		txt = txt.replace("�", "&iacute;");
		txt = txt.replace("�", "&Icirc;");
		txt = txt.replace("�", "&icirc;");
		txt = txt.replace("�", "&Igrave;");
		txt = txt.replace("�", "&igrave;");
		txt = txt.replace("�", "&Iuml;");
		txt = txt.replace("�", "&iuml;");
		txt = txt.replace("�", "&Oacute;");
		txt = txt.replace("�", "&oacute;");
		txt = txt.replace("�", "&Ocirc;");
		txt = txt.replace("�", "&ocirc;");
		txt = txt.replace("�", "&Ograve;");
		txt = txt.replace("�", "&ograve;");
		txt = txt.replace("�", "&Oslash;");
		txt = txt.replace("�", "&oslash;");
		txt = txt.replace("�", "&Otilde;");
		txt = txt.replace("�", "&otilde;");
		txt = txt.replace("�", "&Ouml;");
		txt = txt.replace("�", "&ouml;");
		txt = txt.replace("�", "&Uacute;");
		txt = txt.replace("�", "&uacute;");
		txt = txt.replace("�", "&Ucirc;");
		txt = txt.replace("�", "&ucirc;");
		txt = txt.replace("�", "&Ugrave;");
		txt = txt.replace("�", "&ugrave;");
		txt = txt.replace("�", "&Uuml;");
		txt = txt.replace("�", "&uuml;");
		txt = txt.replace("�", "&Ccedil;");
		txt = txt.replace("�", "&ccedil;");
		txt = txt.replace("�", "&Ntilde;");
		txt = txt.replace("�", "&ntilde;");
		txt = txt.replace("&", "&amp;");
		txt = txt.replace("�", "&reg;");
		txt = txt.replace("�", "&copy;");
		txt = txt.replace("�", "&Yacute;");
		txt = txt.replace("�", "&yacute;");
		txt = txt.replace("�", "&THORN;");
		txt = txt.replace("�", "&thorn;");
		txt = txt.replace("�", "&szlig;");
		txt = txt.replace("�", "&bull;");
		txt = txt.replace("�", "&ordm;");
		txt = txt.replace("&amp;", "&");

		return txt;
	}
}
