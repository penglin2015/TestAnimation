package com.xuyao.prancelib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.androidquery.AQuery;
import com.xuyao.prancelib.util.BaseDialog;
import com.xuyao.prancelib.util.EventBusUtil;
import com.xuyao.prancelib.util.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by PL_Moster on 2017/2/22.
 */

public class BaseV4Fragment extends Fragment {

    public String TAG() {
        return this.getClass().getSimpleName();
    }

    BaseDialog utilDialog;
    AQuery fQuery;
    int screenWidth = 0;//屏幕的总宽度

    public int getScreenWidth() {
        return screenWidth;
    }

    public AQuery getfQuery() {
        return fQuery;
    }

    View fView;

    public BaseV4Fragment setBundle(Bundle bundle){
        setArguments(bundle);
        return this;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fView= inflater.inflate(getLayoutId(), container, false);
        fQuery = new AQuery(fView);
        return fView;
    }




    protected int getLayoutId() {
        return 0;
    }


    private void init(View v) {
        initInData(v);
        initView(v);
        initEvent(v);
        initEndData(v);
        initUtilDialog();
    }

    protected void initEndData(View v) {

    }

    protected void initEvent(View v) {

    }

    protected void initView(View v) {

    }

    protected void initInData(View v) {

    }

    Unbinder unbinder;
    protected  Activity mActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=getActivity();
        unbinder=ButterKnife.bind(this,fView);
        screenWidth = ScreenUtils.getScreenWidth(getActivity());
        init(fView);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 初始化自定义dialog
     */
    private void initUtilDialog() {
        utilDialog = new BaseDialog(getActivity(), new View(getActivity()), (int) (ScreenUtils.getScreenWidth(getActivity()) * 0.8f), WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public BaseDialog getUtilDialog() {
        return utilDialog;
    }

    /**
     * 自定义的显示的dialog
     *
     * @param showDialogView
     */
    protected void showUtilDialog(View showDialogView) {
        utilDialog.setShowDialogView(showDialogView);
        utilDialog.showdialog();
    }

    /**
     * 寻找控件
     * @param id
     * @return
     */
    protected <T extends View> T fv(int id){
        return (T) getfQuery().id(id).getView();
    }


    protected  int getw(){
        return ScreenUtils.getScreenWidth(getActivity());
    }

    protected int geth(){
        return ScreenUtils.getScreenHeight(getActivity());
    }

}
