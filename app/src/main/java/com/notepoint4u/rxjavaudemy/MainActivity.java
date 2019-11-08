package com.notepoint4u.rxjavaudemy;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String greetings = "Hello from RxJava";
   // private Observable<String> myObservable;
    //  private Observer<String> myObserver;
    private DisposableObserver<String> myObserver;
    private DisposableObserver<String> myObserver2;
//    private Disposable disposable;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.text_rx_java);

        //Initialise Observable with just operator.
        //  myObservable = Observable.just(greetings);
        //Do work on background thread
        //   myObservable.subscribeOn(Schedulers.io()); // Manipulation of data will happen here. database operation, file storage etc.


        //Observe the result on the main thread:
        //   myObservable.observeOn(AndroidSchedulers.mainThread()); // All UI related operation.


//        myObservable = Observable.just(greetings)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());

       /* myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: Called");
                //Add or assign the disposable here
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: Called");
                textView.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: Called");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Called");
            }
        };*/



        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: Called");
                textView.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: Called");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Called");
            }
        };
        // Efficient code in single line of code:
        compositeDisposable.add(
                Observable.just(greetings)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(myObserver) // It will return a disposable observer.
        );

        //Add disposable observer to the compositeDisposable :
      //  compositeDisposable.add(myObserver);
      //  myObservable.subscribe(myObserver);

        myObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: Called");
                Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: Called");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Called");
            }
        };

//        compositeDisposable.add(myObserver2);
//        myObservable.subscribe(myObserver2);
        compositeDisposable.add(
                Observable.just(greetings)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(myObserver2) // It will return a disposable observer.
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // disposable.dispose();

//        myObserver.dispose();
//        myObserver2.dispose();

        compositeDisposable.clear();
    }
}
