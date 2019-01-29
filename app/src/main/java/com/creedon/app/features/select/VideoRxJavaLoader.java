package com.creedon.app.features.select;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import iknow.android.utils.callback.SimpleCallback;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author : J.Chou
 * e-mail : who_know_me@163.com
 * time   : 2018/10/04 1:50 PM
 * version: 1.0
 * description:
 */
public class VideoRxJavaLoader implements ILoader {

	@SuppressLint("CheckResult")
	@Override
	public void load(final Context mContext, final SimpleCallback listener) {
		Observable.create(new ObservableOnSubscribe<Cursor>() {
			@Override
			public void subscribe(ObservableEmitter emitter) throws Exception {

				try {
					ContentResolver contentResolver = mContext.getContentResolver();
					Cursor cursors = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
							null,
							null,
							null,
							MediaStore.Video.Media.DATE_MODIFIED + " desc");
					emitter.onNext(cursors);
				} catch (Throwable t) {
					emitter.onError(t);
				}
			}
		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<Cursor>() {
					@Override
					public void accept(Cursor cursors) throws Exception {
						if (listener != null) listener.success(cursors);
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						Log.e("jason", throwable.getMessage());
					}
				});
	}
}
