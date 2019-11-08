package com.notepoint4u.rxjavaudemy;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SecondScreen extends AppCompatActivity {

    private static final String TAG = "SecondScreen";
    private String[] greeting = {"Hello", "Hi", "Bye"};
    //private DisposableObserver<String> myObserver;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        textView = findViewById(R.id.text2_rx_java);

        /*Just operator*/
        // Observable<String> myObservable;// = Observable.just("Hello", "Hi", "Bye"); //

        /*From array operator*/
        // myObservable = Observable.fromArray(greeting);

        /*Range operator*/
        //    Observable<Integer> myObservable = Observable.range(1, 10);

              /*  compositeDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver())
        );*/

        /*Create operator*/
        Observable<Student> myObservable = Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
                //Get the list of students:
                ArrayList<Student> studentArrayList = DataSource.getStudents();
                for (Student student : studentArrayList) {
                    //Emit the observable:
                    emitter.onNext(student);
                }

                //When looping completes, call the onComplete() on the emitter.
                emitter.onComplete();
            }
        });


      /*  compositeDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())

                        *//*map() operator*//* // -> It takes an item as input and make changes to it and provide a modified item.
//                        .map(new Function<Student, Student>() {
//                            @Override
//                            public Student apply(Student student) throws Exception {
//
//                                student.setName(student.getName().toUpperCase());
//                                return student;
//                            }
//                        })

                        *//*flatMap() operator*//* // -> It takes an item as input and make changes to it and provide an observable.
                        // It does not preserve the order
//                        .flatMap(new Function<Student, Observable<Student>>() {
//                            @Override
//                            public Observable<Student> apply(Student student) throws Exception {
//
//                                Student student1 = new Student(student.getName(),"",0,"");
//                                Student student2 = new Student("New member "+student.getName(),"",0,"");
//
//                                student.setName(student.getName().toUpperCase());
//                                return Observable.just(student,student1,student2);
//                            }
//                        })

                        *//* Concat Map:*//* //-> It preserves the order. -> Same as the flatMap():
                        // It waits for each observable to finish all the work untill the next one is processed. so it is slower.
                        //Flat map is faster.
                        .concatMap(new Function<Student, Observable<Student>>() {
                            @Override
                            public Observable<Student> apply(Student student) throws Exception {

                                Student student1 = new Student(student.getName(),"",0,"");
                                Student student2 = new Student("New member "+student.getName(),"",0,"");

                                student.setName(student.getName().toUpperCase());
                                return Observable.just(student,student1,student2);
                            }
                        })

                        .subscribeWith(getStudentObserver())
        );*/


        //Buffer operator:
      /*  myObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .buffer(4) // bundle the items into 4.
                .subscribe(new Observer<List<Integer>>() { // Returns a list of observer of type Integer.
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d(TAG, "onNext: Called");

                        for (Integer i : integers) {
                            Log.d(TAG, "onNext: "+i);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }
                });*/


        /* Filter operator: */

        compositeDisposable.add(myObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Student>() {
                    @Override
                    public boolean test(Student student) throws Exception {
                        return student.getAge() > 25;
                    }
                })
                .subscribeWith(getStudentObserver())
        );

        /* Distinct operator: */ // --> supress the duplicate items emitted by observable.
        Observable<Integer> observable = Observable.just(1, 2, 3, 3, 3, 2, 1, 2, 4, 5);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.distinct()
                .skipWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 2;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: Distinct:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private DisposableObserver getObserver() {
        DisposableObserver myObserver = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object object) {
                Log.d(TAG, "onNext: Called");
                Log.d(TAG, "onNext: " + object);
                textView.setText(object.toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: Called");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: called");
            }
        };

        return myObserver;
    }

    private DisposableObserver<Student> getStudentObserver() {

        return new DisposableObserver<Student>() {
            @Override
            public void onNext(Student student) {
               /* Log.d(TAG, "onNext: Name:" + student.getName() +
                        " Email: " + student.getEmail() +
                        " Age: " + student.getAge() +
                        " Reg_num: " + student.getRegistrationNumber());*/

                Log.d(TAG, "onNext: Name:" + student.getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Called");
            }
        };
    }
}
