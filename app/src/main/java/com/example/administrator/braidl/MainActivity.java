package com.example.administrator.braidl;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    Intent intentService;
    public Users users;
    EditText edtPersonName;
    Intent intent;
    public Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentService = new Intent("com.tangcco.serviceImpl.UserService");
        bindService(intentService, conn, Service.BIND_AUTO_CREATE);

        edtPersonName = (EditText) findViewById(R.id.personId);
        intent = new Intent("con.tangcco.serviceImpl.PresonService");
        bindService(intent, conn1, Service.BIND_AUTO_CREATE);
    }

    ServiceConnection conn = new ServiceConnection() {


        //①Service中需要创建一个实现IBinder的内部类(这个内部类不一定在Service中实现，但必须在Service中创建它)。在OnBind（）方法中需返回一个IBinder实例，不然onServiceConnected方法不会调用。
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            users = Users.Stub.asInterface(service);
        }

        //②ServiceConnection 的回调方法onServiceDisconnected() 在连接正常关闭的情况下是不会被调用的, 该方法只在Service 被破坏了或者被杀死的时候调用.例如, 系统资源不足, 要关闭一些Services, 刚好连接绑定的 Service 是被关闭者之一,  这个时候onServiceDisconnected() 就会被调用。
        @Override
        public void onServiceDisconnected(ComponentName name) {
            users = null;
        }
    };

    ServiceConnection conn1 = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            person = Person.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            person = null;
        }
    };

    public void invokeService(View view) {
        try {
            users.setName("hhd");
            String name = users.getName();
            Toast.makeText(this, "name", Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getPersonName(View view) {
        try {
            String personId = edtPersonName.getText().toString();
            String personName = person.getPersonName(Integer.valueOf(personId));
            Toast.makeText(this, personName, Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

}
