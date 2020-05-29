package com.fission;
import com.musheng.android.router.MSBaseRouter;

public class MainRouter extends MSBaseRouter  {

    public static final String PATH = "/app/Main";

    @Override
    public String getPath(){
        return PATH;
    }

}
