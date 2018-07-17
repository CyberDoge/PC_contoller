package org.project.cd.pccontroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
            if (connectionThread.isConnected())
                result.setText(connectionThread.getStreamsHelper().doInBackground(command.getText().toString()));
            else Toast.makeText(this, "not connected", Toast.LENGTH_SHORT).show();
        });
    }

}
