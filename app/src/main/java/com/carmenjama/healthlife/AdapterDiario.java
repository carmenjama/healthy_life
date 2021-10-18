package com.carmenjama.healthlife;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.healthLife.DAO.model.Alimento;
import com.healthLife.DAO.model.TempDiario;

import java.util.List;

/**
 * Created by USUARIO on 25/08/2015.
 */
public class AdapterDiario extends ArrayAdapter<TempDiario> {
    private Activity context;
    private int layaoutMolde;
    private List<TempDiario> tempAlimentos;

    public AdapterDiario(Activity context, int layaoutMolde, List<TempDiario> tempAlimentos) {
        super(context, layaoutMolde, tempAlimentos);
        this.context=context;
        this.layaoutMolde=layaoutMolde;
        this.tempAlimentos=tempAlimentos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView =inflater.inflate(layaoutMolde, null);
        TempDiario actual = tempAlimentos.get(position);
        TextView datNombe = (TextView)convertView.findViewById(R.id.textView24);
        TextView datCaloria = (TextView)convertView.findViewById(R.id.txtGrasa);
        TextView datPorcion = (TextView)convertView.findViewById(R.id.txtProteina);
        TextView datTotal = (TextView)convertView.findViewById(R.id.txtCarbo);

        datNombe.setText(actual.getNombre());
        datCaloria.setText(String.format("%.2f", actual.getCalorias()));
        datPorcion.setText(String.format("%.2f", actual.getPorcion()));

        datTotal.setText(String.format("%.2f", actual.getTotal()));
        return convertView;
    }
}
