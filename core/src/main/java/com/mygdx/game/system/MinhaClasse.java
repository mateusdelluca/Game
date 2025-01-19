package com.mygdx.game.system;

import com.google.gson.Gson;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.levels.Level_Manager;

import java.io.*;

public class MinhaClasse {

    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(null);
        // Salvar o JSON em um arquivo
        try (FileWriter writer = new FileWriter("pessoa.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("pessoa.json")) {
            // Ler e converter o JSON para um objeto
            MinhaClasse pessoa = gson.fromJson(reader, MinhaClasse.class);

            // Exibir os dados carregados
            System.out.println(pessoa);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
