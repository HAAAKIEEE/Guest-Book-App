package id.ac.polihasnur.ti.haqi.guestbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spinnerReligion();
        TextView login = findViewById(R.id.TxToLogin);
        Button regis =findViewById(R.id.btnRegis);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtFullname = findViewById(R.id.txtFullname);
                EditText txtEmail = findViewById(R.id.txtEmail);
                EditText txtPassword = findViewById(R.id.txtPassword);
                RadioButton rbMale = findViewById(R.id.rbMale);
                RadioButton rbFemale = findViewById(R.id.rbFemale);
                Spinner spReligion = findViewById(R.id.spReligion);

                String fullname = txtFullname.getText().toString();
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                final String gender;
                if(rbMale.isChecked())
                    gender = "Male";
                else
                    gender = "Female";

                String religion = spReligion.getSelectedItem().toString();
        /*
        Toast.makeText(Register.this,
                "Fullname: "+fullname+"\n" +
                    "Email: "+email+"\n"+
                    "Password: "+password+"\n"+
                    "Gender: "+gender+"\n"+
                    "Religion: "+religion, Toast.LENGTH_SHORT).show();

        ==========================================================================================*/
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.1.115/guestbook-android/register.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("failed")){
                                    Toast.makeText(Register.this, "Data gagal diinput.",
                                            Toast.LENGTH_SHORT).show();
                                } else if (response.equals("success")) {
                                    Toast.makeText(Register.this, "Data berhasil diinput.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(Register.this, "Data tidak diinput.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("fullname", fullname);
                        paramV.put("email", email);
                        paramV.put("password", password);
                        paramV.put("gender", gender);
                        paramV.put("religion", religion);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(Register.this , Login.class);
                startActivity(toLogin);
            }
        });
    }
    public void spinnerReligion(){
        Spinner spReligion = findViewById(R.id.spReligion);

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
    public void register(View view){

    }
}