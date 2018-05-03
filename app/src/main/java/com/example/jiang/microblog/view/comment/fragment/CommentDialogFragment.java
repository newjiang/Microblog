package com.example.jiang.microblog.view.comment.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jiang.microblog.R;

/**
 * Created by jiang on 2018/4/16.
 */

public class CommentDialogFragment extends DialogFragment implements View.OnClickListener{

    private EditText commentEditText;
    private ImageView albumButton;
    private ImageView atImage;
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

        commentEditText = (EditText) mDialog.findViewById(R.id.edit_comment);
        albumButton = (ImageView) mDialog.findViewById(R.id.image_photo);
        atImage = (ImageView) mDialog.findViewById(R.id.image_at);
        sendCommemt = (ImageView) mDialog.findViewById(R.id.image_comment_send);

        fillEditText();
        setSoftKeyboard();

        commentEditText.addTextChangedListener(mTextWatcher);
        albumButton.setOnClickListener(this);
        atImage.setOnClickListener(this);

        sendCommemt.setOnClickListener(this);

        return mDialog;
    }

    private void fillEditText() {
        dataCallback = (DialogFragmentDataCallback) getActivity();
        commentEditText.setText(dataCallback.getCommentText());
        commentEditText.setSelection(dataCallback.getCommentText().length());
        if (dataCallback.getCommentText().length() == 0) {
            sendCommemt.setEnabled(false);
            sendCommemt.setColorFilter(ContextCompat.getColor(getActivity(), R.color.iconCover));
        }
    }

    private void setSoftKeyboard() {
        commentEditText.setFocusable(true);
        commentEditText.setFocusableInTouchMode(true);
        commentEditText.requestFocus();

        //TODO 为commentEditText 设置监听器，在 DialogFragment 绘制完后立即呼出软键盘，呼出成功后即注销
        commentEditText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    if (inputMethodManager.showSoftInput(commentEditText, 0)) {
                        commentEditText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
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
            case R.id.image_photo:
                Toast.makeText(getActivity(), "点击了图片", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image_at:
                Toast.makeText(getActivity(), "点击了@", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image_comment_send:
                dataCallback.sendComment(commentEditText.getText().toString());
                commentEditText.setText("");
                dismiss();
                break;
            default:
                break;
        }
    }
}