package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rvCarList;
    FirebaseRecyclerOptions<Car> options;
    FirebaseRecyclerAdapter<Car,CarViewHolder> adapter;

    DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Data Reference
        dbref= FirebaseDatabase.getInstance().getReference().child("cars");

        //Recycler View
        rvCarList=findViewById(R.id.rvCarList);
        rvCarList.setLayoutManager(new LinearLayoutManager(this));
        rvCarList.setHasFixedSize(true);

        //Load Data in Recycler View
        LoadData();

    }

    private void LoadData() {
        //send model class to build models or options
        options=new FirebaseRecyclerOptions.Builder<Car>().setQuery(dbref,Car.class).build();

        //create firebase ui adapter
        adapter=new FirebaseRecyclerAdapter<Car, CarViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CarViewHolder holder, int position, @NonNull Car model) {
                holder.carName.setText(model.getName());
                Picasso.get().load(model.getPurl()).into(holder.carImage);
            }

            @NonNull
            @Override
            public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_single_item,parent,false);
                return new CarViewHolder(view);
            }
        };
        adapter.startListening();
        rvCarList.setAdapter(adapter);
    }

    private class CarViewHolder extends RecyclerView.ViewHolder {
        TextView carName;
        ImageView carImage;
        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carName=itemView.findViewById(R.id.carName);
            carImage=itemView.findViewById(R.id.carImage);
        }
    }
}