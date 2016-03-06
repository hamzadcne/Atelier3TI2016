package Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableWrapper;
import android.support.v7.graphics.drawable.DrawableUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lvsc.tp3ti.R;

import Models.Pharmacie;

/**
 * Created by LVSC on 06-03-2016.
 */
public class PharmacieArrayAdapter extends ArrayAdapter<Pharmacie> {
    Context myContext;
    int layoutResourceId;
    Pharmacie[] pharmacies;

    public PharmacieArrayAdapter(Context context, int resource,Pharmacie[] objects) {
        super(context, resource, objects);

        myContext=context;
        layoutResourceId=resource;
        pharmacies=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        Pharmacie current=pharmacies[position];

        TextView nomTextView= (TextView) convertView.findViewById(R.id.textViewNom);
        TextView villeTextView= (TextView) convertView.findViewById(R.id.txtViewVille);
        TextView latTextView= (TextView) convertView.findViewById(R.id.txtViewLat);
        TextView lonTextView= (TextView) convertView.findViewById(R.id.txtViewLong);


        nomTextView.setText(current.getNom());
        villeTextView.setText(current.getVille());
        latTextView.setText(current.getLatitude().toString());
        lonTextView.setText(current.getLongitude().toString());

        return convertView;
    }
}
