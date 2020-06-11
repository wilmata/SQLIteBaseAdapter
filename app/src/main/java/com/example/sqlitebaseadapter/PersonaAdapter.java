package com.example.sqlitebaseadapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonaAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<Persona> items;
	private int layout;
    
    public PersonaAdapter() { }
    
	public PersonaAdapter(Context ctx, int layout,  ArrayList<Persona> items) {
		this.ctx = ctx;
		this.items = items;
		this.layout = layout;
	}

	@Override
	public int getCount() {
		return items.size();
	}
	@Override
	public Persona getItem(int arg0) {
		return items.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		return items.get(arg0).getId();
	}
	@Override
	public View getView(int arg0, View convertView, ViewGroup parent) {

		View v = convertView;
		
		if(convertView == null){
			LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(layout, null);
		}
		
		Persona r = getItem(arg0);
		ImageView imguser = (ImageView)v.findViewById(android.R.id.icon);
		TextView nombre = (TextView)v.findViewById(android.R.id.text1);
		TextView telefono = (TextView)v.findViewById(android.R.id.text2);

		if(imguser != null) {
			if (r.getSexo() == 0)
				imguser.setImageResource(R.drawable.usuario_varon);
			else if(r.getSexo() == 1)
				imguser.setImageResource(R.drawable.usuario_mujer);
		}

		if(nombre != null)
			nombre.setText(r.getNombre());
		if(telefono != null)
			telefono.setText(r.getTelefono());

		return v;
	}
}
