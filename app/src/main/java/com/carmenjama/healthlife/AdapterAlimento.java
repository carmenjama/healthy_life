package com.carmenjama.healthlife;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.healthLife.DAO.model.TempAlimento;

import java.util.List;

/**
 * Created by USUARIO on 20/08/2015.
 */
public class AdapterAlimento extends ArrayAdapter<TempAlimento> {
    private Activity context;
    private int layaoutMolde;
    private List<TempAlimento> tempAlimentos;

    public AdapterAlimento(Activity context, int layaoutMolde, List<TempAlimento> tempAlimentos) {
        super(context, layaoutMolde, tempAlimentos);
        this.context=context;
        this.layaoutMolde=layaoutMolde;
        this.tempAlimentos=tempAlimentos;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView =inflater.inflate(layaoutMolde, null);
        TempAlimento tempAlimentoActual = tempAlimentos.get(position);
        TextView datID = (TextView)convertView.findViewById(R.id.txtCod);
        TextView datNombre = (TextView)convertView.findViewById(R.id.textView15);
        datID.setText(tempAlimentoActual.getId().toString());
        datNombre.setText(tempAlimentoActual.getNombre());
        return convertView;
    }
}
