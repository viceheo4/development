package com.example.android.apis.app;


import android.os.Bundle;
import android.util.Log;

public class MyTest extends HelloWorld
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Log.v(this.getClass().toString(), this.toString());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "MyTest";
    }
    
}
