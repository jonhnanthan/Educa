package com.educa.junit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.educa.entity.Exercise;
import com.educa.entity.MultipleChoiceExercise;
import com.educa.validation.Correction;
import com.educa.validation.Status;

public class MultipleChoiceExerciseTest {
	
	private MultipleChoiceExercise multipleChoiceExercise1;
	private MultipleChoiceExercise multipleChoiceExercise2;

	@Before 
    public void inicializa() {
    	multipleChoiceExercise1 = new MultipleChoiceExercise("Exercício de Inglês", "Multiple Choice", "13/08/2014", Status.NEW.getValue(), Correction.NOT_RATED.getValue(), "Como se escrevre dois em inglês?", "One", "Two", "Five", "Six", "Two");
    	multipleChoiceExercise2 = new MultipleChoiceExercise("Exercício de Português", "Multiple Choice", "13/08/2014", Status.NEW.getValue(), Correction.NOT_RATED.getValue(), "Qual o coletivo de cães?", "Alcatéia", "Matilha", "Enxame", "Cães", "Matilha");
	}
	
	@Test
	public void deveVerificarTipoDeInstancia(){
		Assert.assertTrue(multipleChoiceExercise1 instanceof Exercise);
		Assert.assertTrue(multipleChoiceExercise2 instanceof Exercise);
	}
	
	@Test
	public void deveAlterarNomeDoExercicio() {
		Assert.assertEquals("Exercício de Inglês", multipleChoiceExercise1.getName());
		multipleChoiceExercise1.setName("Atividade de Inglês");
		Assert.assertEquals("Atividade de Inglês", multipleChoiceExercise1.getName());
		
		Assert.assertEquals("Exercício de Português", multipleChoiceExercise2.getName());
		multipleChoiceExercise2.setName("Atividade de Português");
		Assert.assertEquals("Atividade de Português", multipleChoiceExercise2.getName());
	}

	@Test
	public void deveVerificarTipoDeExercicio(){
		Assert.assertEquals("Multiple Choice", multipleChoiceExercise1.getType());
		Assert.assertEquals("Multiple Choice", multipleChoiceExercise2.getType());
	}
	
	@Test
	public void deveVerificarDataDeCriacaoDoExercicio(){
		Assert.assertEquals("13/08/2014", multipleChoiceExercise1.getDate());
		Assert.assertEquals("13/08/2014", multipleChoiceExercise2.getDate());
	}
	
	@Test
	public void deveVerificarStatusDoExercicio(){
		Assert.assertEquals("New", multipleChoiceExercise1.getStatus());
		Assert.assertEquals("New", multipleChoiceExercise2.getStatus());
	}
	
	@Test
	public void deveVerificarCorrecaoDoExercicio(){
		Assert.assertEquals("Not Rated", multipleChoiceExercise1.getCorrection());
		Assert.assertEquals("Not Rated", multipleChoiceExercise2.getCorrection());

	}
	
	@Test
	public void deveAlterarNomeDaQuestao(){
		Assert.assertEquals("Como se escrevre dois em inglês?", multipleChoiceExercise1.getQuestion());
		multipleChoiceExercise1.setQuestion("Dois em inglês é:");
		Assert.assertEquals("Dois em inglês é:", multipleChoiceExercise1.getQuestion());
		
		Assert.assertEquals("Qual o coletivo de cães?", multipleChoiceExercise2.getQuestion());
		multipleChoiceExercise2.setQuestion("Matilha é o coletivo de:");
		Assert.assertEquals("Matilha é o coletivo de:", multipleChoiceExercise2.getQuestion());
	}
	
	@Test
	public void deveAlterarAlternativasDoExercicio(){
		Assert.assertEquals("One", multipleChoiceExercise1.getAlternative1());
		Assert.assertEquals("Two", multipleChoiceExercise1.getAlternative2());
		Assert.assertEquals("Five", multipleChoiceExercise1.getAlternative3());
		Assert.assertEquals("Six", multipleChoiceExercise1.getAlternative4());
		
		multipleChoiceExercise1.setAlternative1("Ten");
		multipleChoiceExercise1.setAlternative2("Seven");
		multipleChoiceExercise1.setAlternative3("Twelve");
		multipleChoiceExercise1.setAlternative4("Two");
		
		Assert.assertEquals("Ten", multipleChoiceExercise1.getAlternative1());
		Assert.assertEquals("Seven", multipleChoiceExercise1.getAlternative2());
		Assert.assertEquals("Twelve", multipleChoiceExercise1.getAlternative3());
		Assert.assertEquals("Two", multipleChoiceExercise1.getAlternative4());
				
		Assert.assertEquals("Alcatéia", multipleChoiceExercise2.getAlternative1());
		Assert.assertEquals("Matilha", multipleChoiceExercise2.getAlternative2());
		Assert.assertEquals("Enxame", multipleChoiceExercise2.getAlternative3());
		Assert.assertEquals("Cães", multipleChoiceExercise2.getAlternative4());
		
		multipleChoiceExercise2.setAlternative1("Cães");
		multipleChoiceExercise2.setAlternative2("Rebanho");
		multipleChoiceExercise2.setAlternative3("Manada");
		multipleChoiceExercise2.setAlternative4("Alcatéia");
		
		Assert.assertEquals("Cães", multipleChoiceExercise2.getAlternative1());
		Assert.assertEquals("Rebanho", multipleChoiceExercise2.getAlternative2());
		Assert.assertEquals("Manada", multipleChoiceExercise2.getAlternative3());
		Assert.assertEquals("Alcatéia", multipleChoiceExercise2.getAlternative4());
	}
	
	@Test
	public void deveAlterarRespostaCorreta(){
		Assert.assertEquals("Two", multipleChoiceExercise1.getRightAnswer());
		multipleChoiceExercise1.setRightAnswer("It's two");
		Assert.assertEquals("It's two", multipleChoiceExercise1.getRightAnswer());
		
		Assert.assertEquals("Matilha", multipleChoiceExercise2.getRightAnswer());
		multipleChoiceExercise2.setRightAnswer("Coletivo é Matilha");
		Assert.assertEquals("Coletivo é Matilha", multipleChoiceExercise2.getRightAnswer());
	}
}