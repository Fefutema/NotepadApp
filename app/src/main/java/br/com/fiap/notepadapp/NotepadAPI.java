package br.com.fiap.notepadapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotepadAPI {

    @GET(value = "/nota/titulo/{titulo}")
    Call<Nota> buscar(@Path(value = "titulo") String titulo);

    @GET(value = "/nota")
    Call<List<Nota>> buscar();

    @POST(value = "/nota")
    Call<Void> salvar(@Body Nota nota);

    @DELETE(value = "/nota/titulo/{titulo}")
    Call<Void> deletar(@Path(value = "titulo") String titulo);

}
