<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Hash;
use App\Token;
use App\Department;
use App\Student;
use App\StudentDetail;
use App\Level;
use App\Group;
use App\Thread;
use App\Reply;
use App\Channel;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('test', function (Request $request) {
    var_dump(StudentDetail::all()->count());exit();
    return response("ssd");
});

/*
    Student Authentication
*/

Route::prefix('auth')->group(function () {

    /* Authenticate student to login using student ID and password */

    Route::post('/login', function (Request $request) {
        $studentId = $request->studentId;
        $password = $request->password;

        $student = Student::where('stdID', $studentId)
                            ->where('password', $password)
                            ->first();    

        if (!$student) {
            return response()->json([
                'data' => '',
                'status' => 0,
                'error' => 'Login failed please try again'
            ]);
        }
        // $group = Group::find($student->group_id);
        // $level = Level::find('level_id', $group->level_id);

        // $dept = Department::find($level->department_id);

        // $details = $student->studentDetails;

        $details = StudentDetail::where('student_id', $student->id)->first();

        $token = createAppSessionToken($student->id);

        $data = array(
            'fullName' => $details->firstName . ' ' . $details->secondName,
            'email' => $details->email,
            'phone' => $details->phone,
            'level' => '$level->name',
            'department' => '$dept->name',
            'student_id' => $studentId,
            'id' => $student->id,
            'token' => $token,
        );

        return response()->json([
            'data' => $data,
            'status' => 1,
            'error' => ''
        ]);
    });

    Route::post('/password/reset', function (Request $request) {
        $student = Student::where('stdID', $request->studentId)
                            ->where('id', $request->id)
                            ->first();
        if (!$student) {
            return response()->json([
                'data' => '',
                'status' => 0,
                'error' => 'Auth failed please try again'
            ]);
        }

        $student->password = $request->password;
        $student->save();

        return response()->json([
            'data' => '',
            'status' => 1,
            'error' => ''
        ]);
    });

});


/*
    Posts for News Feed
*/

Route::prefix('posts')->group(function () {

    Route::get('/channels', function (Request $request) {
        $channels = Channel::all()->toArray();
        return response()->json([
            'data' => $channels,
            'status' => 1,
            'error' => '',
        ]);
    });

    /* Getting all posts */

    Route::get('', function (Request $request) {

        // $posts = 
            
        // Thread::where('1')
        //     ->orderBy('id', 'desc')
        //     ->get()
        //     ->toArray();

        $posts = DB::table('threads')
                    ->select("*")
                    ->orderBy('id', 'desc')
                    ->get()
                    ->toArray();

        $status = 1;
        $err = '';

        if (count($posts) == 0) {
            $status = 0;
        }

        if (!$posts) {
            $status = 0;
            $err = 'Can not get posts';
        }

        return response()->json([
            'data' => $posts,
            'status' => $status,
            'error' => $err,
        ]);
    });

    /* Getting post details */

    Route::get('/{post_id}', function (Request $request, $post_id) {
        
        $post = Thread::with(['replies', 'student'])->find($post_id);

        $status = 1;
        $err = '';

        if (!$post) {
            $status = 0;
            $err = 'Can not get posts';
        }

        // $replies = Reply::where('thread_id', $post_id)->get();

        // var_dump($replies);exit();
        $post_details = $post->toArray();

        return response()->json([
            'data' => $post_details,
            'status' => $status,
            'error' => $err,
        ]);

    });

    /* Create new posts */

    Route::post('', function (Request $request) {

        $post = new Thread;
        $post->student_id = $request->student_id;
        $post->title = $request->title;
        $post->body = $request->body;
        $post->channel_id = $request->channel_id;
        $post->save();

        $status = 1;
        $err = '';

        if (!$post) {
            $status = 0;
            $err = 'Can not get posts';
        }

        return response()->json([
            'data' => $post->id,
            'status' => $status,
            'error' => $err,
        ]);

    });

    /* Update post data using ID */

    Route::put('/{post_id}', function (Request $request, $post_id) {
        return response()->json([
            'data' => 'update post: ' . $post_id 
        ]);
    });

    /* Delete post using ID */

    Route::delete('/{post_id}', function (Request $request, $post_id) {
        
        $post = Thread::find($post_id)->delete();

        return response()->json([
            'data' => 'delete post: ' . $post_id
        ]);
    });

    Route::post('/{post_id}/reply', function (Request $request, $post_id) {
        $post = Thread::find($post_id);
        $reply = new Reply;
        $reply->thread_id = $post_id;
        $reply->user_id = $request->student_id;
        $reply->body = $request->body;
        $reply->save();

        return response()->json([
            'data' => $reply->id,
            'status' => 1,
            'error' => '',
        ]);

    });

    Route::delete('/reply/{reply_id}', function (Request $request, $reply_id) {
        $post = Thread::find($post_id);
        $reply = Reply::find($reply_id)->delete();

        return response()->json([
            'data' => $reply->id,
            'status' => 1,
            'error' => '',
        ]);

    });




});


/*
    Helper function
*/

/* Create access token for api consumer (Android App) */
function createAppSessionToken($user_id, $device_id = '') {
    $token = Token::where('user_id', $user_id)->first();
    if (!$token) {
        $token = new Token;
        $token->user_id = $user_id;
        $token->device_id = $device_id;
    }
    
    $gen_token = md5(uniqid($user_id, true));
    $token->token = $gen_token;
    $token->save();
    return $gen_token;
}

/* Checking the validation of the access token */
function checkToken($user_id, $user_token) {
    $token = Token::where('user_id', $user_id)->first();

    if ($token->token === $user_token && strlen($user_token) > 0) {
        return true;
    }

    return false;
}