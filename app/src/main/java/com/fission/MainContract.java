package com.fission;

import com.fission.annotation.Contract;
import com.fission.annotation.ForceBuild;
import com.musheng.android.common.mvp.IBasePresenter;
import com.musheng.android.common.mvp.IBaseView;

/**
 * Author      : MuSheng
 * CreateDate  : 2020/5/22 18:11
 * Description :
 */
@ForceBuild()
@Contract(name = "Main")
public interface MainContract {
    interface View extends IBaseView<Presenter> {

    }

    interface Presenter extends IBasePresenter<View> {

    }
}
