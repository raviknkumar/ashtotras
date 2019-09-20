package com.example.android.ashtotrashatanamavali;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ArrayList<GridClass> al;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GridView gv=(GridView)findViewById(R.id.grid);
        al=new ArrayList<GridClass>();
        al.add(new GridClass(R.drawable.narasimha,"narasimha Ashtotram",R.raw.narasimha_ashtotra_shatanamavali));
        al.add(new GridClass(R.drawable.vis,"vishnu  Ashtotram",R.raw.vishnu_ashtothra_shatanamavali));
        al.add(new GridClass(R.drawable.lakshmiimg,"lakshmi  Ashtotram",R.raw.sri_lakshmi_ashtothram));
        al.add(new GridClass(R.drawable.vasavi,"kanyaka parameshwari  Ashtotram",R.raw.vasavi_kanyaka_parameshwari_astotharam));
        al.add(new GridClass(R.drawable.shani,"Shaneshwara  Ashtotram",R.raw.shani_ashtothara_shatanamavali));
        al.add(new GridClass(R.drawable.shiva,"Shiva Ashtotram",R.raw.siva_ashtothara_sathanamavali));
        al.add(new GridClass(R.drawable.ganesh,"Ganesha Ashtotram",R.raw.ganesha_ashtottara_shatanamavali));
        CustomAdapter customAdapter=new CustomAdapter(this,al);

        gv.setAdapter(customAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent intent=new Intent(HomeActivity.this,StotraActivity.class);

                GridClass obj=al.get(pos);
                intent.putExtra("text",obj.getmDesc());
                intent.putExtra("abc",(0+pos));
                intent.putExtra("audioRsrcId",obj.getAudioResourceId());
                intent.putExtra("title",obj.getmDesc());
                startActivity(intent);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {

        }

        if(id==R.id.share) {
            ApplicationInfo app=getApplicationContext().getApplicationInfo();
            String filepath=app.sourceDir;
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filepath)));
            startActivity(intent.createChooser(intent,"Share Via"));
            return true;

        }

        //return super.onOptionsItemSelected(item);
        return true;
    }


}
