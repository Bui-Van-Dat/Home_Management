package com.example.homemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    ImageView img_avatar, img_mic;
    TextView txtName, txtEmail, txtStatement;
    Switch swDoor;
    LinearLayout linearLayout;
    Button btnLight1, btnLight2, btnLight3, btnLight4;

    private static final int REQUEST_CODE_SPEECH_INPUT = 100;
    DatabaseReference mData;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
        initListener();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home: {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        break;

                    }
                    case R.id.nav_profile: {
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                        break;
                    }
                    case R.id.nav_logout: {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), SigninActivity.class));
                        finish();
                        break;
                    }
                }
                return false;
            }
        });
        showUserInfo();

    }
    private void initListener() {
        btnLight1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (btnLight1.getText() == "BẬT ĐÈN 1") {
                    mData.child("alarm").setValue("bật đèn 1");
                    btnLight1.setText("TẮT ĐÈN 1");
                    btnLight1.setTextColor((getResources().getColor(R.color.light_on)));
                } else {
                    mData.child("alarm").setValue("Tắt Đèn 1");
                    btnLight1.setText("BẬT ĐÈN 1");
                    btnLight1.setTextColor((getResources().getColor(R.color.white)));
                }
            }
        });
        btnLight2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (btnLight2.getText() == "BẬT ĐÈN 2") {
                    mData.child("alarm").setValue("bật đèn 2");
                    btnLight2.setText("TẮT ĐÈN 2");
                    btnLight2.setTextColor((getResources().getColor(R.color.light_on)));
                } else {
                    mData.child("alarm").setValue("Tắt Đèn 2");
                    btnLight2.setText("BẬT ĐÈN 2");
                    btnLight2.setTextColor((getResources().getColor(R.color.white)));
                }
            }
        });
        btnLight3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (btnLight3.getText() == "BẬT ĐÈN 3") {
                    mData.child("alarm").setValue("bật đèn 3");
                    btnLight3.setText("TẮT ĐÈN 3");
                    btnLight3.setTextColor((getResources().getColor(R.color.light_on)));
                } else {
                    mData.child("alarm").setValue("Tắt Đèn 3");
                    btnLight3.setText("BẬT ĐÈN 3");
                    btnLight3.setTextColor((getResources().getColor(R.color.white)));
                }
            }
        });
        btnLight4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (btnLight4.getText() == "BẬT ĐÈN 4") {
                    mData.child("alarm").setValue("bật đèn 4");
                    btnLight4.setText("TẮT ĐÈN 4");
                    btnLight4.setTextColor((getResources().getColor(R.color.light_on)));
                } else {
                    mData.child("alarm").setValue("Tắt Đèn 4");
                    btnLight4.setText("BẬT ĐÈN 4");
                    btnLight4.setTextColor((getResources().getColor(R.color.white)));
                }
            }
        });
        img_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });

        swDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isOpen = swDoor.isChecked();
                if (isOpen == true) {
                    mData.child("alarm").setValue("open the door");
                    swDoor.setText("CỬA MỞ");
                }
                if (isOpen == false) {
                    mData.child("alarm").setValue("Close the door");
                    swDoor.setText("CỬA ĐÓNG");
                }
            }
        });
    }

    private void speak() {
        //intent to show speed to text
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //receive voice input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtStatement.setText(result.get(0));
                    mData.child("alarm").setValue(result.get(0));
                }
                break;
            }
        }
    }
    private void initUi() {
        navigationView = findViewById(R.id.nav_view);
        img_avatar = navigationView.getHeaderView(0).findViewById(R.id.img_avatar);
        txtEmail = navigationView.getHeaderView(0).findViewById(R.id.txtEmail);
        txtName = navigationView.getHeaderView(0).findViewById(R.id.txtName);

        swDoor = (Switch) findViewById(R.id.swDoor);
        txtStatement = (TextView) findViewById(R.id.txtMic);
        btnLight1 = (Button) findViewById(R.id.light1);
        btnLight2 = (Button) findViewById(R.id.light2);
        btnLight3 = (Button) findViewById(R.id.light3);
        btnLight4 = (Button) findViewById(R.id.light4);
        linearLayout = (LinearLayout) findViewById(R.id.ln_door);
        img_mic = (ImageView) findViewById(R.id.btn_mic);

        mData = FirebaseDatabase.getInstance().getReference("list_strings");
        mData.child("alarm").setValue("Tắt Đèn");
    }

    private void showUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null) {
            txtName.setVisibility(View.GONE);
        } else {
            txtName.setVisibility(View.VISIBLE);
            txtName.setText(name);
        }
        txtEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.avatar2).into(img_avatar);
    }
}