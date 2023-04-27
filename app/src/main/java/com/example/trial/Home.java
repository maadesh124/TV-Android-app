package com.example.trial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity {
int j;
int ids[]={R.id.ethirneechal, R.id.kayal ,R.id.iniya , R.id.sundari,R.id.vanathaipola,R.id.baakiyalakshmi,R.id.eeramaanarojaave,R.id.katrukennaveli,
R.id.mahanadhi,R.id.pandianstores,R.id.siragadikkaaasai,R.id.tamizhumsaraswathiyum,R.id.thendralvanthuennaithodum,R.id.aahakalyanam,R.id.ponni,
R.id.supersinger,R.id.chellamma};
ImageButton imageButton[]= new ImageButton[ids.length];
String serialNmae[]= {"ethir-neechal","kayal","iniya","sundari","vanathaipola","baakiyalakshmi","eeramaanarojaave","katrukennaveli","mahanadhi","pandianstores"
,"siragadikkaaasai","tamizhumsaraswathiyum","thendralvanthuennaithodum","aahakalyanam","ponni","supersinger","chellamma"};
String demoLinks[]= {"https://www.thiraithee.net/sun-tv-programs/ethir-neechal/13-04-2023-ethir-neechal",
"https://www.thiraithee.net/sun-tv-programs/kayal/13-04-2023-kayal","https://www.thiraithee.net/sun-tv-programs/iniya/13-04-2023-iniya"
,"https://www.thiraithee.net/sun-tv-programs/sundari/13-04-2023-sundari","https://www.thiraithee.net/sun-tv-programs/vanathai-pola/13-04-2023-vanathai-pola"
        ,"https://www.thiraithee.net/vijay-tv-programs/baakiyalakshmi/15-04-2023-baakiyalakshmi"
        ,"https://www.thiraithee.net/vijay-tv-programs/eeramana-rojave/13-04-2023-eeramaana-rojaave"
        ,"https://www.thiraithee.net/vijay-tv-programs/katrukenna-veli/15-04-2023-kaatrukkenna-veli",
        "https://www.thiraithee.net/vijay-tv-programs/mahanadhi/13-04-2023-mahanadhi",
        "https://www.thiraithee.net/vijay-tv-programs/pandian-stores/15-04-2023-pandian-stores"
,"https://www.thiraithee.net/vijay-tv-programs/siragadikka-aasai/13-04-2023-siragadikka-aasai"
        ,"https://www.thiraithee.net/vijay-tv-programs/thamizhum-saraswathiyum/13-04-2023-thamizhum-saraswathiyum"
        ,"https://www.thiraithee.net/vijay-tv-programs/thendral-vandhu-ennai-thodum/15-04-2023-thendral-vanthu-ennai-thodum"
        ,"https://www.thiraithee.net/vijay-tv-programs/aaha-kalyanam/13-04-2023-aaha-kalyanam"
        ,"https://www.thiraithee.net/vijay-tv-programs/ponni/17-04-2023-ponni"
,"https://www.thiraithee.net/vijay-tv-programs/super-singer/15-04-2023-super-singer"
        ,"https://www.thiraithee.net/vijay-tv-programs/chellama/18-04-2023-chellamma"

};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



     for(j =0;j<ids.length;j++)
        {
            imageButton[j] = findViewById(ids[j]);
            imageButton[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ImageButton button = (ImageButton) v;
                    List<ImageButton> l= Arrays.asList(imageButton);
                    int index1=l.indexOf(button);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("d",demoLinks[index1]);
                    intent.putExtra("s",serialNmae[index1]);

                    startActivity(intent);
                    finish();
                }
            });

        }

     ImageButton puthia=(ImageButton)findViewById(R.id.puthia);
     ImageButton sun=(ImageButton)findViewById(R.id.sun);

     puthia.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i=new Intent(getApplicationContext(),News.class);
             i.putExtra("id","NX18e_Wh7NA");
             startActivity(i);
             finish();
         }
     });

        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(getApplicationContext(),News.class);
                i1.putExtra("id","2ywxK3HC4iA");
                startActivity(i1);
                finish();
            }
        });



    }


}
