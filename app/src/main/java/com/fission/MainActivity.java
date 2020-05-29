package com.fission;
import android.os.Bundle;
import android.view.View;
import com.fission.R;
import com.musheng.android.common.mvp.BaseActivity;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = MainRouter.PATH)
public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {

    @Override
    public MainContract.Presenter initPresenter(){
        return new MainPresenter();
    }

    @Override
    public void setRootView(Bundle savedInstanceState){
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initWidget(){
        
    }

}
