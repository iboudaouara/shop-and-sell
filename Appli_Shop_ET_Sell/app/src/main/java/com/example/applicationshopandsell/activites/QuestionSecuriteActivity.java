package com.example.applicationshopandsell.activites;

import android.app.*;
import android.content.*;
import android.os.*;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.applicationshopandsell.R;
import com.example.applicationshopandsell.adapteurs.SpinnerAdapter;
import com.example.applicationshopandsell.ressources.API;
import org.json.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class QuestionSecuriteActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerQuestionSecurite1;
    private Spinner spinnerQuestionSecurite2;

    private EditText edtQuestion1;
    private EditText edtQuestion2;

    /* À récupérer pour la création de comptes */
    private String questionDeSecurite1;
    private String questionDeSecurite2;

    /* À récupérer lors de la réinitilisation de mot de passe */
    private String email;
    private String question1;
    private String question2;

    private Button btnConfirmerChoix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_securite);

        Intent intentionRecupInfo = getIntent();
        email = intentionRecupInfo.getStringExtra("email");

        /* Récupérer l'email de la session de l'utilisateur */
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();

        question1 = intentionRecupInfo.getStringExtra("question1");
        question2 = intentionRecupInfo.getStringExtra("question2");

        spinnerQuestionSecurite1 = findViewById(R.id.spinnerQuestion1);

        List<String> questionSecurite1;

        if (question1 != null) {
            questionSecurite1 = new ArrayList<>();
            questionSecurite1.add(question1);
        } else {
            questionSecurite1 = Arrays.asList(getResources().
                    getStringArray(R.array.questions_securite_1));
        }

        SpinnerAdapter adapter1 = new SpinnerAdapter(
                this, android.R.layout.simple_spinner_item, questionSecurite1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuestionSecurite1.setAdapter(adapter1);

        if (question1 != null) {
            int position1 = questionSecurite1.indexOf(question1);
            if (position1 >= 0) {
                spinnerQuestionSecurite1.setSelection(position1);

            } else {
                Toast.makeText(this, "Question non trouvée.", Toast.LENGTH_SHORT).show();
            }
        }

        spinnerQuestionSecurite2 = findViewById(R.id.spinnerQuestion2);

        List<String> questionSecurite2;

        if (question2 != null) {
            questionSecurite2 = new ArrayList<>();
            questionSecurite2.add(question2);
        } else {
            questionSecurite2 = Arrays.asList(getResources().
                    getStringArray(R.array.questions_securite_2));
        }

        SpinnerAdapter adapter2 = new SpinnerAdapter(
                this, android.R.layout.simple_spinner_item, questionSecurite2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuestionSecurite2.setAdapter(adapter2);

        if (question2 != null) {
            int position2 = questionSecurite2.indexOf(question2);
            if (position2 >= 0) {
                spinnerQuestionSecurite2.setSelection(position2);

            } else {
                Toast.makeText(this, "Question non trouvée.", Toast.LENGTH_SHORT).show();
            }
        }

        edtQuestion1 = findViewById(R.id.edtQuestion1);
        edtQuestion2 = findViewById(R.id.edtQuestion2);


        btnConfirmerChoix = findViewById(R.id.btnConfimerChoix);
        btnConfirmerChoix.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        String reponseSecurite1 = edtQuestion1.getText().toString();
        String reponseSecurite2 = edtQuestion2.getText().toString();

        if (question1 != null && question2 != null) {

            new VerifySecurityAnswersTask().execute(
                    email,
                    reponseSecurite1,
                    reponseSecurite2
            );

        } else {

            String selectedQuestion1 = spinnerQuestionSecurite1.getSelectedItem().toString();
            String selectedQuestion2 = spinnerQuestionSecurite2.getSelectedItem().toString();
            String answer1 = edtQuestion1.getText().toString();
            String answer2 = edtQuestion2.getText().toString();

            Intent envoyerQS = new Intent();
            envoyerQS.putExtra("question1", selectedQuestion1);
            envoyerQS.putExtra("question2", selectedQuestion2);
            envoyerQS.putExtra("answer1", answer1);
            envoyerQS.putExtra("answer2", answer2);
            setResult(RESULT_OK, envoyerQS);
            finish();
        }

    }
    private class VerifySecurityAnswersTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String email = params[0]; // Assuming the email is passed as the first parameter
            String answer1 = params[1];
            String answer2 = params[2];

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(API.URL_POINT_ENTREE + "/api/check_security_answers/mobile");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                // Create JSON payload
                JSONObject jsonInput = new JSONObject();
                jsonInput.put("email", email); // Assuming email needs to be included
                JSONArray answersArray = new JSONArray();
                answersArray.put(answer1);
                answersArray.put(answer2);
                jsonInput.put("answers", answersArray);

                // Write JSON to output stream
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                // Get response
                int responseCode = connection.getResponseCode();
                System.out.println(responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read response
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = reader.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject response = new JSONObject(result);
                    if (response.has("error")) {
                        Toast.makeText(QuestionSecuriteActivity.this, "Error: " + response.getString("error"), Toast.LENGTH_LONG).show();
                    } else {
                        String message = response.getString("message");
                        Toast.makeText(QuestionSecuriteActivity.this, message, Toast.LENGTH_LONG).show();
                        // Show reset password dialog
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        String email = sharedPreferences.getString("email", null);
                        if (email != null) {
                            showPasswordDialog(email);
                        } else {
                            Toast.makeText(QuestionSecuriteActivity.this, "Email not found in SharedPreferences", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(QuestionSecuriteActivity.this, "Error parsing response", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(QuestionSecuriteActivity.this, "Connection erroYOYOr", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showPasswordDialog(final String email) {
        // Create an EditText view for user input
        final EditText input = new EditText(this);
        input.setHint("Enter new password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Create and show the dialog
        new AlertDialog.Builder(this)
                .setTitle("Reset Password")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the password input and proceed with password reset
                        String newPassword = input.getText().toString();

                        if (!newPassword.isEmpty()) {
                            new ResetPasswordTask().execute(email, newPassword);
                        } else {
                            Toast.makeText(QuestionSecuriteActivity.this, "Enter new password", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private class ResetPasswordTask extends AsyncTask<String, Void, String> {
        private static final String TAG = "ResetPasswordTask";

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String newPassword = params[1];
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(API.URL_POINT_ENTREE + "/api/reset_password/mobile");
                Log.d(TAG, "URL: " + url.toString());


                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                // Create JSON payload
                JSONObject jsonInput = new JSONObject();
                jsonInput.put("email", email);
                jsonInput.put("newPassword", newPassword);
                Log.d(TAG, "JSON Payload: " + jsonInput.toString()); // Log the JSON payload

                // Write JSON to output stream
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // Get response
                int responseCode = connection.getResponseCode();
                Log.d(TAG, "Response Code: " + responseCode); // Log the response code
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read response
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = reader.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error: " + e.getMessage()); // Log any exceptions
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Error closing reader: " + e.getMessage()); // Log error closing reader
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject response = new JSONObject(result);
                    if (response.has("error")) {
                        Toast.makeText(QuestionSecuriteActivity.this, "Error: " + response.getString("error"), Toast.LENGTH_LONG).show();
                    } else {
                        String message = response.getString("message");
                        Toast.makeText(QuestionSecuriteActivity.this, message, Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(QuestionSecuriteActivity.this, "Error parsing response", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Error parsing response: " + e.getMessage()); // Log error parsing response
                }
            } else {
                Toast.makeText(QuestionSecuriteActivity.this, "Connection error", Toast.LENGTH_LONG).show();
            }
        }
    }
}