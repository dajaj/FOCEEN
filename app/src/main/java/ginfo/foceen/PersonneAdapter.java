package ginfo.foceen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dajaj on 23/09/15.
 */
public class PersonneAdapter extends BaseAdapter {

    List<Personne> listePersonne;

    LayoutInflater inflater;

    boolean showCheckBox;

    Event evenement;

    public PersonneAdapter(Context context,List<Personne> list) {
        inflater = LayoutInflater.from(context);
        listePersonne = list;
        showCheckBox = false;
        evenement = null;
    }

    public PersonneAdapter(Context context,List<Personne> list,Event event) {
        inflater = LayoutInflater.from(context);
        listePersonne = list;
        showCheckBox = true;
        evenement = event;
    }

    @Override
    public int getCount() {
        return listePersonne.size();
    }

    @Override
    public Object getItem(int position) {
        return listePersonne.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView nom;
        TextView prenom;
        CheckBox presence;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_personne_item, null);

            holder.nom = (TextView)convertView.findViewById(R.id.itemNom);
            holder.prenom = (TextView)convertView.findViewById(R.id.itemPrenom);
            holder.presence = (CheckBox)convertView.findViewById(R.id.itemPresence);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PresenceSql presenceSql = new PresenceSql(inflater.getContext());

        holder.nom.setText(listePersonne.get(position).getNom());
        holder.prenom.setText(listePersonne.get(position).getPrenom());
        if(showCheckBox) {
            holder.presence.setChecked(!presenceSql.getWithIdPersonneAndIdEvent(listePersonne.get(position).getId(), evenement.getId()).isEmpty());
            holder.presence.setVisibility(View.VISIBLE);
        }

        return convertView;

    }
}
