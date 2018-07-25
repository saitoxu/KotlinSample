package io.saitoxu.kotlinsample

/**
 * Created by yosuke.saito on 2018/07/25.
 */

data class RandomUserDemo(var info: Info,
                          var results: List<Result>)

data class Info(var seed: String,
                var results: Int,
                var page: Int,
                var version: String)

data class Result(var gender: String,
                  var email: String,
                  var phone: String,
                  var cell: String)