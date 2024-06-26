package id.ac.polihasnur.ti.haqi.guestbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.List;

public class Home extends AppCompatActivity {
    ListView guestList ;
    int[] list_index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        BottomNavigationView bot_nav = findViewById(R.id.bottomNavigationView);
        bot_nav.setSelectedItemId(R.id.menu_home);

        bot_nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_home) {
                return true;
            } else if (item.getItemId() == R.id.menu_detail) {
                startActivity(new Intent(Home.this, Detail.class).putExtra("id_user", "1"));
                finish();
                return true;
            } else if (item.getItemId() == R.id.menu_edit) {
                startActivity(new Intent(Home.this, Edit.class).putExtra("id_user", "1"));
                finish();
                return true;
            } else {
                startActivity(new Intent(Home.this, Delete.class).putExtra("id_user", "1"));
                finish();
                return true;
            }
        });
        guestList = findViewById(R.id.guestList);
        guestList();
        guestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(Home.this,"Indext guest :" +list_index[position],Toast.LENGTH_SHORT).show();
                Intent toDetail = new Intent(Home.this, Detail.class);
                String id_user = list_index[position]+"";
                toDetail.putExtra("id_user", id_user);
                startActivity(toDetail);
            }
        });
    }
    public void guestList(){
//        ListView guestList = findViewById(R.id.guestList);
        List<String> guestItem = new ArrayList<String>();
        ArrayAdapter<String> dataguest = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,guestItem);
        dataguest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        guestList.setAdapter(dataguest);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.1.115/guestbook-android/home.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        guestItem.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            list_index = new int[jsonArray.length()];
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String fullname = jsonObject.getString("fullname");
                                String email = jsonObject.getString("email");

                                list_index[i] = Integer.parseInt(jsonObject.getString("id_user"));

                                //memasukkan data ke listview
                                guestItem.add((i+1)+". "+fullname+" | "+email);
                                dataguest.notifyDataSetChanged();
                            }
                        }catch (JSONException e){
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){};
        queue.add(stringRequest);


    }
}