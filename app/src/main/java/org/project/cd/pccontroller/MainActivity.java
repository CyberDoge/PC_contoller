package org.project.cd.pccontroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View {

    private Button mainBtn;
    private ConnectionThread connectionThread;
    private EditText command;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectionThread = new ConnectionThread(this);
        connectionThread.execute("192.168.0.104", "10000");
        result = findViewById(R.id.result);
        command = findViewById(R.id.command);
        mainBtn = findViewById(R.id.button);
        mainBtn.setOnClickListener((l) -> connectionThread.send(command.getText().toString()));
    }

    @Override
    public void printError(String error) {
        //todo normal error output
        runOnUiThread(() -> Toast.makeText(this, error, Toast.LENGTH_LONG).show());
    }

    @Override
    public void printInfo(String info) {
        runOnUiThread(() -> Toast.makeText(this, info, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void printOutput(String output) {
        runOnUiThread(() -> result.setText(output));
    }
}
