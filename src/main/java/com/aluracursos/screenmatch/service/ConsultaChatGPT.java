package com.aluracursos.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {

    public static String obtenerTraduccion(String texto) {

        // 🔐 mejor usar variable de entorno
        String apiKey = "API KEY";
        OpenAiService service = new OpenAiService(apiKey);

        CompletionRequest requisicion = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")  // 🔥 modelo compatible
                .prompt("Traduce a español el siguiente texto: " + texto)
                .maxTokens(300)   // baja esto para evitar 429
                .temperature(0.7)
                .build();

        var respuesta = service.createCompletion(requisicion);
        return respuesta.getChoices().get(0).getText();
    }
}