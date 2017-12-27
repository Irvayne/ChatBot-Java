package br.ufpi.lost;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
    	//token gerado pelo gerenciador de bot (botfather) do telegram
        TelegramBot tb = new TelegramBot("518059326:AAFCF5ij7uZy-2cl6_uIKsNgGzKYuDbALsc");
        try {
            tb.run();
        } catch (UnirestException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}