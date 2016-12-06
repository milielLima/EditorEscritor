package control;

import java.io.IOException;

import javax.swing.JTextPane;

import model.ed_ec_model;

/**
 * Classe para controle do editor
 * 
 * @author Miliel R de Lima
 * @since 13/02/2015
 * @version 1.0
 * 
 */
public class ed_ec_control {

	// Variaveis de instancia
	ed_ec_model	model;

	// Funcao construtora
	public ed_ec_control() {

		// Instancializacao
		model = new ed_ec_model();
	}

	// Funcao para controle do metodo salvar
	public String salvar(JTextPane txt, String data, String hora, String dataM) throws IOException {

		// Chama funcao salvar
		String txtOld = model.salvar(txt, data, hora, dataM);

		return txtOld;
	}

	// Funcao de controle de novo arquivo
	public void novo(String nome) {

		// Chama funcao novo
		model.novo(nome);
	}

	// Funcao para controle de abrir arquivo
	public void abrir(JTextPane txt) throws IOException {

		// Chama funcao para abrir arquivo
		model.abrir(txt);
	}
}
