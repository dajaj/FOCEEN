package ginfo.foceen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dajaj on 24/09/15.
 */
public class ExterneAdapter extends BaseAdapter {

    List<Externe> listeExterne;

    LayoutInflater inflater;

    boolean showNbr;

    Event evenement;

    public ExterneAdapter(Context context,List<Externe> list) {
        inflater = LayoutInflater.from(context);
        listeExterne = list;
        showNbr = false;
        evenement = null;
    }

    public ExterneAdapter(Context context,List<Externe> list,Event event) {
        inflater = LayoutInflater.from(context);
        listeExterne = list;
        showNbr = true;
        evenement = event;
    }

    @Override
    public int getCount() {
        return listeExterne.size();
    }

    @Override
    public Object getItem(int position) {
        return listeExterne.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView nom;
        TextView nbr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_extern_item, null);

            holder.nom = (TextView)convertView.findViewById(R.id.itemNomEcoleExterne);
            holder.nbr = (TextView)convertView.findViewById(R.id.itemNombreParticipants);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PresenceExterneSql presenceExterneSql = new PresenceExterneSql(inflater.getContext());

        Log.d("ExterneAdapter",position+" "+listeExterne.get(position).getNomEcole());
        holder.nom.setText(listeExterne.get(position).getNomEcole());
        if(showNbr)
        {
            Log.d("ExterneAdapter", String.valueOf(presenceExterneSql.getPresenceExterneWithIdEcoleAndIdEvent(listeExterne.get(position).getIdEcole(), evenement.getId()).get(0)));
            holder.nbr.setText(String.valueOf(presenceExterneSql.getPresenceExterneWithIdEcoleAndIdEvent(listeExterne.get(position).getIdEcole(), evenement.getId()).get(0).getNb()));
        }
        else
        {
            holder.nbr.setVisibility(View.INVISIBLE);
        }

        return convertView;

    }
}
