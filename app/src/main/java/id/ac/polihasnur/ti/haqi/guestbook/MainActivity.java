package id.ac.polihasnur.ti.haqi.guestbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        Menyembunyikan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        untuksplash
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish(); // Menutup SplashActivity agar tidak dapat diakses kembali setelah LoginActivity dimulai

            }
        },3000);
//        atau menggunakan caraini
//        new Handler().postDelayed(() -> {
//            // Membuat intent untuk memulai LoginActivity
//            Intent intent = new Intent(this, Login.class);
//            startActivity(intent);
//            finish(); // Menutup SplashActivity agar tidak dapat diakses kembali setelah LoginActivity dimulai
//        }, 3000);
    }
}