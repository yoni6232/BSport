package com.example.bsport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bsport.Model.Users;
import com.example.bsport.Prevalent.Prevalent;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    private ProgressDialog loadingbar;
    private FirebaseUser currentUser;
    private ImageButton logoutImage;
    private String ba;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadingbar = new ProgressDialog(this);
        Paper.init(this);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolBar);
        logoutImage = (ImageButton) findViewById(R.id.logout_button);
        ViewPager myViewPager = (ViewPager) findViewById(R.id.main_tabs_pager);

        MenuAccessorAdapter myTabsAccessorAdapter = new MenuAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);
        TabLayout myTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);

        String UserNameKey = Paper.book().read(Prevalent.UserNameKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        if(UserNameKey != null && UserPasswordKey != null){
            if(!TextUtils.isEmpty(UserNameKey) && !TextUtils.isEmpty(UserPasswordKey)){
                AllowAccess(UserNameKey,UserPasswordKey);
                loadingbar.setTitle("Already Logged in");
                loadingbar.setMessage("Please Wait..");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
            }
        }
        else {
            Paper.book().destroy();
            SendUserToLoginActivity();
        }

    }

    private void AllowAccess(final String userName, final String password) {


        final DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference();
        UserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(userName).exists()){
                    Users usersData = dataSnapshot.child("Users").child(userName).getValue(Users.class);
                    if(usersData.getUsername().equals(userName)){

                        if(usersData.getPassword().equals(password)){
                            Prevalent.setUserAdminKey("false");
                            Prevalent.setUserName(userName);

                            Toast.makeText(HomeActivity.this," ברוך הבא",Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                        else{
                            Toast.makeText(HomeActivity.this, "סיסמה שגויה", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                    }
                    else{
                        Toast.makeText(HomeActivity.this, "שם משתמש לא נמצא", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
                else if (dataSnapshot.child("Admin").child(userName).exists()){
                    Users usersData = dataSnapshot.child("Admin").child(userName).getValue(Users.class);
                    if(usersData.getUsername().equals(userName)){

                        if(usersData.getPassword().equals(password)){
                            Prevalent.setUserAdminKey("true");
                            String UserAdminKey = Paper.book().read(Prevalent.UserAdminKey);
                            Prevalent.setUserName(userName);

                            Toast.makeText(HomeActivity.this,"ברוך הבא מנהל",Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                        else{
                            Toast.makeText(HomeActivity.this, "סיסמה לא נכונה", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                    }
                    else{
                        Toast.makeText(HomeActivity.this, "לא נמצא משתמש כזה", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
                else{
                    Toast.makeText(HomeActivity.this, "Account with this " + userName + " do not exists.", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void SendUserToLoginActivity() {
        Intent LoginIntent = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(LoginIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.logout_item){
            logoutImage = (ImageButton) findViewById(R.id.logout_button);
            Paper.book().destroy();
            SendUserToLoginActivity();
        }
        return true;
    }

}

