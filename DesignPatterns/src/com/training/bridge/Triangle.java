package com.training.bridge;

public class Triangle extends Shape{
	public Triangle(IColor color) {
		super(color);
		// TODO Auto-generated constructor stub
	}

	public void applyColor() {
		System.out.println("Triangle is applience with color" + getColor());
		getColor().applyColor();
	}
}
