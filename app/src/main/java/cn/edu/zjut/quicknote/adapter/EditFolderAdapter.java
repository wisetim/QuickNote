package cn.edu.zjut.quicknote.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zjut.quicknote.R;
import cn.edu.zjut.quicknote.bean.NoteFolder;
import cn.edu.zjut.quicknote.constants.EditFolderConstants;

public class EditFolderAdapter extends BaseQuickAdapter<NoteFolder, BaseViewHolder> {

    public List<Boolean> mCheckList = new ArrayList<>();

    public EditFolderAdapter() {
        super(R.layout.item_edit_folder);
    }

    @Override
    public void setNewData(@Nullable List<NoteFolder> data) {
        super.setNewData(data);
        mCheckList.clear();
        for (int i = 0; i < data.size(); i++) {
            mCheckList.add(false);
        }
    }

    @Override
    public void addData(@NonNull NoteFolder data) {
        super.addData(data);
        mCheckList.add(false);
    }

    @Override
    public void remove(@IntRange(from = 0L) int position) {
        super.remove(position);
        mCheckList.remove(position);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoteFolder item) {
        int position = helper.getLayoutPosition();

        TextView folderCount = helper.getView(R.id.tv_edit_folder_count);
        folderCount.setVisibility(View.VISIBLE);
        folderCount.setText("包含： " + item.getNoteCount() + "个便签");

        helper.setChecked(R.id.cb_edit_folder, mCheckList.get(position))
                .addOnClickListener(R.id.iv_edit_folder_rename);
        setSelectedItem(helper, item);
        setFirstItemOrNormal(helper);
    }

    private void setSelectedItem(BaseViewHolder helper, NoteFolder item) {
        if (EditFolderConstants.selectedItem == helper.getLayoutPosition()) {
            helper.getView(R.id.tv_edit_folder_name).setVisibility(View.GONE);
            helper.getView(R.id.textinput_edit_folder_name).setVisibility(View.VISIBLE);
            helper.setText(R.id.et_edit_folder_name, item.getFolderName())
                    .setImageResource(R.id.iv_edit_folder_rename, R.drawable.ic_done_black);
            if (EditFolderConstants.isNewFolder) {
                editItem(helper);
            }
        } else {
            helper.getView(R.id.tv_edit_folder_name).setVisibility(View.VISIBLE);
            helper.getView(R.id.textinput_edit_folder_name).setVisibility(View.GONE);
            helper.setText(R.id.tv_edit_folder_name, item.getFolderName())
                    .setImageResource(R.id.iv_edit_folder_rename, R.drawable.ic_create_black);
        }
    }

    private void editItem(BaseViewHolder helper) {
        EditFolderConstants.isNewFolder = false;
        EditText editText = helper.getView(R.id.et_edit_folder_name);
        editText.selectAll();
        setFoucus(editText);
    }

    //    获取焦点并弹出键盘
    public void setFoucus(View view) {
//        获取 接受焦点的资格
        view.setFocusable(true);
//        获取 焦点可以响应点触的资格
        view.setFocusableInTouchMode(true);
//        请求焦点
        view.requestFocus();
//        弹出键盘
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(0, 0);
        manager.showSoftInput(view, 0);
    }

    private void setFirstItemOrNormal(BaseViewHolder helper) {

        ImageView ivRename = helper.getView(R.id.iv_edit_folder_rename);
        CheckBox checkBox = helper.getView(R.id.cb_edit_folder);
        RelativeLayout rlRoot = helper.getView(R.id.rl_edit_folder);

        // 第一行(随手记）不可编辑
        if (helper.getLayoutPosition() == 0) {

            checkBox.setAlpha(0.26F);
            rlRoot.setClickable(false);

        } else {

            ivRename.setAlpha(0.54F);
            ivRename.setClickable(true);

            checkBox.setAlpha(0.87F);
            rlRoot.setClickable(true);

        }
    }

}
