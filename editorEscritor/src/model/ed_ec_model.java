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

	// Função para um novo arquivo
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
		// MOSTRA OS DIRETORIOS PARA SELEÇÃO.
		int retorno = arquivo.showOpenDialog(parentComponent);

		File file = arquivo.getSelectedFile();

		if (retorno == JFileChooser.APPROVE_OPTION) {
			caminho = file.getPath();
		} else {
			caminho = null;
		}
		return caminho;
	}

	// funcao para selecionar diretório
	public static String getCaminho(Component parentComponent, String titulo) {

		JFileChooser arquivo = new JFileChooser();

		arquivo.setDialogTitle(titulo);
		arquivo.isDirectorySelectionEnabled();

		// POSSIBILITA ESCOLHER APENAS DIRETORIOS.
		arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		String caminho = null;
		// MOSTRA OS DIRETORIOS PARA SELEÇÃO.
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

			// Verifica o diretório
			file = new File(this.caminho + "\\" + dataM + " - " + this.nome);
		}

		if (!file.exists() && this.nome == null) {

			// Abre o diretório
			this.caminho = getCaminho(null, "Selecione um caminho");

			this.nome = JOptionPane.showInputDialog("Informe um nome para o arquivo");

			// Pega o tipo da estencao do arquivo
			tipoArq = tipo();

			// Verifica o diretório
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

					// Corrige erros de formatação para caracteres especiais
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

					// Corrige erros de formatação para caracteres especiais
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

		txt = txt.replace("Á", "&Aacute;");
		txt = txt.replace("á", "&aacute;");
		txt = txt.replace("Â", "&Acirc;");
		txt = txt.replace("â", "&acirc;");
		txt = txt.replace("À", "&Agrave;");
		txt = txt.replace("à", "&agrave;");
		txt = txt.replace("Å", "&Aring;");
		txt = txt.replace("å", "&aring;");
		txt = txt.replace("Ã", "&Atilde;");
		txt = txt.replace("ã", "&atilde;");
		txt = txt.replace("Ä", "&Auml;");
		txt = txt.replace("ä", "&auml;");
		txt = txt.replace("Æ", "&AElig;");
		txt = txt.replace("æ", "&aelig;");
		txt = txt.replace("É", "&Eacute;");
		txt = txt.replace("é", "&eacute;");
		txt = txt.replace("Ê", "&Ecirc;");
		txt = txt.replace("ê", "&ecirc;");
		txt = txt.replace("È", "&Egrave;");
		txt = txt.replace("Ë", "&Euml;");
		txt = txt.replace("ë", "&euml;");
		txt = txt.replace("Ð", "&ETH;");
		txt = txt.replace("ð", "&eth;");
		txt = txt.replace("Í", "&Iacute;");
		txt = txt.replace("í", "&iacute;");
		txt = txt.replace("Î", "&Icirc;");
		txt = txt.replace("î", "&icirc;");
		txt = txt.replace("Ì", "&Igrave;");
		txt = txt.replace("ì", "&igrave;");
		txt = txt.replace("Ï", "&Iuml;");
		txt = txt.replace("ï", "&iuml;");
		txt = txt.replace("Ó", "&Oacute;");
		txt = txt.replace("ó", "&oacute;");
		txt = txt.replace("Ô", "&Ocirc;");
		txt = txt.replace("ô", "&ocirc;");
		txt = txt.replace("Ò", "&Ograve;");
		txt = txt.replace("ò", "&ograve;");
		txt = txt.replace("Ø", "&Oslash;");
		txt = txt.replace("ø", "&oslash;");
		txt = txt.replace("Õ", "&Otilde;");
		txt = txt.replace("õ", "&otilde;");
		txt = txt.replace("Ö", "&Ouml;");
		txt = txt.replace("ö", "&ouml;");
		txt = txt.replace("Ú", "&Uacute;");
		txt = txt.replace("ú", "&uacute;");
		txt = txt.replace("Û", "&Ucirc;");
		txt = txt.replace("û", "&ucirc;");
		txt = txt.replace("Ù", "&Ugrave;");
		txt = txt.replace("ù", "&ugrave;");
		txt = txt.replace("Ü", "&Uuml;");
		txt = txt.replace("ü", "&uuml;");
		txt = txt.replace("Ç", "&Ccedil;");
		txt = txt.replace("ç", "&ccedil;");
		txt = txt.replace("Ñ", "&Ntilde;");
		txt = txt.replace("ñ", "&ntilde;");
		txt = txt.replace("&", "&amp;");
		txt = txt.replace("®", "&reg;");
		txt = txt.replace("©", "&copy;");
		txt = txt.replace("Ý", "&Yacute;");
		txt = txt.replace("ý", "&yacute;");
		txt = txt.replace("Þ", "&THORN;");
		txt = txt.replace("þ", "&thorn;");
		txt = txt.replace("ß", "&szlig;");
		txt = txt.replace("•", "&bull;");
		txt = txt.replace("º", "&ordm;");
		txt = txt.replace("&amp;", "&");

		return txt;
	}
}
