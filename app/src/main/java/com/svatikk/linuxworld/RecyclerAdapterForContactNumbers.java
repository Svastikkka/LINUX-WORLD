package com.svatikk.linuxworld;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by manshusharma on 30/06/17.
 */
public class RecyclerAdapterForContactNumbers extends RecyclerView.Adapter<RecyclerAdapterForContactNumbers.MyViewHolder>{

    ArrayList<GetterSetterForContactNumbers> arrayList =new ArrayList<>();


    Context ctx;


    RecyclerAdapterForContactNumbers(ArrayList<GetterSetterForContactNumbers> arrayList, Context ctx){

        this.arrayList= arrayList;

        this.ctx=ctx;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = LayoutInflater .from(parent.getContext()).inflate(R.layout.card_view_for_contact_numbers,parent,false);


        return new MyViewHolder(view,ctx);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.imageView.setImageResource(arrayList.get(position).getImage_Id());
        holder.textView.setText(arrayList.get(position).getInstitutes());
        holder.textView2.setText(arrayList.get(position).getDesignation());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder   implements View.OnClickListener{


        ImageView imageView;
        TextView textView ,textView2;



        Context ctx;


        public MyViewHolder(View itemView ,Context ctx) {
            super(itemView);


            itemView.setOnClickListener(this);
            this.ctx = ctx;



            imageView=(ImageView)itemView.findViewById(R.id.images);
            textView = (TextView)itemView.findViewById(R.id.person_name);
            textView2 = (TextView)itemView.findViewById(R.id.designation);

        }



        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            //  Contact contact = this.contacts.get(position);




            switch (position) {

                case 0:
                    // Handle the  Contact section

                    Log.i("yoo", "Navneet Agrawal");
                    Intent contact = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("tel:0000000000"));
                    ctx.startActivity(contact);


                    break;
                case 1:
                    // Handle the  Contact section

                    Log.i("yoo", "Rakesh kothari");
                    Intent contact1 = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("tel:0000000000"));
                    ctx.startActivity(contact1);

                    break;

                default:Log.i("ERROR","OCCURS");
            }

        }

    }



    }
