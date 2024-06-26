package id.ac.polihasnur.ti.haqi.guestbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Detail extends AppCompatActivity {
    String id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bot_nav = findViewById(R.id.bottomNavigationView);
        bot_nav.setSelectedItemId(R.id.menu_detail);

        bot_nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_detail) {
                return true;
            } else if (item.getItemId() == R.id.menu_home) {
                startActivity(new Intent(Detail.this, Home.class).putExtra("id_user", "1"));
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_edit) {
                startActivity(new Intent(Detail.this, Edit.class).putExtra("id_user", "1"));
                finish();
                return true;
            } else {
                startActivity(new Intent(Detail.this, Delete.class).putExtra("id_user", "1"));
                finish();
                return true;
            }
        });
        Bundle dataFromIntent = getIntent().getExtras();
        id_user = dataFromIntent.getString("id_user");
//        Toast.makeText(Detail.this, "Indeks: "+id_user, Toast.LENGTH_SHORT).show();
        detailGuest();

    }
    public void detailGuest(){
        TextView detailFullname = findViewById(R.id.detailFullname);
        TextView detailEmail = findViewById(R.id.detailEmail);
        TextView detailGender = findViewById(R.id.detailGender);
        TextView detailReligion = findViewById(R.id.detailReligion);

        //==========================================================================================
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.1.115/guestbook-android/detail.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String fullname = jsonObject.getString("fullname");
                                String email = jsonObject.getString("email");
                                String gender = jsonObject.getString("gender");
                                String religion = jsonObject.getString("religion");

                                detailFullname.setText(": "+fullname);
                                detailEmail.setText(": "+email);
                                detailGender.setText(": "+gender);
                                detailReligion.setText(": "+religion);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("id_user", id_user);
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
    public void openEdit(View view){
        Intent toEdit = new Intent(Detail.this, Edit.class);
        toEdit.putExtra("id_user", id_user);
        startActivity(toEdit);
    }
    public void openDelete(View view){
        Intent toDelete = new Intent(Detail.this, Delete.class);
        toDelete.putExtra("id_user", id_user);
        startActivity(toDelete);
    }

}