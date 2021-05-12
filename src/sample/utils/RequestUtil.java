package sample.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class RequestUtil implements Runnable {
    private static final String ROOT_URL = "http://localhost:8080";
    /**
     * Максимальное время ожидания подключения к серверу, в миллисекундах
     */
    private static final int TIMEOUT = 5000;
    /**
     * Состояние соединения с сервером
     */
    private Boolean disconnect = false;
    /**
     * Параметры, которые будут добавлены в запрос
     */
    private Map<String, String> params;
    /**
     * Один из следующих методов запроса: GET, POST, PUT, DELETE
     */
    private final String method;

    private String json;
    /**
     * Ответ сервера на запрос
     */
    private String response;
    /**
     * Выполнение потока запроса к серверу
     */
    public Thread thread;
    /**
     * Относительный URL-путь, по которому запрос будет отправлен на сервер
     */
    private URL url;
    /**
     * Способ запуска потока выполнения запроса на сервер и получения ответа от сервера
     */
    @Override
    public void run() {
        System.out.println("Name thread: " + thread.getName());
        response = send(thread.getName());
    }
    /**
     * Конструктор для класса RequestUtil
     *
     * @param url relative url-путь, по которому запрос будет отправлен на сервер
     * метод @param один из следующих методов запроса: GET, POST, PUT, DELETE
     */
    public RequestUtil(String url, String method) {
        this.method = method;
        this.thread = new Thread(this, url);
    }

    public Map<String, String> getParams() {
        return params;
    }
    /**
     * Способ отправки запроса на сервер
     *
     * @param url  url-путь, по которому запрос будет отправлен на сервер
     * @return строка ответа с сервера
     */
    public String send(String url) {
        try {
            System.out.println(ROOT_URL + url);
            this.url = new URL(ROOT_URL + url);
            HttpURLConnection conn = (HttpURLConnection) this.url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty( "Accept", "application/json");
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            if (method.equals("POST") || method.equals("PUT")) {
                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.getBytes("utf-8");
                    os.write(input);
                }
            }


//            if (method.equals("POST")) {
//                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//                wr.writeBytes(json);
//                wr.write(json);
//            }

            System.out.println(conn.getResponseCode());

            return readInputStream(conn);
        } catch (ConnectException e) {
            setDisconnect(true);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
    /**
     * Сеттер состояния соединения с сервером
     *
     * @param disconnect состояние подключения
     */
    public void setDisconnect(Boolean disconnect) {
        this.disconnect = disconnect;
    }
    /**
     * Сеттер параметров метода запроса
     *
     * @param  params метода запроса
     */
    public void setParams(Map<String, String> params) { this.params = params; }
    /**
     * Геттер для ответа от сервера
     *
     * @return строка ответа
     */
    public String getResponse() { return response; }
    /**
     * Геттер для состояния подключения к серверу
     *
     * @return true, если соединение открыто, или false, если верно обратное
     */
    public Boolean getDisconnect() { return disconnect; }

    public static int getTIMEOUT() { return TIMEOUT; }
    /**
     * Геттер для относительного url-пути
     *
     * @return  url
     */
    public URL getUrl() { return url; }
    /**
     * Чтение входного потока для получения ответа от сервера
     *
     * @param conn HTTP с сервером
     * @return строка ответа с сервера
     */
    private static String readInputStream(HttpURLConnection conn) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = in.readLine()) != null) {
                content.append(line);
            }
            conn.disconnect();
            return content.toString();
        } catch (IOException e) {
            conn.disconnect();
            return null;
        }
    }

}
