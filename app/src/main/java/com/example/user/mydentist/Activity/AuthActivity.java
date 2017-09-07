package com.example.user.mydentist.Activity;


import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.user.mydentist.ApiService.AuthService;
import com.example.user.mydentist.ApiService.TokenServise;
import com.example.user.mydentist.R;
import com.example.user.mydentist.RxEditText;

import java.io.IOException;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {


    EditText editLogin;
    EditText editPass;
    Button register;
    public final String AUTH_TOKEN = "app_token";
    public final String AUTH_ID = "app_id";
    public  boolean next = false;
    private String login = "";
    private String pass = "";
    private String token = "";
    private String id = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        editLogin = (EditText) findViewById(R.id.editText);
        editPass = (EditText) findViewById(R.id.editText2);
        register = (Button) findViewById(R.id.button);
        register.setEnabled(false);

        Observable<String> loginObservable = RxEditText.getTextWatcherObsevable(editLogin);
        Observable<String> passObservable = RxEditText.getTextWatcherObsevable(editPass);
        Observable.combineLatest(loginObservable,passObservable, new BiFunction<String, String, Boolean>() {

            @Override
            public Boolean apply(@NonNull String s, @NonNull String s2) throws Exception {
                if(s.isEmpty() || s2.isEmpty())
                    return false;
                else
                    return true;
            }
        }).subscribe(new Consumer<Boolean>() {

            @Override
            public void accept(Boolean aBoolean) throws Exception {
                register.setEnabled(aBoolean);
               // register.setTextColor(getResources().getColor(R.color.BtnAuthColor));

            }
        });

    }

    public void OnClick(View v) {

        this.next = true;
        login = editLogin.getText().toString();
        pass = editPass.getText().toString();
        AuthService authService = AuthService.retrofit.create(AuthService.class);
        final Call<ResponseBody> call = authService.getToken(login, pass);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {

                        token = response.body().string();
                        SharedPreferences.Editor editor = StartActivity.preferences.edit();
                        editor.putString(AUTH_TOKEN, token);
                        editor.apply();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),
                                "Ошибка!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    Log.d("zzzzz", token);

                    TokenServise tokenServise = TokenServise.retrofit.create(TokenServise.class);
                    Call<ResponseBody> call1 = tokenServise.getId(token);

                    call1.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful())
                                try {
                                    id = response.body().string();
                                    SharedPreferences.Editor editor = StartActivity.preferences.edit();
                                    editor.putString(AUTH_ID, id);
                                    editor.apply();
                                    Toast.makeText(getApplicationContext(),
                                            "ID пользователя " + id, Toast.LENGTH_SHORT).show();


                                } catch (IOException e) {
                                    Toast.makeText(getApplicationContext(),
                                            "Ошибка!", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            Log.d("zzzzz", id);

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "Ошибка!", Toast.LENGTH_SHORT).show();

            }


        });
        doStart(next);




    }
    public void doStart(boolean next){
        if (next) {
           // SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));   // использовать countdowntimer
            Intent intent = new Intent(this, HeadActivity.class);
            startActivity(intent);
            finish();
        }

    }
}

