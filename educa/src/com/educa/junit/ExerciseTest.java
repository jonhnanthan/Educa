package com.educa.junit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.educa.entity.Exercise;
import com.educa.validation.Correction;
import com.educa.validation.Status;

public class ExerciseTest {
	
	private Exercise exercise1;
	private Exercise exercise2;

	@Before 
    public void inicializa() {
		exercise1 = new Exercise(0, "Exercício 1", "Questão de Português", "Exercise", "16/08/2014", Status.NEW.getValue(), Correction.NOT_RATED.getValue());
		exercise2 = new Exercise(1, "Exercício 2", "Questão de Matemática", "Exercise", "16/08/2014", Status.NEW.getValue(), Correction.NOT_RATED.getValue());
	}
	
	@Test
	public void deveAlterarNomeDoExercicio() {
		Assert.assertEquals("Exercício 1", exercise1.getName());
		exercise1.setName("Exercício Novo 1");
		Assert.assertEquals("Exercício Novo 1", exercise1.getName());
		
		Assert.assertEquals("Exercício 2", exercise2.getName());
		exercise2.setName("Exercício Novo 2");
		Assert.assertEquals("Exercício Novo 2", exercise2.getName());
	}

	@Test
	public void deveVerificarTipoDeExercicio(){
		Assert.assertEquals("Exercise", exercise1.getType());
		Assert.assertEquals("Exercise", exercise2.getType());
	}
	
	@Test
	public void deveVerificarDataDeCriacaoDoExercicio(){
		Assert.assertEquals("16/08/2014", exercise1.getDate());
		Assert.assertEquals("16/08/2014", exercise2.getDate());
	}
	
	@Test
	public void deveVerificarStatusDoExercicio(){
		Assert.assertEquals("New", exercise1.getStatus());
		Assert.assertEquals("New", exercise2.getStatus());
	}
	
	@Test
	public void deveVerificarCorrecaoDoExercicio(){
		Assert.assertEquals("Not Rated", exercise1.getCorrection());
		Assert.assertEquals("Not Rated", exercise2.getCorrection());
	}
	
	@Test
	public void deveAlterarNomeDaQuestao(){
		Assert.assertEquals("Questão de Português", exercise1.getQuestion());
		exercise1.setQuestion("Nova questão de português");
		Assert.assertEquals("Nova questão de português", exercise1.getQuestion());
		
		Assert.assertEquals("Questão de Matemática", exercise2.getQuestion());
		exercise2.setQuestion("Nova questão de matemática");
		Assert.assertEquals("Nova questão de matemática", exercise2.getQuestion());
	}
}