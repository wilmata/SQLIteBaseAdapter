package com.example.sqlitebaseadapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.example.sqlitebaseadapter.PersonaContract.PersonaEntry;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    PersonaDbHelper personaDbHelper;
    SQLiteDatabase db;
    ArrayList<Persona> datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        registerForContextMenu(listView);

        personaDbHelper = new PersonaDbHelper(this);
        db = personaDbHelper.getWritableDatabase();
        CargarPersonas();
    }

    public void CargarPersonas()
    {
        String [] columns = {
                PersonaEntry._ID,
                PersonaEntry.COLUMN_NAME_NAME,
                PersonaEntry.COLUMN_NAME_PHONE,
                PersonaEntry.COLUMN_NAME_SEX
        };

        Cursor cursor = db.query(PersonaEntry.TABLE_NAME, columns, null, null, null, null, null);

        datos = new ArrayList<Persona>();
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                int id = cursor.getInt(cursor.getColumnIndex(PersonaEntry._ID));
                String nombre = cursor.getString(cursor.getColumnIndex(PersonaEntry.COLUMN_NAME_NAME));
                String telefono = cursor.getString(cursor.getColumnIndex(PersonaEntry.COLUMN_NAME_PHONE));
                int sexo = cursor.getInt(cursor.getColumnIndex(PersonaEntry.COLUMN_NAME_SEX));

                Persona p = new Persona(id, nombre, telefono, sexo);
                datos.add(p);

                cursor.moveToNext();
            }
        }

        PersonaAdapter adapter = new PersonaAdapter(this, R.layout.modelo_lista, datos);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_nuevo:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.add_edit_layout, null);
                final EditText etNombre = (EditText)v.findViewById(R.id.etNombre);
                final EditText etTelefono = (EditText)v.findViewById(R.id.etTelefono);
                final RadioButton rbMasculino  = (RadioButton)v.findViewById(R.id.rbMasculino);
                final RadioButton rbFemenino  = (RadioButton)v.findViewById(R.id.rbFemenino);

                builder.setTitle("Agregar registro");
                builder.setView(v);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //codigo para agregar un nuevo registro
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(PersonaEntry.COLUMN_NAME_NAME, etNombre.getText().toString());
                        contentValues.put(PersonaEntry.COLUMN_NAME_PHONE, etTelefono.getText().toString());

                        int sexo = -1;
                        if(rbMasculino.isChecked())
                            sexo = 0;
                        else if(rbFemenino.isChecked())
                            sexo = 1;

                        contentValues.put(PersonaEntry.COLUMN_NAME_SEX, sexo);

                        MainActivity.this.db.insert(PersonaEntry.TABLE_NAME, null, contentValues);
                        MainActivity.this.CargarPersonas();
                        Toast.makeText(MainActivity.this, "Registro agregado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        //mover el cursor al registro que se mantuvo pulsado
        final int _id = datos.get(info.position).getId();

        switch (item.getItemId()) {
            case R.id.edit:


                //editar registro
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.add_edit_layout, null);
                final EditText etNombre = (EditText)v.findViewById(R.id.etNombre);
                final EditText etTelefono = (EditText)v.findViewById(R.id.etTelefono);
                final RadioButton rbMasculino  = (RadioButton)v.findViewById(R.id.rbMasculino);
                final RadioButton rbFemenino  = (RadioButton)v.findViewById(R.id.rbFemenino);


                //Obtener nombre y telefono del cursor y ponerlo en los EditText correspondientes
                String nombre = datos.get(info.position).getNombre();
                String telefono = datos.get(info.position).getTelefono();
                final int sexo = datos.get(info.position).getSexo();

                rbMasculino.setChecked(sexo == 0);
                rbFemenino.setChecked(sexo == 1);

                etNombre.setText(nombre);
                etTelefono.setText(telefono);

                builder.setTitle("Editar registro");
                builder.setView(v);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //codigo para editar registro
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(PersonaEntry.COLUMN_NAME_NAME, etNombre.getText().toString());
                        contentValues.put(PersonaEntry.COLUMN_NAME_PHONE, etTelefono.getText().toString());

                        int sexo = -1;
                        if(rbMasculino.isChecked())
                            sexo = 0;
                        else if(rbFemenino.isChecked())
                            sexo = 1;

                        contentValues.put(PersonaEntry.COLUMN_NAME_SEX, sexo);

                        String where = PersonaEntry._ID + " = '" + _id + "'";
                        MainActivity.this.db.update(PersonaEntry.TABLE_NAME, contentValues, where, null);
                        MainActivity.this.CargarPersonas();
                        Toast.makeText(MainActivity.this, "Registro editado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();
                return true;
            case R.id.delete:
                //borrar registro
                String where = PersonaEntry._ID + " = '" + _id + "'";
                db.delete(PersonaEntry.TABLE_NAME, where,null);
                CargarPersonas();
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        personaDbHelper.close();
        super.onDestroy();
    }
}