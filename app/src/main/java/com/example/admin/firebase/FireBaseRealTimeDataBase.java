package com.example.admin.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBaseRealTimeDataBase extends AppCompatActivity {

    public static final String TAG = "movieActivity";

    private FirebaseDatabase database;
    private DatabaseReference movieRef;

    EditText etMovieName, etMovieDirector, etMovieProducer;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_real_time_data_base);

        etMovieName = (EditText) findViewById(R.id.etMovieName);
        etMovieDirector = (EditText) findViewById(R.id.etMovieDirector);
        etMovieProducer = (EditText) findViewById(R.id.etMovieProducer);

        database = FirebaseDatabase.getInstance();
        movieRef = database.getReference("movies");

         user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void saveMovie(View view) {

        String name, director, producer;

        name = etMovieName.getText().toString();
        director = etMovieDirector.getText().toString();
        producer = etMovieProducer.getText().toString();
        Movie movie = new Movie(name, director, producer);

        movieRef.child(user.getUid())
                .push()
                .setValue(movie)
        .addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(FireBaseRealTimeDataBase.this, "Movie Saved", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FireBaseRealTimeDataBase.this, "Movie Not Saved", Toast.LENGTH_SHORT).show();
            }
        });

        //myRef.setValue("Hello, World!");
    }

    public void getMovies(View view) {
        final List<Movie> movieList = new ArrayList<>();

        movieRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                movieList.clear();
                boolean hasMovies = dataSnapshot.hasChildren();
                Log.d(TAG, "onDataChange: " + hasMovies);
                if(hasMovies){
                    Log.d(TAG, "onDataChange: Movies count: " + dataSnapshot.getChildrenCount());

                    for(DataSnapshot snapShot: dataSnapshot.getChildren()){

                        Movie movie = snapShot.getValue(Movie.class);
                        movieList.add(movie);

                    }
                }

                Log.d(TAG, "getMovies: " + movieList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });

    }
}
