package ginfo.foceen;

/**
 * Created by pierre on 01/11/15.
 */
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.text.DateFormat;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class FileBrowserActivity extends ListActivity {

    private File currentDir;
    private FileArrayAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDir = new File("/sdcard/");
        Log.d("FileBrowserActivity","Current dir : " + currentDir.toString());
        fill(currentDir);
    }

    private void fill(File f)
    {
        Log.d("FileBrowserActivity","File : " + f.toString());
        Log.d("FileBrowserActivity","Can Read : " + f.canRead());
        File[]dirs = f.listFiles();
        //Log.d("FileBrowserActivity","Essai : " + dirs.toString());
        this.setTitle("Current Dir: "+f.getName());
        List<Item>dir = new ArrayList<Item>();
        List<Item>fls = new ArrayList<Item>();
        try{
            for(File ff: dirs)
            {
                Date lastModDate = new Date(ff.lastModified());
                DateFormat formater = DateFormat.getDateTimeInstance();
                String date_modify = formater.format(lastModDate);
                if(ff.isDirectory() && ff.canRead()){


                    File[] fbuf = ff.listFiles();
                    int buf = 0;
                    if(fbuf != null){
                        buf = fbuf.length;
                    }
                    else buf = 0;
                    String num_item = String.valueOf(buf);
                    if(buf == 0) num_item = num_item + " item";
                    else num_item = num_item + " items";

                    //String formated = lastModDate.toString();
                    dir.add(new Item(ff.getName(),num_item,date_modify,ff.getAbsolutePath(),"directory_icon"));
                }
                else
                {
                    fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"file_icon"));
                }
            }
        }catch(Exception e)
        {

        }
        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if(!f.getName().equalsIgnoreCase("sdcard"))
            dir.add(0,new Item("..","Parent Directory","",f.getParent(),"directory_up"));
        adapter = new FileArrayAdapter(FileBrowserActivity.this,R.layout.list_file_browser,dir);
        this.setListAdapter(adapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Item o = adapter.getItem(position);
        if(o.getImage().equalsIgnoreCase("directory_icon")||o.getImage().equalsIgnoreCase("directory_up")){
            currentDir = new File(o.getPath());
            fill(currentDir);
        }
        else
        {
            onFileClick(o);
        }
    }
    private void onFileClick(Item o)
    {
        //Toast.makeText(this, "Folder Clicked: "+ currentDir, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("GetPath",currentDir.toString());
        intent.putExtra("GetFileName",o.getName());
        setResult(RESULT_OK, intent);
        finish();
    }
}