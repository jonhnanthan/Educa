package com.educa.junit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.educa.entity.CompleteExercise;
import com.educa.entity.Exercise;
import com.educa.validation.Correction;
import com.educa.validation.Status;

public class CompleteExerciseTest {
	
	private CompleteExercise completeExercise1;
	private CompleteExercise completeExercise2;

	@Before 
    public void inicializa() {
		completeExercise1 = new CompleteExercise("Completar palavra", "Complete", "15/08/2014", Status.NEW.getValue(), Correction.NOT_RATED.getValue(), "Complete com as letras que estão faltando", "CASA", "2");
		completeExercise2 = new CompleteExercise("Exercício de completar palavra", "Complete", "15/08/2014", Status.NEW.getValue(), Correction.NOT_RATED.getValue(), "Quais letras estão faltando?", "CARROÇA", "25");
	}
	
	@Test
	public void deveVerificarTipoDeInstancia(){
		Assert.assertTrue(completeExercise1 instanceof Exercise);
		Assert.assertTrue(completeExercise2 instanceof Exercise);
	}
	
	@Test
	public void deveAlterarNomeDoExercicio() {
		Assert.assertEquals("Completar palavra", completeExercise1.getName());
		completeExercise1.setName("Atividade de completar palavra");
		Assert.assertEquals("Atividade de completar palavra", completeExercise1.getName());
		
		Assert.assertEquals("Exercício de completar palavra", completeExercise2.getName());
		completeExercise2.setName("Atividade de completar");
		Assert.assertEquals("Atividade de completar", completeExercise2.getName());
	}

	@Test
	public void deveVerificarTipoDeExercicio(){
		Assert.assertEquals("Complete", completeExercise1.getType());
		Assert.assertEquals("Complete", completeExercise2.getType());
	}
	
	@Test
	public void deveVerificarDataDeCriacaoDoExercicio(){
		Assert.assertEquals("15/08/2014", completeExercise1.getDate());
		Assert.assertEquals("15/08/2014", completeExercise2.getDate());
	}
	
	@Test
	public void deveVerificarStatusDoExercicio(){
		Assert.assertEquals("New", completeExercise1.getStatus());
		Assert.assertEquals("New", completeExercise2.getStatus());
	}
	
	@Test
	public void deveVerificarCorrecaoDoExercicio(){
		Assert.assertEquals("Not Rated", completeExercise1.getCorrection());
		Assert.assertEquals("Not Rated", completeExercise2.getCorrection());
	}
	
	@Test
	public void deveAlterarNomeDaQuestao(){
		Assert.assertEquals("Complete com as letras que estão faltando", completeExercise1.getQuestion());
		completeExercise1.setQuestion("As letras que faltam é:");
		Assert.assertEquals("As letras que faltam é:", completeExercise1.getQuestion());
		
		Assert.assertEquals("Quais letras estão faltando?", completeExercise2.getQuestion());
		completeExercise2.setQuestion("As letras que faltam é:");
		Assert.assertEquals("As letras que faltam é:", completeExercise2.getQuestion());
	}
	
	@Test
	public void deveVericarSePalavraCompletaContemLetraOculta(){
		String letraOculta1 = "S";
		Assert.assertTrue(completeExercise1.getWord().contains(letraOculta1));
		
		String letraOculta2 = "R";
		String letraOculta3 = "Ç";
		Assert.assertTrue(completeExercise2.getWord().contains(letraOculta2));
		Assert.assertTrue(completeExercise2.getWord().contains(letraOculta3));
	}
}