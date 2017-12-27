package br.ufpi.lost;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

public final class TelegramBot {

    private final String endpoint = "https://api.telegram.org/";
    private final String token;

    public TelegramBot(String token) {
        this.token = token;
    }

    public HttpResponse<JsonNode> sendMessage(Integer chatId, String text) throws UnirestException {
        return Unirest.post(endpoint + "bot" + token + "/sendMessage")
                .field("chat_id", chatId)
                .field("text", text)
                .asJson();
    }

    public HttpResponse<JsonNode> getUpdates(Integer offset) throws UnirestException {
        return Unirest.post(endpoint + "bot" + token + "/getUpdates")
                .field("offset", offset)
                .asJson();
    }

    public void run() throws UnirestException {
        int last_update_id = 0; // controle das mensagens processadas
        HttpResponse<JsonNode> response;
        while (true) {
            response = getUpdates(last_update_id++);
            if (response.getStatus() == 200) {
                JSONArray responses = response.getBody().getObject().getJSONArray("result");
                if (responses.isNull(0)) {
                    continue;
                } else {
                    last_update_id = responses
                            .getJSONObject(responses.length() - 1)
                            .getInt("update_id") + 1;
                }

                for (int i = 0; i < responses.length(); i++) {
                	//mensagem enviada pelo chat
                    JSONObject message = responses
                            .getJSONObject(i)
                            .getJSONObject("message");
                    int chat_id = message
                            .getJSONObject("chat")
                            .getInt("id");
                    String texto = message
                            .getString("text");
                    String resposta = "";
                    if(texto.equals("1")) {
                    	resposta += "PARTICIPANTES:\n"
                    			+ "Pedro Santos Neto\n"
                    			+ "Irvayne Matheus\n"
                    			+ "Vanderson Moura\n"
                    			+ "Gleison Andrade"
                    			+ "\n\nO que você deseja saber sobre o laboratório?\n"
                         		+ "1 - Participantes\n"
                         		+ "2 - Projetos\n"
                         		+ "3 - Ferramentas\n";
                    }else if(texto.equals("2")) {
                    	resposta += "PROJETOS:\n"
                    			+ "Identificar familiaridade em projetos de software\n"
                    			+ "Auxiliar na correção automatizada de provas\n"
                    			+ "Identificar problemas de usabilidade\n"
                    			+ "Gerenciar rebanhos"
		                    	+ "\n\nO que você deseja saber sobre o laboratório?\n"
		                 		+ "1 - Participantes\n"
		                 		+ "2 - Projetos\n"
		                 		+ "3 - Ferramentas\n";
                    }else if(texto.equals("3")) {
                    	resposta += "FERRAMENTAS:\n"
                    			+ "Codivision - easii.ufpi.br/codivision\n"
                    			+ "Iscool - easii.ufpi.br/iscool\n"
                    			+ "UseSkill - easii.ufpi.br/useskill\n"
                    			+ "\n\nO que você deseja saber sobre o laboratório?\n"
                         		+ "1 - Participantes\n"
                         		+ "2 - Projetos\n"
                         		+ "3 - Ferramentas\n";
                    }else {
                    	 resposta += "Olá, eu sou o BotLost, o bot do Laboratório LOST!!!\n"
                         		+ "O que você deseja saber sobre o laboratório?\n"
                         		+ "1 - Participantes\n"
                         		+ "2 - Projetos\n"
                         		+ "3 - Ferramentas\n";
                    }
                    //mensagem a ser enviada
                    sendMessage(chat_id, resposta);
                }
            }
        }
    }
}