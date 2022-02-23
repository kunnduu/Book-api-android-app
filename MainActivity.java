package com.example.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button button;
    EditText searchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        button = findViewById(R.id.button);
        searchbar = findViewById(R.id.searchbar);
        Bookreport bookreport = new Bookreport(MainActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookreport.getbook(searchbar.getText().toString(), new Bookreport.GetDetail() {  // calling the getbook functioon from teh bookreprt class
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Not working", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<details> details) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        CustomAdapter c = new CustomAdapter(details);
                        recyclerView.setAdapter(c);
                        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                    }

                });
            }
        });
    }
}
