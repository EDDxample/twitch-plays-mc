package eddxample.twitch;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ChatReader {
    private static final Pattern user_patt = Pattern.compile("(?<=^:)\\w+(?=!)");
    private static final Pattern msg_patt  = Pattern.compile("^:\\w+!\\w+@\\w+\\.tmi\\.twitch\\.tv PRIVMSG #\\w+ :");
    private static Socket socket;
    private static BufferedReader in;
    private static DataOutputStream out;

    public static void start(String channel) {
        try {
            socket = new Socket("irc.chat.twitch.tv", 6667);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());

            out.write("NICK justinfan420\n\r".getBytes("utf-8"));
            out.write(String.format("JOIN #%s\n\r", channel).getBytes("utf-8"));
            String response;
            while ((response = in.readLine()) != null) {
                process_response(response);
            }

        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void stop() {
        try { socket.close(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static void process_response(String res) throws IOException {
        if (res.startsWith("PING :tmi.twitch.tv")) {
            out.write("PONG :tmi.twitch.tv\n\r".getBytes("utf-8"));
        } else if (!res.startsWith(":justinfan420!")) {
            Matcher m = user_patt.matcher(res);
        }
    }
    public static void main(String[] args) {
        Matcher m = user_patt.matcher(":hmms185!hmms185@hmms185.tmi.twitch.tv PRIVMSG #rubius :una sub para mi por favor");
        System.out.println(m.group());
    }
}
