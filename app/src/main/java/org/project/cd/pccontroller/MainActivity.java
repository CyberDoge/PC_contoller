package org.project.cd.pccontroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Button mainBtn;
    private ConnectionThread connectionThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectionThread = new ConnectionThread();
        connectionThread.execute("192.168.0.104", "10000");
        mainBtn = findViewById(R.id.button);
        mainBtn.setOnClickListener((l) -> {
            try {
                if (connectionThread.isConnected())
                    System.out.println("result = " + connectionThread.getStreamsHelper().execute("hello").get());
                else Toast.makeText(this, "not connected", Toast.LENGTH_SHORT).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
