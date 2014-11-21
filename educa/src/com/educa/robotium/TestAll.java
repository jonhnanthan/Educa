package com.educa.robotium;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.educa.R;
import com.educa.activity.AnswerCompleteExercise;
import com.educa.activity.AnswerMultipleChoiceExercise;
import com.educa.activity.ChooseMatchExerciseActivity;
import com.educa.activity.ColorMatchExerciseStep1Activity;
import com.educa.activity.ColorMatchExerciseStep2Activity;
import com.educa.activity.ColorMatchExerciseStep3Activity;
import com.educa.activity.ColorMatchExerciseStep4Activity;
import com.educa.activity.CompleteExerciseStep1Activity;
import com.educa.activity.CompleteExerciseStep2Activity;
import com.educa.activity.CompleteExerciseStep3Activity;
import com.educa.activity.EditMultipleChoiceExerciseActivity;
import com.educa.activity.MainActivity;
import com.educa.activity.MultipleChoiceExerciseStep1Activity;
import com.educa.activity.MultipleChoiceExerciseStep2Activity;
import com.educa.activity.MultipleChoiceExerciseStep3Activity;
import com.educa.activity.StudentHomeActivity;
import com.educa.activity.TeacherHomeActivity;
import com.robotium.solo.Solo;

public class TestAll extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;

	public TestAll() {
		super(MainActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void test_A_CreateColorExercise() throws Exception {
//		solo.clickOnText(getActivity().getApplicationContext().getString(R.string.teacher));
//		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class); 
//		
//		solo.clickOnActionBarItem(R.id.new_exercise);
//		solo.waitForActivity(TeacherHomeActivity.class);
//		solo.assertCurrentActivity("Expected TeacherHomeActivity", TeacherHomeActivity.class); 
//
//		solo.clickOnImageButton(0);
//		solo.waitForActivity(ChooseMatchExerciseActivity.class);
//		solo.assertCurrentActivity("Expected ChooseMatchExerciseActivity", ChooseMatchExerciseActivity.class); 
//
//		solo.clickOnImage(0);
//		solo.waitForActivity(ColorMatchExerciseStep1Activity.class);
//		solo.assertCurrentActivity("Expected ColorMatchExerciseStep1Activity", ColorMatchExerciseStep1Activity.class); 
//
//		//Pick the first color
//		solo.clickInList(0);
//		
//		//Next step
//		solo.clickOnImageButton(2);
//		solo.waitForActivity(ColorMatchExerciseStep2Activity.class);
//		solo.assertCurrentActivity("Expected ColorMatchExerciseStep2Activity", ColorMatchExerciseStep2Activity.class); 
//
//		solo.enterText(0, "Que cor é essa?");
//		solo.enterText(1, "Preta");
//		solo.enterText(2, "Cinza");
//		solo.enterText(3, "Marrom");
//		solo.enterText(4, "Amarela");
//		
//		//Next step
//		solo.clickOnImage(2);
//		
//		solo.waitForActivity(ColorMatchExerciseStep3Activity.class);
//		solo.assertCurrentActivity("Expected ColorMatchExerciseStep3Activity", ColorMatchExerciseStep3Activity.class); 
//
//		//Click on the right answer
//		solo.clickOnRadioButton(2);
//		
//		//Next step
//		solo.clickOnImage(2);
//		solo.waitForActivity(ColorMatchExerciseStep4Activity.class);
//		solo.assertCurrentActivity("Expected ColorMatchExerciseStep4Activity", ColorMatchExerciseStep4Activity.class); 
//
//		solo.enterText(0, "Exercício de cores");
//		
//		solo.clickOnImage(2);
//		solo.waitForActivity(TeacherHomeActivity.class);
//		solo.assertCurrentActivity("Expected TeacherHomeActivity", TeacherHomeActivity.class); 
	}
	
	public void test_B_CreateCompleteExercise() throws Exception {
		solo.clickOnText(getActivity().getApplicationContext().getString(R.string.teacher));
		solo.waitForActivity(MainActivity.class);
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class); 
		
		solo.clickOnActionBarItem(R.id.new_exercise);
		solo.waitForActivity(TeacherHomeActivity.class);
		solo.assertCurrentActivity("Expected TeacherHomeActivity", TeacherHomeActivity.class); 
		
		solo.clickOnImage(1);
		solo.waitForActivity(CompleteExerciseStep1Activity.class);
		solo.assertCurrentActivity("Expected CompleteExerciseStep1Activity", CompleteExerciseStep1Activity.class); 

		solo.enterText(0, "Lugar onde você mora");
		solo.enterText(1, "casa");
		
		solo.clickOnImage(2);
		solo.waitForActivity(CompleteExerciseStep2Activity.class);
		solo.assertCurrentActivity("Expected CompleteExerciseStep2Activity", CompleteExerciseStep2Activity.class); 

		solo.clickOnCheckBox(2);
		
		solo.clickOnImage(2);
		solo.waitForActivity(CompleteExerciseStep3Activity.class);
		solo.assertCurrentActivity("Expected CompleteExerciseStep3Activity", CompleteExerciseStep3Activity.class); 

		solo.enterText(0, "Exercício de completar");
		
		solo.clickOnImage(2);
		solo.waitForActivity(TeacherHomeActivity.class);
		solo.assertCurrentActivity("Expected TeacherHomeActivity", TeacherHomeActivity.class); 
	}
	
	public void test_C_CreateMultipleChoiceExercise() throws Exception {
		solo.clickOnText(getActivity().getApplicationContext().getString(R.string.teacher));
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class); 
		
		solo.clickOnActionBarItem(R.id.new_exercise);
		solo.assertCurrentActivity("Expected TeacherHomeActivity", TeacherHomeActivity.class); 
		
		solo.clickOnImage(2);
		solo.waitForActivity(MultipleChoiceExerciseStep1Activity.class);
		solo.assertCurrentActivity("Expected MultipleChoiceExerciseStep1Activity", MultipleChoiceExerciseStep1Activity.class); 
		
		solo.enterText(0, "Qual o último mês do ano?");
		solo.enterText(1, "Janeiro");
		solo.enterText(2, "Novembro");
		solo.enterText(3, "Dezembro");
		solo.enterText(4, "Outubro");
		
		solo.clickOnImage(2);
		solo.waitForActivity(MultipleChoiceExerciseStep2Activity.class);
		solo.assertCurrentActivity("Expected MultipleChoiceExerciseStep2Activity", MultipleChoiceExerciseStep2Activity.class); 
	
		solo.clickOnRadioButton(2);
		
		solo.clickOnImage(2);
		solo.waitForActivity(MultipleChoiceExerciseStep3Activity.class);
		solo.assertCurrentActivity("Expected MultipleChoiceExerciseStep3Activity", MultipleChoiceExerciseStep3Activity.class); 
	
		solo.enterText(0, "Exercício dos meses");
		
		solo.clickOnImage(2);
		solo.waitForActivity(TeacherHomeActivity.class);
		solo.assertCurrentActivity("Expected TeacherHomeActivity", TeacherHomeActivity.class); 
	}

	public void test_D_AnswerColorExercise() throws Exception {
	
	}
	
	public void test_E_AnswerCompleteExercise() throws Exception {
		solo.clickOnText(getActivity().getApplicationContext().getString(R.string.student));
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class); 
		
		solo.clickOnText("Exercício de completar");

		solo.waitForActivity(AnswerCompleteExercise.class);
		solo.assertCurrentActivity("Expected AnswerCompleteExercise", AnswerCompleteExercise.class);
		
		solo.enterText(2, "D");
		solo.clickOnImage(2);
		solo.waitForDialogToOpen();
		solo.clickOnText(getActivity().getResources().getString(R.string.yes));
		solo.waitForDialogToClose();

		solo.enterText(2, "");
		solo.enterText(2, "S");
		solo.clickOnImage(2);
		solo.waitForDialogToOpen();
		solo.clickOnText(getActivity().getResources().getString(R.string.ok));
		solo.waitForActivity(StudentHomeActivity.class);
		solo.assertCurrentActivity("Expected StudentHomeActivity", StudentHomeActivity.class);
	}
	
	public void test_F_AnswerMultipleChoiceExercise() throws Exception {
		solo.clickOnText(getActivity().getApplicationContext().getString(R.string.student));
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class); 
		
		solo.clickOnText("Exercício dos meses");
		
		solo.waitForActivity(AnswerMultipleChoiceExercise.class);
		solo.assertCurrentActivity("Expected AnswerMultipleChoiceExercise", AnswerMultipleChoiceExercise.class);
		
		solo.clickOnRadioButton(0);
		solo.clickOnImage(2);
		solo.waitForDialogToOpen();
		solo.clickOnText(getActivity().getResources().getString(R.string.yes));
		solo.waitForDialogToClose();
		
		solo.clickOnRadioButton(2);
		solo.clickOnImage(2);
		solo.waitForDialogToOpen();
		solo.clickOnText(getActivity().getResources().getString(R.string.ok));
		solo.waitForDialogToClose();
		
		solo.waitForActivity(StudentHomeActivity.class);
		solo.assertCurrentActivity("Expected StudentHomeActivity", StudentHomeActivity.class);
	}
	
	public void test_G_EditMultipleChoiceExercise() throws Exception {
		solo.clickOnText(getActivity().getApplicationContext().getString(R.string.teacher));
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class); 

		View view = solo.getView(R.id.bt_options);
		solo.clickOnView(view);
		
		solo.clickOnText(getActivity().getApplicationContext().getResources().getString(R.string.edit));
		solo.assertCurrentActivity("Expected EditMultipleChoiceExerciseActivity", EditMultipleChoiceExerciseActivity.class); 
		
		solo.enterText(0, "");
		solo.enterText(0, "Qual o mês dos dias das crianças?");
		
		solo.enterText(1, "");
		solo.enterText(1, "Março");
		
		solo.enterText(2, "");
		solo.enterText(2, "Agosto");
		
		solo.enterText(3, "");
		solo.enterText(3, "Setembro");
		
		solo.enterText(4, "");
		solo.enterText(4, "Outubro");
		
		solo.clickOnRadioButton(3);
		solo.clickOnImage(2);

		solo.waitForDialogToOpen();
		solo.clickOnText(getActivity().getResources().getString(R.string.ok));
		solo.waitForDialogToClose();
		
		solo.waitForActivity(TeacherHomeActivity.class);
		solo.assertCurrentActivity("Expected TeacherHomeActivity", TeacherHomeActivity.class); 
	}
	
	public void test_H_DeleteExercises() throws Exception {
		solo.clickOnText(getActivity().getApplicationContext().getString(R.string.teacher));
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class); 

		View view = solo.getView(R.id.bt_options);
		solo.clickOnView(view);
		
		solo.clickOnText(getActivity().getApplicationContext().getResources().getString(R.string.delete));
		solo.waitForDialogToOpen();
		solo.clickOnText(getActivity().getApplicationContext().getResources().getString(R.string.ok));
		solo.waitForDialogToClose();
		
		solo.waitForActivity(TeacherHomeActivity.class);
		solo.assertCurrentActivity("Expected TeacherHomeActivity", TeacherHomeActivity.class); 
		
		view = solo.getView(R.id.bt_options);
		solo.clickOnView(view);
		
		solo.clickOnText(getActivity().getApplicationContext().getResources().getString(R.string.delete));
		solo.waitForDialogToOpen();
		solo.clickOnText(getActivity().getApplicationContext().getResources().getString(R.string.ok));
		solo.waitForDialogToClose();
		
		solo.waitForActivity(TeacherHomeActivity.class);
		solo.assertCurrentActivity("Expected TeacherHomeActivity", TeacherHomeActivity.class); 
	}

	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}