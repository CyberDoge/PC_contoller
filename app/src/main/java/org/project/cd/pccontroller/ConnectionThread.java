package org.project.cd.pccontroller;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConnectionThread extends AsyncTask<String, Void, Integer> {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private StreamsHelper streamsHelper = null;

    @Override
    protected Integer doInBackground(String... strings) {
        try {
            System.out.println("start");
            socket = new Socket(strings[0], Integer.parseInt(strings[1]));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            streamsHelper = new StreamsHelper();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    private void send(String message) {
        try {
            message +='\n';
            writer.write(message, 0, message.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String read() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            //todo
        }
        return null;
    }

    public StreamsHelper getStreamsHelper() {
        return streamsHelper;
    }


    public class StreamsHelper extends AsyncTask<String, Void, String> {
        private StreamsHelper() {
        }

        @Override
        protected String doInBackground(String... strings) {
            send(strings[0]);
            return read();
        }
    }
}
