package org.project.cd.pccontroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Button mainBtn;
    private ConnectionThread connectionThread;
    private EditText command;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectionThread = new ConnectionThread();
        connectionThread.execute("192.168.0.104", "10000");
        result = findViewById(R.id.result);
        command = findViewById(R.id.command);
        mainBtn = findViewById(R.id.button);
        mainBtn.setOnClickListener((l) -> {
            if (connectionThread.isConnected()) {
                try {
                    result.setText(connectionThread.getStreamsHelper().execute(command.getText().toString()).get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            else Toast.makeText(this, "not connected", Toast.LENGTH_SHORT).show();
        });
    }

}
