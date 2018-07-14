<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class StudentDetail extends Model
{

    protected $table = 'students_details';

    //work in process
    protected $fillable = [
        'firstName', 'secondName', 'thirdName', 'student_id'
    ];

    public function student()
    {
        return $this->belongsTo(Student::class);
    }
}
