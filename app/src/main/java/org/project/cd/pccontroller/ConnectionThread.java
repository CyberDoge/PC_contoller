package org.project.cd.pccontroller;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Looper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionThread extends AsyncTask<String, Void, Integer> {
    private Socket socket;
    private DataOutputStream writer;
    private DataInputStream reader;
    private ConnectionInputListener connectionInput;
    private View view;

    public ConnectionThread(View view) {
        this.view = view;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        try {
            socket = tryConnect(strings[0], Integer.parseInt(strings[1]));
            writer = new DataOutputStream(socket.getOutputStream());
            reader = new DataInputStream(socket.getInputStream());
            connectionInput = new ConnectionInputListener();
            connectionInput.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Socket tryConnect(String host, int port) throws InterruptedException {
        Socket socket;
        Looper.prepare();
        while (true) {
            try {
                socket = new Socket(host, port);
            } catch (IOException e) {
                view.printInfo("try connect");
                Thread.sleep(1000);
                continue;
            }
            return socket;
        }
    }


    public void send(String message) {
        if (socket != null && socket.isConnected()) {
            try {
                writer.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else view.printError("not connected");
    }

    private class ConnectionInputListener extends AsyncTask<Void, Void, Void> {
        private ConnectionInputListener() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                while (true) {
                    view.printOutput(reader.readUTF());
                }
            } catch (IOException e) {
                view.printError(e.getLocalizedMessage());
            }
            return null;
        }
    }
}
