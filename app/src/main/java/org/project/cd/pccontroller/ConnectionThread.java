package org.project.cd.pccontroller;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionThread extends AsyncTask<String, Void, Integer> {
    private Socket socket;
    private DataOutputStream writer;
    private DataInputStream reader;
    private StreamsHelper streamsHelper = null;

    @Override
    protected Integer doInBackground(String... strings) {
        System.out.println("start");
        try {
            /*InetSocketAddress address = new InetSocketAddress(strings[0], Integer.parseInt(strings[1]));
            while (!address.isUnresolved()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            socket = new Socket(strings[0], Integer.parseInt(strings[1]));
            writer = new DataOutputStream(socket.getOutputStream());
            reader = new DataInputStream(socket.getInputStream());
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
            writer.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String read() {
        try {
            return reader.readUTF();
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
