package com.carmenjama.healthlife;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthLife.DAO.model.Enfermedad;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by USUARIO on 13/08/2015.
 */
public class ListViewEnfer extends ArrayAdapter <Enfermedad> {
    private Activity context;
    private int layaoutMolde;
    private List<Enfermedad> tempAlimentos;

    public ListViewEnfer(Activity context, int layaoutMolde, List<Enfermedad> tempAlimentos) {
        super(context, layaoutMolde, tempAlimentos);
        this.context=context;
        this.layaoutMolde=layaoutMolde;
        this.tempAlimentos=tempAlimentos;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView =inflater.inflate(layaoutMolde, null);
        Enfermedad tempEnfermedad = tempAlimentos.get(position);
        TextView datID = (TextView)convertView.findViewById(R.id.txtCod);
        TextView datNombre = (TextView)convertView.findViewById(R.id.textView15);
        datID.setText(tempEnfermedad.getId().toString());
        datNombre.setText(tempEnfermedad.getDescripcion());
        return convertView;
    }
}
