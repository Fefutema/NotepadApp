package br.com.fiap.notepadapp;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etTitulo;
    private EditText etDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitulo = (EditText) findViewById(R.id.etTitulo);
        etDescricao = (EditText) findViewById(R.id.etDescricao);

    }

    public void salvar(View v){
        NotepadAPI notepadAPI = getRetrofit().create(NotepadAPI.class);

        if(etTitulo.getText().toString().equalsIgnoreCase("") || etDescricao.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(MainActivity.this, "Todos os campos sao obrigatorios !!!", Toast.LENGTH_LONG).show();
        } else {

            Nota nota = new Nota();
            nota.setTitulo(etTitulo.getText().toString());
            nota.setTexto(etDescricao.getText().toString());

            notepadAPI.salvar(nota).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(MainActivity.this, "Nota salva com sucesso", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Ocorreu um erro ao salvar !!!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void pesquisar(View v){

        NotepadAPI notepadAPI = getRetrofit().create(NotepadAPI.class);

        if(etTitulo.getText() == null || etTitulo.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(MainActivity.this, "O campo titulo é obrigatorio !!!", Toast.LENGTH_LONG).show();
        }else {

            notepadAPI.buscar(etTitulo.getText().toString())
                    .enqueue(new Callback<Nota>() {
                        @Override
                        public void onResponse(Call<Nota> call, Response<Nota> response) {
                            etDescricao.setText(response.body().getTexto());
                        }

                        @Override
                        public void onFailure(Call<Nota> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Ocorreu um erro ao buscar !!!", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    public Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("https://notepadfelipefutema.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
