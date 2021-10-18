package com.carmenjama.healthlife;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.healthLife.DAO.model.Alimento;
import com.healthLife.DAO.model.TempAlimento;

import java.util.List;

/**
 * Created by USUARIO on 28/08/2015.
 */
public class AdapterCat extends ArrayAdapter<Alimento> {
    private Activity context;
    private int layaoutMolde;
    private List<Alimento> tempAlimentos;

    public AdapterCat(Activity context, int layaoutMolde, List<Alimento> tempAlimentos) {
        super(context, layaoutMolde, tempAlimentos);
        this.context=context;
        this.layaoutMolde=layaoutMolde;
        this.tempAlimentos=tempAlimentos;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView =inflater.inflate(layaoutMolde, null);
        Alimento tempAlimentoActual = tempAlimentos.get(position);
        TextView d0 = (TextView)convertView.findViewById(R.id.textView24);
        TextView d1 = (TextView)convertView.findViewById(R.id.txtCal);
        TextView d2 = (TextView)convertView.findViewById(R.id.txtGra);
        TextView d3 = (TextView)convertView.findViewById(R.id.txtPro);
        TextView d4 = (TextView)convertView.findViewById(R.id.txtCarbo);

        d0.setText(tempAlimentoActual.getNombre());
        d1.setText(tempAlimentoActual.getCalorias().toString());
        d2.setText(tempAlimentoActual.getGrasas().toString());
        d3.setText(tempAlimentoActual.getProteinas().toString());
        d4.setText(tempAlimentoActual.getCarbohidratos().toString());
        return convertView;
    }
}
