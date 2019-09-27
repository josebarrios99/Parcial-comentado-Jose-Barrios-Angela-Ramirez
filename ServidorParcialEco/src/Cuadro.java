import processing.core.PApplet;

/**
 * Esta clase fue creada con el proposito de contener todos
 * los datos necesarios para la crearcion de los cuadros
 * que se van a dibujar
 * @author Angela
 *
 */

public class Cuadro {
	/*
	 * Aqui se estan creando las variables
	 */
	private int x;
	private int y;
	private String color;
	/**
	 * Esto es el metodo constructor de la clase, en donde se le
	 * especifica los parametros que debera tener cada objeto de
	 * esta clase
	 * @param x -La posicion en x del cuadro
	 * @param y	-La posicion en y del cuadro
	 * @param color -El tipo de color que tendra el cuadro
	 */
	public Cuadro(int x, int y, String color) {
		/*
		 * Se estan inicializando las variables
		 */
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	/**
	 * Este metodo sirve para pintar el cuadro dependiendo
	 * de que variable se le de, se pintara de un color u otro 
	 * @param app
	 */
	public void pintar(PApplet app) {
		/*
		 * Se crea un switch con la variable color que es un string que sera recibido desde android studio
		 * para asi decirle que tipo de fill debera usar para ese cuadro
		 */
		switch(color) {
		case "rojo":
			app.fill(255,0,0);
			break;
		case "verde":
			app.fill(0,255,0);
			break;
		case "azul":
			app.fill(0,0,255);
			break;
		}
		/*
		 * Se dibuja el cuadro en las posiciones especificadas
		 */
		app.rectMode(app.CENTER);
		app.rect(x, y, 50, 50);
	}	

}
