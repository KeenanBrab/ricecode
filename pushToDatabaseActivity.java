package com.example.keenan.scanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static com.example.keenan.scanner.Activity2.MACHINE_LABEL;
import static com.example.keenan.scanner.Activity2.SEND_DATA;

public class pushToDatabaseActivity extends AppCompatActivity {
    private final String ACTIVITY_STARTUP_TAG = "pushToDatabaseActivity";
    private TextView mDataDisplay;
    private TextView mMachineLabel;
    private HashMap<String, String> machineData = new HashMap<>();
    private String machineLabel;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String timestamp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_to_database);

        mDataDisplay = (TextView) findViewById(R.id.tv_ContentDisplay);
        mMachineLabel = (TextView) findViewById(R.id.tv_machineLabel);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        machineLabel = extras.getString(MACHINE_LABEL);
        machineData = (HashMap<String, String>) extras.getSerializable(SEND_DATA);
        //machineData = (HashMap<String, String>) intent.getSerializableExtra(Activity2.SEND_DATA);
        Log.i(ACTIVITY_STARTUP_TAG, "pushToDatabasActivity Starting...");

        dataFormatForDisplay(machineData, machineLabel);

        myRef = database.getReference(machineLabel);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.push_to_database, menu);
        return true;
    }
    //handles clicks on the menu buttons, Click listener for sending data to a database...
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String correctedValue;
        //menu items are identified by integer i.d's
        int menuItemSelected = item.getItemId();
        //FIXME THE "No machine scanned" does not work properly.

        if (menuItemSelected == R.id.action_pushToDataBase)  {
            Log.i("pushtoDataBasePressed", "push to database pressed..." );
            //System.out.println("push to data is send: "+ machineData);
                timestamp = getDate();
                myRef.child(timestamp).setValue(machineData);
        }
        return super.onOptionsItemSelected(item);
    }

    private String getDate() {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return simpleDateFormat.format(calendar.getTime());
    }


    //method for formatting the machineData variable and display in view
    private void dataFormatForDisplay(HashMap<String,String> data, String label) {

        Set<String> keySet = data.keySet();
        Collection<String> values = data.values();

        ArrayList<String> listOfKeys = new ArrayList<String>(keySet);
        ArrayList<String> listOfValues = new ArrayList<String>(values);

        mMachineLabel.setText(label);

        for(HashMap.Entry<String, String> dataEntry : data.entrySet()) {
            mDataDisplay.append(dataEntry.getKey() + ": " + dataEntry.getValue() + "\n\n");
        }
    }
}
