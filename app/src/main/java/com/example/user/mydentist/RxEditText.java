package com.example.user.mydentist;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by user on 02.08.2017.
 */

public class RxEditText {
    public static Observable<String> getTextWatcherObsevable(@NonNull final EditText editText){
        final PublishSubject <String> subject = PublishSubject.create();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            subject.onNext(editable.toString());
            }
        });
        return subject;
    }
}
