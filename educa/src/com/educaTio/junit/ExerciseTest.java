package com.educaTio.junit;

import com.educaTio.entity.Exercise;
import com.educaTio.validation.Correction;
import com.educaTio.validation.Status;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExerciseTest {
	
	private Exercise exercise1;
	private Exercise exercise2;

	@Before 
    public void inicializa() {
		exercise1 = new Exercise(0, "Exerc�cio 1", "Quest�o de Portugu�s", "Exercise", "16/08/2014", Status.NEW.getValue(), Correction.NOT_RATED.getValue());
		exercise2 = new Exercise(1, "Exerc�cio 2", "Quest�o de Matem�tica", "Exercise", "16/08/2014", Status.NEW.getValue(), Correction.NOT_RATED.getValue());
	}
	
	@Test
	public void deveAlterarNomeDoExercicio() {
		Assert.assertEquals("Exerc�cio 1", exercise1.getName());
		exercise1.setName("Exerc�cio Novo 1");
		Assert.assertEquals("Exerc�cio Novo 1", exercise1.getName());
		
		Assert.assertEquals("Exerc�cio 2", exercise2.getName());
		exercise2.setName("Exerc�cio Novo 2");
		Assert.assertEquals("Exerc�cio Novo 2", exercise2.getName());
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
		Assert.assertEquals("Quest�o de Portugu�s", exercise1.getQuestion());
		exercise1.setQuestion("Nova quest�o de portugu�s");
		Assert.assertEquals("Nova quest�o de portugu�s", exercise1.getQuestion());
		
		Assert.assertEquals("Quest�o de Matem�tica", exercise2.getQuestion());
		exercise2.setQuestion("Nova quest�o de matem�tica");
		Assert.assertEquals("Nova quest�o de matem�tica", exercise2.getQuestion());
	}
}