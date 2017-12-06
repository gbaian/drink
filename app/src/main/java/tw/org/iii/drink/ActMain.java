package tw.org.iii.drink;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActMain extends Activity {
    int[] price = {20,30,40};

    private AdapterView.OnItemSelectedListener spiDrink_select = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//選擇下拉式選項後執行的方法
            switch (position){
                case 0:
                    txtPrice.setText(price[0]+"");
                    break;
                case 1:
                    txtPrice.setText(price[1]+"");
                    break;
                case 2:
                    txtPrice.setText(price[2]+"");
                    break;
                default:
                    txtPrice.setText("");
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {//未選擇下拉式選項時執行的方法

        }
    };
    private View.OnClickListener btnCheck_click = new View.OnClickListener() {//確認
        @Override
        public void onClick(View v) {
            if("".equals(txtCup.getText().toString()))
                txtCup.setText(0+"");

            int x = Integer.parseInt(txtPrice.getText().toString());
            int y = Integer.parseInt(txtCup.getText().toString());
            txtTotal.setText(x*y+"");
            btnAnswer.setVisibility(View.VISIBLE);
        }
    };
    private View.OnClickListener btnStore_click = new View.OnClickListener() {//儲存
        @Override
        public void onClick(View v) {
            SharedPreferences table = getSharedPreferences("drink", 0);
            SharedPreferences.Editor row = table.edit();
            row.putInt("KK",spiDrink.getSelectedItemPosition()).commit();

            table = getSharedPreferences("sweet", 0);
            row = table.edit();
            row.putInt("KK",spiSweet.getSelectedItemPosition()).commit();

            table = getSharedPreferences("ice", 0);
            row = table.edit();
            row.putInt("KK",spiIce.getSelectedItemPosition()).commit();

            table = getSharedPreferences("cup", 0);
            row = table.edit();
            row.putInt("KK",Integer.parseInt(txtCup.getText().toString())).commit();


            Toast.makeText(ActMain.this,"新增資料成功",Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener btnRead_click = new View.OnClickListener() {//讀取
        @Override
        public void onClick(View v) {
            SharedPreferences table = getSharedPreferences("drink", 0);
            int data = table.getInt("KK",0);
            spiDrink.setSelection(data);

            table = getSharedPreferences("sweet", 0);
            data = table.getInt("KK",0);
            spiSweet.setSelection(data);

            table = getSharedPreferences("ice", 0);
            data = table.getInt("KK",0);
            spiIce.setSelection(data);

            table = getSharedPreferences("cup", 0);
            data = table.getInt("KK",0);
            txtCup.setText(data+"");
        }
    };


    private View.OnClickListener btnPlus_click=new View.OnClickListener() {//+
        @Override
        public void onClick(View v) {
            if("".equals(txtCup.getText().toString()))
                txtCup.setText(0+"");
            int x = Integer.parseInt(txtCup.getText().toString());
            x++;
            txtCup.setText(x+"");

        }
    };
    private View.OnClickListener btnMinus_click = new View.OnClickListener() {//-
        @Override
        public void onClick(View v) {
            if("".equals(txtCup.getText().toString()))
                txtCup.setText(0 + "");
            int x = Integer.parseInt(txtCup.getText().toString());
            if (x<=0)
                return;
            x--;
            txtCup.setText(x+"");
        }
    };
    private View.OnClickListener btnAnswer_click = new View.OnClickListener() {//結帳
        @Override
        public void onClick(View v) {
            if("".equals(txtPay.getText().toString())){
                txtBack.setText("未輸入金額");
                return;
            }

            int x = Integer.parseInt(txtTotal.getText().toString());
            int y = Integer.parseInt(txtPay.getText().toString());

            if(y-x<0)
                txtBack.setText("不足"+(y-x)*-1+"元");
            else
                txtBack.setText(y-x+"元");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actmain);
        InitialComponent();
    }

    private void InitialComponent() {
        spiDrink = (Spinner)findViewById(R.id.spinner_drink);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.drink, R.layout.myspinner );
        adapter.setDropDownViewResource(R.layout.myspinner);
        spiDrink.setAdapter(adapter);
        spiDrink.setOnItemSelectedListener(spiDrink_select);

        spiSweet = (Spinner)findViewById(R.id.spinner_sweet);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.sweet, R.layout.myspinner2 );
        adapter2.setDropDownViewResource(R.layout.myspinner2);
        spiSweet.setAdapter(adapter2);

        spiIce = (Spinner)findViewById(R.id.spinner_ice);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this, R.array.ice, R.layout.myspinner2 );
        adapter3.setDropDownViewResource(R.layout.myspinner2);
        spiIce.setAdapter(adapter3);

        txtPrice =(TextView)findViewById(R.id.txtprice);
        txtTotal =(TextView)findViewById(R.id.txttotal);
        txtCup=(EditText)findViewById(R.id.txtcup);
        btnCheck=(Button)findViewById(R.id.btncheck);
        btnCheck.setOnClickListener(btnCheck_click);
        btnStore=(Button)findViewById(R.id.btnstore);
        btnStore.setOnClickListener(btnStore_click);
        btnRead=(Button)findViewById(R.id.btnread);
        btnRead.setOnClickListener(btnRead_click);
        btnPlus=(Button)findViewById(R.id.btnplus);
        btnPlus.setOnClickListener(btnPlus_click);
        btnMinus=(Button)findViewById(R.id.btnminus);
        btnMinus.setOnClickListener(btnMinus_click);
        txtPay=(EditText)findViewById(R.id.txtpay);
        txtBack=(TextView)findViewById(R.id.txtback);
        btnAnswer=(Button)findViewById(R.id.btnanswer);
        btnAnswer.setOnClickListener(btnAnswer_click);
    }

    Spinner spiDrink;
    Spinner spiSweet;
    Spinner spiIce;
    TextView txtPrice;
    TextView txtTotal;
    EditText txtCup;
    Button btnCheck;
    Button btnStore;
    Button btnRead;
    Button btnPlus;
    Button btnMinus;
    EditText txtPay;
    TextView txtBack;
    Button btnAnswer;
}
