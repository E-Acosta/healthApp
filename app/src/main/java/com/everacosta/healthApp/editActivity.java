package com.everacosta.healthApp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

import com.everacosta.healthApp.Modelos.contacto;
import com.everacosta.healthApp.Modelos.contactoContract;
import com.everacosta.healthApp.Modelos.contactoDBHelper;

import java.util.ArrayList;

public class editActivity extends AppCompatActivity {

    contactoDBHelper con;
    private ArrayList<contacto> contactos = new ArrayList<>();
    EditText etNombre, etApellido, etTelefono;
    Button birthdayButton, updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final int ID = Integer.valueOf(getIntent().getStringExtra("ID"));
        birthdayButton = findViewById(R.id.birthdayButton);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etTelefono = findViewById(R.id.etTelefono);
        updateButton = findViewById(R.id.ingresarButton);
        consultarSql(ID);
        writeInformation();
        birthdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSql(ID);
            }
        });
    }

    private void consultarSql(int ID) {
        con = new contactoDBHelper(this, "bd_registros", null, 1);
        try {
            SQLiteDatabase db = con.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + contactoContract.TABLE_NAME + " WHERE id =" + ID, null);
            if (cursor.moveToFirst()) {
                //while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(contactoContract.ID));
                String nombre = cursor.getString(cursor.getColumnIndex(contactoContract.NOMBRE));
                String apellido = cursor.getString(cursor.getColumnIndex(contactoContract.APELLIDO));
                String telefono = cursor.getString(cursor.getColumnIndex(contactoContract.TELEFONO));
                String cumpleaños = cursor.getString(cursor.getColumnIndex(contactoContract.CUMPLEAÑOS));
                cursor.moveToNext();
                contactos.add(new contacto(id, nombre, apellido, telefono, cumpleaños));
                // }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        // Toast.makeText(this, contactos.get(0).getNombre(), Toast.LENGTH_SHORT).show();
    }

    private void updateSql(int ID) {
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
        con = new contactoDBHelper(this, "bd_registros", null, 1);
        SQLiteDatabase db = con.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(contactoContract.NOMBRE, etNombre.getText().toString());
        values.put(contactoContract.APELLIDO, etApellido.getText().toString());
        values.put(contactoContract.TELEFONO, etTelefono.getText().toString());
        values.put(contactoContract.CUMPLEAÑOS, birthdayButton.getText().toString());
        long idResultado = db.update(contactoContract.TABLE_NAME, values, "id = ?", new String[]{String.valueOf(ID)});
        Toast.makeText(this, "Registro Actualizado:"+idResultado , Toast.LENGTH_SHORT).show();
        db.close();
        this.finish();
        startActivity(new Intent(editActivity.this,opcionesActivity.class));
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

    public void writeInformation() {
        etNombre.setText(contactos.get(0).getNombre());
        etApellido.setText(contactos.get(0).getApellido());
        etTelefono.setText(contactos.get(0).getTelefono());
        birthdayButton.setText(contactos.get(0).getCumpleaños());
    }
}
