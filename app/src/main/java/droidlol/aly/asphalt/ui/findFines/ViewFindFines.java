package droidlol.aly.asphalt.ui.findFines;

import droidlol.aly.asphalt.pojo.FinesResponse;
import droidlol.aly.asphalt.ui.base.BaseView;

public interface ViewFindFines extends BaseView {
    void onFindFinesSuccess(FinesResponse finesResponse);
    void onFindFinesFail();

}
