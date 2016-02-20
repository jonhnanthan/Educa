package com.educa.junit;

import com.educa.entity.ColorMatchExercise;
import com.educa.entity.Exercise;
import com.educa.validation.Correction;
import com.educa.validation.Status;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ColorMatchExerciseTest {
	
	private ColorMatchExercise colorMatchExercise1;
	private ColorMatchExercise colorMatchExercise2;

	@Before
    public void inicializa() {
		colorMatchExercise1 = new ColorMatchExercise("Associa��o de cor", "Color Match", "13/08/14", Status.NEW.getValue(), Correction.NOT_RATED.getValue(), "Qual o nome dessa cor em ingl�s?", "Red", "Blue", "Green", "Yellow", "Red", "Red");
		colorMatchExercise2 = new ColorMatchExercise("Exerc�cio de cores", "Color Match", "13/08/14", Status.NEW.getValue(), Correction.NOT_RATED.getValue(), "Que cor � essa?", "Cinza", "Branco", "Preto", "Rosa", "Cinza", "Cinza");
	}
	
	@Test
	public void deveVerificarTipoDeInstancia(){
		Assert.assertTrue(colorMatchExercise1 instanceof Exercise);
		Assert.assertTrue(colorMatchExercise2 instanceof Exercise);
	}
	
	@Test
	public void deveAlterarNomeDoExercicio() {
		Assert.assertEquals("Associa��o de cor", colorMatchExercise1.getName());
		colorMatchExercise1.setName("Atividade de cor");
		Assert.assertEquals("Atividade de cor", colorMatchExercise1.getName());
		
		Assert.assertEquals("Exerc�cio de cores", colorMatchExercise2.getName());
		colorMatchExercise2.setName("Escolha de cores");
		Assert.assertEquals("Escolha de cores", colorMatchExercise2.getName());
	}

	@Test
	public void deveVerificarTipoDeExercicio(){
		Assert.assertEquals("Color Match", colorMatchExercise1.getType());
		Assert.assertEquals("Color Match", colorMatchExercise2.getType());
	}
	
	@Test
	public void deveVerificarDataDeCriacaoDoExercicio(){
		Assert.assertEquals("13/08/14", colorMatchExercise1.getDate());
		Assert.assertEquals("13/08/14", colorMatchExercise2.getDate());
	}
	
	@Test
	public void deveVerificarStatusDoExercicio(){
		Assert.assertEquals("New", colorMatchExercise1.getStatus());
		Assert.assertEquals("New", colorMatchExercise2.getStatus());
	}
	
	@Test
	public void deveVerificarCorrecaoDoExercicio(){
		Assert.assertEquals("Not Rated", colorMatchExercise1.getCorrection());
		Assert.assertEquals("Not Rated", colorMatchExercise2.getCorrection());
	}
	
	@Test
	public void deveAlterarNomeDaQuestao(){
		Assert.assertEquals("Qual o nome dessa cor em ingl�s?", colorMatchExercise1.getQuestion());
		colorMatchExercise1.setQuestion("O nome dessa cor, em ingl�s, �:");
		Assert.assertEquals("O nome dessa cor, em ingl�s, �:", colorMatchExercise1.getQuestion());
		
		Assert.assertEquals("Que cor � essa?", colorMatchExercise2.getQuestion());
		colorMatchExercise2.setQuestion("A cor correspondente �:");
		Assert.assertEquals("A cor correspondente �:", colorMatchExercise2.getQuestion());
	}
	
	@Test
	public void deveAlterarAlternativasDoExercicio(){
		Assert.assertEquals("Red", colorMatchExercise1.getAlternative1());
		Assert.assertEquals("Blue", colorMatchExercise1.getAlternative2());
		Assert.assertEquals("Green", colorMatchExercise1.getAlternative3());
		Assert.assertEquals("Yellow", colorMatchExercise1.getAlternative4());

		colorMatchExercise1.setAlternative1("Gray");
		colorMatchExercise1.setAlternative2("Purple");
		colorMatchExercise1.setAlternative3("White");
		colorMatchExercise1.setAlternative4("Black");
		
		Assert.assertEquals("Gray", colorMatchExercise1.getAlternative1());
		Assert.assertEquals("Purple", colorMatchExercise1.getAlternative2());
		Assert.assertEquals("White", colorMatchExercise1.getAlternative3());
		Assert.assertEquals("Black", colorMatchExercise1.getAlternative4());
				
		Assert.assertEquals("Cinza", colorMatchExercise2.getAlternative1());
		Assert.assertEquals("Branco", colorMatchExercise2.getAlternative2());
		Assert.assertEquals("Preto", colorMatchExercise2.getAlternative3());
		Assert.assertEquals("Rosa", colorMatchExercise2.getAlternative4());
		
		colorMatchExercise2.setAlternative1("Cinza");
		colorMatchExercise2.setAlternative2("Branco");
		colorMatchExercise2.setAlternative3("Preto");
		colorMatchExercise2.setAlternative4("Rosa");
		
		Assert.assertEquals("Cinza", colorMatchExercise2.getAlternative1());
		Assert.assertEquals("Branco", colorMatchExercise2.getAlternative2());
		Assert.assertEquals("Preto", colorMatchExercise2.getAlternative3());
		Assert.assertEquals("Rosa", colorMatchExercise2.getAlternative4());
	}
	
	@Test
	public void deveAlterarRespostaCorreta(){
		Assert.assertEquals("Red", colorMatchExercise1.getRightAnswer());
		colorMatchExercise1.setRightAnswer("It's red");
		Assert.assertEquals("It's red", colorMatchExercise1.getRightAnswer());
		
		Assert.assertEquals("Cinza", colorMatchExercise2.getRightAnswer());
		colorMatchExercise2.setRightAnswer("� cinza");
		Assert.assertEquals("� cinza", colorMatchExercise2.getRightAnswer());
	}
}