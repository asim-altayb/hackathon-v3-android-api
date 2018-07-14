<?php

namespace App\Http\Controllers\Auth;

use Illuminate\Foundation\Auth\AuthenticatesUsers;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Auth;

class StudentsLoginController extends Controller
{
    use AuthenticatesUsers;

    public function __construct()
    {
        $this->middleware('guest:student', ['except' => ['logout']]);
    }

    public function showLoginForm()
    {
        return view('auth.login');
    }

    public function logout()
    {
        Auth::guard('student')->logout();
        return redirect('/');
    }

    public function login(Request $request)
    {
        // Validate the form data
        $this->validate($request, [
            'stdID'   => 'required',
            'password' => 'required'
        ]);

        // Attempt to log the student in
        if (Auth::guard('student')->attempt(['stdID' => $request->stdID, 'password' => $request->password], $request->remember)) {
            // if successful, then redirect to their intended location
            return redirect()->intended(route('student.dashboard'));
        }

        // if unsuccessful, then redirect back to the login with the form data
        return redirect()->back()->withInput($request->only('stdID', 'remember'));
    }

}
