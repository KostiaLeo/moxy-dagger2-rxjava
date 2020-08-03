
package com.example.viewstatemvp
/*
import android.content.Context
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RxLearningKotlin {
    fun launch() {
        val migosObs = RxLearning().createMigosObs()
            .flatMap {
                Observable.just(it).delay(it.length * 100L, TimeUnit.MILLISECONDS)
            }
        val disposable =
            migosObs
                .doOnNext { Log.d("migoss", "onNext: $it. ${it.length * 100}ms") }
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe { song ->
                    Log.d("migoss", "song: $song")
                }
    }

    val api = Api()

    fun fetchSmallMovieList(
        categories: List<String>,
        key: String,
        language: String,
        ctx: Context
    ): Single<List<List<SmallMovieList>>> {

        return Flowable.fromIterable(categories)
            .flatMap { category ->
                api.getListOfPosters(category, key, language)
                    .toFlowable()
                    .subscribeOn(Schedulers.io())
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
    }
}

typealias SmallMovieList = Any

//class Api() {
//    fun getListOfPosters(
//        category: String,
//        key: String = "",
//        language: String
//    ): Single<SmallMovie> {
//        return Single.just(listOf(SmallMovieList()))
//    }
//}
//
//
//fun a() {
//    fun putMovieInDatabase(){
//
//        val database = Firebase.firestore
//        _selectProperty.value?.also {
//            val listOfMovie = SmallMovieList(
//                id = it.id,
//                posterPath = it.poster_path,
//                title = it.title,
//                voteAverage = it.vote_average.toFloat(),
//                backdropPath = it.backdrop_path)
//
//            viewModelScope.launch {
//                database.collection("users").document(userId.value!!).collection("movie")
//                    .add(listOfMovie)
//                    .addOnSuccessListener { documentReference ->
//                        Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
//                        _test.value = true
//                    }.addOnFailureListener { e ->
//                        Log.w("TAG", "Error adding document", e)
//                    }
//            }
//        }
//    }
//
//    Single.fromCallable {
//        database.collection("users").document(userId.value!!).collection("movie")
//            .add(listOfMovie)
//    }
//
//    Observable.fromPublisher {
//        database.collection("users").document(userId.value!!).collection("movie")
//            .add(listOfMovie)
//    }
//}*/
