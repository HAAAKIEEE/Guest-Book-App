package id.ac.polihasnur.ti.haqi.guestbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Delete extends AppCompatActivity {
    String id_user;
    Spinner spReligion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinnerReligion1();

        BottomNavigationView bot_nav = findViewById(R.id.bottomNavigationView);
        bot_nav.setSelectedItemId(R.id.menu_delete);

        bot_nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_delete) {
                return true;
            } else if (item.getItemId() == R.id.menu_detail) {
                startActivity(new Intent(Delete.this, Detail.class).putExtra("id_user", "1"));
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_edit) {
                startActivity(new Intent(Delete.this, Edit.class).putExtra("id_user", "1"));
                finish();
                return true;
            } else {
                startActivity(new Intent(Delete.this, Home.class).putExtra("id_user", "1"));
                finish();
                return true;
            }
        });
        Bundle dataFromIntent = getIntent().getExtras();
        id_user = dataFromIntent.getString("id_user");
        Toast.makeText(Delete.this, "Indeks: " + id_user, Toast.LENGTH_SHORT).show();
        Button btnDelete = findViewById(R.id.bntDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.1.115/guestbook-android/delete.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("failed")){ //jika data berhasil dihapus
                                    Toast.makeText(Delete.this, "Data gagal dihapus",
                                            Toast.LENGTH_SHORT).show();
                                }else {

                                    Intent toHome = new Intent(Delete.this, Home.class);
                                    startActivity(toHome);
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
        });
        getGuestDetailDelete();
    }

    public void getGuestDetailDelete(){
        EditText deleteFullname = findViewById(R.id.deleteFullname);
        EditText detailEmail = findViewById(R.id.deleteEmail);
        EditText deletePassword = findViewById(R.id.deletePassword);
        RadioButton rbMale = findViewById(R.id.rbMale);
        RadioButton rbFemale = findViewById(R.id.rbFemale);
        //==========================================================================================
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
                                String password = jsonObject.getString("password");
                                String gender = jsonObject.getString("gender");
                                String religion = jsonObject.getString("religion");

                                deleteFullname.setText(fullname);
                                detailEmail.setText(email);
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
    public void spinnerReligion1(){
         spReligion = findViewById(R.id.spReligion1);

        List<String> item_religion = new ArrayList<String>();
        item_religion.add("-Religion");
        item_religion.add("Islam");
        item_religion.add("Khatolik");
        item_religion.add("Protestan");
        item_religion.add("Hindu");
        item_religion.add("Budha");
        item_religion.add("Other");

        ArrayAdapter<String> dataReligion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item_religion);
        dataReligion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spReligion.setAdapter(dataReligion);
    }
}