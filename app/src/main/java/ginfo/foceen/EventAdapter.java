package ginfo.foceen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dajaj on 24/09/15.
 */
public class EventAdapter  extends BaseAdapter {

    List<Event> listeEvent;

    LayoutInflater inflater;

    public EventAdapter(Context context,List<Event> list) {
        inflater = LayoutInflater.from(context);
        listeEvent = list;
    }

    @Override
    public int getCount() {
        return listeEvent.size();
    }

    @Override
    public Object getItem(int position) {
        return listeEvent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView nom;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_event_item, null);

            holder.nom = (TextView)convertView.findViewById(R.id.itemNomEvent);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.nom.setText(listeEvent.get(position).getNom());

        return convertView;
    }
}
