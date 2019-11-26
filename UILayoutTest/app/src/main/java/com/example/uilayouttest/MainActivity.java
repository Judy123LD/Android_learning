package com.example.uilayouttest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uilayouttest.listviewtest.Fruit;
import com.example.uilayouttest.listviewtest.FruitAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String[] data={"a","b","c","d","e","f","g","h","i","j","k","l","m","n"};
    private List<Fruit> fruitList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

    }

    private void initFruits(){
        for(int i=0;i<6;i++){
            Fruit apple=new Fruit(getRandomLengthName("apple"),R.drawable.msg_recv);
            fruitList.add(apple);
            Fruit banana=new Fruit(getRandomLengthName("banana"),R.drawable.msg_send);
            fruitList.add(banana);
//            Fruit orange=new Fruit("orange",R.drawable.img3);
//            fruitList.add(orange);
        }
    }

    /**
     * 随机产生水果名字(长度不一)
     * @param name
     * @return
     */
    private String getRandomLengthName(String name){
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=1;i<length;i++){
            builder.append(name);
        }
        return builder.toString();
    }
}
