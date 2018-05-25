package com.example.jiang.microblog.view.comment.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.utils.DialogFragmentDataCallback;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.utils.TextColorTools;
import com.example.jiang.microblog.view.share.at.AtActivity;

import static android.app.Activity.RESULT_OK;
import static com.example.jiang.microblog.view.share.ShareActivity.AT_FRIENDS;

/**
 * Created by jiang on 2018/4/16.
 */

public class CommentDialogFragment extends DialogFragment implements View.OnClickListener{

    //评论内容
    private EditText commentText;
    //@图标
    private ImageView icAt;
    //是否转发选择框
    private CheckBox isRetweeted;
    //发送评论图片
    private ImageView sendCommemt;

    private InputMethodManager inputMethodManager;
    private DialogFragmentDataCallback dataCallback;

    @Override
    public void onAttach(Context context) {
        if (!(getActivity() instanceof DialogFragmentDataCallback)) {
            throw new IllegalStateException("DialogFragment 所在的 activity 必须实现 DialogFragmentDataCallback 接口");
        }
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog mDialog = new Dialog(getActivity(), R.style.BottomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.layout_dialog_comment);
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams;
        if (window != null) {
            layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }

        commentText = (EditText) mDialog.findViewById(R.id.edit_comment);
        icAt = (ImageView) mDialog.findViewById(R.id.at_icon);
        isRetweeted = (CheckBox) mDialog.findViewById(R.id.is_retweeted);
        sendCommemt = (ImageView) mDialog.findViewById(R.id.send_icon);

        fillEditText();
        setSoftKeyboard();

        commentText.addTextChangedListener(mTextWatcher);
        icAt.setOnClickListener(this);
        isRetweeted.setOnClickListener(this);
        sendCommemt.setOnClickListener(this);
        return mDialog;
    }

    private void fillEditText() {
        dataCallback = (DialogFragmentDataCallback) getActivity();
        commentText.setText(dataCallback.getCommentText());
        commentText.setSelection(dataCallback.getCommentText().length());
        if (dataCallback.getCommentText().length() == 0) {
            sendCommemt.setEnabled(false);
            sendCommemt.setColorFilter(ContextCompat.getColor(getActivity(), R.color.iconCover));
        }
    }

    private void setSoftKeyboard() {
        commentText.setFocusable(true);
        commentText.setFocusableInTouchMode(true);
        commentText.requestFocus();

        //TODO 为commentEditText 设置监听器，在 DialogFragment 绘制完后立即呼出软键盘，呼出成功后即注销
        commentText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    if (inputMethodManager.showSoftInput(commentText, 0)) {
                        commentText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    private TextWatcher mTextWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() > 0) {
                sendCommemt.setEnabled(true);
                sendCommemt.setClickable(true);
                sendCommemt.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            } else {
                sendCommemt.setEnabled(false);
                sendCommemt.setColorFilter(ContextCompat.getColor(getActivity(), R.color.iconCover));
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.at_icon:
                startActivityForResult(new Intent(getActivity(), AtActivity.class), AT_FRIENDS);
                break;
            case R.id.send_icon:
                int comment_ori;
                boolean checked = isRetweeted.isChecked();
                if (checked) {
                    comment_ori = 0;
                } else {
                    comment_ori = 1;
                }
                dataCallback.sendComment(commentText.getText().toString(), comment_ori);
                commentText.setText("");
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AT_FRIENDS:
                if (resultCode == RESULT_OK) {
                    String friends = data.getStringExtra(IntentKey.AT_FRIEND);
                    SpannableStringBuilder highlight = TextColorTools.highlight(commentText.getText() + friends, friends);
                    commentText.setText(highlight);
                }
                break;
            default:
                break;
        }
    }
}