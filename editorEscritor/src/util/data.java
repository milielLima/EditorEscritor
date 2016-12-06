package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class data {

	public String		mes, dia, ano, dia_semana, hora;
	SimpleDateFormat	horaFormatada	= new SimpleDateFormat("HH:mm:ss");

	public void le_hora() {

		Date horaAtual = new Date();
		hora = horaFormatada.format(horaAtual);
	}

	@SuppressWarnings("deprecation")
	public void le_data() {

		Date data = new Date();

		// mes = ""+data.getMonth();
		dia = "" + data.getDate();
		ano = "" + (1900 + data.getYear());
		// dia_semana = ""+data.getDay();

		switch (data.getDay()) {
			case 0:
				dia_semana = "Dom";
				break;
			case 1:
				dia_semana = "Seg";
				break;
			case 2:
				dia_semana = "Ter";
				break;
			case 3:
				dia_semana = "Quar";
				break;
			case 4:
				dia_semana = "Quin";
				break;
			case 5:
				dia_semana = "Sex";
				break;
			case 6:
				dia_semana = "Sab";
				break;
		}

		switch (data.getMonth()) {
			case 0:
				mes = "Jan";
				break;
			case 1:
				mes = "Fev";
				break;
			case 2:
				mes = "Mar";
				break;
			case 3:
				mes = "Abr";
				break;
			case 4:
				mes = "Maio";
				break;
			case 5:
				mes = "Jun";
				break;
			case 6:
				mes = "Jul";
				break;
			case 7:
				mes = "Ago";
				break;
			case 8:
				mes = "Set";
				break;
			case 9:
				mes = "Out";
				break;
			case 10:
				mes = "Nov";
				break;
			case 11:
				mes = "Dez";
				break;
		}
	}
}