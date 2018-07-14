<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::group(['prefix' => 'admin'], function () {
    Voyager::routes();
});

Route::get('/login', 'Auth\StudentsLoginController@showLoginForm')->name('student.show-login');
Route::post('/logout', 'Auth\StudentsLoginController@logout')->name('student.logout');
Route::post('/login', 'Auth\StudentsLoginController@login')->name('student.login');
Route::get('/', 'Student\StudentsController@index')->name('student.dashboard');
