package com.educaTio.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.educaTio.R;
import com.educaTio.database.DataBaseProfessor;
import com.educaTio.entity.CompleteExercise;
import com.educaTio.entity.Exercise;
import com.educaTio.entity.MultipleChoiceExercise;
import com.educaTio.entity.MultipleCorrectChoiceExercise;
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
			Log.d("Exception while downloading url", e.toString());
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

		try {
			data = downloadUrl(url[0]);
		} catch (final Exception e) {
			Log.d("Background Task", e.toString());
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
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (json != null) {
			try {
				System.out.println(json.getJSONArray("content")
						.getJSONObject(0).toString());
				JSONArray array = json.getJSONArray("content");

				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);

					switch (obj.getInt("tipo")) {
					case 1:
						// multipla varias
						multipleCorrect(obj);
						break;
					case 3:
						// multipla com uma certa
						multipleChoice(obj);
						break;
					case 2:
						// completar
						complete(obj);
						break;
					default:
						break;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void complete(JSONObject obj) {
		// {"id":1,"corpo":"{'Resposta': 'aa', 'Palavra': 'aa'}","nome":"aa","tipo":2}
		try {
			String question = obj.getString("nome");
			String word = obj.getJSONObject("corpo").getString("Palavra");
			String hiddenIndexes = "1";
			String name = obj.getString("nome");
			Date currentDate = new Date();
			String fDate = new SimpleDateFormat("dd-MM-yyyy")
					.format(currentDate);
			CompleteExercise exercise = new CompleteExercise(
					name,
					DataBaseProfessor.getInstance(c).COMPLETE_EXERCISE_TYPECODE,
					fDate, String.valueOf(com.educaTio.validation.Status.NEW), String
							.valueOf(Correction.NOT_RATED), question, word,
					hiddenIndexes);

			if (exerciseNameDontExists(exercise)) {

				DataBaseProfessor
						.getInstance(c)
						.addActivity(
								name,
								DataBaseProfessor
										.getInstance(c).COMPLETE_EXERCISE_TYPECODE,
								exercise.getJsonTextObject());

			} else {
				Toast.makeText(
						c,
						c.getResources().getString(
								R.string.exercise_name_already_exists),
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void multipleChoice(JSONObject obj) {
		try {
			String question = obj.getJSONObject("corpo").getString("Pergunta");
			String alternative1 = obj.getJSONObject("corpo").getString(
					"Alternativa1");
			String alternative2 = obj.getJSONObject("corpo").getString(
					"Alternativa2");
			String alternative3 = obj.getJSONObject("corpo").getString(
					"Alternativa3");
			String alternative4 = obj.getJSONObject("corpo").getString(
					"Resposta");
			String rightAnswer = obj.getJSONObject("corpo").getString(
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
								name,
								DataBaseProfessor
										.getInstance(c).MULTIPLE_CHOICE_EXERCISE_TYPECODE,
								exercise.getJsonTextObject());
			} else {
				Toast.makeText(
						c,
						c.getResources().getString(
								R.string.exercise_name_already_exists),
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void multipleCorrect(JSONObject obj) {
		// {"tipo": 1, "nome": "asdfasdf",
		// "corpo":
		// "{'Alternativa3': 'asfdaf', 'Alternativa2': 'asfdasfd', 'Alternativa1': 'asfdasdf', 'Pergunta': 'sdfasdfas', 'Resposta': 'asfdasfd'}",
		// "id": 2}
		try {
			String question = obj.getJSONObject("corpo").getString("Pergunta");
			String alternative1 = obj.getJSONObject("corpo").getString(
					"Alternativa1");
			String alternative2 = obj.getJSONObject("corpo").getString(
					"Alternativa2");
			String alternative3 = obj.getJSONObject("corpo").getString(
					"Alternativa3");
			String alternative4 = obj.getJSONObject("corpo").getString(
					"Resposta");
			String rightAnswer[] = { obj.getJSONObject("corpo").getString(
					"Resposta") };

			String name = obj.getString("nome");
			Date currentDate = new Date();
			String fDate = new SimpleDateFormat("dd-MM-yyyy")
					.format(currentDate);

			MultipleCorrectChoiceExercise exercise = new MultipleCorrectChoiceExercise(
					name,
					DataBaseProfessor.getInstance(c).MULTIPLE_CORRECT_CHOICE_EXERCISE_TYPECODE,
					fDate, String.valueOf(com.educaTio.validation.Status.NEW), String
							.valueOf(Correction.NOT_RATED), question,
					alternative1, alternative2, alternative3, alternative4,
					rightAnswer);
			// exercise.getStatus().setStatus(getApplicationContext().getResources().getString(R.string.status_new));
			// exercise.getCorrection().setCorrection(getApplicationContext().getResources().getString(R.string.correction_not_rated));

			if (exerciseNameDontExists(exercise)) {
				// MainActivity.teacherDataBaseHelper.addExercise(exercise);

				DataBaseProfessor
						.getInstance(c)
						.addActivity(
								name,
								DataBaseProfessor
										.getInstance(c).MULTIPLE_CORRECT_CHOICE_EXERCISE_TYPECODE,
								exercise.getJsonTextObject());

			} else {
				Toast.makeText(
						c,
						c.getResources().getString(
								R.string.exercise_name_already_exists),
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean exerciseNameDontExists(Exercise exercise) {
		List<String> names = DataBaseProfessor.getInstance(
				c).getActivitiesName();

		for (String string : names) {
			if (string.equals(exercise.getName())) {
				return false;
			}
		}

		return true;
	}

}
