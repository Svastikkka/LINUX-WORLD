package com.svatikk.linuxworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import java.util.ArrayList;


public class ContactNumber extends AppCompatActivity {




    int[] image_id={
            // 2
            R.drawable.ic_action_teachers,
            R.drawable.ic_action_teachers2,
    };
    String[]  JietContact , JietDesignation;
    android.widget.Toolbar toolbar;

    RecyclerView recyclerView;
    RecyclerAdapterForContactNumbers adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<GetterSetterForContactNumbers> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_number);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //below the code add require the back button icon
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        JietContact = getResources().getStringArray(R.array.JietContact);

        JietDesignation = getResources().getStringArray(R.array.JietDesignation);

        recyclerView =(RecyclerView)findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        //Add animation to opening the layout
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.opeaning_activity);
        recyclerView.setAnimation(animation);
        int count = 0;
        for (String Names : JietContact){
            arrayList.add(new GetterSetterForContactNumbers(Names,image_id[count],JietDesignation[count]));
            count++;

        }
        adapter = new RecyclerAdapterForContactNumbers(arrayList,this);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
