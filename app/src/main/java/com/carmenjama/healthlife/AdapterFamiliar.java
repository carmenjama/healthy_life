package com.carmenjama.healthlife;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.healthLife.DAO.model.TempAlimento;
import com.healthLife.DAO.model.TempPersona;

import java.util.List;

/**
 * Created by USUARIO on 23/08/2015.
 */
public class AdapterFamiliar extends ArrayAdapter<TempPersona> {
    private Activity context;
    private int layaoutMolde;
    private List<TempPersona> tempPersona;

    public AdapterFamiliar(Activity context, int layaoutMolde, List<TempPersona> tempPersona) {
        super(context, layaoutMolde, tempPersona);
        this.context=context;
        this.layaoutMolde=layaoutMolde;
        this.tempPersona=tempPersona;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView =inflater.inflate(layaoutMolde, null);
        TempPersona tempAlimentoActual = tempPersona.get(position);
        Spinner datNombre = (Spinner)convertView.findViewById(R.id.opFamilia);
        return convertView;
    }
}
