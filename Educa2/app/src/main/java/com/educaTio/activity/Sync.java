package com.educaTio.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.educaTio.R;
import com.educaTio.database.DataBaseProfessor;
import com.educaTio.entity.ColorMatchExercise;
import com.educaTio.entity.CompleteExercise;
import com.educaTio.entity.Exercise;
import com.educaTio.entity.ImageMatchExercise;
import com.educaTio.entity.MultipleChoiceExercise;
import com.educaTio.entity.MultipleCorrectChoiceExercise;
import com.educaTio.utils.ActiveSession;
import com.educaTio.validation.Correction;

public class Sync extends AsyncTask<String, Integer, String> {
	
	private Context c = null;
	
	public void setContext(Context con){
		c = con;
	}

	public String downloadUrl(final String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			final URL url = new URL(strUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();

			iStream = urlConnection.getInputStream();
			final BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream, "UTF-8"));
			final StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();
			br.close();

		} catch (final Exception e) {
			TeacherHomeActivity.updateDialogMessage(c.getResources().getString(R.string.sync_no_net));
			Log.d("SYNC", String.format("Exception while downloading url: %s", e.toString()));
			cancel(true);
		} finally {
			if (iStream != null) {
				iStream.close();
			}
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		System.out.println(data);
		return data;
	}

	/**
	 * A class, to download Google Places.
	 */
	// private class SyncTask extends AsyncTask<String, Integer, String> {

	/** The data. */
	private String data = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected String doInBackground(final String... url) {

		TeacherHomeActivity.updateDialogMessage(c.getResources().getString(R.string.sync_activities));
		try {
			data = downloadUrl(url[0]);
		} catch (final Exception e) {
			Log.d("SYNC", e.toString());
		}
		
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(final String result) {
		createObjectsAndSync(result);
	}

	private void createObjectsAndSync(String result) {
		JSONObject json = null;

		try {
			json = new JSONObject(result);
		} catch (Exception e) {
			Log.d("SYNC", result);
			TeacherHomeActivity.updateDialogMessage(c.getResources().getString(R.string.sync_error));
			cancel(true);
		}

		if (json != null) {
			try {
				System.out.println(json.getJSONArray("content")
						.getJSONObject(0).toString());
				JSONArray array = json.getJSONArray("content");

				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					String tipo = (new JSONObject(obj.getString("corpo"))).getString("Tipo");
					
					if (tipo.equalsIgnoreCase(DataBaseProfessor.getInstance(c).MULTIPLE_CORRECT_CHOICE_EXERCISE_TYPECODE)){
						// multipla varias
						multipleCorrect(obj); //OK
					}

					if (tipo.equalsIgnoreCase(DataBaseProfessor.getInstance(c).MULTIPLE_CHOICE_EXERCISE_TYPECODE)){
						// multipla com uma certa
						multipleChoice(obj); //OK
					}
					if (tipo.equalsIgnoreCase(DataBaseProfessor.getInstance(c).COMPLETE_EXERCISE_TYPECODE)){
						// completar
						complete(obj);
					}
					if (tipo.equalsIgnoreCase(DataBaseProfessor.getInstance(c).COLOR_MATCH_EXERCISE_TYPECODE)){
						// cores
						colorMatch(obj); //OK
					}
					if (tipo.equalsIgnoreCase(DataBaseProfessor.getInstance(c).IMAGE_MATCH_EXERCISE_TYPECODE)){
						// imagem
						imageMatch(obj); //OK
					}
}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			TeacherHomeActivity.updateAdapter();
		}
	}

	private void complete(JSONObject obj) {
//		{
//			tipo: 1,
//					nome: "Completar",
//				professor: 2,
//				pasta: "web",
//				corpo: "{'Resposta': ['t', 'e'], 'Palavra': 'teste', 'Professor': 't@t.com', 'Tipo': 'COMPLETE_EXERCISE'}",
//				id: 14
//		}
		try {
			String question = obj.getString("nome");
			String word = (new JSONObject(obj.getString("corpo"))).getString("Palavra");

			char[] answer = (new JSONObject(obj.getString("corpo"))).getString("Resposta").toCharArray();
			StringBuffer hiddenIndexes = new StringBuffer();
			for (int i = 0; i < answer.length; i++) {
				if (Character.isLetter(answer[i])) {
					for (int j = 0; j < word.length(); j++) {
						if (word.toCharArray()[j] == answer[i]) {
							hiddenIndexes.append(j);
							break;
						}
					}
				}
			}
			Log.d("john", hiddenIndexes.toString());

			String name = obj.getString("nome");

			Date currentDate = new Date();
			String fDate = new SimpleDateFormat("dd-MM-yyyy")
					.format(currentDate);
			CompleteExercise exercise = new CompleteExercise(
					name,
					DataBaseProfessor.getInstance(c).COMPLETE_EXERCISE_TYPECODE,
					fDate, String.valueOf(com.educaTio.validation.Status.NEW), String
							.valueOf(Correction.NOT_RATED), question, word,
					hiddenIndexes.toString());

			if (exerciseNameDontExists(exercise)) {

				DataBaseProfessor
						.getInstance(c)
						.addActivity(
								(new JSONObject(obj.getString("corpo"))).getString("Professor"),
								"WEB/" + obj.getString("pasta"), name,
								DataBaseProfessor
										.getInstance(c).COMPLETE_EXERCISE_TYPECODE,
								exercise.getJsonTextObject());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void imageMatch(JSONObject obj) { //OK
//		{
//			tipo: 4,
//					nome: "Image Match",
//				professor: 2,
//				pasta: "web",
//				corpo: "{'Resposta': 'dois', 'Professor': 't@t.com', 'Pergunta': 'imagem', 'Imagem': 'num5.png', 'Tipo': 'IMAGE_MATCH_EXERCISE'}",
//				id: 13
//		}
		/*
		 * {"tipo": 4,
		 *  "nome": "ADASD",
		 *   "professor": null,
		 *    "corpo": "{'Tipo': 'IMAGE_MATCH_EXERCISE', 'Resposta': '2015-02-26.jpg', 'Pergunta': 'DFASDFASDF'}",
		 *     "id": 11}
		 */

		try {
			JSONObject corpo = new JSONObject(obj.getString("corpo"));
			String question = corpo.getString("Pergunta");
			String rightAnswer = corpo.getString(
					"Resposta");

			String name = obj.getString("nome");
			Date currentDate = new Date();
			String fDate = new SimpleDateFormat("dd-MM-yyyy")
					.format(currentDate);

			String image = corpo.getString("Imagem");
			String imageCode = "";
			if (image.equalsIgnoreCase("abelha")) {
				imageCode = String.valueOf(R.drawable.abelha);
			} else if (image.equalsIgnoreCase("abacaxi")) {
				imageCode = String.valueOf(R.drawable.abacaxi);
			} else if (image.equalsIgnoreCase("arvore")) {
				imageCode = String.valueOf(R.drawable.arvore);
			} else if (image.equalsIgnoreCase("elefante")) {
				imageCode = String.valueOf(R.drawable.elefante);
			} else if (image.equalsIgnoreCase("escada")) {
				imageCode = String.valueOf(R.drawable.escada);
			} else if (image.equalsIgnoreCase("estrela")) {
				imageCode = String.valueOf(R.drawable.estrela);
			} else if (image.equalsIgnoreCase("igreja")) {
				imageCode = String.valueOf(R.drawable.igreja);
			} else if (image.equalsIgnoreCase("ima")) {
				imageCode = String.valueOf(R.drawable.ima);
			} else if (image.equalsIgnoreCase("olho")) {
				imageCode = String.valueOf(R.drawable.olho);
			} else if (image.equalsIgnoreCase("osso")) {
				imageCode = String.valueOf(R.drawable.osso);
			} else if (image.equalsIgnoreCase("ovo")) {
				imageCode = String.valueOf(R.drawable.ovo);
			} else if (image.equalsIgnoreCase("um")) {
				imageCode = String.valueOf(R.drawable.um);
			} else if (image.equalsIgnoreCase("urso")) {
				imageCode = String.valueOf(R.drawable.urso);
			} else if (image.equalsIgnoreCase("uva")) {
				imageCode = String.valueOf(R.drawable.uva);
			} else {
				imageCode = String.valueOf(R.drawable.abelha);
			}

			ImageMatchExercise exercise = new ImageMatchExercise(name, 
					DataBaseProfessor.getInstance(c).IMAGE_MATCH_EXERCISE_TYPECODE, fDate, String.valueOf(com.educaTio.validation.Status.NEW),
                    String.valueOf(Correction.NOT_RATED), question, rightAnswer, imageCode );


            if (exerciseNameDontExists(exercise)) {

				DataBaseProfessor.getInstance(c).addActivity((new JSONObject(obj.getString("corpo"))).getString("Professor"), "WEB/" + obj.getString("pasta"), name,
						DataBaseProfessor.getInstance(c).IMAGE_MATCH_EXERCISE_TYPECODE, exercise.getJsonTextObject());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void colorMatch(JSONObject obj) { //OK
//		{
//			tipo: 4,
//					nome: "Color Match",
//				professor: 2,
//				pasta: "web",
//				corpo: "{'Alternativa3': 'amarelo', 'Alternativa2': 'azul', 'Alternativa1': 'branco', 'Tipo': 'COLOR_MATCH_EXERCISE', 'Resposta': '#000000', 'Professor': 't@t.com', 'Pergunta': 'teste'}",
//				id: 12
//		}
		/*
		 * {"tipo": 4,
		 *  "nome": "LEONARDO ALVES DOS SANTOS",
		 *   "professor": null,
		 *    "corpo": "{'Alternativa3': 'asdadfasdf', 'Alternativa2': 'ksdafs', 'Alternativa1': 'ladsasd', 'Tipo': 'COLOR_MATCH_EXERCISE', 'Resposta': '#ffaadd', 'Pergunta': 'lkasdk'}",
		 *     "id": 5},
		 */
		String colors[] = { "#551C01", "#AC2B16", "#FF9A00", "#FFE600", "#0B804B", "#20E66F", "#00207A", "#00A2FF", "#653E9B", "#B694E8", "#B65775", "#FF89C8", "#000000", "#EEEEEE"};

		try {
			JSONObject corpo = new JSONObject(obj.getString("corpo"));
			String question = corpo.getString("Pergunta");
			String alternative1 = corpo.getString(
					"Alternativa1");
			String alternative2 = corpo.getString(
					"Alternativa2");
			String alternative3 = corpo.getString(
					"Alternativa3");
			String alternative4 = corpo.getString(
					"Resposta");
			String rightAnswer = corpo.getString(
					"Resposta");

			String name = obj.getString("nome");
			Date currentDate = new Date();
			String fDate = new SimpleDateFormat("dd-MM-yyyy")
					.format(currentDate);

			int values[] = new int[14];
			int value = 0;
			int max = 0;
			for (String string : colors) {
				int soma = 0;
				for (int i = 1; i < string.split("").length; i++) {
					if (string.split("")[i] == rightAnswer.split("")[i]){
						soma++;
					}
				}
				values[value] = soma;
				if (values[max] > soma) max = value;
				value++;
			}
			
            String color = String.valueOf(Color.parseColor(colors[max]));

            ColorMatchExercise exercise = new ColorMatchExercise(name, 
            		DataBaseProfessor.getInstance(c).COLOR_MATCH_EXERCISE_TYPECODE, 
            		fDate, 
            		String.valueOf(com.educaTio.validation.Status.NEW),
                    String.valueOf(Correction.NOT_RATED), question, alternative1,
                    alternative2,
                    alternative3, alternative4, rightAnswer, color);


            if (exerciseNameDontExists(exercise)) {

                DataBaseProfessor.getInstance(c).addActivity((new JSONObject(obj.getString("corpo"))).getString("Professor"), "WEB/" + obj.getString("pasta"), name,
                		DataBaseProfessor.getInstance(c).COLOR_MATCH_EXERCISE_TYPECODE, exercise.getJsonTextObject());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void multipleChoice(JSONObject obj) { //OK
//		{
//			tipo: 4,
//					nome: "Multipla Escolha",
//				professor: 2,
//				pasta: "web",
//				corpo: "{'Alternativa3': '4', 'Alternativa2': '3', 'Alternativa1': '2', 'Tipo': 'MULTIPLE_CHOICE_EXERCISE', 'Resposta': '1', 'Professor': 't@t.com', 'Pergunta': 'teste'}",
//				id: 10
//		}
		/*
		 * {"tipo": 4,
		 *  "nome": "hdfghfdg",
		 *   "professor": null,
		 *    "corpo": "{'Alternativa3': 'dfhdf', 'Alternativa2': 'dfghdfgh', 'Alternativa1': 'hdfghdfhg', 'Tipo': 'MULTIPLE_CHOICE_EXERCISE', 'Resposta': 'fhfdghdfg', 'Pergunta': 'fghdfgh'}",
		 *  "id": 4},
		 */
		try {
			JSONObject corpo = new JSONObject(obj.getString("corpo"));
			String question = corpo.getString("Pergunta");
			String alternative1 = corpo.getString(
					"Alternativa1");
			String alternative2 = corpo.getString(
					"Alternativa2");
			String alternative3 = corpo.getString(
					"Alternativa3");
			String alternative4 = corpo.getString(
					"Resposta");
			String rightAnswer = corpo.getString(
					"Resposta");

			String name = obj.getString("nome");
			Date currentDate = new Date();
			String fDate = new SimpleDateFormat("dd-MM-yyyy")
					.format(currentDate);

			MultipleChoiceExercise exercise = new MultipleChoiceExercise(
					name,
					DataBaseProfessor.getInstance(c).MULTIPLE_CHOICE_EXERCISE_TYPECODE,
					fDate, String.valueOf(com.educaTio.validation.Status.NEW), String
							.valueOf(Correction.NOT_RATED), question,
					alternative1, alternative2, alternative3, alternative4,
					rightAnswer);

			if (exerciseNameDontExists(exercise)) {

				DataBaseProfessor
						.getInstance(c)
						.addActivity(
								(new JSONObject(obj.getString("corpo"))).getString("Professor"),
								"WEB/" + obj.getString("pasta"), name,
								DataBaseProfessor
										.getInstance(c).MULTIPLE_CHOICE_EXERCISE_TYPECODE,
								exercise.getJsonTextObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void multipleCorrect(JSONObject obj) { // OK
//		{
//			tipo: 2,
//					nome: "Multipla Correta",
//				professor: 2,
//				pasta: "web",
//				corpo: "{'Alternativa3': {'4': '0'}, 'Alternativa2': {'3': '1'}, 'Alternativa1': {'2': '1'}, 'Alternativa0': {'1': '0'}, 'Tipo': 'MULTIPLE_CORRECT_CHOICE_EXERCISE', 'Professor': 't@t.com', 'Pergunta': 'teste'}",
//				id: 11
//		}
//		{"tipo": 5,
//			"nome": "asdfasdf",
//			"professor": null,
//			"corpo": "{'Alternativa3': {'asdfasdf': '1'}, 'Alternativa2': {'asdfasdf': '1'}, 'Alternativa1': {'fdhgdfg': '1'}, 'Alternativa0': {'asdfasfd': '0'}, 'Tipo': 'MULTIPLE_CORRECT_CHOICE_EXERCISE', 'Pergunta': 'asdasd'}",
//		"id": 3},
		try {
			JSONObject corpo = new JSONObject(obj.getString("corpo"));
			String question = corpo.getString("Pergunta");

			int length = 0;
			Iterator it = corpo.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (key.contains("Alternativa")) {
					String value = corpo.getString(key).split("\"")[3];
					if (value.equalsIgnoreCase("1")) {
						length++;
					}
				}
			}

			String rightAnswer[] = new String[length];
			Iterator it2 = corpo.keys();
			while (it2.hasNext()) {
				String key = (String) it2.next();
				if (key.contains("Alternativa")) {
					String value = corpo.getString(key).split("\"")[3];
					if (value.equalsIgnoreCase("1")) {
						rightAnswer[length - 1] = corpo.getString(key).split(
								"\"")[1];
						length--;
					}
				}
			}
			String alternative1 = corpo.getString("Alternativa0").split("\"")[1];
			String alternative2 = corpo.getString("Alternativa1").split("\"")[1];
			String alternative3 = corpo.getString("Alternativa2").split("\"")[1];
			String alternative4 = corpo.getString("Alternativa3").split("\"")[1];

			String name = obj.getString("nome");
			Date currentDate = new Date();
			String fDate = new SimpleDateFormat("dd-MM-yyyy")
					.format(currentDate);

			MultipleCorrectChoiceExercise exercise = new MultipleCorrectChoiceExercise(
					name,
					DataBaseProfessor.getInstance(c).MULTIPLE_CORRECT_CHOICE_EXERCISE_TYPECODE,
					fDate, String.valueOf(com.educaTio.validation.Status.NEW),
					String.valueOf(Correction.NOT_RATED), question,
					alternative1, alternative2, alternative3, alternative4,
					rightAnswer);

			if (exerciseNameDontExists(exercise)) {

				DataBaseProfessor
						.getInstance(c)
						.addActivity(
								(new JSONObject(obj.getString("corpo"))).getString("Professor"),
								"WEB/" + obj.getString("pasta"), name,
								DataBaseProfessor.getInstance(c).MULTIPLE_CORRECT_CHOICE_EXERCISE_TYPECODE,
								exercise.getJsonTextObject());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean exerciseNameDontExists(Exercise exercise) {
		List<String> names = DataBaseProfessor.getInstance(
				c).getActivitiesName();

		for (String string : names) {
			if (string != null && string.equals(exercise.getName())) {
				return false;
			}
		}

		return true;
	}

}
