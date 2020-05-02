package com.everacosta.healthApp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.everacosta.healthApp.Modelos.contactoContract;
import com.everacosta.healthApp.Modelos.contactoDBHelper;

public class addContactActivity extends AppCompatActivity {
    EditText etNombre, etApellido, etTelefono;
    Button ingresarButton, birthdayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_contact);
        birthdayButton = findViewById(R.id.birthdayButton);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etTelefono = findViewById(R.id.etTelefono);
        ingresarButton = findViewById(R.id.ingresarButton);
        birthdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        ingresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noEmpty();

            }
        });
    }

    private void registrarUsuario() {
        contactoDBHelper conexion = new contactoDBHelper(this, "bd_registros", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(contactoContract.NOMBRE, etNombre.getText().toString());
        values.put(contactoContract.APELLIDO, etApellido.getText().toString());
        values.put(contactoContract.TELEFONO, etTelefono.getText().toString());
        values.put(contactoContract.CUMPLEAÑOS, birthdayButton.getText().toString());
        long idResultado = db.insert(contactoContract.TABLE_NAME, null, values);
        Toast.makeText(this, "Id registro: " + idResultado, Toast.LENGTH_SHORT).show();
        db.close();
        this.finish();
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                birthdayButton.setText(selectedDate);

            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void noEmpty() {
        if (Float.parseFloat(etTelefono.getText().toString())>=Float.parseFloat(etApellido.getText().toString())){
            etTelefono.setError("El Diastolico tiene que ser menor al Sistolico");
            return;
        }
        if (Float.parseFloat(etTelefono.getText().toString())<0){
            etTelefono.setError("El Diastolico no puede ser negativo");
            return;
        }
        if (0>Float.parseFloat(etApellido.getText().toString())){
            etApellido.setError("El sistolico no puede ser negativo");
            return;
        }
        if (TextUtils.isEmpty(etNombre.getText().toString())) {
            etNombre.setError("No hay nombre");
            return;
        }
        if (TextUtils.isEmpty(etApellido.getText().toString())) {
            etApellido.setError("No hay Sistolico");
            return;
        }
        if (TextUtils.isEmpty(etTelefono.getText().toString())) {
            etTelefono.setError("No hay Distolico");
            return;
        }
        if (TextUtils.isEmpty(birthdayButton.getText().toString())) {
            birthdayButton.setError("No hay Fecha");
            return;
        }

        registrarUsuario();
    }
}
