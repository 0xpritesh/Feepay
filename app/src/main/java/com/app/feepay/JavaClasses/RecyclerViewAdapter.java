package com.app.feepay.JavaClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.feepay.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<TransactionData,RecyclerViewAdapter.MyViewHolder> {

    String a,b,c;
    Context context;

    public RecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<TransactionData> options, Context context) {
        super(options);
        this.context = context;

//        Toast.makeText(context, options.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull TransactionData model) {

        holder.TranactionIdTXT.setText(model.getTransactionId());
        holder.DateTimeTXT.setText(model.getDateTime());
        holder.AmountTXT.setText(model.getAmount());

//        a = holder.TranactionIdTXT.getText().toString();
//        b = model.getDateTime();
//        c = model.getAmount();
//
//        Toast.makeText(context, a, Toast.LENGTH_SHORT).show();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row,parent,false);

        return new MyViewHolder(v);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TranactionIdTXT,DateTimeTXT,AmountTXT;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TranactionIdTXT = itemView.findViewById(R.id.TranactionIdTXT);
            DateTimeTXT = itemView.findViewById(R.id.DateTimeTXT);
            AmountTXT = itemView.findViewById(R.id.AmountTXT);

        }
    }

}