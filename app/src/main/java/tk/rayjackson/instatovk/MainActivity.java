package tk.rayjackson.instatovk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.httpClient.VKJsonOperation;
import com.vk.sdk.util.VKJsonHelper;
import com.vk.sdk.util.VKUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    User user;
    Activity activity;

    Button btnVkLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        btnVkLogIn = findViewById(R.id.btn_vk_login);
        btnVkLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.login(activity, "photos");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(final VKAccessToken res) {
                //TODO get user id
                VKRequest request = VKApi.users().get();
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        saveUser(response);
                        Intent intent = new Intent(MainActivity.this, FeedActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                        super.attemptFailed(request, attemptNumber, totalAttempts);
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                    }
                });
            }

            @Override
            public void onError(VKError error) {
                makeErrorToast(activity);
            }
        }))
            super.onActivityResult(requestCode, resultCode, data);
    }

    public void saveUser(VKResponse response) {
        try {
            JSONObject object = response.json.getJSONArray("response").getJSONObject(0);
            int id = object.getInt("id");
            String name = object.getString("first_name");
            String surname = object.getString("last_name");
            //TODO загрузка фотографий
            User user = new User(id, name, surname);
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
            user.toSharedPreferences(sp);
        } catch (JSONException e) {
            makeErrorToast(activity);
        }
    }

    public void makeErrorToast(Activity activity) {
        Toast.makeText(activity, "Something went wrong. Try again.", Toast.LENGTH_SHORT).show();
    }
}