package ru.skurko.sandbox;

public class MyFirstProgramm {
	public static void main(String[] args) {

		Square squareOne = new Square(15);
		System.out.println("Площадь квадрата равна " + squareOne.area());

		Rectangle rectangleOne = new Rectangle(10, 20);
		System.out.println("Площадь прямоугольника равна " + rectangleOne.area());
	}
}
